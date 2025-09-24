package com.bs;


import com.bs.utils.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BSTest2 {
    WebDriver driver;

    @AfterTest
    public void killSession(){
        driver.quit();
    }

    @BeforeTest
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
//        capabilities.setCapability("os", Platform.WIN11);
//        capabilities.setCapability("os_version","11");
        // Browser + version + os
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "140"); // example

        // bstack:options map
        HashMap<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("userName", "sivakolli_aIpA4i");
        bstackOptions.put("accessKey", "8MZSbx6Y8RsspGzMTRsr");
        // IMPORTANT: set geo location (ISO 2 country code). e.g. "FR", "IN", "US"
//        bstackOptions.put("geoLocation", "IN");
//        bstackOptions.put("gpsLocation", "19.0759,72.8773");
        bstackOptions.put("geoLocation", "US");
        //bstackOptions.put("gpsLocation", "19.0759,72.8773");
        // e.g. other options
        bstackOptions.put("projectName", "Hackathon Project");
        bstackOptions.put("buildName", "Testathon Test");
        bstackOptions.put("sessionName", "BS Test");
        bstackOptions.put("os", "Windows");
        bstackOptions.put("osVersion", "11");
        capabilities.setCapability("bstack:options", bstackOptions);


        ChromeOptions chromeOptions = new ChromeOptions();

// Auto-allow geolocation in Chrome (profile prefs)
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 1); // 1 = allow
        chromeOptions.setExperimentalOption("prefs", prefs);

        chromeOptions.merge(capabilities);
        // Create remote driver (replace hub URL if using EU hub)
        //URL hub = new URL("https://hub.browserstack.com/wd/hub");
       // driver = new RemoteWebDriver(hub, chromeOptions);
        driver = new RemoteWebDriver(new URL("https://sivakolli_aIpA4i:8MZSbx6Y8RsspGzMTRsr@hub.browserstack.com/wd/hub"), capabilities);


        driver.manage().window().maximize();
        driver.get("https://testathon.live/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void type(By by, String data){
        driver.findElement(by).sendKeys(data);
    }

    public void clickOnElement(By by){
        driver.findElement(by).click();
    }

    @Test
    public void login() throws InterruptedException {
        //Login
        clickOnElement(By.cssSelector("a[id='Sign In']"));
        clickOnElement(By.xpath("//div[text()='Select Username']"));
        clickOnElement(By.xpath("//div[text()='demouser']"));
        clickOnElement(By.xpath("//div[text()='Select Password']"));
        clickOnElement(By.xpath("//div[text()='testingisfun99']"));
        clickOnElement(By.cssSelector("#login-btn"));


        //filter
        clickOnElement(By.xpath("//span[text()='Apple']"));
        Thread.sleep(3000);

        List<WebElement> products = driver.findElements(By.cssSelector("p[class*='shelf-item']"));
        List<String> productsNames = new ArrayList<>();
        for (int i=0; i<products.size(); i++){
            productsNames.add(products.get(i).getText());
        }

        System.out.println(productsNames);

        boolean allContain = productsNames.stream()
                .allMatch(item -> item.toLowerCase().contains("iphone"));

        System.out.println(allContain); // true

        //offers by enabling geo location

//        clickOnElement(By.cssSelector("#offers"));
//        List<WebElement> offers = driver.findElements(By.cssSelector(".offer-title"));
//        Assert.assertTrue(offers.size()>1);


        // logo navigation
        clickOnElement(By.cssSelector("a[class*='Navbar_logo']"));
        String firstProduct = productsNames.get(0);
        System.out.println(firstProduct);

        clickOnElement(By.xpath("//p[text()='"+firstProduct+"']/following-sibling::div[contains(@class,'btn')]"));
        Thread.sleep(3000);
        //Verify bag items
        String numberOfItems = driver.findElement(By.cssSelector("span[class=\"bag__quantity\"]")).getText().trim();

        Assert.assertEquals(Integer.parseInt(numberOfItems),1);
        clickOnElement(By.cssSelector(".buy-btn"));

        // Enter address
        type(By.cssSelector("#firstNameInput"), "testFirstName");
        type(By.cssSelector("#lastNameInput"), "testLastName");
        type(By.cssSelector("#addressLine1Input"), "testHyderabad");
        type(By.cssSelector("#provinceInput"), "testTelangana");
        type(By.cssSelector("#postCodeInput"), "test500090");
        clickOnElement(By.id("checkout-shipping-continue"));

        //veriyf order placed
        String successMessage = driver.findElement(By.cssSelector("#confirmation-message")).getText();
        Assert.assertEquals(successMessage,"Your Order has been successfully placed.");

        List<WebElement> productDetailsInCart = driver.findElements(By.xpath("//section//*/descendant::li[@class='product-option']"));
        List<String> pDetails = new ArrayList<>();

        for (WebElement element:productDetailsInCart){
            pDetails.add(element.getText());
        }

        System.out.println(pDetails);

        // veriyf continue shopping button
        clickOnElement(By.xpath("//button[text()='Continue Shopping »']"));


        //orders
        clickOnElement(By.xpath("//strong[text()='Orders']"));
        String date = driver.findElement(By.cssSelector("div[class='a-column a-span3'] span[class='a-color-secondary value']")).getText();
        System.out.println(date);

        Assert.assertEquals(date, DateUtils.getCurrentDate());

        String productTitle = driver.findElement(By.cssSelector("div[class='a-fixed-left-grid-col a-col-right'] div:nth-child(1)")).getText();
        Assert.assertEquals(productTitle.split(":")[1].trim(), productsNames.get(0));

        //favourites
        clickOnElement(By.cssSelector("a[class*='Navbar_logo']"));
        clickOnElement(By.xpath("//p[text()='"+firstProduct+"']/preceding-sibling::div[@class='shelf-stopper']/button/span/*"));
        clickOnElement(By.xpath("//strong[text()='Favourites']"));

        WebElement favItem = driver.findElement(By.cssSelector("p[class*='shelf-item']"));
        Assert.assertEquals(favItem.getText(), productsNames.get(0));

        driver.quit();
    }
}
