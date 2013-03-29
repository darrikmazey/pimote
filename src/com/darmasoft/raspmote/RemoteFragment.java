package com.darmasoft.raspmote;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RemoteFragment extends Fragment implements JSONRPCStatusListener {

	private static final String TAG = "raspmote:RemoteFragment";
	
	private TextView _tv_status = null;
	
	public RemoteFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_remote, null);
		_tv_status = (TextView)v.findViewById(R.id.fragment_remote_status);
		
		Button b = null;
		b = (Button)v.findViewById(R.id.fragment_remote_select);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectClicked();
			}
		});
		
		b = (Button)v.findViewById(R.id.fragment_remote_up);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				upClicked();
			}
		});
		
		b = (Button)v.findViewById(R.id.fragment_remote_down);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				downClicked();
			}
		});
		
		b = (Button)v.findViewById(R.id.fragment_remote_left);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				leftClicked();
			}
		});
		
		b = (Button)v.findViewById(R.id.fragment_remote_right);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rightClicked();
			}
		});
		
		b = (Button)v.findViewById(R.id.fragment_remote_back);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				backClicked();
			}
		});
		b.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				backLongPressed();
				return(true);
			}
		});
		
		b = (Button)v.findViewById(R.id.fragment_remote_osd);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				osdClicked();
			}
		});
		
		b = (Button)v.findViewById(R.id.fragment_remote_menu);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				menuClicked();
			}
		});
		
		return(v);
	}

	@Override
	public void onPause() {
		Log.d(TAG, "onPause()");
		RaspmoteApplication.get_app().set_status_listener(null);
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.d(TAG, "onResume()");
		RaspmoteApplication.get_app().set_status_listener(this);
		set_status_textview(RaspmoteApplication.get_app().host_status());
		super.onResume();
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d(TAG, "onAttach()");
		super.onAttach(activity);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		Log.d(TAG, "onHiddenChanged(" + hidden + ")");
		super.onHiddenChanged(hidden);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		Log.d(TAG, "setUserVisibleHint(" + isVisibleToUser + ")");
		/*
		if (isVisibleToUser) {
			RaspmoteApplication.get_app().set_status_listener(this);
			set_status_textview(RaspmoteApplication.get_app().host_status());
		} else {
			RaspmoteApplication.get_app().set_status_listener(null);
		}
		*/
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void statusChanged(boolean status) {
		Log.d(TAG, "statusChanged(" + status + ")");
		set_status_textview(status);
	}

	private void set_status_textview(boolean status) {
		if (status) {
			_tv_status.setText("Connected");
			_tv_status.setBackgroundColor(0xff00ff00);
		} else {
			_tv_status.setText("Disconnected");
			_tv_status.setBackgroundColor(0xffff0000);
		}
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
	
	public void backClicked() {
		Log.d(TAG, "backClicked()");
		JSONRPCRequestTask task = new JSONRPCRequestTask();
		JSONRPC2Request req = new JSONRPC2Request("Input.Back", 0);
		task.execute(req);
	}
	
	public void backLongPressed() {
		Log.d(TAG, "backLongPressed()");
		JSONRPCRequestTask task = new JSONRPCRequestTask();
		JSONRPC2Request req = new JSONRPC2Request("Input.Home", 0);
		task.execute(req);
	}

	public void menuClicked() {
		Log.d(TAG, "menuClicked()");
		JSONRPCRequestTask task = new JSONRPCRequestTask();
		JSONRPC2Request req = new JSONRPC2Request("Input.ContextMenu", 0);
		task.execute(req);
	}
	
	public void osdClicked() {
		Log.d(TAG, "osdClicked()");
		JSONRPCRequestTask task = new JSONRPCRequestTask();
		JSONRPC2Request req = new JSONRPC2Request("Input.ShowOSD", 0);
		task.execute(req);
	}
	
}
