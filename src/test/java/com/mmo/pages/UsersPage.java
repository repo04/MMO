package com.mmo.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mmo.util.BaseClass;
import org.testng.Assert;

public class UsersPage extends BaseClass {

    private String dateAndTime;
    private static String userEmailId;
	private String userFirstName;
	private String userSecondName;
	private static String subUserDetails;

    public void createUser(String userType, String userRole) {
    	this.userFirstName = "mmoAutomated";
		this.dateAndTime = u.currentDateTime();

		if(userType.contains("FreeUS"))
		{
			this.userSecondName = "FreeUS";
		}else if(userType.contains("FreeNonUS")){
			this.userSecondName = "FreeNonUS";
		}else if(userType.contains("5k")){
			this.userSecondName = "5k";
		}else{
			this.userSecondName = "Prof";
		}

		if(userRole.contains("SA_Admin")) {
			this.userSecondName = this.userSecondName + "SA_Admin" + this.dateAndTime;
			new Select(driver.findElement(By.xpath("//select[@id='accessLevel']"))).selectByVisibleText("Admin");
		}else if(userRole.contains("SA_User")){
			this.userSecondName = this.userSecondName + "SA_User" + this.dateAndTime;
			new Select(driver.findElement(By.xpath("//select[@id='accessLevel']"))).selectByVisibleText("User");
		}else if(userRole.contains("SAAd_Admin")){
			this.userSecondName = this.userSecondName + "SAAd_Admin" + this.dateAndTime;
			new Select(driver.findElement(By.xpath("//select[@id='accessLevel']"))).selectByVisibleText("Admin");
		}
		else{
			this.userSecondName = this.userSecondName + "SAAd_User" + this.dateAndTime;
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
    
    public void verifyUserDetailInUsersList(String userID, String userFirstName, String userSecondName, String role) {
    	ip.isElementPresentByXPATH(driver, "//tbody[@class='ui-table-tbody']");
    	WebElement getRows = driver.findElement(By.xpath("//tbody[@class='ui-table-tbody']"));
    	List<WebElement> TotalRowsList = getRows.findElements(By.tagName("tr"));
    	System.out.println("Total number of Rows in the table are : "+ TotalRowsList.size());
    	int x = 1;
    	for (; x < TotalRowsList.size()+1;) {
    		try {
				ip.isTextPresentByXPATH(driver, "//tr["+ x + "]/td[1]", userFirstName + " " + userSecondName, 5);
    			ip.isTextPresentByXPATH(driver, "//tr["+ x + "]/td[2]", userID, 5);
	    		ip.isTextPresentByXPATH(driver, "//tr["+ x + "]/td[4]", role, 5);
	    		System.out.println("found at x : "+ x);
	    		break;
	    	}
    		catch (Exception e) {
    			System.out.println("Not found at x= "+ x);
    			x++;
    		}
    	}
    	String name = driver.findElement(By.xpath("//div[@class='header-username']")).getText();

		if(name.equalsIgnoreCase(userFirstName + " " + userSecondName)){
			Assert.assertEquals(driver.findElement(By.xpath("//tr["+ x + "]/td[5]/a/i")).getAttribute("class"),"pbi-icon-mini pbi-ban", "Delete user link enabled");
		}else{
			ip.isElementClickableByXpath(driver, "//tr["+ x + "]/td[5]/a/i", 60);
		}
    }

	public void deleteUser(String userID) {
		ip.isElementPresentByXPATH(driver, "//tbody[@class='ui-table-tbody']");
		WebElement getRows1 = driver.findElement(By.xpath("//tbody[@class='ui-table-tbody']"));
		List<WebElement> TotalRowsList1 = getRows1.findElements(By.tagName("tr"));
		System.out.println("Total number of Rows in the table are : " + TotalRowsList1.size());
		int x = 1;
		for (; x < TotalRowsList1.size()+1;) {
			try {
				ip.isTextPresentByXPATH(driver, "//tr["+ x + "]/td[2]", userID, 10);
				System.out.println("found at x : "+ x);
				break;
			}
			catch (Exception e) {
				System.out.println("Not found at x= "+ x);
				x++;
			}
		}

		driver.findElement(By.xpath("//tr["+ x + "]/td[5]/a/i")).click();
		ip.isTextPresentByXPATH(driver, "//h5", "Confirm Delete User");
		ip.isTextPresentByXPATH(driver, "//p", "Do you really want to delete this user? Once done cannot be reversed.");
		ip.isElementPresentByXPATH(driver, "(//button[@type='button'])[3]");
		driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();

		ip.invisibilityOfElementByXpath(driver, "//tr["+ TotalRowsList1.size() + "]/td[1]");

		ip.isElementPresentByXPATH(driver, "//tbody[@class='ui-table-tbody']");
		WebElement getRows2 = driver.findElement(By.xpath("//tbody[@class='ui-table-tbody']"));
		List<WebElement> TotalRowsList2 = getRows2.findElements(By.tagName("tr"));
		System.out.println("Total number of Rows in the table after deletion are : " + TotalRowsList2.size());

		Assert.assertEquals(TotalRowsList2.size(), TotalRowsList1.size() - 1, "Table row is not reduced, might be user is not deleted");

		for (int y = 1; y < TotalRowsList2.size() + 1; y++) {
			Assert.assertNotEquals(driver.findElement(By.xpath("//tr["+ y + "]/td[2]")).getText(), userID);
			System.out.println("Not Found at y : "+ y);
		}
	}

    /**
	 * @return subUserDetails
	 */
	public String getSubUserDetails() {
		subUserDetails =  this.userEmailId;
		return subUserDetails;
	}
}
