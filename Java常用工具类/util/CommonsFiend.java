/**
 * Program  : CommonsFiend.java
 * Author   : niehai
 * Create   : 2008-5-14 ����05:05:54
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang.time.DateFormatUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import cn.ipanel.apps.portalBackOffice.define.*;

/**
 * @since
 * @author niehai
 * @2008-5-14 ����05:05:54
 */
public class CommonsFiend {

	/**
	 * @since ��ȡ�����
	 * @author niehai
	 * @create 2008-5-14 ����05:06:31
	 * @param len
	 * @return
	 */
	public static String getUniqueId(int len) {
		if (len < 10)
			len = 10;
		return getUniqueId(len, 999999999);
	}

	private static String getUniqueId(int length, int maxrandom) {
		String tmpstr = "";
		String thread = (new SimpleDateFormat("yyyyMMddhhmmssSSS"))
				.format(new Date())
				+ Integer.toString(getRandom(maxrandom));
		thread = Integer.toString(thread.hashCode());
		if (thread.indexOf("-") >= 0)
			thread = thread.substring(thread.indexOf("-") + 1);
		if (thread.length() < length) {
			for (int i = thread.length(); i < length; i++)
				tmpstr = tmpstr + "0";

			thread = tmpstr + thread;
		}
		return thread;
	}

	public static int getRandom(int max) {
		return (int) (Math.random() * (double) max);
	}

	public static Date stringToDate(String timeStr) {
		Date date = new Date();
		SimpleDateFormat apf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = apf.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;

	}

	public static String getCurrentDate() {
		return DateFormatUtils.format(Calendar.getInstance().getTime(),
				Defines.FORMAT_DATE_STRING);
	}
	
	
	public static String getCurrentDateTime() {
		return DateFormatUtils.format(Calendar.getInstance().getTime(),
				Defines.FORMAT_DATE_TIME_STRING);
	}

