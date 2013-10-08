package atp.report.mail.utils;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MailSenderInfo {
	public enum MailServer {
		QQ, l63
	};

	public static Map<MailServer, InetSocketAddress> serverManager = new HashMap<MailServer, InetSocketAddress>();

	static {
		serverManager.put(MailServer.QQ, new InetSocketAddress("smtp.qq.com",
				25));
		serverManager.put(MailServer.l63, new InetSocketAddress("smtp.163.com",
				25));
	}

	public static MailServer getMailServer(String username) {
		if (username.contains("qq") || username.contains("QQ")) {
			return MailServer.QQ;
		} else if (username.contains("163")) {
			return MailServer.l63;
		}

		return null;
	}

	// 发送邮件的服务器的IP和端口
	private String mailServerHost;
	private String mailServerPort = "25";

	// 登陆邮件发送服务器的用户名和密码
	private String userName;
	private String password;

	// 是否需要身份验证
	private boolean validate = false;

	// 邮件发送者的地址
	private String fromAddress;
	// 邮件接收者的地址
	private String toAddress;
	private List<String> moreTo;

	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;
	// 邮件附件的文件名
	private String[] attachFileNames;

	public MailSenderInfo() {
		moreTo = new ArrayList<String>();
	}

	/**
	 * 获得邮件会话属性
	 */
	public Properties getProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", this.mailServerHost);
		properties.put("mail.smtp.port", this.mailServerPort);
		properties.put("mail.smtp.auth", validate ? "true" : "false");

		return properties;
	}

	public void setMailServer(MailServer server) {
		setMailServerHost(serverManager.get(server).getHostName());
		setMailServerPort(serverManager.get(server).getPort() + "");
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public List<String> getMoreTo() {
		return moreTo;
	}

	public void addToAddress(String toAddress) {
		moreTo.add(toAddress);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] attachFileNames) {
		this.attachFileNames = attachFileNames;
	}

}
