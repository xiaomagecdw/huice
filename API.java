package com.huice.base;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;

public class API {
	
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static Locator locator;
	
	@SuppressWarnings("static-access")
	public API(WebDriver driver){
		
		this.driver = driver;	
	}	
	
	public void selectByValue(By by,String value){
		
		Select select = new Select(driver.findElement(by));
		select.selectByValue(value);
		
	}
	
	private  ExpectedCondition<Boolean> elementsPresent(final By by){
		
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver){
				
				if(driver.findElements(by).size()>0){
					return true;
				}else{
					return false;
				}
			};
		};
	}
	
	public  boolean elementIsPresenet(By by){
		
		return elementsPresent(by).apply(driver);
	}
	
	//下来列表
		public  void doRanmdomSelect(WebElement select, int start){
			
			Select s = new Select(select);
			List<WebElement> list = s.getOptions();
			
			if(start > list.size()){
				start = list.size()-1;
			}
			
			Random random = new Random();
			int index =  random.nextInt(list.size()-start) + start ;
			s.selectByIndex(index);
		}	
		//标题栏
		public  void  clickSelect( String linkName) {
			
		List<WebElement> lst = driver.findElements(By.xpath("//*[@id ='mainNav']/a"));
		System.out.println(lst.size());
		for(WebElement link :  lst){
			   link.getText();
			if(link.getText().equals(linkName)){
				 link.click();
				break;
			}
		}
			
		}
		
		public  boolean  searchWindowTitle( String  title) {
			
			Set<String> handls = driver.getWindowHandles();
			for(String hande : handls){
				driver.switchTo().window(hande);
				if(driver.getTitle().contains(title)){
					return true;
				}
			}
			System.out.println("没有匹配的title！");
			return false;
			
		}
		
		public void  searchNextWindow(){
			
			Set<String> handles = driver.getWindowHandles();
			if(handles.size()>0){
				Object [] arrayHandle = handles.toArray();
				String ntHandle = String.valueOf(arrayHandle[arrayHandle.length-1]);
				driver.switchTo().window(ntHandle);
			}
		}
		
		//js调用
		private  void  addJs( String  jsCodes){
			
			JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
			jsExecutor.executeScript(jsCodes);
			
		}
		
		public  void scrollToBottom( ){
			
			String jsString = "scrollToBottom(0,10000)";
			addJs(jsString);
		}
		
		public void scrollMidle(){
			String jsString = "scrollTo(0,300)";
			addJs(jsString);
		}
		
		public  void removeReadonly( String id){
			
			String jsString = "document.getElementById(\"" +id +"\").removeAttribute(\"readonly\");";
			addJs(jsString);
		}
		
		public  void wait(int seconds){
			
			try{
				int millis = seconds*1000;
				Thread.sleep(millis);
			}catch(InterruptedException e){
				System.out.println("等待超时");
			}	
		}
		
		public Map<String, String> scearchOrderInfo(String oderId){
//			locator.linkTo("http://www.huicewang.com/ecshop/user.php?act=order_list");
//			locator.wait(2);
			
			List<WebElement> orders = driver.findElements(By.xpath("//div[@class='userCenterBox boxCenterList clearfix']/table//tr"));
//			System.out.println(orders.size());
			Map<String, String> results = new LinkedHashMap<>();
			if(orders.size()>0){
				for(int i = 2;i<orders.size();i++){
					List<WebElement> orderInfons = driver.findElements(By.xpath("//div[@class='userCenterBox boxCenterList clearfix']/table//tr["+i+"]//td"));
					System.out.println(orderInfons.size());
					if(orderInfons.get(0).getText().equals(oderId)){
						results.put("订单号", orderInfons.get(0).getText());
						results.put("下单时间", orderInfons.get(1).getText());
						results.put("订单金额", orderInfons.get(2).getText());
						results.put("订单状态", orderInfons.get(3).getText());
						results.put("操作", orderInfons.get(4).getText());
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
		
		public List<String> getElementsText(By by){
			List<String> textsList = new ArrayList<String>();
			for (WebElement E : driver.findElements(by)){
				textsList.add(E.getText().trim());
			}
			return textsList;
		}

}


