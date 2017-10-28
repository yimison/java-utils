package mine.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * �˹��������ڶ������ļ��Ķ�д
 * 
 * @author Touch
 */
public class BinaryFile {
	// �Ѷ������ļ������ֽ����飬���û�����ݣ��ֽ�����Ϊnull
	public static byte[] read(String filePath) {
		byte[] data = null;
		try {
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(filePath));
			try {
				data = new byte[in.available()];
				in.read(data);
			} finally {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	// ���ֽ�����Ϊд��������ļ�������Ϊnullʱֱ�ӷ���
	public static void write(String filePath, byte[] data) {
		if (data == null)
			return;
		try {
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(filePath));
			try {
				out.write(data);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
