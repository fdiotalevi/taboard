/**
 * 
 */
package org.taboard.source.contacts;

import org.taboard.R;
import org.taboard.config.SourceConfig;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @author BeWi
 *
 */
public class ContactsFragment extends Fragment {

	private SourceConfig cfg = null;
	
	/**
	 * 
	 */
	public ContactsFragment() {
		throw new RuntimeException("invalid use of Fragments");
	}
	
	/**
	 * 
	 */
	public ContactsFragment(SourceConfig cfg) {
		this.cfg = cfg;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		// inflate fragment layout
		return inflater.inflate(R.layout.contacts_fragment, container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		// init listview
		ListView contactsList = (ListView) getActivity().findViewById(R.id.contactsList);
		
		// init cursor
		Cursor contactsCursor = getActivity().getContentResolver().query(
				Contacts.CONTENT_URI,
				new String [] {
						Contacts._ID,
						Contacts.DISPLAY_NAME_PRIMARY,
						Contacts.LOOKUP_KEY,
						Contacts.PHOTO_THUMBNAIL_URI,
						Contacts.LAST_TIME_CONTACTED
						
				},
				null,
				null,
				Contacts.DISPLAY_NAME_PRIMARY + " asc");
		
		// init adapter
		ContactsCursorAdapter contactsAdater = new ContactsCursorAdapter(getActivity(), contactsCursor);
		
		// attach adapter to list
		contactsList.setAdapter(contactsAdater);
	}

}
