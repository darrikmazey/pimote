package com.darmasoft.pimote;

import java.util.Map;

public class PingRequest extends PiRequest {
	
	public PingRequest(int id) {
		super("JSONRPC.Ping", null, id);
	}
	
	public PingRequest() {
		super("JSONRPC.Ping", null, 0);
	}
	
	@Override
	public PingResponse create_response(String json, boolean status) {
		return(new PingResponse(json, status));
	}

	public int method_num() {
		return(PiRequest.METHOD_PING);
	}
}
