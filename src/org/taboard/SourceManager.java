package org.taboard;

import org.taboard.config.SourceConfig;
import org.taboard.filter.Filterable;

import android.os.Bundle;

public interface SourceManager {
	 void doFilter(Class<? extends Filterable> filterableClass, Bundle filter, String description);

	void deleteSource(SourceConfig mSc);
}
