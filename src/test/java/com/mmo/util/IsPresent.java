/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmo.util;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;


public class IsPresent {

    /**
     * Driver checks if the given TEXT is present in the specified element by
     * CSS
     *
     * @param driver
     * @param css
     * @param text
     */
    public void isTextPresentByCSS(WebDriver driver, String css, String text) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(css), text));        
    }

    /**
     * Driver checks if the given TEXT is present in the specified element by
     * PATH
     *
     * @param driver
     * @param path
     * @param text
     */
    public void isTextPresentByXPATH(WebDriver driver, String path, String text) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(path), text));
    }

    /**
     * Driver checks if the given TEXT is present in the specified element by
     * XPATH till allocated time
     *
     * @param driver
     * @param path
     * @param text
     * @param wait
     */
    public void isTextPresentByXPATH(WebDriver driver, String path, String text, int wait) {
        new WebDriverWait(driver, wait).until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(path), text));
    }

    /**
     * Driver checks if the given TEXT is present in the specified element by
     * ID till allocated time
     *
     * @param driver
     * @param id
     * @param text
     * @param wait
     */
    public void isTextPresentByID(WebDriver driver, String id, String text, int wait) {
        new WebDriverWait(driver, wait).until(ExpectedConditions.textToBePresentInElementLocated(By.id(id), text));
    }

    
    /**
     * Driver checks if an element containing Text is present by XPATH on the
     * DOM of a page
     *
     * @param driver
     * @param elementText
     */
    public void isElementPresentContainsTextByXPATH(WebDriver driver, String elementText) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'" + elementText + "')]")));
    }

    /**
     * Driver checks if an element starts with Text is present by XPATH on the
     * DOM of a page
     *
     * @param driver
     * @param elementText
     */
    public void isElementPresentStartsWithTextByXPATH(WebDriver driver, String elementText) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[starts-with(text(),'" + elementText + "')]")));
    }

    /**
     * Driver checks if an element is present by LINK on the DOM of a page
     *
     * @param driver
     * @param elementByLINK
     */
    public void isElementPresentByLINK(WebDriver driver, String elementByLINK) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.linkText(elementByLINK)));
    }

    /**
     * Driver checks if an element is present by ID on the DOM of a page
     *
     * @param driver
     * @param elementByID
     */
    public void isElementPresentByID(WebDriver driver, String elementByID) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.id(elementByID)));
    }

    /**
     * Driver checks if an element is present by XPATH on the DOM of a page
     *
     * @param driver
     * @param elementByXPATH
     */
    public void isElementPresentByXPATH(WebDriver driver, String elementByXPATH) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementByXPATH)));
    }
    
    /**
     * Driver checks if an element is present by CSS on the DOM of a page
     *
     * @param driver
     * @param elementByXPATH
     */
    public void isElementPresentByCSS(WebDriver driver, String elementByXPATH) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(elementByXPATH)));
    }

    /**
     * Driver checks if the current title matches with isTitle
     *
     * @param driver
     * @param isTitle
     */
    public void isTitlePresent(WebDriver driver, String isTitle) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.titleIs(isTitle));        
    }

    /**
     * Driver checks if the current title contains isTitle
     *
     * @param driver
     * @param isTitle
     */
    public void isTitleContains(WebDriver driver, String isTitle) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.titleContains(isTitle));
    }

    /**
     * Driver finds an element by XPATH if its visible and enabled such that can
     * be clicked
     *
     * @param driver
     * @param elementClickable
     */
    public void isElementClickableByXpath(WebDriver driver, String elementClickable, int time) {
        new WebDriverWait(driver, time).until(ExpectedConditions.elementToBeClickable(By.xpath(elementClickable)));
    }
    
     /**
     * An expectation for checking that an element with text is either invisible or not present on the DOM
     * 
     * @param driver
     * @param elementXpath 
     */
    public void invisibilityOfElementByXpathWithText(WebDriver driver, String elementXpath, String text) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.invisibilityOfElementWithText(By.xpath(elementXpath), text));
    }
    
    /**
     * An expectation for checking that an element is either invisible or not present on the DOM
     * 
     * @param driver
     * @param elementXpath
     */
    public void invisibilityOfElementByXpath(WebDriver driver, String elementXpath) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(elementXpath)));
    }

    /**
     *
     * @param driver
     * @param elementXpath
     */
    public void invisibilityOfElementByCSS(WebDriver driver, String elementXpath) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(elementXpath)));
    }
    
    //**NEW**
    /**
     * Driver checks if the current url contains isURL
     *
     * @param driver
     * @param isURL
     */
    public void isURLContains(WebDriver driver, String isURL) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.urlContains(isURL));
    }
    
    /**
     * Driver finds an element by CSS if its visible and enabled such that can
     * be clicked
     *
     * @param driver
     * @param elementClickable
     */
    public void isElementClickableByCSS(WebDriver driver, String elementClickable, int time) {
        new WebDriverWait(driver, time).until(ExpectedConditions.elementToBeClickable(By.cssSelector(elementClickable)));
    }
    
    /**
     * 
     * @param driver
     * @param elementClickable
     */
    public void ignoreStaleElementExceptionClickByXpath(WebDriver driver, String elementClickable) {
    	new WebDriverWait(driver, 60).ignoring(StaleElementReferenceException.class).
		until((WebDriver d) -> {
            d.findElement(By.xpath(elementClickable)).click();
            return true;
        });
    }
    
    /**
     * Wait until an element is no longer attached to the DOM.
     *
     * @param driver
     * @param element
     */
    public void checkStalenessOfElement(WebDriver driver, WebElement element) {
    	new WebDriverWait(driver, 60).until(ExpectedConditions.stalenessOf(element));
    }

    /**
     *
     * @param driver
     * @param elementXpath
     * @return
     */
    public WebElement findElementByXpath(WebDriver driver, String elementXpath) {
        return new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementXpath)));
    }

    /**
     * An expectation for checking whether the given frame is available to switch to. If the frame
     * is available it switches the given driver to the specified frame
     *
     * @param driver
     * @param frameID
     */
    public void frameToBeAvailableAndSwitchToIt(WebDriver driver, String frameID) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameID));
    }
}
