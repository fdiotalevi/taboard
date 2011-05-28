/**
 * 
 */
package org.taboard.source.charts;

import org.taboard.SourceManager;
import org.taboard.config.SourceConfig;
import org.taboard.source.git.GitCommitsFragment;

import android.app.Activity;
import android.app.Fragment;

/**
 * @author BeWi
 *
 */
public class ChartsSourceConfig implements SourceConfig {
	
	private String chartTitle;
	private String[] columnLabels;
	private String[] rowLabels;
	private double[] rowValues;
	
	
	/**
	 * 
	 */
	public ChartsSourceConfig(String chartTitle, String[] columnLabels, String[] rowLabels, double[] rowValues) {
		this.chartTitle = chartTitle;
		this.columnLabels = columnLabels;
		this.rowLabels = rowLabels;
		this.rowValues = rowValues;
	}

	Class<? extends Fragment> getFragmentClass() {
		return ChartsFragment.class;
	}

	public Fragment createFragment(Activity activity, SourceManager sourceManager) {
		return new ChartsFragment(this);
	}

	/* (non-Javadoc)
	 * @see org.taboard.config.SourceConfig#getTag()
	 */
	public String getTag() {
		return "charts " + chartTitle;
	}
	
	public String getChartTitle() {
		return chartTitle;
	}
	
	public String[] getColumnLabels() {
		return columnLabels;
	}
	
	public String[] getRowLabels() {
		return rowLabels;
	}
	
	public double[] getRowValues() {
		return rowValues;
	}
	
}
