package util.dynamic_proxy.cglib;

/**
 * ԭ����
 * @author Rnti
 *
 */
public class HelloClass{
	int a;
	public void hello() {
		System.out.println("hello" + this);
	}
	@Override
	public String toString() {
		return this.a + " HelloClass";
	}
}

