/**
 * 
 */
package org.taboard.source.contacts;

import org.taboard.R;
import org.taboard.filter.FilterableFragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @author BeWi
 * 
 */
public class ContactsFragment extends Fragment implements FilterableFragment {

	private ContactsSourceConfig cfg = null;

	private ListView contactsList = null;

	/**
	 * 
	 */
	public ContactsFragment() {
		throw new RuntimeException("invalid use of Fragments");
	}

	/**
	 * @param sourceManager
	 * 
	 */
	public ContactsFragment(ContactsSourceConfig cfg) {
		this.cfg = cfg;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// inflate fragment layout
		return inflater.inflate(R.layout.contacts_fragment, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		// init listview
		contactsList = (ListView) getActivity().findViewById(R.id.contactsList);

		// init cursor
		Cursor contactsCursor = ContactsSourceConfig
				.standardCursor(getActivity().getContentResolver());

		// init adapter
		ContactsCursorAdapter contactsAdater = new ContactsCursorAdapter(
				getActivity(), contactsCursor);

		// attach adapter to list
		contactsList.setAdapter(contactsAdater);
	}

	public void onFilterChanged() {
		if (cfg.getCurrentFilter() == null) {
			contactsList.setFilterText(null);
		} else {
			// get filter
			String contactNameFilter = cfg.getCurrentFilter().getString(
					"contactName");
			// forward filter
			contactsList.setFilterText(contactNameFilter);

			String emailFilter = cfg.getCurrentFilter().getString("email");
			contactsList.setFilterText(emailFilter);
		}
	}

}
