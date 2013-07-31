package osgitestclient;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import osgitest.service.Hello;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		ServiceReference ref = context.getServiceReference(Hello.class
				.getName());
		if (ref != null) {
			Hello hello = null;
			try {
				hello = context.getService(ref);
				if (hello != null) {
					hello.sayHello();
				} else {
					System.out.println("Service: Hello---object null");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				context.ungetService(ref);
				hello = null;
			}
		}

	}

	@Override
	public void stop(BundleContext context) throws Exception {

	}

}
