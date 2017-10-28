package mine.util.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * �������ڶ���Ķ�д
 * 
 * @author Touch
 */
public class ObjectFile {
	// ��һ������д���ļ���isAppendΪtrue��ʾ׷�ӷ�ʽд��false��ʾ����д
	public static void write(String filePath, Object o, boolean isAppend) {
		if (o == null)
			return;
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(filePath, isAppend));
			try {
				out.writeObject(o);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ��һ����������д���ļ���isAppendΪtrue��ʾ׷�ӷ�ʽд��false��ʾ����д
	public static void write(String filePath, Object[] objects, boolean isAppend) {
		if (objects == null)
			return;
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(filePath, isAppend));
			try {
				for (Object o : objects)
					out.writeObject(o);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ��ȡ���󣬷���һ������
	public static Object read(String filePath) {
		Object o = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					filePath));
			try {
				o = in.readObject();
			} finally {
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	// ��ȡ���󣬷���һ���������飬count��ʾҪ���Ķ���ĸ���
	public static Object[] read(String filePath, int count) {
		Object[] objects = new Object[count];
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					filePath));
			try {
				for (int i = 0; i < count; i++) {
					//�ڶ��ε���in.readObject()���׳��쳣������Ϊʲô��
					objects[i] = in.readObject();
				}
			} finally {
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}
}
