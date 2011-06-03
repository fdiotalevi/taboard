package org.taboard.base;

import java.io.Serializable;

import org.taboard.config.SourceConfig;
import org.taboard.filter.FilterableSource;

import android.os.Bundle;

public interface SourceManager extends Serializable{
	 void doFilter(Class<? extends FilterableSource> filterableClass, Bundle filter, String description);

	void deleteSource(SourceConfig sourceConfig);

	void addFragment(SourceConfig sourceConfig);
}
