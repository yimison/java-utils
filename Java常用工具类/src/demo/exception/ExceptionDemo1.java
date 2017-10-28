package demo.exception;

import mine.util.exception.MyException;
/*
 * �۲��쳣ջ�������쳣����ʽ
 */
public class ExceptionDemo1 {
	public void f() throws MyException {
		throw new MyException("�Զ����쳣");
	}

	public void g() throws MyException {
		f();
	}

	public  void h() throws MyException  {
		try {
			g();
		} catch (MyException e) {
			//1��ͨ����ȡջ�켣�е�Ԫ����������ʾ�쳣�׳��Ĺ켣
			for (StackTraceElement ste : e.getStackTrace())
				System.out.println(ste.getMethodName());
			//2��ֱ�ӽ��쳣ջ��Ϣ�������׼���������׼�����
			e.printStackTrace();//�������׼������
			e.printStackTrace(System.err);
			e.printStackTrace(System.out);
			//3�����쳣��Ϣ������ļ���
			//e.printStackTrace(new PrintStream("file/exception.txt"));
			//4�������׳��쳣,���ֱ���׳���ôջ·���������ģ������fillInStackTrace()
			//��ô����������������ǰ��h()��������Ϊ�쳣������ԭ�㡣
			//throw e;
			throw (MyException)e.fillInStackTrace();
		}
	}
	public static void main(String[] args) {
			try {
				new ExceptionDemo1().h();
			} catch (MyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
