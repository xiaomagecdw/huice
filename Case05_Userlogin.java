package com.huice.cases;

import java.util.Map;

import org.testng.annotations.Test;
import com.huice.base.Config;
import com.huice.base.WebSingle;

public class Case05_Userlogin extends WebSingle{
	
	@Test(dataProvider="getData")
	public void userLogin(Map<String, String> data){
		
		locator.linkTo(data.get("主页地址"));
		if(locator.elementExistNoWait("首页", "退出登录")){
			locator.element("首页", "退出登录").click();
		}
		
		locator.linkTo(data.get("登录地址"));
		locator.element("登录页", "用户名").sendKeys(data.get("用户名"));
		locator.element("登录页", "密码").sendKeys(data.get("密码"));
		locator.element("登录页", "立即登录").click();
		locator.wait(2);
	}

}
