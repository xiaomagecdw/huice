package com.huice.cases;


import org.openqa.selenium.By;
import org.testng.annotations.Test;
import com.huice.base.WebSuite;

public class Case01_firefox extends WebSuite{

	@Test
	public static void firefox(){
		
		
//		WebDriver driver = Driver.getDriver("firefox");
//		API api = new API(driver);
//		CheckPoint checkPoint =new CheckPoint();
		driver.get("http://www.huicewang.com/ecshop/");
		api.wait(2);
		api.selectByValue(By.id("category"), "3");
		driver.findElement(By.id("keyword")).sendKeys("诺基亚");
		driver.findElement(By.className("go")).click();
		api.wait(2);
		checkPoint.assertEquals(api.elementIsPresenet(By.xpath("//div[@class='goodsItem']")),true, "未搜索到产品：GSM-诺基亚");
		checkPoint.result("首页搜索功能正常");
//	    if(api.elementIsPresenet(By.xpath("//div[@class='goodsItem']"))){
//	    	System.out.println("pass");
//	    	Driver.screeShotPath(driver);
//	    }else{
//	    	System.out.println("fail'");
//	    }
//       driver.quit();
	}
}

