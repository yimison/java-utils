/**
 * Program  : ZipExtractor.java
 * Author   : laihm
 * Create   : 2009-5-4 ����03:20:34
 *
 * Copyright 2009 by iPanel Technologies ltd.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of iPanel Technologies ltd.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with iPanel Technologies ltd.
 *
 */

package cn.ipanel.apps.portalBackOffice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import cn.ipanel.apps.portalBackOffice.util.Base64Fiend;
import cn.ipanel.apps.portalBackOffice.util.CommonsFiend;

/**
 * 
 * @author   laihm
 * @version  1.0.0
 * @2009-5-4 ����03:20:34
 */
public class ZipExtractor {
	
	/**
	 * Task:��ȡѹ���ļ����ļ��б�
	 * @author laihm
	 * @create 2009-5-15 ����06:44:36
	 * @since 
	 * @param prePathǰ��·��
	 * @param zipNamezip�ļ���
	 * @param folder�ļ�������
	 * @return
	 * @throws FileNotFoundException���������ļ�ʱ�׳����쳣
	 * @throws IOException��������쳣
	 */
	public static Map getFileStrMapByPathFolder(String prePath,String zipName,String folder) throws FileNotFoundException,IOException{
		if(FileFiend.judgeFileZip(zipName)){
			File f=new File(prePath+FileFiend.FILE_SEPARATOR+zipName);
			Map map=new HashMap();
			ZipInputStream zipInput=new ZipInputStream(new FileInputStream(f));
			ZipEntry zipEntry = null;
			while ((zipEntry = zipInput.getNextEntry()) != null&&zipEntry.getName().startsWith(zipName.substring(0,zipName.indexOf("."))+"/")){
				if(isInFolder(zipEntry,folder)){
					int len = new Long(zipEntry.getSize()).intValue();
					String fullName = suffix2LowerCase(zipEntry.getName());
					if(fullName.endsWith("Thumbs.db"))continue;
					byte[] data = new byte[len];
					int actual = 0;
					int bytesread = 0;
					while ((bytesread != len) && (actual != -1)) {
						actual = zipInput.read(data, bytesread, len
								- bytesread);
						bytesread += actual;
					}
					String fileStr;
					if(isPicFile(fullName)){
						fileStr = Base64Fiend.encode(data);
					}else{
						fileStr= new String(data);
					}
					map.put(fullName,fileStr);
				}	
			}
			zipInput.close();
			return map;
		}
		return null;
	}
	
	/**
	 * ��ȡѹ���ļ������е�ͼƬ
	 * @author tianwl
	 * @param prePath
	 * @param zipName
	 * @param folder
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Map getImgStrMapByPathFolder(String prePath,String zipName,String folder) throws FileNotFoundException,IOException{
		if(FileFiend.judgeFileZip(zipName)){
			File f=new File(prePath+FileFiend.FILE_SEPARATOR+zipName);
			Map map=new HashMap();
			ZipInputStream zipInput=new ZipInputStream(new FileInputStream(f));
			ZipEntry zipEntry = null;
			while ((zipEntry = zipInput.getNextEntry()) != null&&zipEntry.getName().startsWith(zipName.substring(0,zipName.indexOf("."))+"/")){
				if(isInFolder(zipEntry,folder)){
					int len = new Long(zipEntry.getSize()).intValue();
					String fullName = suffix2LowerCase(zipEntry.getName());
					if(fullName.endsWith("Thumbs.db"))continue;
					byte[] data = new byte[len];
					int actual = 0;
					int bytesread = 0;
					while ((bytesread != len) && (actual != -1)) {
						actual = zipInput.read(data, bytesread, len
								- bytesread);
						bytesread += actual;
					}
					String fileStr;
					if(isPicFile(fullName)){
						fileStr = Base64Fiend.encode(data);
						map.put(fullName,fileStr);
					}
				}	
			}
			zipInput.close();
			return map;
		}
		return null;
	}
	
	private static String suffix2LowerCase(String fileName){
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
		fileName = fileName.substring(0,fileName.lastIndexOf(".")+1) + suffix.toLowerCase();
		return  fileName;
	}
	
	/**
	 * Task:�жϵ�ǰ zipEntry �Ƿ�����ָ����folder�£����û���޶�folder�������е��ļ�������
	 * @author laihm
	 * @create 2009-5-5 ����09:52:11
	 * @since 
	 * @param zipEntry
	 * @param folder
	 * @return
	 */
	private static boolean isInFolder(ZipEntry zipEntry,String folder){
		if(zipEntry.isDirectory()){
			return false;
		}
		if("".equals(folder)){  //���û���޶�folder�������е��ļ�������
			return true;
		}
		String fullName=zipEntry.getName();
		String tmp=folder+"/";
		if(fullName.startsWith(tmp)/*&&fullName.substring(tmp.length()).indexOf("/")==-1*/)
			return true;
		return false;
	}
	
	/**
	 * Task:�ж��Ƿ���ͼƬ�ļ�
	 * @author huangfei
	 * @create 2008-11-22 ����10:47:25
	 * @since 
	 * @param fileName
	 * @return
	 */
	private static boolean isPicFile(String fileName){
		return CommonsFiend.validateImgFormat(fileName);
	}
	
	public static String getZipRootFolder(String prePath,String zipName) throws FileNotFoundException,IOException{
		if(FileFiend.judgeFileZip(zipName)){
			File f=new File(prePath+FileFiend.FILE_SEPARATOR+zipName);
			ZipInputStream zipInput=new ZipInputStream(new FileInputStream(f));
			ZipEntry zipEntry = null;
			while ((zipEntry = zipInput.getNextEntry()) != null&&zipEntry.getName().startsWith(zipName.substring(0,zipName.indexOf("."))+"/")){
				if(zipEntry.getName().indexOf("/")!=-1){
					zipInput.close();
					return zipEntry.getName().substring(0, zipEntry.getName().indexOf("/"));
				}
			}
			zipInput.close();
		}
		return null;
	} 
	
	public static void main(String[] args){
//		System.out.println(null+"abc");
//		try{
//			System.out.println(getZipRootFolder("D:\\tomcat-5.0.28\\webapps\\Portal\\uploadTempDir\\1101877681","tvgame(virtual).zip"));
//			Map map=getFileStrMapByPathFolder("D:\\tomcat-5.0.28\\webapps\\Portal\\uploadTempDir\\1101877681","tvgame(virtual).zip","");
//			Iterator ite=map.entrySet().iterator();
//			while(ite.hasNext()){
//				Map.Entry entry=(Map.Entry)ite.next();
//				System.out.println(entry.getKey());
//			}
//		}catch(FileNotFoundException ex){
//			ex.printStackTrace();
//		}catch(IOException ex){
//			ex.printStackTrace();
//		}
		
		String str = "language  Spec ific ation";
		String str2 = str.replaceAll(" ", "");

		System.out.println(str2);


	}
}

