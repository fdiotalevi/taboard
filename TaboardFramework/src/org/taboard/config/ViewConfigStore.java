package org.taboard.config;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class ViewConfigStore {

	private final static String VIEW_CONFIGURATTION = "VIEW_CONFIGURATION_KEY";
	private final static String TABOARD_PREFERENCES = "TABOARD_CONFIGURATION_KEY";
	private final static String TAG = "ViewConfigStore";

	/**
	 * try to read from preferences. If fails return null
	 * 
	 * @param context
	 * @return
	 */
	public static ViewConfig readViewConfig(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				TABOARD_PREFERENCES, Context.MODE_PRIVATE);
		if (prefs.contains(VIEW_CONFIGURATTION))
			try {
				return (ViewConfig) ObjectSerializer.deserialize(prefs
						.getString(VIEW_CONFIGURATTION,
								ObjectSerializer.serialize(new ViewConfig())));
			} catch (IOException e) {
				Log.e(TAG, "Cannot deserialize configurations", e);
			}

		return null;
	}

	/**
	 * store given configuration to preferences
	 * @param vc
	 * @param context
	 */
	public static void storeViewConfig(ViewConfig vc, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				TABOARD_PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		try {
			editor.putString(VIEW_CONFIGURATTION,
					ObjectSerializer.serialize(vc));
		} catch (IOException e) {
			Log.e(TAG, "Cannot serialize configurations", e);
		}
		editor.commit();
	}

}
