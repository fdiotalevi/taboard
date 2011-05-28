package org.taboard.config;

import android.app.Activity;
import android.app.Fragment;

public interface SourceConfig {

	Fragment createFragment(Activity activity);

	String getTag();

}
