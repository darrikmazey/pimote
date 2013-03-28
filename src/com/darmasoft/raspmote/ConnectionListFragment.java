package com.darmasoft.raspmote;

import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.support.v4.app.LoaderManager;

public class ConnectionListFragment extends Fragment
	implements OnItemClickListener, OnItemLongClickListener, LoaderManager.LoaderCallbacks<Cursor> {

	private static final String TAG = "raspmote:ConnectionListFrament";
	private SimpleCursorAdapter _adapter = null;
	
	private int _context_item_id = -1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onCreate()");
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.d(TAG,  "onDestroy()");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onDestroyView()");
		super.onDestroyView();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		Log.d(TAG,  "onPause()");
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		Log.d(TAG,  "onResume()");
		super.onResume();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		Log.d(TAG,  "onStart()");
		super.onStart();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onCreateView()");
		View rootView = inflater.inflate(R.layout.fragment_connection_list, container, false);

		String[] columns = new String[] { "name", "host", "port" };
		int[] to = new int[] {
				R.id.connection_list_item_name,
				R.id.connection_list_item_host, 
				R.id.connection_list_item_port };
		ListView lv = (ListView)rootView.findViewById(R.id.connection_list_fragment_listview);
		
		_adapter = new SimpleCursorAdapter(getActivity(), R.layout.connection_list_item, null, columns, to, 0);
		lv.setAdapter(_adapter);
		
		lv.setClickable(true);
		lv.setOnItemClickListener(this);
		lv.setLongClickable(true);
		lv.setOnItemLongClickListener(this);
		registerForContextMenu(lv);
		
		getLoaderManager().initLoader(0, null, this);
		return(rootView);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Log.d(TAG, "onContextItemSelected(" + _context_item_id + ")");
		switch(item.getItemId()) {
			case R.id.connection_list_item_context_delete:
				Log.d(TAG, "deleting item: " + _context_item_id);
				RaspbmcHost host = RaspbmcHost.find_by_id(_context_item_id);
				if (host != null) {
					host.destroy();
				}
				break;
			case R.id.connection_list_item_context_edit:
				Log.d(TAG,  "editing item: " + _context_item_id);
				Intent intent = new Intent();
				intent.setClass(getActivity(), ConnectionDetailsActivity.class);
				intent.putExtra("id", _context_item_id);
				startActivity(intent);
				break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		Log.d(TAG, "onCreateOptionsMenu()");
		menu.add(0,0,0,"New Connection");
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected(" + item.getItemId() + ")");
		boolean handled = true;
		switch(item.getItemId()) {
			case 0:
				Log.d(TAG, "New Connection");
				Intent intent = new Intent();
				intent.setClass(getActivity(), ConnectionDetailsActivity.class);
				intent.putExtra("id", 0);
				startActivity(intent);				
				break;
			default:
				handled = false;
				break;
		}
		return(handled);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		Log.d(TAG, "onCreateContextMenu(" + _context_item_id + ")");
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.connection_list_item_context_menu, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	public ConnectionListFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		((RaspmoteApplication)this.getActivity().getApplication()).set_current_host_id((int) id);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		Log.d(TAG, "onItemLongClick(" + id + ")");
		_context_item_id = (int)id;
		getActivity().openContextMenu(parent);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Log.d(TAG,  "onCreateLoader()");
		return(new CursorLoader(this.getActivity(), RaspbmcHostProvider.CONTENT_URI, null, null, null, null));
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		Log.d(TAG, "onLoadFinished()");
		_adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursor) {
		Log.d(TAG, "onLoaderReset()");
		_adapter.swapCursor(null);
	}

}
