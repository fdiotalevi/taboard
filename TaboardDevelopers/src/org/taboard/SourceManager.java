package org.taboard;

import java.io.Serializable;

import org.taboard.config.SourceConfig;
import org.taboard.filter.FilterableSource;
import org.taboard.source.git.GitSourceConfig;

import android.os.Bundle;

public interface SourceManager extends Serializable{
	 void doFilter(Class<? extends FilterableSource> filterableClass, Bundle filter, String description);

	void deleteSource(SourceConfig mSc);

	void addFragment(SourceConfig gitSourceConfig);
}
