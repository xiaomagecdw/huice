package com.huice.cases;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.huice.base.WebSuite;

public class Case04_checkOderID extends WebSuite{
	
	@Test
	public void CheckOderId(){
		
//		WebDriver driver = Driver.getDriver("chrome");
//		API api =new API(driver);
//		CheckPoint checkPoint = new CheckPoint();
		driver.get("http://www.huicewang.com/ecshop/");
		api.scrollMidle();
		api.wait(1);
		List<WebElement> element = driver.findElements(By.xpath("//div[@id='itemBest']/h2/a"));
		for(WebElement ll :element){
			ll.click();
			api.wait(1);
			
			List<String> price = new ArrayList<String>();
			List<WebElement> ss = driver.findElements(By.xpath("//div[@id='show_best_area']/div[@class='goodsItem']/font"));
			for(WebElement dd :ss){
				price.add(dd.getText());
			}
			checkPoint.assertNotEquals(price, null, "价格异常！");
		}
		checkPoint.result("精选手机价格显示稳定！");
	}

}
