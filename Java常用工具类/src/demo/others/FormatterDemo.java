package demo.others;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Formatter;

/**
 * Formatter�����ڸ�ʽ��
 * 
 * @author Touch
 * 
 */
public class FormatterDemo {
	public static void main(String[] args) {
		int i = 1;
		double d = 2.2352353456345;
		// 1.������򵥵ĸ�ʽ�����,����c�����е�printf����
		System.out.format("%-3d%-5.3f\n", i, d);
		System.out.printf("%-3d%-5.3f\n", i, d);
		// Formatter���ʹ��
		// 2.��ʽ�����������̨
		Formatter f = new Formatter(System.out);
		f.format("%-3d%-8.2f%-10s\n", i, d, "touch");
		// 3.��ʽ��������ļ�
		Formatter ff = null;
		try {
			ff = new Formatter(new PrintStream("file/formater.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ff.format("%-3d%-8.2f%-10s\n", i, d, "touch");
		// 4.String.format().ͬc������sprintf()
		System.out.println(String.format("(%d%.2f%s)", i, d, "touch"));
	}
}
