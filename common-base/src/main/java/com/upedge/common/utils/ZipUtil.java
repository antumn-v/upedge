package com.upedge.common.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	
	public static void zipFile() throws IOException {
		ZipOutputStream zipOut = null; // 声明压缩流对象
		zipOut = new ZipOutputStream(new FileOutputStream("../config/bbb.zip"));

		zipOut.putNextEntry(new ZipEntry("bbb/ddd/aaa.text")); // 设置ZipEntry对象
		zipOut.setComment("test"); // 设置注释
		
		byte[] content = "我是测试内容".getBytes();
		zipOut.write(content);
        zipOut.close() ;    // 关闭输出流  
	}

//	public static void main(String[] args) throws IOException {
//
//		File file = new File("d:" + File.separator + "mldn.txt") ;  // 定义要压缩的文件 
//        File zipFile = new File("d:" + File.separator + "mldn.zip") ;   // 定义压缩文件名称 
//        InputStream input = new FileInputStream(file) ; // 定义文件的输入流 
//        ZipOutputStream zipOut = null ; // 声明压缩流对象 
//        zipOut = new ZipOutputStream(new FileOutputStream(zipFile)) ; 
//        zipOut.putNextEntry(new ZipEntry(file.getName())) ; // 设置ZipEntry对象 
//        zipOut.setComment("www.mldnjava.cn") ;  // 设置注释 
//        int temp = 0 ; 
//        while((temp=input.read())!=-1){ // 读取内容 
//            zipOut.write(temp) ;    // 压缩输出 
//        } 
//        
//        input.close() ; // 关闭输入流 
//        zipOut.close() ;    // 关闭输出流  
//
//	}
}
