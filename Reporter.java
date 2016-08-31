package com.huice.listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

import com.huice.base.WebSuite;

public class Reporter implements IReporter {
	
	@Override
	public void generateReport(List<XmlSuite> arg0, List<ISuite> arg1, String arg2){
		
		String PROJECTNAME ="";
		String ENDTIMESTRING = "";
		int PASSED_NUMBER = 0;
		int FAILED_NUMBER = 0;
		int SKIPPED_NUMBER = 0;
		int TOTAL_NUMBER = 0 ;
		float PASSRATE = 0;
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		PROJECTNAME = arg0.get(0).getName();
		ENDTIMESTRING = "测试结束时间："+simpleDateFormat.format(new Date());
		
		for(String key : arg1.get(0).getResults().keySet()){
			ITestContext testResultContext = arg1.get(0).getResults().get(key).getTestContext();
			PASSED_NUMBER = testResultContext.getPassedTests().size();
			FAILED_NUMBER = testResultContext.getFailedTests().size();
			SKIPPED_NUMBER = testResultContext.getSkippedTests().size();
		}
				
		TOTAL_NUMBER = PASSED_NUMBER+FAILED_NUMBER+SKIPPED_NUMBER;
		PASSRATE = 100*((float)PASSED_NUMBER/(TOTAL_NUMBER));
		if(Double.isNaN(PASSRATE)){
			PASSRATE = 0;
		}
		
		System.out.println("成功："+PASSED_NUMBER);
		System.out.println("失败："+FAILED_NUMBER);
		System.out.println("跳过："+SKIPPED_NUMBER);
		System.out.println("总计："+TOTAL_NUMBER);
		System.out.println("成功率："+PASSRATE);
		
		StringBuffer htmlString  = new StringBuffer();
//		htmlString.append("\n");
		htmlString.append(PROJECTNAME+"\n");
		htmlString.append(ENDTIMESTRING+"\n");
		htmlString.append("成功："+PASSED_NUMBER+"\n");
		htmlString.append("失败："+FAILED_NUMBER+"\n");
		htmlString.append("跳过："+SKIPPED_NUMBER+"\n");
		htmlString.append("总计："+TOTAL_NUMBER+"\n");
		htmlString.append("成功率："+PASSRATE+"\n");
		htmlString.append("测试日志："+"\n");
		
		for(String text: WebSuite.resultLog){
			htmlString.append(text+"\n");
		}
//		htmlString.append("\n");
		
		write("source/htmlReporter"+new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date())+".html",htmlString.toString());
		
	}
	
	public static void write(String filePath, String data){
		File file = new File(filePath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		BufferedWriter bw = null;
		try {
			 bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
			 bw.write(data);
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally {
			try {
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
