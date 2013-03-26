package com.darmasoft.raspmote;

import java.text.ParseException;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

public class RaspbmcHost extends DBObject {

	private static final String TAG = "raspmote:RaspbmcHost";
	private static final String _table_name = "raspbmc_hosts";
	private static final String _create_table_sql = "create table " + _table_name + " ( " +
			"_id integer primary key, " +
			"name text, " +
			"host text, " +
			"port integer, " +
			"created_at text, " +
			"updated_at text" +
			")";
	
	// additional fields
	private String _name;
	private String _host;
	private int _port;
	
	public RaspbmcHost() {
	}

	public RaspbmcHost(String name, String host, int port, int id, Date created_at, Date updated_at) {
		super(id, created_at, updated_at);
		_name = name;
		_host = host;
		_port = port;
	}

	public String name() {
		return(_name);
	}
	
	public void set_name(String name) {
		_name = name;
		_dirty = true;
	}
	
	public String host() {
		return(_host);
	}
	
	public void set_host(String host) {
		_host = host;
		_dirty = true;
	}
	
	public int port() {
		return(_port);
	}

	public void set_port(int port) {
		_port = port;
		_dirty = true;
	}
	
	public boolean insert() {
		DB d = new DB(RaspmoteApplication.get_context());
		SQLiteDatabase db = d.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.clear();

		Date now = new Date();
		String updated_at_str = DateHelper.dateToSqlString(now);
		String created_at_str = DateHelper.dateToSqlString(now);
		values.put("name",  _name);
   		values.put("host", _host);
   		values.put("port", _port);
   		values.put("created_at", created_at_str);
   		values.put("updated_at", updated_at_str);
   		
   		try {
			_id = (int) db.insertOrThrow(_table_name, null, values);
		} catch (SQLiteConstraintException e) {
			db.close();
			return(false);
		}
   	
		db.close();

		return(true);
	}
	
	// selects
	public static RaspbmcHost find_by_id(int id) {
		if (id > 0) {
			String[] columns = new String[] { "_id", "name", "host", "port", "created_at", "updated_at" };
			String selection = "_id = ?";
			String[] selectionArgs = new String[] { Integer.toString(id) };

			DB d = new DB(RaspmoteApplication.get_context());
			SQLiteDatabase db = d.getReadableDatabase();
			Cursor c = db.query(_table_name, columns, selection, selectionArgs, null, null, null);
			if (c.getCount() > 0) {
				c.moveToFirst();
				String name = c.getString(c.getColumnIndex("name"));
				String host = c.getString(c.getColumnIndex("host"));
				int port = c.getInt(c.getColumnIndex("port"));
				Date created_at;
				Date updated_at;
				try {
					created_at = DateHelper.sqlStringToDate(c.getString(c.getColumnIndex("created_at")));
					updated_at = DateHelper.sqlStringToDate(c.getString(c.getColumnIndex("updated_at")));
				} catch (ParseException e) {
					created_at = new Date();
					updated_at = created_at;
				}
				db.close();
				return(new RaspbmcHost(name, host, port, id, created_at, updated_at));
			}
		}
		return(null);
	}

	public static Cursor all() {
		Log.d(TAG, "all()");
		DB d = new DB(RaspmoteApplication.get_context());
		SQLiteDatabase db = d.getReadableDatabase();
		
		String[] columns = new String[] { "_id", "name", "host", "port" };
		Cursor c = db.query(_table_name, columns, null, null, null, null, null);
		return(c);
	}
	
	static public boolean create_table(SQLiteDatabase db) {
		Log.d(TAG, "create_table()");
		db.execSQL(_create_table_sql);
		return(true);
	}
	
	static public boolean drop_table(SQLiteDatabase db) {
		Log.d(TAG,  "drop_table(" + _table_name + ")");
		db.execSQL("DROP TABLE IF EXISTS " + _table_name);
		return(true);
	}
}