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
 * ������̳����֯����ɫ�͹���Ա�����û�����ʼ��ҳ�����Ȩ��<br>
 * 
 * @author IcyFenix
 */
public class OrganizationRoleManager {

	// UserAdmin Service
	private UserAdmin userAdmin;

	// �����û���
	public static Group ANONYMOUS, GUEST, REGISTER_MEMBER, ADMINISTRATOR;

	// ҳ��Ȩ�޿��Ʊ�
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
	 * ���������ؽ�ɫ
	 */
	private Role createRole(String name, int type) {
		Role role = userAdmin.createRole(name, type);
		return role == null ? userAdmin.getRole(name) : role;
	}

	/**
	 * ��ʼ��Ȩ���顢Ȩ�ޡ�Ĭ���û�����Ϣ
	 */
	public void initiate() {
		// ����Ȩ����
		ANONYMOUS = (Group) createRole("group.anonymous", Role.GROUP);
		GUEST = (Group) createRole("group.guest", Role.GROUP);
		REGISTER_MEMBER = (Group) createRole("group.registerMember", Role.GROUP);
		ADMINISTRATOR = (Group) createRole("group.administrator", Role.GROUP);

		// ����Ĭ���û�
		User guest = (User) createRole("guest", Role.USER);
		guest.getProperties().put(USER_IDENTIFIER, "guest");
		guest.getCredentials().put(USER_CREDENTIAL, "guest");
		User admin = (User) createRole("admin", Role.USER);
		admin.getProperties().put(USER_IDENTIFIER, "admin");
		admin.getCredentials().put(USER_CREDENTIAL, "123456");

		// ����Ȩ�޽ṹ
		REGISTER_MEMBER.addMember(ADMINISTRATOR);
		GUEST.addMember(REGISTER_MEMBER);
		ANONYMOUS.addMember(GUEST);

		// Ĭ���û�Ȩ��
		GUEST.addMember(guest);
		ADMINISTRATOR.addMember(admin);

		// ����ҳ�����Ȩ��
		CONTROL_MAP.put("/logon.jsp", ANONYMOUS); // �����û������¼
		CONTROL_MAP.put("/board.jsp", GUEST); // �����û�����鿴����
		CONTROL_MAP.put("/content.jsp", GUEST);
		CONTROL_MAP.put("/new.jsp", REGISTER_MEMBER); // ע���û�������������
		CONTROL_MAP.put("/admin/userManager.jsp", ADMINISTRATOR); // ����Ա�û��������ҳ��
	}

	/**
	 * ��֤�û��Ϸ���
	 */
	public User authenticate(String name, String pwd) {
		User user = userAdmin.getUser(USER_IDENTIFIER, name);
		if (user == null) {
			throw new SecurityException("�Ƿ����û���" + name);
		}
		if (!user.hasCredential(USER_CREDENTIAL, pwd)) {
			throw new SecurityException("��������룡");
		}
		return user;
	}

	/**
	 * ��֤�û�ҳ��Ȩ��
	 */
	public boolean checkAuthorization(User user, HttpServletRequest request) {
		Role role = CONTROL_MAP.get(request.getServletPath());
		Authorization author = userAdmin.getAuthorization(user);
		if (author.hasRole(role.getName())) {
			// ����֤���Ķ�����request�У�����������
			request.setAttribute(HttpContext.AUTHORIZATION, author);
			return true;
		} else {
			return false;
		}
	}
}
