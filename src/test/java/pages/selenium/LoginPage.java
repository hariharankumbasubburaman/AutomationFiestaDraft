package pages.selenium;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lib.selenium.PreAndPost;
import lib.utils.ConfigUtil;

public class LoginPage extends PreAndPost{
	private Properties prop;
	
	public LoginPage(RemoteWebDriver driver, ExtentTest test) {	
		this.driver = driver;
		this.test = test;
		//driver.switchTo().frame(0);
		PageFactory.initElements(driver,this);
		
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/test/resources/config.properties")));
		} catch (Exception e) {
			reportStep("Missing the configuration file", "FAIL");
		}
			
	}
	 
	@FindBy(id="user_name") 
	private WebElement eleUserName;	
	
	@FindBy(id="user_password")
	private WebElement elePassword;	
	
	@FindBy(how=How.ID,using="sysverb_login")
	private WebElement eleLogin;

	
	@Given("Enter username as (.*)$")
	public LoginPage typeUserName(String username,String eleDesc) {	
		type(eleUserName, username,eleDesc);
		return this;
	}	

	@And("Enter password as (.*)$")
	public LoginPage typePassword(String password,String eleDesc) {
		type(elePassword, decryptPassword(password),eleDesc);
		return this;
	}	
	
	@Then("Click the Login")
	public HomePage clickLogIn(String eleDesc) {
		click(eleLogin,eleDesc);
		return new HomePage(driver,test);		
	}
	
	public HomePage loginApp(String eleDesc) {
		
		String username = ConfigUtil.getProperty("username");
		String password = ConfigUtil.getProperty("password");
		return 
		typeUserName(username,eleDesc)
		.typePassword(password,eleDesc)
		.clickLogIn(eleDesc);
	}
	
}
