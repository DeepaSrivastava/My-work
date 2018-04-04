package stepdefin;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class FbSmokeTest 
{
	WebDriver driver;	
	
	@SuppressWarnings("deprecation")
	@Given("^Open ie and start application$")
	public void Open_ie_and_start_application() throws Throwable {
		System.setProperty("webdriver.ie.driver","./IEDriverServer.exe");
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		driver = new InternetExplorerDriver(caps);
		driver.get("https://www.linkedin.com/m/login/");	
	   System.out.println("Login to ie");
	  
	   
	}

	@When("^I enter valid \"([^\"]*)\" and valid \"([^\"]*)\"$")
	public void I_enter_valid_and_valid(String uname, String pass) throws Throwable {
		 int frame=driver.findElements(By.tagName("iframe")).size();
		   System.out.println(frame);
		   driver.switchTo().frame(0);
		   driver.findElement(By.id("session_key-login")).clear();
		   driver.findElement(By.id("session_key-login")).sendKeys(uname);
			driver.findElement(By.id("session_password-login")).sendKeys(pass);
			driver.findElement(By.id("btn-primary")).click();
		 System.out.println("I enter valid username  and valid password");
	}
	

	@Then("^user should be login successfully$")
	public void user_should_be_login_successfully() throws Throwable {
		 System.out.println("user should be login successfully");
	}

}
