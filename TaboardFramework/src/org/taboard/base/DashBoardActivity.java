package org.taboard.base;

import org.taboard.base.R;
import org.taboard.config.RefreshableFragment;
import org.taboard.config.SourceConfig;
import org.taboard.config.ViewConfig;
import org.taboard.config.ViewConfigStore;
import org.taboard.filter.FilterableFragment;
import org.taboard.filter.FilterableSource;
import org.taboard.view.AboutDialogFragment;
import org.taboard.view.GridLayout;
import org.taboard.view.SourceChooserDialogFragment;

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

public abstract class DashBoardActivity extends Activity implements SourceManager {

	private final Handler mHandler = new Handler();
	
	private ViewConfig mViewConfig;


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

		ViewConfig vc = ViewConfigStore.readViewConfig(this);
		if (vc == null){
			vc = loadDefaultViewConfig();
		}
		setViewConfig(vc);
		
		
		addFragments();
	}

	public void addFragment(SourceConfig sc){
		FragmentManager fm = getFragmentManager();
		FragmentTransaction t = fm.beginTransaction();
		addFragment(t, sc);
		t.commit();
	}
	private void addFragments() {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction t = fm.beginTransaction();
		for (SourceConfig sc : mViewConfig.configs) {
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
						DashBoardActivity.this.doRefresh();
						refresh.setActionView(null);
					}
				}, 1000);
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem item = null;
		if (mViewConfig.numCols == 2) {
			if (mViewConfig.numRows == 2){
				item  = menu.findItem(R.id.menu_layout_2x2);
			} else if (mViewConfig.numRows == 3){
				item = menu.findItem(R.id.menu_layout_2x3);
			}
		} else if (mViewConfig.numCols == 3){
			if (mViewConfig.numRows == 2){
				item = menu.findItem(R.id.menu_layout_3x2);
			} else if (mViewConfig.numRows == 3){
				item = menu.findItem(R.id.menu_layout_3x3);
			}			
		}
		
		if (item != null){
			item.setChecked(true);
		} 
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			doFilter(FilterableSource.class, null, null);
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
			setLayout(2, 3);
			item.setChecked(true);
			return true;
		case R.id.menu_layout_3x3:
			setLayout(3, 3);
			item.setChecked(true);
			return true;
			
		case R.id.menu_load_vc:
			ViewConfig vc = ViewConfigStore.readViewConfig(this);
			if (vc == null){
				vc = loadDefaultViewConfig();
			}
			setViewConfig(vc);
			
			return true;
		case R.id.menu_store_vc:
			
			ViewConfigStore.storeViewConfig(getViewConfig(), this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setViewConfig(ViewConfig vc) {
		mViewConfig = vc;						
	}

	private ViewConfig getViewConfig() {
		return mViewConfig;
	}
	

	public abstract ViewConfig loadDefaultViewConfig();

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

	public void doFilter(Class<? extends FilterableSource> filterableClass,
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

		// change the HomeAsUp icon accordingly
		getActionBar().setDisplayHomeAsUpEnabled(filter != null);
		
		for (SourceConfig sc : mViewConfig.configs) {
			// only update the filters that can handle this filter
			if (filterableClass.isAssignableFrom(sc.getClass())) {

				// step 1: remember the current filter
				((FilterableSource) sc).setCurrentFilter(filter);

				// step 2: let the fragment know that the filter has been
				// changed
				FilterableFragment f = (FilterableFragment) fm
						.findFragmentByTag(sc.getTag());
				f.onFilterChanged();

			}
		}
		t.commit();

	}

	protected void doRefresh() {
		FragmentManager fm = getFragmentManager();
		for (SourceConfig sc : mViewConfig.configs) {			
			Fragment f = fm.findFragmentByTag(sc.getTag());
			if (f instanceof RefreshableFragment){
				((RefreshableFragment) f).doRefresh();
			}
		}
		
	}

	
	public void deleteSource(SourceConfig sc) {
		FragmentManager fm = getFragmentManager();
		Fragment f = fm.findFragmentByTag(sc.getTag());
		FragmentTransaction t = fm.beginTransaction();
		t.remove(f);
		t.commit();
	}

	public void addSource(int arrayIndex) {
		FragmentTransaction t = getFragmentManager().beginTransaction();
		SourceConfig sc;
		try {
			sc = getSourceConfigsClasses()[arrayIndex].newInstance();
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
	
	
	public abstract Class<SourceConfig<? extends Fragment>>[] getSourceConfigsClasses();
	
	public abstract int getArrayLabelSourceResourceId();
	
}