package com.darmasoft.raspmote;

import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import com.darmasoft.raspmote.Log;

public class MainActivity extends FragmentActivity {

	private static final String TAG = "raspmote:MainActivity";
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			if (position == 0) {
				Fragment fragment = new ConnectionListFragment();
				Bundle args = new Bundle();
				fragment.setArguments(args);
				return(fragment);
			}
			return(null);
		}

		@Override
		public int getCount() {
			// Show 1 total page.
			return 1;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.connection_list_title).toUpperCase(l);
			}
			return null;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case R.id.item_debug:
    		Log.d(TAG, "debug item selected");
    		RaspbmcHost host = new RaspbmcHost("raspbmc", "192.168.248.198", 80, 0, new Date(), new Date());
    		if (host.insert()) {
    			Log.d(TAG,  "inserted host with id: "+ host.id());
    		} else {
    			Log.e(TAG, "failed to insert host");
    		}
    		break;
    	case R.id.action_settings:
    		Log.d(TAG, "settings selected");
    		break;
    	case R.id.item_connections:
    		Log.d(TAG,  "connections selected");
    		break;
    	}
    	return(true);
	}
	
}