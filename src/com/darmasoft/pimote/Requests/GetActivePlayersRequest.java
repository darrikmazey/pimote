package com.darmasoft.pimote.Requests;

import java.util.Map;

import com.darmasoft.pimote.PiRequest;

public class GetActivePlayersRequest extends PiRequest {
	
	public GetActivePlayersRequest(int id) {
		super("Player.GetActivePlayers", null, id);
	}
	
	public GetActivePlayersRequest() {
		super("Player.GetActivePlayers", null, 0);
	}
	
	@Override
	public GetActivePlayersResponse create_response(String json, boolean status) {
		return(new GetActivePlayersResponse(json, status));
	}

	public int method_num() {
		return(PiRequest.METHOD_GET_ACTIVE_PLAYERS);
	}
}
