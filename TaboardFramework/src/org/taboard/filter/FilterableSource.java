package org.taboard.filter;

import android.os.Bundle;

public interface FilterableSource {

	void setCurrentFilter(Bundle filter);
	Bundle getCurrentFilter();

}
