package com.alon.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

public class MD5Util {

	public static String md5(String src){
		return DigestUtils.md5Hex(src);
	}

	private static final String salt = "dragonKing";

	/**
	  * 方法表述: 第一次MD5加密，用于网络传输
	  * @Author 一股清风
	  * @Date 16:14 2019/5/17
	  * @param       inputPass
	  * @return java.lang.String
	*/
	public static String inputPassToFormPass(String inputPass){
		//避免在网络传输被截取然后反推出密码，所以在md5加密前先打乱密码
		String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}

	/**
	  * 方法表述: 第二次MD5加密，用于存储到数据库
	  * @Author 一股清风
	  * @Date 16:14 2019/5/17
	  * @param       formPass
	 * @param       salt
	  * @return java.lang.String
	*/
	public static String formPassToDBPass(String formPass, String salt) {
		String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}

	/**
	  * 方法表述: 合并
	  * @Author 一股清风
	  * @Date 16:14 2019/5/17
	  * @param       input
	 * @param       saltDB
	  * @return java.lang.String
	*/
	public static String inputPassToDbPass(String input, String saltDB){
		String formPass = inputPassToFormPass(input);
		String dbPass = formPassToDBPass(formPass, saltDB);
		return dbPass;
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname)) {
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			} else {
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
			}
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static void main(String[] args) {
//		System.out.println(inputPassToDbPass("123456",NumberUtil.getRandomString(6)));
//		System.out.println(inputPassToDbPass("123456",NumberUtil.getRandomString(6)));
		System.out.println(formPassToDBPass("123","1a2b3c4d"));
	}

}
