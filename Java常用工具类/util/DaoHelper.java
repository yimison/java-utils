/**
 * Program  : DaoHelper.java
 * Author   : huangfei
 * Create   : 2009-5-4 ����04:02:08
 *
 * Copyright 2006 by ipanel Technologies Ltd.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of ipanel Technologies Ltd.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with ipanel Technologies Ltd.
 *
 */

package cn.ipanel.apps.portalBackOffice.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 * ���Ƿ�Ϊ��ȡĳ���������ֶεľ�ȷֵ�����գ�<br>
 * ����Ĵ������Ϊ�㾫ȷ�ṩ��Щ�ֶ����Ƶ�ֵ<br>
 * �����԰������ɳ��õ�SQL<br>
 * �������԰�������bean ��������ֶ�
 * @author   huangfei
 * @version  1.0.0
 * @2009-5-4 ����04:02:08
 */
public class DaoHelper {
	private DataSource dataSource;
	
	public DaoHelper(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public void autoCreatSql(String tableName){
		String sql = "select * from "+tableName+" limit 1";
		try {
			Connection conn = this.dataSource.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);    
			ResultSetMetaData data=rs.getMetaData(); 
			
			String selectSql="select ";
			String insertSql="insert into "+tableName +" ( ";
			String updateSql="update "+tableName +" set ";
			String beanFileds="\r\n";
			String getFileds="\r\n";
			
			for(int i = 1 ; i<= data.getColumnCount() ; i++){ 
//				���ָ���е����� 
				String columnName = data.getColumnName(i);    
//				���ָ���е�����������    
				String columnTypeName=data.getColumnTypeName(i); 
				selectSql +=(i==(data.getColumnCount())?columnName:columnName+", ");
				insertSql +=(i==(data.getColumnCount())?columnName+")":columnName+", ");
				updateSql +=(i==(data.getColumnCount())?columnName +"=?":columnName+"=?, ");
				beanFileds +=  "private String "+columnName +";   //"+columnTypeName+"\r\n";
				getFileds  +=  tableName+"."+"get"+columnName.substring(0, 1).toUpperCase()+columnName.substring(1)+"()"+"\r\n";;
			}
			
			insertSql +="values(";
			for(int j = 1 ; j<= data.getColumnCount() ; j++){ 
				insertSql +=(j==(data.getColumnCount())?"?)":"?, ");
			}
			
			selectSql += " from " +tableName +" where 1=1 ";
			updateSql += " where 1=1 ";
			System.out.println(selectSql+"\r\n");
			System.out.println(insertSql+"\r\n");
			System.out.println(updateSql+"\r\n");
			System.out.println(getFileds);
			System.out.println(beanFileds);

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	} 
}

