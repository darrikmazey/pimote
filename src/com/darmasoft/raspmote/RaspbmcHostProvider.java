package com.darmasoft.raspmote;

import java.util.List;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class RaspbmcHostProvider extends ContentProvider {
	private DB _d = null;

	private static final String TAG = "raspmote:RaspbmcHostProvider";
	
	private static UriMatcher _uri_matcher;
	private static final String _collection_path = "raspbmc_hosts";
	private static final int _collection_token = 1;
	private static final String _member_path = "raspbmc_hosts/#";
	private static final int _member_token = 2;
	private static final String _authority = "com.darmasoft.raspmote.raspbmc_host_provider";
	private static final Uri _base_uri = Uri.parse("content://" + _authority);
	public static final Uri CONTENT_URI = _base_uri.buildUpon().appendPath(_collection_path).build();

	public RaspbmcHostProvider() {
		_uri_matcher = new UriMatcher(UriMatcher.NO_MATCH);
		_uri_matcher.addURI(_authority, _collection_path, _collection_token);
		_uri_matcher.addURI(_authority, _member_path, _member_token);
	}

	public static int get_id_from_uri(Uri uri)
	{
		Log.d(TAG, "get_id_from_uri(" + uri.toString() + ")");
		List<String> segs = uri.getPathSegments();
		int id = Integer.valueOf(segs.get(segs.size() - 1));
		return(id);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		switch(_uri_matcher.match(uri)) {
			case _collection_token:
				return 0;
			case _member_token:
				Log.d(TAG, "delete(" + uri.toString() + ")");
				int id = RaspbmcHostProvider.get_id_from_uri(uri);
				SQLiteDatabase db = _d.getWritableDatabase();
				int rows = db.delete(RaspbmcHost.table_name(), "_id = ?", new String[] { Integer.toString(id) });
				getContext().getContentResolver().notifyChange(uri, null);
				return(rows);
		}
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		switch(_uri_matcher.match(uri)) {
			case _collection_token:
				return("vnd.android.cursor.dir/vnd.com.darmasoft.raspmote.raspbmc_host_provider.hosts");
			case _member_token:
				return("vnd.android.cursor.item/vnd.com.darmasoft.raspmote.raspbmc_host_provider.hosts");
			default:
				throw new UnsupportedOperationException("URI: " + uri + " not supported.");
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.d(TAG,  "insert()");
		switch(_uri_matcher.match(uri)) {
			case _collection_token:
				SQLiteDatabase db = _d.getWritableDatabase();
				long id = db.insert(RaspbmcHost.table_name(), null, values);
				Log.d(TAG,  "notifying: " + uri.toString());
				getContext().getContentResolver().notifyChange(uri, null);
				return(CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build());
			default:
				throw new UnsupportedOperationException("URI: " + uri + " not supported.");
		}
	}

	@Override
	public boolean onCreate() {
		_d = new DB(getContext());
		return(true);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.d(TAG,  "query()");
		SQLiteDatabase db = _d.getReadableDatabase();
		switch(_uri_matcher.match(uri)) {
			case _collection_token:
				Log.d(TAG, "querying " + RaspbmcHost.table_name());
				Cursor cursor = db.query(RaspbmcHost.table_name(), null, null, null, null, null, null);
				cursor.setNotificationUri(getContext().getContentResolver(), uri);
				return(cursor);
			case _member_token:
			default:
				return null;
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
