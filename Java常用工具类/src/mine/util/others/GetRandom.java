package mine.util.others;

import java.util.Random;

public class GetRandom {
	// ����ch1��ch2֮��(����ch1,ch2)������һ���ַ�,���ch1 > ch2������'\0'
	public static char getRandomChar(char ch1, char ch2) {
		if (ch1 > ch2)
			return 0;
		// ����������ʽ�ȼ�
		// return (char) (ch1 + new Random().nextDouble() * (ch2 - ch1 + 1));
		return (char) (ch1 + Math.random() * (ch2 - ch1 + 1));
	}

	// ����a��b֮�g(����a,b)������һ����Ȼ��,���a > b || a < 0������-1
	public static int getRandomInt(int a, int b) {
		if (a > b || a < 0)
			return -1;
		// ����������ʽ�ȼ�
		// return a + (int) (new Random().nextDouble() * (b - a + 1));
		return a + (int) (Math.random() * (b - a + 1));
	}
}
