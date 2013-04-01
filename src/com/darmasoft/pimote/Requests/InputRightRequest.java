package com.darmasoft.pimote.Requests;

import java.util.Map;

import com.darmasoft.pimote.PiRequest;

public class InputRightRequest extends PiRequest {
	
	public InputRightRequest(int id) {
		super("Input.Right", null, id);
	}
	
	public InputRightRequest() {
		super("Input.Right", null, 0);
	}
	
	@Override
	public InputRightResponse create_response(String json, boolean status) {
		return(new InputRightResponse(json, status));
	}

	public int method_num() {
		return(PiRequest.METHOD_INPUT_RIGHT);
	}
}
