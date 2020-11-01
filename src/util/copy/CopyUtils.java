package util.copy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.annotation.CopyFrom;
import util.reflex.AnnotationUtil;
import util.reflex.FieldUtil;

/**
 * 拷贝对象工具类
 * 主要存放将a拷贝到b的方法
 * @author Rnti
 *
 */
public class CopyUtils {
	/**
	 * 1.实例化一个B（需要无参数构造）
	 * 2.通过类A的实例以及，B的类型，将A的某些字段值拷贝给B
	 * 3.这些字段值由 CopyForm 提供并表示
	 * @param <T>
	 * @param a
	 * @param b
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <Ta,Tb>Tb copyForm(Ta a, Class<Tb> b) throws InstantiationException, IllegalAccessException {
		if(a == null || b == null) throw new NullPointerException();
		final Tb bInstance = b.newInstance();
		List<Field> bFieldList =  FieldUtil.getFields(bInstance);// 获取B类的属性
//		Set<Field> aFieldSet =  new HashSet<Field>(FieldUtil.getFields(a));
		CopyFrom bCopyFrom = b.getDeclaredAnnotation(CopyFrom.class);// 获取到映射注解
		if(bCopyFrom != null) {
			// 获取到映射集合 b -> a	// b需要从a的哪个字段拷贝
			bFieldList = AnnotationUtil.getNeedCopyList(bFieldList, bCopyFrom);
			System.out.println("需要拷贝字段集合:" + Arrays.toString(bFieldList.toArray()));
			bFieldList.forEach((bField)->{
//				Field aField = aFieldSet.stream().filter((aF)->aF.getName().equals(bField.getName())).findFirst().get();
				Field aField = null;
				try {
					aField = a.getClass().getDeclaredField(bField.getName());// 通过b属性明获取a属性
				} catch (NoSuchFieldException | SecurityException e1) {
					e1.printStackTrace();
				}
				// 不判断是否找到
				System.out.println("找到对应字段:" + aField.getName());
				aField.setAccessible(true);// 设置为可以访问
				bField.setAccessible(true);
				try {
					Object fieldVal = aField.get(a);// 获取指定a对象的当前字段的值
					System.out.println("获取a的值为:" + fieldVal);
					bField.set(bInstance, fieldVal);// 设置当前b对象值
				} catch (IllegalArgumentException | IllegalAccessException 
						| SecurityException e) {
					e.printStackTrace();
				}
			});
		}
		return bInstance;
	}
	/**
	 * 拷贝对象A和B同类型，并且实现了Serializable接口，就直接深度拷贝
	 * @param <T>
	 * @param a
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable>T copyForm(T a){
		if(a == null) throw new NullPointerException();
		T bInstance = null;
		ByteArrayOutputStream bAOS = new ByteArrayOutputStream(100);
		ObjectOutputStream oOs = null;
		try {
			oOs = new ObjectOutputStream(bAOS);// 将对象写入字节缓存区
			oOs.writeObject(a); // 写入对象
			oOs.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oOs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ObjectInputStream oIS = null;
		try {
			oIS = new ObjectInputStream(new ByteArrayInputStream(bAOS.toByteArray()));
			bInstance = (T) oIS.readObject();// 读取对象
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				bAOS.close();
				oIS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bInstance;
	}
	
	/**
	 * 拷贝对象A和B同类型
	 * @param <T>
	 * @param a
	 * @return 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	public static <T>T copyForm(T obj) throws InstantiationException, IllegalAccessException{
		if(obj == null) throw new NullPointerException();
		T newObj = (T) obj.getClass().newInstance();
		FieldUtil.getFieldsAsStream(obj).forEach(field->{
			field.setAccessible(true);// 设置为可以访问
			Object fieldVal = null;
			try {
				fieldVal = field.get(obj);// 获取指定obj对象的当前字段的值
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			System.out.println("获取obj的值为:" + fieldVal);
			try {
				Field newObjField = newObj.getClass().getDeclaredField(field.getName());
				newObjField.setAccessible(true);
				newObjField.set(newObj, fieldVal);// 设置当前newObj对象值
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		});
		return newObj;
	}
}
