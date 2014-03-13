package com.sgx.download.encrypt;

import java.math.BigInteger;

public class StringUtil {
	public static String to16String(BigInteger value) {
		return to16String(value.toString(16));
	}

	public static String to16String(String value) {
		String str = value;
		if ((value.length() & 1) != 0) {
			str = "0" + str;
		}
		return str;
	}

}
