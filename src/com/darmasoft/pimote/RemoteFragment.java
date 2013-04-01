package com.darmasoft.pimote;

import java.util.HashMap;
import java.util.Map;

import com.darmasoft.pimote.R;
import com.darmasoft.pimote.Requests.InputBackRequest;
import com.darmasoft.pimote.Requests.InputContextMenuRequest;
import com.darmasoft.pimote.Requests.InputHomeRequest;
import com.darmasoft.pimote.Requests.InputShowOSDRequest;
import com.darmasoft.pimote.Requests.PlayerPlayPauseRequest;
import com.darmasoft.pimote.Requests.PlayerStopRequest;
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
import android.widget.LinearLayout;
import android.widget.TextView;

public class RemoteFragment extends Fragment implements JSONRPCStatusListener, ActivePlayerListener {

	private static final String TAG = "pimote:RemoteFragment";
	
	private TextView _tv_status = null;
	private LinearLayout _layout_play_controls = null;
	private ImageButton _btn_play = null;
	
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
		_layout_play_controls = (LinearLayout)v.findViewById(R.id.fragment_remote_play_controls);
		_btn_play = (ImageButton)v.findViewById(R.id.fragment_remote_play_controls_play);
		
		ImageButton b = null;
		
		b = (ImageButton)v.findViewById(R.id.fragment_remote_play_controls_stop);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				stopClicked();
			}
		});

		b = (ImageButton)v.findViewById(R.id.fragment_remote_play_controls_play);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playPauseClicked();
			}
		});

		ImageButton ibtn = null;
		ibtn = (ImageButton)v.findViewById(R.id.fragment_remote_back);
		ibtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				backClicked();
			}
		});
		ibtn.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				backLongPressed();
				return(true);
			}
		});
		
		ibtn = (ImageButton)v.findViewById(R.id.fragment_remote_osd);
		ibtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				osdClicked();
			}
		});
		
		ibtn = (ImageButton)v.findViewById(R.id.fragment_remote_menu);
		ibtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				menuClicked();
			}
		});
		
		activePlayerChanged(false);
		return(v);
	}

	@Override
	public void onPause() {
		Log.d(TAG, "onPause()");
		PimoteApplication.get_app().set_status_listener(null);
		PimoteApplication.get_app().set_active_player_listener(null);
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.d(TAG, "onResume()");
		PimoteApplication.get_app().set_status_listener(this);
		PimoteApplication.get_app().set_active_player_listener(this);
		set_status_textview(PimoteApplication.get_app().host_status(), PimoteApplication.get_app().is_playing_video());
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
		set_status_textview(status, false);
	}

	@Override
	public void videoPlayersChanged(boolean status) {
		Log.d(TAG, "videoPlayersChanged(" + status + ")");
		set_status_textview(PimoteApplication.get_app().host_status(), status);
	}

	private void set_status_textview(boolean status, boolean playing) {
		if (status) {
			if (playing) {
				_tv_status.setText("Playing");
				_tv_status.setBackgroundColor(0xff00ffff);
			} else {
				_tv_status.setText("Connected");
				_tv_status.setBackgroundColor(0xff00ff00);
			}
		} else {
			_tv_status.setText("Disconnected");
			_tv_status.setBackgroundColor(0xffff0000);
		}
	}
	
	public void backClicked() {
		Log.d(TAG, "backClicked()");
		PimoteApplication.get_app().json_rpc_server().queue_request(new InputBackRequest());
	}
	
	public void backLongPressed() {
		Log.d(TAG, "backLongPressed()");
		PimoteApplication.get_app().json_rpc_server().queue_request(new InputHomeRequest());
	}

	public void menuClicked() {
		Log.d(TAG, "menuClicked()");
		PimoteApplication.get_app().json_rpc_server().queue_request(new InputContextMenuRequest());
	}
	
	public void osdClicked() {
		Log.d(TAG, "osdClicked()");
		PimoteApplication.get_app().json_rpc_server().queue_request(new InputShowOSDRequest());
	}
	
	public void playPauseClicked() {
		Log.d(TAG, "playPauseClicked()");
		PimoteApplication.get_app().json_rpc_server().queue_request(new PlayerPlayPauseRequest());
	}
	
	public void stopClicked() {
		Log.d(TAG, "stopClicked()");
		PimoteApplication.get_app().json_rpc_server().queue_request(new PlayerStopRequest());
	}

	@Override
	public void activePlayerChanged(boolean status) {
		Log.d(TAG, "activePlayerChanged(" + status + ")");
		if (status) {
			_btn_play.setBackgroundResource(R.drawable.pause);
			_layout_play_controls.setVisibility(View.VISIBLE);
		} else {
			_layout_play_controls.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void pauseChanged(boolean paused) {
		if (paused) {
			_btn_play.setBackgroundResource(R.drawable.play);
			_tv_status.setText("Paused");
			_tv_status.setBackgroundColor(0xff00ffff);
		} else {
			_btn_play.setBackgroundResource(R.drawable.pause);
			_tv_status.setText("Playing");
			_tv_status.setBackgroundColor(0xff00ffff);
		}
	}
}
