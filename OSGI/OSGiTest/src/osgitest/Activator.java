package osgitest;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import osgitest.impl.HelloImpl;
import osgitest.service.Hello;

public class Activator implements BundleActivator {

	private List<ServiceRegistration> registrations = new ArrayList<ServiceRegistration>();

	public void start(BundleContext bundleContext) throws Exception {
		registrations.add(bundleContext.registerService(Hello.class.getName(),
				new HelloImpl("Hello, OSGi"), null));
	}

	public void stop(BundleContext bundleContext) throws Exception {
		for (ServiceRegistration registration : registrations) {
			System.out.println("unregistering: " + registration);
			registration.unregister();
		}
	}

}
