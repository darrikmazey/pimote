package com.darmasoft.pimote.Requests;

import java.util.Map;

import com.darmasoft.pimote.PiRequest;

public class InputShowOSDRequest extends PiRequest {
	
	public InputShowOSDRequest(int id) {
		super("Input.ShowOSD", null, id);
	}
	
	public InputShowOSDRequest() {
		super("Input.ShowOSD", null, 0);
	}
	
	@Override
	public InputShowOSDResponse create_response(String json, boolean status) {
		return(new InputShowOSDResponse(json, status));
	}

	public int method_num() {
		return(PiRequest.METHOD_INPUT_SHOW_OSD);
	}
}
