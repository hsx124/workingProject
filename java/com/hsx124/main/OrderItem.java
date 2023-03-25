package com.hsx124.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hsx124.pojo.CreditCard;
import com.hsx124.pojo.GiftCard;
import com.hsx124.pojo.Iphone;
import com.hsx124.pojo.User;
import com.hsx124.util.ToolUtil;
import com.hsx124.webconst.PageConst;

public class OrderItem
		implements PageConst {
	private Iphone ip;
	private User user;
	private CreditCard cc;
	private GiftCard gc;
	List<User> datas = new ArrayList<>();
	private List<String> proxyDatas = new ArrayList<>();

	public void doOrder() throws Exception {
		Map<String, Object> command = new HashMap<>();
		command.put("source", "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
		AtomicInteger orderCount = new AtomicInteger();
		File outputfile = new File(String.valueOf(System.getProperty("user.dir")) + File.separator +
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSS")) + ".log");
		FileWriter fw = new FileWriter(outputfile);
		PrintWriter out = new PrintWriter(fw, true);
		long starTime = System.currentTimeMillis();
		this.datas.stream().parallel().forEach(ur -> {
			ChromeDriver chromeDriver = null;

			ToolUtil tl = null;

			try {
				String orderNo;

				tl = new ToolUtil();

				tl.init();

				System.out.println("####################" + ur.getLastName() + "　" + ur.getFirstName() + "　"
						+ ur.getIhpone().getModel() + "　" + ur.getIhpone().getColor() + "　"
						+ ur.getIhpone().getScreenSize() + "　" + ur.getIhpone().getCapacity()
						+ "オーダー開始します####################");

				out.println("####################" + ur.getLastName() + "　" + ur.getFirstName() + "　"
						+ ur.getIhpone().getModel() + "　" + ur.getIhpone().getColor() + "　"
						+ ur.getIhpone().getScreenSize() + "　" + ur.getIhpone().getCapacity()
						+ "オーダー開始します####################");

				if (orderCount.get() == this.proxyDatas.size()) {
					orderCount.set(0);
				}

				synchronized (ur) {
					orderCount.incrementAndGet();
				}

				chromeDriver = new ChromeDriver(tl.getOption(new String[0]));

				chromeDriver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", command);

//				chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

				chromeDriver.manage().timeouts().scriptTimeout(Duration.ofMinutes(1));

				chromeDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));

				Actions action = new Actions((WebDriver) chromeDriver);

				chromeDriver.navigate().to("https://www.apple.com/jp");

				chromeDriver.manage().window().maximize();

				System.out.println("アップルサイトへのアクセスします。");

				out.println("アップルサイトへのアクセスします。");
				WebDriverWait wait = new WebDriverWait((WebDriver) chromeDriver, Duration.ofSeconds(2000L));
				tl.setSavePicturePath(ur.getLastName(), ur.getFirstName(), ur.getIhpone().getModel(),
						ur.getIhpone().getScreenSize(), ur.getIhpone().getColor(), ur.getIhpone().getCapacity(),
						LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSS")));
				
				while (true) {
					WebElement linkIphone = null;
					try {
						linkIphone = chromeDriver.findElement(By.linkText("iPhone"));
						if (linkIphone != null) {
							linkIphone.click();
							System.out.println("iPhoneリンクをクリックします。");
							out.println("iPhoneリンクをクリックします。");
							break;
						}
						System.out.println(linkIphone);
					} catch (NoSuchElementException e) {
						System.out.println(linkIphone);

						tl.folderRename((WebDriver) chromeDriver, ur, out);

						return;
					}

					action.moveToElement(linkIphone).pause(500L).click().build().perform();
				}

				Thread.sleep(1000L);

				try {
					while (true) {
						List<WebElement> itemsList = chromeDriver
								.findElements(By.cssSelector(".chapternav-items li a span"));

						if (ur.getIhpone().getModel().contains("iPhone 12 mini")) {
							ur.getIhpone().setModel("iPhone 12");
						} else if (ur.getIhpone().getModel().contains("iPhone 13 mini")) {
							ur.getIhpone().setModel("iPhone 13");
						} else if (ur.getIhpone().getModel().contains("iPhone 13 Pro Max")) {
							ur.getIhpone().setModel("iPhone 13 Pro");
						} else if (ur.getIhpone().getModel().contains("iPhone 14 Pro Max")) {
							ur.getIhpone().setModel("iPhone 14 Pro");
						}

						String model = null;

						Pattern pattern = Pattern.compile(ur.getIhpone().getModel(), 2);
						Matcher matcher = null;
						for (WebElement webEle : itemsList) {
							model = webEle.getText();
							matcher = pattern.matcher(model);
							if (matcher.matches()) {
								action.moveToElement(webEle).pause(500L).click().build().perform();
								System.out.println("対象機種を選択します。" + ur.getIhpone().getModel());
								out.println("対象機種を選択します。" + ur.getIhpone().getModel());
								break;
							}
						}
						if (model != null && model.equals(ur.getIhpone().getModel())) {
							break;
						}
						if (!chromeDriver.getCurrentUrl().contains("iphone-14-pro")) {
							return;
						}
					}
				} catch (NoSuchElementException e1) {
					e1.printStackTrace();

					tl.folderRename((WebDriver) chromeDriver, ur, out);

					return;
				}

				Thread.sleep(1000L);

				try {
					WebElement buyButton = chromeDriver.findElement(By.className("ac-ln-button"));

					action.moveToElement(buyButton).pause(500L).click().perform();
					System.out.println("購入ボタンを押下します。");
					out.println("購入ボタンを押下します。");
				} catch (NoSuchElementException e2) {
					System.out.println(String.valueOf(ur.getIhpone().getModel()) + "購入ボタンなし");

					if (chromeDriver.getCurrentUrl().indexOf("jp/iphone-14") == -1) {
						return;
					}

					chromeDriver.findElement(By.className("ac-ln-button")).click();
				}

				tl.randomSleep();

				while (true) {
					try {
						WebElement sizeElement = chromeDriver.findElement(
								By.cssSelector("input[value='" + ur.getIhpone().getScreenSize() + "']+label"));

						action.moveToElement(sizeElement).pause(500L).click().build().perform();

						System.out.println("購入する機種のサイズを選びます。" + ur.getIhpone().getScreenSize());

						out.println("購入する機種のサイズを選びます。" + ur.getIhpone().getScreenSize());
						if (sizeElement != null) {
							break;
						}
					} catch (NoSuchElementException e) {
						System.out.println("この機種" + ur.getIhpone().getModel() + "はサイズ選べません！");
						out.println("この機種" + ur.getIhpone().getModel() + "はサイズ選べません！");
						try {
							chromeDriver.findElement(By.id("dimensionScreensize"));
						} catch (NoSuchElementException e1) {
							break;
						}
						return;
					} catch (StaleElementReferenceException staleElementReferenceException) {
					}
				}

				Thread.sleep(800L);

				WebElement colorElement = chromeDriver.findElement(By
						.cssSelector("ul.colornav-items > li>input[value='" + ur.getIhpone().getColor() + "']+label"));

				colorElement.click();

				System.out.println("ul.colornav-items > li>input[value='" + ur.getIhpone().getColor() + "']");

				System.out.println("購入する機種の色を選択します。" + ur.getIhpone().getColor());

				out.println("購入する機種の色を選択します。" + ur.getIhpone().getColor());

				Thread.sleep(800L);

				WebElement mSizeElement = chromeDriver
						.findElement(By.xpath("//input[@value='" + ur.getIhpone().getCapacity() + "']"));

				action.moveToElement(mSizeElement).pause(500L).click().build().perform();

				System.out.println("購入する機種の容量を選びます。" + ur.getIhpone().getCapacity());

				out.println("購入する機種の容量を選びます。" + ur.getIhpone().getCapacity());

				Thread.sleep(800L);

				WebElement noTradeInElement = chromeDriver.findElement(By.id("noTradeIn_label"));

				action.moveToElement(noTradeInElement).pause(500L).click().build().perform();

				System.out.println("下取りに出さない。。。");

				out.println("下取りに出さない。。。");

				Thread.sleep(1500L);

				try {
					WebElement care = chromeDriver.findElement(By.id("applecareplus_59_noapplecare_label"));

					System.out.println("AppleCare加入しない。。");

					out.println("AppleCare加入しない。。");

					if (!care.isDisplayed()) {
						care = chromeDriver.findElement(By.id("applecareplus_58_noapplecare"));
					}

					action.moveToElement(care).pause(1000L).click().build().perform();
				} catch (NoSuchElementException e) {
					WebElement care = chromeDriver.findElement(By.id("applecareplus_58_noapplecare"));

					action.moveToElement(care).pause(1000L).click().build().perform();
				}

				tl.randomSleep();

				chromeDriver.findElement(By.name("add-to-cart")).click();

				System.out.println("バックに追加します。。。");

				out.println("バックに追加します。。。");

				tl.randomSleep();

				WebElement proccedBtn = chromeDriver.findElement(By.name("proceed"));

				Actions ac = new Actions((WebDriver) chromeDriver);

				ac.moveToElement(proccedBtn).release().pause(800L).click().build().perform();

				System.out.println("バックを確認します。。");

				out.println("バックを確認します。。");

				tl.randomSleep();

				WebElement countSelect = chromeDriver.findElement(By.tagName("select"));

				if (chromeDriver.getCurrentUrl().contains("/jp/shop/bag")) {
					tl.savePicture((WebDriver) chromeDriver);

					Select selectObjec = new Select(countSelect);

					System.out.println("購入数量を設定します。。" + ur.getIhpone().getOrderCount() + "台");

					out.println("購入数量を設定します。。" + ur.getIhpone().getOrderCount() + "台");

					selectObjec.selectByValue(String.valueOf(ur.getIhpone().getOrderCount()));
				} else {
					proccedBtn.click();

					tl.randomSleep();

					Select selectObjec = new Select(countSelect);

					selectObjec.selectByValue(String.valueOf(ur.getIhpone().getOrderCount()));
				}

				tl.savePicture((WebDriver) chromeDriver);

				Thread.sleep(1000L);

				((ChromiumDriver) chromeDriver).executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", command);

				WebElement shopCheckout = chromeDriver.findElement(By.id("shoppingCart.actions.checkout"));

				shopCheckout.click();
				System.out.println("注文手続きへ。。");
				out.println("注文手続きへ。。");
				tl.randomSleep();
				if ("ゲスト".equals(ur.getPayMethod())) {
					try {
						WebElement guestElement = chromeDriver.findElement(By.id("signIn.guestLogin.guestLogin"));
						guestElement.click();
						System.out.println("ゲストとしてログインします。。");
						out.println("ゲストとしてログインします。。");
						tl.randomSleep();
					} catch (NoSuchElementException e) {
						tl.folderRename((WebDriver) chromeDriver, ur, out);

						return;
					}
				} else {
					String windowHandle = chromeDriver.getWindowHandle();

					WebElement accountInput = chromeDriver.switchTo().frame("aid-auth-widget-iFrame")
							.findElement(By.id("account_name_text_field"));

					accountInput.clear();

					accountInput.sendKeys(new CharSequence[] { "923198281@qq.com" });

					tl.randomSleep();

					accountInput.sendKeys(
							new CharSequence[] { Keys.chord(new CharSequence[] { (CharSequence) Keys.ENTER }) });

					tl.randomSleep();

					tl.savePicture((WebDriver) chromeDriver);
					WebElement passwordInput = chromeDriver.findElement(By.id("password_text_field"));
					passwordInput.sendKeys(new CharSequence[] { "Hsx19900829" });
					passwordInput.sendKeys(
							new CharSequence[] { Keys.chord(new CharSequence[] { (CharSequence) Keys.ENTER }) });
					chromeDriver.switchTo().window(windowHandle);
				}
				Thread.sleep(800L);
				tl.savePicture((WebDriver) chromeDriver);
				WebElement takeItem = chromeDriver
						.findElement(By.cssSelector("label[for='fulfillmentOptionButtonGroup1']"));
				takeItem.click();
				System.out.println("受け取り方法を選択します。。" + takeItem.getText());
				out.println("受け取り方法を選択します。。" + takeItem.getText());
				tl.randomSleep();
				try {
					chromeDriver.findElement(By.cssSelector(".rs-edit-location-button"));
				} catch (NoSuchElementException e) {
					try {
						WebElement storeLocationEle = chromeDriver
								.findElement(By.id("checkout.fulfillment.pickupTab.pickup.storeLocator.searchInput"));

						storeLocationEle.sendKeys(new CharSequence[] { "104-0061" });

						storeLocationEle.sendKeys(
								new CharSequence[] { Keys.chord(new CharSequence[] { (CharSequence) Keys.ENTER }) });
						System.out.println("配送先の場所を選びます。。" + storeLocationEle.getText());
						out.println("配送先の場所を選びます。。" + storeLocationEle.getText());
					} catch (NoSuchElementException e1) {
						WebElement uketori = chromeDriver.findElement(By.cssSelector(".rs-fulfillment-sectiontitle"));

						if (uketori.getText().indexOf("配送のみ") != -1) {
							System.out.println("ご注文の商品" + ur.getIhpone().getModel() + "のお受け取りは配送のみとなります。");

							out.println("ご注文の商品" + ur.getIhpone().getModel() + "のお受け取りは配送のみとなります。");
						}
					}
				}

				tl.savePicture((WebDriver) chromeDriver);

				Thread.sleep(800L);

				List<WebElement> storeList = chromeDriver
						.findElements(By.cssSelector("ul.rt-storelocator-store-group li"));

				int enabledCount = 0;

				for (WebElement webElement : storeList) {
					WebElement inputElement = webElement.findElement(By.name("store-locator-result"));

					if (!inputElement.isEnabled()) {
						enabledCount++;

						continue;
					}

					String storeName = webElement.findElement(By.cssSelector(".form-selector-title")).getText();

					Pattern pt = Pattern.compile("(apple)\\s*(新宿|丸の内|銀座|表参道).*", 2);

					Matcher matcher = pt.matcher(storeName);

					if (matcher.matches()) {
						System.out.println("受け取りストアを選択します。" + storeName);

						out.println("受け取りストアを選択します。" + storeName);

						tl.savePicture((WebDriver) chromeDriver);
						webElement.click();
						break;
					}
				}
				if (enabledCount == 3 || enabledCount == 4) {
					System.out.println("受け取れる店舗ありません!!!");
					out.println("受け取れる店舗ありません!!!");
					tl.savePicture((WebDriver) chromeDriver);
					String targetPath = tl.getTargetPath();
					File file = new File(targetPath);
					file.renameTo(new File(String.valueOf(targetPath) + "_受け取るストアなし"));
					return;
				}
				try {
					WebElement timeZoneElement = chromeDriver.findElement(
							By.id("checkout.fulfillment.pickupTab.pickup.timeSlot.dateTimeSlots.timeSlotValue"));
					Select timeZoneSelect = new Select(timeZoneElement);
					timeZoneSelect.selectByIndex(timeZoneSelect.getAllSelectedOptions().size());
				} catch (NoSuchElementException noSuchElementException) {
				}

				chromeDriver.findElement(By.id("rs-checkout-continue-button-bottom")).click();

				System.out.println("受け取りの詳細に進むボタン押下。。");

				out.println("受け取りの詳細に進むボタン押下。。");

				Thread.sleep(1000L);

				tl.savePicture((WebDriver) chromeDriver);

				try {
					WebElement rcvInfoElement = chromeDriver
							.findElement(By.cssSelector("label[for='pickupOptionButtonGroup0']"));

					rcvInfoElement.click();
					System.out.println("ご注文の商品はどなたが受け取りになりますか？" + rcvInfoElement.getText());
					out.println("ご注文の商品はどなたが受け取りになりますか？" + rcvInfoElement.getText());
				} catch (NoSuchElementException e) {
					tl.folderRename((WebDriver) chromeDriver, ur, out);

					return;
				}

				Thread.sleep(1500L);

				chromeDriver.findElement(By.id("checkout.pickupContact.selfPickupContact.selfContact.address.lastName"))
						.sendKeys(new CharSequence[] { ur.getLastName() });

				System.out.println("受取人姓：" + ur.getLastName());

				out.println("受取人姓：" + ur.getLastName());

				chromeDriver
						.findElement(By.id("checkout.pickupContact.selfPickupContact.selfContact.address.firstName"))
						.sendKeys(new CharSequence[] { ur.getFirstName() });

				System.out.println("受取人名：" + ur.getFirstName());

				out.println("受取人名：" + ur.getFirstName());

				chromeDriver
						.findElement(By.id("checkout.pickupContact.selfPickupContact.selfContact.address.emailAddress"))
						.sendKeys(new CharSequence[] { ur.getEmail() });

				System.out.println("受取人メール：" + ur.getEmail());

				out.println("受取人メール：" + ur.getEmail());

				chromeDriver
						.findElement(By.id("checkout.pickupContact.selfPickupContact.selfContact.address.mobilePhone"))
						.sendKeys(new CharSequence[] { ur.getMobile() });

				System.out.println("受取人連絡先：" + ur.getMobile());

				out.println("受取人連絡先：" + ur.getMobile());

				chromeDriver.findElement(By.id("rs-checkout-continue-button-bottom")).click();

				System.out.println("支払いに進ボタン押下。。");

				out.println("支払いに進ボタン押下。。");

				tl.savePicture((WebDriver) chromeDriver);

				Thread.sleep(1300L);

				if (ur.getCreditCard() == null) {
					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.giftCard.giftCardInput.resetFields"))
							.click();

					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.giftCard.giftCardInput.giftCard"))
							.sendKeys(new CharSequence[] { ur.getGiftCard().getGiftNo() });

					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.giftCard.giftCardInput.applyGiftCard"))
							.click();

					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.giftCard.billingAddress.address.lastName"))
							.sendKeys(new CharSequence[] { ur.getGiftCard().getLastName() });

					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.giftCard.billingAddress.address.firstName"))
							.sendKeys(new CharSequence[] { ur.getGiftCard().getFirstName() });

					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.giftCard.billingAddress.address.postalCode"))
							.sendKeys(new CharSequence[] { ur.getGiftCard().getPostCode() });

					tl.savePicture((WebDriver) chromeDriver);

					WebElement addressElement = chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.giftCard.billingAddress.address.state"));

					Select address = new Select(addressElement);

					address.selectByValue(ur.getGiftCard().getState());

					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.giftCard.billingAddress.address.city"))
							.sendKeys(new CharSequence[] { ur.getGiftCard().getCity() });

					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.giftCard.billingAddress.address.street"))
							.sendKeys(new CharSequence[] { ur.getGiftCard().getStreet() });

					if (ur.getGiftCard().getStreet2() != " ") {
						chromeDriver.findElement(By.id(
								"checkout.billing.billingOptions.selectedBillingOptions.giftCard.billingAddress.address.street2"))
								.sendKeys(new CharSequence[] { ur.getGiftCard().getStreet2() });
					}

					System.out.println("ギフトカードで支払います。。");

					out.println("ギフトカードで支払います。。");
				} else {
					chromeDriver.findElement(By.id("checkout.billing.billingoptions.credit")).click();

					tl.randomSleep();

					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.creditCard.cardInputs.cardInput-0.cardNumber"))
							.sendKeys(new CharSequence[] { ur.getCreditCard().getCardNumber() });

					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.creditCard.cardInputs.cardInput-0.expiration"))
							.sendKeys(new CharSequence[] { ur.getCreditCard().getExpiration() });

					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.creditCard.cardInputs.cardInput-0.securityCode"))
							.sendKeys(new CharSequence[] { ur.getCreditCard().getSecurityCode() });

					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.creditCard.billingAddress.address.lastName"))
							.sendKeys(new CharSequence[] { ur.getCreditCard().getLastName() });

					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.creditCard.billingAddress.address.firstName"))
							.sendKeys(new CharSequence[] { ur.getCreditCard().getFirstName() });

					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.creditCard.billingAddress.address.postalCode"))
							.sendKeys(new CharSequence[] { ur.getCreditCard().getPostCode() });

					tl.savePicture((WebDriver) chromeDriver);

					WebElement addressElement = chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.creditCard.billingAddress.address.state"));

					Select address = new Select(addressElement);

					address.selectByValue(ur.getCreditCard().getState());

					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.creditCard.billingAddress.address.city"))
							.sendKeys(new CharSequence[] { ur.getCreditCard().getCity() });

					chromeDriver.findElement(By.id(
							"checkout.billing.billingOptions.selectedBillingOptions.creditCard.billingAddress.address.street"))
							.sendKeys(new CharSequence[] { ur.getCreditCard().getStreet() });

					if (ur.getCreditCard().getStreet2() != " ") {
						chromeDriver.findElement(By.id(
								"checkout.billing.billingOptions.selectedBillingOptions.creditCard.billingAddress.address.street2"))
								.sendKeys(new CharSequence[] { ur.getCreditCard().getStreet2() });
					}

					System.out.println("クレジットカードで支払います。。");

					out.println("クレジットカードで支払います。。");
				}

				Thread.sleep(1000L);

				tl.savePicture((WebDriver) chromeDriver);

				chromeDriver.findElement(By.id("rs-checkout-continue-button-bottom")).click();

				System.out.println("注文の確認ボタンを押下します。。");

				out.println("注文の確認ボタンを押下します。。");

				Thread.sleep(1500L);

				chromeDriver.findElement(By.id("rs-checkout-continue-button-bottom")).click();

				System.out.println("最後の注文ボタンを押下します。。");
				out.println("最後の注文ボタンを押下します。。");
				Thread.sleep(4300L);
				tl.savePicture((WebDriver) chromeDriver);
				do {
					orderNo = "";
					orderNo = chromeDriver.findElement(By.cssSelector("a.as-buttonlink")).getText();
					System.out.println(String.valueOf(ur.getLastName()) + "　" + ur.getFirstName() + "　" + orderNo);
					out
							.println(String.valueOf(ur.getLastName()) + "　" + ur.getFirstName() + "　" + orderNo);
					tl.savePicture((WebDriver) chromeDriver);
				} while (orderNo.trim().equals(""));
				tl.savePicture((WebDriver) chromeDriver);
				long endTime = System.currentTimeMillis();
				System.out.println("####################" + ur.getLastName() + "　" + ur.getFirstName() + "　"
						+ ur.getIhpone().getModel() + "　" + ur.getIhpone().getColor() + "　"
						+ ur.getIhpone().getScreenSize() + "　" + ur.getIhpone().getCapacity()
						+ "オーダー終了します####################");
				System.out.println(String.valueOf((endTime - starTime) / 1000L) + "秒かかりました。。");
				out.println("####################" + ur.getLastName() + "　" + ur.getFirstName() + "　"
						+ ur.getIhpone().getModel() + "　" + ur.getIhpone().getColor() + "　"
						+ ur.getIhpone().getScreenSize() + "　" + ur.getIhpone().getCapacity()
						+ "オーダー終了します####################");
				out.println(String.valueOf((endTime - starTime) / 1000L) + "秒かかりました。。");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					tl.folderRename((WebDriver) chromeDriver, ur, out);
					tl.savePicture((WebDriver) chromeDriver);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				long endTime = System.currentTimeMillis();

				System.out.println(String.valueOf((endTime - starTime) / 1000L) + "秒かかりました。。");

				out.println(String.valueOf((endTime - starTime) / 1000L) + "秒かかりました。。");
			} finally {
				chromeDriver.quit();
			}
		});
		out.close();
	}

	public void readInfoFromExcel() throws Exception, FileNotFoundException, IOException {
		String excelPath = String.valueOf(System.getProperty("user.dir")) + File.separator + "sample.xlsx";
		File excel = new File(excelPath);
		Workbook workbook = WorkbookFactory.create(new FileInputStream(excel));

		for (Sheet sheet : workbook) {
			for (int rowNum = sheet.getFirstRowNum() + 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);

				if ("購入情報".equals(sheet.getSheetName())) {
					this.ip = new Iphone();
					this.ip.setModel(row.getCell(1).getStringCellValue());
					this.ip.setScreenSize(row.getCell(2).getStringCellValue());
					this.ip.setColor(row.getCell(3).getStringCellValue());
					this.ip.setCapacity(row.getCell(4).getStringCellValue());
					this.user = new User();
					this.user.setLastName(row.getCell(5).getStringCellValue());
					this.user.setFirstName(row.getCell(6).getStringCellValue());
					this.user.setEmail(row.getCell(7).getStringCellValue());
					this.user.setMobile(row.getCell(8).getStringCellValue());

					if (row.getCell(9).getStringCellValue().contains("ギフトカード")) {
						this.gc = new GiftCard();
						Row giftRow = workbook.getSheet("ギフト").getRow(row.getRowNum());
						this.gc.setGiftNo(giftRow.getCell(1).getStringCellValue());
						this.gc.setLastName(giftRow.getCell(2).getStringCellValue());
						this.gc.setFirstName(giftRow.getCell(3).getStringCellValue());
						this.gc.setPostCode(giftRow.getCell(4).getStringCellValue());
						this.gc.setState(giftRow.getCell(5).getStringCellValue());
						this.gc.setCity(giftRow.getCell(6).getStringCellValue());
						this.gc.setStreet(giftRow.getCell(7).getStringCellValue());
						System.out.println(this.gc);
						this.gc.setStreet2((giftRow.getCell(8) == null) ? "" : giftRow.getCell(8).getStringCellValue());
					} else {

						this.cc = new CreditCard();
						Row creditRow = workbook.getSheet("クレジットカード").getRow(row.getRowNum());
						this.cc.setCardNumber(creditRow.getCell(1).getStringCellValue());
						this.cc.setExpiration(creditRow.getCell(2).getStringCellValue());
						this.cc.setSecurityCode(creditRow.getCell(3).getStringCellValue());
						this.cc.setLastName(creditRow.getCell(4).getStringCellValue());
						this.cc.setFirstName(creditRow.getCell(5).getStringCellValue());
						this.cc.setPostCode(creditRow.getCell(6).getStringCellValue());
						this.cc.setState(creditRow.getCell(7).getStringCellValue());
						this.cc.setCity(creditRow.getCell(8).getStringCellValue());
						this.cc.setStreet(creditRow.getCell(9).getStringCellValue());
						this.cc.setStreet2(
								(creditRow.getCell(10) == null) ? "" : creditRow.getCell(10).getStringCellValue());
					}
					this.user.setPayMethod(row.getCell(10).getStringCellValue());
					this.user.setCreditCard(this.cc);
					this.user.setIhpone(this.ip);
					this.user.setGiftCard(this.gc);
					this.datas.add(this.user);
				}
			}
		}

		this.datas.forEach(System.out::println);
		workbook.close();
	}

	public void readTxt() {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("proxy.txt");
		Scanner sc = new Scanner(inputStream, "UTF-8");
		while (sc.hasNext()) {
			String content = sc.nextLine();
			if (content.trim().startsWith("#") || "".equals(content)) {
				continue;
			}
			this.proxyDatas.add(content);
		}
		sc.close();
		this.proxyDatas.forEach(System.out::println);
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
		(new OrderItem()).readInfoFromExcel();
	}
}