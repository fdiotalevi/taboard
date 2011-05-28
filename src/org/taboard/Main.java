package org.taboard;

import java.util.ArrayList;
import java.util.List;

import org.taboard.config.SourceConfig;
import org.taboard.config.UrlSourceConfig;
import org.taboard.filter.Filterable;
import org.taboard.filter.FilterableFragment;
import org.taboard.source.charts.ChartsSourceConfig;
import org.taboard.source.contacts.ContactsSourceConfig;
import org.taboard.source.git.GitCommitsFragment;
import org.taboard.source.git.GitSourceConfig;
import org.taboard.source.google.GoogleSourceConfig;
import org.taboard.source.googlecode.GoogleCodeIssueSourceConfig;
import org.taboard.view.GridLayout;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class Main extends Activity implements SourceManager {

	List<SourceConfig> mSources;

	private String URL1 = "http://github.com/api/v2/json/commits/list/fdiotalevi/taboard/master";
	private String URL2 = "http://github.com/api/v2/json/commits/list/openintents/aainterfaces/master";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mSources = new ArrayList<SourceConfig>();
		mSources.add(new GitSourceConfig(URL1, "Taboard"));
		mSources.add(new GitSourceConfig(URL2, "Open Android Apps"));
		mSources.add(new GoogleCodeIssueSourceConfig(
				"http://code.google.com/p/openintents/issues/csv", "OpenIntent"));
		mSources.add(new ContactsSourceConfig(null));
        mSources.add(new GoogleSourceConfig(null));
        mSources.add(new ChartsSourceConfig(
        		"TestChart",
        		new String[] { "Build Types", "number of builds per type" },
        		new String[] { "Successful Builds", "Failed Builds", "Cocktail Builds" },
        		new double[] { 300d, 100d, 200d } ));

		FragmentManager fm = getFragmentManager();
		FragmentTransaction t = fm.beginTransaction();
		for (SourceConfig sc : mSources) {
			Fragment fragment = sc.createFragment(this);
			t.add(R.id.taboard, fragment, sc.getTag());

		}
		t.commit();
	}

	public void doFilter(Class<? extends Filterable> filterableClass,
			Bundle filter) {
		FragmentManager fm = getFragmentManager();

		for (SourceConfig sc : mSources) {
			if (filterableClass.isAssignableFrom(sc.getClass())) {

				// step 1: remember the current filter
				((Filterable) sc).setCurrentFilter(filter);

				// step 2: let the fragment know that the filter has been changed
				FilterableFragment f = (FilterableFragment) fm
						.findFragmentByTag(sc.getTag());
				f.onFilterChanged();

			}
		}
	}
}