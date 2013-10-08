package atp.report.mail.main;

import atp.report.mail.sender.AttachFileMailSender;
import atp.report.mail.utils.MailSenderInfo;

public class MailSenderWithValidate {
	/**
	 * 
	 * @param args
	 *            FromAddr, Password, ToAddr, Subject, Content, Attach
	 *            files(must be absolute path)
	 */
	public static void main(String[] args) {
		assert (5 <= args.length && args.length <= 127);

		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setValidate(true);

		byte argsIndex = 0;
		mailInfo.setMailServer(MailSenderInfo.getMailServer(args[argsIndex]));
		mailInfo.setUserName(args[argsIndex]);
		mailInfo.setFromAddress(args[argsIndex]);
		++argsIndex;

		mailInfo.setPassword(args[argsIndex]);
		++argsIndex;

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
