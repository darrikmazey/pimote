package com.darmasoft.pimote.Requests;

import com.darmasoft.pimote.Log;
import com.darmasoft.pimote.PiResponse;

public class InputHomeResponse extends PiResponse {

	private static final String TAG = "pimote:InputHomeResponse";
	
	public InputHomeResponse(String json, boolean status) {
		super(json, status);
		Log.d(TAG, "InputHomeResponse()");
	}

	@Override
	protected void parse_response_string() {
		super.parse_response_string();
		Log.d(TAG, "parse_response_string()");
	}
}
