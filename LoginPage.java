package com.huice.pages;

import org.openqa.selenium.By;

public class LoginPage extends Page {
	
	public static String baseUrl = "http://www.huicewang.com/ecshop/user.php";
	public static By userName = By.name("username");
	public static By Password = By.name("password");
	public static By submit = By.name("submit");
	
	public LoginPage(){
		super(baseUrl);
	}
	
	public void loginAction(String name, String pwd){
		getEelement(userName).sendKeys(name);
		getEelement(Password).sendKeys(pwd);
		getEelement(submit).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
//	
//	@FindBy(how=How.NAME,using ="username")
//	WebElement loginname;
//	
//	@FindBy(how=How.NAME,using ="password")
//	WebElement password;
//	
//	WebElement submit;
//	
//	public void logingAction(String name, String pwd){
//		loginname.sendKeys(name);
//		password.sendKeys(pwd);
//		submit.click();
//		try {
//			Driver.wait(2);
//		} catch (Exception e) {
//			e.printStackTrace();
//			// TODO: handle exception
//		}
//	}

}
