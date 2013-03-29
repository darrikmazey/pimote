package com.darmasoft.raspmote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {

	private static final String TAG = "raspmote:DB";
	
	private static final String DATABASE_NAME = "raspmote_db";
	private static final int DATABASE_VERSION = 8;
	
	public DB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.d(TAG, "DB()");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate()");
		RaspbmcHost.create_table(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade()");
		RaspbmcHost.drop_table(db);
		
		onCreate(db);
	}

}
