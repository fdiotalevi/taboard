package org.taboard.filter;

import android.os.Bundle;

public interface Filterable {

	void setCurrentFilter(Bundle filter);
	Bundle getCurrentFilter();

}
