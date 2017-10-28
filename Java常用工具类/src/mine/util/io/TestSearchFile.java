package mine.util.io;

import java.io.File;
import java.util.List;

public class TestSearchFile {
	public static void main(String[] args) {
		System.out.println("-------- ָ��Ŀ¼�������ļ�����Ŀ¼-------");
		List<File> list = (List<File>) new SearchFile(
				"G:/java/workspace/test/file").files();
		for (File file : list)
			System.out.println(file.getName());
		System.out.println("--------ָ��Ŀ¼����txtΪ��׺���ļ�------");
		list = (List<File>) new SearchFile("G:/java/workspace/test/file",
				".*\\.txt").files();
		for (File file : list)
			System.out.println(file.getName());
		System.out.println("--------�Ը�Ŀ¼Ϊ��Ŀ¼�������ļ�����Ŀ¼--");
		list = (List<File>) new SearchFile("G:/java/workspace/test")
				.allFilesAndDirectory();
		for (File file : list)
			System.out.println(file.getName());
	}
}
