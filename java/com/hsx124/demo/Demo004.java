package com.hsx124.demo;

import java.io.File;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.hsx124.util.ToolUtil;

public class Demo004 {
	public static void main(String[] args) {
		try {
			ToolUtil tl = new ToolUtil();
			tl.init();

			ChromeOptions option = new ChromeOptions();

			option.addExtensions(new File[] { new File("E:\\proxy.zip") });

			option.setPageLoadStrategy(PageLoadStrategy.NONE);

			WebDriver driver = new ChromeDriver(option);

			driver.get("https://www.ip138.com/");
			tl.setSavePicturePath(new String[0]);
			tl.randomSleep();
			tl.savePicture(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}