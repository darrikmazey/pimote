package com.darmasoft.pimote;

import com.darmasoft.pimote.Requests.InputDownRequest;
import com.darmasoft.pimote.Requests.InputLeftRequest;
import com.darmasoft.pimote.Requests.InputRightRequest;
import com.darmasoft.pimote.Requests.InputSelectRequest;
import com.darmasoft.pimote.Requests.InputUpRequest;
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
		PimoteApplication.get_app().json_rpc_server().queue_request(new InputLeftRequest());
	}
	
	public void rightClicked() {
		Log.d(TAG, "rightClicked()");
		PimoteApplication.get_app().json_rpc_server().queue_request(new InputRightRequest());
	}
	
	public void selectClicked() {
		Log.d(TAG, "selectClicked()");
		PimoteApplication.get_app().json_rpc_server().queue_request(new InputSelectRequest());
	}
	
	public void upClicked() {
		Log.d(TAG, "upClicked()");
		PimoteApplication.get_app().json_rpc_server().queue_request(new InputUpRequest());
	}
	
	public void downClicked() {
		Log.d(TAG, "downClicked()");
		PimoteApplication.get_app().json_rpc_server().queue_request(new InputDownRequest());
	}
	

}
