package com.darmasoft.raspmote;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ConnectionListFragment extends Fragment implements OnItemClickListener {

	private static final String TAG = "raspmote:ConnectionListFrament";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onCreate()");
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
		Cursor cursor = RaspbmcHost.all();
		String[] columns = new String[] { "name", "host", "port" };
		int[] to = new int[] {
				R.id.connection_list_item_name,
				R.id.connection_list_item_host, 
				R.id.connection_list_item_port };
		ListView lv = (ListView)rootView.findViewById(R.id.connection_list_fragment_listview);
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.connection_list_item, cursor, columns, to, 0);
		lv.setAdapter(adapter);
		
		lv.setClickable(true);
		lv.setOnItemClickListener(this);
		return(rootView);
	}

	public ConnectionListFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		((RaspmoteApplication)this.getActivity().getApplication()).set_current_host_id((int) id);
	}


}
