/**
 * Program  : WebServiceUtil.java
 * Author   : leigq
 * Create   : 2010-11-12 ����09:02:05
 *
 * Copyright 2010 by Embedded Internet Solutions Inc.,
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.ipanel.apps.portalBackOffice.define.Defines;
import cn.ipanel.apps.portalBackOffice.domain.WSAddress;

/**
 * webService ������
 * @author leigq
 * @version 1.0.0
 * @2010-11-12 ����09:02:05
 */
public class WebServiceUtil {
	
	private Properties properties = new Properties();
	
	private static Logger logger = Logger.getLogger(WebServiceUtil.class);
	
	public WebServiceUtil() {
		properties = PropertyManager.getConfig();
	}

	/**
	 * ��ȡWebService������Ϣ,���ص����ݸ�ʽΪ
	 * @return List<WSAddress>
	 * @author leigq
	 * @create 2010-11-12 ����09:58:32
	 */
	public List<WSAddress> getWebServers() {
		List<WSAddress> result = new ArrayList<WSAddress>();

		Enumeration<?> enu = properties.propertyNames();
		Pattern pattern = Pattern.compile("^(wsAddress)X?");
		CONTINUE_POINT: while (enu.hasMoreElements()) {
			try {
				String key = (String) enu.nextElement();
				Matcher matcher = pattern.matcher(key);
				if (!matcher.find())
					continue;

				String propertityValue = (String) properties.get(key);
				// ���������';'�ָ���,�򲻴���
				if (propertityValue.indexOf(";") == -1)
					continue;

				String[] values = propertityValue.split(";");
				// ����������ι���,�򲻴���
				if (values.length != 4)
					continue;

				for (int i = 0; i < values.length; i++)
					if (values[i] == null || values[i].trim().length() == 0)
						continue CONTINUE_POINT;

				result.add(new WSAddress(key, values[0], values[1], values[2],values[3]));
			} catch (Exception e) {
				logger.warn(e);
			}
		}
		return result;
	}
	
	/**
	 * ����keyֵ�Ƿ��Ѿ���ʹ��,���ظ�ʹ����ͬkeyֵ,�����µ����ø��Ǿɵ�����
	 * @param key
	 * @return
	 * @author leigq
	 * @create 2010-11-12 ����10:01:10
	 */
	public boolean checkKeyIsExist(String key) {
		Set<Object> keys = properties.keySet();
		if (keys.contains(key))
			return true;
		return false;
	}
	/**
	 * ����WebService���õ�property�ļ�
	 * @param wsAddress
	 * @return
	 * @author leigq
	 * @create 2010-11-12 ����10:02:25
	 */
	public boolean storWSAddress(WSAddress wsAddress){
		if (wsAddress == null || checkWSAddressValue(wsAddress))
			throw new RuntimeException("��������ȷ,����.");

		String wsProperty = wsAddress.getAddress() + ";" + wsAddress.getAccessFolder() + ";" + wsAddress.getPublishFolder() + ";" + wsAddress.getVisitURL();
		String wsKey = wsAddress.getWsName();

		properties.setProperty(wsKey, wsProperty);
		try {
			properties.store(new FileOutputStream(new File(Defines.CONFIG_FILE_PATH)), null);
		} catch (IOException e) {
			throw new RuntimeException("�������ô洢ʧ�ܣ�����.");
		}
		return true;
	}
	/**
	 *  �Ƴ�webService����
	 * @param key
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @author leigq
	 * @create 2010-11-12 ����10:03:51
	 */
	public boolean removeWSAddress(String key){
		try {
			properties.remove(key);
			properties.store(new FileOutputStream(new File(Defines.CONFIG_FILE_PATH)), "");
			return true;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("�����ļ�δ�ҵ�������.");
		} catch (IOException e) {
			throw new RuntimeException("�ļ��洢ʧ�ܣ�����.");
		}
		
		
	}
	

	/**
	 * �������Ƿ���ȷ,�κδ�����ֵ�����쳣
	 * @param wsAddress
	 * @return leigq
	 * @author leigq
	 * @create 2010-11-12 ����10:03:38
	 */
	private boolean checkWSAddressValue(WSAddress wsAddress) {
		String wsName = wsAddress.getWsName();
		if (wsName == null || wsName.trim().length() == 0)
			throw new RuntimeException("����: wsNameΪ��,����.");
		
		String publishFolder = wsAddress.getPublishFolder();
		if (publishFolder == null || publishFolder.trim().length() == 0 || publishFolder.indexOf(";") != -1)
			throw new RuntimeException("����: publishFolderΪ�ջ�����Ƿ��ַ�:';',����.");
		
		String address = wsAddress.getAddress();
		if (address == null || address.trim().length() == 0 || address.indexOf(";") != -1)
			throw new RuntimeException("����: wsAddressΪ�ջ�����Ƿ��ַ�:';',����.");
		
		String accessFolder = wsAddress.getAccessFolder();
		if (accessFolder == null || accessFolder.trim().length() == 0 || accessFolder.indexOf(";") != -1)
			throw new RuntimeException("����: accessFolderΪ�ջ�����Ƿ��ַ�:';',����.");
		String visitURL = wsAddress.getVisitURL();
		if (visitURL == null || visitURL.trim().length() == 0 || visitURL.indexOf(";") != -1)
			throw new RuntimeException("����: visitURLΪ�ջ�����Ƿ��ַ�:';',����.");
		return false;
	}
}

