package com.hangyi.eyunda.util;

import java.util.TreeSet;

public class Test {
	private static final int BIT_LEN = 14;

	public static int min(String t) {
		int r = 999999999;
		for (int i = 0; i < t.length(); i++) {
			String s = "";
			if (i == 0)
				s = t;
			else
				s = t.substring(i) + t.substring(0, i);

			int n = Integer.parseInt(s, 2);

			if (n < r)
				r = n;
		}
		return r;
	}

	public static String turn(int n) {
		String s = Integer.toBinaryString(n);

		String zeros = "";
		for (int i = 0; i < Test.BIT_LEN - s.length(); i++)
			zeros += "0";

		s = zeros + s;

		return s;
	}

	public static void main(String[] args) {

		TreeSet<Integer> setResult = new TreeSet<Integer>();

		int size = new Double(Math.pow(2, Test.BIT_LEN)).intValue();

		for (int n = 0; n < size; n++) {
			String t = Test.turn(n);

			Integer key = Test.min(t);
			setResult.add(key);
		}

		System.out.println("size = " + setResult.size());
		for (Integer n : setResult) {
			System.out.println(Test.turn(n) + ":" + n);
		}

	}

}
