package com.darmasoft.pimote.Requests;

import java.util.HashMap;
import java.util.Map;

import com.darmasoft.pimote.PiRequest;
import com.darmasoft.pimote.PimoteApplication;

public class PlayerPlayPauseRequest extends PiRequest {
	
	public PlayerPlayPauseRequest(int playerid, int id) {
		super("Player.PlayPause", null, id);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("playerid", playerid);
		set_params(params);
	}
	
	public PlayerPlayPauseRequest(int playerid) {
		super("Player.PlayPause", null, 0);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("playerid", playerid);
		set_params(params);
	}
	
	public PlayerPlayPauseRequest() {
		super("Player.PlayPause", null, 0);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("playerid", PimoteApplication.get_app().active_video_player());
		set_params(params);
	}
	@Override
	public PlayerPlayPauseResponse create_response(String json, boolean status) {
		return(new PlayerPlayPauseResponse(json, status));
	}

	public int method_num() {
		return(PiRequest.METHOD_PLAYER_PLAY_PAUSE);
	}
}
