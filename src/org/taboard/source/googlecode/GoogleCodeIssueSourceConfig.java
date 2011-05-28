package org.taboard.source.googlecode;

import org.taboard.SourceManager;
import org.taboard.config.SourceConfig;
import org.taboard.source.git.ContactFilterable;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class GoogleCodeIssueSourceConfig implements SourceConfig<GoogleIssuesFragment>, ContactFilterable {
	private String mUrl;
	private String mName;
	private Bundle mCurrentFilter;

	public GoogleCodeIssueSourceConfig(String url, String name) {
		mUrl = url;
		mName = name;
	}

	Class<? extends Fragment> getFragmentClass() {
		return GoogleIssuesFragment.class;
	}

	public GoogleIssuesFragment createFragment(Activity activity, SourceManager sourceManager) {
		return new GoogleIssuesFragment(this, sourceManager);
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

	public void setCurrentFilter(Bundle filter) {
		mCurrentFilter = filter;
	}

	public Bundle getCurrentFilter() {
		return mCurrentFilter;
	}
}
