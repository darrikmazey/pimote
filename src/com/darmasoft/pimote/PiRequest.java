package com.darmasoft.pimote;

import java.util.HashMap;
import java.util.Map;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

public class PiRequest {

	private static final String TAG = "pimote:PiRequest";
	
	private String _method = null;
	private Map<String,Object> _params = null;
	private int _id = 0;
	private PiResponse _response = null;
	
	public static final int METHOD_PING = 1;
	public static final int METHOD_GET_ACTIVE_PLAYERS = 2;
	
	public PiRequest(String method, Map<String,Object> params, int id) {
		_method = method;
		_params = params;
		_id = id;
	}

	public PiRequest(String method, Map<String,Object> params) {
		this(method, params, 0);
	}
	
	public PiRequest() {
		this(null, new HashMap<String,Object>(), 0);
	}
	
	public String method() {
		return(_method);
	}
	
	public void set_method(String method) {
		_method = method;
	}
	
	public int method_num() {
		return(-1);
	}
	
	public Map<String,Object> params() {
		return(_params);
	}
	
	public void set_params(Map<String,Object> params) {
		_params = params;
	}
	
	public int id() {
		return(_id);
	}
	
	public void set_id(int id) {
		_id = id;
	}
	
	public PiResponse response() {
		return(_response);
	}
	
	public void set_response(PiResponse response) {
		_response = response;
		response.set_pi_request(this);
	}
	
	public JSONRPC2Request json_rpc_request() {
		return(new JSONRPC2Request(_method, _params, _id));
	}
	
	public PiResponse create_response(String json, boolean status) {
		return(new PiResponse(json, status));
	}
}
