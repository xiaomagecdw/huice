package com.huice.cases;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.huice.base.Driver;
import com.huice.base.WebSingle;
import com.huice.base.WebSuite;

public class Case03_Download extends WebSuite{
	
	@Test
	public void Download(){
		
		String oderId = null;
		
//		WebDriver driver = Driver.getDriver("chrome");
//		API api = new API(driver);
//		CheckPoint checkPoint = new CheckPoint();
		driver.get("http://www.huicewang.com/ecshop/");
		Driver.loginIn(driver,"login");
		api.wait(5);
		
		Driver.clickSelect(driver, "GSM手机");
		driver.findElement(By.linkText("诺基亚")).click();
		api.wait(1);
		api.scrollMidle();
		List<WebElement> list = driver.findElements(By.xpath("//div[@class='goodsItem']"));
		if(list.size()>0){
			list.get(0).findElement(By.xpath("//div[@class='goodsItem']/a[text()='购买']")).click();
			api.wait(2);
			WebElement pp = driver.findElement(By.id("speDiv"));
			pp.findElement(By.id("spec_value_158")).click();
			Driver.screeShotPath(driver);
			api.wait(2);
			pp.findElement(By.linkText("购买")).click();
			api.wait(5);
			driver.findElement(By.xpath("//img[@alt='checkout']")).click();
			api.wait(5);
			Driver.selectClick(driver, "城际");
//			driver.findElements(By.name("shipping")).get(3).click();
//			api.wait(5);
			driver.findElements(By.name("payment")).get(1).click();
			driver.findElement(By.xpath("//input[@type='image']")).click();
			api.wait(5);
			oderId = driver.findElement(By.xpath("//div[@class='flowBox']/h6/font")).getText().trim();
			checkPoint.assertNotEquals(oderId, null, "订单异常");
			driver.findElement(By.xpath("//a[@href='user.php']")).click();
			api.wait(2);
			driver.findElement(By.xpath("//div[@class='userMenu']/a[@href='user.php?act=order_list']")).click();
			Map<String, String> Info = api.scearchOrderInfo(oderId);
			System.out.println("订单单号:"+Info.get("订单号")+"\n"+
		             "下单时间："+Info.get("下单时间")+"\n"+
		               "订单金额："+Info.get("订单金额")+"\n"+
		                  "订单状态："+Info.get("订单状态")+"\n"+
		                    "操    作："+Info.get("操作"));

//			checkPoint.equalsNotNull(Info, "订单不存在！");
			
		}else{
			checkPoint.isFailed("下单失败！！！");
		}
		checkPoint.result("下单成功，订单号："+oderId);	
		
	}

}
