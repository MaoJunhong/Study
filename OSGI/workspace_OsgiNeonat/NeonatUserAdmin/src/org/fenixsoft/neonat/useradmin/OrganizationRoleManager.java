package org.fenixsoft.neonat.useradmin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.http.HttpContext;
import org.osgi.service.useradmin.Authorization;
import org.osgi.service.useradmin.Group;
import org.osgi.service.useradmin.Role;
import org.osgi.service.useradmin.User;
import org.osgi.service.useradmin.UserAdmin;

/**
 * 创建论坛的组织、角色和管理员基础用户，初始化页面管理权限<br>
 * 
 * @author IcyFenix
 */
public class OrganizationRoleManager {

	// UserAdmin Service
	private UserAdmin userAdmin;

	// 基础用户组
	public static Group ANONYMOUS, GUEST, REGISTER_MEMBER, ADMINISTRATOR;

	// 页面权限控制表
	public static final Map<String, Role> CONTROL_MAP = new HashMap<String, Role>();

	public static final String USER_CREDENTIAL = "password";
	public static final String USER_IDENTIFIER = "username";

	OrganizationRoleManager(UserAdmin userAdmin) {
		this.userAdmin = userAdmin;
	}

	OrganizationRoleManager() {
		this(UserAdminTracker.getUserAdmin());
	}

	/**
	 * 创建并返回角色
	 */
	private Role createRole(String name, int type) {
		Role role = userAdmin.createRole(name, type);
		return role == null ? userAdmin.getRole(name) : role;
	}

	/**
	 * 初始化权限组、权限、默认用户等信息
	 */
	public void initiate() {
		// 创建权限组
		ANONYMOUS = (Group) createRole("group.anonymous", Role.GROUP);
		GUEST = (Group) createRole("group.guest", Role.GROUP);
		REGISTER_MEMBER = (Group) createRole("group.registerMember", Role.GROUP);
		ADMINISTRATOR = (Group) createRole("group.administrator", Role.GROUP);

		// 创建默认用户
		User guest = (User) createRole("guest", Role.USER);
		guest.getProperties().put(USER_IDENTIFIER, "guest");
		guest.getCredentials().put(USER_CREDENTIAL, "guest");
		User admin = (User) createRole("admin", Role.USER);
		admin.getProperties().put(USER_IDENTIFIER, "admin");
		admin.getCredentials().put(USER_CREDENTIAL, "123456");

		// 构造权限结构
		REGISTER_MEMBER.addMember(ADMINISTRATOR);
		GUEST.addMember(REGISTER_MEMBER);
		ANONYMOUS.addMember(GUEST);

		// 默认用户权限
		GUEST.addMember(guest);
		ADMINISTRATOR.addMember(admin);

		// 构造页面访问权限
		CONTROL_MAP.put("/logon.jsp", ANONYMOUS); // 匿名用户允许登录
		CONTROL_MAP.put("/board.jsp", GUEST); // 来宾用户允许查看帖子
		CONTROL_MAP.put("/content.jsp", GUEST);
		CONTROL_MAP.put("/new.jsp", REGISTER_MEMBER); // 注册用户允许新增帖子
		CONTROL_MAP.put("/admin/userManager.jsp", ADMINISTRATOR); // 管理员用户允许管理页面
	}

	/**
	 * 验证用户合法性
	 */
	public User authenticate(String name, String pwd) {
		User user = userAdmin.getUser(USER_IDENTIFIER, name);
		if (user == null) {
			throw new SecurityException("非法的用户：" + name);
		}
		if (!user.hasCredential(USER_CREDENTIAL, pwd)) {
			throw new SecurityException("错误的密码！");
		}
		return user;
	}

	/**
	 * 验证用户页面权限
	 */
	public boolean checkAuthorization(User user, HttpServletRequest request) {
		Role role = CONTROL_MAP.get(request.getServletPath());
		Authorization author = userAdmin.getAuthorization(user);
		if (author.hasRole(role.getName())) {
			// 把验证过的对象传入request中，共后续访问
			request.setAttribute(HttpContext.AUTHORIZATION, author);
			return true;
		} else {
			return false;
		}
	}
}
