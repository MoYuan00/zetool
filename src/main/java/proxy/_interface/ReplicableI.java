package proxy._interface;


/**
 * 此接口包含一个方法
 * T copyFrom(Class<T> source);
 * 此方法使你具有从source对象将其中的字段值拷贝过来的能力.
 * @author Rnti
 *
 */
public interface ReplicableI {
	/**
	 * 从source对象
	 * @param source
	 * @return
	 */
	public <T> T copyFrom(Class<T> source);
}
