package com.darmasoft.pimote.Requests;

import com.darmasoft.pimote.Log;
import com.darmasoft.pimote.PiResponse;

public class InputDownResponse extends PiResponse {

	private static final String TAG = "pimote:InputDownResponse";
	
	public InputDownResponse(String json, boolean status) {
		super(json, status);
		Log.d(TAG, "InputDownResponse()");
	}

	@Override
	protected void parse_response_string() {
		super.parse_response_string();
		Log.d(TAG, "parse_response_string()");
	}
}
