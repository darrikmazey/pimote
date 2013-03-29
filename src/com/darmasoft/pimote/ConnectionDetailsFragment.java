package com.darmasoft.pimote;

import com.darmasoft.pimote.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ConnectionDetailsFragment extends Fragment {

	private static final String TAG = "pimote:ConnectionDetailsFragment";
	
	private RaspbmcHost _host = null;
	
	public ConnectionDetailsFragment(RaspbmcHost host) {
		Log.d(TAG,  "ConnectionDetailsFragment()");
		_host = host;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate()");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView()");
		View v = inflater.inflate(R.layout.fragment_connection_details, container, false);
		return(v);
	}

}
