package org.fenixsoft.neonat.useradmin;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.useradmin.UserAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * 初始化用户组和管理员用户
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
	 * 获取UserAdmin服务
	 */
	public static UserAdmin getUserAdmin() {
		if (userAdmin == null) {
			throw new IllegalAccessError("OSGi容器中没有可供访问的UserAdmin服务");
		}
		return userAdmin;
	}

	/**
	 * 在跟踪到OSGi容器中存在UserAdmin服务时创建基础结构
	 */
	public UserAdmin addingService(ServiceReference<UserAdmin> reference) {
		userAdmin = context.getService(reference);
		new OrganizationRoleManager(userAdmin).initiate();

		return super.addingService(reference);
	}
}
