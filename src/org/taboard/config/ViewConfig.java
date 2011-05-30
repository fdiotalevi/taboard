package org.taboard.config;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewConfig implements Serializable{
	public ArrayList<SourceConfig> configs = new ArrayList<SourceConfig>();
	public int numRows;
	public int numCols;	
}
