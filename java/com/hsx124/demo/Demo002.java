package com.hsx124.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class Demo002 {
	static {
		Path path = Path.of("src\\main\\resources", new String[] { "chromedriver.exe" });
		System.setProperty("webdriver.chrome.driver", path.toAbsolutePath().toString());
	}

	public static void main(String[] args) throws Exception {
		ChromeOptions option = new ChromeOptions();

		WebDriver driver = new ChromeDriver(option);
		driver.manage().window().maximize();

		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
			driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2L));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5L));

			driver.navigate().to("https://www.apple.com/jp");
			driver.manage().window().maximize();
			WebElement linkIphone = driver.findElement(By.linkText("iPhone"));
			Actions action = new Actions(driver);
			Thread.sleep(1000L);
			action.moveToElement(linkIphone).click();
			action.perform();

			Actions action1 = new Actions(driver);
			WebElement iphone12 = driver.findElement(By.className("chapternav-item-iphone-12"));
			System.out.println(iphone12.getTagName());
			Thread.sleep(1000L);
			action1.moveToElement(iphone12);
			action1.click().perform();

			Actions action2 = new Actions(driver);
			WebElement buyButton = driver.findElement(By.className("ac-ln-button"));
			Thread.sleep(1000L);
			System.out.println(buyButton.getTagName());
			action2.moveToElement(buyButton).pause(1000L).click().perform();

			Actions action3 = new Actions(driver);
			Thread.sleep(5000L);

			WebElement sizeElement = driver.findElement(By.cssSelector("input[value=\"6_1inch\"]+label"));

			action3.moveToElement(sizeElement).pause(1000L).click().perform();

			Actions action4 = new Actions(driver);
			WebElement colorElement = driver.findElement(By.cssSelector("ul.colornav-items > li:nth-child(2)"));
			Thread.sleep(1000L);
			action4.moveToElement(colorElement).pause(1000L).click().perform();

			Actions action5 = new Actions(driver);
			WebElement mSizeElement = driver.findElement(By.xpath("//input[@value='256gb']"));
			Thread.sleep(1000L);
			action5.moveToElement(mSizeElement).pause(1000L).click().perform();

			Actions action6 = new Actions(driver);
			WebElement noTradeInElement = driver.findElement(By.id("noTradeIn_label"));
			Thread.sleep(1000L);
			action6.moveToElement(noTradeInElement).pause(1000L).click().perform();

			Actions action7 = new Actions(driver);
			WebElement care = driver.findElement(By.id("applecareplus_59_noapplecare_label"));
			Thread.sleep(1000L);
			action7.moveToElement(care).pause(1000L).click().build().perform();

			Thread.sleep(1000L);
			WebElement backBtn = driver.findElement(By.name("add-to-cart"));
			backBtn.click();

			savePicture(backBtn);
		} catch (Exception e) {

			e.printStackTrace();
			driver.quit();
		} finally {
			Thread.sleep(10000L);
			driver.quit();
		}
	}

	public static WebElement getWebElement(WebDriver driver, String cssSelector) {
		WebElement webElement = driver.findElement(By.cssSelector(cssSelector));
		return webElement;
	}

	public static void actionClick(Actions action, WebElement element) {
		action.release();
		action.moveToElement(element).pause(1000L).click().build().perform();
	}

	public static Duration getRandomSeconds() {
		Random rd = new Random();
		int second = rd.nextInt(3) + 1;
		return Duration.ofSeconds(second);
	}

	public static void savePicture(WebElement ele) throws IOException {
		File screenshotAs = ele.<File> getScreenshotAs(OutputType.FILE);
		Path path = Paths.get(screenshotAs.toURI());
		Path to = Paths.get("E:" + File.separator + ele.getText() + ".png", new String[0]);
		Files.move(path, to, new java.nio.file.CopyOption[0]);
	}
}