/**
 * 
 */
package org.taboard.source.charts;

import org.taboard.app.developers.R;

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
public class ChartsFragment extends Fragment {

	private ChartsSourceConfig cfg = null;
	
	public ChartsFragment() {
		throw new RuntimeException("invalid use of Fragments");
	}
	
	/**
	 * 
	 */
	public ChartsFragment(ChartsSourceConfig cfg) {
		this.cfg = cfg;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		// inflate fragment layout
		return inflater.inflate(R.layout.charts_fragment, container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		// init html code header
		String htmlCode = 
				  "<html>"
				+	"<head>"
				+	  "<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"
				+	  "<script type=\"text/javascript\">"
				+		"google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
				+		"google.setOnLoadCallback(drawChart);"
				+		"function drawChart() {"
				+		"var data = new google.visualization.DataTable();";
		
		// init column labels
		String[] columnLabels = cfg.getColumnLabels();
		if (columnLabels == null || columnLabels.length != 2) {
			throw new RuntimeException("found more or less than 2 columnLabels - has to be exactly 2 (first for row labels (which are strings) and second for row values (which are numbers)");
		}
		htmlCode += "data.addColumn('string', '" + columnLabels[0] + "');";
		htmlCode += "data.addColumn('number', '" + columnLabels[1] + "');";
		
		// init rows
		String[] rowLabels = cfg.getRowLabels();
		double[] rowValues = cfg.getRowValues();
		
		if (rowLabels == null || rowValues == null) {
			throw new RuntimeException("neither rowLabels nor rowValues can be null - chart has to display data !");
		}		
		if (rowLabels.length != rowValues.length) {
			throw new RuntimeException("rowLabels and rowValues must have the same size/length (i.e. represent and equal number of rows) !");
		}
		
		htmlCode += "data.addRows(" + rowLabels.length + ");";
		
		for (int i = 0; i < rowLabels.length; i++) {
			htmlCode += "data.setValue(" + i + ", 0, '" + rowLabels[i] + "');";
			htmlCode += "data.setValue(" + i + ", 1, " + rowValues[i] + ");";
		}

		// finalise html code
		htmlCode += "var chart = new google.visualization.PieChart(document.getElementById('chart_div'));"
				 + "chart.draw(data, {width: 100, height: 100, title: '" + cfg.getChartTitle() + "'});"
      			+ "}"
      			+ "</script>"
      			+ "</head>"
      			+ "<body>"
      			+ "<div id=\"chart_div\"></div>"
      			+ "</body>"
      			+ "</html>";
		
		// format input: A String of data in the given encoding. The date must be URI-escaped -- '#', '%', '\', '?' should be replaced by %23, %25, %27, %3f respectively.
		int len = htmlCode.length();
		StringBuilder buf = new StringBuilder(len + 100);
		for (int i = 0; i < len; i++) {
			char chr = htmlCode.charAt(i);
			switch (chr) {
			case '%':
				buf.append("%25");
				break;
			case '\'':
				buf.append("%27");
				break;
			case '#':
				buf.append("%23");
				break;
			case '?':
				buf.append("%3f");
				break;  
			default:
				buf.append(chr);
			}
		}
		String fixedHTML = buf.toString();
		
		/** temporary lame pie chart */
		fixedHTML = "<img src=\"https://chart.googleapis.com/chart?cht=p3&amp;chd=s:Uf9a&amp;chs=300x150&amp;chl=";
		// do labels
		for (int i = 0; i < rowLabels.length; i++) {
			fixedHTML += rowLabels[i];
			if (i < rowLabels.length - 1) {
				fixedHTML += "|";
			}
		}
		fixedHTML += "&amp;chd=t:";
		// do values
		for (int i = 0; i < rowValues.length; i++) {
			fixedHTML += Math.round(rowValues[i]);
			if (i < rowValues.length - 1) {
				fixedHTML += ",";
			}
		}
		fixedHTML += "\">";
		
		
		// init listview
		WebView googleView = (WebView) getActivity().findViewById(R.id.charts_fragment_webview);
		googleView.loadData(fixedHTML, "text/html", "utf-8");
	}
}
