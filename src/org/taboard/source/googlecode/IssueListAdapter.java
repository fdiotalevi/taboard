package org.taboard.source.googlecode;

import java.util.List;

import org.taboard.R;

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
		
		Issue item = getItem(position);
		
		((TextView)row.findViewById(R.id.commitMessage)).setText(item.message);
		
		return row;
	}

	
	
}
