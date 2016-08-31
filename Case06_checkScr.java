package com.huice.cases;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.huice.base.WebSingle;
import com.huice.uitl.HttpHelper;

public class Case06_checkScr extends WebSingle{
	
	@Test
	public void CheckScr(){
		
		locator.linkTo("http://www.huicewang.com/ecshop/");
		List<WebElement> link = locator.elements("首页", "精品推荐-价格");
		for(WebElement links: link){
			links.click();
			locator.wait(1);
			List<String> img = locator.elementsAttribute("首页", "精品推荐-产品图片", "scr");
			HttpHelper.URLisAvailable(img);
			checkPoint.assertEquals(HttpHelper.URLisAvailable(img), true);
		}
		checkPoint.result("首页精品推荐区域的所有产品图片链接正常！");
		
//		int counts = 0;
//		locator.linkTo("http://www.huicewang.com/ecshop/");
//		locator.wait(1);
////		List<WebElement> scr = locator.elements("首页", "精品推荐");
//		List<WebElement> scr = driver.findElements(By.xpath("//div[@id='itemBest']/h2/a"));
//		System.out.println(scr.size());
//		for(WebElement http :scr){
//			http.click();
//			api.wait(1);
//			List<WebElement> http1 = driver.findElements(By.xpath("//div[@id='show_best_area']/div[@class='goodsItem']/img[@class='goodsimg']"));
//			System.out.println(http1.size());
//			
//		}
	}
	
	
	

}
