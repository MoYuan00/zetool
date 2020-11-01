package util.dynamic_proxy.cglib;

/**
 * ‘≠∂‘œÛ
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

