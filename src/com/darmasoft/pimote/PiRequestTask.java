package com.darmasoft.pimote;

import java.util.ArrayList;
import java.util.List;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

import android.os.AsyncTask;

public class PiRequestTask extends AsyncTask<PiRequest, Integer, ArrayList<PiResponse>> {

	private static final String TAG = "pimote:PiRequestTask";
	
	private static final int NO_MATCH = -1;
	private static final int CONNECTION_REFUSED = 1;
	
	@Override
	protected ArrayList<PiResponse> doInBackground(PiRequest... reqs) {
		Log.d(TAG, "doInBackground()");
		JSONRPC2Session session = ((PimoteApplication)PimoteApplication.get_context()).rpc_session();
		ArrayList<PiResponse> responses = new ArrayList<PiResponse>();
		if (session == null) {
			PimoteApplication.get_app().set_host_status(false, false);
			return(responses);
		}
		for (int i = 0; i < reqs.length; i++) {
			PiRequest req = reqs[i];
			JSONRPC2Response response = null;
			PiResponse pi_response = null;
			try {
				response = session.send(req.json_rpc_request());
				pi_response = req.create_response(response.toJSONString(), response.indicatesSuccess());
				req.set_response(pi_response);
				responses.add(pi_response);
				PimoteApplication.get_app().set_host_status(true, false);
			} catch (JSONRPC2SessionException e) {
				switch(parseException(e)) {
					case CONNECTION_REFUSED:
						Log.e(TAG, "CONNECTION REFUSED");
						PimoteApplication.get_app().set_host_status(false, false);
						break;
					case NO_MATCH:
						Log.e(TAG, "UNKNOWN ERROR: " + e.getMessage());
						break;
				}
				PimoteApplication.get_app().json_rpc_server().request_finished(req);
			}
		}
		return(responses);
	}

	@Override
	protected void onPostExecute(ArrayList<PiResponse> result) {
		Log.d(TAG, "onPostExecute()");
		for (int i = 0; i < result.size(); i++) {
			PiResponse res = result.get(i);
			PimoteApplication.get_app().json_rpc_server().request_finished(res.pi_request());
			PimoteApplication.get_app().json_rpc_server().handle_pi_response(res);
			if (res.status()) {
				Log.d(TAG, "success: " + res.json_response_string());
			} else {
				Log.d(TAG, "error! " + res.json_response_string());
			}
		}
		PimoteApplication.get_app().notify_status_listener();
	}

	private int parseException(JSONRPC2SessionException e) {
		String msg = e.getMessage();
		if (msg.matches(".*Connection refused.*")) {
			return(CONNECTION_REFUSED);
		} else {
			return(NO_MATCH);
		}
	}
}
