package com.demo.common.util.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * Created by wupingping on 2016/08/02.
 */
public class SFTPUtil {
	private static final Logger log = LoggerFactory.getLogger(SFTPUtil.class);

	static Session session = null;
	static Channel channel = null;

	/**
	 * 覆盖写入
	 */
	public final static int OVERWRITE = ChannelSftp.OVERWRITE;

	/**
	 * 断点续传
	 */
	public final static int RESUME = ChannelSftp.RESUME;

	/**
	 * 追加写入
	 */
	public final static int APPEND = ChannelSftp.APPEND;

	/**
	 * 建立SFTP连接
	 *
	 * @param sftpDetails
	 * @return
	 * @throws JSchException
	 */
	public static ChannelSftp getChannel(SFTPDetails sftpDetails) throws JSchException {

		String ftpHost = sftpDetails.getReqHost();
		// Integer port = sftpDetails.getReqPort();
		String ftpUserName = sftpDetails.getReqUserName();
		String ftpPassword = sftpDetails.getReqPassword();

		Integer ftpPort = sftpDetails.getDefaultPort();
		JSch jsch = new JSch(); // 创建JSch对象
		if (sftpDetails.getReqType() == 0) {
			session = jsch.getSession(ftpUserName, ftpHost, ftpPort); // 根据用户名，主机ip，端口获取一个Session对象
		} else {
			session = jsch.getSession(ftpUserName, ftpHost);
		}
		log.debug("Session created.");
		if (ftpPassword != null) {
			session.setPassword(ftpPassword); // 设置密码
		}
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config); // 为Session对象设置properties
		session.setTimeout(sftpDetails.getTimeOut()); // 设置timeout时间
		session.connect(); // 通过Session建立链接
		log.debug("Session connected.");

