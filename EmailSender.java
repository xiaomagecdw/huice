package com.huice.uitl;

import java.lang.reflect.Field;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import com.huice.base.Parameter;
import com.huice.base.log;

public class EmailSender {
	
	public static String SMTP = "smtp";
	public static String SUBJECT = "subject";
	public static String FROM = "from";
	public static String SENDER_NAME = "senderName";
	public static String SET_TO = "setTo";
	public static String COPY_TO = "copyTo";
	public static String USER_NAME = "userName";
	public static String PASSWORD = "password";
	public static String FILE_PATH = "filePath";
	private MimeMessage mimeMsg; //MIME邮件对象
	private Session session; //邮件会话对象
	private Properties props; //系统属性
	private Multipart mp; //Multipart对象，邮件内容。标题，附件等内容均添加到其中后生成MimeMessage对象
	private String smtp = null; //发件人SMTP服务地址
	private String subject = null; //邮件主题
	private String from = null; //发件人邮箱地址
	private String senderName = null; //收件显示名称
	private String setTo = null; //收件人邮箱地址，多个地址时用英文逗号隔开
	private String copyTo = null; //抄送人邮箱地址，多个地址时用英文逗号隔开
	private String userName = null; //发件人邮箱用户名
	private String password = null; //发件人邮箱密码
	private String filePath = null; //附件路劲
	
	/**
	 * Constructor
	 * @param parameters 邮件发送的配置参数
	 */
	
	public EmailSender(Parameter parameters){
		init(parameters);
	}
	
	/**
	 * 调用sendOut方法完成邮件发送
	 * @throws MessageingException
	 */
	
	public boolean send(String text, String encoding){
		boolean needCopyto = true;
		setSmtpHost();          //设置smtp服务地址
		createMimeMessage();  //创建会话
		setNeedAuth(true);   //设置验证
		if(!setSubject()) return false;		//设置主题
		if(!setBody(text, encoding)) return false;   	//设置正文
		
		if(!(filePath == null || filePath.equals(""))){			//设置附件
			if(!addFileAffix()) return false;
		}
		
		if(!setTo()) return false;  //设置收件人
		
		if(copyTo == null || copyTo.equals("")){		//设置抄送人
			needCopyto = false; 	
		}else{
			if(!setCopyTo()) return false;
		}
		
		if(!setFrom()) return false;		//设置发件人
		
		if(!sendOut(needCopyto)) return false; 	//调用发送函数
		
		return true;
		
	}
	
	
	@SuppressWarnings("null")
	protected void init(Parameter parameters){
		//检查是否缺少发送邮件的必要参数
		String[] argNames = {"smtp", "from", "setTo", "userName", "password"};
		for(String name : argNames){
			String value = parameters.get(name);
			if(value == null || value.equals("")){
			System.out.println("缺少发送邮件的必要参数:"+name);
		}
	}
	//通过发射初始化参数列表
	for(String name : parameters.asMap().keySet()){
		String value = parameters.get(name);
		if(value != null || value.equals("")){
			try {
				Field filed = this.getClass().getDeclaredField(name);
				filed.set(this, value);
			} catch (NoSuchFieldException e) {
				System.out.println("EmailSender不存在如下参数："+name);
			}catch(SecurityException e){
				System.out.println("初始化EmailSender参数异常！");
			}catch (IllegalArgumentException e) {
				System.out.println("EmailSender以下参数，参数值类型不正确："+name);
			}catch (IllegalAccessException e) {
				System.out.println("初始化EmailSender参数异常！");
			}
		}
	}
}
	
	/**
	 * 设置邮件发送服务器
	 * @param hostName String
	 */
	
	protected void setSmtpHost(){
		log.info("设置系统属性：mail.smtp.host = "+smtp);
		if(props == null)
			props = System.getProperties();	//获得系统属性对象
			props.put("mail.smtp.host", smtp);  //设置SMTP主机	
	}
	
