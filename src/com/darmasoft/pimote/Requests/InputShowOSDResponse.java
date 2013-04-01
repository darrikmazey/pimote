package com.darmasoft.pimote.Requests;

import com.darmasoft.pimote.Log;
import com.darmasoft.pimote.PiResponse;

public class InputShowOSDResponse extends PiResponse {

	private static final String TAG = "pimote:InputShowOSDResponse";
	
	public InputShowOSDResponse(String json, boolean status) {
		super(json, status);
		Log.d(TAG, "InputShowOSDResponse()");
	}

	@Override
	protected void parse_response_string() {
		super.parse_response_string();
		Log.d(TAG, "parse_response_string()");
	}
}
