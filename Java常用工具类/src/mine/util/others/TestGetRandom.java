package mine.util.others;

public class TestGetRandom {
	public static void main(String[] args) {
		System.out.println("������������ַ���");
		for (int i = 1; i <= 100; i++) {
			System.out.print(GetRandom.getRandomChar('A', 'Z') + "  ");
			if (i % 10 == 0)
				System.out.println();
		}
		System.out.println("�������������Ȼ����");
		for (int i = 1; i <= 100; i++) {
			System.out.print(GetRandom.getRandomInt(0, 9) + "  ");
			if (i % 10 == 0)
				System.out.println();
		}
	}
}
