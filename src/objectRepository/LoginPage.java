package objectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	WebDriver driver;
	public LoginPage(WebDriver driver2) {
		this.driver=driver2;
		PageFactory.initElements(driver2, this); //for page object factory
	}
	
	
	//page object
	//By username=By.id("USERNAME");
	By password=By.id("PASSWORD");
	By logon=By.id("Logon");
	/*public WebElement username(){
		return driver.findElement(username);
	}*/
	public WebElement password(){
		return driver.findElement(password);
	}
	public WebElement go(){
		return driver.findElement(logon);
	}
	
	//page factory Style
	
	@FindBy(id="USERNAME")
	WebElement user;
	
	public WebElement usern(){
		return user;
	}
}
