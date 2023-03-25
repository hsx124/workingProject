package com.hsx124.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Demo001 {
	static {
		System.setProperty("webdriver.edge.driver", "F:/msedgedriver.exe");
	}

	public static void main(String[] args) throws Exception {
		WebDriver driver = new EdgeDriver();
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
			driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2L));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5L));

			driver.navigate().to("https://www.apple.com/jp");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5L));

			WebElement iphoneLink = (WebElement) wait
					.until(
							ExpectedConditions
									.visibilityOfElementLocated(By.cssSelector(".ac-gn-link.ac-gn-link-iphone")));
			if (iphoneLink.isDisplayed()) {
				iphoneLink.click();
			}

			driver.findElement(By.className("chapternav-item-iphone-12")).click();
			driver.findElement(By.className("ac-ln-button")).click();
			screenShot(driver, "E:", "buttonClick");

			WebElement sizeElement = (WebElement) wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='6_1inch']")));
			if (sizeElement.isEnabled()) {
				sizeElement.click();
			}
			WebElement element = (WebElement) wait.until(
					ExpectedConditions.visibilityOfElementLocated(
							By.cssSelector("input[value='6_1inch']+label")));
			if (element.isDisplayed()) {
				element.click();
			}
			((WebElement) wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[value='256gb']+label"))))
							.click();
			WebElement noTradeIn = (WebElement) wait
					.until(ExpectedConditions.presenceOfElementLocated(By.id("noTradeIn")));
			if (noTradeIn.isEnabled()) {
				noTradeIn.click();
			}

			WebElement care = (WebElement) wait
					.until(ExpectedConditions.presenceOfElementLocated(By.name("applecareplus_59")));
			if (care.isEnabled()) {
				care.click();
			}
			WebElement cart = (WebElement) wait
					.until(ExpectedConditions.presenceOfElementLocated(By.name("add-to-cart")));
			if (cart.isEnabled()) {
				cart.click();
			}
		} catch (Exception e) {

			e.printStackTrace();
			Thread.sleep(10000L);
			driver.quit();
		} finally {
			Thread.sleep(10000L);
			driver.quit();
		}
	}

	public static void screenShot(WebDriver driver, String path, String filename) throws IOException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
		driver.switchTo().defaultContent();
		TakesScreenshot ts = (TakesScreenshot) (new Augmenter()).augment(driver);
		Path from = Paths.get(((File) ts.<File> getScreenshotAs(OutputType.FILE)).toURI());
		Path to = Paths.get(String.valueOf(path) + "\\" + filename + ".png", new String[0]);
		Files.move(from, to, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
	}
}