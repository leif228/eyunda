package com.hangyi.eyunda.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtil {
	/**
	 * 求两个集合的交集
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static List<Object> intersection(Object[] array1, Object[] array2) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array1.length; i++) {
			Object tmp = array1[i];
			if (isExist(array2, tmp) >= 0) {
				list.add(tmp);
			}
		}
		return list;
	}

	/**
	 * 求并集
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static List<Object> union(Object[] array1, Object[] array2) {
		List<Object> list = new ArrayList<Object>();

		for (Object ele : array1)
			list.add(ele);

		for (Object ele : array2)
			if (isExist(array1, ele) < 0)
				list.add(ele);

		return list;
	}

	/**
	 * 判断指定的对象在集合中是否存在 如果存在，则返回该对象在集合中的索引
	 * 对象相等是根据equals方法判断,对象最好覆盖equals方法来确定对像是否相等
	 * 
	 * @param array
	 * @param obj
	 */
	public static int isExist(Object[] array, Object obj) {
		int iResult = -1;
		for (int i = 0; i < array.length; i++) {
			Object tmp = array[i];
			if (tmp.equals(obj)) {
				iResult = i;
				break;
			}

		}
		return iResult;
	}

	/**
	 * 對數組繼續排序
	 * 
	 * @param A
	 * @return
	 */
	public static String[] bubbleSort(String A[]) {
		int iAlength = A.length;
		int i = 0;
		int k = 0;
		String temp = "";
		while (i < iAlength) {
			for (k = i + 1; k < iAlength; k++) {
				if (A[k].compareToIgnoreCase(A[i]) < 0) {
					temp = A[i];
					A[i] = A[k];
					A[k] = temp;
				}
			}
			i++;
		}
		return A;
	}

	/**
	 * 
	 * @param array
	 * @return
	 */
	static String sortArray(String[] array) {
		String result = "";
		for (int i = 0; i < array.length; i++) {
			result += array[i] + " ";
		}
		return result;
	}
	// 从List中随机出count个对象
	public static <T> List<T> randomTopic(List<T> list, int count) {
		// 创建一个长度为count(count<=list)的数组,用于存随机数
		int[] a = new int[count];
		// 利于此数组产生随机数
		int[] b = new int[list.size()];
		int size = list.size();

		// 取样填充至数组a满
		for (int i = 0; i < count; i++) {
			int num = (int) (Math.random() * size); // [0,size)
			int where = -1;
			for (int j = 0; j < b.length; j++) {
				if (b[j] != -1) {
					where++;
					if (where == num) {
						b[j] = -1;
						a[i] = j;
					}
				}
			}
			size = size - 1;
		}
		// a填满后 将数据加载到rslist
		List<T> rslist = new ArrayList<T>();
		for (int i = 0; i < count; i++) {
			T df = (T) list.get(a[i]);
			rslist.add(df);
		}
		return rslist;
	}
	/**
	 * 測試部分
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String[] arr = { "本土項目", "外包項目" };
		System.out.println("Before: " + sortArray(arr));
		// 调用排序方法
		arr = bubbleSort(arr);
		System.out.println("After: " + sortArray(arr));
		
		String ArrayContent = "1&2&&3&4&5&";
		String[] arr2 = ArrayContent.split("\\&");
		for (int i = 0; i < arr2.length; i++) {
			System.out.println(arr2[i]);
		}
	}

}
