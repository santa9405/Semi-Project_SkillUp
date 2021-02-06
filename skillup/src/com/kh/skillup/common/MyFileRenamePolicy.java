package com.kh.skillup.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy {

	@Override
	public File rename(File originalFile) {
		
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
		
		int random = (int) (Math.random() * 100000);
 		String ranStr = "_" + String.format("%05d", random); 
 		
 		int dot = originalFile.getName().lastIndexOf(".");
 		String ext = ""; 
 		
 		if(dot != -1)
 			ext = originalFile.getName().substring(dot);
 		
 		String filaName = ft.format(new Date(currentTime)) + ranStr + ext;
 		
		return new File(originalFile.getParent(), filaName);
	}
}
