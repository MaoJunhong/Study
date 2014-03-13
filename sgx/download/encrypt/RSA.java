package com.sgx.download.encrypt;

import java.math.BigInteger;

public class RSA {

	private BigInteger m;
	private BigInteger e;

	public String encrypt(BigInteger plain) {
		BigInteger A = pkcs1pad2(plain, (m.bitLength() + 7) >> 3);
		if (A == null) {
			return null;
		}

		BigInteger D = A.modPow(e, m);
		if (D == null) {
			return null;
		}

		return StringUtil.to16String(D);
	}

	private BigInteger pkcs1pad2(BigInteger F, int A) {
		double I = Math.ceil(F.bitLength() / 8.);
		if (A < I + 11 + 4) {
			return null;
		}

		int B = (int) (A - I - 7);
		byte[] E = new byte[B + 6];
		E[0] = (byte) 0;
		E[1] = (byte) 2;
		E[2] = (byte) 255;
		E[3] = (byte) 255;
		E[4] = (byte) 255;
		E[5] = (byte) 255;

		int G = 0;
		int D = 6;
		while (D < E.length) {
			G = 0;
			while (G == 0) {
				G = (int) Math.floor(Math.random() * 255);
			}
			E[D++] = (byte) G;
		}
		BigInteger H = new BigInteger(E);
		String C = StringUtil.to16String(H) + "00" + StringUtil.to16String(F);

		return new BigInteger(C, 16);
	}

	public void setPublic(String modulus, String exponent) {
		assert (modulus != null && modulus.length() > 0);
		assert (exponent != null && exponent.length() > 0);

		this.m = new BigInteger(modulus, 16);
		this.e = new BigInteger(exponent, 16);
	}
}
