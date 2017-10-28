package demo.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;


/*
 * DataInputStream���ڶ�ȡ��ʽ��������
 */
public class DataInputStreamAndByteArrayInputStreamDemo {
	public static void main(String[] args) throws IOException {
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(
				FileReaderAndBufferedReaderDemo.read("file/test3.txt")
						.getBytes()));
		while (in.available() != 0)
			System.out.print((char) in.readByte());

	}
}
