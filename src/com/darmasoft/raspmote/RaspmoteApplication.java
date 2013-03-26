package com.darmasoft.raspmote;

import android.app.Application;

public class RaspmoteApplication extends Application {

	private static final String TAG = "raspmote:RaspmoteApplication";
	
	public RaspmoteApplication() {
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate()");
	}

}
