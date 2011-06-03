package org.taboard.source.jenkins;

import org.taboard.base.SourceManager;
import org.taboard.config.SourceConfig;

import android.app.Activity;
import android.os.Bundle;

public class JenkinsFeedSourceConfig  implements SourceConfig<JenkinsFeedFragment>{ //, ContactFilterable 
		
	private String mUrl;
	private String mName;
	private Bundle mCurrentFilter;

	public JenkinsFeedSourceConfig(String url, String name) {
		mUrl = url;
		mName = name;
	}

	public JenkinsFeedFragment createFragment(Activity activity,
			SourceManager sourceManager) {
		return new JenkinsFeedFragment(this, sourceManager);
	}
	
	public String getTitle() {
		return "Jenkins CI: " + mName;
	}

	public String getTag() {
		return "jenkins";
	}

	public void setCurrentFilter(Bundle filter) {
		mCurrentFilter = filter;
	}

	public Bundle getCurrentFilter() {
		return mCurrentFilter;
	}
	
	public String getUrl() {
		return mUrl;
	}
	
	

}
