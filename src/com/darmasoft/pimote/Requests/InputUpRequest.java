package com.darmasoft.pimote.Requests;

import java.util.Map;

import com.darmasoft.pimote.PiRequest;

public class InputUpRequest extends PiRequest {
	
	public InputUpRequest(int id) {
		super("Input.Up", null, id);
	}
	
	public InputUpRequest() {
		super("Input.Up", null, 0);
	}
	
	@Override
	public InputUpResponse create_response(String json, boolean status) {
		return(new InputUpResponse(json, status));
	}

	public int method_num() {
		return(PiRequest.METHOD_INPUT_UP);
	}
}
