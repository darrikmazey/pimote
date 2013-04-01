package com.darmasoft.pimote.Requests;

import java.util.Map;

import com.darmasoft.pimote.PiRequest;

public class InputLeftRequest extends PiRequest {
	
	public InputLeftRequest(int id) {
		super("Input.Left", null, id);
	}
	
	public InputLeftRequest() {
		super("Input.Left", null, 0);
	}
	
	@Override
	public InputLeftResponse create_response(String json, boolean status) {
		return(new InputLeftResponse(json, status));
	}

	public int method_num() {
		return(PiRequest.METHOD_INPUT_LEFT);
	}
}
