package org.taboard.source.git;

import org.taboard.config.SourceConfig;

import android.app.Activity;
import android.app.Fragment;

public class GitSourceConfig implements SourceConfig {
	private String mUrl;
	private String mName;

	public GitSourceConfig(String url, String name) {
		mUrl = url;
		mName = name;
	}

	Class<? extends Fragment> getFragmentClass() {
		return GitCommitsFragment.class;
	}

	public Fragment createFragment(Activity activity) {
		return new GitCommitsFragment(this);
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
}
