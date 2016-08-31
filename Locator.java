package com.huice.base;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.huice.uitl.XmlParser;

public class Locator {
	
	private WebDriver driver;
	private XmlParser xp;
	private int objectWaitTime;
	private String filePath;
	private boolean existFlag;
	
	public Locator(WebDriver driver, String filePath, int objectWaitTime){
		
		this.driver = driver;
		this.filePath= filePath;
		this.objectWaitTime= objectWaitTime;
		xp = new XmlParser(this.filePath);
		
	}
	
	public boolean linkTo(String Url){
		
		try {
			driver.get(Url);
		} catch (TimeoutException e) {
			System.out.println("页面加载失败："+Url);
			return false;
		}
		return true;
	}
	
	public WebElement element(String page, String object){
		
		return getElement(page,object,true);
	}
	
	public String elementText(String page,String object){
		
		return getElement(page, object, true).getText().trim();
	}
	
	public List<WebElement> elements(String pageKey, String objKey){
		return getElements(pageKey, objKey);
	}
	
	public WebElement elementNotWait(String pageKey, String objKey){
		return getElement(pageKey, objKey, false);
	}
	
	public boolean elementExist(String pageKey, String objKey){
		getElement(pageKey, objKey, true);
		return existFlag;
	}
	
	public boolean elementExistNoWait(String pageKey, String objKey){
		
		boolean existFlag = false;
		String type = xp.getElementText("对象/"+pageKey+"/"+objKey+"/type");
		String value = xp.getElementText("对象/"+pageKey+"/"+objKey+"/value").trim();
		By by = getBy(type, value);
		try {
			driver.findElement(by);
			existFlag = true;
		} catch (Exception e) {
		}
		return existFlag;
	}
	
	public boolean waitElementToBeDisplayed(final WebElement element, String pageKey, String objKey){
		boolean wait = false ;
		if(element == null){
			try {
				wait = new WebDriverWait(driver, objectWaitTime)
						.until(new ExpectedCondition<Boolean>() {
							@SuppressWarnings("null")
							public Boolean apply(WebDriver d){
								return element.isDisplayed();
							}
						});
			} catch (Exception e) {
				System.out.println(pageKey+"-"+objKey+"为在页面显示！");
			}	
		}
		return wait;	
	}
	
	public WebElement theFirstElement(String pageKey, String objKey){
		List<WebElement> elements = getElements(pageKey, objKey);
		return elements.get(0);
	}
	
	public WebElement theLastElement(String pageKey, String objKey){
		List<WebElement> elements = getElements(pageKey, objKey);
		return elements.get(elements.size()-1);
	}
	
	public WebElement theRandomElement(String pageKey, String objKey){
		List<WebElement> elements = getElements(pageKey, objKey);
		int index = (int) (Math.random()*elements.size());
		return elements.get(index);
	}
	
	public List<String> elementsAttribute(String pageKey, String objKey, String attribute){
		List<String> list = new ArrayList<String>();
		List<WebElement> elements = elements(pageKey,objKey);
		for(WebElement e :elements){
			list.add(e.getAttribute(attribute));
		}
		return list;
	}
	
	public List<String> elememtsText(String pageKey, String objKey){
		List<String> list = new ArrayList<String>();
		List<WebElement> elements = elements(pageKey,objKey);
		for(WebElement e: elements){
			list.add(e.getText());
		}
		return list;
	}
	
	public Select select(String pageKey,String objKey){
		return new Select(this.getElement(pageKey, objKey, true));
	}
	
	public void selectByValue(String pageKey, String objKey,String Value){
		Select select = select(pageKey, objKey);
		select.selectByValue(Value);
	}
	
	public void selectByIndex(String pageKey, String objKey,int index ){
		Select select = select(pageKey, objKey);
		select.selectByIndex(index);
	}
	
	public void selectByVisibleText(String pageKey,String objKey,String text){
		Select select = select(pageKey, objKey);
		select.deselectByVisibleText(text);
	}
	
	public void addJS(String jsCodes){
		JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
		jsExecutor.executeScript(jsCodes);
	}
	
	public void removeReadonly(WebDriver driver, String id){
		String jsString = "document.getElementById(\""+id+"\").removeAttribute(\"readonly\");";
		addJS(jsString);
	}
	
	public void scrollToBottom(){
		String jsString = "scrollToBottom(0,10000);";
		addJS(jsString);
	}
	
	public void scrollToMidlle(){
		String jsString = "scrollToMidlle(0,300);";
		addJS(jsString);
	}
	
