package com.darmasoft.pimote;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class RaspbmcHost extends DBObject {

	private static final String TAG = "pimote:RaspbmcHost";
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

	public static RaspbmcHost from_cursor(Cursor c) {
		if (c.moveToFirst()) {
			int id = c.getInt(c.getColumnIndex("_id"));
			String name = c.getString(c.getColumnIndex("name"));
			String host = c.getString(c.getColumnIndex("host"));
			int port = c.getInt(c.getColumnIndex("port"));
			Date ca = new Date();
			Date ua = ca;
			
			try {
				ca = DateHelper.sqlStringToDate(c.getString(c.getColumnIndex("created_at")));
				ua = DateHelper.sqlStringToDate(c.getString(c.getColumnIndex("updated_at")));
			} catch (ParseException e) {
			}
			
			return(new RaspbmcHost(name, host, port, id, ca, ua, false));
		}
		return(null);
	}
		
	public RaspbmcHost(String name, String host, int port, int id, Date created_at, Date updated_at, boolean new_record) {
		super(id, created_at, updated_at);
		_name = name;
		_host = host;
		_port = port;
		_new_record = new_record;
		_dirty = new_record;
	}
	
	public Uri server_uri()
	{
		Uri uri;
		if (_port == 80) {
			uri = Uri.parse("http://" + _host + "/jsonrpc");
		} else {
			uri = Uri.parse("http://" + _host + ":" + _port + "/jsonrpc");
		}
		return(uri);
	}

	public URL server_url()	{
		URL url;
		try {
			if (_port == 80) {
				url = new URL("http://" + _host + "/jsonrpc");
			} else {
				url = new URL("http://" + _host + ":" + _port + "/jsonrpc");
			}
		} catch (MalformedURLException e) {
			return(null);
		}
		return(url);
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
	
	public static String table_name() {
		return _table_name;
	}
	
	public boolean destroy() {
		
		int rows = PimoteApplication.get_context().getContentResolver().delete(RaspbmcHostProvider.CONTENT_URI.buildUpon().appendPath(Integer.toString(_id)).build(), null, null);
		if (rows == 1) {
			PimoteApplication.host_deleted(_id);
			return(true);
		} else {
			return(false);
		}
	}
	
	public boolean insert() {
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
   		
   		Uri uri = PimoteApplication.get_context().getContentResolver().insert(RaspbmcHostProvider.CONTENT_URI, values);
   		_id = RaspbmcHostProvider.get_id_from_uri(uri);

   		return(true);
	}
	
	public boolean save() {
		Log.d(TAG, "save(" + _id + ")");
		if (!_dirty) {
			Log.d(TAG, "not dirty: aborting");
			return(false);
		}
		if (_new_record) {
			return(insert());
		}
		
		ContentValues values = new ContentValues();
		values.clear();
		
		Date now = new Date();
		String updated_at_str = DateHelper.dateToSqlString(now);
		values.put("updated_at", updated_at_str);
		values.put("name", _name);
		values.put("host", _host);
		values.put("port", _port);

		Uri uri = RaspbmcHostProvider.CONTENT_URI.buildUpon().appendPath(Integer.toString(_id)).build();
		int rows = PimoteApplication.get_context().getContentResolver().update(uri, values, null, null);
		if (rows == 1) {
			_dirty = false;
			_new_record = false;
			return(true);
		} else {
			return(false);
		}
	}
	
	// selects
	public static RaspbmcHost find_by_id(int id) {
		if (id > 0) {
			Uri uri = RaspbmcHostProvider.CONTENT_URI.buildUpon().appendPath(Integer.toString(id)).build();
			Cursor cursor = PimoteApplication.get_context().getContentResolver().query(uri, null, null, null, null);
			return(RaspbmcHost.from_cursor(cursor));
		}
		return(null);
	}

	public static int count() {
		Cursor c = RaspbmcHost.all();
		return(c.getCount());
	}
	
	public static Cursor all() {
		Log.d(TAG, "all()");
		Cursor cursor = PimoteApplication.get_context().getContentResolver().query(RaspbmcHostProvider.CONTENT_URI,  null, null, null, null);
		return(cursor);
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
