package com.darmasoft.raspmote;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class RaspmoteApplication extends Application {

	private static final String TAG = "raspmote:RaspmoteApplication";
	private static RaspmoteApplication _instance = null;
	
	private int _current_host_id = -1;
	private RaspbmcHost _current_host = null;
	
	public RaspmoteApplication() {
		_instance = this;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate()");
		
		Log.d(TAG,  "ensuring writable database is upgraded");
		DB d = new DB(this);
		SQLiteDatabase db = d.getWritableDatabase();
		db.close();
	}

	public static Context get_context() {
		return(_instance);
	}
	
	public RaspbmcHost current_host() {
		if (_current_host_id > 0) {
			if (_current_host == null) {
				_current_host = RaspbmcHost.find_by_id(_current_host_id);
			}
			return(_current_host);
		} else {
			return(null);
		}
	}
	public static void host_deleted(int id) {
		_instance.raspbmc_host_deleted(id);
	}
	
	public void raspbmc_host_deleted(int id) {
		if (_current_host_id == id) {
			_current_host_id = -1;
			_current_host = null;
		}
	}
	
	public int current_host_id() {
		return(_current_host_id);
	}
	
	public void set_current_host_id(int hid) {
		Log.d(TAG, "set_current_host_id(" + hid + ")");
		Log.d(TAG, "old current_host_id: " + _current_host_id);
		_current_host_id = hid;
	}
}
