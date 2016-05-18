/*
 * Copyright (c) 2016.
 */

package core.utils;

import java.util.Comparator;

/**
 * @author Saurabh
 * @version 1.0
 */
public class RevalIdComparator implements Comparator<String> {
	/**
	 * @param id1 treemap key new
	 * @param id2 treemap key old
	 * @return compareTo values by comparing two Reval Id's in treemap
	 */
	@Override
	public int compare(String id1, String id2) {
		int i = Integer.parseInt(id1.substring(1));
		int j = Integer.parseInt(id2.substring(1));

		if (i > j)
			return 1;
		else if (i < j)
			return -1;
		else
			return 0;
	}

}
