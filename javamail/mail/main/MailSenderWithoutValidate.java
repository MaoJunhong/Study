package atp.report.mail.main;

import atp.report.mail.sender.AttachFileMailSender;
import atp.report.mail.utils.MailSenderInfo;
import atp.report.mail.utils.MailSenderInfo.MailServer;

@Deprecated
public class MailSenderWithoutValidate {

	/**
	 * 
	 * @param args
	 *            ToAddr, Subject, Content, Attach files(must be absolute path)
	 */
	public static void main(String[] args) {
		assert (3 <= args.length && args.length <= 127);

		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setValidate(true);

		// must be changed!
		mailInfo.setMailServer(MailServer.QQ);
		mailInfo.setUserName("swrdlgc@qq.com");
		mailInfo.setPassword("*********");
		mailInfo.setFromAddress("swrdlgc@qq.com");

		byte argsIndex = 0;
		mailInfo.setToAddress(args[argsIndex]);
		++argsIndex;

		mailInfo.setSubject(args[argsIndex]);
		++argsIndex;
		mailInfo.setContent(args[argsIndex]);
		++argsIndex;

		if (args.length > argsIndex) {
			String arg[] = new String[args.length - argsIndex];
			System.arraycopy(args, argsIndex, arg, 0, arg.length);
			mailInfo.setAttachFileNames(arg);
		}

		AttachFileMailSender.sendHtmlMail(mailInfo);
	}
}
