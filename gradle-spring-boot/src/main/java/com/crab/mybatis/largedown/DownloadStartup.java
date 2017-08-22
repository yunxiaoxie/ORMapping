package com.crab.mybatis.largedown;

/**
 * 
 * @author annegu
 * @since 2009-07-16
 *
 */
public class DownloadStartup {

	private static final String encoding = "utf-8";
	public static void main(String[] args) {

		DownloadTask downloadManager = new DownloadTask();
		
//		String urlStr = "http://yztele4.skycn.com/down/Thunder5.9.3.951.zip";
		String urlStr = "http://mirrors.tuna.tsinghua.edu.cn/apache/tomcat/tomcat-9/v9.0.0.M19/bin/apache-tomcat-9.0.0.M19-deployer.zip";
//		String urlStr = "http://www.dianping.com/";
		
		downloadManager.setSleepSeconds(5);
		String downladFileName = downloadManager.download(urlStr, encoding);
		System.out.println("Download file is " + downladFileName + ".");		
	}
	
}
