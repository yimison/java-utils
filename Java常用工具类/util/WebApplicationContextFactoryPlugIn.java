/**
 * Program  : RuntimeBeanFactoryPlugIn.java<br/>
 * Author   : icesxDolphin<br/>
 * Create   : 2007-2-27 ����11:39:08<br/>
 *
 * Copyright 1997-2006 by Embedded Internet Solutions Inc.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Embedded Internet Solutions Inc.("Confidential Information").
 * You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement
 * you entered into with Embedded Internet Solutions Inc.
 *
 */
package cn.ipanel.apps.payment.util;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.struts.ContextLoaderPlugIn;

/**
 * ����plugin��Struts������(see strust-config.xml)��������ȡ��Spring�������ģ���֧�ִ�Sping�л�ȡʵ��.
 *
 * @author : Ices
 * @create : 2007-5-28 ����10:10:53
 */
public class WebApplicationContextFactoryPlugIn extends ContextLoaderPlugIn {

	private static WebApplicationContext applicationContext = null;

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	protected void onInit() throws ServletException {
		applicationContext = super.getWebApplicationContext();
	}
	public static  boolean isInited(){
		return applicationContext!=null;
	}
}
