package pages.selenium;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import lib.selenium.PreAndPost;

public class CreateNewIncident extends PreAndPost{
	
	public CreateNewIncident(RemoteWebDriver driver, ExtentTest test) {	
		this.driver = driver;
		this.test = test;
		/*driver.switchTo().defaultContent();
		String newButtonFrameElement = "return document.querySelector('macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot.querySelector(\"iframe[id='gsft_main']\")";
		swicthToFrameInsideShadowRoot(newButtonFrameElement);
		driver.switchTo().frame("gsft_main");*/
		PageFactory.initElements(driver,this);
	}
	 
	@FindBy(id="incident.number") 
	private WebElement eleIncidentNumber;	
	
	@FindBy(id="sys_display.incident.caller_id")
	private WebElement eleCallerId;	
	
	@FindBy(how=How.ID,using="incident.short_description")
	private WebElement eleShortDescription;
	
	public CreateNewIncident getIncidentNumber() {	
		pause(3000);
		incidentNumber = getAttribute(eleIncidentNumber,"value");
		return this;
	}	

	public CreateNewIncident selectUser(String data,String eleDesc) {
		typeAndChoose(eleCallerId, data,eleDesc);
		return this;
	}	
	
	public CreateNewIncident typeShortDescription(String data,String eleDesc) {
		type(eleShortDescription, data,eleDesc);
		return this;		
	}
	
	public CreateNewIncident clickSubmit(String eleDesc) {
		WebElement eleSubmit = locateElement("sysverb_insert");
		click(eleSubmit,eleDesc);
		return this;		
	}
	
	
	//a[@aria-label="Open record: INC0010036"]
	
	public CreateNewIncident verifyIncidentCreation() {
		WebElement ele = locateElement("id","incident_breadcrumb");
		verifyDisplayed(ele,"Incident_breadcrumb");
		return this;		
	}
}
