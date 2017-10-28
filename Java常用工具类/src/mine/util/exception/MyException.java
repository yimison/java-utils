package mine.util.exception;

/**
 * �Զ����쳣�෽��
 * 1��ͨ���̳�Throwable
 * 2��ͨ���̳�Exception
 * 
 * @author Touch
 */
public class MyException extends Exception {

	private static final long serialVersionUID = 1L;

	public MyException() {
		super();
	}

	public MyException(String message) {
		super(message);
	}

	public MyException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyException(Throwable cause) {
		super(cause);
	}
}
