package org.taboard.source.googlecode;

import java.util.List;

import org.taboard.app.developers.R;
import org.taboard.base.ColorScheme;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class IssueListAdapter extends ArrayAdapter<Issue>{

	public IssueListAdapter(Context context, int textViewResourceId, List<Issue> objects) {
		super(context, R.layout.commit_row, textViewResourceId, objects);
		
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row =  super.getView(position, convertView, parent);
				
		if (position % 2 == 0)
			row.setBackgroundColor(ColorScheme.ODD_ROW);
		else
			row.setBackgroundColor(ColorScheme.EVEN_ROW);
		
		Issue item = getItem(position);
		
		((TextView)row.findViewById(R.id.commitMessage)).setText(item.message);
		
		((TextView)row.findViewById(R.id.authorEmail)).setTextColor(ColorScheme.EMAIL_COLOR);
		((TextView)row.findViewById(R.id.authorEmail)).setText(item.authorEmail);
		
		return row;
	}

	
	
}
