package com.mybuckstory.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class FileUtil{
	
	private static final Logger logger = Logger.getLogger(FileUtil.class);
	
	public static void save(byte[] data, String fileName){
		File file = new File(fileName);
		try{
			FileUtils.writeByteArrayToFile(file, data);
		}catch(IOException e){
			logger.error(e, e);
		}
		
	}

}
