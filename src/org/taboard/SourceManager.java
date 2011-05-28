package org.taboard;

import org.taboard.config.SourceConfig;
import org.taboard.filter.FilterableSource;

import android.os.Bundle;

public interface SourceManager {
	 void doFilter(Class<? extends FilterableSource> filterableClass, Bundle filter, String description);

	void deleteSource(SourceConfig mSc);
}
