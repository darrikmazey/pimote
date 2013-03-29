package com.darmasoft.raspmote;

import java.util.ArrayList;
import java.util.List;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

import android.os.AsyncTask;

public class JSONRPCRequestTask extends AsyncTask<JSONRPC2Request, Integer, ArrayList<JSONRPC2Response>> {

	private static final String TAG = "raspmote:JSONRPCRequestTask";
	
	private static final int NO_MATCH = -1;
	private static final int CONNECTION_REFUSED = 1;
	
	@Override
	protected ArrayList<JSONRPC2Response> doInBackground(JSONRPC2Request... reqs) {
		Log.d(TAG, "doInBackground()");
		JSONRPC2Session session = ((RaspmoteApplication)RaspmoteApplication.get_context()).rpc_session();
		ArrayList<JSONRPC2Response> responses = new ArrayList<JSONRPC2Response>();
		if (session == null) {
			RaspmoteApplication.get_app().set_host_status(false, false);
			return(responses);
		}
		for (int i = 0; i < reqs.length; i++) {
			JSONRPC2Request req = reqs[i];
			JSONRPC2Response response = null;
			try {
				response = session.send(req);
				responses.add(response);
				RaspmoteApplication.get_app().set_host_status(true, false);
			} catch (JSONRPC2SessionException e) {
				switch(parseException(e)) {
					case CONNECTION_REFUSED:
						Log.e(TAG, "CONNECTION REFUSED");
						RaspmoteApplication.get_app().set_host_status(false, false);
						break;
					case NO_MATCH:
						Log.e(TAG, "UNKNOWN ERROR: " + e.getMessage());
						break;
				}
			}
		}
		return(responses);
	}

	@Override
	protected void onPostExecute(ArrayList<JSONRPC2Response> result) {
		Log.d(TAG, "onPostExecute()");
		for (int i = 0; i < result.size(); i++) {
			JSONRPC2Response res = result.get(i);
			if (res.indicatesSuccess()) {
				Log.d(TAG, "success: " + res.getResult().toString());
			} else {
				Log.d(TAG, "error!");
			}
		}
		RaspmoteApplication.get_app().notify_status_listener();
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
