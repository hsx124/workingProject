package com.hsx124.demo;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.hsx124.util.ToolUtil;

public class Demo004 {
	static WebDriver driver;

	public static void main(String[] args) {
		try {
			ToolUtil tl = new ToolUtil();
			tl.init();
			//			ChromeOptions option = new ChromeOptions();
			//			option.addArguments("--start-maximized");
			//			option.addArguments("--remote-allow-origins=*");
			//			option.addArguments("--proxy-server=http://	216.137.184.253:80");
			//			option.addArguments("--proxy-bypass-list=	216.137.184.253");
			//			WebDriver driver = new ChromeDriver(option);

			ChromeOptions chromeOptions = new ChromeOptions();
			String proxyadd = "110.73.2.248:8123";
			//158.101.113.18
			Proxy proxy = new Proxy();
			proxy.setHttpProxy(proxyadd);
			//163.172.31.44 
			proxy.setSslProxy(proxyadd);
			chromeOptions.setCapability("proxy", proxy);
			chromeOptions.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(chromeOptions);
			//39.111.193.84
			driver.get("https://www.ip138.com/");
//			driver.get("https://www.proxydocker.com/ja/whats-my-ips-address/");
			tl.setSavePicturePath(new String[0]);
			tl.randomSleep();
			tl.savePicture(driver);
		} catch (Exception e) {
			e.printStackTrace();
			driver.quit();
		}
	}
}