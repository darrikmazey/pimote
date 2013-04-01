package com.darmasoft.pimote;

import java.util.HashMap;
import java.util.Map;

import com.darmasoft.pimote.R;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class RemoteFragment extends Fragment implements JSONRPCStatusListener {

	private static final String TAG = "pimote:RemoteFragment";
	
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
		
		ImageButton b = null;
		
		b = (ImageButton)v.findViewById(R.id.fragment_remote_play_controls_play);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playPauseClicked();
			}
		});

		
		Button btn = null;
		btn = (Button)v.findViewById(R.id.fragment_remote_back);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				backClicked();
			}
		});
		btn.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				backLongPressed();
				return(true);
			}
		});
		
		btn = (Button)v.findViewById(R.id.fragment_remote_osd);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				osdClicked();
			}
		});
		
		btn = (Button)v.findViewById(R.id.fragment_remote_menu);
		btn.setOnClickListener(new View.OnClickListener() {
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
		PimoteApplication.get_app().set_status_listener(null);
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.d(TAG, "onResume()");
		PimoteApplication.get_app().set_status_listener(this);
		set_status_textview(PimoteApplication.get_app().host_status());
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
			pimoteApplication.get_app().set_status_listener(this);
			set_status_textview(pimoteApplication.get_app().host_status());
		} else {
			PimoteApplication.get_app().set_status_listener(null);
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
	
	public void playPauseClicked() {
		Log.d(TAG, "playPauseClicked()");
		JSONRPCRequestTask task = new JSONRPCRequestTask();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("playerid", 1);
		JSONRPC2Request req = new JSONRPC2Request("Player.PlayPause", params, 0);
		task.execute(req);
	}
}
