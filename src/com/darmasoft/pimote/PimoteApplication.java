package com.darmasoft.pimote;

import java.net.URL;
import java.util.Date;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import com.darmasoft.pimote.Requests.GetActivePlayersRequest;
import com.darmasoft.pimote.Requests.PingRequest;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class PimoteApplication extends Application {

	private static final String TAG = "pimote:PimoteApplication";
	private static PimoteApplication _instance = null;
	private JSONRPC2Session _rpc_session = null;
	private JSONRPCServer _server = null;
	
	private int _current_host_id = -1;
	private boolean _current_status = false;
	private Date _last_status_update = null;
	private int _active_video_player = -1;
	private boolean _player_paused = false;
	
	// listeners
	private JSONRPCStatusListener _status_listener = null;
	private HostChangedListener _host_listener = null;
	private ActivePlayerListener _player_listener = null;
	
	public PimoteApplication() {
		_instance = this;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate()");

		Cursor c = RaspbmcHost.all();
		int count = c.getCount();
		if (count == 1) {
			_current_host_id = RaspbmcHost.from_cursor(c).id();
			configure_rpc_session();
			Log.d(TAG, "Only one host.  Using: " + _current_host_id);
		}
		_server = new JSONRPCServer();
	}

	public static Context get_context() {
		return(_instance);
	}
	
	public static PimoteApplication get_app() {
		return(_instance);
	}
	
	public JSONRPCServer json_rpc_server() {
		return(_server);
	}
	
	public RaspbmcHost current_host() {
		if (_current_host_id > 0) {
			return(RaspbmcHost.find_by_id(_current_host_id));
		} else {
			return(null);
		}
	}
	public static void host_deleted(int id) {
		_instance.raspbmc_host_deleted(id);
	}
	
	public void raspbmc_host_deleted(int id) {
		if (_current_host_id == id) {
			_current_host_id = -1;
		}
	}
	
	public int current_host_id() {
		return(_current_host_id);
	}
	
	public void set_current_host_id(int hid) {
		Log.d(TAG, "set_current_host_id(" + hid + ")");
		Log.d(TAG, "old current_host_id: " + _current_host_id);
		if ((hid > 0)&&(_current_host_id != hid)) {
			if (_host_listener != null) {
				_host_listener.hostChanged();
			}
		}
		_current_host_id = hid;
		configure_rpc_session();
	}
	
	private void configure_rpc_session() {
		Log.d(TAG, "configure_rpc_session()");
		if (_current_host_id <= 0) {
			_rpc_session = null;
		} else {
			RaspbmcHost h = current_host();
			URL server_url = h.server_url();
			Log.d(TAG, "server_url: " + server_url.toString());
			_rpc_session = new JSONRPC2Session(server_url);
			ping_server();
			get_active_players();
		}
	}
	
	public boolean host_status() {
		return(_current_status);
	}
	
	public Date host_status_last_updated() {
		return(_last_status_update);
	}
	
	public void set_host_status(boolean st) {
		set_host_status(st, true);
	}
	
	public void set_host_status(boolean st, boolean notify) {
		Log.d(TAG, "set_host_status(" + st + ")");
		_last_status_update = new Date();
		boolean old_status = _current_status;
		_current_status = st;
		if (notify) {
			notify_status_listener();
		}
	}
	
	public void notify_status_listener() {
		notify_status_listener(_current_status, is_playing_video());
	}
	
	public JSONRPC2Session rpc_session() {
		return(_rpc_session);
	}
	
	public void set_status_listener(JSONRPCStatusListener listener) {
		_status_listener = listener;
	}
	
	public void set_host_changed_listener(HostChangedListener listener) {
		_host_listener = listener;
	}
	
	public void set_active_player_listener(ActivePlayerListener listener) {
		_player_listener = listener;
	}
	
	private void notify_status_listener(boolean status, boolean playing) {
		if (_status_listener != null) {
			_status_listener.statusChanged(status);
			_status_listener.videoPlayersChanged(playing);
		}
	}
	
	public void ping_server() {
		Log.d(TAG, "ping_server()");
		json_rpc_server().queue_request(new PingRequest());
	}
	
	public void get_active_players() {
		Log.d(TAG, "get_active_players()");
		json_rpc_server().queue_request(new GetActivePlayersRequest());
	}
	
	public boolean is_playing_video() {
		return(_active_video_player > 0);
	}
	
	public int active_video_player() {
		return(_active_video_player);
	}
	
	public void set_active_player(int player) {
		Log.d(TAG, "set_active_player(" + player + ")");
		_active_video_player = player;
		if (_player_listener != null) {
			_player_listener.activePlayerChanged(is_playing_video());
		}
	}
	
	public void set_player_paused(boolean paused) {
		if (_player_paused != paused) {
			_player_paused = paused;
			if (_player_listener != null) {
				_player_listener.pauseChanged(_player_paused);
			}
		}
	}
}
