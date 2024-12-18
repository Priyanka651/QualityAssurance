package week12;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.time.Duration;
import java.util.Set;


public class TestMyTripReadDatafromExcelTestNG {
    WebDriver driver;
    Actions actions;
    JavascriptExecutor js;
    Object[] windowHandles;
    String labelHotels = "Hotels";
    String labelFlights = "Flights";

    // Logger
    Logger logger = LogManager.getLogger(TestMyTripReadDatafromExcelTestNG.class);

    // Configurable Parameters
    String driverType = "webdriver.chrome.driver";
    String myWebDriverPath = "C:\\Browserdriver\\chrome\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    String baseURL = "http://www.trip.com";
    String screenshotPath = System.getProperty("user.home") + "/Desktop/CS522QA/screenshots";
    String excelPath = "/C:\\Users\\rajes\\IdeaProjects\\CS522_Selenium\\src\\week12/MyTripData.xlsx";
    String searchCity = "";

    @BeforeTest
    void setup() {
        // Initialize Log4j2 configuration
        Configurator.initialize(new DefaultConfiguration());
        Configurator.setRootLevel(Level.INFO);

        // Logging the start of the test
        logger.info("----------- Beginning -----------");

        System.setProperty(driverType, myWebDriverPath);
        driver = new ChromeDriver();
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;
        driver.get(baseURL);
        driver.manage().window().maximize();
        logger.info("Opened " + baseURL + " and maximized browser window size.");
        searchCity = getSearchCityFromExcel();
        logger.info("City to search: " + searchCity);
    }

    @AfterTest
    void tearDown() {
        driver.quit();
        logger.info("Closed all open windows and killed all sessions.");
        logger.info("----------- Ending -----------");
    }

    @Test(priority = 1)
    void searchCitiesFromExcel() {
        try {
            WebElement searchBox = driver.findElement(By.cssSelector(".gccpoi__TripSearchBox-input"));
            searchBox.clear();
            searchBox.sendKeys(searchCity);
            searchBox.sendKeys(Keys.ENTER);

            logger.info("Searching for city: " + searchCity);

            // Wait for results to load
            Thread.sleep(3000); // Replace with explicit waits in real scenarios

            // Validate presence on page
            boolean isCityPresent = driver.getPageSource().contains(searchCity);
            try {
                Assert.assertTrue(isCityPresent, "City '" + searchCity + "' not found on the page!");
                logger.info("City '" + searchCity + "' found on the page.");
            } catch (AssertionError e) {
                logger.error("City '" + searchCity + "' not found. Assertion failed.");
                throw e;
            }

            // Take a screenshot
            takeSnapShot(driver, screenshotPath + "//search_city.png");

        } catch (Exception e) {
            logger.error("Error occurred during city search.", e);
        }
    }

    @Test(priority = 2)
    void switchWindows() {
        try {
            Set<String> windows = driver.getWindowHandles();
            windowHandles = windows.toArray();
            if (windowHandles.length > 1) {
                driver.switchTo().window((String) windowHandles[1]);
                logger.info("Switched to new window. Current title: " + driver.getTitle());
                logger.info("Current URL: " + driver.getCurrentUrl());

                // Take a screenshot
                takeSnapShot(driver, screenshotPath + "//switch_window.png");
            } else {
                logger.warn("No additional windows to switch to.");
            }
        } catch (Exception e) {
            logger.error("Error occurred during window switching.", e);
        }
    }

    @Test(priority = 3)
    void scrollDownUp() {
        try {
            int scrollDistance = 550;
            js.executeScript("window.scrollBy(0," + scrollDistance + ");");
            logger.info("Scrolled down by " + scrollDistance + " pixels.");
            js.executeScript("window.scrollBy(0," + -scrollDistance + ");");
            logger.info("Scrolled up by " + scrollDistance + " pixels.");

            // Take a screenshot
            takeSnapShot(driver, screenshotPath + "//scroll_down_up.png");
        } catch (Exception e) {
            logger.error("Error occurred during scrolling.", e);
        }
    }

    @Test(priority = 4)
    void goToHotel() {
        try {
            driver.findElement(By.linkText("Hotels")).click();
            logger.info("Clicked Hotels tab and waited.");

            String hotel = driver.findElement(By.cssSelector("#header_action_nav_hotels")).getText();
            try {
                Assert.assertEquals(labelHotels, hotel);
                logger.info("Verified the Hotels label matches.");
            } catch (AssertionError e) {
                logger.error("Hotels label does not match. Assertion failed.");
                throw e;
            }

            // Take a screenshot
            takeSnapShot(driver, screenshotPath + "//hotel_tab.png");
        } catch (Exception e) {
            logger.error("Error occurred while navigating to Hotels tab.", e);
        }
    }

    @Test(priority = 5)
    void goToFlights() {
        try {
            // Navigate to the Flights tab
            driver.findElement(By.linkText("Flights")).click();
            logger.info("Clicked Flights tab and waited.");

            // Validate the Flights label
            String flights = driver.findElement(By.cssSelector("#header_action_nav_flights")).getText();
            try {
                Assert.assertEquals(labelFlights, flights);
                logger.info("Verified the Flights label matches.");
            } catch (AssertionError e) {
                logger.error("Flights label does not match. Assertion failed.");
                throw e;
            }

            // Take a screenshot
            takeSnapShot(driver, screenshotPath + "//flights_tab.png");
        } catch (Exception e) {
            logger.error("Error occurred while navigating to Flights tab.", e);
        }
    }

    void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
        // Capture and save the screenshot
        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File destFile = new File(fileWithPath);
        FileUtils.copyFile(srcFile, destFile);
        logger.info("Screenshot saved at: " + fileWithPath);
    }

    private String getSearchCityFromExcel() {
        try (FileInputStream fis = new FileInputStream(excelPath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            // Access the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            // Read the second row (index 1)
            Row row = sheet.getRow(1); // Assuming the first city is in row 2 (index 1)
            if (row != null) {
                // Access the first cell in the row
                Cell cell = row.getCell(0); // Assuming city names are in the first column
                return cell != null ? cell.getStringCellValue() : "";
            }
        } catch (IOException e) {
            logger.error("Failed to read city from Excel file.", e);
        }
        return "";
    }
}