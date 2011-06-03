package org.taboard.app.developers;

import org.taboard.base.DashBoardActivity;
import org.taboard.config.SourceConfig;
import org.taboard.config.ViewConfig;
import org.taboard.source.charts.ChartsSourceConfig;
import org.taboard.source.contacts.ContactsSourceConfig;
import org.taboard.source.git.GitSourceConfig;
import org.taboard.source.google.GoogleSourceConfig;
import org.taboard.source.googlecode.GoogleCodeIssueSourceConfig;

import android.app.Fragment;


public class Main extends DashBoardActivity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1434608880980899960L;
	
	
	private static final Class[] SOURCE_CONFIGS = new Class[] {
		                       GitSourceConfig.class, //
		                       GoogleCodeIssueSourceConfig.class, //
		                       ContactsSourceConfig.class, //
		                       ChartsSourceConfig.class, GoogleSourceConfig.class };
		


	@Override
	public ViewConfig loadDefaultViewConfig() {
		return ViewConfigStore.loadDefault();
	}
	
	@Override
	public int getArrayLabelSourceResourceId() {
		return R.array.arraylabels_source;
	}

	@Override
	public Class<SourceConfig<? extends Fragment>>[] getSourceConfigsClasses(){
		return SOURCE_CONFIGS; 
	}
}
