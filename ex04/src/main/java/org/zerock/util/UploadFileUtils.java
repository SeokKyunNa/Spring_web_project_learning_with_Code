package org.zerock.util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadFileUtils {

	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);
	
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception{
		
		return null;
		
	}
	
	private static String calcPath(String uploadPath) {
		
		Calendar cal = Calendar.getInstance();
		
		String yearPath = File.pathSeparator + cal.get(Calendar.YEAR);	// file.pathSeparator = 파일구분자
		
		String monthPath = yearPath + File.pathSeparator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);	// Calendar.MONTH 는 0부터 시작 (1월은 0)
		
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		makeDir(uploadPath, yearPath, monthPath, datePath);
		
		logger.info(datePath);
		
		return datePath;
		
	}
	
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
}
