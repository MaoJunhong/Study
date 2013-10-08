package atp.report.mail.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import atp.report.mail.sender.AttachFileMailSender;
import atp.report.mail.utils.Configuration;
import atp.report.mail.utils.MailSenderInfo;

/**
 * send mail with Properties file
 * 
 * @author swrd
 * 
 */
public class MailSender {
	private static Configuration configuration;

	/**
	 * 
	 * @param args
	 *            PropertiesFileName, ToAddr, Subject, Content, Attach
	 *            files(must be absolute path)
	 */
	public static void main(String[] args) {
		assert (4 <= args.length && args.length <= 127);

		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setValidate(true);

		byte argsIndex = 0;
		try {
			configuration = new Configuration(args[argsIndex]);
			++argsIndex;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		mailInfo.setMailServerHost(configuration.getPropertie("ServerHost"));
		mailInfo.setMailServerPort(configuration.getPropertie("ServerPort"));

		mailInfo.setUserName(configuration.getPropertie("Name"));
		mailInfo.setPassword(configuration.getPropertie("Password"));

		mailInfo.setFromAddress(configuration.getPropertie("Name"));

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
