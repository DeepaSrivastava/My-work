package Example;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CrossBrowserTest {
		public static void main(String[] args) throws MalformedURLException {
	    		
	    		 WebDriver driver;
	    		 DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
	 			caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
	 			caps.setPlatform(Platform.WINDOWS);
	    		 
	    		 driver=new RemoteWebDriver(new URL("http://10.91.91.176:4444/wd/hub"),caps);
	    		 driver.get("http://google.com");
	    		 System.out.println(driver.getTitle());
	    		 System.out.println("google webpage");
	     
	        }


}