	/**
	 * 创建MIME邮件对象
	 * @return
	 */
	protected boolean createMimeMessage(){
		try {
			log.info("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props,null);		//获得邮件会话对象
		} catch (Exception e) {
			System.out.println("获取邮件会话对象时发生错误！");
			return false;
		}
		
		log.info("准备创建MIME邮件对象！");
		try {
			mimeMsg = new  MimeMessage(session);		//创建MIME邮件对象
			mp = new MimeMultipart("related");
			return true;
		} catch (Exception e) {
			System.out.println("创建MIME邮件对象失败");
			return false;
		}
	}
	
	/**
	 * 设置SMTP是否需要验证
	 * @param need
	 */
	
	protected void setNeedAuth(boolean need){
		log.info("设置SMTP身份认证：mail.smtp.auth = "+need);
		if(props == null) props = System.getProperties();
		if(need){
			props.put("mail.smtp.auth", "true");
		}else{
			props.put("mail.smtp.auth", "false");
		}
	}
	
	/**
	 * 设置邮件主题
	 * @param mailSubject
	 * @return
	 */
	
	protected boolean setSubject(){
		log.info("设置邮件主题");
		try {
			if(subject != null || subject.equals("")){
				mimeMsg.setSubject(subject);
			}else  mimeMsg.setSubject(" ");
			return true;
		} catch (Exception e) {
			System.out.println("设置邮件主题发生错误！");
			return false;
		}
	}
	
	/**
	 * 设置邮件正文
	 * @param mailBody String
	 */
	
	protected boolean setBody(String text, String encoding){
		try {
			MimeBodyPart textBody =  new MimeBodyPart();
			textBody.setContent(text, "text/html;charset = gb2312");
			mp.addBodyPart(textBody);
			return true;
		} catch (Exception e) {
			System.out.println("设置邮件正文时发生错误！");
			return false;
		}
	}
	
	/**
	 * 添加附件
	 * @param filename String
	 */
	
	protected boolean addFileAffix(){
		log.info("增加邮件附件："+filePath);
		try {
			BodyPart bodyp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filePath);
			bodyp.setDataHandler(new DataHandler(fileds));
			bodyp.setFileName(fileds.getName());
			mp.addBodyPart(bodyp);
			return true;
		} catch (Exception e) {
			System.out.println("增加邮件附件："+filePath+"是发生错误！");
			return false;
		}
	}
	
	/**
	 * 设置发信人
	 * @param from String 
	 */
	
	protected boolean setFrom(){
		log.info("设置发信人");
		try {
			if(senderName == null || senderName.equals("")){
				mimeMsg.setFrom(new InternetAddress(from));
			}else mimeMsg.setFrom(new InternetAddress(from,senderName));
			return true;
		} catch (Exception e) {
			System.out.println("设置发件人时异常！");
			return false;
		}
	}
	
	/**
	 * 设置收件人
	 * @param to String
	 */
	
	protected boolean setTo(){
		log.info("设置收件人");
		try {
			mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(setTo));
			return true;
		} catch (Exception e) {
			System.out.println("设置收件人时发生异常！");
			return false;
		}
	}
	/**
	 * @param copyto String
	 */
	
	protected boolean setCopyTo(){
		log.info("设置抄送人");
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC, (Address[])InternetAddress.parse(copyTo));
			return true;
		} catch (Exception e) {
			System.out.println("设置抄送人时发生异常！");
			return false;
		}
	}
	
	/**
	 * 发送邮件
	 * @throws MessageingException
	 */
	
	protected boolean sendOut(boolean needCopyto){
		log.info("发送邮件");
		try {
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			log.info("正在发送邮件.....");
			Session mailSession = Session.getInstance(props,null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String)props.get("mail.smtp.host"),userName,password);
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
			if(needCopyto){
				transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.CC));
			}
			log.info("邮件发送成功！");
			transport.close();
			return true;
		} catch (Exception e) {
			System.out.println("邮件发送失败！");
		return false;
		}
	}

}
