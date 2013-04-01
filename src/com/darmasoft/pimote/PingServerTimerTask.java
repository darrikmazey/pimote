package com.darmasoft.pimote;

import java.util.TimerTask;

import android.os.Looper;

public class PingServerTimerTask extends TimerTask {

	private static final String TAG = "pimote:PingServerTimerTask";
	
	public PingServerTimerTask() {
	}

	@Override
	public void run() {
		Log.d(TAG, "run()");
	}

}
