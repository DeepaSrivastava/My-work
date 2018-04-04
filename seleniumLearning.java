package Example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import trakDataSetUpLibrary.BrowUrlLoginSection;
import trakDataSetUpLibrary.DataDrivenUsingXlsx;

public class seleniumLearning extends BrowUrlLoginSection {
	
	public void checkboxValidation()
	{
		boolean log=driver.findElement(By.id("")).isSelected();  
		if(log) //return true if checked/ticked
		{
			driver.findElement(By.id("")).click(); //click to uncheck
		}
		
	}

	public void ieBrowser()
	{
		System.setProperty("webdriver.ie.driver","S:\\Testing\\Test Automation\\Selenium_inst\\Selenium WebDrivers\\IEDriverServer.exe");
		  // Set capability of IE driver to Ignore all zones browser protected mode settings.
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		driver = new InternetExplorerDriver(caps);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("URLname");
	}
	
	
	
	public void dragAndDrop()
	{
		Actions a=new Actions(driver);
		WebElement source=driver.findElement(By.id(""));
		WebElement target=driver.findElement(By.id(""));
		a.dragAndDrop(source,target ).build().perform();
	}
	
	public void frame()
	{
		System.out.println(driver.findElements(By.tagName("frame")).size());
		System.out.println(driver.findElements(By.tagName("iframe")).size());
		//frame switching
		Set<String> ids=driver.getWindowHandles();
		Iterator<String> it=ids.iterator();
		it.next();
		String popUp1=it.next();
		driver.switchTo().window(popUp1);
		
		
	}
	public int findFramenumber(WebDriver driver , By by)
	{
		int i;
		int framecount=driver.findElements(By.tagName("frame")).size();
		
		for (i=0;i<framecount;i++)
		{
			driver.switchTo().frame(i);
			int count=driver.findElements(by).size();
			
			if(count>0)
			{
				break;
			}
			else
			{
				System.out.println("countinue looping");
			}
		}
		driver.switchTo().defaultContent();
		return i;
	}
	public void mouseAction()
	{
		//mouse hover
		Actions a=new Actions(driver);
		 a.moveToElement(driver.findElement(By.xpath(""))).build().perform();
		 //text in caps
		 a.moveToElement(driver.findElement(By.xpath(""))).click().keyDown(Keys.SHIFT).sendKeys("hello").build().perform();
		 //mouse double click
		 a.moveToElement(driver.findElement(By.xpath(""))).click().keyDown(Keys.SHIFT).sendKeys("hello").doubleClick().build().perform();
		 //right click on element
		 a.moveToElement(driver.findElement(By.xpath(""))).contextClick().build().perform();
	}
	
	public List<Object> getExcelData(String excelPath,String SheetName) throws IOException
	{
		
		List cellDataList = new ArrayList();
		FileInputStream file = new FileInputStream(new File(excelPath));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheet(SheetName);
		Iterator rows = sheet.rowIterator();
		sheet.getLastRowNum();
		int lineNumber = 0;
		while (rows.hasNext())

		                        {
		                            XSSFRow row = ((XSSFRow) rows.next());
		                            lineNumber++;
		                            if(lineNumber==1){continue;}
		                            
		                            Iterator cells = row.cellIterator();
		                            List cellTempList = new ArrayList();    
		                            int current = 0, next =1;
		                            
		                            while(cells.hasNext())

		                            {

		                                XSSFCell cell = (XSSFCell) cells.next();


		                                current = cell.getColumnIndex();


		                                if(current<next){

		                                }
		                                else{

		                                    int loop = current-next;

		                                    for(int k=0;k<loop+1;k++){

		                                        cellTempList.add(null);
		                                        next = next + 1;
		                                    }
		                                }
		                                switch (cell.getCellType()) {
		                                            case Cell.CELL_TYPE_STRING:
		                                                System.out.println("Hello CELL_TYPE_STRING: "+cell.getRichStringCellValue().getString());
		                                                cellTempList.add(cell.getRichStringCellValue().getString());
		                                                break;
		                                            case Cell.CELL_TYPE_NUMERIC:                                                    
		                                                    System.out.println("Hello CELL_TYPE_NUMERIC: "+cell.getNumericCellValue());
		                                                    cellTempList.add(String.valueOf(cell.getNumericCellValue()));                                                   
		                                                break;
		                                            case Cell.CELL_TYPE_BOOLEAN:
		                                                System.out.println("Hello CELL_TYPE_BOOLEAN: "+cell.getBooleanCellValue());
		                                                break;
		                                            case Cell.CELL_TYPE_FORMULA:
		                                                System.out.println("Hello CELL_TYPE_FORMULA: "+cell.getCellFormula());
		                                                cellTempList.add(cell.getCellFormula());
		                                                break;                                              

		                                            default:
		                                                System.out.println("Inside default");
		                                }
		                                next = next + 1;

		                            }
		                            cellDataList.add(cellTempList); 
		                         }
		
		return cellDataList;
		
		
	}
	
