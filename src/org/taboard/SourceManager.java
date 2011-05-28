package org.taboard;

import org.taboard.filter.Filterable;

import android.os.Bundle;

public interface SourceManager {
	 void doFilter(Class<? extends Filterable> filterableClass, Bundle filter, String description);
}
