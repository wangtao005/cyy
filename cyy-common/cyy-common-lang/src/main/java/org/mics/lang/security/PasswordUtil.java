package org.mics.lang.security;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 密码工具类
 * @author mics
 * @date 2020年6月10日
 * @version  1.0
 */
public class PasswordUtil {
	
	/**
	 * 一分钟不变密码
	 * @author mics
	 * @date 2019年11月9日
	 * @version  1.0
	 */
	public static String getTimePassword() {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String passwordStr = simpleDateFormat.format(date);
		byte[] pwdByte = DigestUtil.sha1(passwordStr.getBytes(),"mics".getBytes());
		String password = new String(pwdByte);
		return password;
	}
	
	/**
	 * 生成安全的密码，根据salt并经过1024次 sha-1 hash
	 */
	public static String encryptPassword(String plainPassword, String salt) {
	    byte[] hashPassword = DigestUtil.sha1(plainPassword.getBytes(), salt.getBytes());
	    return salt + EncodeUtil.encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 需要验证的明文密码
	 * @param salt          盐
	 * @param password      数据库密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String salt, String password) {
	    byte[] hashPassword = DigestUtil.sha1(plainPassword.getBytes(), salt.getBytes());
	    return password.equals(salt + EncodeUtil.encodeHex(hashPassword));
	}
}
