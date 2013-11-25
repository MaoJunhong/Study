package atp.report.mail.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
	Properties props = new Properties();

	public Configuration(String fileName) throws FileNotFoundException,
			IOException {
		props.load(new FileInputStream(fileName));
	}

	public String getPropertie(String name) {
		String res = props.getProperty(name);
		// System.err.println("name: " + name + ", res: " + res);
		if (res == null) {
			System.err.println(name + " Propertie not found!");
			System.exit(0);
			return "";
		}
		return res;
	}
}
