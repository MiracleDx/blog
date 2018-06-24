package com.dongx.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * FtpUtils
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-24 11:39
 * Modified by:
 */
@Slf4j
public class FtpUtils {

	/** ftp服务器地址 */
	private String hostname = "172.16.200.68";
	
	/** 默认端口 */
	private Integer port = 21;
	
	/** 登录帐号 */
	private String loginName = "ftpuser";
	
	/** 登录密码 */
	private String password = "dongx";


	FTPClient ftpClient;

	/**
	 * 初始化ftp服务
	 * @return
	 */
	private void initFtpClient() {
		// 连接FTP服务器
		try {
			ftpClient.connect(hostname, port);
			// 登录FTP服务器
			ftpClient.login(loginName, password);
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftpClient.disconnect();
				log.info("ftp server connect failed");
			} else {
				log.info("ftp server connect success");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param basePath FTP服务器基础目录
	 * @param filePath FTP服务器文件存放路径 文件的路径为basePath + filePath
	 * @param fileName 上传到FTP服务器的文件名
	 * @param input 输入流   
	 * @return
	 */
	public boolean uploadFile(String basePath, String filePath, String fileName, InputStream input) {
		boolean result = false;
		ftpClient = new FTPClient();
		try {
			initFtpClient();
			// 切换到上传目录
			result = changeWorkDirectory(basePath, filePath);
			if (result == false) {
				return result;
			}
			result = storeFile(fileName, input);
			ftpClient.logout();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnectClient();
		}
		return result;
	}

	/**
	 * 上传文章并写入文件
	 * @param basePath FTP服务器基础目录
	 * @param filePath FTP服务器文件存放路径 文件的路径为basePath + filePath
	 * @param fileName 上传到FTP服务器的文件名
	 * @param content 文章内容
	 * @return
	 */
	public boolean uploadContent(String basePath, String filePath, String fileName, String content) {
		boolean result = false;
		ftpClient = new FTPClient();
		InputStream is;
		try {
			initFtpClient();
			is = new ByteArrayInputStream(content.getBytes());
			// 切换到上传目录
			result = changeWorkDirectory(basePath, filePath);
			if (result == false) {
				return result;
			}
			result = storeFile(fileName, is);
			ftpClient.logout();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnectClient();
		}
		return result;
	}

	/**
	 * 读取ftp服务器上的文件内容
	 * @param basePath FTP服务器基础目录
	 * @param filePath FTP服务器文件存放路径 文件的路径为basePath + filePath
	 * @param fileName 上传到FTP服务器的文件名
	 * @return
	 */
	public String readContent(String basePath, String filePath, String fileName) {
		ftpClient = new FTPClient();
		String encoding = "UTF-8";
		StringBuilder builder = null;
		try {
			initFtpClient();
			changeWorkDirectory(basePath, filePath);
			ftpClient.enterLocalPassiveMode();
			InputStream is = ftpClient.retrieveFileStream(newString(fileName));
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, encoding));
			String line;
			builder = new StringBuilder(150);
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			reader.close();
			if (is != null) {
				is.close();
			}
			// 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
			ftpClient.getReply();
			ftpClient.logout();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnectClient();
		}
		
		String content = builder.toString();
		return content;
	}


	/**
	 * 删除ftp服务器上的文件
	 * @param basePath FTP服务器基础目录
	 * @param filePath FTP服务器文件存放路径 文件的路径为basePath + filePath
	 * @param fileName 上传到FTP服务器的文件名
	 * @return
	 */
	public boolean deleteFile(String basePath, String filePath, String fileName) {
		boolean result = false;
		ftpClient = new FTPClient();
		try {
			initFtpClient();
			result = changeWorkDirectory(basePath, filePath);
			if (result == false) {
				return result;
			}
			ftpClient.enterLocalPassiveMode();
			result = ftpClient.deleteFile(fileName);
			log.info("delete file success: {}", fileName);
			ftpClient.logout();
		} catch (Exception e) {
			log.info("delete file failed {}", fileName);
			e.printStackTrace();
		} finally {
			disConnectClient();
		}
		return result;
	}

	/**
	 * 下载ftp服务器上的文件
	 * @param remotePath FTP服务器上的相对路径
	 * @param fileName 要下载的文件名
	 * @param localPath 下载后保存到本地的路径
	 * @return
	 */
	public boolean downloadFile(String remotePath, String fileName, String localPath) {
		boolean result = false;
		ftpClient = new FTPClient();
		try {
			initFtpClient();
			ftpClient.changeWorkingDirectory(remotePath);
			FTPFile[] fs = ftpClient.listFiles();
			for (FTPFile file : fs) {
				if (StringUtils.equals(file.getName(), fileName)) {
					File localFile = new File(localPath + "/" + file.getName());
					ftpClient.enterLocalPassiveMode();
					OutputStream os = new FileOutputStream(localFile);
					ftpClient.retrieveFile(file.getName(), os);
					os.close();
				}
			}
			ftpClient.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			disConnectClient();
		}
		return result;
	}

	/**
	 * 切换工作目录
	 * @param basePath
	 * @param filePath
	 * @throws Exception
	 */
	private boolean changeWorkDirectory(String basePath, String filePath) throws Exception {
		boolean result = false;
		if (!ftpClient.changeWorkingDirectory(newString(basePath + filePath))) {
			log.info("changeDirectory failed: {}", basePath + filePath);
			// 如果目录不存在创建目录
			String[] dirs = filePath.split("/");
			String tempPath = basePath;
			for (String dir : dirs) {
				if (StringUtils.isEmpty(dir)) {
					continue;
				}
				tempPath += "/" + dir;
				if (!ftpClient.changeWorkingDirectory(newString(tempPath))) {
					log.info("changeDirectory failed: {}", tempPath);
					if (!ftpClient.makeDirectory(newString(tempPath))) {
						log.info("makeDirectory failed: {}", tempPath);
						return result;
					} else {
						ftpClient.changeWorkingDirectory(newString(tempPath));
					}
				}
			}
		} else {
			log.info("changeDirectory success: {}", basePath + filePath);
			result = true;
		}
		return result;
	}

	/**
	 * 向服务器中写入文件
	 * @param fileName
	 * @param is
	 * @return
	 * @throws Exception
	 */
	private boolean storeFile(String fileName, InputStream is) throws Exception {
		boolean result = false;
		// 设置上传文件类型为二进制类型
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.enterLocalPassiveMode();
		if (!ftpClient.storeFile(newString(fileName), is)) {
			log.info("upload file failed: {}", fileName);
			return result;
		} else {
			log.info("upload file success: {}", fileName);
			result = true;
		}
		is.close();
		return result;
	}

	/**
	 * 对字符串进行转码
	 * @param str 转码字符串 
	 * @return
	 * @throws Exception
	 */
	private String newString(String str) throws Exception {
		return new String(str.getBytes("GBK"),"iso-8859-1");
	}

	/**
	 * 关闭ftp服务
	 */
	private void disConnectClient() {
		if (ftpClient.isConnected()) {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}




