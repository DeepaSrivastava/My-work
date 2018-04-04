package trakDataSetUpLibrary;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class ProjectSetUp extends BrowUrlLoginSection {
	
	public void switchToMultiWindow(WebDriver driver, By by) throws InterruptedException{
		 Set<String> beforePopup = driver.getWindowHandles();
		    
		    for (String handle: driver.getWindowHandles())
		          {driver.switchTo().window(handle);
		          }
		   
		    Thread.sleep(3000);
		    driver.findElement(by).click(); 
		   Thread.sleep(3000);
		    // get all the window handles after the popup window appears
		   Set<String> afterPopup = driver.getWindowHandles();
		        
		   // remove all the handles from before the popup window appears
		   afterPopup.removeAll(beforePopup);

		   // there should be only one window handle left
		   if(afterPopup.size() == 1) {
		             driver.switchTo().window((String)afterPopup.toArray()[0]);
		             System.out.println("switch to active window");
		   }
	}
	
	
	
	
	public int findFramenumber(WebDriver driver, By by) {
		int i;
		int framecount = driver.findElements(By.tagName("frame")).size();

		for (i = 0; i < framecount; i++) {
			driver.switchTo().frame(i);
			int count = driver.findElements(by).size();

			if (count > 0) {
				break;
			} else {
				System.out.println("countinue looping");
			}
		}
		driver.switchTo().defaultContent();
		return i;
	}
	
	
	public void Select_Profile(String p) throws InterruptedException, IOException {
		Thread.sleep(100);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("eprmenu");
		String handle = driver.getWindowHandle();
		boolean profileChange=isElementPresent(By.id("MainMenuSessionBar")) ;
		if(profileChange){
		driver.findElement(By.id("MainMenuSessionBar")).click();
		}else{
			driver.findElement(By.id("MainMenuToggle")).click();
			driver.findElement(By.id("MainMenuSessionBar")).click();
		}
		waitForNewWindowAndSwitchToIt(driver);
		/*Screenshot sc=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		ImageIO.write(sc.getImage(), "PNG", new File("./errorScreenshots/Ashotprofile.png"));
		*/System.out.println("New window");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tSSUserOtherLogonLoc_ChangeLoc")));
		int logonTable = driver.findElements(By.xpath(".//*[@id='tSSUserOtherLogonLoc_ChangeLoc']/tbody/tr")).size();
		String profiletable = "tSSUserOtherLogonLoc_ChangeLoc";
		// String pro=p;
		this.clickonProfile(p, logonTable, profiletable);
		//
		driver.switchTo().window(handle);
		System.out.println("Profile changed");
	}
	
	public void clickonProfile(String regnumber, int rowcnt, String tblname) {
		System.out.println(regnumber);
		System.out.println(rowcnt);
		System.out.println(tblname);
		for (int j = 1; j <= rowcnt; j++) {
			String str2 = driver.findElement(By.xpath(".//*[@id='" + tblname + "']/tbody/tr[" + j + "]/td[3]"))
					.getText();
			System.out.println(str2);

			String regNum = regnumber;
			if (str2.equals(regNum)) {
				driver.findElement(By.xpath(".//*[@id='" + tblname + "']/tbody/tr[" + j + "]/td[2]")).click();
				break;
			} else
				System.out.println("Full out");
		}
	}
	
	
	
	public void patientSearchInFloorList(String reg) throws InterruptedException {
		System.out.println("Floor Search");
		driver.switchTo().defaultContent();
		driver.switchTo().frame("TRAK_main");
		Thread.sleep(1000);
		driver.findElement(By.id("FloorplanHeaderSwitcherList")).click();

		// find patient and click on ed whiteboard

		int Inwardrowcnt = driver.findElements(By.xpath(".//*[@id='tPACWard_ListPatientsInWard']/tbody/tr")).size();
		int OutofBedrowcnt = driver.findElements(By.xpath(".//*[@id='tPACWardRoom_ListPatients']/tbody/tr")).size();

		String patientinbed = "tPACWard_ListPatientsInWard";
		String patientoutbed = "tPACWardRoom_ListPatients";
		String regno = reg;// "8500062";
		this.clickonPatientRegnumber(regno, Inwardrowcnt, patientinbed);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,2500)", "");
		System.out.println("Scroll down");
		this.clickonPatientRegnumber(regno, OutofBedrowcnt, patientoutbed);
	}
	
	
	public void clickonPatientRegnumber(String regnumber, int rowcnt, String tblname) {
		System.out.println(regnumber);
		System.out.println(rowcnt);
		System.out.println(tblname);
		for (int j = 1; j <= rowcnt; j++) {
			String str2 = driver.findElement(By.xpath(".//*[@id='" + tblname + "']/tbody/tr[" + j + "]/td[4]"))
					.getText();
			System.out.println(str2);

			String regNum = regnumber;
			if (str2.equals(regNum)) {
				driver.findElement(By.xpath(".//*[@id='" + tblname + "']/tbody/tr[" + j + "]/td[5]")).click();
				break;
			} else
				System.out.println("Full out");
		}

	}

	public void find_patient(String Surname, String Gender, String DOB) throws InterruptedException, IOException {
		// load Properties File
		FileInputStream reader = new FileInputStream("./Trak_Script/PropertiesFile/BrandNewPatient.properties");
		prop.load(reader);
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("eprmenu");
		driver.switchTo().defaultContent();
		driver.switchTo().frame("TRAK_main");
		System.out.println(Surname);
		driver.findElement(By.id(prop.getProperty("Surname"))).clear();
		driver.findElement(By.id(prop.getProperty("Surname"))).click();
		driver.findElement(By.id(prop.getProperty("Surname"))).sendKeys(Surname);
		System.out.println(Gender);
		driver.findElement(By.id(prop.getProperty("Gender"))).clear();
		driver.findElement(By.id(prop.getProperty("Gender"))).sendKeys(Gender);
		System.out.println(DOB);
		driver.findElement(By.id(prop.getProperty("DOB"))).clear();
		driver.findElement(By.id(prop.getProperty("DOB"))).sendKeys(DOB);
		driver.findElement(By.id(prop.getProperty("ForeName"))).clear();
		driver.findElement(By.id(prop.getProperty("find"))).click();

	}

	public void selectWardInWardSummaryList(String regnumber, int rowcnt, String tblname) {
		driver.switchTo().defaultContent();
		driver.switchTo().frame("eprmenu");
		driver.switchTo().defaultContent();
		driver.switchTo().frame("TRAK_main");
		System.out.println(regnumber);
		System.out.println(rowcnt);
		System.out.println(tblname);
		for (int j = 1; j <= rowcnt; j++) {
			String str2 = driver.findElement(By.xpath(".//*[@id='" + tblname + "']/tbody/tr[" + j + "]/td[4]"))
					.getText();
			System.out.println(str2);
			String hos = ", GRH";
			// 9A Tower Block, GRH====9A Tower Block GRH
			String regNum = regnumber + hos;
			System.out.println("Added: " + regNum);

			if (str2.equals(regNum)) {

				System.out.println("click it");
				driver.findElement(By.id("F.Planz" + j + "")).click();
				// driver.findElement(By.xpath(".//*[@id='"+tblname+"']/tbody/tr["+j+"]/td[1]")).click();
				break;
			} else
				System.out.println("Full out");
		}
	}
	
	//Below method is used to wait for the new window to be present and switch to it.

	public  void waitForNewWindowAndSwitchToIt(WebDriver driver) throws InterruptedException {
		String cHandle = driver.getWindowHandle();
		String newWindowHandle = null;
		Set<String> allWindowHandles = driver.getWindowHandles();

		// Wait for 20 seconds for the new window and throw exception if not
		// found
		for (int i = 0; i < 20; i++) {
			if (allWindowHandles.size() > 1) {
				for (String allHandlers : allWindowHandles) {
					if (!allHandlers.equals(cHandle))
						newWindowHandle = allHandlers;
				}
				driver.switchTo().window(newWindowHandle);
				System.out.println("New window found by waitForNewWindowAndSwitchToIt method ");
				break;
			} else {
				Thread.sleep(1000);
			}
		}
		if (cHandle == newWindowHandle) {
			throw new RuntimeException("Time out - No window found");
		}
	}
		
	
	public static void captureScreenShot(WebDriver ldriver, String path) {
		File scrFile1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String fileNameSuffix1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss'.png'").format(new Date());
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		try {
			FileUtils.copyFile(scrFile1, new File(path + fileNameSuffix1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("New Screenshot path");

	}
	
	public void fullScreenShot() throws AWTException, UnsupportedFlavorException, IOException{
		
		//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        
        Robot alrt = new Robot();
        alrt.keyPress(KeyEvent.VK_PRINTSCREEN);
        alrt.keyRelease(KeyEvent.VK_PRINTSCREEN);
        
        Clipboard alrtclipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      
            //Get data from clipboard and assign it to an image.  //clipboard.getData() returns an object, so we need to cast it to a BufferdImage.
            BufferedImage alrtclipboardimage = (BufferedImage)alrtclipboard.getData(DataFlavor.imageFlavor);
            String alrtclipboardfileNameSuffix = new SimpleDateFormat("yyyyMMddHHmmss'.png'").format(new Date());

            //file that we'll save to disk.
          //  File file1 = new File("./errorScreenshots/Alert_During_LogonLocationAdding_"+userToCreate+"_@"+alrtclipboardfileNameSuffix+".jpg");
            
            //class to write image to disk.  You specify the image to be saved, its type,           // and then the file in which to write the image data.
          //  ImageIO.write(alrtclipboardimage, "jpg", file1);
//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&  

	}

	 
	
}


