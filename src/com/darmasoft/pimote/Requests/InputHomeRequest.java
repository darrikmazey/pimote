package com.darmasoft.pimote.Requests;

import java.util.Map;

import com.darmasoft.pimote.PiRequest;

public class InputHomeRequest extends PiRequest {
	
	public InputHomeRequest(int id) {
		super("Input.Home", null, id);
	}
	
	public InputHomeRequest() {
		super("Input.Home", null, 0);
	}
	
	@Override
	public InputHomeResponse create_response(String json, boolean status) {
		return(new InputHomeResponse(json, status));
	}

	public int method_num() {
		return(PiRequest.METHOD_INPUT_HOME);
	}
}
