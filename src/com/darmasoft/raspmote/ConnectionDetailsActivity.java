package com.darmasoft.raspmote;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConnectionDetailsActivity extends Activity {

	private static final String TAG = "raspmote:ConnectionDetailsActivity";
	
	private int _host_id = -1;
	private RaspbmcHost _host = null;
	private TextView _tv_name = null;
	private TextView _tv_host = null;
	private TextView _tv_port = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG,  "onCreate()");
		Bundle extras = getIntent().getExtras();
		_host_id = extras.getInt("id");
		Log.d(TAG, "RaspbmcHost id: " + _host_id);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_connection_details);
		
		if (_host_id > 0) {
			_host = RaspbmcHost.find_by_id(_host_id);
		} else {
			_host = new RaspbmcHost("", "", 80, 0, new Date(), new Date(), true);
		}
		
		_tv_name = (TextView)findViewById(R.id.fragment_details_name);
		_tv_name.setText(_host.name());
		_tv_host = (TextView)findViewById(R.id.fragment_details_host);
		_tv_host.setText(_host.host());
		_tv_port = (TextView)findViewById(R.id.fragment_details_port);
		_tv_port.setText(Integer.toString(_host.port()));
		
		Button save = (Button)findViewById(R.id.fragment_details_save);
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG, "save clicked");
				_host.set_host(_tv_host.getText().toString());
				_host.set_name(_tv_name.getText().toString());
				_host.set_port(Integer.valueOf(_tv_port.getText().toString()));
				_host.save();
				finish();
			}
		});
		
		Button cancel = (Button)findViewById(R.id.fragment_details_cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG, "cancel clicked");
				finish();
			}
		});
		
		
	}

	public ConnectionDetailsActivity() {
		// TODO Auto-generated constructor stub
	}

}
