package trakDataSetUpLibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

/**
 * This class invoke Browser and URL and Login to application (with/without
 * card)
 *
 */

	public class BrowUrlLoginSection {
	
		String Browsername;
		String URLname;
	
		protected static WebDriver driver;
		public static FileInputStream reader;
		public static WebDriverWait wait;
		public static Properties prop = new Properties();
		private static boolean acceptNextAlert = true;
	
		// ProjectSetUp pro=new ProjectSetUp();
		@BeforeSuite
	
		public void loadLogin()
				throws IOException, InterruptedException, EncryptedDocumentException, InvalidFormatException {
	
			reader = new FileInputStream("./Trak_Script/PropertiesFile/BrowUrl.properties");
			prop.load(reader);
	
			Browsername = prop.getProperty("Browser");
			URLname = prop.getProperty("URL");
	
			if (Browsername.equalsIgnoreCase("ie")) {
				// System.setProperty("webdriver.ie.driver","./IEDriverServer.exe");
				// Set capability of IE driver to Ignore all zones browser protected
				// mode settings.
				DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				driver = new InternetExplorerDriver(caps);
				// driver=new RemoteWebDriver(new
				// URL("http://10.91.91.176:4444/wd/hub"),caps);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.get(URLname);
			}
	
			if (Browsername.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "./geckodriver.exe");
				driver = new FirefoxDriver();
				// driver.manage().window().maximize();
				driver.get(URLname);
			}
			if (Browsername.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
				driver = new ChromeDriver();
				// driver.manage().window().maximize();
				driver.get(URLname);
			}
	
			DataDrivenUsingXlsx ddxl = new DataDrivenUsingXlsx();
			List<Object> outerAry = ddxl.getExcelData("./DataFile/trakDataSetUpLibrary.xlsx", "BrowUrlLoginSection");
			outerAry.remove(0);
			Object obj = outerAry;
			String str = obj.toString();
			str = str.replace("[", "");
			str = str.replace("]", "").trim();
			String[] childAry = str.split(",");
	
			boolean log = isElementPresent(By.id("Logon"));
			/*
			 * Screenshot sc=new
			 * AShot().shootingStrategy(ShootingStrategies.viewportPasting(50)).
			 * takeScreenshot(driver); ImageIO.write(sc.getImage(), "PNG", new
			 * File("./errorScreenshots/Logon.png"));
			 */if (log) {
	
				System.out.println("login without Card");
				driver.findElement(By.id(prop.getProperty("Username"))).sendKeys(childAry[0].toString().trim());
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				Thread.sleep(1000);
				driver.findElement(By.id(prop.getProperty("Password"))).sendKeys(childAry[1].toString().trim());
				driver.findElement(By.id(prop.getProperty("Logon"))).click();
				// driver.findElement(By.xpath("//label[text()='ENXX Admin OP Wait
				// List']")).click();
				// driver.findElement(By.xpath("//label[text()='ENXX Nurses
				// Emergency']")).click();
			} else {
				System.out.println("login with Card");
				driver.findElement(By.xpath("//label[text()='ENXX Admin Emergency']")).click();
			}
		}
	
		public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
			if (testResult.getStatus() == ITestResult.FAILURE) {
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String fileNameSuffix = new SimpleDateFormat("yyyyMMddHHmmss'.png'").format(new Date());
				FileUtils.copyFile(scrFile, new File("./takeScreenShotOnFailure" + testResult.getName() + "-"
						+ Arrays.toString(testResult.getParameters()) + fileNameSuffix));
			}
		}
	
		protected static boolean isElementPresent(By by) {
			try {
				driver.findElement(by);
				return true;
			} catch (NoSuchElementException e) {
				return false;
			}
		}
	
		@AfterSuite
		public void end() {
			// driver.quit();
		}
	
		public static String closeAlertAndGetItsText() {
			try {
				Alert alert = driver.switchTo().alert();
				String alertText = alert.getText();
				System.out.println("alert text was: " + alertText);
				if (acceptNextAlert) {
					alert.accept();
				} else {
					alert.dismiss();
				}
				return alertText;
			} finally {
				acceptNextAlert = true;
			}
		}
	
		protected static boolean isAlertPresent() {
			try {
				driver.switchTo().alert();
				return true;
			} catch (NoAlertPresentException e) {
				return false;
			}
		}
	
}
