package com.darmasoft.pimote;

import java.util.HashMap;
import java.util.Map;

public class JSONRPCServer {
	
	private static final String TAG = "pimote:JSONRPCServer";
	private static final int MAX_REQUESTS = 20;

	private Map<Integer,PiRequest> _request_map = new HashMap<Integer,PiRequest>();
	private int _first_marker = -1;
	private int _second_marker = -1;
	
	public JSONRPCServer() {
		Log.d(TAG, "JSONRPCServer()");
	}

	public void queue_request(PiRequest req) {
		Log.d(TAG, "queue_request(" + req.method() + ")");
		req.set_id(next_available_id());
		_request_map.put(req.id(), req);
		PiRequestTask task = new PiRequestTask();
		task.execute(req);
	}
	
	public void request_finished(PiRequest req) {
		Log.d(TAG, "request_finished(" + req.method() + ")");
		_request_map.remove(req.id());
	}
	
	private int next_available_id() {
		for (int i = 0; i <= MAX_REQUESTS; i++) {
			if (!_request_map.containsKey(i)) {
				return(i);
			}
		}
		return(-1);
	}
}
