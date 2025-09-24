package com.bs;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    public static WebDriver driver;


    @Parameters("browser")
    @BeforeTest
    public void setBrowser(String browser) throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        MutableCapabilities capabilities = new MutableCapabilities();
        HashMap<String, Object> bstackOptions = new HashMap<>();

        switch (browser) {
            case "chrome_win10":
                capabilities.setCapability("browserName", "Chrome");
                capabilities.setCapability("browserVersion", "latest");

                bstackOptions.put("userName", "sivakolli_aIpA4i");
                bstackOptions.put("accessKey", "8MZSbx6Y8RsspGzMTRsr");
                //bstackOptions.put("browserstack.geoLocation", "IN");
                bstackOptions.put("geoLocation", "IN");
                bstackOptions.put("projectName", "Hackathon Project");
                bstackOptions.put("buildName", "Testathon Test");
                bstackOptions.put("sessionName", "BS Test");
                bstackOptions.put("os", "Windows");
                bstackOptions.put("osVersion", "10");
                capabilities.setCapability("bstack:options", bstackOptions);
                driver = new RemoteWebDriver(new URL("https://sivakolli_aIpA4i:8MZSbx6Y8RsspGzMTRsr@hub.browserstack.com/wd/hub"), capabilities);
                break;
            case "chrome_win11":
                capabilities.setCapability("browserName", "Chrome");
                capabilities.setCapability("browserVersion", "latest");

                bstackOptions.put("userName", "sivakolli_aIpA4i");
                bstackOptions.put("accessKey", "8MZSbx6Y8RsspGzMTRsr");
                //bstackOptions.put("browserstack.geoLocation", "IN");
                bstackOptions.put("geoLocation", "IN");
                bstackOptions.put("projectName", "Hackathon Project");
                bstackOptions.put("buildName", "Testathon Test");
                bstackOptions.put("sessionName", "BS Test");
                bstackOptions.put("os", "Windows");
                bstackOptions.put("osVersion", "11");
                capabilities.setCapability("bstack:options", bstackOptions);
                driver = new RemoteWebDriver(new URL("https://sivakolli_aIpA4i:8MZSbx6Y8RsspGzMTRsr@hub.browserstack.com/wd/hub"), capabilities);
                break;
            case "firefox":
                capabilities.setCapability("browserName", "Firefox");
                capabilities.setCapability("browserVersion", "latest");

                bstackOptions.put("userName", "sivakolli_aIpA4i");
                bstackOptions.put("accessKey", "8MZSbx6Y8RsspGzMTRsr");
                //bstackOptions.put("browserstack.geoLocation", "IN");
                bstackOptions.put("geoLocation", "IN");
                bstackOptions.put("projectName", "Hackathon Project");
                bstackOptions.put("buildName", "Testathon Test");
                bstackOptions.put("sessionName", "BS Test");
                bstackOptions.put("os", "Windows");
                bstackOptions.put("osVersion", "10");
                capabilities.setCapability("bstack:options", bstackOptions);
                driver = new RemoteWebDriver(new URL("https://sivakolli_aIpA4i:8MZSbx6Y8RsspGzMTRsr@hub.browserstack.com/wd/hub"), capabilities);
                break;
            case "edge":
                capabilities.setCapability("browserName", "Edge");
                capabilities.setCapability("browserVersion", "latest");

                bstackOptions.put("userName", "sivakolli_aIpA4i");
                bstackOptions.put("accessKey", "8MZSbx6Y8RsspGzMTRsr");
                //bstackOptions.put("browserstack.geoLocation", "IN");
                bstackOptions.put("geoLocation", "IN");
                bstackOptions.put("projectName", "Hackathon Project");
                bstackOptions.put("buildName", "Testathon Test");
                bstackOptions.put("sessionName", "BS Test");
                bstackOptions.put("os", "Windows");
                bstackOptions.put("osVersion", "10");
                capabilities.setCapability("bstack:options", bstackOptions);
                driver = new RemoteWebDriver(new URL("https://sivakolli_aIpA4i:8MZSbx6Y8RsspGzMTRsr@hub.browserstack.com/wd/hub"), capabilities);
                break;
            case "safari":
                capabilities.setCapability("browserName", "Safari");
                capabilities.setCapability("browserVersion", "latest");

                bstackOptions.put("userName", "sivakolli_aIpA4i");
                bstackOptions.put("accessKey", "8MZSbx6Y8RsspGzMTRsr");
                //bstackOptions.put("browserstack.geoLocation", "IN");
                bstackOptions.put("geoLocation", "IN");
                bstackOptions.put("projectName", "Hackathon Project");
                bstackOptions.put("buildName", "Testathon Test");
                bstackOptions.put("sessionName", "BS Test");
                bstackOptions.put("os", "OS X");
                bstackOptions.put("osVersion", "Ventura");
                capabilities.setCapability("bstack:options", bstackOptions);

                driver = new RemoteWebDriver(new URL("https://sivakolli_aIpA4i:8MZSbx6Y8RsspGzMTRsr@hub.browserstack.com/wd/hub"), capabilities);
                break;
        }
        driver.get("https://testathon.live/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @AfterTest
    public void killSession() {
        driver.quit();
    }


}
