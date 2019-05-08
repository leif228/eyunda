package com.hangyi.eyunda.pay.pinanpay;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

public class StringUtil {

	/**
	 * 将字符串分割然后以键值对存入 LinkHashMap
	 * 
	 * @param s
	 * @return
	 */
	public static Map<String, String> splitStringToMap(String s) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String[] temp = splitRequest(s); // 将字符串拆分成一维数组
		for (int i = 0; i < temp.length; i++) {
			String[] arr = temp[i].split("="); // 继续分割并存到另一个一临时的一维数组当中
			String value;
			if (arr.length == 1) {
				value = "";
			} else if (arr.length > 2) {
				value = temp[i].substring(arr[0].length() + 1);
			} else {
				value = arr[1];
			}
			map.put(arr[0], value);
		}
		return map;
	}

	/**
	 * 将字符串分割然后以键值对存入 LinkHashMap
	 * 
	 * @param s
	 * @return
	 */
	public static Map<String, String[]> splitStringToMapArray(String s) {
		LinkedHashMap<String, String[]> map = new LinkedHashMap<String, String[]>();
		String[] temp = splitRequest(s); // 将字符串拆分成一维数组
		for (int i = 0; i < temp.length; i++) {
			String[] arr = temp[i].split("="); // 继续分割并存到另一个一临时的一维数组当中
			String value;
			if (arr.length == 1) {
				value = "";
			} else if (arr.length > 2) {
				value = temp[i].substring(arr[0].length() + 1);
			} else {
				value = arr[1];
			}
			value = value.replace("[", "").replace("]", "").replace("{", "").replace("}", "");
			String[] itemList = value.split(",");
			map.put(arr[0], itemList);
		}
		return map;
	}

	/**
	 * Map转NameValuePair[]
	 * 
	 * @param map
	 * @return
	 */
	public static NameValuePair[] mapToNameValuePair(Map<String, String> map) {
		NameValuePair[] para = new NameValuePair[map.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			para[i++] = new NameValuePair(entry.getKey(), entry.getValue());
		}
		return para;
	}

	/**
	 * Map转NameValuePair[]
	 * 
	 * @param map
	 * @return
	 */
	/**
	 * Map转NameValuePair[]
	 * 
	 * @param map
	 * @return
	 */
	public static NameValuePair[] mapArrayToNameValuePair(Map<String, String[]> map) {
		List<String[]> list = new ArrayList<String[]>();
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			for (String value : entry.getValue()) {
				list.add(new String[] { entry.getKey(), value });
			}
		}
		NameValuePair[] para = new NameValuePair[list.size()];
		int i = 0;
		for (String[] nameValue : list) {
			para[i++] = new NameValuePair(nameValue[0], nameValue[1]);
		}
		return para;
	}

	/**
	 * 将NameValuePairs数组转变为字符串
	 * 
	 * @param nameValues
	 * @return
	 */
	public static String nameValuePairToString(NameValuePair[] nameValues) {
		if (nameValues == null || nameValues.length == 0) {
			return null;
		}

		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < nameValues.length; i++) {
			NameValuePair nameValue = nameValues[i];
			if (nameValue == null) {
				continue;
			}

			if (i == 0) {
				buffer.append(nameValue.getName() + "=" + nameValue.getValue());
			} else {
				buffer.append("&" + nameValue.getName() + "=" + nameValue.getValue());
			}
		}

		return buffer.toString();
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String mapArrayToString(Map<String, String[]> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String[] values = params.get(key);
			StringBuffer value = new StringBuffer();
			if (values[0].indexOf("commodityNo=") != -1) {// 判断字段值是产品信息，yfj
				value.append("[");
				for (int j = 0; j < values.length; j++) {
					String val = values[j];
					value.append("{");
					value.append(val);
					if (j == values.length - 1) {
						value.append("}");
					} else {
						value.append("},");
					}
				}
				value.append("]");
			} else {
				value.append(values[0]);
			}

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key.replace("[]", "") + "=" + value.toString();
			} else {
				prestr = prestr + key.replace("[]", "") + "=" + value.toString() + "&";
			}
		}

		return prestr;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String mapToString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	public static String objectToString(Object object, Class clazz) throws IllegalArgumentException,
			IllegalAccessException, ClassNotFoundException {
		StringBuffer sb = new StringBuffer();
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				field.setAccessible(true);
				if ("serialVersionUID".equals(field.getName())) {
					continue;
				}
				if (field.get(object) == null) {
					continue;
				}
				if (sb.length() != 0) {
					sb.append("&");
				}
				sb.append(field.getName());
				sb.append("=");
				if (field.getType().equals(Date.class)) {
					sb.append(DateUtils.getHttpDateStr((Date) field.get(object)));
				} else if (field.getType().equals(List.class)) {
					sb.append("[");
					List list = (List) field.get(object);
					String genTypeStr = field.getGenericType().toString();
					if (genTypeStr.contains("<")) {
						int start = genTypeStr.indexOf("<");
						int end = genTypeStr.indexOf(">");
						genTypeStr = genTypeStr.substring(start + 1, end);
						Class genType = Class.forName(genTypeStr);
						for (int i = 0; i < list.size(); i++) {
							String str = objectToString(list.get(i), genType);
							if (i == 0) {
								sb.append("{");
							} else {
								sb.append(",{");
							}
							sb.append(str);
							sb.append("}");
						}
					} else {
						for (int i = 0; i < list.size(); i++) {
							if (i == 0) {
								sb.append("{");
							} else {
								sb.append(",{");
							}
							sb.append(list.get(i).toString());
							sb.append("}");
						}
					}
					sb.append("]");
				} else {
					sb.append(field.get(object).toString());
				}
			}
		}
		// if(clazz.getSuperclass() != null &&
		// !clazz.getSuperclass().equals(Object.class)){
		// String superStr = objectToString(object, clazz.getSuperclass());
		// if(superStr != null && superStr.length() > 0){
		// sb.append("&").append(superStr);
		// }
		// }
		return sb.toString();
	}

	public static Object stringToObject(String str, Class clazz) throws IllegalArgumentException,
			IllegalAccessException, InstantiationException, SecurityException, ClassNotFoundException {
		Object object = clazz.newInstance();
		String[] temp = splitRequest(str); // 将字符串拆分成一维数组
		for (int i = 0; i < temp.length; i++) {
			String[] arr = temp[i].split("="); // 继续分割并存到另一个一临时的一维数组当中
			String value;
			if (arr.length == 1) {
				value = "";
			} else if (arr.length > 2) {
				value = temp[i].substring(arr[0].length() + 1);
			} else {
				value = arr[1];
			}
			Field field = null;
			try {
				field = clazz.getDeclaredField(arr[0]);
			} catch (NoSuchFieldException e) {
				continue;
			}
			field.setAccessible(true);
			if (field.getType().isPrimitive()) {
				if ("boolean".equals(field.getType().getName())) {
					field.setBoolean(object, Boolean.parseBoolean(value));
				}
				if ("byte".equals(field.getType().getName())) {
					field.setByte(object, Byte.parseByte(value));
				}
				if ("char".equals(field.getType().getName())) {
					field.setChar(object, value.toString().charAt(0));
				}
				if ("double".equals(field.getType().getName())) {
					field.setDouble(object, Double.parseDouble(value));
				}
				if ("float".equals(field.getType().getName())) {
					field.setFloat(object, Float.parseFloat(value));
				}
				if ("int".equals(field.getType().getName())) {
					field.setInt(object, Integer.parseInt(value));
				}
				if ("long".equals(field.getType().getName())) {
					field.setLong(object, Long.parseLong(value));
				}
				if ("short".equals(field.getType().getName())) {
					field.setShort(object, Short.parseShort(value));
				}
			} else {
				// 如果类型不在下列语句中,请自行维护
				if (field.getType().equals(Date.class)) {
					Date date = DateUtils.getHttpDate(value);
					field.set(object, date);
				} else if (field.getType().equals(BigDecimal.class)) {
					BigDecimal bigDecimal = new BigDecimal(value);
					field.set(object, bigDecimal);
				} else if (field.getType().equals(List.class)) {
					if (value.startsWith("[") && value.endsWith("]")) {
						value = value.replace("[", "").replace("]", "");
						List list = new ArrayList();
						String[] itemList = value.split(",");
						if (itemList.length > 0) {
							for (String item : itemList) {
								item = item.replace("{", "").replace("}", "");
								String genType = field.getGenericType().toString();
								if (genType.contains("<")) {
									int start = genType.indexOf("<");
									int end = genType.indexOf(">");
									genType = genType.substring(start + 1, end);
									Object obj = stringToObject(item, Class.forName(genType));
									list.add(obj);
								} else {
									list.add(item);
								}
							}
						}
						field.set(object, list);
					}

				} else if (field.getType().equals(Long.class)) {
					Long l = new Long(value);
					field.set(object, l);
				} else {
					field.set(object, value);
				}
			}
		}

		return object;
	}

	public static String[] splitRequest(String request) {
		if (request == null || request.length() == 0) {
			return null;
		}
		if (!request.contains("[")) {
			return request.split("&");
		}
		StringBuffer sb = new StringBuffer();
		int i = request.indexOf("=");
		String key = request.substring(0, i);
		sb.append(key).append("=");
		request = request.substring(i + 1, request.length());
		String value = "";
		int j = 0;
		if (request.startsWith("[")) {
			int l = 0;
			int r = 0;
			for (j = 0; j < request.length(); j++) {
				if (request.charAt(j) == '[') {
					l++;
				}
				if (request.charAt(j) == ']') {
					r++;
				}
				if (l != 0 && l == r) {
					break;
				}
			}
			j++;
		} else {
			j = request.indexOf("&");
		}
		value = request.substring(0, j);
		sb.append(value);
		if (j < request.length()) {
			request = request.substring(j + 1, request.length());
		} else {
			request = "";
		}
		String[] reqs = splitRequest(request);
		if (reqs != null) {
			// String[] retReqs = Arrays.copyOf(reqs, reqs.length + 1);
			String[] retReqs = null;
			System.arraycopy(reqs, 0, retReqs, 0, Math.min(reqs.length, reqs.length + 1));
			retReqs[retReqs.length - 1] = sb.toString();
			return retReqs;
		} else {
			String[] retReqs = new String[1];
			retReqs[0] = sb.toString();
			return retReqs;
		}
	}

	// 测试
	public static void main(String args[]) throws IllegalArgumentException, IllegalAccessException, SecurityException,
			InstantiationException, NoSuchFieldException, ClassNotFoundException {
		Test test = new Test();
		test.setB(new BigDecimal(1000));
		test.setD(new Date());
		test.setI(true);
		test.setS("aaa");
		List<SubClass> list = new ArrayList<SubClass>();
		SubClass sc = new SubClass();
		sc.setA("a1");
		sc.setB(1);
		list.add(sc);
		sc = new SubClass();
		sc.setA("a2");
		sc.setB(2);
		list.add(sc);
		test.setList(list);

		String str = objectToString(test, Test.class);
		System.out.println(str);

		Test object = (Test) stringToObject(str, Test.class);
		System.out.println(object.getS());
		System.out.println(object.getB());
		System.out.println(object.getD());
		System.out.println(object.isI());
		System.out.println(object.getList().get(0).getA());
		System.out.println(object.getList().get(0).getB());
		System.out.println(object.getList().get(1).getA());
		System.out.println(object.getList().get(1).getB());

	}

}

class Test {
	private List<SubClass> list;
	private String s;
	private Date d;
	private BigDecimal b;
	private boolean i;

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public Date getD() {
		return d;
	}

	public void setD(Date d) {
		this.d = d;
	}

	public BigDecimal getB() {
		return b;
	}

	public void setB(BigDecimal b) {
		this.b = b;
	}

	public boolean isI() {
		return i;
	}

	public void setI(boolean i) {
		this.i = i;
	}

	public List<SubClass> getList() {
		return list;
	}

	public void setList(List<SubClass> list) {
		this.list = list;
	}

}

class SubClass {
	private String a;
	private int b;

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

}
