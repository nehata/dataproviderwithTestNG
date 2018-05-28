package appScenarios;

// program to demonstrate use of Data Provider Annotation in TestNG.


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import excelReader.ExcelUtility;
import pageObjects.HomePage;
import pageObjects.LoginPOM;


public class DataProviderDemoTest {
	 WebDriver driver;
	 WebElement element;
	 WebDriverWait wait;
	 LoginPOM login = new LoginPOM();
	 public static String excelFilePath = "C:\\Users\\neha.tanna\\eclipse-workspace\\DataProviderWithTestNG\\TestData.xlsx";
	 public static String sheetName = "FlipkartLogin";
//	LoginPage login = new LoginPage(driver);
	
	@BeforeMethod
	public void setup() {
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://ntvmprvrpt0479/SSFC/Account/Login#/home");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
  @Test(dataProvider="performLogin")
  public void testSupersportLogin(String username,String password) {
	  try {
		  
		  driver.findElement(By.xpath(login.userName_XPath)).sendKeys(username);
		  driver.findElement(By.xpath(login.passWord_XPath)).sendKeys(password);
		  driver.findElement(By.xpath(login.loginButton_XPath)).click();
		  wait = new WebDriverWait(driver, 20);
		  wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(login.Menu_Profiles_css)));
		  element = driver.findElement(By.cssSelector(login.Menu_Profiles_css));
		  if (element.isDisplayed()) {
			  
			  System.out.println("User has logged in");
			
		}else {
			
			System.out.println("Login failed");
		}
		  
		  driver.findElement(By.cssSelector(login.logoutdropdown_css)).click();
		  driver.findElement(By.cssSelector(login.Logout_css)).click();
		  
	  }catch(AssertionError | Exception e) {
		  e.printStackTrace();
		  Assert.fail();
	  }
  }
  
  
  @DataProvider(name="performLogin")
  public Object[][] loginData(){
	  
	  Object[][] arrayObject = getExcelData(excelFilePath, sheetName);
	  
	  return arrayObject;
	  
	  
  }
  
  // generic method to get data from excel sheet
//Rows - Number of times your test has to be repeated.
//Columns - Number of parameters in test data.
  public Object[][] getExcelData(String excelFilePath, String sheetName){
	  Object[][] excelData = null;
	  ExcelUtility excelUtility = new ExcelUtility(excelFilePath);
	  int rows = excelUtility.getRowCount(sheetName);
	  System.out.println(rows);
	  int columns = excelUtility.getColumnCount(sheetName);
	  System.out.println(columns);
	  excelData = new Object[rows - 1][columns];

		for (int i = 2; i <= rows; i++) {
			for (int j = 0; j < columns; j++) {
				excelData[i - 2][j] = excelUtility.getCellData(sheetName, j, i);

			}
		}

		return excelData; 
  }
  
  @AfterMethod
  public void teardown() {
	  
	  driver.quit();
  }
}
