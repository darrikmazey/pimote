package com.darmasoft.pimote;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.darmasoft.pimote.Requests.GetActivePlayersResponse;
import com.darmasoft.pimote.Requests.InputSelectResponse;
import com.darmasoft.pimote.Requests.PingResponse;
import com.darmasoft.pimote.Requests.PlayerPlayPauseResponse;
import com.darmasoft.pimote.Requests.PlayerStopResponse;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import android.os.Handler;
import android.os.Looper;

public class JSONRPCServer {
	
	private static final String TAG = "pimote:JSONRPCServer";
	private static final int MAX_REQUESTS = 20;

	private Map<Integer,PiRequest> _request_map = new HashMap<Integer,PiRequest>();
	private int _first_marker = -1;
	private int _second_marker = -1;
	private Handler _ping_handler = new Handler();
	private Timer _ping_timer = new Timer();
	
	public static final long PING_INTERVAL = 10000;
	public static final long PING_INTERVAL_DISCONNECTED = 10000;
	
	public JSONRPCServer() {
		Log.d(TAG, "JSONRPCServer()");
		start_heartbeat();
	}

	public void restart_heartbeat() {
		stop_heartbeat();
		start_heartbeat(PING_INTERVAL);
	}
	
	public void restart_heartbeat(long delay) {
		stop_heartbeat();
		start_heartbeat(delay);
	}
	
	public void start_heartbeat() {
		start_heartbeat(PING_INTERVAL);
	}
	
	public void start_heartbeat(long delay) {
		Log.d(TAG, "start_heartbeat(" + delay + ")");
		TimerTask ping_task = new TimerTask() {
			@Override
			public void run() {
				_ping_handler.post(new Runnable() {
					public void run() {
						try {
							PimoteApplication.get_app().ping_server();
							if (PimoteApplication.get_app().host_status()) {
								PimoteApplication.get_app().get_active_players();
							}
						} catch (Exception e) {
							Log.e(TAG, e.getMessage());
						}
					}
				});
			}
		};
		_ping_timer.schedule(ping_task, new Date(), delay);
	}
	
	public void stop_heartbeat() {
		_ping_timer.cancel();
	}
	
	public void queue_request(PiRequest req) {
		Log.d(TAG, "queue_request(" + req.method() + ")");
		req.set_id(next_available_id());
		_request_map.put(req.id(), req);
		PiRequestTask task = new PiRequestTask();
		task.execute(req);
	}
	
	public void request_finished(PiRequest req) {
		Log.d(TAG, "request_finished(" + req.method() + ")");
		_request_map.remove(req.id());
	}
	
	private int next_available_id() {
		for (int i = 0; i <= MAX_REQUESTS; i++) {
			if (!_request_map.containsKey(i)) {
				return(i);
			}
		}
		return(-1);
	}

	public void handle_pi_response(PiResponse res) {
		Log.d(TAG, "handle_pi_response(" + res.pi_request().method() + ")");
		switch(res.pi_request().method_num()) {
			case PiRequest.METHOD_PING:
				handle_pi_response((PingResponse)res);
				break;
			case PiRequest.METHOD_GET_ACTIVE_PLAYERS:
				handle_pi_response((GetActivePlayersResponse)res);
				break;
			case PiRequest.METHOD_PLAYER_PLAY_PAUSE:
				handle_pi_response((PlayerPlayPauseResponse)res);
				break;
			case PiRequest.METHOD_PLAYER_STOP:
				handle_pi_response((PlayerStopResponse)res);
				break;
			case PiRequest.METHOD_INPUT_LEFT:
			case PiRequest.METHOD_INPUT_RIGHT:
			case PiRequest.METHOD_INPUT_UP:
			case PiRequest.METHOD_INPUT_DOWN:
			case PiRequest.METHOD_INPUT_BACK:
			case PiRequest.METHOD_INPUT_SELECT:
			case PiRequest.METHOD_INPUT_SHOW_OSD:
			case PiRequest.METHOD_INPUT_HOME:
				Log.d(TAG, "NOOP PiResponse: " + res.pi_request().method());
				break;
			default:
				Log.d(TAG, "UNHANDLED RESPONSE TYPE!");
				break;
		}
	}
	
	public void handle_pi_response(PingResponse res) {
		Log.d(TAG, "handle_pi_response(PingResponse: " + res.pi_request().method() + ")");
	}
	
	public void handle_pi_response(GetActivePlayersResponse res) {
		Log.d(TAG, "handle_pi_response(GetActivePlayersResponse: " + res.pi_request().method() + ")");
		JSONObject jsonobj = res.json_response_object();
		JSONArray players = (JSONArray) jsonobj.get("result");
		int player_count = players.size();
		if (player_count == 0) {
			PimoteApplication.get_app().set_active_player(-1);
			return;
		}
		Log.d(TAG, "active players: " + player_count);
		for (int i = 0; i < player_count; i++) {
			JSONObject player = (JSONObject) players.get(i);
			String ptype = player.get("type").toString();
			int pid = Integer.valueOf(player.get("playerid").toString());
			Log.d(TAG, "player " + pid + ": " + ptype);
			if (ptype.equals("video")) {
				Log.d(TAG, "found video player: " + pid);
				PimoteApplication.get_app().set_active_player(pid);
			}
		}
	}
	
	public void handle_pi_response(PlayerPlayPauseResponse res) {
		Log.d(TAG, "handle_pi_response(PlayerPlayPauseResponse: " + res.pi_request().method() + ")");
		JSONObject jsonobj = res.json_response_object();
		JSONObject result = (JSONObject)jsonobj.get("result");
		int speed = Integer.valueOf(result.get("speed").toString());
		if (speed == 0) {
			PimoteApplication.get_app().set_player_paused(true);
		} else {
			PimoteApplication.get_app().set_player_paused(false);
		}
	}
	
	public void handle_pi_response(PlayerStopResponse res) {
		Log.d(TAG, "handle_pi_response(PlayerStopResponse: " + res.pi_request().method() + ")");
		PimoteApplication.get_app().get_active_players();
	}

}
