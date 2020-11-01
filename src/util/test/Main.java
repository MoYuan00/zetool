package util.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import proxy.javabean.CClass2;
import proxy.javabean.DClass2;
import util.annotation.method.CopyTo;
import util.copy.CopyUtils;
import util.reflex.FieldUtil;
import util.reflex.anntation.AnnotationUtil;
import util.test.javabean.AClass;
import util.test.javabean.BClass;

public class Main {
	static class A{
		int a = 0;
		@CopyTo(sourceField = { "a" }, targetClass = Main.class)
		public int show() {
			return 0;
		}
	}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		
		for(Method method : A.class.getMethods()) {
			for(Annotation an : method.getAnnotations()) {
				System.out.println(an.annotationType());
				if (an.annotationType().equals(CopyTo.class)) {
					System.out.println(
							Arrays.toString( AnnotationUtil.getNeedCopyList(A.class, (CopyTo)an).toArray() )
							);
					System.out.println(AnnotationUtil.getNeedCopyList(A.class, (CopyTo)an).size());
				}
			}
		}
		
		
//		// 拷贝不同类对象
//		BClass b = CopyUtils.copy(new AClass(), BClass.class);
//		FieldUtil.showFieldsValue(b);
//		
//		DClass2 d = CopyUtils.copy(new CClass2(), DClass2.class);
//		FieldUtil.showFieldsValue(d);
//		
//		BClass b2 = new AClass().copyToB();
//		FieldUtil.showFieldsValue(b2);
//		
//		// 拷贝同类对象
//		BClass b3 = CopyUtils.copy(new BClass());
//		FieldUtil.showFieldsValue(b3);
		
	}
}
