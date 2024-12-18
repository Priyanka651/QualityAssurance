package week10;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MyEbayJavaSelenium {

    // ------ Global variable definitions and declarations ------ //

    WebDriver driver;

    // using Chrome
//    String myWebBrowserDriver = "webdriver.chrome.driver";
//    String myWebBrowserDriverPath = "C:\\BrowserDrivers\\chrome\\chromedriver.exe";

    // using Firefox
    String myWebBrowserDriver = "webdriver.gecko.driver";
    String myWebBrowserDriverPath = "C:\\BrowserDrivers\\firefox\\geckodriver.exe";

    String urlEbay = "http://www.ebay.com/";
    String eBayPageTitle = "Daily Deals on eBay | Best deals and Free Shipping";
    String urlAmazon = "http://www.amazon.com/";
    String mySearchItem = "JBL Speakers";
    String lnkDailyDeals = "Daily Deals";

    // ------ Functional Methods/Test Steps -------- //

    // launch browser and go to ebay.com
    public void launchBrowser() throws InterruptedException {
        System.setProperty(myWebBrowserDriver, myWebBrowserDriverPath);

        // using Chromedriver
//        driver = new ChromeDriver();

        // using Geckodriver
        driver = new FirefoxDriver();

        System.out.println("launchBrowser() - Step 1: Initiated browser driver " + myWebBrowserDriver);
        driver.manage().window().maximize();
        System.out.println("launchBrowser() - Step 2: Maximized browser windows.");
        driver.get(urlEbay);
        System.out.println("launchBrowser() - Step 3: Launch web site " + urlEbay);
        Thread.sleep(2000);
    }

    // Search Product and click link text Daily Deals
    public void searchProduct() throws InterruptedException {
        driver.findElement(By.id("gh-ac")).clear();
        driver.findElement(By.id("gh-ac")).sendKeys(mySearchItem);
//        driver.findElement(By.name("_nkw")).clear();
//        driver.findElement(By.name("_nkw")).sendKeys(mySearchItem);
        System.out.println("searchProduct() - Step 1: Set my search item " + mySearchItem);
        driver.findElement(By.id("gh-btn")).click();
//        driver.findElement(By.cssSelector("input[type='submit']")).click();
        Thread.sleep(3000);
        System.out.println("searchProduct() - Step 2: Clicked Search button and wait 3s.");
        driver.findElement(By.linkText(lnkDailyDeals)).click();
        Thread.sleep(3000);
        System.out.println("searchProduct() - Step 3: Clicked Daily Deals link and wait 3s.");
    }

    // Navigate to amazon.com then go back
    public void navigateToBack() throws InterruptedException {
        driver.navigate().to(urlAmazon);
        Thread.sleep(3000);
        System.out.println("navigateToBack() - Step 1: Navigate to web site " + urlAmazon + " and get page title is " + driver.getTitle());
        driver.navigate().back();
        Thread.sleep(3000);
        System.out.println("navigateToBack() - Step 2: Navigate back to web site " + urlEbay + " and get page title is " + driver.getTitle());
        if (driver.getTitle().contentEquals(eBayPageTitle)){
            System.out.println("navigateToBack() - Step 3: Verified current page " + driver.getTitle() + " equals to expected page " + eBayPageTitle);
            System.out.println("Test Passed!");
        } else {
            System.out.println("navigateToBack() - Step 3: Verified current page " + driver.getTitle() + " is not equals to expected page " + eBayPageTitle);
            System.out.println("Test Failed");
        }
    }

    // Close all browser window and sessions
    public void closeExit() {
        driver.quit();
        System.out.println("closeExit() - Step 1: Closed all open browser windows and killed all open sessions.");
    }

    // ---------- Test Cases execution ------------ //

    public static void main(String[] args) throws InterruptedException {
        MyEbayJavaSelenium obj = new MyEbayJavaSelenium();

        obj.launchBrowser();
        obj.searchProduct();
        obj.navigateToBack();
        obj.closeExit();
    }
}