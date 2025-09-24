package com.bs;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class BaseBS {
    public static WebDriver driver;
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    @Parameters("browser")
    @BeforeTest
    public void setBrowser(String browser) throws MalformedURLException {
        HashMap<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("userName", "sivakolli_aIpA4i");
        bstackOptions.put("accessKey", "8MZSbx6Y8RsspGzMTRsr");
        bstackOptions.put("geoLocation", "IN");
        bstackOptions.put("projectName", "Hackathon Project");
        bstackOptions.put("buildName", "Testathon Test");
        //bstackOptions.put("sessionName", method.getName());
        bstackOptions.put("sessionName", "Bs Test");

        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("bstack:options", bstackOptions);

        switch (browser) {
            case "chrome_win10":
                setBrowserCapabilities(capabilities, "Chrome", "latest", "Windows", "10");
                break;
            case "chrome_win11":
                setBrowserCapabilities(capabilities, "Chrome", "latest", "Windows", "11");
                break;
            case "firefox_win":
                setBrowserCapabilities(capabilities, "Firefox", "latest", "Windows", "10");
                break;

            case "firefox_mac":
                setBrowserCapabilities(capabilities, "Firefox", "latest", "OS X", "tahoe");
                break;
            case "edge_win":
                setBrowserCapabilities(capabilities, "Edge", "latest", "Windows", "10");
                break;
            case "edge_mac":
                setBrowserCapabilities(capabilities, "Edge", "latest", "OS X", "Sonoma");
                break;
            case "safari_tahoe":
                setBrowserCapabilities(capabilities, "Safari", "latest", "OS X", "tahoe");
                break;
            case "safari_sonoma":
                setBrowserCapabilities(capabilities, "Safari", "latest", "OS X", "Sonoma");
                break;
        }

        driverThreadLocal.set(new RemoteWebDriver(new URL("https://sivakolli_aIpA4i:8MZSbx6Y8RsspGzMTRsr@hub.browserstack.com/wd/hub"), capabilities));
        driver = driverThreadLocal.get();
        driver.get("https://testathon.live/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    private void setBrowserCapabilities(MutableCapabilities capabilities, String browserName, String browserVersion, String os, String osVersion) {
        capabilities.setCapability("browserName", browserName);
        capabilities.setCapability("browserVersion", browserVersion);
        if (os != null && osVersion != null) {
            HashMap<String, Object> bstackOptions = (HashMap<String, Object>) capabilities.getCapability("bstack:options");
            bstackOptions.put("os", os);
            bstackOptions.put("osVersion", osVersion);
        }
    }
}
