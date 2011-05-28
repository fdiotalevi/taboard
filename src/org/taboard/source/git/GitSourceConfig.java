package org.taboard.source.git;

import org.taboard.SourceManager;
import org.taboard.config.SourceConfig;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class GitSourceConfig implements SourceConfig<GitCommitsFragment>, ContactFilterable {
	private String mProject;
	private String mName;
	private Bundle mCurrentFilter;


	public GitSourceConfig(){
		
	}
	
	public GitSourceConfig(String project, String name) {
		mProject = project;
		mName = name;		
	}

	Class<? extends Fragment> getFragmentClass() {
		return GitCommitsFragment.class;
	}

	public GitCommitsFragment createFragment(Activity activity, SourceManager sourceManager) {
		return new GitCommitsFragment(this, sourceManager);
	}

	public String getTag() {
		return "git" + mName;
	}

	public String getTitle() {
		return "git changes: " + mName;
	}

	public String getUrl() {
		return "http://github.com/api/v2/json/commits/list/" + mProject +"/master";
	}
	public String getName(){
		return mName;
	}
	public String getProject(){
		return mProject;
	}

	public void setCurrentFilter(Bundle filter) {
		mCurrentFilter = filter;
		
	}

	public Bundle getCurrentFilter() {
		return mCurrentFilter;
		
	}

}
