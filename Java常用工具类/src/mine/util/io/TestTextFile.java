package mine.util.io;

public class TestTextFile {
	public static void main(String[] args) {
		TextFile.write("file/textfile.txt", false,
				"����һ���ı��ļ��Ķ�д����\nTouch\n������\n��\n");
		TextFile.write("file/textfile.txt", true, "�人��ҵѧԺ\n�������");
		System.out.println(TextFile.read("file/textfile.txt"));
	}
}
