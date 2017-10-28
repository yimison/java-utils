package cn.ipanel.routing.model.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;
import cn.ipanel.routing.util.Config;

public class DBConn{
	private static BasicDataSource ds;

	
	static{
		try{
			ds = new BasicDataSource();
			ds.setDriverClassName(Config.DATABASE_DRIVER);//Java��JDBC���������������
			ds.setUrl(Config.DATABASE_URL);               //���ݿ�URL
			ds.setUsername(Config.DATABASE_USERNAME);     
			ds.setPassword(Config.DATABASE_PASSWORD);
			ds.setValidationQuery("select count(*) from area");//  SQL��ѯ����������֤���������ǴӸóص����ӣ�Ȼ�󷵻ء�
			ds.setTestOnBorrow(true);// ָʾ�Ķ����Ƿ񽫱���֤ǰ�ؽ�����
			ds.setPoolPreparedStatements(true);//�趨�Ƿ��������û�С�
			ds.setInitialSize(10);//���������ڳ��е�����ʱ�����ġ�
			ds.setMaxActive(30);//��û�����Ƶ�����������������ӣ�����Դӳصķ��䣬ͬ����ʱ�䡣
			ds.setRemoveAbandoned(true); //�Ƴ�������
			ds.setRemoveAbandonedTimeout(120);
			}catch (Exception e){
			e.printStackTrace();
			}
	}


	public static Connection getConnection(boolean autocommit){
		Connection conn=null;
		try {
			conn= ds.getConnection();
			conn.setAutoCommit(autocommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
}