package com.hangyi.eyunda.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import javax.media.MediaLocator;

import org.jim2mov.core.DefaultMovieInfoProvider;
import org.jim2mov.core.ImageProvider;
import org.jim2mov.core.Jim2Mov;
import org.jim2mov.core.MovieInfoProvider;
import org.jim2mov.utils.MovieUtils;

public class ImgToMovUtil {
	/**
	 * @param sourceDir 
	 * 			源目录放置jpg图片
	 * @param mmsi 
	 * 			船舶唯一标识
	 * @return  
	 *          生成的avi文件
	 */
	public static File getAviByImgs(File sourceDir, String mmsi){
		
		try {
			if(sourceDir!=null&&sourceDir.isDirectory()){
				final File[] jpgs = sourceDir.listFiles();

				// 对文件名进行排序(文件名中的数字越小,生成视频的帧数越靠前)
				Arrays.sort(jpgs, new Comparator<File>() {
					public int compare(File file1, File file2) {
						String numberName1 = file1.getName().replace(".jpg", "");
						String numberName2 = file2.getName().replace(".jpg", "");
						return new Integer(numberName1) - new Integer(numberName2);
					}
				});
				
				String url = Constants.AVI_DIR + File.separator + mmsi + File.separator + sourceDir.getName() + ".avi";  //生成视频的名称=sourceDir.getName()+".avi"
				MediaLocator ml = new MediaLocator((new StringBuilder()).append("file:").append(url).toString());
				
				DefaultMovieInfoProvider dmip = new DefaultMovieInfoProvider(ml);
				dmip.setFPS(8); // 设置每秒帧数
				dmip.setNumberOfFrames(jpgs.length); // 总帧数
				//视频宽和高，最好与图片宽高保持一直
				dmip.setMWidth(320);
				dmip.setMHeight(288);

				new Jim2Mov(new ImageProvider() {
					public byte[] getImage(int frame) {
						try {
							// 设置压缩比，视频文件大小
							return MovieUtils.convertImageToJPEG((jpgs[frame]), 0.5f);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return null;
					}
				}, dmip, null).saveMovie(MovieInfoProvider.TYPE_AVI_MJPEG);
				
				return new File(url);
				
			}else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
