package com.darmasoft.pimote.Requests;

import java.util.Map;

import com.darmasoft.pimote.PiRequest;

public class InputSelectRequest extends PiRequest {
	
	public InputSelectRequest(int id) {
		super("Input.Select", null, id);
	}
	
	public InputSelectRequest() {
		super("Input.Select", null, 0);
	}
	
	@Override
	public InputSelectResponse create_response(String json, boolean status) {
		return(new InputSelectResponse(json, status));
	}

	public int method_num() {
		return(PiRequest.METHOD_INPUT_SELECT);
	}
}
