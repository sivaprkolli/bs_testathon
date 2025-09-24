package com.bs;

import com.bs.utils.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class HackathonTests extends BaseBS {

    public List<String> productsNames;
    public String firstProduct;

    public void type(By by, String data) {
        driver.findElement(by).sendKeys(data);
    }

    public void clickOnElement(By by) {
        driver.findElement(by).click();
    }

    public String getTextMessage(By by) {
        return driver.findElement(by).getText().trim();
    }

    @Test
    public void loginAndVerifyUserName() throws InterruptedException {
        //Login
        clickOnElement(By.cssSelector("a[id='Sign In']"));
        clickOnElement(By.xpath("//div[text()='Select Username']"));
        clickOnElement(By.xpath("//div[text()='demouser']"));
        clickOnElement(By.xpath("//div[text()='Select Password']"));
        clickOnElement(By.xpath("//div[text()='testingisfun99']"));
        clickOnElement(By.cssSelector("#login-btn"));
        Assert.assertEquals(getTextMessage(By.cssSelector(".username")), "demouser");

    }

    @Test(dependsOnMethods = "loginAndVerifyUserName")
    public void verifyFilters() throws InterruptedException {
        clickOnElement(By.xpath("//span[text()='Apple']"));
        Thread.sleep(3000);
        List<WebElement> products = driver.findElements(By.cssSelector("p[class*='shelf-item']"));
        productsNames = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            productsNames.add(products.get(i).getText());
        }

        System.out.println(productsNames);

        boolean allContain = productsNames.stream()
                .allMatch(item -> item.toLowerCase().contains("iphone"));

        Assert.assertTrue(allContain);
    }

    //offers by enabling geo location
//        clickOnElement(By.cssSelector("#offers"));
//        List<WebElement> offers = driver.findElements(By.cssSelector(".offer-title"));
//        Assert.assertTrue(offers.size()>1);

    @Test(dependsOnMethods = "verifyFilters")
    public void homoLogoNavigation() throws InterruptedException {
        clickOnElement(By.cssSelector("a[class*='Navbar_logo']"));
        Thread.sleep(2000);
        Assert.assertTrue(driver.findElements(By.cssSelector("p[class*='shelf-item']")).size() > 0);
    }


    @Test(dependsOnMethods = "homoLogoNavigation")
    public void verifyProductBagSize() throws InterruptedException {
        firstProduct = productsNames.get(0);
        System.out.println(firstProduct);

        clickOnElement(By.xpath("//p[text()='" + firstProduct + "']/following-sibling::div[contains(@class,'btn')]"));
        Thread.sleep(3000);
        //Verify bag items
        String numberOfItems = driver.findElement(By.cssSelector("span[class=\"bag__quantity\"]")).getText().trim();

        Assert.assertEquals(Integer.parseInt(numberOfItems), 1);
    }

    @Test(dependsOnMethods = "verifyProductBagSize")
    public void verifyOrderPlaced() {
        clickOnElement(By.cssSelector(".buy-btn"));
        type(By.cssSelector("#firstNameInput"), "testFirstName");
        type(By.cssSelector("#lastNameInput"), "testLastName");
        type(By.cssSelector("#addressLine1Input"), "testHyderabad");
        type(By.cssSelector("#provinceInput"), "testTelangana");
        type(By.cssSelector("#postCodeInput"), "test500090");
        clickOnElement(By.id("checkout-shipping-continue"));
        String successMessage = driver.findElement(By.cssSelector("#confirmation-message")).getText();
        Assert.assertEquals(successMessage, "Your Order has been successfully placed.");
    }

    @Test(dependsOnMethods = "verifyOrderPlaced")
    public void verifyProductDetailsOnOrders() throws InterruptedException {
        List<WebElement> productDetailsInCart = driver.findElements(By.xpath("//section//*/descendant::li[@class='product-option']"));
        List<String> pDetails = new ArrayList<>();

        for (WebElement element : productDetailsInCart) {
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
    }

    @Test(dependsOnMethods = "verifyProductDetailsOnOrders")
    public void verifyFavouriteProducts() throws InterruptedException {
        //favourites
        clickOnElement(By.cssSelector("a[class*='Navbar_logo']"));
        clickOnElement(By.xpath("//p[text()='" + firstProduct + "']/preceding-sibling::div[@class='shelf-stopper']/button/span/*"));
        clickOnElement(By.xpath("//strong[text()='Favourites']"));

        // WebElement favItem = driver.findElement(By.cssSelector("p[class*='shelf-item']"));
        Thread.sleep(3000);
        Assert.assertEquals(driver.findElement(By.cssSelector("p[class*='shelf-item']")).getText(), productsNames.get(0));

        driver.quit();
    }
}
