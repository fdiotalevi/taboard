/**
 * 
 */
package org.taboard.source.google;

import org.taboard.base.SourceManager;
import org.taboard.config.SourceConfig;
import org.taboard.source.git.GitCommitsFragment;

import android.app.Activity;
import android.app.Fragment;

/**
 * @author BeWi
 *
 */
public class GoogleSourceConfig implements SourceConfig {

	private String mKeywords = null;
	
	/**
	 * 
	 */
	public GoogleSourceConfig(String mKeywords) {
		this.mKeywords = mKeywords;
	}

	Class<? extends Fragment> getFragmentClass() {
		return GoogleFragment.class;
	}

	public Fragment createFragment(Activity activity, SourceManager sourceManager) {
		return new GoogleFragment(this);
	}

	/* (non-Javadoc)
	 * @see org.taboard.config.SourceConfig#getTag()
	 */
	public String getTag() {
		return "google " + mKeywords;
	}
	
}
