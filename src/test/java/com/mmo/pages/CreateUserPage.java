package com.mmo.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mmo.util.BaseClass;

public class CreateUserPage extends BaseClass {

    private String dateAndTime;
    private static String userEmailId;
	private String userFirstName;
	private String userSecondName;
	

    public void createUser(String userRole) {
    	this.dateAndTime = u.currentDateTime();
    	ip.isElementClickableByXpath(driver, "//button[@id='createUserBtn']", 60);
    	this.userFirstName = "autoMMOPb";
		
    	switch (userRole) {
    		case "Admin":
    			this.userSecondName = "admin" + this.dateAndTime;
    			new Select(driver.findElement(By.xpath("//select[@id='accessLevel']"))).selectByVisibleText("Admin");
    		default:
    			this.userSecondName = "user" + this.dateAndTime;
    			new Select(driver.findElement(By.xpath("//select[@id='accessLevel']"))).selectByVisibleText("User");    			
    	}
    	userEmailId = this.userFirstName + "+" + this.userSecondName + "@gmail.com";
    	driver.findElement(By.xpath("//input[@id='firstName']")).clear();
		driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys(this.userFirstName);
		driver.findElement(By.xpath("//input[@id='lastName']")).clear();
		driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys(this.userSecondName);
		driver.findElement(By.xpath("//input[@id='emailId']")).clear();
		driver.findElement(By.xpath("//input[@id='emailId']")).sendKeys(userEmailId);
		driver.findElement(By.xpath("//button[@id='createUserBtn']")).click();
		ip.isTextPresentByXPATH(driver, "//div[@id='toast-container']/div/div", "User has been successfully created.");
	}
    
    public void verifyUserCreated() {
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ip.isElementClickableByXpath(driver, "//a[contains(text(),'Users')]", 60);
    	driver.findElement(By.xpath("//a[contains(text(),'Users')]")).click();
    	ip.isElementPresentByXPATH(driver, "//div[@class='ui-table-wrapper']/table/tbody");
    	WebElement TogetRows = driver.findElement(By.xpath("//div[@class='ui-table-wrapper']/table/tbody"));
    	List<WebElement> TotalRowsList = TogetRows.findElements(By.tagName("tr"));
    	System.out.println("Total number of Rows in the table are : "+ TotalRowsList.size());
    	for (int x = 1; x < TotalRowsList.size()+1;) {
    		try {
        		ip.isTextPresentByXPATH(driver, "//tr["+ x + "]/td[2]", "autommob+usr17jun@gmail.com", 10);
	    		ip.isTextPresentByXPATH(driver, "//tr["+ x + "]/td[4]", "User", 10);
	    		System.out.println("found at x : "+ x);
	    		break;
	    	}
    		catch (Exception e) {
    			System.out.println("Not found at x= "+ x);
    			x++;
    		}
    	}
    	System.out.println("FINISHED");
    }
    
    /**
     * @return userName
     */
    public static String getUser() {
        return userEmailId;
    }	
}
