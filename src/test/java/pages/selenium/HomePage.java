package pages.selenium;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import lib.selenium.PreAndPost;

public class HomePage extends PreAndPost{

	public HomePage(RemoteWebDriver driver, ExtentTest test) {	
		this.driver = driver;
		this.test = test;	
		driver.switchTo().defaultContent();
		PageFactory.initElements(driver, this);
	}		

	public HomePage searchUsingFilter(String value,String eleDesc) {	
		type(locateElement("filter"),value,eleDesc);
		return this; 
	}	
	
	public HomePage clickAll() {
		String allElementSelector = "return document.querySelector('macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot."
			    + "querySelector('sn-canvas-appshell-root').querySelector('sn-canvas-appshell-layout')."
			    + "querySelector('sn-polaris-layout').shadowRoot.querySelector('sn-polaris-header').shadowRoot."
			    + "querySelector(\"div.sn-polaris-tab[aria-label='All']\")";
		clickElementInsideShadowRoot(allElementSelector,"All Link");
		return this;
	}
	
	public HomePage clickIncident() {
		String incidentElementSelector = "return document.querySelector('macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot\r\n"
			    + "    .querySelector('sn-canvas-appshell-root')\r\n"
			    + "    .querySelector('sn-canvas-appshell-layout')\r\n"
			    + "    .querySelector('sn-polaris-layout')\r\n"
			    + "    .shadowRoot.querySelector('sn-polaris-header').shadowRoot\r\n"
			    + "    .querySelector('sn-polaris-menu').shadowRoot\r\n"
			    + "    .querySelector('sn-collapsible-list').shadowRoot\r\n"
			    + "    .querySelector('a[data-id=\"087800c1c0a80164004e32c8a64a97c9\"]');";
		
		
		clickElementInsideShadowRoot(incidentElementSelector,"Incident menu option");
		return this;
	}
	
	@FindBy(xpath="//*[@id='incident_breadcrumb']//a[contains(@aria-label,'All Press enter to remove all subsequent conditions.')]") 
	private WebElement eleBreadCrumbAll;
	public ListIncidents clickBreadCrumb_All(String eleDesc) {
		String newButtonFrameElement = "return document.querySelector('macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot.querySelector(\"iframe[id='gsft_main']\")";
		swicthToFrameInsideShadowRoot(newButtonFrameElement,"All link");
		
		click(eleBreadCrumbAll,eleDesc);
		return new ListIncidents(driver,test);
		
	}
	

	
	@FindBy(linkText="Create New")
	WebElement eleCreateNew;	
	public CreateNewIncident clickCreateNew(String eleDesc) {		
		click(eleCreateNew,eleDesc);
		return new CreateNewIncident(driver, test); 
	}	
	
	@FindBy(xpath="//button[@id='sysverb_new']")
	WebElement eleNew;	
	public CreateNewIncident clickNew(String eleDesc) {	
		String newButtonFrameElement = "return document.querySelector('macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot.querySelector(\"iframe[id='gsft_main']\")";
		swicthToFrameInsideShadowRoot(newButtonFrameElement,"New button");
		click(eleNew,eleDesc);
		return new CreateNewIncident(driver, test); 
	}	
	
	@FindBy(linkText="Open")
	WebElement eleOpen;	
	public ListIncidents clickOpen(String eleDesc) {		
		click(eleOpen,eleDesc);
		return new ListIncidents(driver, test); 
	}	
	
}


