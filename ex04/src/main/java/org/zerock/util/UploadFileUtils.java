package org.zerock.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadFileUtils {

	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);
	
	/* 파일 경로 */
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception{
		
		return null;
		
	}
	
	/* 파일 경로 적용 */
	private static String calcPath(String uploadPath) {
		
		Calendar cal = Calendar.getInstance();
		
		String yearPath = File.pathSeparator + cal.get(Calendar.YEAR);	// file.pathSeparator = 파일구분자
		
		String monthPath = yearPath + File.pathSeparator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);	// Calendar.MONTH 는 0부터 시작 (1월은 0)
		
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		makeDir(uploadPath, yearPath, monthPath, datePath);
		
		logger.info(datePath);
		
		return datePath;
		
	}
	
	/* 파일 경로 생성 */
	private static void makeDir(String uploadPath, String...paths) {
		
		if(new File(uploadPath + paths[paths.length - 1]).exists()) {
			return;
		}
		
		for(String path : paths) {
			File dirPath = new File(uploadPath + path);
			if(!dirPath.exists()) {
				dirPath.mkdir();
			}
		}
	}
	
	/* 썸네일 생성 */
	private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception{
		
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));
		
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
		
		String thumbnailName = uploadPath + path + File.pathSeparator + "s_" + fileName;
		
		File newFile = new File(thumbnailName);
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
		
	}
}
