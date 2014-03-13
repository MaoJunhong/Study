package com.sgx.download.encrypt;

import java.math.BigInteger;

public class Encrypter {

	private static String serverRND = "412A20DAEA0B329A56FF969B323E5D13";
	private static String Modulus = "DF252B5576265C27A51EB58B8A1041C19A4536CDBBC57CAFDED2BDC39D5EB0084979ABBCF0603728EE21497F9941CD1C72A46210A6DF7D4CA81C9C127E9BE0368FD7C8D70FB043A53597BDE58ABC5331C363DE9873782E26F14ADAAF724A90D078416DBFEF648C197B52B990F4188D05FE004950F468C79CE6EE8338A986BF4F";
	private static String Exponent = "10001";

	private String user;
	private String password;

	public Encrypter(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public String getIDToken1() {
		return user + "===" + serverRND;
	}

	public String getIDToken2() {
		return encryptVerifyStaticRSABlock(user.toUpperCase(), password,
				serverRND);
	}

	public static String encryptVerifyStaticRSABlock(String user,
			String password, String serverRND) {
		String hashValue = MD5.string2MD5(user + password);

		BigInteger biPwdHash = new BigInteger(hashValue, 16);
		BigInteger tmp = new BigInteger(serverRND, 16);
		biPwdHash = biPwdHash.xor(tmp);

		BigInteger plaintext = new BigInteger("0110"
				+ StringUtil.to16String(biPwdHash), 16);

		RSA rsa = new RSA();
		rsa.setPublic(Modulus, Exponent);

		return rsa.encrypt(plaintext);
	}

	public static void main(String[] args) {
		String IDToken1 = "a===C83E567AD13FE149B22117FD88927E6B";
		String IDToken2 = "18ee9079e5fc108f065ea9dd4e2ef510b2d36917ce757128e1e17e2d98cda0a301e19dfe321d19685103a2e1256bfb058562dbafbdf141db33449ebfcbe5fc03c6ef9556bf59b27df4abf9e92984e001d9b6db85e197af3e75ac002794c6d3a6802e91323106ef1a91f0d88db498d707f42a8eb69ba1f7f485eef01444274eb2";

		Encrypter tokenizer = new Encrypter("a", "a");
		System.err.println(IDToken1);
		System.err.println(tokenizer.getIDToken1());
		System.err.println(IDToken2);
		System.err.println(tokenizer.getIDToken2());
	}
}
