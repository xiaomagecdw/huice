package com.huice.base;

import java.io.IOException;

public class ErrorHandler {
	
	/**
	 * 在系统日志和测试报告中记录自定义信息message
	 * @param e 
	 * @param message  要输出的自定义信息
	 * @param isReport 是否将自定义信息输出到测试报告中
	 */
	
	public static void continueRunning(IOException e, String message){
		logMessage(message);
	}
	
	/**
	 * 在程序中捕获异常后，记录message和异常的堆栈信息到日志。并在报告中输出自定义信息message
	 * @param cause 捕获到的原始异常
	 * @param message 要输出的自定义信息
	 * @param isReport 是否将自定义信息输出到测试报告中
	 */
	
	public static void conitnueRunning(Throwable cause, String message, boolean isReport){
		logMessage(message ,cause, isReport);
	}
	
	/**
	 * 抛出JuiceException,并在系统日志和测试报告中记录自定义信息message
	 * @param message 要输出的自定义信息
	 * @param isReport 是否将自定义信息输出到测试报告中
	 */
	
	public static void stopRunning(String message){
		logMessage(message);
		throw new huiceException(message);
	}
	
	/**
	 * 在程序中捕获异常后，记录message和异常的堆栈信息到信息。抛出JuiceException，并在报告中输出自定义信息message
	 * @param message 要输出的自定义信息
	 * @param isReport 是否将自定义自定义输出到测试报告中
	 */
	
	public static void stopRunning(Throwable cause, String message, boolean isReport){
		logMessage(message, cause, isReport);
		throw new huiceException(message);
	}
	
	@SuppressWarnings("unused")
	private static String createMessage(Throwable cause){
		return "异常堆栈信息：\n"
				+ getErrorStack(cause);
	}
	
	private static String getErrorStack(Throwable cause){
		StackTraceElement[] stackElements = cause.getStackTrace();
		StringBuffer sbff = new StringBuffer();
		sbff.append(cause+"\n");
		if(stackElements !=null){
			for(StackTraceElement stack: stackElements){
				sbff.append(stack.getClassName());
				sbff.append("."+stack.getMethodName());
				sbff.append("(Line:"+stack.getLineNumber()+")");
				sbff.append("\n");
			}
		}
		return sbff.toString();
		
	}
	
	private static void logMessage(String message){
		log.error(message);
	}
	
	private static void logMessage(String message, Throwable cause, boolean isReport){
//		log.error(message, cause);
	}


	
}
