package org.taboard.source.googlecode;

import org.taboard.config.SourceConfig;

import android.app.Activity;
import android.app.Fragment;

public class GoogleCodeIssueSourceConfig implements SourceConfig {
	private String mUrl;
	private String mName;

	public GoogleCodeIssueSourceConfig(String url, String name) {
		mUrl = url;
		mName = name;
	}

	Class<? extends Fragment> getFragmentClass() {
		return GoogleIssuesFragment.class;
	}

	public Fragment createFragment(Activity activity) {
		return new GoogleIssuesFragment(this);
	}

	public String getTag() {
		return "goo code" + mName;
	}

	public String getTitle() {
		return "Google Code Issues: " + mName;
	}

	public String getUrl() {
		return mUrl;
	}
}
