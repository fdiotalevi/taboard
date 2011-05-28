package org.taboard;

import java.util.ArrayList;
import java.util.List;

import org.taboard.config.SourceConfig;
import org.taboard.config.UrlSourceConfig;
import org.taboard.source.git.GitCommitsFragment;
import org.taboard.source.git.GitSourceConfig;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class Main extends Activity {
	
	List<SourceConfig> mSources;
	
	private String URL1 = "http://github.com/api/v2/json/commits/list/fdiotalevi/taboard/master";
	private String URL2 = "http://github.com/api/v2/json/commits/list/openintents/aainterfaces/master";

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mSources = new ArrayList<SourceConfig>();
        mSources.add(new GitSourceConfig(URL1, "Taboard"));
        mSources.add(new GitSourceConfig(URL2, "Open Android Apps"));
        //mSources.add(new ContactsSourceConfig());
        
        FragmentManager fm = getFragmentManager();
        FragmentTransaction t = fm.beginTransaction();
        for (SourceConfig sc: mSources){
        	Fragment fragment = sc.createFragment(this);
        	t.add(R.id.taboard, fragment, sc.getTag());
        	
        }
        t.commit();
    }
    
    
}