package org.taboard.source.git;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.taboard.R;

import android.app.Activity;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GitCommitsFragment extends ListFragment{
	
	private static final String TAG = "gitcommits";
	private GitSourceConfig mSc;
	
	
	public GitCommitsFragment(GitSourceConfig sc) {
		mSc =sc; 	
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
   		
   		AsyncTask<String, Void, List<Commit>> task = new AsyncTask<String, Void, List<Commit>>() {

			@Override
			protected List<Commit> doInBackground(String... params) {
				HttpClient client = new DefaultHttpClient();
		   		HttpGet get = new HttpGet(params[0]);
		   		List<Commit> output = new ArrayList<Commit>();
		   		
		   		try {
		   			BasicResponseHandler handler = new BasicResponseHandler();
					String response = client.execute(get, handler);
														
					@SuppressWarnings("unused")
					JSONObject commits = new JSONObject(response); 
										
					JSONArray commitArray = commits.getJSONArray("commits");
					
					
					
					for (int i = 0; i < commitArray.length(); i++) {
						output.add(new Commit((JSONObject) commitArray.get(i)));
					}
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return output;
			}
			
		     protected void onPostExecute(List<Commit> commits) {
		    	 GitCommitsFragment.this.setListAdapter(new CommitListAdapter(GitCommitsFragment.this.getActivity(), R.id.commitMessage, commits));
		     }
	
   			
   		};
   		
   		task.execute(mSc.getUrl());
   		
   		
   	}
   
   
   

}
