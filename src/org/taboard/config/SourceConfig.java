package org.taboard.config;

import org.taboard.SourceManager;

import android.app.Activity;
import android.app.Fragment;

public interface SourceConfig<T extends Fragment>{

	T createFragment(Activity activity, SourceManager sourceManager);

	String getTag();

}
