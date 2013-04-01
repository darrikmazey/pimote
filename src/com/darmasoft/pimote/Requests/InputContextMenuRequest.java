package com.darmasoft.pimote.Requests;

import java.util.Map;

import com.darmasoft.pimote.PiRequest;

public class InputContextMenuRequest extends PiRequest {
	
	public InputContextMenuRequest(int id) {
		super("Input.ContextMenu", null, id);
	}
	
	public InputContextMenuRequest() {
		super("Input.ContextMenu", null, 0);
	}
	
	@Override
	public InputContextMenuResponse create_response(String json, boolean status) {
		return(new InputContextMenuResponse(json, status));
	}

	public int method_num() {
		return(PiRequest.METHOD_INPUT_CONTEXT_MENU);
	}
}
