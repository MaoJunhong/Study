package atp.report.mail.main;

import atp.report.mail.sender.AttachFileMailSender;
import atp.report.mail.utils.MailSenderInfo;
import atp.report.mail.utils.MailSenderInfo.MailServer;

@Deprecated
public class Main {
	public static void main(String[] args) {

		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServer(MailServer.QQ);
		mailInfo.setValidate(true);

		mailInfo.setUserName("swrdlgc@qq.com");
		mailInfo.setPassword("");

		mailInfo.setFromAddress("swrdlgc@qq.com");
		mailInfo.setToAddress("swrdlgc@qq.com");

		mailInfo.setSubject("邮箱标题");
		mailInfo.setContent("邮箱内容");
		mailInfo.setAttachFileNames(new String[] { "/Users/swrd/Desktop/fruit.jpg" });

		// 这个类主要来发送邮件
		// SimpleMailSender.sendTextMail(mailInfo);// 发送文体格式
		// SimpleMailSender.sendHtmlMail(mailInfo);// 发送html格式
		AttachFileMailSender.sendHtmlMail(mailInfo);
	}
}
