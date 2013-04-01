package com.darmasoft.pimote;

public class GetActivePlayersResponse extends PiResponse {

	private static final String TAG = "pimote:GetActivePlayersResponse";
	
	public GetActivePlayersResponse(String json, boolean status) {
		super(json, status);
		Log.d(TAG, "GetActivePlayersResponse()");
	}

	@Override
	protected void parse_response_string() {
		super.parse_response_string();
		Log.d(TAG, "parse_response_string()");
	}
}
