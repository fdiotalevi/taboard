/**
 * 
 */
package org.taboard.source.contacts;

import java.io.InputStream;
import java.net.URI;

import org.taboard.R;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

/**
 * @author BeWi
 *
 */
public class ContactsCursorAdapter extends CursorAdapter {

	private final LayoutInflater mInflater;
	private Context context;
	
	private int nameCursorIndex = -1;
	private int thumbnailCursorIndex = -1;
	private int lastContactCursorIndex = -1;
	private int keyCursorIndex = -1;
	
	/**
	 * @param context
	 * @param c
	 */
	public ContactsCursorAdapter(Context context, Cursor c) {
		super(context, c);
		
		this.mInflater = LayoutInflater.from(context);
		
	}

	/**
	 * @param context
	 * @param c
	 * @param autoRequery
	 */
	public ContactsCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);

		this.mInflater = LayoutInflater.from(context);
		
	}

	/**
	 * @param context
	 * @param c
	 * @param flags
	 */
	public ContactsCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		
		this.mInflater = LayoutInflater.from(context);
	}

	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		ContactViewHolder holder = (ContactViewHolder)view.getTag();
		
		String contactName = "";
		if (nameCursorIndex >= 0) {
			contactName = cursor.getString(nameCursorIndex);
		}
		holder.name.setText(contactName);
		
		String contactLastContact = "";
		if (lastContactCursorIndex >= 0) {
			contactLastContact = DateFormat.format("EEEE, MMMM dd, yyyy h:mmaa", cursor.getLong(lastContactCursorIndex)).toString();
		}
		holder.lastContact.setText(contactLastContact);
		
		
		Bitmap contactThumbnail = null;
		if (thumbnailCursorIndex >= 0) {
			String thumbnailUriString = cursor.getString(thumbnailCursorIndex);
			if (thumbnailUriString != null && false) { //TODO 
				InputStream thumbnailStream = Contacts.openContactPhotoInputStream(context.getContentResolver(), Uri.parse(thumbnailUriString));
				contactThumbnail = BitmapFactory.decodeStream(thumbnailStream);
			}
		}
		holder.thumbnail.setImageBitmap(contactThumbnail);
		
		String contactKey = "";
		if (keyCursorIndex >= 0) {
			contactKey = cursor.getString(keyCursorIndex);
		}
		Uri contactLookupUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, contactKey);
		holder.thumbnail.assignContactUri(Contacts.getLookupUri(context.getContentResolver(), Contacts.lookupContact(context.getContentResolver(), contactLookupUri)));

	}

	/* (non-Javadoc)
	 * @see android.widget.CursorAdapter#newView(android.content.Context, android.database.Cursor, android.view.ViewGroup)
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		
		/** init cursor */
		if (cursor != null) {
			if (nameCursorIndex < 0) {
				nameCursorIndex = cursor.getColumnIndex(Contacts.DISPLAY_NAME_PRIMARY);
			}
			if (thumbnailCursorIndex < 0) {
				thumbnailCursorIndex = cursor.getColumnIndex(Contacts.PHOTO_THUMBNAIL_URI);
			}
			if (lastContactCursorIndex < 0) {
				lastContactCursorIndex = cursor.getColumnIndex(Contacts.LAST_TIME_CONTACTED);
			}
			if (keyCursorIndex < 0) {
				keyCursorIndex = cursor.getColumnIndex(Contacts.LOOKUP_KEY);
			}
		}
		
		/** init view */
		
		final View view = mInflater.inflate(R.layout.contacts_row, parent, false);

		ContactViewHolder holder = new ContactViewHolder();
		holder.thumbnail = (QuickContactBadge) view.findViewById(R.id.contactsRow_thumbnail);
		holder.name = (TextView) view.findViewById(R.id.contactsRow_name);
		holder.lastContact = (TextView) view.findViewById(R.id.contactsRow_lastContact);
		view.setTag(holder);
		
		return view;
	}

	@Override
	public boolean hasStableIds() {
		// IDs of cursor never change - allows performance improvements in ListView.
		return true;
	}
	
	@Override
	public int getViewTypeCount() {
		return 1;
	}
	
	static class ContactViewHolder {
		QuickContactBadge thumbnail;
		TextView name;
		TextView lastContact;
	}
	
}