		log.debug("Opening Channel.");
		channel = session.openChannel("sftp"); // 打开SFTP通道
		channel.connect(); // 建立SFTP通道的连接
		log.debug("Connected successfully to ftpHost = " + ftpHost + ",as ftpUserName = " + ftpUserName
				+ ", returning: " + channel);
		return (ChannelSftp) channel;
	}

	/**
	 * 关闭SFTP连接
	 *
	 * @throws Exception
	 */
	public static void closeChannel() throws Exception {
		if (channel != null) {
			channel.disconnect();
		}
		if (session != null) {
			session.disconnect();
		}
	}

	/**
	 * 上传文件
	 *
	 * @param channelSftp
	 *            文件通道
	 * @param localpath
	 *            本地文件路径
	 * @param destPath
	 *            目标文件路径
	 * @param fileName
	 *            文件名
	 * @param type
	 *            写入方式 0：域名 1：IP方式
	 * @param desc
	 *            文件内容描述 日志跟踪时打印信息
	 */
	public static void put(ChannelSftp channelSftp, String localpath, String destPath, String fileName, int type,
			String desc) throws Exception {
		OutputStream out = null;
		InputStream in = null;
		try {
			File file = new File(localpath);
			if (!file.exists()) {
				channelSftp.quit();
				return;
			}
			long fileSize = file.length();
			// 1.设置输出流
			try {
				Vector<?> vector = channelSftp.ls(destPath);
				if (vector.size() < 1) {
					channelSftp.mkdir(destPath);
				}
			} catch (Exception e) {
				log.debug("directionary is not exist");
				channelSftp.mkdir(destPath);
			}
			out = channelSftp.put(destPath + "/" + fileName, new SFTPMonitor(fileSize, desc), type);
			// 2.设置写入块大小
			byte[] buff = new byte[1024 * 1024]; // 设定每次传输的数据块大小为1M
			int read;
			if (out != null) {
				log.debug("Start to read input stream");
				in = new FileInputStream(localpath);
				do {
					read = in.read(buff, 0, buff.length);
					if (read > 0) {
						out.write(buff, 0, read);
					}
					out.flush();
				} while (read >= 0);
				log.debug("input stream read done.");
			}
		} catch (SftpException e) {
			log.error("SFTP OCCURED SftpException", e);
			throw new Exception(e);
		} catch (IOException e) {
			log.error("SFTP OCCURED IOException", e);
			throw new Exception(e);
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
			// 3.退出连接
			channelSftp.quit();
		}
	}

	/**
	 * 传送文件流到服务器
	 * 
	 * @param channelSftp
	 *            文件通道
	 * @param inputStream
	 *            文件输入流
	 * @param destPath
	 *            目标路径
	 * @param fileName
	 *            文件名
	 * @param type
	 *            传输方式
	 * @param desc
	 *            描述
	 */
	public static boolean put(ChannelSftp channelSftp, InputStream inputStream, String destPath, String fileName,
			int type, String desc) {
		OutputStream out = null;
		// 1.校验服务器端
		long fileSize = 0;
		boolean flag = false;
		try {
			fileSize = inputStream.available();
			/** add this code by gauler **/
			try {
				channelSftp.cd(destPath);
				flag = true;
			} catch (SftpException sException) {
				log.error("ftp server don't have this path,then do make dir,create path is :" + destPath);
				flag = false;
			}
			if (!flag) {
				channelSftp.mkdir(destPath);
			}
			/** add code end, comment out this code by gauler **/
			// Vector<?> vector = channelSftp.ls(destPath);
			// if (vector.size() < 1) {
			// channelSftp.mkdir(destPath);
			// }
		} catch (SftpException e) {
			log.error("获取服务器文件列表端异常");
			channelSftp.quit();
			return false;
		} catch (IOException e) {
			log.error("读取文件流异常", e);
			channelSftp.quit();
			return false;
		}
		try {
			out = channelSftp.put(destPath + "/" + fileName, new SFTPMonitor(fileSize, desc), type);// 获取输出流
		} catch (SftpException e) {
			log.error("获取SFTP输出流异常{}", e);
			channelSftp.quit();
			return false;
		}
		// 2.写入到服务器端
		byte[] buff = new byte[1024 * 1024];
		int read;
		try {
			if (out != null) {
				do {
					read = inputStream.read(buff, 0, buff.length);
					if (read > 0) {
						out.write(buff, 0, read);
					}
					out.flush();
				} while (read >= 0);
			}

		} catch (IOException e) {
			log.error("写文件到服务器端发生异常{}", e);
			channelSftp.quit();
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error("关闭输出文件流异常,{}", e);
				}
			}
		}
		// 3.退出连接
		channelSftp.quit();
		return true;
	}

	/**
	 * 下载文件
	 *
	 * @param channelSftp
	 *            文件通道
	 * @param localpath
	 *            本地文件路径
	 * @param destPath
	 *            目标文件路径
	 * @param type
	 *            写入方式
	 * @param desc
	 *            文件内容描述
	 */
	public static void get(ChannelSftp channelSftp, String localpath, String destPath, int type, String desc)
			throws Exception {
		OutputStream out = null;
		InputStream in = null;
		try {
			File file = new File(localpath);
			File fileParent = file.getParentFile();
			if (fileParent != null) {
				fileParent.mkdirs();
			}
			long fileSize = file.length();
			out = new FileOutputStream(file);
			// 1.设置输入流
			in = channelSftp.get(destPath, new SFTPMonitor(fileSize, desc));
			// 2.设置写入块大小
			byte[] buff = new byte[1024 * 1024];
			int read;
			if (in != null) {
				System.out.println("Start to read input stream");
				do {
					read = in.read(buff, 0, buff.length);
					if (read > 0) {
						out.write(buff, 0, read);
					}
					out.flush();
				} while (read >= 0);
				System.out.println("input stream read done.");
			}
		} catch (SftpException e) {
			log.error("SFTP OCCURED SftpException", e);
			throw e;
		} catch (IOException e) {
			log.error("SFTP OCCURED IOException", e);
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
			// 3.退出连接
			channelSftp.quit();
		}
	}

}
