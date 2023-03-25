package com.hsx124.demo;

import java.nio.file.Path;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FireFoxDemo {
	public static void main(String[] args) {
		Path path = Path.of("src\\main\\resources", new String[] { "geckodriver.exe" });
		System.setProperty("webdriver.gecko.driver", path.toAbsolutePath().toString());
		FirefoxProfile pp = new FirefoxProfile();
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("marionatte", false);
		FirefoxOptions option = new FirefoxOptions();
		option.merge(dc);
		option.setBinary("C:\\Users\\shuxi\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
		Proxy proxy = new Proxy();
		String proxyServer = "13850146355_area-JP:hsx19900829@proxy.stormip.cn:1000";
		proxy.setHttpProxy(proxyServer);
		option.setProxy(proxy);
		WebDriver driver = new FirefoxDriver();
		driver.get("https://www.ip138.com/");
	}
}