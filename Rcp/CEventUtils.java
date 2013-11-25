package net.atp.trader.client.utils.manager;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import net.atp.trader.client.Activator;
import net.atp.trader.client.utils.event.CEvent;
import net.atp.trader.client.utils.event.CEventListener;

import org.eclipse.core.runtime.preferences.BundleDefaultsScope;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

/**
 * simple event manager can easy send event and receive event note that, the
 * CEventManager should be use only for one instance in a class!
 * 
 * @author kingchess
 * 
 */
public class CEventManager {
	public static HashMap<String, HashMap<CEventListener, ServiceRegistration<EventHandler>>> eventHasMaps;

	static {
		eventHasMaps = new HashMap<String, HashMap<CEventListener, ServiceRegistration<EventHandler>>>();
	}

	public CEventManager() {
	}

	/**
	 * !only for instance! send cevent with data
	 * 
	 * @param eventName
	 *            event define by CEvent
	 * @param data
	 *            the data you want to send
	 */
	public static void sendEvent(String eventName, Object data) {
		BundleContext bctx = Activator.getDefault().getBundle()
				.getBundleContext();
		ServiceReference<EventAdmin> serviceRef = bctx
				.getServiceReference(EventAdmin.class);
		EventAdmin eventAdmin = bctx.getService(serviceRef);

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("DATA", data);
		Event sevent = new Event(eventName, properties);
		eventAdmin.postEvent(sevent);
	}

	/**
	 * only for instance
	 * 
	 * @param eventName
	 * @param listener
	 */
	public static void addEventListener(final String eventName,
			final CEventListener listener) {
		BundleContext ctx = Activator.getDefault().getBundle()
				.getBundleContext();
		EventHandler handler = new EventHandler() {

			@Override
			public void handleEvent(final Event e) {
				CEvent cevent = new CEvent(eventName, e.getProperty("DATA"));
				listener.receiveEvent(cevent);
			}
		};

		Dictionary<String, String> properties = new Hashtable<String, String>();
		properties.put(EventConstants.EVENT_TOPIC, eventName);
		ServiceRegistration<EventHandler> sr = ctx.registerService(
				EventHandler.class, handler, properties);

		HashMap<CEventListener, ServiceRegistration<EventHandler>> hashMaps = eventHasMaps
				.get(eventName);
		if (hashMaps == null) {
			hashMaps = new HashMap<CEventListener, ServiceRegistration<EventHandler>>();
			eventHasMaps.put(eventName, hashMaps);
		}
		hashMaps.put(listener, sr);
	}

	/**
	 * remove event associated with the eventName and the spectify listener
	 * 
	 * @param eventName
	 * @param listener
	 */
	public static void removeEventListener(String eventName,
			CEventListener listener) {
		HashMap<CEventListener, ServiceRegistration<EventHandler>> hashMaps = eventHasMaps
				.get(eventName);
		if (hashMaps == null) {
			return;
		}
		ServiceRegistration<EventHandler> sr = hashMaps.get(listener);
		if (sr == null)
			return;

		sr.unregister();
		hashMaps.remove(listener);
	}
}
