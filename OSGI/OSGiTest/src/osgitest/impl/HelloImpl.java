package osgitest.impl;

import osgitest.service.Hello;

public class HelloImpl implements Hello {

	private final String helloString;

	public HelloImpl(String helloString) {
		this.helloString = helloString;
	}

	@Override
	public void sayHello() {
		System.out.println(helloString);
	}

}
