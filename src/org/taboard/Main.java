package org.taboard;

import java.util.ArrayList;
import java.util.List;

import org.taboard.config.SourceConfig;
import org.taboard.filter.Filterable;
import org.taboard.filter.FilterableFragment;
import org.taboard.source.charts.ChartsSourceConfig;
import org.taboard.source.contacts.ContactsSourceConfig;
import org.taboard.source.git.GitSourceConfig;
import org.taboard.source.google.GoogleSourceConfig;
import org.taboard.source.googlecode.GoogleCodeIssueSourceConfig;
import org.taboard.source.jenkins.JenkinsFeedSourceConfig;
import org.taboard.view.AboutDialogFragment;
import org.taboard.view.GridLayout;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;

public class Main extends Activity implements SourceManager {

	
	// also check arrays.xml for labels
	private static final Class[] SOURCE_CONFIGS = new Class[]{GitSourceConfig.class, //
		GoogleCodeIssueSourceConfig.class, //
		ContactsSourceConfig.class, //
		ChartsSourceConfig.class, GoogleSourceConfig.class};	

	private final Handler mHandler = new Handler();

	List<SourceConfig> mSources;

	private String URL1 = "http://github.com/api/v2/json/commits/list/fdiotalevi/taboard/master";
	private String URL2 = "http://github.com/api/v2/json/commits/list/openintents/aainterfaces/master";

	private View mActionBarView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mActionBarView = getLayoutInflater().inflate(
				R.layout.action_bar_custom, null);
		ActionBar bar = getActionBar();

		// bar.setCustomView(mActionBarView);
		bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_USE_LOGO);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayShowTitleEnabled(true);
		bar.setDisplayShowHomeEnabled(true);

		mSources = new ArrayList<SourceConfig>();
		mSources.add(new GitSourceConfig(URL1, "Taboard"));
		mSources.add(new GitSourceConfig(URL2, "Open Android Apps"));
		mSources.add(new GoogleCodeIssueSourceConfig("http://code.google.com/p/openintents/issues/csv", "OpenIntent"));
		mSources.add(new JenkinsFeedSourceConfig("http://ci.jenkins-ci.org/rssLatest", "Jenkins CI"));
		
		mSources.add(new ContactsSourceConfig(null));
		mSources.add(new GoogleSourceConfig(null));
		mSources.add(new ChartsSourceConfig("TestChart", new String[] {
				"Build Types", "number of builds per type" }, new String[] {
				"Successful Builds", "Failed Builds", "Cocktail Builds" },
				new double[] { 300d, 100d, 200d }));

		addFragments();
	}

	private void addFragments() {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction t = fm.beginTransaction();
		for (SourceConfig sc : mSources) {
			addFragment(t, sc);

		}
		t.commit();
	}

	private void addFragment(FragmentTransaction t, SourceConfig sc) {
		Fragment fragment = sc.createFragment(this, this);
		t.add(R.id.taboard, fragment, sc.getTag());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);

		// set up a listener for the refresh item
		final MenuItem refresh = (MenuItem) menu.findItem(R.id.menu_refresh);
		refresh.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			// on selecting show progress spinner for 1s
			public boolean onMenuItemClick(MenuItem item) {

				mHandler.postDelayed(new Runnable() {
					public void run() {
						refresh.setActionView(null);
					}
				}, 1000);
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			doFilter(Filterable.class, null, null);
			return true;
		case R.id.menu_refresh:
			// switch to a progress animation
			item.setActionView(R.layout.indeterminate_progress_action);

			return true;
		case R.id.menu_add_source:
			showSourceChooser();
			return true;

		case R.id.menu_about:

			String versionName = null;
			try {
				versionName = getPackageManager().getPackageInfo(
						getPackageName(), 0).versionName;
			} catch (NameNotFoundException e) {
				// that will never happen
				e.printStackTrace();
			}

			showDialog("Taboard for developer managers\n Version "
					+ versionName);
			return true;

		case R.id.menu_layout_2x2:
			item.setChecked(true);
			setLayout(2, 2);
			return true;
		case R.id.menu_layout_3x2:
			setLayout(3, 2);
			item.setChecked(true);
			return true;
		case R.id.menu_layout_2x3:
			setLayout( 2, 3);
			item.setChecked(true);
			return true;
		case R.id.menu_layout_3x3:
			setLayout(3, 3);
			item.setChecked(true);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setLayout(int rows, int columns) {
		GridLayout gl = (GridLayout) findViewById(R.id.taboard);
		gl.setNumRowsCols(rows, columns);
		gl.removeAllViews();
		addFragments();

	}

	void showDialog(String text) {
		// DialogFragment.show() will take care of adding the fragment
		// in a transaction. We also want to remove any currently showing
		// dialog, so make our own transaction and take care of that here.
		FragmentTransaction ft = getFragmentManager().beginTransaction();

		DialogFragment newFragment = AboutDialogFragment.newInstance(text);

		// Show the dialog.
		newFragment.show(ft, "dialog");
	}
	
	
	void showSourceChooser() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();

		DialogFragment newFragment = new SourceChooserDialogFragment();
		
		// Show the dialog.
		newFragment.show(ft, "sourcechooserdialog");
		
	}

	public void doFilter(Class<? extends Filterable> filterableClass,
			Bundle filter, String description) {
		if (filter != null) {
			getActionBar().setSubtitle("Filtered by " + description);
		} else {
			getActionBar().setSubtitle(null);
		}
		FragmentManager fm = getFragmentManager();

		FragmentTransaction t = fm.beginTransaction();
		t.setBreadCrumbShortTitle("Something");
		t.setBreadCrumbTitle("Something");

		getActionBar().setDisplayHomeAsUpEnabled(filter != null);
		for (SourceConfig sc : mSources) {
			if (filterableClass.isAssignableFrom(sc.getClass())) {

				// step 1: remember the current filter
				((Filterable) sc).setCurrentFilter(filter);

				// step 2: let the fragment know that the filter has been
				// changed
				FilterableFragment f = (FilterableFragment) fm
						.findFragmentByTag(sc.getTag());
				f.onFilterChanged();

			}
		}
		t.commit();

	}

	public void addSource(int arrayIndex) {
		FragmentTransaction t = getFragmentManager().beginTransaction();
		SourceConfig sc;
		try {
			sc = (SourceConfig) SOURCE_CONFIGS[arrayIndex].newInstance();
			addFragment(t, sc);
			t.commit();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}