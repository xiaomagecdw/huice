package com.huice.cases;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.huice.base.WebSingle;
import com.huice.base.WebSuite;;


public class Case02_chrome extends WebSuite{
	
	@Test
	public void chrome(){
		
		locator.linkTo("http://www.huicewang.com/ecshop/");
		locator.element("首页", "留言板").click();
		locator.wait(2);
		locator.element("留言板", "点击邮件地址").sendKeys("123@163.com");
		locator.element("留言板", "留言类型-投诉").click();
		locator.element("留言板", "主题").sendKeys("我要投诉");
		locator.element("留言板", "留言内容").sendKeys("找不到我要的产品");
		locator.element("留言板", "我要留言").click();
//		driver.get("http://www.huicewang.com/ecshop/");
//		driver.findElement(By.partialLinkText("留言板")).click();
//		api.wait(2);
//		driver.findElement(By.className("inputBg")).sendKeys("123@163.com");
//		driver.findElement(By.xpath("//input[@value='1']")).click();
//		driver.findElement(By.name("msg_title")).sendKeys("我要投诉");
//		driver.findElement(By.name("msg_content")).sendKeys("找不到我要的产品！");
//		driver.findElement(By.className("bnt_blue_1")).click();
//		api.wait(2);
//		String text = driver.findElement(By.xpath("//div[@class='boxCenterList RelaArticle']//p")).getText();
		String text = locator.element("留言板", "成功提示").getText();
		checkPoint.assertContains(text,"你的留言已成功发表","留言失败！");
		checkPoint.result("留言功能正常！");
		
		
	}

}
