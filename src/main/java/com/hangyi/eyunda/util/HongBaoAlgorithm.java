package com.hangyi.eyunda.util;

import java.util.Random;

public class HongBaoAlgorithm {
	/**
	 * 
	 * @param remainSize 剩余的红包数量
	 * @param remainMoney 剩余的钱
	 * @return
	 */
	public static double getRandomMoney(int remainSize, Double remainMoney) {
	    // remainSize 剩余的红包数量
	    // remainMoney 剩余的钱
	    if (remainSize == 1) {
	        remainSize--;
	        return (double) Math.round(remainMoney * 100) / 100;
	    }
	    Random r = new Random();
	    double min   = 0.01; //
	    double max   = remainMoney / remainSize * 2;
	    double money = r.nextDouble() * max;
	    money = money <= min ? 0.01: money;
	    money = Math.floor(money * 100) / 100;
	    remainSize--;
	    remainMoney -= money;
	    return money;
	}
}