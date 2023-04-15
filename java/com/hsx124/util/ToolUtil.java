package com.hsx124.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.hsx124.pojo.Iphone;
import com.hsx124.pojo.User;
import com.hsx124.webconst.PageConst;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ToolUtil
		implements PageConst {
	private long waitLoadBaseTime = 1850L;
	private int waitLoadRandomTime = 1850;
	private Random random = new Random(System.currentTimeMillis());

	private String targetPath;

	/**
	 * 初期化
	 */
	public void init() {
		//		System.setProperty("webdriver.chrome.driver", "E:\\order\\chromedriver.exe");
		WebDriverManager.chromedriver().setup();
	}

	public void setManage(WebDriver driver) {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(PAGE_WAIT_TIME));
		driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(SCRIPT_WAIT_TIME));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIME));
	}

	/**
	 * スリープ時間の設定
	 * @throws Exception
	 */
	public void randomSleep() throws Exception {
		long time = this.waitLoadBaseTime + this.random.nextInt(this.waitLoadRandomTime);
		Thread.sleep(time);
	}

	/**
	 * ウェブページのハートコピーを取得
	 * @param driver
	 * @throws IOException
	 */
	public void savePicture(WebDriver driver) throws IOException {
		String directoryName = getTargetPath();
		File file = new File(directoryName);
		if (!file.exists()) {
			file.mkdirs();
		}
		File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Path path = Paths.get(screenshotAs.toURI());
		Path to = Paths.get(String.valueOf(directoryName) + File.separator +
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSS")) + ".png", new String[0]);
		Files.move(path, to, new java.nio.file.CopyOption[0]);
	}

	/**
	 * フォルダ名のリネーム
	 * @param driver
	 * @param ur　ユーザインスタンス
	 * @param out
	 * @throws IOException
	 */
	public void folderRename(WebDriver driver, User ur, PrintWriter out) throws IOException {
		savePicture(driver);
		String targetPath = getTargetPath();
		File file = new File(targetPath);
		file.renameTo(new File(targetPath + "_オーダー失敗"));

		System.out.println("####################" +
				ur.getLastName() + "　" + ur.getFirstName() + "　" + ur.getIhpone().getModel() + "　" +
				ur.getIhpone().getColor() + "　" + ur.getIhpone().getScreenSize() + "　" +
				ur.getIhpone().getCapacity() + "オーダー失敗しました。####################");
		out.println("####################" +
				ur.getLastName() + "　" + ur.getFirstName() + "　" + ur.getIhpone().getModel() + "　" +
				ur.getIhpone().getColor() + "　" + ur.getIhpone().getScreenSize() + "　" +
				ur.getIhpone().getCapacity() + "オーダー失敗しました。####################");
	}

	/**
	 * パス取得
	 * @return
	 */
	public String getTargetPath() {
		return this.targetPath;
	}

	public void setSavePicturePath(String... args) {
		StringBuilder sb = new StringBuilder();
		for (String string : args) {
			sb.append(string);
		}

		this.targetPath = System.getProperty("user.dir") + File.separator + "test" + File.separator
				+ String.join("_", sb.toString().split(" "));
	}

	public ChromeOptions getOption(String... proxyIP) {
		ChromeOptions option = new ChromeOptions();

		if (proxyIP.length != 0) {
			String proxyServer = proxyIP[0];
			Proxy proxy = (new Proxy()).setHttpProxy(proxyServer).setSslProxy(proxyServer);

			option.setProxy(proxy);
			System.out.println(proxy.getHttpProxy());
		}
		String[] optionArguments = {
				"--headless",
				"--start-maximized",
				"--hide-scrollbars",
				"--no-sanlbox",
				"--disable-gpu",
				"--ignore-certificate-errors",
				"--incognito", "disabled-infobars",
				"--window-size=1920,1080",
				"--disable-blink-features=AutomationControlled",
				"user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36",
				"--remote-allow-origins=*"
		};

		option.addArguments(optionArguments);
		option.setExperimentalOption("useAutomationExtension", Boolean.valueOf(false));
		option.setExperimentalOption("excludeSwitches", Collections.singleton("enable-automation"));
		option.setPageLoadStrategy(PageLoadStrategy.NONE);
		return option;
	}

	public void getItemsInfo() throws Exception {
		init();
		ChromeDriver chromeDriver = new ChromeDriver(getOption(new String[0]));
		chromeDriver.navigate().to("https://www.apple.com/jp");
		randomSleep();
		savePicture((WebDriver) chromeDriver);
		chromeDriver.findElement(By.className("ac-gn-link-iphone")).click();
		randomSleep();
		Pattern pattern = Pattern.compile(".*iphone[-_].*", 2);
		List<WebElement> findElements = chromeDriver.findElements(By.cssSelector(".chapternav-item .chapternav-link"));
		List<String> links = new ArrayList<>();
		for (WebElement webElement : findElements) {
			String iphoneLink = webElement.getAttribute("href");
			if (pattern.matcher(iphoneLink).matches()) {
				System.out.println(iphoneLink);
				links.add(iphoneLink);
			}
		}

		Map<String, Iphone> map = new HashMap<>();
		for (String string : links) {
			String titile;
			chromeDriver.navigate().to(string);
			randomSleep();
			savePicture((WebDriver) chromeDriver);
			System.out.println(string);

			try {
				titile = chromeDriver.findElement(By.cssSelector(".ac-ln-title:nth-child(2)")).getText();
				System.out.println("title:" + titile);
			} catch (Exception e1) {
				System.out.println("no such element");
				titile = string.substring(string.lastIndexOf("/") + 1);
			}
			Iphone iphone = new Iphone();

			try {
				WebElement buyButton = chromeDriver.findElement(By.className("ac-ln-button"));
				buyButton.click();
			} catch (NoSuchElementException e) {
				System.out.println("no such element");
			}
			randomSleep();

			List<WebElement> screenSizeList = chromeDriver.findElements(By.name("dimensionScreensize"));
			String[] screensize = new String[screenSizeList.size()];

			for (int i = 0; i < screenSizeList.size(); i++) {
				screensize[i] = ((WebElement) screenSizeList.get(i)).getAttribute("value");
			}

			randomSleep();
			List<WebElement> colorList = chromeDriver.findElements(By.cssSelector(".colornav-item input"));

			String[] colors = new String[colorList.size()];
			for (int j = 0; j < colorList.size(); j++) {
				colors[j] = ((WebElement) colorList.get(j)).getAttribute("value");
			}
			List<WebElement> typeList = chromeDriver.findElements(By.name("dimensionCapacity"));
			String[] types = new String[typeList.size()];

			for (int k = 0; k < typeList.size(); k++) {
				types[k] = ((WebElement) typeList.get(k)).getAttribute("value");
			}

			map.put(titile, iphone);
		}

		for (Map.Entry<String, Iphone> entry : map.entrySet()) {
			String key = entry.getKey();
			Iphone val = entry.getValue();
			System.out.println(String.valueOf(key) + "-->" + val);
		}
	}

	//	public static void main(String[] args) {
	//		try {
	//			(new ToolUtil()).randomSleep();
	//		} catch (Exception e) {
	//
	//			e.printStackTrace();
	//		}
	//	}
}