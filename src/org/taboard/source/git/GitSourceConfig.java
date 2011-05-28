package org.taboard.source.git;

import org.taboard.SourceManager;
import org.taboard.config.SourceConfig;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class GitSourceConfig implements SourceConfig<GitCommitsFragment>, ContactFilterable {
	private String mUrl;
	private String mName;
	private Bundle mCurrentFilter;


	public GitSourceConfig(String url, String name) {
		mUrl = url;
		mName = name;		
	}

	Class<? extends Fragment> getFragmentClass() {
		return GitCommitsFragment.class;
	}

	public GitCommitsFragment createFragment(Activity activity) {
		return new GitCommitsFragment(this, (SourceManager) activity);
	}

	public String getTag() {
		return "git" + mName;
	}

	public String getTitle() {
		return "git changes: " + mName;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setCurrentFilter(Bundle filter) {
		mCurrentFilter = filter;
		
	}

	public Bundle getCurrentFilter() {
		return mCurrentFilter;
		
	}

}
