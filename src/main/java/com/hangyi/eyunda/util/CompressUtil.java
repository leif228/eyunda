package com.hangyi.eyunda.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * zlib压缩与解压缩
 */
public class CompressUtil {
	private static int cachesize = 1024;

	public static void compress(byte[] data, OutputStream os) {
		DeflaterOutputStream v0 = new DeflaterOutputStream(os);
		try {
			v0.write(data, 0, data.length);
			v0.finish();
			v0.flush();
		} catch (IOException v1) {
			v1.printStackTrace();
		}
	}

	public static byte[] compressBytes(byte input[]) {
		Deflater compresser = new Deflater();

		compresser.reset();
		compresser.setInput(input);
		compresser.finish();
		byte output[] = new byte[0];
		ByteArrayOutputStream o = new ByteArrayOutputStream(input.length);
		try {
			byte[] buf = new byte[cachesize];
			int got;
			while (!compresser.finished()) {
				got = compresser.deflate(buf);
				o.write(buf, 0, got);
			}
			output = o.toByteArray();
		} finally {
			try {
				o.close();
				compresser.end();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

	public static byte[] decompress(InputStream is) {
		InflaterInputStream v3 = new InflaterInputStream(is);
		ByteArrayOutputStream v4 = new ByteArrayOutputStream(1024);
		try {
			byte[] buf = new byte[cachesize];
			while (true) {
				int got = v3.read(buf, 0, cachesize);
				if (got <= 0) {
					break;
				}

				v4.write(buf, 0, got);
			}
		} catch (IOException v1) {
			v1.printStackTrace();
		}

		return v4.toByteArray();
	}

	public static byte[] decompressBytes(byte input[]) {
		Inflater decompresser = new Inflater();

		byte output[] = new byte[0];
		decompresser.reset();
		decompresser.setInput(input);
		ByteArrayOutputStream o = new ByteArrayOutputStream(input.length);
		try {
			byte[] buf = new byte[cachesize];

			int got;
			while (!decompresser.finished()) {
				got = decompresser.inflate(buf);
				o.write(buf, 0, got);
			}
			output = o.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				o.close();
				decompresser.end();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

	public static String getDecompressData(String data) {
		String result = "";
		try {
			result = new String(CompressUtil.decompressBytes(data.getBytes("ISO-8859-1")), "UTF-8");
		} catch (UnsupportedEncodingException v0) {
			v0.printStackTrace();
		}

		return result;
	}

	public static void main(String[] args) throws Exception {
		String v0 = new String(CompressUtil.compressBytes("蜡笔小新".getBytes("UTF-8")), "ISO-8859-1");
		System.out.println("压缩后的字符串:" + v0);
		System.out.println("解压后的字符串:" + new String(CompressUtil.decompressBytes(v0.getBytes("ISO-8859-1")), "UTF-8"));
	}
}
