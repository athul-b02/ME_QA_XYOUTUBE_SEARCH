package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @Test(enabled = true)
        public void testCase01() {
                System.out.println("Test Case01: BEGIN");
                driver.get("https://www.youtube.com/");
                Assert.assertTrue(driver.getCurrentUrl().contains("youtube.com"), "HomePage url is not valid");
                Wrappers.clickOnElementWrapper(driver, By.xpath("//a[text()='About']"));
                Wrappers.displayText(driver, By.xpath("//section[@class='ytabout__content']"));
                System.out.println("Test Case01: END");
        }

        @Test(enabled = true)
        public void testCase02() {
                System.out.println("Test Case02: BEGIN");
                driver.get("https://www.youtube.com/");
                Wrappers.clickOnElementWrapper(driver, By.xpath("//a[@title='Films']"));
                
                Wrappers.scrollTillEndRight(driver, By.xpath("//div[@id='right-arrow']//button"));

                SoftAssert sA = new SoftAssert();
                sA.assertEquals(Wrappers.getMovieRating(driver, 
                By.xpath("//div[@id='items' and @class='style-scope yt-horizontal-list-renderer']/*[last()]")),
                 "U","Rating is not U");

                sA.assertTrue(Wrappers.checkMovieCategory(driver,
                By.xpath("//div[@id='items' and @class='style-scope yt-horizontal-list-renderer']/*[last()]")),"Category does not exist for the movie");
                sA.assertAll();
                System.out.println("Test Case02: END");
        }

        @Test(enabled = true)
        public void testCase03(){
                System.out.println("Test Case03: BEGIN");
                driver.get("https://www.youtube.com/");
                
                Wrappers.clickOnElementWrapper(driver, By.xpath("//a[@title='Music']"));
                Wrappers.scrollTillEndRight(driver, By.xpath("(//div[@id='right-arrow']//button)[1]"));
                SoftAssert sA = new SoftAssert();
                
                sA.assertTrue(Wrappers.checkSongCount(driver, 
                By.xpath("(//div[@id='items' and @class='style-scope yt-horizontal-list-renderer'])[1]/*[last()]"),
                 50),
                 "song count not less than or equal to 50");
                sA.assertAll();
                System.out.println("Test Case03: END");
        }
        @Test(enabled = true)
        public void testCase04(){
                /*
                 * xpaths list:
                 * Latest news posts:
                 * //ytd-rich-section-renderer[@class='style-scope ytd-rich-grid-renderer'][descendant::span[text()='Latest news posts']]
                 * each news link
                 * //div[@role='link']
                 * vote counts
                 * //span[@id='vote-count-middle']
                 */
                
                System.out.println("Test Case04: BEGIN");
                driver.get("https://www.youtube.com/");
                Wrappers.clickOnElementWrapper(driver, By.xpath("//a[@title='News']"));
                Wrappers.printNewsDetails(driver, 
                By.xpath("//ytd-rich-section-renderer[@class='style-scope ytd-rich-grid-renderer'][descendant::span[text()='Latest news posts']]"));
                System.out.println("Test Case04: END");
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}