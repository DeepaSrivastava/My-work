package Testcases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import objectRepository.LoginPage;

public class LoginApplication {
	@Test
	public void login(){
		System.setProperty("webdriver.ie.driver","./IEDriverServer.exe");
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		WebDriver driver = new InternetExplorerDriver(caps);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://base.trakcare.glos.nhs.uk/trakcare/ENGL-BASE-2017/csp/logon.csp?LANGID=1");
		LoginPage log=new LoginPage(driver);
		log.usern().sendKeys("dsrivastava");
		log.password().sendKeys("System@12");
		log.go().click();
	}

}
