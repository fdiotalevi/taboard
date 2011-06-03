package org.taboard.source.googlecode;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.taboard.app.developers.R;
import org.taboard.base.SourceManager;
import org.taboard.filter.ContactFilterable;
import org.taboard.filter.FilterableFragment;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import au.com.bytecode.opencsv.CSVReader;

public class GoogleIssuesFragment extends ListFragment implements FilterableFragment{
	
	private static final String TAG = "googleissues";
	private GoogleCodeIssueSourceConfig mSc;
	protected List<Issue> mIssues;
	private SourceManager mSourceManager;
	
	
	public GoogleIssuesFragment(GoogleCodeIssueSourceConfig sc, SourceManager sm) {
		mSc =sc; 	
		mSourceManager = sm;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.git_commit_list, null);
		TextView tv = (TextView) view.findViewById(R.id.title);
		tv.setText(mSc.getTitle());
		
		return view;
		
	}
	
   @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        
        getCommitList();
        
                
    }
   
   	private void getCommitList() {
   		
   		AsyncTask<String, Void, List<Issue>> task = new AsyncTask<String, Void, List<Issue>>() {

			@Override
			protected List<Issue> doInBackground(String... params) {
				HttpClient client = new DefaultHttpClient();
		   		HttpGet get = new HttpGet(params[0]);
		   		List<Issue> output = new ArrayList<Issue>();
		   		
		   		try {
					HttpResponse response = client.execute(get);
					 CSVReader reader = new CSVReader(new InputStreamReader(response.getEntity().getContent()));
					    String [] nextLine;
					    while ((nextLine = reader.readNext()) != null) {
					    	if (nextLine.length > 5){
					    		output.add(new Issue(nextLine));
					    	}
					    }
					
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return output;
			}
			
		     protected void onPostExecute(List<Issue> commits) {
		    	 mIssues = commits;
		    	 GoogleIssuesFragment.this.setListAdapter(new IssueListAdapter(GoogleIssuesFragment.this.getActivity(), R.id.commitMessage, mIssues));
		     }
	
   			
   		};
   		
   		task.execute(mSc.getUrl());   		   	
   	}
   	
   	@Override
   	public void onListItemClick(ListView l, View v, int position, long id) {
   		Bundle filter = new Bundle();
		String email = ((IssueListAdapter) l.getAdapter()).getItem(position).authorEmail;
		filter.putString(
				"email",
				email);

		mSourceManager.doFilter(ContactFilterable.class, filter, email);
   	}

	public void onFilterChanged() {
		Bundle filter = mSc.getCurrentFilter();
		List<Issue> filteredIssues;
		if (filter != null) {
			String emailFilter = filter.getString("email");
			filteredIssues = new ArrayList<Issue>();
			for (Issue c : mIssues) {
				if (c.authorEmail.equalsIgnoreCase(emailFilter)) {
					filteredIssues.add(c);
				}
			}
		} else {
			filteredIssues = mIssues;
		}

		getListView().setAdapter(
				new IssueListAdapter(getActivity(), R.id.commitMessage,
						filteredIssues));

	} 
   
   

}
