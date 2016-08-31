package com.huice.uitl;

import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HttpHelper {
	
	public static synchronized boolean URLisAvailable(String url){
		
		boolean flag = false;
		int counts = 0;
		while(counts < 3){
			int state = -1;
			try {
				HttpsURLConnection com = (HttpsURLConnection) new URL(url).openConnection();
				state = com.getResponseCode();
				if(state == 200){
					System.out.println("URL is available:" +url);
					flag = true;
					break;
				}
			} catch (Exception e) {
				counts++;
				System.out.println("URL不可用，第"+ counts+"次链接"+url+"为"+state);
				continue;
			}
		}
		return flag;
	}
	
	public static synchronized boolean URLisAvailable(List<String> url){
		boolean flag = false;
		int counts = 0;
		if(url == null ||url.size()<=0){
			flag = false;
			System.out.println("链接数组为空");
		}
		
		for(int i = 0;i<url.size();i++){
			if(url.get(i).equalsIgnoreCase(null)){
				System.out.println(url.get(i)+"为空链接");
				flag = false;
			}
			while(counts < 3){
				int state = -1;
				try {
					HttpsURLConnection com = (HttpsURLConnection) new URL(url.get(i)).openConnection();
					state = com.getResponseCode();
					if(state == 200){
						System.out.println("URL is available:"+url.get(i));
						break;
					}
				} catch (Exception e) {
					counts++;
					System.out.println("URL不可用,第"+counts +"次链接"+url.get(i)+"为"+state);
					flag = false;
					continue;
				}
			}
		}
		return flag;
	}

}
