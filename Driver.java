package com.huice.base;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
//import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Driver {
//	WebDriver driver  =  Driver.getDriver(type)
	
	private Driver(){}
	
	private static  WebDriver driver = null;
	
	public static  WebDriver getDriver(String type)throws IOException{
		
		if(driver == null){
			driver  =  catDriver(type);
		}
		return driver;
	}
	
	private  static WebDriver catDriver(String type)throws IOException {
		
		
		switch(type.toLowerCase()){
		case"firefox":
			driver  =   catFirefoxDriver();
			break;
		case"chrome":
			System.setProperty("webdriver.chrome.driver", "source/ChromeDriver");
			driver  =   catChromeDriver();
			break;
		case"ie":
			System.setProperty("webdriver.ie.driver", "F:/python2.7.11/IEDriverServer.exe");
			driver  = catInternetExplorerDriver();
			break;
		default:
			System.out.println("Error:Invalid Browser Type");
			break;
		}
		return driver;
		     
	}
	private static FirefoxDriver catFirefoxDriver() throws IOException{
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("xpinstall.signatures.required ",false);
		profile.addExtension(new File("source/firebug-2.0.17.xpi"));
		profile.addExtension(new File("source/netExport-0.8.xpi"));
		profile.setPreference("extensions.firebug.currentVersion", "2.0.17");
		profile.setEnableNativeEvents(true);
		profile.setPreference("extensions.firebug.allPagesActivation", "on");
		profile.setPreference("extensions.firebug.defaultPanelName", "net");
		profile.setPreference("extensions.firebug.net.enableSites", true);
		profile.setPreference("extensions.firebug.netExport.alwaysEnableAutoExport",  true);
		profile.setPreference("extensions.firebug.netExport.saveFilse", true);
		profile.setPreference("extensions.firebug.netExport.defaultLogDir", "source/tupian");
//		ProfilesIni allProfiles = new ProfilesIni();
//		FirefoxProfile profile = allProfiles.getProfile("defualt/ocma2ewi.default");
//		FirefoxDriver driver = new  FirefoxDriver();
		return new FirefoxDriver(profile);
	}
	private static ChromeDriver catChromeDriver(){
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		return new ChromeDriver(options);
	}
	private static InternetExplorerDriver catInternetExplorerDriver(){
		return new InternetExplorerDriver();
	}
	
	//用户选择登录方式
	public static void  loginIn(WebDriver driver, String  name) {
		
		switch (name) {
		case "login":
			driver.findElement(By.xpath("//a[@href='user.php']")).click();
			String username = "hello";
			String password ="123456789";
			Driver.login(driver, username, password);
			break;
		case "register":
			driver.findElement(By.xpath("//*[@id='ECS_MEMBERZONE']/a[2]/img")).click();
			Driver.register(driver);
			break;
		default:
			addToCookic(driver);
			break;
		}
		
	}
	
	//刷新登录
	public static void addToCookic(WebDriver driver){
		
		Cookie uesr_name = new Cookie.Builder("ECS[username]","hello").domain("www.huicewang.com").build();
		Cookie user_password = new Cookie.Builder("ECS[password]", "77f21c2e701359c44baad4e44e9adb05").domain("www.huicewang.com").build();
		Cookie  user_submit = new Cookie.Builder("ECS[user_id]",	"14'").domain("www.huicewang.com").build();
	     driver.manage().addCookie(uesr_name);
	    driver.manage().addCookie(user_password);
	    driver.manage().addCookie(user_submit);
	    driver.get("http://www.huicewang.com/ecshop/");
	}
	
	
	
	//快递选择
	public static void  selectClick(WebDriver driver,String name){
		switch (name.toLowerCase()) {
		
		case"城际":
			driver.findElement(By.xpath("//input[@supportcod='1']")).click();
			break;
		case"平邮":
			driver.findElement(By.xpath("//input[@supportcod='2']")).click();
			break;
		default:
			driver.findElement(By.xpath("//input[@supportcod='0']")).click();
			break;
		}
		
	}
	
	//截图
	private  static void  screeShot(WebDriver driver,String  path){
		
		try {
			File screeshotFile = ((TakesScreenshot ) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screeshotFile, new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void screeShotPath(WebDriver driver){
		
		String namePath = String.valueOf(new Date().getTime())+".jpg";
		File dir = new File("test-output");
		if(!dir.exists()){
			dir.mkdirs();
		}
		String path = dir.getAbsolutePath()+"/"+namePath;
		screeShot(driver, path);
	}
	
	//配件选择
	public static void Select(WebDriver driver,  int  type) {
		
		switch (type) {
		case 0:
			driver.findElement(By.id("spec_value_158")).click();
			driver.findElement(By.id("spec_value_159")).click();
			driver.findElement(By.id("spec_value_157")).click();
			Driver.wait(2);
			break;
		case 1:
			driver.findElement(By.id("spec_value_158")).click();
			driver.findElement(By.id("spec_value_159")).click();
			Driver.wait(2);
			break;
		case 2:
			driver.findElement(By.id("spec_value_158")).click();
			driver.findElement(By.id("spec_value_157")).click();
			Driver.wait(2);
			break;
		case 3:
			driver.findElement(By.id("spec_value_159")).click();
			driver.findElement(By.id("spec_value_157")).click();
			Driver.wait(2);
			break;
		default:
			System.out.println("直接购买！");
			break;
		}
		
	}
	
	//注册
	private static void register(WebDriver driver){
		
		driver.findElement(By.id("username")).sendKeys("hello");
		driver.findElement(By.id("email")).sendKeys("918971505@qq.com");
		driver.findElement(By.id("password1")).sendKeys("123456789");
		driver.findElement(By.id("conform_password")).sendKeys("123456789");
		driver.findElement(By.name("extend_field1")).sendKeys("918971505@qq.com");
		driver.findElement(By.name("extend_field2")).sendKeys("918971505");
		driver.findElement(By.name("extend_field3")).sendKeys("123456789");
		driver.findElement(By.name("extend_field4")).sendKeys("123456789");
		driver.findElement(By.name("extend_field5")).sendKeys("01234567891");
		Driver.wait(2);
		Select quetion =  new Select(driver.findElement(By.name("sel_question")));
		quetion.selectByValue("interest");
		driver.findElement(By.name("passwd_answer")).sendKeys("吃");
		Driver.wait(2);
		driver.findElement(By.className("us_Submit_reg")).click();	
	}
	
	
	
	//下来列表
	public static void doRanmdomSelect(WebElement select, int start){
		
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
	public static void  clickSelect(WebDriver driver, String linkName) {
		
	List<WebElement> lst = driver.findElements(By.xpath("//*[@id ='mainNav']/a"));
//	System.out.println(lst.size());
	for(WebElement link :  lst){
		   link.getText();
		if(link.getText().equals(linkName)){
			 link.click();
			break;
		}
	}
		
	}
	
	//查看购物车
	public static void switchShoppingCart(WebDriver driver){
		
		driver.findElement(By.partialLinkText("查看购")).click();
		
	}
	
	//登录
	private  static void login(WebDriver driver, String username, String password){
		
		
//		driver.findElement(By.xpath("//*[@id='ECS_MEMBERZONE']/a[1]/img")).click();
		
		driver.findElement(By.name("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.id("remember")).click();
		driver.findElement(By.name("submit")).click();
		
	}
	

	
	public void  searchNextWindow(WebDriver driver){
		
		Set<String> handles = driver.getWindowHandles();
		if(handles.size()>0){
			Object [] arrayHandle = handles.toArray();
			String ntHandle = String.valueOf(arrayHandle[arrayHandle.length-1]);
			driver.switchTo().window(ntHandle);
		}
	}
	
	public static boolean  searchWindowTitle(WebDriver driver, String  title) {
		
		Set<String> handls = driver.getWindowHandles();
		for(String hande : handls){
			driver.switchTo().window(hande);
			if(driver.getTitle().contains(title)){
				return true;
			}
		}
		return false;
		
	}
	
	//等待元素加载
	public static WebElement waitForElement(WebDriver driver,final By by, int objectWaitTime){
		WebElement element  = null;
		try{
			element = new WebDriverWait(driver, objectWaitTime)
					.until(new ExpectedCondition<WebElement>() {
						@Override
						public WebElement apply(WebDriver driver){
							return driver.findElement(by);
						}
					});
		}catch(Exception e){
			System.out.println(by +""+objectWaitTime+"");
		}
		return 	element;
	}
		
	
	//元素判断
	public static boolean elementPresent(WebDriver driver, final By by, int objectWaitTime){
		
		return new WebDriverWait(driver, objectWaitTime)
				.until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver driver){
						try{
							driver.findElement(by);
							return true;
							
						}catch(Exception e){
							e.printStackTrace();
							return false;
						}
						
					}
					
				});
	}
	
	//元素加载
	public static boolean elementsPresent(WebDriver driver, final By by, int objectWaitTime){
		
		return new WebDriverWait(driver, objectWaitTime)
				.until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver driver){
						if(driver.findElements(by).size()>0){
							return true;
						}else{
							return false;			
						}
					}
					
				});
	}
	
	//等待页面加载
	public static boolean waitPageLoad(WebDriver driver, int objectWaitTime){
		
		return new  WebDriverWait(driver, objectWaitTime)
				.until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver driver){
						return((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
					}
				});
	}
	
	//js调用
	private static void  addJs(WebDriver driver, String  jsCodes){
		
		JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
		jsExecutor.executeScript(jsCodes);
		
	}
	
	public static void scrollToBottom(WebDriver driver){
		
		String jsString = "scrollToBottom(0,10000)";
		addJs(driver,jsString);
	}
	
	public static void removeReadonly(WebDriver driver, String id){
		
		String jsString = "document.getElementById(\"" +id +"\").removeAttribute(\"readonly\");";
		addJs(driver,jsString);
	}
	
	
	//根据订单号查询该订单所有信息
	public  static  Map<String,String> sreachOderInfo(String oderId){
		
		Map<String,String> map = new  HashMap<String,String>();
		
		List<WebElement> list = driver.findElements(By.xpath("//div[@class='userCenterBox boxCenterList clearfix']//td[1]"));
		
		for(int r=0;r<list.size();r++){
			List<WebElement> orderInfons = driver.findElements(By.xpath("//div[@class='userCenterBox boxCenterList clearfix']/table//tr["+r+"]//td"));
			System.out.println(orderInfons);
			if(orderInfons.get(0).getText().equals(oderId)){
				WebElement  element0  = driver.findElement(By.xpath("//div[@class='userCenterBox boxCenterList clearfix']//tr["+(r+1)+"]/td[1]"));
				WebElement  element1  = driver.findElement(By.xpath("//div[@class='userCenterBox boxCenterList clearfix']//tr["+(r+1)+"]/td[1]"));
				WebElement  element2  = driver.findElement(By.xpath("//div[@class='userCenterBox boxCenterList clearfix']//tr["+(r+1)+"]/td[1]"));
				WebElement  element3  = driver.findElement(By.xpath("//div[@class='userCenterBox boxCenterList clearfix']//tr["+(r+1)+"]/td[1]"));
				WebElement  element4  = driver.findElement(By.xpath("//div[@class='userCenterBox boxCenterList clearfix']//tr["+(r+1)+"]/td[1]"));
				map.put("oderID", element0.getText());
				map.put("time", element1.getText());
				map.put("money", element2.getText());
				map.put("state", element3.getText());
				map.put("operation", element4.getText());
				break;
				
			}
		}
		return map;
		
	}
	
	//根据订单
	
	
	public static void wait(int seconds){
		
		try{
			int millis = seconds*1000;
			Thread.sleep(millis);
		}catch(InterruptedException e){
			System.out.println("等待超时");
		}	
	}
	
	public static void  treaDown(WebDriver driver) {
		
		driver.quit();
		
	}
	
	
	
}
