package com.huice.base;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.huice.listener.ExtentManager;
import com.huice.uitl.DateHandler;
import com.huice.uitl.XmlParser;
import com.relevantcodes.extentreports.ExtentReports;

public class TestBase {
	
	private String filePath ="test-data/"+this.getClass().getSimpleName()+".xml";
	private XmlParser xp;
	private Map<String, String> commonMap;
	private static Map<String, String> global;
	private static String reportPath = "report/"+DateHandler.getTimeStamp()+".xml";
	private static ExtentReports extentReports;
	
	static{
		XmlParser g_xp = new XmlParser("test-data/Global.xml");
		global = g_xp.getChildrenInfoByElement(g_xp.getElement("/*"));
	}
	
	@BeforeSuite
	public void beforeSuite(){
		extentReports = ExtentManager.getReporter(reportPath);
	}
	
	@AfterSuite
	public void afterSuite(){
		extentReports.close();
	}
	
	public static ExtentReports getExtent(){
		return extentReports;
	}
	
	public static String getReportPath(){
		return reportPath;
	}
	
	@DataProvider
	public Object[][] getData(Method method){
		this.init();
		this.getCommonData();
		File file = new File(filePath);
		List<Element> elements = xp.getElements("data/"+method.getName());
		if(file.exists() && elements.size()>0){
			Object[][] object = new Object[elements.size()][];
			for(int i=0;i<elements.size();i++){
				Map<String, String> mergeCommon = this.getMergeData(xp.getChildrenInfoByElement(elements.get(i)), commonMap);
				Map<String, String> mergeGlobal = this.getMergeData(mergeCommon, global);
				Object[] temp = new Object[]{mergeGlobal};
				object[i] = temp;
			}
			return object;
		}else{
			Object[][] object = new Object[1][1];
			object[0][0] = global;
			return object;
		}
	}
	
	private void init(){
		if(xp ==null){
			xp = new XmlParser(filePath);
		}
	}
	
	private void getCommonData(){
		if(commonMap == null){
			Element element = xp.getElement("/*/common");
			commonMap = xp.getChildrenInfoByElement(element);
		}
	}
	
	private Map<String, String> getMergeData(Map<String, String>map1,Map<String, String>map2){
		Iterator<String> it = map2.keySet().iterator();
		while(it.hasNext()){
			String Key = it.next();
			String value = map2.get(Key);
			if(!map1.containsKey(Key)){
				map1.put(Key, value);
			}
		}
		return map1;
	}

}
