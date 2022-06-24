package com.online.module.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.log4j.Logger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUtil implements Serializable {

	private static final long serialVersionUID = -5125202281572327162L;
	private static final Logger logger = Logger.getLogger(FileUtil.class);
	
	private static FileUtil me;
	
	public static final String CHAR_COMMA = ",";

	public static FileUtil getInstance() {
		if (FileUtil.me == null) {
			FileUtil.me = new FileUtil();
		}
		
		return FileUtil.me;
	}
	
	private FileUtil() {
		
	}
	
	public String handleFileUpload(InputStream is, String oldFileName, String newFolderDir, String newFileNameWithoutExtension) throws IOException {
		String fullFileName = "";
		
		if (!newFolderDir.contains(System.getProperty("file.separator")))
			newFolderDir = newFolderDir.concat(System.getProperty("file.separator"));
		
		File folder = new File(newFolderDir);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		folder = null;
		
		if (newFileNameWithoutExtension == null || newFileNameWithoutExtension.isEmpty())
			fullFileName = newFolderDir.concat(oldFileName);
		else
			fullFileName = newFolderDir.concat(newFileNameWithoutExtension).concat(".").concat(getExtention(oldFileName));
		
		writeFile(is, fullFileName);
		
		if (newFileNameWithoutExtension != null) {
			fullFileName = newFileNameWithoutExtension.concat(".").concat(getExtention(oldFileName));
		}
		
		return fullFileName;
	}
	
	public void writeFile(final InputStream is, final String fullFileName) throws FileNotFoundException, IOException
	{
		File fileExist = new File(fullFileName);
		if (!fileExist.exists()) {
			FileOutputStream fos = new FileOutputStream(fullFileName);
			int BUFFER_SIZE = 8192;
			byte[] buffer = new byte[BUFFER_SIZE];
			int a;
			while (true) {
				a = is.read(buffer);
				if (a < 0) 
					break;
				fos.write(buffer, 0, a);
				fos.flush();
			}
			fos.close();
			is.close();
		}
		
		fileExist = null;
	}
	
	public static String getExtention(String fileName) {
		String ext = "";
		
		int dotPos = 0;
		if (fileName.contains("."))
			dotPos = fileName.lastIndexOf(".");
		if (dotPos > 0) 
			ext = fileName.substring(dotPos + 1);
		
		return ext;
	}
	
	public void deleteFile(String filePath) throws Exception {
		if (filePath != null && !filePath.isEmpty()) {
			File temp = new File(filePath);
			if (temp.exists()) {
				boolean success = temp.delete();
				if (!success) {
					throw new IOException("Fail to Delete File");
				}
			}
			temp = null;
		}
	}
	
	public static byte[] readBytesFromFile(File file) {
		FileInputStream fileInputStream = null;
		byte[] bytesArrys = null;
		
		try {
			bytesArrys = new byte[(int) file.length()];
			
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytesArrys);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		return bytesArrys;
	}

	public static FileUtil getMe() {
		return me;
	}

	public static void setMe(FileUtil me) {
		FileUtil.me = me;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static String getCharComma() {
		return CHAR_COMMA;
	}
	
}