	@Test
	public void checkBlankValue() throws IOException
	{
		//driver.findElement(By.xpath("//label[text()='ENXX Admin Emergency']")).click();
		driver.switchTo().defaultContent();
		  driver.switchTo().frame("eprmenu");
		 //click on Registration tab
		  driver.findElement(By.id("MainMenuItemAnchor50312")).click();	
		  driver.switchTo().defaultContent();
			driver.switchTo().frame("eprmenu");
			driver.switchTo().defaultContent();
			driver.switchTo().frame("TRAK_main");
		new seleniumLearning();
		DataDrivenUsingXlsx ddxl= new DataDrivenUsingXlsx();
		List<Object> outerAry=ddxl.getExcelData("./DataFile/Resource_Schedule.xlsx","Sheet2");
			//List<Object> outerAry=s.getExcelData("./DataFile/Resource_Schedule.xlsx","Sheet2");
			outerAry.remove(0);
			System.out.println(outerAry);
			for(Object obj : outerAry){
			String str= obj.toString();
			str=str.replace("[", "");
			str=str.replace("]", "").trim();
			String []childAry= str.split(",");
			System.out.println(outerAry);
			driver.findElement(By.id("RegistrationNo")).click();
			driver.findElement(By.id("RegistrationNo")).clear();
			driver.findElement(By.id("RegistrationNo")).sendKeys(childAry[0].toString().trim());
			driver.findElement(By.id("PAPERName")).click();
			driver.findElement(By.id("PAPERName")).clear();
			driver.findElement(By.id("PAPERName")).sendKeys(childAry[1].toString().trim());
			driver.findElement(By.id("PAPERName2")).click();
			driver.findElement(By.id("PAPERName2")).clear();
			driver.findElement(By.id("PAPERName2")).sendKeys(childAry[2].toString().trim());
			driver.findElement(By.id("DateOfBirth")).click();
			driver.findElement(By.id("DateOfBirth")).clear();
			driver.findElement(By.id("DateOfBirth")).sendKeys(childAry[3].toString().trim());
			System.out.println("Done");
			driver.findElement(By.id("")).click();
			driver.findElement(By.id("")).clear();
			driver.findElement(By.id("")).sendKeys(childAry[4].toString().trim());
			driver.findElement(By.id("")).click();
			driver.findElement(By.id("")).clear();
			driver.findElement(By.id("")).sendKeys(childAry[5].toString().trim());
			
			}
	}
	//Below method is used to get the main window handle. we will the driver as parameter.

	public static String getMainWindowHandle(WebDriver driver) {
		return driver.getWindowHandle();
	}
//Below method is used to get the current window title

	public static String getCurrentWindowTitle() {
		String windowTitle = driver.getTitle();
		return windowTitle;
	}
//Below method is used to close all the other windows except the main window.

	public static boolean closeAllOtherWindows(WebDriver driver, String openWindowHandle) {
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String currentWindowHandle : allWindowHandles) {
			if (!currentWindowHandle.equals(openWindowHandle)) {
				driver.switchTo().window(currentWindowHandle);
				driver.close();
			}
		}
		
		driver.switchTo().window(openWindowHandle);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}
	
//Below method is used to wait for the new window to be present and switch to it.

	 public static void waitForNewWindowAndSwitchToIt(WebDriver driver) throws InterruptedException {
	        String cHandle = driver.getWindowHandle();
	        String newWindowHandle = null;
	        Set<String> allWindowHandles = driver.getWindowHandles();
	        
	        //Wait for 20 seconds for the new window and throw exception if not found
	        for (int i = 0; i < 20; i++) {
	            if (allWindowHandles.size() > 1) {
	                for (String allHandlers : allWindowHandles) {
	                    if (!allHandlers.equals(cHandle))
	                    	newWindowHandle = allHandlers;
	                }
	                driver.switchTo().window(newWindowHandle);
	                break;
	            } else {
	                Thread.sleep(1000);
	            }
	        }
	        if (cHandle == newWindowHandle) {
	            throw new RuntimeException(
	                    "Time out - No window found");
	        }
	    }

	 //Below method is used to invoke browser in node system
	 public  void crossbrowser() throws MalformedURLException {
 		
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

