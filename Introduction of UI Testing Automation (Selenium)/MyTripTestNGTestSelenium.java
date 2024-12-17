package week11;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import java.time.Duration;
//import java.time.Duration;

public class MyTripTestNGTestSelenium {
    WebDriver driver;
    Actions actions;
    Object[] windowHandles;
    String myWebDriverType = "webdriver.chrome.driver";
    String myWebDriverPath = "C:\\Browserdriver\\chrome\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    String myURL = "http://www.trip.com/";
    String mySearchWords = "Tokyo";
    String labelHotels = "Hotels";
    String labelFlights = "Flights";

    String screenshotPath = "c://CS522Screenshots";

    @BeforeTest
    void setup(){
        System.out.println("----------- Beginning -----------");
        System.setProperty(myWebDriverType, myWebDriverPath);
        driver = new ChromeDriver();
        actions = new Actions(driver);
        driver.get(myURL);
        driver.manage().window().maximize();
        System.out.println("First Step: opened " + myURL + " and maximized browser window size.");
    }

    @AfterTest
    void tearDown(){
        driver.quit();
        System.out.println("Last Step: close all open windows and kill all open sessions.");
        System.out.println("----------- Ending -----------");
    }

    @Test(priority = 1)
    void goForMySearch() {

        // input search word and hit enter key to search, implicit wait
        driver.findElement(By.cssSelector(".gccpoi__TripSearchBox-input")).sendKeys(mySearchWords);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("Step 3: Set search word to \"" + mySearchWords + "\" and waiting before pressing Enter.");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("Step 3: Set my search word " + mySearchWords + " and wait 2s before hitting enter key");

        actions.sendKeys(Keys.ENTER).build().perform();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("Step 4: Hit enter key and wait 3s before the page " + driver.getTitle() + " loaded.");

        // assertion to check if the search city is in the new page
        boolean b = driver.getPageSource().contains(mySearchWords);
        Assert.assertTrue(b);

        // take a screenshot
        try{
            takeSnapShot(driver, screenshotPath + "//" + "test1.png") ;
        }catch (Throwable t){
            System.out.println("Failed to take screenshot" + " test1.png.");
        }
    }

    @Test(priority = 2)
    void switchWindows(){
        windowHandles=driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
        System.out.println("Step 5: remember current window is " + driver.getTitle() + ".");

        // take a screenshot
        try{
            takeSnapShot(driver, screenshotPath + "//" + "test2.png") ;
        }catch (Throwable t){
            System.out.println("Failed to take screenshot" + " test2.png.");
        }
    }

    @Test(priority = 3)
    void scrollDownUp() {
        // Initialize JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll down (e.g., 550 pixels)
        int scrollDistance = 550;
        js.executeScript("window.scrollBy(0," + scrollDistance + ");");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Scroll up (e.g., 550 pixels)
        js.executeScript("window.scrollBy(0," + -scrollDistance + ");");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        System.out.println("Step 6: Scroll down and up the page.");

        // Take a screenshot
        try {
            takeSnapShot(driver, screenshotPath + "//" + "test3.png");
        } catch (Throwable t) {
            System.out.println("Failed to take screenshot: test3.png.");
        }
    }


    @Test(priority = 4)
    void goToHotel(){

        // go to top navigation bar Hotel
        driver.findElement(By.linkText("Hotels")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("Step 7: Clicked  Hotels tab and waited.");


        // getText() for the hotel title text
        String hotel = driver.findElement(By.cssSelector("#header_action_nav_hotels")).getText();
        // assertion to check if the hotel title text is in the page
        Assert.assertEquals(labelHotels, hotel);

        // take a screenshot
        try {
            takeSnapShot(driver, screenshotPath + "//" + "test4.png");
        } catch (Throwable t) {
            System.out.println("Failed to take screenshot: test4.png.");
        }

    }

    @Test(priority = 5)
    void goToFlights() {
        // Go to the top navigation bar Flights
        driver.findElement(By.linkText("Flights")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("Step 8: Clicked Flights tab and waited.");

        // getText() for the flights title text
        String flights = driver.findElement(By.cssSelector("#header_action_nav_flights")).getText();
        // Assertion to check if the flights title text matches the expected label
        Assert.assertEquals(labelFlights, flights);

        // Take a screenshot
        try {
            takeSnapShot(driver, screenshotPath + "//" + "test5.png");
        } catch (Throwable t) {
            System.out.println("Failed to take screenshot: test5.png.");
        }
    }


    public void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception {
        // Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);

        //Call getScreenshotAs method to create image file
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination
        File DestFile=new File(fileWithPath);

        //Copy file at destination
        FileUtils.copyFile(SrcFile, DestFile);
    }
}