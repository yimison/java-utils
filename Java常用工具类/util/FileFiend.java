/**
 * Program  : FileFiend.java
 * Author   : niehai
 * Create   : 2009-4-23 ����02:20:51
 *
 * Copyright 2007 by Embedded Internet Solutions Inc.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Embedded Internet Solutions Inc.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with Embedded Internet Solutions Inc.
 *
 */

package cn.ipanel.apps.portalBackOffice.util;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;




/**
 * @since
 * @author niehai
 * @2009-4-23 ����02:20:51
 */
public class FileFiend {
	public static final String FILE_SEPARATOR = System
			.getProperty("file.separator");

	private final static Logger logger = Logger.getLogger(FileFiend.class);

	/**
	 * ɾ��ָ�����ļ��С�
	 * 
	 * @param directoryPath
	 * @return �Ƿ�ɹ� �ɹ�=true.
	 */
	public static boolean deleteDirectory(String directoryPath) {
		File file;
		File[] files;
		file = new File(directoryPath);
		if (file != null) {
			if (file.exists() && file.isDirectory()) {
				files = file.listFiles();
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						if (files[i].exists()) {
							if (files[i].isDirectory()) {
								deleteDirectory(files[i].getPath());
							} else if (files[i].isFile()) {
								boolean isHad = files[i].delete();
								if (!isHad) {
									logger
											.info("delete file <<<<<<<faile>>>>>>>:"
													+ files[i]);
									return false;
								}
							}
						}
					}
					file.delete();
				}
			} else if (!file.exists() && file.isDirectory()) {
				logger.debug("directory do not exists!:" + directoryPath
						+ " at _File.deleteDirectory()");
				return true;
			}
		} else {
			logger.debug("file Object is null!:path=" + directoryPath);
			return false;
		}
		return true;
	}

	/**
	 * ɾ��ָ�����ļ�
	 * 
	 * @param filePath
	 * @return int:1=delete sucess;0=delete <<<<<<<faile>>>>>>>;2=no such
	 *         file;
	 */
	public static int deleteFile(String filePath) {
		File file = new File(filePath);
		if (file != null) {
			if (file.exists()) {
				if (file.delete()) {
					logger.debug("delete the file sucess: " + filePath);
					return 1;
				} else {
					logger.debug("delete the file <<<<<<<faile>>>>>>>: "
							+ filePath);
					return 0;
				}
			} else {
				logger.debug("this file do not existe :" + filePath);
				return 2;
			}
		} else {
			logger.debug("file Object is null!:path=" + filePath);
			return 2;
		}

	}

	/**
	 * ��String�д����ļ���������ļ����ڸ��ǵ�����������ڴ������ļ���
	 * 
	 * @param filePath
	 * @param fileData
	 * @return д�ļ��Ƿ�ɹ�.
	 * @throws IOException
	 */
	public static boolean writeFile(String filePath, String fileData,
			String charsetName) {
		if (filePath == null || fileData == null) {
			logger.debug("the fileName or fileData is null: fileName="
					+ filePath + " fileData=" + fileData);
			return false;
		} else if (filePath.equals("") || filePath.trim().equals("")) {
			logger.debug("the fileName or fileData is   : fileName=" + filePath
					+ " fileData=" + fileData);
			return false;
		}
		FileOutputStream fileOutputStream = null;
		try {
			byte[] data = fileData.getBytes(charsetName);
			File file = new File(filePath);
			if (!file.exists()) {
				logger.debug("this file is not exist!:" + filePath);
				file.createNewFile();
				logger.debug("creat file!:" + filePath);
			}
			fileOutputStream = new FileOutputStream(filePath);
			fileOutputStream.write(data);
			fileOutputStream.close();
			logger.debug("write file:" + filePath);
			return true;
		} catch (FileNotFoundException e) {
			logger.debug(e.getMessage());
		} catch (IOException e) {
			logger.debug(e.getMessage());
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					logger.debug(e.toString());
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * ��byte[]������д���ļ���������ļ����ڸ��ǵ�����������ڴ������ļ���
	 * 
	 * @param filePath
	 *            �ļ�ȫ·��.
	 * @param fileData
	 *            �ļ�����.
	 * @return д�ļ��Ƿ�ɹ�.
	 */
	public static boolean writeFile(String filePath, byte[] fileData) {
		if (filePath == null || fileData == null) {
			logger.debug("filePath or fileData is null");
			return false;
		} else if (filePath.trim().equals("")) {
			logger.debug("filePath is \"\"!");
			return false;
		}
		FileOutputStream write;
		try {
			write = new FileOutputStream(filePath);
			write.write(fileData);
			write.close();
			logger.debug("write file:" + filePath + " success!");
			return true;
		} catch (FileNotFoundException e) {
			logger.debug(e.getMessage());
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
		return false;
	}

	/**
	 * ��String�д����ļ���������ļ����ڸ��ǵ�����������ڴ������ļ�,����ļ�·���ϵ�Ŀ¼û���򴴽���Ŀ¼��
	 * 
	 * @param filePath
	 * @param fileData
	 * @return д�ļ��Ƿ�ɹ�.
	 * @throws IOException
	 */
	public static boolean directWriteFile(String filePath, String fileData,
			String charsetName) {
		if (filePath == null || fileData == null) {
			logger.debug("the fileName or fileData is null: fileName="
					+ filePath + " fileData=" + fileData);
			return false;
		} else if (filePath.equals("") || filePath.trim().equals("")) {
			logger.debug("the fileName or fileData is   : fileName=" + filePath
					+ " fileData=" + fileData);
			return false;
		}
		String fileDir = filePath.substring(0, filePath.lastIndexOf(System
				.getProperty("file.separator")));
		boolean flag = makeDirectory(fileDir);
		if (!flag) {
			return false;
		}
		FileOutputStream fileOutputStream = null;
		try {
			byte[] data = fileData.getBytes(charsetName);
			File file = new File(filePath);
			if (!file.exists()) {
				logger.debug("this file is not exist!:" + filePath);
				file.createNewFile();
				logger.debug("creat file!:" + filePath);
			}
			fileOutputStream = new FileOutputStream(filePath);
			fileOutputStream.write(data);
			fileOutputStream.close();
			logger.debug("write file:" + filePath);
			return true;
		} catch (FileNotFoundException e) {
			logger.debug(e.getMessage());
		} catch (IOException e) {
			logger.debug(e.getMessage());
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					logger.debug(e.toString());
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * ��byte[]������д���ļ���������ļ������򸲸ǵ�����������ڴ������ļ�,Ŀ¼������Ҳֱ�Ӵ�����Ŀ¼��
	 * 
	 * @param filePath
	 *            �ļ�ȫ·��.
	 * @param fileData
	 *            �ļ�����.
	 * @return д�ļ��Ƿ�ɹ�.
	 */
	public static boolean directWriteFile(String filePath, byte[] fileData) {
		if (filePath == null || fileData == null) {
			logger.debug("filePath or fileData is null");
			return false;
		} else if (filePath.trim().equals("")) {
			logger.debug("filePath is \"\"!");
			return false;
		}
		String fileDir = filePath.substring(0, filePath.lastIndexOf(System
				.getProperty("file.separator")));
		boolean flag = makeDirectory(fileDir);
		if (!flag) {
			return false;
		}
		FileOutputStream write;
		try {
			write = new FileOutputStream(filePath);
			write.write(fileData);
			write.close();
			logger.debug("write file:" + filePath + " success!");
			return true;
		} catch (FileNotFoundException e) {
			logger.debug(e.getMessage());
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
		return false;
	}

	/**
	 * ��ָ�����ļ���ѹ����ָ�����ļ��У���ѹ����ļ���Ŀ¼�͸�����ѹ���ļ�����ͬ.
	 * 
	 * @param zipFilePath
	 *            ȫ·��
	 * @param unZipDirectory
	 *            ȫ·��
	 * @return ��ѹ���ļ��Ƿ�ɹ�.
	 * @throws IOException
	 */
	public static boolean unZipFile(String zipFilePath, String unZipDirectory)
			throws IOException {
		ZipFile zipFile = new ZipFile(zipFilePath);
		Enumeration entries = zipFile.entries();
		if (zipFile == null) {
			return false;
		}
		while (entries.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) entries.nextElement();
			File f = new File(unZipDirectory + FILE_SEPARATOR
					+ zipEntry.getName());
			if (zipEntry.isDirectory()) {
				if (!f.exists() && !f.mkdirs())
					throw new IOException("Couldn't create directory: " + f);
			} else {
				BufferedInputStream is = null;
				BufferedOutputStream os = null;
				try {
					is = new BufferedInputStream(zipFile
							.getInputStream(zipEntry));
					File destDir = f.getParentFile();
					if (!destDir.exists() && !destDir.mkdirs()) {
						throw new IOException("Couldn't create dir " + destDir);
					}
					os = new BufferedOutputStream(new FileOutputStream(f));
					int b = -1;
					while ((b = is.read()) != -1) {
						os.write(b);
					}
				} finally {
					if (is != null)
						is.close();
					if (os != null)
						os.close();
				}
			}
		}
		zipFile.close();
		return true;
	}
	
	/**
	 * ��ָ�����ļ���ѹ����ָ�����ļ��У���ѹ����ļ���Ŀ¼�͸�����ѹ���ļ�����ͬ.
	 * 
	 * @param zipFilePath
	 *            ȫ·��
	 * @param unZipDirectory
	 *            ȫ·��
	 * @return ��ѹ���ļ��Ƿ�ɹ�.
	 * @throws IOException
	 */
	public static boolean unZipFile1(String zipFilePath, String unZipDirectory)
			throws IOException {
		ZipFile zipFile = new ZipFile(zipFilePath);
		Enumeration entries = zipFile.entries();
		if (zipFile == null) {
			return false;
		}
		while (entries.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) entries.nextElement();
			File f = new File(unZipDirectory + FILE_SEPARATOR
					+ ((String)zipEntry.getName()).substring(((String)zipEntry.getName()).indexOf("/")+1));
			if (zipEntry.isDirectory()) {
				if (!f.exists() && !f.mkdirs())
					throw new IOException("Couldn't create directory: " + f);
			} else {
				BufferedInputStream is = null;
				BufferedOutputStream os = null;
				try {
					is = new BufferedInputStream(zipFile
							.getInputStream(zipEntry));
					File destDir = f.getParentFile();
					if (!destDir.exists() && !destDir.mkdirs()) {
						throw new IOException("Couldn't create dir " + destDir);
					}
					os = new BufferedOutputStream(new FileOutputStream(f));
					int b = -1;
					while ((b = is.read()) != -1) {
						os.write(b);
					}
				} finally {
					if (is != null)
						is.close();
					if (os != null)
						os.close();
				}
			}
		}
		zipFile.close();
		return true;
	}

	/**
	 * ���������ļ�cut��ָ����ȫ·���������·���������Զ�������·����
	 * 
	 * @param filePath
	 *            ȫ·����
	 * @param toDirectory
	 *            ȫ·����
	 * @return �Ƿ�ɹ� �ɹ�==true.
	 */
	public static boolean cutFile(String filePath, String toDirectory) {
		if (filePath == null || toDirectory == null) {
			logger
					.info("the filePath or toDirectory is null ! when cut this file: "
							+ filePath + "---- to:" + toDirectory);
			return false;
		} else if (filePath.trim().equals("") || toDirectory.trim().equals("")) {
			logger
					.info("the filePath or toDirectory is \"\"! when cut this file: "
							+ filePath + "---- to:" + toDirectory);
			return false;
		}
		if (copyFile(filePath, toDirectory)) {
			int delte = deleteFile(filePath);
			if (delte == 1) {
				return true;
			} else {
				logger
						.info("copy the file sucess form "
								+ filePath
								+ "----to:"
								+ toDirectory
								+ " but delete this file <<<<<<<faile>>>>>>> when cut this file.");
				return false;
			}
		} else {
			logger.debug("copy the file <<<<<<<faile>>>>>>> form " + filePath
					+ "----to:" + toDirectory + " when cut this file.");
			return false;
		}
	}

	/**
	 * ��ָ�����ļ���cut��ָ����·���£������·�������ڣ����ܹؼ���·����
	 * 
	 * @param directory
	 *            ȫ·��
	 * @param toDirectory
	 *            ȫ·��
	 * @return �Ƿ�ɹ� �ɹ�==true.
	 */
	public static boolean cutDirectory(String directory, String toDirectory) {
		if (copyDirectory(directory, toDirectory)) {
			boolean isDelete = deleteDirectory(directory);
			if (isDelete) {
				logger.debug("cut directory success: form"
						+ " directory ---- to " + toDirectory);
				return true;
			} else {
				logger
						.info("copy directory sucess but delete <<<<<<<faile>>>>>>> when cut the directory form"
								+ " directory ---- to " + toDirectory);
				return false;
			}
		} else {
			return false;
		}

	}

	/**
	 * �ж����ļ�ϵͳ���Ƿ���ڴ��ļ���
	 * 
	 * @param filePath
	 *            ȫ·��
	 * @return �ļ���ϵͳ��=true.
	 */
	public static boolean isHave(String filePath) {
		if (filePath == null) {
			logger
					.info("not know file isHave .filePath is null! at _File.isHave");
			return true;
		} else if (filePath.trim().equals("")) {
			logger
					.info("not know file isHave . filePath is \"\"! at _File.isHave");
			return true;
		}
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * ��ָ�����ļ�copy��ָ����·���£������·�������ڽ�������·����
	 * 
	 * @param filePath
	 *            ȫ·��
	 * @param toDirectoryPath
	 *            ȫ·��
	 * @return �Ƿ�ɹ� �ɹ�==true.
	 */
	public static boolean copyFile(String filePath, String toDirectoryPath) {
		if (filePath == null || toDirectoryPath == null) {
			logger
					.info("the filePath or toDirectory is null at _File.copyFile()");
			return false;
		} else if (filePath.trim().equals("")
				|| toDirectoryPath.trim().equals("")) {
			logger
					.info("the filePath or toDirectory is \"\" at _File.copuFile()");
			return false;
		}
		File directory = new File(toDirectoryPath);
		if (!directory.exists()) {
			makeDirectory(toDirectoryPath);
		}

		File file = new File(filePath);
		String toFilePath = toDirectoryPath + FILE_SEPARATOR + file.getName();
		File toFile = new File(toFilePath);
		try {
			if (!file.isFile()) {
				logger.debug("can not get FileInputStream from this file:"
						+ filePath);
				return false;
			}
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(toFile);
			byte[] data = new byte[fis.available()];
			int bytesRead;
			while ((bytesRead = fis.read(data)) != -1) {
				fos.write(data, 0, bytesRead);
			}
			fos.flush();
			fos.close();
			fis.close();
			logger
					.info("copy file file:" + file + "-----to:"
							+ toDirectoryPath);
			return true;
		} catch (FileNotFoundException e) {
			logger.debug(e.getMessage());
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
		return false;
	}

	/**
	 * ��ָ�����ļ�copy��ָ����·���£������·�������ڽ�������·����
	 * 
	 * @param sfilePath
	 *            ȫ·��
	 * @param toDirectoryPath
	 *            ȫ·��
	 * @return �Ƿ�ɹ� �ɹ�==true.
	 */
	public static boolean copyFileByFilePath(String sfilePath, String dfilePath) {
		if (sfilePath == null || dfilePath == null) {
			logger.info("the filePath is null at _File.copyFile()");
			return false;
		} else if (sfilePath.trim().equals("") || dfilePath.trim().equals("")) {
			logger.info("the filePath is \"\" at _File.copuFile()");
			return false;
		}
		String toFileDir = dfilePath.substring(0, dfilePath.lastIndexOf(System
				.getProperty("file.separator")));
		boolean flag = makeDirectory(toFileDir);
		if (!flag) {
			return false;
		}
		File file = new File(sfilePath);
		File toFile = new File(dfilePath);
		try {
			if (!file.isFile()) {
				logger.debug("can not get FileInputStream from this file:"
						+ sfilePath);
				return false;
			}
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(toFile);
			byte[] data = new byte[fis.available()];
			int bytesRead;
			while ((bytesRead = fis.read(data)) != -1) {
				fos.write(data, 0, bytesRead);
			}
			fos.flush();
			fos.close();
			fis.close();
			logger.info("copy file file:" + file + "-----to:" + dfilePath);
			return true;
		} catch (FileNotFoundException e) {
			logger.debug(e.getMessage());
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
		return false;
	}

	/**
	 * ��ָ�����ļ���copy��ָ����·���£����ָ�����ƶ���·�������ڣ�������·��;ָ����·�����ڽ�����.
	 * 
	 * @param directoryPath
	 *            ȫ·��
	 * @param toDirectoryPath
	 *            ȫ·��
	 * @return �Ƿ�ɹ� �ɹ�==true.
	 */
	public static boolean copyDirectory(String directoryPath,
			String toDirectoryPath) {
		File file;
		File[] files;
		file = new File(directoryPath);
		if (file.exists() && file.isDirectory()) {
			files = file.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].exists()) {
						if (files[i].isDirectory()) {
							copyDirectory(files[i].getPath(), toDirectoryPath
									+ FILE_SEPARATOR + files[i].getName());
						} else if (files[i].isFile()) {
							copyFile(files[i].getPath(), toDirectoryPath);
						}
					}
				}
			}
		} else {
			logger.debug("file not exists or is not directory!");
			return false;
		}
		logger.debug("copy directory form:" + directoryPath + "to :"
				+ toDirectoryPath + ".");
		return true;
	}

	/**
	 * �Ӹ������ַ����д����ļ�ϵͳ·����
	 * 
	 * @param directory
	 *            ������·����ʾ�ַ�����
	 * @return �Ƿ�ɹ� �ɹ�==true.
	 */
	public static boolean makeDirectory(String directory) {
		File file = new File(directory);
		if (!file.exists()) {
			if (file.mkdirs()) {
				logger.debug("make dirctory success!:" + directory);
				return true;
			} else {
				logger.debug("make dirctory <<<<<<<faile>>>>>>>!:" + directory);
				return false;
			}
		} else {
			logger.debug("this directory is existed!:" + directory);
			return true;
		}
	}

	/**
	 * ���ļ��ж������ݷŵ�String��.
	 * 
	 * @param filePath
	 *            �ļ�·��.
	 * @param encoding
	 *            �����ʽ.
	 * @return �ļ���String.
	 */
	public static String readFile(String filePath, String encoding) {
		String contents = null;
		FileInputStream fissrc = null;
		try {
			fissrc = new FileInputStream(filePath);
			int len = fissrc.available();
			byte[] data = new byte[len];
			int actual = 0;
			int bytesread = 0;
			while ((bytesread != len) && (actual != -1)) {
				actual = fissrc.read(data, bytesread, len - bytesread);
				bytesread += actual;
			}
			contents = new String(data, encoding);
			fissrc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fissrc != null){
				try {
					fissrc.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return contents;
	}

	/**
	 * ������е��ļ���.
	 * 
	 * @param directoryPath
	 * @return
	 */
	public static List getAllFiles(String directoryPath) {
		List allFiles = new ArrayList();
		if (!isHave(directoryPath)) {
			return allFiles;
		} else {
			File file = new File(directoryPath);
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					allFiles.add(files[i].getName());
				}
				return allFiles;
			} else {
				return allFiles;
			}
		}
	}

	/**
	 * �������ļ����ļ��еػ�ȡ���С.
	 * 
	 * @param path
	 *            �ļ������ļ��еĴ�С.
	 * @return long ���͵Ĵ�С bytes
	 * @throws FileNotFoundException
	 * @since 2006.4.22
	 */
	public static long getSize(String path) throws FileNotFoundException {
		File file = new File(path);
		if (file.isFile()) {
			return getFileSize(path);
		} else {
			return getDirectorySize(path, 0);
		}
	}

	/**
	 * ����һ���ļ��Ĵ�С.
	 * 
	 * @param filePath
	 *            �ļ���ȫ·��.
	 * @return long bytes value �ļ���С.
	 * @throws FileNotFoundException
	 * @since 2006.4.22
	 */
	public static long getFileSize(String filePath)
			throws FileNotFoundException {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new FileNotFoundException("this file is not exited: "
					+ filePath);
		}
		return file.length();
	}

	/**
	 * ����һ���ļ��еĴ�С.
	 * 
	 * @param directoryPath
	 *            �ļ��е�ȫ·��.
	 * @return �ļ��д�С.
	 * @throws FileNotFoundException
	 * @since 2006.4.22
	 */
	public static long getDirectorySize(String directoryPath)
			throws FileNotFoundException {
		return getDirectorySize(directoryPath, 0);
	}

	/**
	 * �ݹ�Ļ�ȡ�ļ��е�ÿһ���ļ��Ĵ�С,�������ܵĴ�С.
	 * 
	 * @param directoryPath
	 *            �ļ��д�С.
	 * @param size
	 *            �ݹ����
	 * @return long bytes value;
	 * @throws FileNotFoundException
	 * @since 2006.4.22
	 */
	private static long getDirectorySize(String directoryPath, long size)
			throws FileNotFoundException {
		File file = new File(directoryPath);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			String filePath = files[i].getAbsolutePath();
			if (files[i].isDirectory()) {
				size = getDirectorySize(filePath, size);
			} else {
				size += getFileSize(filePath);
			}
		}
		return size;
	}

	/**
	 * @author xiaobao
	 * @param filePath
	 * @return long bytes value;
	 * @since 1.0.0 ���ļ��ж������ݷŵ�byte��.
	 * @createTime 2006-11-06 13:50:20
	 */
	public static byte[] readFileByByte(String filePath) {
		byte[] data = null;
		FileInputStream fissrc;
		try {
			fissrc = new FileInputStream(filePath);
			int len = fissrc.available();
			data = new byte[len];
			int actual = 0;
			int bytesread = 0;
			while ((bytesread != len) && (actual != -1)) {
				actual = fissrc.read(data, bytesread, len - bytesread);
				bytesread += actual;
			}
			fissrc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * ɾ���ǿ�Ŀ¼
	 * 
	 * @param dir
	 * @throws IOException
	 * @author lixiang
	 * @create 2008-8-30 ����12:46:18
	 * @since
	 */
	public static void deleteDirectory(File dir) throws IOException {
		if ((dir == null) || !dir.isDirectory()) {
			throw new IllegalArgumentException("Argument " + dir
					+ " is not a directory. ");
		}

		File[] entries = dir.listFiles();
		int sz = entries.length;

		for (int i = 0; i < sz; i++) {
			if (entries[i].isDirectory()) {
				deleteDirectory(entries[i]);
			} else {
				entries[i].delete();
			}
		}

		dir.delete();
	}

	/**
	 * ��׽��Ļ
	 * 
	 * @param fileName
	 * @author lixiang
	 * @param top
	 * @param left
	 * @create 2008-8-30 ����08:03:10
	 * @since
	 */
	public static void snapShotsf(String fileName, int left, int top) {
		try {
			BufferedImage screenshot = (new Robot())
					.createScreenCapture(new Rectangle(left, top, 640, 526));
			String name = fileName + ".jpg";
			File f = new File(name);
			ImageIO.write(screenshot, "jpg", f);

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	/**
	 * I֡תͼƬ
	 * 
	 * @param mpgFilePath
	 *            MPG���ļ�·�� �磺d:/test/s22.MPG
	 * @param targetFilePath
	 *            ���ɵ�jpg�ļ�·��������ļ����ļ��б������ �磺d:/abc/t22.jpg��d:/abc�������
	 * @param processerFolder
	 *            �ļ��У������д����ļ�ת���õ���dll �ļ�,exe�ļ� �磺d:/abc/mpg2jpg (��windows�»��õ�)
	 * @return
	 * @author lixiang
	 * @create 2008-12-17 ����05:54:14
	 */
	public static boolean processJPG(String mpgFilePath, String targetFilePath,
			String processerFolder, int height, int width) {

		String osName = System.getProperty("os.name");
		logger.debug("current os.name: " + osName);

		if (osName.indexOf("Windows") >= 0) {
			mpgFilePath = reverse(mpgFilePath, false);
			targetFilePath = reverse(targetFilePath, false);
			processerFolder = reverse(processerFolder, false);
			FileWriter fw = null;
			String directory = processerFolder.substring(0, 1);
			try {
				fw = new FileWriter(processerFolder + "/process.bat");
				fw.write("@echo off\r\n");
				fw.write(directory + ":\r\n");
				fw.write("cd\\"
						+ processerFolder.substring(processerFolder
								.indexOf(":/") + 2) + "\r\n");
				fw.write("ffmpeg -i " + "mpg"
						+ mpgFilePath.substring(mpgFilePath.lastIndexOf("/"))
						+ " -y -f image2 -t 0.001 -s " + width + "x" + height
						+ " " + targetFilePath + "\r\n");
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// ����s22.MPG�� processerFolder ��mpg��
			String sfilePath = mpgFilePath;
			String dfilePath = processerFolder + "/mpg"
					+ mpgFilePath.substring(mpgFilePath.lastIndexOf("/"));
			dfilePath = reverse(dfilePath, true);
			deleteFile(dfilePath);
			copyFileByFilePath(sfilePath, dfilePath);

			String cmd = "cmd.exe /c " + directory + ":/"
					+ processerFolder.substring(2) + "/process.bat";
			try {
				Runtime.getRuntime().exec(cmd);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			processJPGLinux(mpgFilePath, targetFilePath, processerFolder,
					height, width);
		}
		logger.debug("conver MPG to JPG sucess: " + targetFilePath);
		return true;
	}

	/**
	 * I֡תͼƬ
	 * 
	 * @param mpgFilePath
	 *            MPG���ļ�·�� �磺d:/test/s22.MPG
	 * @param targetFilePath
	 *            ���ɵ�jpg�ļ�·��������ļ����ļ��б������ �磺d:/abc/t22.jpg��d:/abc�������
	 * @param processerFolder
	 *            �����ļ�ת���õ���dll �ļ�,exe�ļ�
	 * @return
	 * @author lixiang
	 * @create 2008-12-17 ����05:54:14
	 */
	private static void processJPGLinux(String mpgFilePath,
			String targetFilePath, String processerFolder, int height, int width) {
		int p = targetFilePath.split("/")[(targetFilePath.split("/").length) - 1]
				.length();
		int g = targetFilePath.length();
		String ggg = targetFilePath.substring(0, g - p - 1);
		FileWriter fw = null;
		try {
			fw = new FileWriter(ggg + "/www.sh");
			fw.write("#!/bin/bash\n");
			fw.write("cd " + processerFolder + "\n");
			fw.write("ffmpeg -i " + mpgFilePath + " -y -f image2 -t 0.001 -s "
					+ width + "x" + height + " " + targetFilePath);
			fw.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String cmd = "/bin/sh " + ggg + "/www.sh";
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * б���滻
	 * 
	 * @param sourceStr
	 * @param lineType
	 *            true : ȫת��Ϊ"\"; false ȫת��Ϊ"/"
	 * @return
	 */
	private static String reverse(String sourceStr, boolean lineType) {
		if (lineType) {
			Pattern pattern = Pattern.compile("/", Pattern.DOTALL);
			Matcher matcher = pattern.matcher(sourceStr);
			sourceStr = matcher.replaceAll("\\\\"); // ת�����
		} else {
			Pattern pattern = Pattern.compile("\\\\", Pattern.DOTALL);
			Matcher matcher = pattern.matcher(sourceStr);
			sourceStr = matcher.replaceAll("/"); // ת�����
		}
		return sourceStr;
	}
	
	
	/**
	 * @param filePath
	 *            String �ļ�·��
	 * @return byte[]
	 * @author zhuogf
	 * @create 2006-12-12 17:58:00
	 * @since ��ָ�����ļ��ж�����Ϣ������byte[]��
	 */
	public static byte[] readFileByte(String filePath) {
		byte[] data = null;
		FileInputStream fissrc;
		try {
			fissrc = new FileInputStream(filePath);
			int len = fissrc.available();
			data = new byte[len];
			int actual = 0;
			int bytesread = 0;
			while ((bytesread != len) && (actual != -1)) {
				actual = fissrc.read(data, bytesread, len - bytesread);
				bytesread += actual;
			}			
			fissrc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}
		
	
	public byte[] doDownload(String fileName)  
	   {  
	       FileInputStream fis;  
	       byte[] data =null;  
	       FileChannel fc; 	  
	       try {  
	           fis = new FileInputStream(System.getProperty("java.io.tmpdir") + "/" + fileName);  
	           fc = fis.getChannel();  
	           data = new byte[(int)(fc.size())];  
	           ByteBuffer bb = ByteBuffer.wrap(data);  
	           fc.read(bb);  
	       } catch (FileNotFoundException e) {  	
	    	   e.printStackTrace();
	       } catch (IOException e) {  
	           e.printStackTrace(); 
	       }  
	       return data;  
	   }  

	
	
	/**
	 * �ж��ļ���׺���Ƿ�Ϊzip,rar
	 */
	public static boolean judgeFileZip(String fileName) {
		String[] zip = { "zip", "ziP", "zIp", "zIP", "Zip", "ZiP", "ZIp", "ZIP" };		
		String type = fileName.substring(fileName.lastIndexOf(".")+1, fileName
				.length());
		if(fileName.indexOf(".")==-1)return false;
		boolean b = false;
		for (int i = 0; i < zip.length; i++) {
			if (type.equals(zip[i])) {
				b = true;
				break;
			}
		}		
		return b;
	}

	public static void main(String[] args) {
	//	FileFiend.deleteFile("E:/tomcat-5.5.27/webapps/PortalBackOffice/zipfile/qw.zip");
	//	FileFiend.deleteDirectory("E:/tomcat-5.5.27/webapps/PortalBackOffice/zipfile/xxxxx_1.0");
		String portalname = "xxxxxxx_1.0";
		String[] st = portalname.split("\\.");
		int j = Integer.parseInt(st[1]);
		logger.info(j);
		logger.info(st[0]);
	}
}
