package com.huice.base;

import com.huice.uitl.XmlParser;

public class Config {
	
	public static String debugBrowser;
	public static int browserWaitTime;
	public static int objectWaitTime;
	public static int pageWaitTime;
	public static String objectRespository;
	public static int retryTimes;
	public static String SMTPserver;
	public static String from;
	public static String username;
	public static String password;
	public static String to;
	public static String copyto;
	public static String filename;
	public static String subject;
	
	static{
		XmlParser webXp = new XmlParser("config/Config.xml");
		debugBrowser = webXp.getElementText("/config/debugBrowser");
		browserWaitTime = Integer.valueOf(webXp.getElementText("/config/browserWaitTime"));
		objectWaitTime = Integer.valueOf(webXp.getElementText("/config/objectWaitTime"));
		pageWaitTime = Integer.valueOf(webXp.getElementText("/config/pageWaitTime"));
		objectRespository = webXp.getElementText("/config/objectRespoistory");
		retryTimes = Integer.valueOf(webXp.getElementText("/config/retryTimes"));
		SMTPserver = webXp.getElementText("/config/mail/SMTPserver");
		from = webXp.getElementText("config/mail/from");
		username = webXp.getElementText("/config/mail/username");
		password = webXp.getElementText("/config/mail/password");
		to = webXp.getElementText("/config/mail/to");
		copyto = webXp.getElementText("/config/mail/copyto");
		filename = webXp.getElementText("/config/mail/filename");
		subject = webXp.getElementText("config/mail/subject");
	}

}
