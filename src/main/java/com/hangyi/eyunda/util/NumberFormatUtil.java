package com.hangyi.eyunda.util;

import java.text.NumberFormat;

public class NumberFormatUtil {
	private static String defaultValue = "0.0";
	private static NumberFormat nf1 = null;
	private static NumberFormat nf2 = null;
	private static NumberFormat nf3 = null;

	static {
		nf1 = NumberFormat.getInstance();
		nf1.setMaximumFractionDigits(1);
		nf1.setMinimumFractionDigits(1);

		nf2 = NumberFormat.getInstance();
		nf2.setMaximumFractionDigits(2);
		nf2.setMinimumFractionDigits(2);

		nf3 = NumberFormat.getInstance();
		nf3.setMaximumFractionDigits(3);
		nf3.setMinimumFractionDigits(3);
	}

	public static Integer toInt(double number) {
		if (Double.isNaN(number) || Double.isInfinite(number))
			return 0;
		else
			return new Integer((int) number);
	}

	public static double format1(double number) {
		if (Double.isNaN(number) || Double.isInfinite(number))
			return Double.parseDouble(defaultValue);
		else
			return Double.parseDouble(nf1.format(number).replace(",", ""));
	}

	public static double format(double number) {
		if (Double.isNaN(number) || Double.isInfinite(number))
			return Double.parseDouble(defaultValue);
		else
			return Double.parseDouble(nf2.format(number).replace(",", ""));
	}

	public static double format3(double number) {
		if (Double.isNaN(number) || Double.isInfinite(number))
			return Double.parseDouble(defaultValue);
		else
			return Double.parseDouble(nf3.format(number).replace(",", ""));
	}
	
	public static Integer[] StringtoInt(String[] ss) {
		if (ss == null)
			return null;

		Integer ret[] = new Integer[ss.length];

		int i = 0;
		if(ss[i].equals(""))
			return null;
		for (String s : ss)
			ret[i++] = Integer.valueOf(s);

		return ret;
	}

	public static Double[] StringtoDouble(String[] ss) {
		if (ss == null)
			return null;

		Double ret[] = new Double[ss.length];

		int i = 0;
		if(ss[i].equals(""))
			return null;
		for (String s : ss)
			ret[i++] = Double.valueOf(s);

		return ret;
	}

	public static void main(String[] args) {
		double d = 3423423233.1415926D;
		float f = 2.71828F;
		int i = 123456;

		System.out.println(NumberFormatUtil.format(d));
		System.out.println(NumberFormatUtil.format(f));
		System.out.println(NumberFormatUtil.format(i));
	}
}
