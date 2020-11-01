package util.copy;

import util.copy.executor.EqualsObjecCopyExecutor;
import util.copy.executor.UnequalsObjectCopyExecutor;

/**
 * 拷贝对象工具类
 * 主要存放将a拷贝到b的方法
 * @author Rnti
 *
 */
public class CopyUtils {
	/**
	 * 将Tsource对象克隆给Ttarget
	 * @param <Tsource>
	 * @param <Ttarget>
	 * @param sourceObj
	 * @param targetClass
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static  <Tsource, Ttarget>Ttarget 
		copy(Tsource sourceObj, Class<Ttarget> targetClass) 
			throws InstantiationException, IllegalAccessException{
		return UnequalsObjectCopyExecutor.copyForm(sourceObj, targetClass);
	}
	/**
	 * 克隆一份T对象
	 * @param <T>
	 * @param sourceObj
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T>T copy(T sourceObj) 
			throws InstantiationException, IllegalAccessException{
		return EqualsObjecCopyExecutor.copyForm(sourceObj);
	}
}
