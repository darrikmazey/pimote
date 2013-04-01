package com.darmasoft.pimote;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class RemoteDPadFragment extends Fragment {

	private static final String TAG = "pimote:RemoteDPadFragment";
	
	public RemoteDPadFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_dpad, container, false);
		
		ImageButton b = null;
		
		b = (ImageButton)v.findViewById(R.id.fragment_remote_select);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectClicked();
			}
		});
		
		b = (ImageButton)v.findViewById(R.id.fragment_remote_up);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				upClicked();
			}
		});
		
		b = (ImageButton)v.findViewById(R.id.fragment_remote_down);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				downClicked();
			}
		});
		
		b = (ImageButton)v.findViewById(R.id.fragment_remote_left);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				leftClicked();
			}
		});
		
		b = (ImageButton)v.findViewById(R.id.fragment_remote_right);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rightClicked();
			}
		});

		return(v);
	}

	public void leftClicked() {
		Log.d(TAG, "leftClicked()");
		JSONRPCRequestTask task = new JSONRPCRequestTask();
		JSONRPC2Request req = new JSONRPC2Request("Input.Left", 0);
		task.execute(req);
	}
	
	public void rightClicked() {
		Log.d(TAG, "rightClicked()");
		JSONRPCRequestTask task = new JSONRPCRequestTask();
		JSONRPC2Request req = new JSONRPC2Request("Input.Right", 0);
		task.execute(req);
	}
	
	public void selectClicked() {
		Log.d(TAG, "selectClicked()");
		JSONRPCRequestTask task = new JSONRPCRequestTask();
		JSONRPC2Request req = new JSONRPC2Request("Input.Select", 0);
		task.execute(req);
	}
	
	public void upClicked() {
		Log.d(TAG, "upClicked()");
		JSONRPCRequestTask task = new JSONRPCRequestTask();
		JSONRPC2Request req = new JSONRPC2Request("Input.Up", 0);
		task.execute(req);
	}
	
	public void downClicked() {
		Log.d(TAG, "downClicked()");
		JSONRPCRequestTask task = new JSONRPCRequestTask();
		JSONRPC2Request req = new JSONRPC2Request("Input.Down", 0);
		task.execute(req);
	}
	

}
