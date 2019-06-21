package com.ciaj.comm.utils;

import java.util.Collection;

/**
 * @Description
 * @Author Ciaj.
 * @Date 2019/6/21 10:25
 * @Version 1.0
 */
public class CollectionUtil {
	public static boolean isEmpty(Collection coll) {
		return (coll == null || coll.size() == 0) ? true : false;
	}

	public static boolean isNotEmpty(Collection coll) {
		return (coll != null && coll.size() > 0) ? true : false;
	}

	public static boolean isEmpty(Object[] arrs) {
		return (arrs == null || arrs.length == 0) ? true : false;
	}

	public static boolean isNotEmpty(Object[] arrs) {
		return (arrs != null && arrs.length > 0) ? true : false;
	}
}
