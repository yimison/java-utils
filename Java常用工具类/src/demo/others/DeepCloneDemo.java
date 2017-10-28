package demo.others;

public class DeepCloneDemo {
	public static void main(String[] args) {
		B b = new B(2, new A(1));
		B b1 = (B) b.clone();
		System.out.println(b == b1);
		System.out.println(b.equals(b1));
		System.out.println(b.getClass() == b.getClass());
		System.out.println("�ı�b�ĸ���b1ǰ��y=" + b.getY() + ",x=" + b.getA().getX());
		b1.setY(5);
		b1.getA().setX(100);
		System.out.println("�ı�b�ĸ���b1��y=" + b.getY() + ",x=" + b.getA().getX());
		System.out.println("���¡�ɹ�������");
	}
}

class A implements Cloneable {
	private int x;

	// Ϊ��ʵ�����¡
	public Object clone() {
		A a = null;
		try {
			a = (A) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return a;
	}

	public A(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
}

class B implements Cloneable {
	private int y;
	private A a;

	// ����Object��clone����
	// protected native Object clone() throws CloneNotSupportedException;
	// ע�⵽protected,�����Ȩ�޸�Ϊ��public
	public Object clone() {
		B b = null;
		try {
			b = (B) super.clone();
			// ʵ�����¡��û���������ֻ�ǿ�¡��a������
			b.a = (A) a.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return b;
	}

	public B(int y, A a) {
		this.y = y;
		this.a = a;
	}

	public int getY() {
		return y;
	}

	public A getA() {
		return a;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setA(A a) {
		this.a = a;
	}
}
