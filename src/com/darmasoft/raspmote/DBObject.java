package com.darmasoft.raspmote;

import java.util.Date;

import android.database.sqlite.SQLiteDatabase;

public class DBObject extends Object {
	
	private static final String TAG = "raspmote:DBObject";
	protected static String _table_name = null;
	protected static String _create_table_sql = null;

	// fields
	protected int _id;
	protected Date _created_at;
	protected Date _updated_at;
	
	protected boolean _new_record = true;
	protected boolean _dirty = false;
	
	public DBObject() {
	}

	public DBObject(int id, Date created_at, Date updated_at) {
		_id = id;
		_created_at = created_at;
		_updated_at = updated_at;
	}
	
	public static String table_name() {
		return(_table_name);
	}
	
	public String get_table_name() {
		return(_table_name);
	}
	
	public boolean is_dirty() {
		return(_dirty);
	}
	
	public boolean is_new_record() {
		return(_new_record);
	}
	
	public int id() {
		return(_id);
	}
	
	public void set_id(int id) {
		_id = id;
	}
	
	public boolean insert() {
		Log.e(TAG,  "MUST OVERRIDE DBObject.insert()");
		return(false);
	}
	
	public boolean save() {
		Log.e(TAG,  "MUST OVERRIDE DBObject.save()");
		return(false);
	}
	
	public boolean destroy() {
		Log.e(TAG,  "MUST OVERRIDE DBObject.destroy()");
		return(false);
	}
	
	static public DBObject find_by_id(int id) {
		return(null);
	}
	
	static public boolean create_table(SQLiteDatabase db) {
		Log.e(TAG,  "MUST OVERRIDE DBObject.create_table()");
		return(false);
	}
	
	static public boolean drop_table(SQLiteDatabase db) {
		Log.d(TAG,  "drop_table(" + _table_name + ")");
		db.execSQL("DROP TABLE IF EXISTS " + _table_name);
		return(true);
	}
}
