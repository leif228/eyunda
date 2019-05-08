package com.hangyi.eyunda.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class StringZipUtil {
	public StringZipUtil() {
	}

	public static void main(String[] args) throws Exception {
		String a = "JREAP/GetResourceInfoServlet?checkedPathList=计划统计/dfdf/dffsa/删除,运行系统/值班记事,计划统计/数据查询/删除,设备管理/设备检修,计划统计/报表管理/统计期间/台帐,运行系统/定期工作,设备管理,计划统计/dfdf/fdasfasdf,设备管理/流程处理/待办事宜,设备管理/设备备品配件2/增加,设备管理/台帐管理/电缆台帐维护,运行系统/定期工作/定期工作记录,设备管理/流程处理/设备异动,运行系统/值班记事/值班记录查询,计划统计,系统管理/组织及人员管理/组织管理,计划统计/指标趋势比较/删除,计划统计/数据查询/修改,运行系统/运行日志,系统管理/数据字典管理/删除,个性化设置,运行系统/定期工作/定期工作查询,计划统计/dfdf/aaaaa,运行系统/排班表,运行系统/运行台帐,系统管理/角色及角色组管理/角色及角色组管理,计划统计/dfdf/fdasfasdf/增加,设备管理/设备备品配件2/删除,运行系统/用户手册,计划统计/dfdf/fdasfasdf/删除,运行系统/值班记事/交接班,计划统计/指标趋势比较,设备管理/设备备品配件/历史备品管理,计划统计/指标趋势比较/增加,系统管理/模块管理,系统管理/数据字典管理/增加分类,运行系统/值班记事/关键字设置,设备管理/设备备品配件2/修改,计划统计/报表管理/统计期间/公共报表,计划统计/dfdf/dffsa/修改,运行系统/值班记事/值班记录,系统管理/数据字典管理/删除分类,计划统计/数据查询/增加,系统管理/数据字典管理/增加,系统管理/角色及角色组管理,系统管理/数据字典管理/修改,计划统计/报表管理/统计期间/自定义报表,设备管理/流程处理,运行系统,运行系统/当值设置,设备管理/设备检修/检修总结,计划统计/报表管理,设备管理/台帐管理,设备管理/设备备品配件/最新备品管理,系统管理/操作日志/aaa,设备管理/设备检修/检修计划,系统管理/数据字典管理,运行系统/定期工作/应填报告设置,系统管理/操作日志,设备管理/流程处理/设备消缺管理,设备管理/设备备品配件2,设备管理/设备检修/技改竣工报告,计划统计/指标趋势比较/修改,运行系统/技术管理,运行系统/定期工作/应填报告查询,计划统计/dfdf,运行系统/定期工作/定期工作设置,计划统计/报表管理/统计期间,计划统计/dfdf/dffsa/增加,设备管理/设备备品配件,计划统计/dfdf/dffsa,计划统计/数据查询,运行系统/班组管理,设备管理/台帐管理/设备信息维护,系统管理/数据字典管理/修改分类,计划统计/dfdf/fdasfasdf/修改,系统管理/组织及人员管理,系统管理,";
		String b = zip(a);
		String c = unzip(b);
		System.out.println("之前字符串:" + a);
		System.out.println("之后字符串:" + b);
		System.out.println("还原字符串:" + c);
		System.out.println("之前长度:" + a.length());
		System.out.println("之后长度:" + b.length());
		// byte[] abyte = a.getBytes();
		// byte[] testbyte1 = null;
		// byte[] testbyte2 = null;
		// testbyte1 = zip(abyte);
		// String b=new sun.misc.BASE64Encoder().encode(testbyte1);
		// byte[] testbyte3=new sun.misc.BASE64Decoder().decodeBuffer(b);
		//
		// System.out.println("之前长度:"+abyte.length);
		// System.out.println("之后长度:"+testbyte1.length);
		// // 处理1
		// testbyte2 = unzip(testbyte3);
		//
		// // 处理2
		// // byte[]<->String<->byte[]
		// // String b = new String(testbyte1);
		// // byte[] testbyte3 = b.getBytes();
		// // testbyte2 = unzip(testbyte3);
		//
		// System.out.println(new String(testbyte2));
	}

	public static byte[] unzip(byte[] zipBytes) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(zipBytes);
		// String entryName = new String("zip");
		ZipInputStream zis = new ZipInputStream(bais);
		zis.getNextEntry();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final int BUFSIZ = 4096;
		byte inbuf[] = new byte[BUFSIZ];
		int n;
		while ((n = zis.read(inbuf, 0, BUFSIZ)) != -1) {
			baos.write(inbuf, 0, n);
		}
		byte[] data = baos.toByteArray();
		zis.close();
		return data;
	}

	public static byte[] zip(byte[] data) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipEntry ze = new ZipEntry("zip");
		ZipOutputStream zos = new ZipOutputStream(baos);
		zos.putNextEntry(ze);
		zos.write(data, 0, data.length);
		zos.close();
		byte[] zipBytes = baos.toByteArray();
		return zipBytes;
	}

	/**
	 * 压缩
	 * 
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("deprecation")
	public static String zip(String data) throws Exception {
		System.out.println("压缩之前:" + data);
		byte[] abyte = data.getBytes();
		byte[] testbyte1 = zip(abyte);
		String b = Base64.encode(new String(testbyte1));
		b = URLEncoder.encode(b);
		System.out.println("压缩之后:" + b);
		return b;
	}

	/**
	 * 解压缩
	 * 
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("deprecation")
	public static String unzip(String data) throws Exception {
		System.out.println("解压数据:" + data);
		data = URLDecoder.decode(data);
		byte[] testbyte1 = Base64.decode(data).getBytes();
		byte[] testbyte2 = unzip(testbyte1);

		String b = new String(testbyte2);
		System.out.println("解压后数据:" + b);
		return b;
	}

	/**
	 * dwr调用
	 * 
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public String zip_dwr(String data) throws Exception {
		return zip(data);
	}

}
