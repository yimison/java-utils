package book.oo.factory;

/**
 * �ӿ��࣬������ֵ�������
 */
public interface IOrderNumber {
	/**
	 * ����������������
	 * @param intArray	���������������
	 * @return	��������������
	 */
	public int[] orderASC(int[] intArray);
	
	/**	����������������	*/
	public int[] orderDESC(int[] intArray);
}