	public static Date stringToTime(String timeStr) {
		Date date = new Date();
		SimpleDateFormat apf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = apf.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Calendar stringToCalendar(String timeStr) {
		Date date = stringToTime(timeStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * @author liquan_apps
	 * @createTime 2007-1-28 19:32:51
	 * @param
	 * @return
	 * @since �õ���������� ****-**-**
	 */
	public static String nextDay(String today) {
		Calendar calendar = Calendar.getInstance();
		long todayLong = stringToDate(today).getTime();
		long lastDayLong = todayLong + 3600000 * 24;
		Date lastDay = new Date(lastDayLong);
		return DateFormatUtils.format(lastDay, Defines.FORMAT_DATE_STRING);
	}

	/**
	 * �����ڷֽ�������������
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @author lixiang
	 * @create 2008-8-28 ����09:22:46
	 * @since
	 */
	public static List getDays(String startDate, String endDate) {
		List list = new ArrayList();
		if (startDate.equals(endDate))
			list.add(startDate);
		else {
			for (String date = startDate; !date.equals(endDate); date = CommonsFiend
					.nextDay(date)) {
				list.add(date);
			}
			list.add(endDate);
		}
		return list;
	}

	public static String[] getUpdateTime() {
		Date date = Calendar.getInstance().getTime();
		date.setMinutes(date.getMinutes() + 1);
		String[] returnTime = new String[2];
		returnTime[0] = DateFormatUtils.format(date,
				Defines.FORMAT_DATE_TIME_STRING);
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		returnTime[1] = DateFormatUtils.format(date,
				Defines.FORMAT_DATE_TIME_STRING);
		return returnTime;
	}

	/**
	 * ��ʱ��ֽ��Сʱ
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @author lixiang
	 * @create 2008-11-20 ����02:17:57
	 * @since
	 */
	public static int[] getTimesString(String beginTime, String endTime) {
		int s1 = Integer.parseInt(beginTime.split(":")[0]);
		int s2 = Integer.parseInt(endTime.split(":")[0]);
		int[] back = new int[s2 - s1 + 1];
		for (int i = 0; i < back.length; i++) {
			back[i] = s1 + i;
		}
		return back;
	}

	/**
	 * ����ĳ���ĳһ�£���ȡ���µ���������
	 * 
	 * @param date
	 *            ��ʽyyyy-MM
	 * @return
	 * @author lixiang
	 * @throws ParseException
	 * @create 2008-9-22 ����01:59:33
	 * @since
	 */
	public static List getDatesList(String date) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM");
		Calendar time = Calendar.getInstance();
		time.clear();
		Date d1 = format.parse(date);
		time.setTime(d1);
		int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);
		DateFormat formats = new SimpleDateFormat(Defines.FORMAT_DATE_STRING);
		List list = new ArrayList();
		for (int i = 1; i <= day; i++) {
			String s = format.format(d1) + "-" + i;
			Date sss = formats.parse(s);
			String dd = formats.format(sss);
			list.add(dd);
		}
		return list;
	}

	/**
	 * ����HH:mm:ss�ĸ�ʽ�ض��ַ�����ȡ��沥��ʱ�䳤��
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @author lixiang
	 * @create 2008-9-24 ����03:31:28
	 * @since
	 */
	public static String getPlayTime(String beginTime, String endTime) {
		int begin = Integer.parseInt(beginTime.split(":")[0]);
		int end = Integer.parseInt(endTime.split(":")[0]);
		return String.valueOf(end - begin);
	}

	public static Date stringToTime(String timeStr, String formatString) {
		Date date = new Date();
		SimpleDateFormat apf = new SimpleDateFormat(formatString);
		try {
			date = apf.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static List sortList(List list, String name) {
		Comparator namCompare = new BeanComparator(name);
		Collections.sort(list, namCompare);
		return list;
	}

	/**
	 * @since int --> byte
	 * @author niehai
	 * @create 2008-10-31 ����11:15:05
	 * @param number
	 * @param byteSum
	 * @return
	 */
	public static byte[] convertBytes(int number, int byteSum) {
		byte[] b = new byte[byteSum];
		int len = b.length;
		for (int i = 0; i < len; i++) {
			b[len - i - 1] = (byte) ((number >> ((i) * 8)) & 0xFF);
		}
		return b;
	}

	public static String getCurrentTime() {
		return DateFormatUtils.format(Calendar.getInstance().getTime(),
				Defines.FORMAT_TIME_STRING);
	}

	public static String next59Secends(String beginTime) {
		Date begin = new Date();
		try {
			begin = new SimpleDateFormat(Defines.FORMAT_TIME_STRING)
					.parse(beginTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long nextMinute = begin.getTime() + 59000;
		return DateFormatUtils.format(nextMinute, Defines.FORMAT_TIME_STRING);
	}
	
	/**
	 * Task:�Ƚ�����1�Ƿ�������2֮ǰ(����)
	 * @author laihm
	 * @create 2009-5-20 ����10:20:03
	 * @since 
	 * @param date1�����ַ���1
	 * @param date2�����ַ���2
	 * @return ����1������2֮ǰ����true,���򷵻�false
	 */
	public static boolean isNotAfter(String date1,String date2){
		return stringToDate(date1).before(stringToDate(date2))||stringToDate(date1).equals(stringToDate(date2));
	}
	
	/**
	 * Task:�Ƚ�ʱ��1�Ƿ���ʱ��2֮ǰ(����)
	 * @author laihm
	 * @create 2009-5-20 ����10:23:08
	 * @since 
	 * @param time1ʱ���ַ���1
	 * @param time2ʱ���ַ���2
	 * @param formatStringʱ���ʽ
	 * @return ʱ��1��ʱ��2֮ǰ����true,���򷵻�false
	 */
	public static boolean isNotAfter(String time1,String time2,String formatString){
		return stringToTime(time1, formatString).before(stringToTime(time2, formatString))||stringToTime(time1, formatString).equals(stringToTime(time2, formatString));
	}
	
	/**
	 * Task:�Ƚ�����ʱ���ַ������������
	 * @author laihm
	 * @create 2009-5-20 ����03:18:29
	 * @since 
	 * @param s1ʱ���ַ���1
	 * @param s2ʱ���ַ���2
	 * @return ���������������ʾs1��s2֮ǰ��������ʾs1��s2֮��
	 */
	public int compareDate(String s1,String s2){
		Date date1=stringToDate(s1);
		Date date2=stringToDate(s2);
		return (int)(date1.getTime()-date2.getTime())/(24*3600*1000);
	}

	public static boolean validateImgFormat(String fileName) {
		String imgType = Defines.IMAGETYPE_REGEXP;// �����ִ�Сд
		Pattern pattern = Pattern.compile(imgType, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(fileName);
		boolean result = matcher.find();
		return result;
	}
	
	/**
	 * �����Ŀ�������ַ·��(���һ���ַ��Ƿָ���).
	 * 
	 * @return
	 * @author sunny
	 * @create 2007-10-25 ����03:44:40
	 */
	public static String getAbsPathOfProject() {
		String url = CommonsFiend.class.getClassLoader().getResource("").toString(); 
		String reg = "file:(.+)WEB-INF";
		Matcher mat = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(
				url);
		if (mat.find()) {
			String path = mat.group(1);
			path = path.replaceAll("/", "\\" + File.separator);
			if (File.separator.equals("\\"))// windows
				return path.substring(1);
			return path;
		}
		return null;
	}
	
	
	/**
	 * ���ֽ�תΪSring�ַ���
	 * 
	 * @param data
	 * @return
	 */
	public static String base64CodeByteTo64String(byte[] data) {
		BASE64Encoder encoder = new BASE64Encoder();
		if (data == null) {
			System.out.println("not get the img!");
			return null;
		}
		return encoder.encode(data).replaceAll("\\s*","");
	}
	
	
	/**
	 * ��string���͵�����ת��Ϊbyte����.
	 * 
	 * @param fileData
	 *            String ���͵�����.
	 * @return ת��������byte����,�����쳣����filedateΪnullʱ����null.
	 */
	public static byte[] base64CodeDecode(String fileData) {
		if (fileData == null) {
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			return decoder.decodeBuffer(fileData);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return null;
	}
		
	
	/**
	 * ��zip�ļ�ת��Base64�����ַ���
	 * @param filePath
	 * @return
	 * @author pengcc
	 */
	public static String getBase64StringOfZipFile(String filePath){		
		byte[] data=FileFiend.readFileByte(filePath);
		return CommonsFiend.base64CodeByteTo64String(data);
	}
	
	
	/**
	 * ������html�������ͼƬ
	 * @param htmlCode
	 * @return
	 * @author pengcc
	 */
	public static List getImageNamesInHtmlCode(String htmlCode){
		if(htmlCode==null){
			return new ArrayList();
		}
		NodeFilter linkAndImagFilter = new NodeFilter() {		
			 static final long serialVersionUID = -3600416039172283494L;
			public boolean accept(Node node) {
				if (node instanceof ImageTag)
					return true; 
				return false; 
			}
		};		
		Set imageSet=new HashSet(); //ȥ���ظ���ͼƬ
		Parser parser = new Parser();
		try {
			parser.setEncoding("gb2312");
			parser.setInputHTML(htmlCode);
			NodeList nlist = parser.extractAllNodesThatMatch(linkAndImagFilter);
			for (int j = 0; j < nlist.size(); j++) {
				Tag node = (Tag) nlist.elementAt(j);
				if (node instanceof ImageTag) {
					ImageTag img = (ImageTag) node;
					String imageName = img.getAttribute("src");
					if(imageName!=null){
					   imageName = imageName.substring(imageName.lastIndexOf('/') + 1);
					   imageSet.add(imageName);
					}
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		List imageList=new ArrayList(imageSet); //ȥ���ظ���ͼƬ
		return imageList;
	}
	
	
	/**
	 * ��֤marquee�����ݺ�ͼƬzip��
	 * @param imageList
	 * @param zipfilePath
	 * @return
	 */
	public static boolean validateMarqueeZipPic(List imageList,String zipfilePath){
		if(imageList.size()==0){
			return true;  //��ͼƬ������Ҫ��֤zip
		}
		if(FileFiend.judgeFileZip(zipfilePath)==false){  //�ϴ��Ĳ���zip
			return false;
		}
		boolean result=true;
		InputStream in=null;
		ZipInputStream zipInput=null;
		List zipPicList=new ArrayList();
		try{
			File file=new File(zipfilePath);
			in= new FileInputStream(file);
			zipInput=new ZipInputStream(in);
			ZipEntry zipEntry = null;				
		   while ((zipEntry = zipInput.getNextEntry()) != null) {
				String fileName = zipEntry.getName();
				if(CommonsFiend.validateImgFormat(fileName)==false){  //��֤marqueeContent��ͼƬ�ĸ�ʽ
					return false;
				}
				zipPicList.add(fileName); 			   
			}
		}
		 catch (FileNotFoundException e) {			
			e.printStackTrace();			
		} catch (IOException e) {			
			e.printStackTrace();			
		}
		finally{			
				try {					
					if(zipInput!=null){
						zipInput.closeEntry();
						zipInput.close();
					}
					if(in!=null){
						in.close();
					}
				} catch (IOException e) {					
					e.printStackTrace();					
				}			
		}
		
		for(int i=0;i<imageList.size();i++){
			String imageName=(String)imageList.get(i);
			boolean contain=false;
			for(int j=0;j<zipPicList.size();j++){
				String fileName=(String)zipPicList.get(j);
				 if(imageName.equals(fileName)){
				    	contain=true;    //�ҵ��˶�Ӧ��ͼƬ�ļ�
				    	break;
				 }
			}
		    if(contain==false){  //��һ��ͼƬû�ҵ�
	    	  result=false;
			  break;
		    }
	    }

		return result;
	}
	
	/**
	 * ������List������String��","��������
	 * @author huangfei
	 * @create 2009-6-24 ����03:02:00
	 * @since 
	 * @param StringList
	 * @return
	 */
	public static String splitListWithComma(List StringList){
		StringBuffer strOfList = new StringBuffer("");
		for(int i=0;i<StringList.size();i++){
			if(i!=0){
				strOfList.append(",");
			}
			strOfList.append(StringList.get(i).toString());
		}
		return strOfList.toString();
	}

}
