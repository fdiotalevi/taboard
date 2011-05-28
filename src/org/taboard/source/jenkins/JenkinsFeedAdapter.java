package org.taboard.source.jenkins;

import java.util.List;

import org.taboard.R;
import org.taboard.config.ColorScheme;
import org.taboard.source.googlecode.Issue;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class JenkinsFeedAdapter extends ArrayAdapter<Build>{

	public JenkinsFeedAdapter(Context context, int textViewResourceId, List<Build> objects) {
		super(context, R.layout.jenkins_row, textViewResourceId, objects);
		
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row =  super.getView(position, convertView, parent);
				
		if (position % 2 == 0)
			row.setBackgroundColor(ColorScheme.ODD_ROW);
		else
			row.setBackgroundColor(ColorScheme.EVEN_ROW);
		
		Build item = getItem(position);
		
		((TextView)row.findViewById(R.id.message)).setText(item.message);
		
		return row;
	}
}

	