	public void elementsToBeDiaplayed(String typeName, String typeValue){
		
		StringBuffer sbff = new StringBuffer();
		typeName = typeName.toLowerCase();
		
		switch (typeName) {
		case "className":
			sbff.append("var all = document.getElementsClassName('"+typeValue+"');");
			break;
		case "id":
			sbff.append("var all = document.getElementsId('"+typeValue+"');");
			break;
		case "tagname":
			sbff.append("var all = document.getElementsTagName('"+typeValue+"');");
			break;
		default:
			System.out.println("documen对象的getELements方法不支持查找此类型："+typeName);
		}
		sbff.append("for(var i=0;i<all;i++){all[i].style.display = 'block';");
		addJS(sbff.toString());
	}
	
	public List<String>  linkedAndGet(List<String> url, String pageKey,String objKey, String attribute){
		List<String> text = new ArrayList<String>();
		int size = url.size();
		for(int i = 0;i<size;i++){
			boolean islinked = linkTo(url.get(i));
			if(islinked){
				if(attribute.equalsIgnoreCase("text")){
					text.add(element(pageKey,objKey).getText().trim());
				}else text.add(element(pageKey,objKey).getAttribute(attribute));
			}else text.add("-1");
		}
		return text;
	}
	
	public boolean linkedAndGet(List<String> url, String pageKey, String objKey){
		boolean existFlag = false;
		for(String urll:url){
			boolean islinked = linkTo(urll);
			if(islinked){
				waitForPageLoad();
				if(!elementExist(pageKey,objKey)){
					System.out.println("页面："+urll+"元素"+pageKey+"-"+objKey+"不存在！");
					existFlag = false;
				}else existFlag = true;
			}
		}
		return existFlag;
	}
	
	public boolean waitForPageLoad(){
		return new WebDriverWait(driver, objectWaitTime)
				.until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver driver){
						return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
					}
				});
	}
	
	public void wait(int seconds){
		try {
			int millis = 1000*seconds;
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			System.out.println("当前线程正在处于等待状态！");
		}
	}
	
	private By getBy(String type, String value){
		By by = null;
		switch (type.trim()) {
		case "id":
			by = By.id(value);
			break;
		case "name":
		    by = By.name(value);
		    break;
		case "className":
			by = By.className(value);
			break;
		case "tagName":
			by = By.tagName(value);
			break;
		case "linkText":
			by = By.linkText(value);
			break;
		case "partiaLinkText":
			by = By.partialLinkText(value);
			break;
		case "xpath":
			by = By.xpath(value);
			break;
		case "cssSelector":
			by = By.cssSelector(value);
			break;
		default:
			System.out.println("元素定位错误！By"+type+"不存在此类型！");
			break;
		}
		return by;
	}
	
	
	private WebElement getElement(String page, String object, boolean wait){
		
		WebElement element = null;
		existFlag = false;
		if(xp.isExist("对象/"+page+"/"+object)){
			String type = xp.getElementText("对象/"+page+"/"+object+"/type");
			String value = xp.getElementText("对象/"+page+"/"+object+"/value");
			final By by = getBy(type, value);
			if(wait){
				try {
					element = new WebDriverWait(driver, objectWaitTime)
							.until(new ExpectedCondition<WebElement>(){
								@Override
								public WebElement apply(WebDriver d){
									existFlag = true;
									return d.findElement(by);
								}
							});
					if(!waitElementToBeDisplayed(element, page, object)){
						System.out.println(page+"-"+object+"为在页面显示！");
					}
				} catch (Exception e) {
					System.out.println("未找到页面元素："+page+"-"+object);	
				}
			}else{
				return driver.findElement(by);	
			}
		}else{
			System.out.println(page+"-"+object+"未在对象库文件中存在：object.xml");
		}
		return element;
	}
	
	public List<WebElement> getElements(String pageKey, String objKey){
		
		List<WebElement> elements = new ArrayList<WebElement>();
		if(xp.isExist("/对象/"+pageKey+"/+objKey")){
			String type = xp.getElementText("/对象/"+pageKey+"/"+objKey+"/type");
			String value = xp.getElementText("/对象/"+pageKey+"/"+objKey+"/value");
			By byy = getBy(type,value);
			elements = driver.findElements(byy);
			if(elements.size()==0){
				String message = "未找到页面元素"+pageKey+"-"+objKey;
				System.out.println(message);
			}
			for(WebElement e: elements){
				if(e.equals("")||e == null){
					String message = pageKey +"-"+objKey+"返回多个对象，请其包含空值！";
					System.out.println(message);		
				}
			}
		}else{
			String message = pageKey+"-"+objKey+"未在对象库中存在文件："+filePath;
			System.out.println(message);
		}
		return elements;
	}
}
