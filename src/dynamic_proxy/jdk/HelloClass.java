package dynamic_proxy.jdk;

/**
 * 原对象
 * 继承要代理的接口
 * @author Rnti
 *
 */
public class HelloClass implements HelloInterface{
	int a;
	@Override
	public void hello() {
		System.out.println("hello" + this);
	}
	@Override
	public String toString() {
		return this.a + " HelloClass";
	}
}

