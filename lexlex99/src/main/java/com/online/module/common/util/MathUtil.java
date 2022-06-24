package com.online.module.common.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MathUtil {
	
	private static MathUtil SELF_INSTANCE;
	
	public static MathUtil getInstance() {
		if (SELF_INSTANCE == null) {
			SELF_INSTANCE = new MathUtil();
		}
		
		return SELF_INSTANCE;
	}

	public static Long returnIdObjToLong(Object obj) {
		if (obj != null) {
			if (obj instanceof Number) {
				if (obj instanceof BigDecimal) {
					return ((BigDecimal) obj).longValue();
				} else if (obj instanceof BigInteger) {
					return ((BigInteger) obj).longValue();
				} else if (obj instanceof Double) {
					return ((Double) obj).longValue();
				} else if (obj instanceof Long) {
					return ((Long) obj).longValue();
				} else if (obj instanceof Float) {
					return ((Float) obj).longValue();
				} else if (obj instanceof Integer) {
					return ((Integer) obj).longValue();
				}
			}
		}
		
		return null;
	}
	
	public static MathUtil getSELF_INSTANCE() {
		return SELF_INSTANCE;
	}

	public static void setSELF_INSTANCE(MathUtil sELF_INSTANCE) {
		SELF_INSTANCE = sELF_INSTANCE;
	}

	public static String currencyFormat(String currency) {
		String result = "";
		String seperator = ".";
		
		for (int i = currency.length(); i > 0; i--) {
			char a = currency.charAt(i);
			if (i%3 == 0) {
				result = result + seperator;
			}
			result = result + a;
		}
		
		return result;
	}
	
}
