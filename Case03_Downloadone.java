package com.huice.cases;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.huice.base.Driver;

public class Case03_Downloadone {
	

	
	public  static void  main(String args[])throws Exception{
		
		WebDriver driver = Driver.getDriver("chrome");
		
		driver.get( "http://www.huicewang.com/ecshop/");
		
		Driver.loginIn(driver,"login");
		
		Driver.wait(5);
		Driver.clickSelect(driver, "GSM手机");
		driver.findElement(By.linkText("诺基亚")).click();
		Driver.wait(1);
		List<WebElement> list = driver.findElements(By.xpath("//div[@class = 'goodsItem']"));
		System.out.println(list.size());
		list.get(0).findElement(By.xpath("//div[@class='goodsItem']/a[text()='购买']")).click();
		Driver.wait(2);

		WebElement 		pp = driver.findElement(By.id("speDiv"));
		pp.findElement(By.id("spec_value_158")).click();
		Driver.screeShotPath(driver);
		Driver.wait(2);
		pp.findElement(By.linkText("购买")).click();
				
		Driver.wait(5);
		Driver.screeShotPath(driver);
		driver.findElement(By.xpath("//img[@alt='checkout']")).click();
		Driver.wait(2);
//		Driver.selectClick(driver, "name");
		driver.findElements(By.name("payment")).get(1).click();
		Driver.wait(1);
		driver.findElement(By.xpath("//input[@type='image']")).click();
		Driver.wait(2);
		driver.findElement(By.xpath("//a[@href='user.php']")).click();
		driver.findElement(By.xpath("//div[@class ='userMenu']/a/img[@src='themes/default/images/u3.gif']")).click();
		Driver.screeShotPath(driver);
		Map<String, String> Info = Driver.sreachOderInfo("2016073004498");
		System.out.println("订单单号:"+Info.get("oderID")+"\n"+
				             "下单时间："+Info.get("time")+"\n"+
				               "订单金额："+Info.get("money")+"\n"+
				                  "订单状态："+Info.get("state")+"\n"+
				                    "操    作："+Info.get("operation"));
	
	}


}



