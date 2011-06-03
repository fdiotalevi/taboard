package org.taboard.source.git;

import java.util.List;

import org.taboard.ColorScheme;
import org.taboard.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;


public class CommitListAdapter extends ArrayAdapter<Commit> implements Filterable{
	

	
	public CommitListAdapter(Context context, int textViewResourceId, List<Commit> objects) {
		super(context, R.layout.commit_row, textViewResourceId, objects);
		
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row =  super.getView(position, convertView, parent);
		
		if (position % 2 == 0)
			row.setBackgroundColor(ColorScheme.ODD_ROW);
		else
			row.setBackgroundColor(ColorScheme.EVEN_ROW);
		
		Commit item = getItem(position);
		
		((TextView)row.findViewById(R.id.commitMessage)).setText(item.message);
		((TextView)row.findViewById(R.id.commitId)).setText("Commit id: "+item.id);
		
		((TextView)row.findViewById(R.id.authorEmail)).setTextColor(ColorScheme.EMAIL_COLOR);
		((TextView)row.findViewById(R.id.authorEmail)).setText(item.authorEmail);
		
		return row;
	}	
	
	
}
