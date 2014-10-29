package p;

public class A {
	public A() {
		System.out.println(this.getClass().getName() + ": "
				+ this.getClass().getClassLoader().toString());
	}
}
