package demo.exception;
 
import mine.util.exception.MyException;

public class ExceptionDemo3 {

	public void g() throws MyException {
		throw new MyException("�쳣2");
	}

	public static void main(String[] args) {
		ExceptionDemo3 ex = new ExceptionDemo3();
		try {
			ex.g();
		} finally {
			//ֱ��return�ᶪʧ�����׳����쳣
			return;
		}

	}
}
