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
import org.taboard.Main;
import org.taboard.R;
import org.taboard.SourceManager;
import org.taboard.filter.FilterableFragment;
import org.taboard.view.AboutDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class GitCommitsFragment extends ListFragment implements
		FilterableFragment {

	private static final String TAG = "gitcommits";
	private GitSourceConfig mSc;
	private SourceManager mSourceManager;
	protected List<Commit> mCommits;

	public GitCommitsFragment(GitSourceConfig sc, SourceManager sm) {
		mSc = sc;
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

		AsyncTask<String, Void, List<Commit>> task = new AsyncTask<String, Void, List<Commit>>() {

			@Override
			protected void onPreExecute() {
				showProgressBar(true);
			}

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
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return output;
			}

			protected void onPostExecute(List<Commit> commits) {
				mCommits = commits;
				GitCommitsFragment.this.setListAdapter(new CommitListAdapter(
						GitCommitsFragment.this.getActivity(),
						R.id.commitMessage, commits));
				showProgressBar(false);
			}

		};

		if (mSc.getUrl() != null) {
			task.execute(mSc.getUrl());
		} else {
			showConfigDialog();
		}

	}

	
	public class GitCommitsConfigDialogFragment extends DialogFragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.gitcommits_config_dialog,container,false);
	        return v;			
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		
			Dialog v = super.onCreateDialog(savedInstanceState);
			v.setTitle(R.string.git_hub_config_dialog_title);
			return v;
		}
		
	}
	
	private void showConfigDialog() {
		// DialogFragment.show() will take care of adding the fragment
		// in a transaction. We also want to remove any currently showing
		// dialog, so make our own transaction and take care of that here.
		FragmentTransaction ft = getFragmentManager().beginTransaction(); 

		DialogFragment newFragment = new GitCommitsConfigDialogFragment();

		// Show the dialog.
		newFragment.show(ft, "dialog");
		
	}

	public void showProgressBar(boolean showProgressBar) {
		if (showProgressBar) {
			getListView().setVisibility(View.GONE);
			getView().findViewById(R.id.progress_bar).setVisibility(
					View.VISIBLE);
		} else {
			getListView().setVisibility(View.VISIBLE);
			getView().findViewById(R.id.progress_bar).setVisibility(View.GONE);

		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Bundle filter = new Bundle();
		String email = ((CommitListAdapter) l.getAdapter()).getItem(position).authorEmail;
		filter.putString("email", email);

		mSourceManager.doFilter(ContactFilterable.class, filter, email);
	}

	public void onFilterChanged() {
		Bundle filter = mSc.getCurrentFilter();
		List<Commit> filteredCommits;
		if (filter != null) {
			String emailFilter = filter.getString("email");
			filteredCommits = new ArrayList<Commit>();
			for (Commit c : mCommits) {
				if (c.authorEmail.equalsIgnoreCase(emailFilter)) {
					filteredCommits.add(c);
				}
			}
		} else {
			filteredCommits = mCommits;
		}

		getListView().setAdapter(
				new CommitListAdapter(getActivity(), R.id.commitMessage,
						filteredCommits));

	}

}
