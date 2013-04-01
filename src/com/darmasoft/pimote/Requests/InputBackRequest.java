package com.darmasoft.pimote.Requests;

import java.util.Map;

import com.darmasoft.pimote.PiRequest;

public class InputBackRequest extends PiRequest {
	
	public InputBackRequest(int id) {
		super("Input.Back", null, id);
	}
	
	public InputBackRequest() {
		super("Input.Back", null, 0);
	}
	
	@Override
	public InputBackResponse create_response(String json, boolean status) {
		return(new InputBackResponse(json, status));
	}

	public int method_num() {
		return(PiRequest.METHOD_INPUT_BACK);
	}
}
