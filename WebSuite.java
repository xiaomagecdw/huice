package com.huice.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;


public class WebSuite {
	
	protected static WebDriver driver = null;
	protected static API api = null;
	protected static CheckPoint checkPoint = null;
	protected Locator locator =null;
	public static List<String>resultLog = new ArrayList<String>();
	
	@BeforeTest
	@Parameters({"browserType"})
	public void setUp(String  browserType)throws  IOException{
		driver = Driver.getDriver(browserType);
		api = new API(driver);
		locator = new Locator(driver, "object.xml", 30);
	}
	
	@BeforeClass
	public void beforeClass(){
		checkPoint = new CheckPoint(this.getClass().getSimpleName());
		locator = new Locator(driver, "object.xml", 30);
//		checkPoint = new CheckPoint();	
	}
	
	@AfterTest
	public void teardown(){
		driver.close();
		driver.quit();
	}

}
