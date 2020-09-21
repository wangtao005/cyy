package org.mics.lang.file;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.mics.lang.exception.FileException;

public class FtpUtil {

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 * @throws IOException
	 */
	public static Map<String, Object> uploadFile(String path,  String filename,  InputStream input) throws IOException {

		String[] str = filename.split("\\.");
		String filetype = str[str.length - 1];
		filename = getNameKey() + "." + filetype;

		Properties properties = new Properties();
		properties.load(FtpUtil.class.getResourceAsStream("/ftp.properties"));

		boolean success = false;
		Map<String, Object> map = new HashMap<String, Object>();
		String filepath = "";
		FTPClient ftp = new FTPClient();
		String url = properties.getProperty("url");
		int port = Integer.parseInt(properties.getProperty("port"));
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		try {
			int reply;
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();

			// 判断reply的code是否正常，一般正常为2开头的code
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				map.put("status", success);
				map.put("filepath", filepath);
				return map;
			}
			ftp.makeDirectory(path);
			ftp.changeWorkingDirectory(path);
			// ftp上传文件是以文本形式传输的，所以多媒体文件会失真，需要转为二进制形式传输
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			// 转码后可以文件名可以为中文
			ftp.storeFile(new String(filename.getBytes("GBK"), "iso-8859-1"), input);

			input.close();
			ftp.logout();
			success = true;
			if (!path.isEmpty()) {
				filepath = "/" + path;
			}
			filepath = filepath + "/" + filename;
			map.put("status", success);
			map.put("filepath", filepath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (Exception ioe) {

				}
			}
		}
		return map;
	}

	/**
	 * Description: 向FTP服务器下载文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param localFileName
	 *            下载后文件名
	 * @return 成功返回true，否则返回false
	 * @throws IOException
	 */
	public static InputStream downFile(String remoteFileName, String localFileName, HttpServletResponse response) {
		Properties properties = new Properties();
		try {
			properties.load(FtpUtil.class.getResourceAsStream("/ftp.properties"));
		} catch (IOException e1) {
			throw new  FileException("加载文件异常");
		}

		BufferedOutputStream outStream = null;
		InputStream in= null;
		boolean success = false;
		FTPClient ftp = new FTPClient();
		String url = properties.getProperty("url");
		int port = Integer.parseInt(properties.getProperty("port"));
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		try {
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			ftp.changeWorkingDirectory(remoteFileName);
			outStream = new BufferedOutputStream(new FileOutputStream(localFileName));   
			success = ftp.retrieveFile(remoteFileName, outStream);
			in = ftp.retrieveFileStream(new String(remoteFileName.getBytes("UTF-8"), "iso-8859-1"));
			
			if (success == true) {
				return in;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != outStream) {
			}
		}
		if (success == false) {
		}
		return in;
	}

	/**
	 * Description: 向FTP服务器删除文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @return 成功返回true，否则返回false
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public static boolean deleteFile(String filepath) throws IOException {

		Properties properties = new Properties();
		properties.load(FtpUtil.class.getResourceAsStream("/ftp.properties"));

		boolean success = false;
		FTPClient ftp = new FTPClient();
		String url = properties.getProperty("url");
		int port = Integer.parseInt(properties.getProperty("port"));
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		try {
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			success = ftp.deleteFile(filepath);// 删除
			ftp.logout();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (Exception ioe) {

				}
			}
		}
		return success;
	}

	/**
	 * 获得随即生成的文件名
	 * 
	 * @return
	 */
	public static String getNameKey() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		NumberFormat nf = new DecimalFormat("0000");
		Random random = new Random();
		return new StringBuffer(sdf.format(new Date())).append(nf.format(random.nextInt(1000))).toString();
	}

}
