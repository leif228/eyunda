package com.hangyi.eyunda.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class PriceUtil {
	private static final BigDecimal base100 = new BigDecimal("100.00");

	public static double getDoubleYuan(long fen) {
		BigDecimal bd = new BigDecimal(fen);
		double result = bd.divide(base100).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		return result;
	}

	public static double getDoubleYuan(int fen) {
		BigDecimal bd = new BigDecimal(fen);
		double result = bd.divide(base100).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		return result;
	}

	public static long getLongFen(double yuan) {
		BigDecimal bd = new BigDecimal(yuan);
		long result = bd.multiply(base100).setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue();
		return result;
	}

	public static int getIntFen(double yuan) {
		BigDecimal bd = new BigDecimal(yuan);
		int result = bd.multiply(base100).setScale(0, BigDecimal.ROUND_HALF_EVEN).intValue();
		return result;
	}

	public static String getFormatYuan(double yuan) {
		DecimalFormat df = new DecimalFormat("#0.00");
		String result = df.format(yuan);
		return result;
	}
}
