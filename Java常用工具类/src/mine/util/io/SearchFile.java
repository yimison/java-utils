package mine.util.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * FileDirectory�����ڲ���ָ����Ŀ¼�µ������ļ���Ŀ¼ ����ͨ��������ʽ��Ҫ���ҵ� �ļ���Ŀ¼����ɸѡ
 * 
 * @author Touch
 */
public final class SearchFile {
	// ����ļ�
	private List<File> fileList = new ArrayList<File>();
	// ���Ŀ¼
	private List<File> directoryList = new ArrayList<File>();
	// ����ļ���Ŀ¼
	private List<File> list = new ArrayList<File>();
	private File file;// Ŀ¼
	private String regex;// ������ʽ

	public SearchFile(String path) {
		file = new File(path);
		this.regex = ".*";
	}

	public SearchFile(File file) {
		this.file = file;
		this.regex = ".*";
	}

	public SearchFile(String path, String regex) {
		file = new File(path);
		this.regex = regex;
	}

	public SearchFile(File file, String regex) {
		this.file = file;
		this.regex = regex;
	}

	// ���ص�ǰĿ¼�µ������ļ�����Ŀ¼
	public List<File> files() {
		File[] files = file.listFiles();
		List<File> list = new ArrayList<File>();
		for (File f : files)
			if (f.getName().matches(regex))
				list.add(f);
		return list;
	}

	// ���ظø�Ŀ¼�µ������ļ�
	public List<File> allFiles() {
		if (list.isEmpty())
			search(file);
		return fileList;
	}

	// ���ظø�Ŀ¼�µ�������Ŀ¼
	public List<File> allDirectory() {
		if (list.isEmpty())
			search(file);
		return directoryList;
	}

	// ���ظø�Ŀ¼�µ������ļ�����Ŀ¼
	public List<File> allFilesAndDirectory() {
		if (list.isEmpty())
			search(file);
		return list;
	}

	// �ݹ�������ǰĿ¼�µ������ļ���Ŀ¼
	private void search(File file) {
		File[] files = file.listFiles();
		if (files == null || files.length == 0)
			return;
		for (File f : files) {
			if (f.getName().matches(regex))
				list.add(f);
			if (f.isFile() && f.getName().matches(regex))
				fileList.add(f);
			else {
				if (f.getName().matches(regex))
					directoryList.add(f);
				search(f);
			}
		}
	}
}
