package com.zetool.beancopy.proxy;


/**
 * �˽ӿڰ���һ������
 * T copyFrom(Class<T> source);
 * �˷���ʹ����д�source�������е��ֶ�ֵ��������������.
 * @author Rnti
 *
 */
public interface ReplicableI {
	/**
	 * ��source����
	 * @param source
	 * @return
	 */
	public <T> T copyFrom(Class<T> source);
}
