/**
 * 
 */
package org.taboard.source.contacts;

import org.taboard.base.SourceManager;
import org.taboard.config.SourceConfig;
import org.taboard.filter.ContactFilterable;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Contacts;

/**
 * @author BeWi
 *
 */
public class ContactsSourceConfig implements SourceConfig<ContactsFragment>, ContactFilterable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7807538627788912969L;

	private String mName = null;
	
	private Bundle mCurrentFilter = null;
	
	/**
	 * 
	 */
	public ContactsSourceConfig(String mName) {
		this.mName = mName;
	}
	
	

	/* (non-Javadoc)
	 * @see org.taboard.config.SourceConfig#createFragment(android.app.Activity)
	 */
	public ContactsFragment createFragment(Activity activity, SourceManager sourceManager) {
		return new ContactsFragment(this);
	}

	/* (non-Javadoc)
	 * @see org.taboard.config.SourceConfig#getTag()
	 */
	public String getTag() {		
		return "contacts " + mName;
	}

	Class<? extends Fragment> getFragmentClass() {
		return ContactsFragment.class;
	}
	
	public void setCurrentFilter(Bundle filter) {
		mCurrentFilter = filter;
		
	}

	public Bundle getCurrentFilter() {
		return mCurrentFilter;
		
	}
	
	protected static Cursor standardCursor(ContentResolver cr) {
		return cr.query(
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
	}
	
	protected static Cursor cursorFilteredByName(ContentResolver cr, String constraint) {
		return cr.query(
				Contacts.CONTENT_URI,
				new String [] {
						Contacts._ID,
						Contacts.DISPLAY_NAME_PRIMARY,
						Contacts.LOOKUP_KEY,
						Contacts.PHOTO_THUMBNAIL_URI,
						Contacts.LAST_TIME_CONTACTED
						
				},
				"lower(" + Contacts.DISPLAY_NAME_PRIMARY + ") LIKE '%" + constraint + "%'",
				null,
				Contacts.DISPLAY_NAME_PRIMARY + " asc");
	}
	
	protected static Cursor cursorFilteredByEmail (ContentResolver cr, String email) {
		Cursor contactsMatchingEmailAdress =  cr.query(
				Email.CONTENT_URI,
	            new String[] {  
						Email.CONTACT_ID,
						Email.DISPLAY_NAME_PRIMARY,
						Email.LOOKUP_KEY,
						Email.PHOTO_THUMBNAIL_URI,
						Email.LAST_TIME_CONTACTED,
	                	Email.DATA1
	            },
	            "lower(" + Email.DATA1 + ") LIKE '%" + email + "%'",
	            null,
	            Email.CONTACT_ID + "asc"); 
		
//		String contactsCursorWhere = "";
//		if (contactsMatchingEmailAdress != null && contactsMatchingEmailAdress.moveToFirst()) {
//			contactsCursorWhere = 
//			put em all in an array and make up where condition
//		}
//		
//		cr.query(
//				Contacts.CONTENT_URI,
//				new String [] {
//						Contacts._ID,
//						Contacts.DISPLAY_NAME_PRIMARY,
//						Contacts.LOOKUP_KEY,
//						Contacts.PHOTO_THUMBNAIL_URI,
//						Contacts.LAST_TIME_CONTACTED
//						
//				},
//				"lower(" + Contacts._ID + " = " + constraint + "%'",
//				null,
//				Contacts.DISPLAY_NAME_PRIMARY + " asc");
		
		return contactsMatchingEmailAdress;
	}
	
	
	
	
}
