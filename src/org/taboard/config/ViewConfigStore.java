package org.taboard.config;

import java.io.IOException;
import java.util.ArrayList;

import org.taboard.source.charts.ChartsSourceConfig;
import org.taboard.source.contacts.ContactsSourceConfig;
import org.taboard.source.git.GitSourceConfig;
import org.taboard.source.google.GoogleSourceConfig;
import org.taboard.source.googlecode.GoogleCodeIssueSourceConfig;
import org.taboard.source.jenkins.JenkinsFeedSourceConfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class ViewConfigStore {
	private final static String PROJECT1 = "fdiotalevi/taboard";
	private final static String PROJECT2 = "openintents/aainterfaces";
	
	private final static String VIEW_CONFIGURATTION = "VIEW_CONFIGURATION_KEY";
	private final static String TABOARD_PREFERENCES = "TABOARD_CONFIGURATION_KEY";
	private final static String TAG = "ViewConfigStore";

	
	public static ViewConfig loadDefault(){

		ArrayList<SourceConfig> mSources = new ArrayList<SourceConfig>();
		mSources.add(new GitSourceConfig(PROJECT1, "Taboard"));
		mSources.add(new GitSourceConfig(PROJECT2, "Open Android Apps"));
		mSources.add(new GoogleCodeIssueSourceConfig(
				"http://code.google.com/p/openintents/issues/csv", "OpenIntent"));
		mSources.add(new JenkinsFeedSourceConfig("http://ci.jenkins-ci.org/rssLatest", "Jenkins CI"));
				mSources.add(new ContactsSourceConfig(null));
		mSources.add(new GoogleSourceConfig(null));
		mSources.add(new ChartsSourceConfig("TestChart", new String[] {
				"Build Types", "number of builds per type" }, new String[] {
				"Successful Builds", "Failed Builds", "Cocktail Builds" },
				new double[] { 300d, 100d, 200d }));

		ViewConfig viewConfig = new ViewConfig();
		viewConfig.numCols = 2;
		viewConfig.numRows = 2;
		return viewConfig;
	}
	
	public static ViewConfig readViewConfig(Context context){
		SharedPreferences prefs = context.getSharedPreferences(TABOARD_PREFERENCES, Context.MODE_PRIVATE);			
		if (prefs.contains(VIEW_CONFIGURATTION))
			try {
				return (ViewConfig) ObjectSerializer.deserialize(prefs.getString(VIEW_CONFIGURATTION, ObjectSerializer.serialize(new ViewConfig())));
			} catch (IOException e) {
				Log.e(TAG, "Cannot deserialize configurations", e);				
				return new ViewConfig();				
			}
		else
			return new ViewConfig();
	}
	
	public static void storeViewConfig(ViewConfig vc, Context context){
		SharedPreferences prefs = context.getSharedPreferences(TABOARD_PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		try {
			editor.putString(VIEW_CONFIGURATTION, ObjectSerializer.serialize(vc));
		} catch (IOException e) {
			Log.e(TAG, "Cannot serialize configurations", e);			
		}
		editor.commit();
	}
	
	
}
