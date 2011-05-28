/**
 * 
 */
package org.taboard.source.contacts;

import org.taboard.config.SourceConfig;
import org.taboard.source.google.GoogleFragment;

import android.app.Activity;
import android.app.Fragment;

/**
 * @author BeWi
 *
 */
public class ContactsSourceConfig implements SourceConfig {

	private String mName = null;
	
	/**
	 * 
	 */
	public ContactsSourceConfig(String mName) {
		this.mName = mName;
	}

	/* (non-Javadoc)
	 * @see org.taboard.config.SourceConfig#createFragment(android.app.Activity)
	 */
	public Fragment createFragment(Activity activity) {
		// TODO Auto-generated method stub
		return new ContactsFragment(this);
	}

	/* (non-Javadoc)
	 * @see org.taboard.config.SourceConfig#getTag()
	 */
	public String getTag() {
		// TODO Auto-generated method stub
		return "contacts " + mName;
	}

	Class<? extends Fragment> getFragmentClass() {
		return ContactsFragment.class;
	}
	
}
