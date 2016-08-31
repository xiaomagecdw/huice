package com.huice.base;


import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class WebSingle extends TestBase{
	
	protected Locator locator;
	protected CheckPoint checkPoint;
	protected WebDriver driver;
	protected static API api = null;

	@BeforeClass
	public synchronized void beforeTest()throws IOException{
		driver = Driver.getDriver("chrome");
		checkPoint = new CheckPoint(this.getClass().getSimpleName());
		locator = new Locator(driver, "object.xml", 30);
		api = new API(driver); 
	}
	
	@AfterClass
	public void afterTest(){
		driver.close();
		driver.quit();
	}
}
