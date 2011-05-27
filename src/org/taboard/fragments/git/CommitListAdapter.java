package org.taboard.fragments.git;

import java.util.List;

import org.taboard.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CommitListAdapter extends ArrayAdapter<Commit>{

	public CommitListAdapter(Context context, int textViewResourceId, List<Commit> objects) {
		super(context, R.layout.commit, textViewResourceId, objects);
		
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row =  super.getView(position, convertView, parent);
		
		Commit item = getItem(position);
		
		((TextView)row.findViewById(R.id.commitMessage)).setText(item.message);
		
		return row;
	}

	
	
}
