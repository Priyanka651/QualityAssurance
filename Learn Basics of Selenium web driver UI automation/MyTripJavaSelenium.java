package week10;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class MyTripJavaSelenium {
    public static void main(String[] args) throws InterruptedException {

        // Definitions
        String myWebDriverType = "webdriver.chrome.driver"; // define your browser driver type, chrome or gecko?
        String myWebDriverPath = "C:\\BrowserDrivers\\chrome\\chromedriver.exe"; // define your browser driver path
        String myURL = "http://www.trip.com";
        String mySearchWords = "Tokyo"; // define your trip.com search city, or country

        System.setProperty(myWebDriverType, myWebDriverPath);

        // if you are using Chrome browser
        WebDriver driver = new ChromeDriver();

        // if you are using Firefox browser
//        WebDriver driver = new FirefoxDriver();

        // use for keyboard keys
        Actions actions = new Actions(driver);

        System.out.println("----------- Beginning -----------");

        // Open URL
        driver.get(myURL);
        driver.manage().window().maximize();
        System.out.println("Step 1: opened " + myURL + " and maximized browser window size.");

        // Get current web page title
//        String currHandle=driver.getWindowHandle();
        System.out.println("Step 2: remember current window is " + driver.getTitle() + " before opening a new window.");

        // Input search words and hit enter key to search
        driver.findElement(By.cssSelector("#ibuHeaderSearch > div > div > div > div.gccpoi__TripSearchBox-content > input")).sendKeys(mySearchWords);
        Thread.sleep(2000);
        System.out.println("Step 3: Set my search word " + mySearchWords + " and wait 2s before hitting enter key");
        actions.sendKeys(Keys.ENTER).build().perform();
        Thread.sleep(3000);
        System.out.println("Step 4: Hit enter key and wait 3s before the page " + driver.getTitle() + " loaded.");

        // Switching windows
        Object[] windowHandles=driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
        System.out.println("Step 5: remember current window is " + driver.getTitle() + ".");

        // To perform Scroll on application using Selenium
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,550)", "");
        //        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        Thread.sleep(3000);
        js.executeScript("window.scrollBy(0,-550)", "");
        Thread.sleep(3000);
        System.out.println("Step 6: Scroll down and up the page.");

        // Navigate top menus
        driver.findElement(By.id("header_action_nav_hotels")).click(); // Hotels
        Thread.sleep(3000);
        System.out.println("Step 7: Clicked tab bar Hotels and wait 3s before the page loaded.");
        driver.findElement(By.id("header_action_nav_flights")).click(); // Flights
        Thread.sleep(3000);
        System.out.println("Step 8: Clicked tab bar Flights and wait 3s before the page loaded.");

        driver.close(); //closing current window
        System.out.println("Step 9: Close current window.");
        driver.switchTo().window((String) windowHandles[0]); // Switch back to the old window
        System.out.println("Step 10: Switch back to the old window.");

        driver.quit();
        System.out.println("Step 11: close all open windows and kill all open sessions.");

        System.out.println("----------- Ending -----------");
    }
}