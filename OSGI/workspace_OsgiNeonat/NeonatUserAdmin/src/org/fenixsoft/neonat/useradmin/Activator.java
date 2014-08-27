package org.fenixsoft.neonat.useradmin;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private static UserAdminTracker userAdminTracker;
	private static HttpServiceTracker httpServiceTracker;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		userAdminTracker = new UserAdminTracker(context);
		userAdminTracker.open();
		httpServiceTracker = new HttpServiceTracker(context);
		httpServiceTracker.open();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		userAdminTracker.close();
		httpServiceTracker.close();
	}

}
