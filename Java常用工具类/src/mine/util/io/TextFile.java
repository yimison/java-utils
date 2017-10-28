package mine.util.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * �˹����������ı��ļ��Ķ�д
 * 
 * @author Touch
 */
public class TextFile {
	// ��ȡָ��·���ı��ļ�
	public static String read(String filePath) {
		StringBuilder str = new StringBuilder();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filePath));
			String s;
			try {
				while ((s = in.readLine()) != null)
					str.append(s + '\n');
			} finally {
				in.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str.toString();
	}

	// д��ָ�����ı��ļ���appendΪtrue��ʾ׷�ӣ�false��ʾ��ͷ��ʼд��
	//text��Ҫд����ı��ַ�����textΪnullʱֱ�ӷ���
	public static void write(String filePath, boolean append, String text) {
		if (text == null)
			return;
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filePath,
					append));
			try {
				out.write(text);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
