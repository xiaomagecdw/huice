package com.huice.base;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class Actions {
	
	private WebDriver driver;
	private Locator locator;
	
	public  Actions(WebDriver driver){
		this.driver = driver;
		this.locator = new Locator(driver, "object.xml", 30);
	}
	
	public void loginAction(String name,String pwd){
		locator.element("登录页", "用户名").sendKeys(name);
		locator.element("登录页", "密码").sendKeys(pwd);
		locator.element("登录页","立即登录").click();
		locator.wait(2);
		
	}
	
	public Map<String, String> scearchOrderInfo(String oderId){
		locator.linkTo("http://www.huicewang.com/ecshop/user.php?act=order_list");
		locator.wait(2);
		
		List<WebElement> orders = driver.findElements(By.xpath("//div[@class='userCenterBox boxCenterList clearfix']/table//tr"));
		Map<String, String> results = new LinkedHashMap<>();
		if(orders.size()>0){
			for(int i = 0;i<orders.size();i++){
				List<WebElement> orderInfons = driver.findElements(By.xpath("//div[@class='userCenterBox boxCenterList clearfix']/table//tr["+i+"]//td"));
				if(orderInfons.get(0).getText().equals(oderId)){
					results.put("订单号", orderInfons.get(0).getText());
					results.put("下单时间", orderInfons.get(1).getText());
					results.put("订单金额", orderInfons.get(2).getText());
					results.put("订单状态", orderInfons.get(3).getText());
					results.put("操 作", orderInfons.get(4).getText());
					return results;
				}
			}
		}
		else{
			System.out.println("无订单消息！");
			return null;
		}
		System.out.println("订单不存在："+oderId);
		return null;
	}
	

}
