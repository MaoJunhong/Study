package atp.report.mail;

import atp.report.mail.sender.SimpleMailSender;
import atp.report.mail.utils.MailSenderInfo;
import atp.report.mail.utils.MailSenderInfo.MailServer;

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
		mailInfo.setContent("邮箱内容<div style='width:500px;height:100px;background-color:yellow;text-align:center;color:red;font-size:20px;border:solid red;'><b><a  href='http://1004798663.qzone.qq.com/'>欢迎来到我的主页！</a></b></div>");
		mailInfo.setAttachFileNames(new String[] { "/Users/swrd/Desktop/text.pdf" });

		// 这个类主要来发送邮件
		// SimpleMailSender.sendTextMail(mailInfo);// 发送文体格式
		SimpleMailSender.sendHtmlMail(mailInfo);// 发送html格式
		// AttachFileMailSender.sendHtmlMail(mailInfo);
	}
}
