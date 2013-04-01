package com.darmasoft.pimote;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class PiResponse {

	private static final String TAG = "pimote:PiResponse";
	
	private boolean _status = false;
	private String _json_response_string = null;
	private JSONObject _json_response_object = null;
	private PiRequest _pi_request = null;
	
	public PiResponse(String json, boolean status) {
		Log.d(TAG, "PiResponse()");
		_json_response_string = json;
		_status = status;
		parse_response_string();
	}

	public int method_num() {
		if (_pi_request != null) {
			return(_pi_request.method_num());
		} else {
			return(-1);
		}
	}
	public void set_status(boolean status) {
		_status = status;
	}
	
	public boolean status() {
		return(_status);
	}
	
	public String json_response_string() {
		return(_json_response_string);
	}
	
	public void set_json_response_string(String json) {
		_json_response_string = json;
	}
	
	protected void parse_response_string() {
		Log.d(TAG, "parse_response_string()");
		_json_response_object = (JSONObject) JSONValue.parse(_json_response_string);
	}
	
	public JSONObject json_response_object() {
		return(_json_response_object);
	}
	
	public PiRequest pi_request() {
		return(_pi_request);
	}
	
	public void set_pi_request(PiRequest req) {
		_pi_request = req;
	}
}
