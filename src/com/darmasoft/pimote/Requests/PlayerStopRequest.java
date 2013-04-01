package com.darmasoft.pimote.Requests;

import java.util.HashMap;
import java.util.Map;

import com.darmasoft.pimote.PiRequest;
import com.darmasoft.pimote.PimoteApplication;

public class PlayerStopRequest extends PiRequest {
	
	public PlayerStopRequest(int playerid, int id) {
		super("Player.Stop", null, id);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("playerid", playerid);
		set_params(params);
	}
	
	public PlayerStopRequest(int playerid) {
		super("Player.Stop", null, 0);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("playerid", playerid);
		set_params(params);
	}
	
	public PlayerStopRequest() {
		super("Player.Stop", null, 0);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("playerid", PimoteApplication.get_app().active_video_player());
		set_params(params);
	}
	@Override
	public PlayerStopResponse create_response(String json, boolean status) {
		return(new PlayerStopResponse(json, status));
	}

	public int method_num() {
		return(PiRequest.METHOD_PLAYER_STOP);
	}
}
