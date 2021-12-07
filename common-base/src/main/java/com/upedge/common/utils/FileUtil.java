package com.upedge.common.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileUtil {
	
	public static void main(String[] args) throws IOException {
//		System.out.println(File.separator);
//		createFile("..\\module\\src\\main\\java\\com\\upedge\\service");

//		createFile("../module/src/main/java/com/upedge/service");
//		File file = new File("../consul/sss/eee/eer/dd.tst");
//		File folder = new File("../consul/sss/eee/eer");
//		folder.mkdirs();
//		file.createNewFile();
//		System.out.println("..\\module\\src\\main\\java\\com\\upedge\\service".contains("\\"));
//		System.out.println("..\\module\\src\\main\\java\\com\\upedge\\service".contains("\\\\"));
	}

	public static String uploadImage(String base64Data, String urlPrefix, String imgPath) {
		try {
			String dataPrix = "";
			String data = "";
			if (base64Data == null || "".equals(base64Data)) {
				throw new Exception("上传失败，上传图片数据为空");
			} else {
				String[] d = base64Data.split("base64,");
				if (d != null && d.length == 2) {
					dataPrix = d[0];
					data = d[1];
				} else {
					throw new Exception("上传失败，数据不合法");
				}
			}
			String suffix = "";
			if ("data:image/jpeg;".equalsIgnoreCase(dataPrix)) {//data:image/jpeg;base64,base64编码的jpeg图片数据
				suffix = ".jpg";
			} else if ("data:image/x-icon;".equalsIgnoreCase(dataPrix)) {//data:image/x-icon;base64,base64编码的icon图片数据
				suffix = ".ico";
			} else if ("data:image/gif;".equalsIgnoreCase(dataPrix)) {//data:image/gif;base64,base64编码的gif图片数据
				suffix = ".gif";
			} else if ("data:image/png;".equalsIgnoreCase(dataPrix)) {//data:image/png;base64,base64编码的png图片数据
				suffix = ".png";
			} else {
				throw new Exception("上传图片格式不合法");
			}

			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar calendar = Calendar.getInstance();
			String dateName = df.format(calendar.getTime());
			String tempFileName = urlPrefix + dateName + suffix;
			String localPath = imgPath + dateName + suffix;
			//因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
			byte[] bs = Base64Utils.decodeFromString(data);
			try {
				File file = new File(localPath);
				//使用apache提供的工具类操作流
				FileUtils.writeByteArrayToFile(file, bs);
			} catch (Exception ee) {
				ee.printStackTrace();
				throw new Exception("上传失败，写入文件失败，" + ee.getMessage());
			}
			return tempFileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void createFile(String fileName) throws IOException {
		if(fileName.contains("\\")) {
			fileName = fileName.replaceAll("\\\\", "/");
		}
		String[] folders = fileName.split("/");
		String path = "";
		for(int i = 0; i< folders.length-1; i++) {
			String folder = folders[i];
			path += folder+"/";
		}
		File pathFolders = new File(path);
		pathFolders.mkdirs();
		File file = new File(fileName);
		file.createNewFile();
	}
	
    public static void writer(String str, String filename) {
    	
    	File file = new File(filename);
        FileWriter fileWriter = null;
        
    	try {
    		if(!file.exists()) {
    			createFile(filename);
    		}
			fileWriter = new FileWriter(file,false);
	        fileWriter.write(str);
	        fileWriter.flush();
	        fileWriter.close();
	        
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try {
            	fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
    }

}
