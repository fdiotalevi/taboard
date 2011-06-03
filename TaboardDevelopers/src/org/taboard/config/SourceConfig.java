package org.taboard.config;

import java.io.Serializable;

import org.taboard.SourceManager;

import android.app.Activity;
import android.app.Fragment;

/**
 * Configuration of a data source toe should be shown in a Fragment of type T
 * 
 * @author friedger
 *
 * @param <T> type of Fragment to be used for displaying this data
 */
public interface SourceConfig<T extends Fragment> extends Serializable{

	T createFragment(Activity activity, SourceManager sourceManager);

	String getTag();

}
