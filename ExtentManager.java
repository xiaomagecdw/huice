package com.huice.listener;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	
	private static ExtentReports extent;
	public synchronized static ExtentReports getReporter(String filePath){
		if(extent == null){
			extent = new ExtentReports(filePath, true);
			extent.addSystemInfo("Environment", "测试节点");
		}
		return extent;
	}

}
