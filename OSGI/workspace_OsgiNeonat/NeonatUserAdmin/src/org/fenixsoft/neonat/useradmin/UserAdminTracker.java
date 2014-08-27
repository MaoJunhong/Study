package org.fenixsoft.neonat.useradmin;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.useradmin.UserAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * ��ʼ���û���͹���Ա�û�
 * 
 * @author IcyFenix
 * 
 */
public class UserAdminTracker extends ServiceTracker<UserAdmin, UserAdmin> {

	private BundleContext context;
	private static UserAdmin userAdmin;

	public UserAdminTracker(BundleContext context) {
		super(context, UserAdmin.class, null);
		this.context = context;
	}

	/**
	 * ��ȡUserAdmin����
	 */
	public static UserAdmin getUserAdmin() {
		if (userAdmin == null) {
			throw new IllegalAccessError("OSGi������û�пɹ����ʵ�UserAdmin����");
		}
		return userAdmin;
	}

	/**
	 * �ڸ��ٵ�OSGi�����д���UserAdmin����ʱ���������ṹ
	 */
	public UserAdmin addingService(ServiceReference<UserAdmin> reference) {
		userAdmin = context.getService(reference);
		new OrganizationRoleManager(userAdmin).initiate();

		return super.addingService(reference);
	}
}
