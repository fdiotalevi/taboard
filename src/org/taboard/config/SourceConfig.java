package org.taboard.config;

import android.app.Activity;
import android.app.Fragment;

public interface SourceConfig<T extends Fragment> {

	T createFragment(Activity activity);

	String getTag();

}
