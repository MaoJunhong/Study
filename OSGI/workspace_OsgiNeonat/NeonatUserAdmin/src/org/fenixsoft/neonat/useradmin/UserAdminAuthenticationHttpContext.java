package org.fenixsoft.neonat.useradmin;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.osgi.internal.signedcontent.Base64;
import org.osgi.service.http.HttpContext;
import org.osgi.service.useradmin.User;

public class UserAdminAuthenticationHttpContext implements HttpContext {

	private HttpContext httpContext;

	public UserAdminAuthenticationHttpContext(HttpContext hc) {
		httpContext = hc;
	}

	private void forbidden(HttpServletResponse response, String msg) {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);/*
		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write("<h1>��ֹ����</h1><br />");
			response.getWriter().write(msg);
			response.getWriter().flush();
		} catch (IOException e) {
		}*/
	}

	@Override
	public boolean handleSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String auth = request.getHeader("Authorization");
		if (auth != null) {
			auth = new String(Base64.decode(auth.substring(6).getBytes()));
			try {
				OrganizationRoleManager orm = new OrganizationRoleManager();
				// ��֤�û��Ϸ���
				User user = orm.authenticate(auth.split(":")[0], auth.split(":")[1]);
				// ��֤ҳ��Ȩ��
				boolean result = orm.checkAuthorization(user, request);
				if (!result) {
					forbidden(response, "�û�Ȩ�޲���");
					return false;
				} else {
					return true;
				}
			} catch (SecurityException e) {
				forbidden(response, e.getMessage());
				return false;
			}
		} else {
			// ����HTTP Basic��֤����
			response.setHeader("WWW-Authenticate", "Basic realm=\"Neonat BBS\"");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
	}

	@Override
	public URL getResource(String name) {
		return httpContext.getResource(name);
	}

	@Override
	public String getMimeType(String name) {
		return httpContext.getMimeType(name);
	}

}
