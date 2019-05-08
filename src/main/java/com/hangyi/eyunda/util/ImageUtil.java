package com.hangyi.eyunda.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * 图片工具类， 图片水印，文字水印，缩放，补白等
 */
public final class ImageUtil {
	private static Logger log = Logger.getLogger(ImageUtil.class);

	/** 图片格式：JPG */
	private static final String PICTRUE_FORMATE_JPG = "jpg";

	private ImageUtil() {
	}

	/**
	 * 添加图片水印
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param waterImg
	 *            水印图片路径，如：C://myPictrue//logo.png
	 * @param x
	 *            水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y
	 *            水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 */
	public final static void pressImage(String targetImg, String waterImg, int x, int y, float alpha) {
		try {
			File file = new File(targetImg);
			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);

			Image waterImage = ImageIO.read(new File(waterImg)); // 水印文件
			int width_1 = waterImage.getWidth(null);
			int height_1 = waterImage.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

			int widthDiff = (width_1 + x > width) ? width - x : width_1;
			int heightDiff = (height_1 + y > height) ? height - y : height_1;

			g.drawImage(waterImage, x, y, widthDiff, heightDiff, null); // 水印文件结束
			g.dispose();
			ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
		} catch (IOException e) {
			log.error("图片添加图片水印错误", e);
		}
	}

	/**
	 * 添加文字水印
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param pressText
	 *            水印文字， 如：中国证券网
	 * @param fontName
	 *            字体名称， 如：宋体
	 * @param fontStyle
	 *            字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
	 * @param fontSize
	 *            字体大小，单位为像素
	 * @param color
	 *            字体颜色
	 * @param x
	 *            水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y
	 *            水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 * @param position
	 *            0 左上 1 左下 2 右上 3 右下
	 * @param qualNum
	 *            图片质量
	 */
	public static void pressText(String targetImg, String pressText, String fontName, int fontStyle, int fontSize,
			Color color, int x, int y, float alpha, int position, float qualNum) {

		try {
			File file = new File(targetImg);
			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			int width_1 = fontSize * getLength(pressText);
			int height_1 = fontSize;
			if (width >= x + width_1 && height > y + height_1) {
				BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = bufferedImage.createGraphics();
				g.drawImage(image, 0, 0, width, height, null);
				g.setFont(new Font(fontName, fontStyle, fontSize));
				g.setColor(color);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

				int widthDiff = width - width_1;
				int heightDiff = height - height_1;
				if (x < 0) {
					x = widthDiff / 2;
				} else if (position == 2) {
					x = widthDiff - x;
				} else if (position == 3) {
					x = widthDiff - x;
				}

				if (y < 0) {
					y = heightDiff / 2;
				} else if (position == 0) {
					y = height_1 - 6;
				} else if (position == 1) {
					y = heightDiff - y + height_1 - 1;
				} else if (position == 2) {
					y = height_1 - 6;
				} else if (position == 3) {
					y = heightDiff - y + height_1 - 1;
				}

				g.drawString(pressText, x, y);
				g.dispose();
				ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
				// FileOutputStream out=new FileOutputStream(targetImg);
				// JPEGImageEncoder encoder =JPEGCodec.createJPEGEncoder(out);
				// JPEGEncodeParam param =
				// encoder.getDefaultJPEGEncodeParam(bufferedImage);
				// param.setQuality(qualNum, true);
				// encoder.encode(bufferedImage, param);
				// out.close();
			}
		} catch (Exception e) {
			log.error("图片添加文字水印错误", e);
		}
	}

	public static void pressText(String targetImg, String pressText) {
		String fontName = "Times New Romas";
		int fontStyle = Font.PLAIN;
		int fontSize = 22;
		Color color = Color.BLUE;
		int x = 0;
		int y = 5;
		float alpha = 1f;
		int position = 3;
		float qualNum = 100f;
		pressText(targetImg, pressText, fontName, fontStyle, fontSize, color, x, y, alpha, position, qualNum);
	}

	/**
	 * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
	 * 
	 * @param text
	 * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
	 */
	public static int getLength(String text) {
		int textLength = text.length();
		int length = textLength;
		for (int i = 0; i < textLength; i++) {
			if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
				length++;
			}
		}
		return (length % 2 == 0) ? length / 2 : length / 2 + 1;
	}

	/**
	 * 图片缩放
	 * 
	 * @param filePath
	 *            图片路径
	 * @param height
	 *            高度
	 * @param width
	 *            宽度
	 * @param bb
	 *            比例不对时是否需要补白
	 */
	public static void resize(String filePath, int height, int width, boolean bb) {
		try {
			double ratio = 0; // 缩放比例
			File f = new File(filePath);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);

			// 计算比例
			if (bi.getHeight() > bi.getWidth()) {
				ratio = (new Integer(height)).doubleValue() / bi.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue() / bi.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
			itemp = op.filter(bi, null);

			if (bb) {
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null),
							itemp.getHeight(null), Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null),
							itemp.getHeight(null), Color.white, null);
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, "jpg", f);
		} catch (IOException e) {
			log.error("图片缩放错误", e);
		}
	}

	public static boolean isImage(String name) {
		Pattern p = Pattern.compile(".+\\.(gif|jpeg|jpg|png)");
		Matcher m = p.matcher(name.toLowerCase());
		return m.matches();
	}

	public static void makeRoomLogo(String roomLogo, String logo_bg, String[] logoPaths) throws IOException {
		int[][] p = { { 0, 0 },

				{ 1, 68 }, { 136, 68 },

				{ 68, 1 }, { 1, 136 }, { 136, 136 },

				{ 1, 1 }, { 136, 1 }, { 1, 136 }, { 136, 136 },

				{ 31, 31 }, { 151, 31 }, { 1, 151 }, { 91, 151 }, { 181, 151 },

				{ 1, 31 }, { 91, 31 }, { 181, 31 }, { 1, 151 }, { 91, 151 }, { 181, 151 },

				{ 31, 1 }, { 151, 1 }, { 31, 91 }, { 151, 91 }, { 1, 181 }, { 91, 181 }, { 181, 181 },

				{ 31, 1 }, { 151, 1 }, { 1, 91 }, { 91, 91 }, { 181, 91 }, { 1, 181 }, { 91, 181 }, { 181, 181 },

				{ 1, 1 }, { 91, 1 }, { 181, 1 }, { 1, 91 }, { 91, 91 }, { 181, 91 }, { 1, 181 }, { 91, 181 },
				{ 181, 181 } };

		FileUtils.copyFile(new File(logo_bg), new File(roomLogo));

		String tf = roomLogo.substring(0, roomLogo.lastIndexOf(".")) + "_temp.jpg";

		int n = logoPaths.length;
		if (n > 9)
			n = 9;

		int a = 0;
		for (int i = 0; i < n; i++)
			a += i;

		int w = 90;
		if (n == 1)
			w = 270;
		else if (n == 2 || n == 3 || n == 4)
			w = 135;
		else
			w = 90;

		for (int i = 0; i < n; i++) {
			FileUtils.copyFile(new File(logoPaths[i]), new File(tf));

			ImageUtil.resize(tf, w, w, false);

			ImageUtil.pressImage(roomLogo, tf, p[a + i][0], p[a + i][1], 1.0f);

			FileUtil.deleteFile(tf);
		}

	}

	public static void makeShipLogo(String shipLogo, String lineRentLogo, String timeRentLogo, String waterImg)
			throws IOException {
		FileUtils.copyFile(new File(shipLogo), new File(lineRentLogo));
		ImageUtil.resize(lineRentLogo, 270, 270, false);

		FileUtils.copyFile(new File(lineRentLogo), new File(timeRentLogo));
		ImageUtil.pressImage(timeRentLogo, waterImg, 0, 0, 1.0f);
	}

	public static void main(String[] args) throws IOException {
		String rootPath = "/Users/xuguangzhong/temp/share/doc/default/test";
		String logo_bg = rootPath + "/malik_bg.jpg";

		String[] logoPaths = { rootPath + "/timeRent.jpg", rootPath + "/malik_c2.jpg", rootPath + "/malik_c3.jpg",
				rootPath + "/malik_s1.jpg", rootPath + "/malik_s2.jpg", rootPath + "/malik_s3.jpg",
				rootPath + "/malik_z1.jpg", rootPath + "/malik_z2.jpg", rootPath + "/malik_z3.jpg" };

		List<String> lstLogoPaths = Arrays.asList(logoPaths);

		for (int i = 0; i < 9; i++) {
			String[] a = new String[i + 1];
			List<String> subList = lstLogoPaths.subList(0, i + 1);
			subList.toArray(a);

			ImageUtil.makeRoomLogo(rootPath + "/logo/logo" + (i + 1) + ".jpg", logo_bg, a);
		}

		String rootPath1 = "/Users/xuguangzhong/temp/tt/registerForHuiyang.jpg";
		String rootPath2 = "/Users/xuguangzhong/temp/tt/eyunda98.jpg";
		String rootPath3 = "/Users/xuguangzhong/temp/tt/downloadForAndroid.jpg";

		ImageUtil.resize(rootPath1, 320, 320, false);
		ImageUtil.resize(rootPath2, 320, 320, false);
		ImageUtil.resize(rootPath3, 320, 320, false);
	}

}