package demo.others;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * �����������,Calendar�಻�ȶ�,����ʱ�Ժ��ݴ���
 */
public class CalendarDemo {
	public static void main(String[] args) {
		simpleDemo();
		showCalendar();
		numberOfDays();
	}

	// Calendar���÷���ʾ��
	public static void simpleDemo() {
		//��ǰ����
		Calendar c = new GregorianCalendar();
		//c.setTime(new Date());
		StringBuilder str = new StringBuilder();
		//��ȡ��ǰ���ڵ���Ϣ
		str.append("year:" + c.get(Calendar.YEAR));
		str.append("   month:" + (c.get(Calendar.MONTH) + 1));
		str.append("   day:" + c.get(Calendar.DAY_OF_MONTH));
		str.append("   week:" + (c.get(Calendar.DAY_OF_WEEK) - 1));
		str.append("   hour:" + c.get(Calendar.HOUR_OF_DAY));
		str.append("   minute:" + c.get(Calendar.MINUTE));
		str.append("   second:" + c.get(Calendar.SECOND));
		System.out.println(str);

		// ת����Date����
		Date d = Calendar.getInstance().getTime();
		System.out.println(d);
	}

	// �����ǰ�µ�����
	public static void showCalendar() {
		// ��õ�ǰʱ��
		Calendar c = Calendar.getInstance();

		// ���ô��������Ϊ1��

		c.set(Calendar.DATE, 1);

		// ���1�������ڼ�

		int start = c.get(Calendar.DAY_OF_WEEK);

		// ��õ�ǰ�µ����������

		int maxDay = c.getActualMaximum(Calendar.DATE);

		// �������

		System.out.println("������ ����һ ���ڶ� ������ ������ ������   ������");

		// �����ʼ�Ŀո�

		for (int i = 1; i < start; i++) {

			System.out.print("      ");

		}

		// ��������е���������

		for (int i = 1; i <= maxDay; i++) {

			// �����������

			System.out.print(" " + i);

			// ����ָ��ո�

			System.out.print("   ");

			if (i < 10) {

				System.out.print(' ');

			}

			// �ж��Ƿ���

			if ((start + i - 1) % 7 == 0) {

				System.out.println();

			}

		}

		// ����

		System.out.println();

	}

	// ������������֮����������
	public static void numberOfDays() {

		// ������������

		// ���ڣ�2009��3��11��

		Calendar c1 = Calendar.getInstance();

		c1.set(2009, 3 - 1, 11);

		// ���ڣ�2010��4��1��

		Calendar c2 = Calendar.getInstance();

		c2.set(2010, 4 - 1, 1);

		// ת��Ϊ���ʱ��

		long t1 = c1.getTimeInMillis();

		long t2 = c2.getTimeInMillis();

		// ��������

		long days = (t2 - t1) / (24 * 60 * 60 * 1000);

		System.out.println(days);
	}

}
