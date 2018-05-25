package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	 static WebDriver driver;

	public LoginPage(WebDriver driver) {
		
		LoginPage.driver = driver;
	//	PageFactory.initElements(driver, this);
	}
	

	@FindBy(how=How.XPATH,using="//a[@class='_2k0gmP']")
	WebElement login;
	
	@FindBy(how=How.XPATH,using="//input[@class='_2zrpKA']") WebElement username;
	
	@FindBy(how=How.XPATH, using ="//input[@class='_2zrpKA _3v41xv']")
	WebElement password;
	
	@FindBy(how=How.XPATH,using="//button[@class='_2AkmmA _1LctnI _7UHT_c']")
	WebElement loginBtn;
	
	
	public HomePage clickLogin() {
		loginBtn.click();
		
		return new HomePage(driver);
		
	}
	public HomePage loginIntoFlipkart(String struser,String strpwd) {
		
	//	login.click();
		username.sendKeys(struser);
		password.sendKeys(strpwd);
		return clickLogin();
		
	}
}
