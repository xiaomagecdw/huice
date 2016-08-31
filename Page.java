package com.huice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.huice.base.WebSuite;

public class Page extends WebSuite{
	
	protected String url;
	
	public Page(String url){
		this.url = url;
		driver.get(url);
	}
	
	public WebElement getEelement(By by){
		return driver.findElement(by);
	}

}
