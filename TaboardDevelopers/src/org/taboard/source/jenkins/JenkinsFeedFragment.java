package org.taboard.source.jenkins;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.taboard.R;
import org.taboard.SourceManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class JenkinsFeedFragment  extends ListFragment{
	
	private JenkinsFeedSourceConfig mSc;
	private SourceManager mSourceManager;
	protected List<Build> mIssues;

	public JenkinsFeedFragment(JenkinsFeedSourceConfig sc, SourceManager sm) {
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
   		
   		AsyncTask<String, Void, List<Build>> task = new AsyncTask<String, Void, List<Build>>() {

			@Override
			protected List<Build> doInBackground(String... params) {
				HttpClient client = new DefaultHttpClient();
		   		HttpGet get = new HttpGet(params[0]);
		   		List<Build> output = new ArrayList<Build>();
		   		
		   		try {
					HttpResponse response = client.execute(get);
					
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					Document doc = null;
					try {
						DocumentBuilder db = dbf.newDocumentBuilder();
						doc = db.parse(new InputSource(response.getEntity().getContent()));
						doc.getDocumentElement().normalize();

					} catch (Throwable e) {
						Log.e(this.getClass().getName(), "Cannot parse", e);
					}
					
					NodeList list = doc.getElementsByTagName("entry");
					
					
					for (int i = 0; i < list.getLength(); i++) {
						
						
						Build b = null;
						NodeList children = list.item(i).getChildNodes(); 
						for (int j = 0; j < children.getLength(); j++) {							
							Node child = children.item(j);
							if ("title".equals(child.getNodeName()))
							{
								b = new Build(getText(child));
							}						
						}
						output.add(b);
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
			
		     protected void onPostExecute(List<Build> commits) {
		    	 mIssues = commits;
		    	 JenkinsFeedFragment.this.setListAdapter(new JenkinsFeedAdapter(JenkinsFeedFragment.this.getActivity(), R.id.message, mIssues));
		    	 
		     }
	
   			
   		};
   		
   		task.execute(mSc.getUrl());   		   	
   	}
   	
   	@Override
   	public void onListItemClick(ListView l, View v, int position, long id) {
//   		Bundle filter = new Bundle();
//		String email = ((IssueListAdapter) l.getAdapter()).getItem(position).authorEmail;
//		filter.putString(
//				"email",
//				email);
//
//		mSourceManager.doFilter(ContactFilterable.class, filter, email);
   	}

	public void onFilterChanged() {
//		Bundle filter = mSc.getCurrentFilter();
//		List<Issue> filteredIssues;
//		if (filter != null) {
//			String emailFilter = filter.getString("email");
//			filteredIssues = new ArrayList<Issue>();
//			for (Issue c : mIssues) {
//				if (c.authorEmail.equalsIgnoreCase(emailFilter)) {
//					filteredIssues.add(c);
//				}
//			}
//		} else {
//			filteredIssues = mIssues;
//		}
//
//		getListView().setAdapter(
//				new IssueListAdapter(getActivity(), R.id.commitMessage,
//						filteredIssues));

	} 
	
	protected String getText(Node node) {
		return node.getFirstChild() != null ? node.getFirstChild().getNodeValue().trim() : "";
	}
   

}
