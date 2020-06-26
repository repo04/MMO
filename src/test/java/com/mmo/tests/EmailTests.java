package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.mmo.util.EmailUtils;

import javax.mail.Message;

public class EmailTests extends BaseClass{

    private static EmailUtils emailUtils;
    Actions a = new Actions();

    @BeforeClass
    public static void connectToEmail() {
        try {
            emailUtils = new EmailUtils("autommopb@gmail.com", "Pitney@123",
                        "smtp.gmail.com", EmailUtils.EmailFolder.STARTUSINGMMO);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    //@Test
    public void testVerificationCode() {
        try {
            //TODO: Execute actions to send verification code to email

            String verificationCode = emailUtils.getAuthorizationCode();

            //TODO: Enter verification code on screen and submit

            //TODO: add assertions

        } catch (Exception e) {
            e.printStackTrace();
            //Assert.fail(e.getMessage());
        }
    }

    //@Test
    public void testTextContained() {
        try{
            Message email = emailUtils.getMessagesBySubject("Loan Approval", true, 5)[0];
            //Assert.assertTrue("Approval message is not in email", emailUtils.isTextInMessage(email, "You have been approved"));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    //@Test
    public void testLink() {

        //TODO: apply for a loan using criteria that will result in the application being rejected

        try{
            Message email = emailUtils.getMessagesBySubject("Welcome! Get started with Precisely MapMarker",
                    false, 1)[0];
            String link = emailUtils.getUrlsFromMessage(email, "Complete Regi=stration Now").get(0);

            //BaseClass.driver.get(link);

            //TODO: continue testing
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }


    @Test
    public void testToken() {
        try {
            //TODO: Execute actions to send verification code to email
            String emailSubject = "You're ready to start using MapMarker";
            String userID = "autoMMOPb+5k260620180343@gmail.com";
            //String userID = "autoMMOPb+FreeUS260620172154@gmail.com";
            String Token = emailUtils.getToken(emailSubject, userID, EmailUtils.EmailFolder.STARTUSINGMMO);
            System.out.println("Token: " + Token);
            driver.get("https://login-qa.saas.precisely.services/claim-account?productId=geocoding&token=" + Token);

            //TODO: Enter verification code on screen and submit

            //TODO: add assertions

        } catch (Exception e) {
            e.printStackTrace();
            //Assert.fail(e.getMessage());
        }
    }

}
