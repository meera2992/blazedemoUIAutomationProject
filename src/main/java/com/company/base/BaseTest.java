package com.company.base;

import com.company.blazedemoLogger.BlazedemoLogger;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.junit.runners.model.TestClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.annotations.*;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author meeramore
 */
//@Listeners(ListenersBlog.class)
public class BaseTest extends BlazedemoLogger {

    public static WebDriver driver = null;

    public static Properties Config = new Properties();
    public static FileInputStream fis = null;
    public static java.util.List<ITestNGMethod> allTestMethods = null;
    static String chromedriver_loc = System.getProperty("user.dir") + "\\drivers\\chromedriver.exe";
    static String firefoxdriver_loc = System.getProperty("user.dir") + "\\drivers\\geckodriver.exe";
    static String iedriver_loc = System.getProperty("user.dir") + "\\drivers\\IEDriverServer.exe";
    static String configfile_loc = System.getProperty("user.dir") + "/src/test/resources/Config.properties";

    Logger log = Logger.getLogger("BlazedemoLogger");

    @BeforeClass
    public String beforeClass() {
        String className = this.getClass().getName();
        return className;
    }

    // BeforeMethod
    @Parameters({"browser"})
    @BeforeMethod()
    public void driverLaunchCucumber(@Optional("chrome") String browser, Method method, XmlTest xmlTest) throws IOException {
        fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/Config.properties");
        Config.load(fis);
        if (Config.getProperty("LocalRun").equalsIgnoreCase("true")) {
             if (driver == null || ((ChromeDriver) driver).getSessionId() == null) {
                if (Config.getProperty("browser").equals("firefox")) {

                    System.setProperty("webdriver.gecko.driver", firefoxdriver_loc);
                    driver = new FirefoxDriver();
                    driver.manage().deleteAllCookies();
                    getURL();

                } else if (Config.getProperty("browser").equalsIgnoreCase("ie")) {
                    //if (driver == null) {
                    DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                    capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
                    System.setProperty("webdriver.ie.driver", iedriver_loc);
                    driver = new InternetExplorerDriver();
                    getURL();
                    //}
                } else if (Config.getProperty("browser").equalsIgnoreCase("chrome")) {

                    //if (driver == null) {
                    System.setProperty("webdriver.chrome.driver", chromedriver_loc);
                    driver = new ChromeDriver();
                    //DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                    ChromeOptions capabilities = new ChromeOptions();
                    capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("incognito");
                    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                    getURL();
                    //}
                }
            } else {
                log.info("Driver is not null and already opened");
            }
        }
        try {
            fis = new FileInputStream(configfile_loc);
        } catch (
                FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            Config.load(fis);
        } catch (
                IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //===============After Method=======
    @AfterMethod
    public void endMethod(Method method, XmlTest xmlTest, ITestResult iTestResult) throws IOException, NoSuchMethodException {
        String methodName = iTestResult.getMethod().getMethodName();
        if((xmlTest.getName().equals("blazedemo-automation"))){
            driver.quit();
        }
        String suiteName = xmlTest.getSuite().getName();
        if ((!suiteName.equalsIgnoreCase("Default Suite"))) {
            driver.quit();
        }
    }

    //===============After Class=======
    @AfterClass()
    public void driverQuitter(XmlTest xmlTest) {
        driver.quit();
    }

    public void getURL() {
        String url = Config.getProperty("URL");
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    public String getHomeDirectory() {
        return System.getProperty("user.dir");
    }

    public void setTestMethods(java.util.List<ITestNGMethod> testMethods) {
        this.allTestMethods = testMethods;
    }

    @Test
    public void testMethod() {
        System.setProperty("testng.thread.affinity", Boolean.TRUE.toString());
        TestNG testng = new TestNG();
        testng.setXmlSuites(Collections.singletonList(suite()));
        testng.setVerbose(2);
        testng.run();
    }

    private XmlSuite suite() {
        XmlSuite suite = new XmlSuite();
        suite.setParallel(XmlSuite.ParallelMode.CLASSES);
        suite.setName("sample_suite");
        xmlTest(suite);
        System.err.println(suite.toXml());
        return suite;
    }

    private int xmlTest(XmlSuite suite) {
        XmlTest xmlTest = new XmlTest(suite);
        xmlTest.setName("sample_test");
        xmlTest.setClasses(Collections.singletonList(xmlClass()));
        int cls = xmlTest.getClasses().size();
        return cls;
    }

    private XmlClass xmlClass() {
        return new XmlClass(TestClass.class,true);
    }


}