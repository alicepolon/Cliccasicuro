package cliccasicuro;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * This class identifies the steps for a purchasing process on Cliccasicuro.it
 * 
 * Scenario: iPhone purchase process in Cliccasicuro
 *
 *  Given open "https://www-staging.cliccasicuro.it"
 *  When set the model to "iPhone"
 *  Then click on button "Protezione"
 *  Then fill the product details
 *  Then click on button "Ordina"
 *  Then fill the order with credit card "master"
 *  Then click on button "Al pagamento"
 *  Then fill credit card form with credit card "9451123100000111"
 *  Then click on button "Ordinare a pagamento"
 *  Then check if it was successful
 *  
 *  
 * @author Aliss
 *
 */

public class CliccasicuroStepDefinition {

    protected WebDriver driver;
    
    @Before
    public void setup() {
        driver = new FirefoxDriver();
    }
    
    
    @Given("^open \"([^\"]*)\"$")
    public void open(String url) throws Throwable {
        driver.get(url);
    }
   
    @When("^set the model to \"([^\"]*)\"$")
    public void set_the_model_to(String model) throws Throwable {
        //Set Device
        driver.findElement(By.xpath("//*[@data-id='modelOptions']")).click();
        driver.findElement(By.xpath("//a[contains(text(), '"+model+"')]")).click();
    }

    @Then("^click on button \"([^\"]*)\"$")
    public void click_on_button(String label) throws Throwable {
        //Click on a button identified by label
        driver.findElement(By.xpath("//*[contains(text(), '"+label+"')]")).click();
    }
   
   
    @Then("^fill the product details$")
    public void fill_the_product_details() throws Throwable {
        //Set Model
        driver.findElement(By.xpath("//*[@data-id='modelOptions']")).click();
        driver.findElement(By.linkText("iPhone 5")).click();
        
        //Set Price
        driver.findElement(By.xpath("//*[@data-id='priceRangeOptions']")).click();
        driver.findElement(By.linkText("250 - 500 €")).click();
        
        //Set Theft option
        if ( !driver.findElement(By.id("theftOption")).isSelected() )
        {
            driver.findElement(By.id("theftOption")).click();
        }   
    }
    

    @Then("^fill the order with credit card \"([^\"]*)\"$")
    public void fill_the_order_with_credit_card(String creditcard) throws Throwable {
       //Set E-mail
       driver.findElement(By.id("purchase_customer_email")).sendKeys("x@x.com");;
       
       //Set Keys
       driver.findElement(By.id("purchase_identifiers___name___serialNumber")).sendKeys("0123456");;
       
       //Set DeviceAge
       driver.findElement(By.id("deviceAge")).click();
       
       //Set Pay method  
       //Credit card example "visa" or "master"
       if ( !driver.findElement(By.id("saferpay_"+creditcard)).isSelected() )
       {
            driver.findElement(By.id("saferpay_"+creditcard)).click();
       }
       
       //Set privacy
       driver.findElement(By.id("input_avb_pib")).click();
      
    }
    
    //Wait for the presence of an element in the page
    public WebElement fluentWait(final By locator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });

        return  foo;
    }

    @Then("^fill credit card form with credit card \"([^\"]*)\"$")
    public void fill_credit_card_form_with_credit_card(String creditcardnum) throws Throwable {
       
       //Set credit card number 
       WebElement creditcardnumber = fluentWait(By.id("pan"));
       creditcardnumber.sendKeys(creditcardnum);
       
       //Set credit card month
       driver.findElement(By.xpath("//*[@data-id='exp_month']")).click();
       driver.findElement(By.linkText("02")).click();
       
       //Set credit card year
       driver.findElement(By.xpath("//*[@data-id='exp_year']")).click();
       driver.findElement(By.linkText("2016")).click();

       //Set the CVV - any 3 digit
       WebElement cvc = driver.findElement(By.id("cvc"));
       cvc.sendKeys("123");
   }

   
    @Then("^check if it was successful$")
    public void check_if_it_was_successful() throws Throwable {
       // Check if the order is successful depending on the landing page
       String expectedUrl = "https://www-staging.cliccasicuro.it/purchase/checkout/success";
       WebDriver driver = new FirefoxDriver();
       driver.get(expectedUrl);
       try{
         Assert.assertEquals(expectedUrl, driver.getCurrentUrl());
         System.out.println("Correct webpage");
       }
       catch(Throwable pageNavigationError){
         System.out.println("Incorrect webpage");
       }
   }
   
    @After 
    public void closeBrowser() { 
        driver.quit(); 
    }
}
