package com.darmasoft.pimote;

public class PingResponse extends PiResponse {

	private static final String TAG = "pimote:PingResponse";
	
	public PingResponse(String json, boolean status) {
		super(json, status);
		Log.d(TAG, "PingResponse()");
	}

	@Override
	protected void parse_response_string() {
		super.parse_response_string();
		Log.d(TAG, "parse_response_string()");
	}
}
