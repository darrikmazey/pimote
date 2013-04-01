package com.darmasoft.pimote.Requests;

import java.util.Map;

import com.darmasoft.pimote.PiRequest;

public class InputDownRequest extends PiRequest {
	
	public InputDownRequest(int id) {
		super("Input.Down", null, id);
	}
	
	public InputDownRequest() {
		super("Input.Down", null, 0);
	}
	
	@Override
	public InputDownResponse create_response(String json, boolean status) {
		return(new InputDownResponse(json, status));
	}

	public int method_num() {
		return(PiRequest.METHOD_INPUT_DOWN);
	}
}
