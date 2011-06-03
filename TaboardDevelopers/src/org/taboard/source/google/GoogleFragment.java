/**
 * 
 */
package org.taboard.source.google;

import org.taboard.app.developers.R;
import org.taboard.config.SourceConfig;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * @author BeWi
 *
 */
public class GoogleFragment extends Fragment {

	private SourceConfig cfg = null;
	
	public GoogleFragment() {
		throw new RuntimeException("invalid use of Fragments");
	}
	
	/**
	 * 
	 */
	public GoogleFragment(SourceConfig cfg) {
		this.cfg = cfg;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		// inflate fragment layout
		return inflater.inflate(R.layout.google_fragment, container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		// init listview
		WebView googleView = (WebView) getActivity().findViewById(R.id.google_fragment_webview);
		googleView.loadUrl("http://google.com/");
	}
}
