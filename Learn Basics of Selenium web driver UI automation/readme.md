

## Open/Close browser

```bash
WebDriver driver;
System.
setProperty("webdriver.chrome.driver","C:\\Drivers\\Selenium\\
chrome\\chromedriver.exe");
driver = new ChromeDriver();
or
System.
setProperty("webdriver.gecko.driver","C:\\Drivers\\Selenium\\fir
efox\\geckodriver.exe");
FirefoxDriver driver = new FirefoxDriver();
driver.close();
driver.quit();
```


## Get Commands
```bash
driver.get("http://www.google.com");
driver.getTitle();
driver.getCurrentUrl();
driver.getWindowHandle();
driver.getWindowHandles();
driver.getPageSource();
```

## Navigation Commands
```bash
driver.navigate().to("http://www.google.com");
driver.navigate().back();
driver.navigate().forward();
driver.navigate().refresh();
```

## Window size
```bash
driver.manage().window().maximize();
```

## Delete browser cookies
```bash
driver.manage().deleteAllCookies();
```

## Switch Window
```bash
driver.switchTo().window(childwindow);
driver.switchTo().window(parent);
driver.switchTo.frame("Frame_ID");
driver.switchTo().defaultContent();
```

## Locator Commands
```bash
driver.findElement(By.
name("field-keywords")).sendKeys("Video
Camera");
driver.findElement(By.
id("nav-search-submit-button")).click();
driver.findElement(By.
xpath("//*[@id=\"gh-ac\"]")).click();
driver.findElement(By.
cssSelector("#gh-ac")).click();
driver.findElement(By.
linkText("Best Sellers")).click();
```

## How to get dynamic path
```bash
• driver.findElement(By.cssSelector("input[type='submit']")).click();
```

## Scroll functions can be defined as follows :
```bash
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("window.scrollBy(0,250)", "");
```

 ## How to scroll down on a web page in Selenium by defining the number of pixels

```bash
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class HandleScroll 
{

 @Test
 public void scrollDown()
         {
          System.setProperty("webdriver.gecko.driver","D://Selenium    Environment//Drivers//geckodriver.exe");
            WebDriver driver = new FirefoxDriver();
            driver.navigate().to("Website URL");

            //to perform Scroll on application using Selenium
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,350)", "");
         }
}
```


## Sample practice
**1. MyEbayJavaSelenium.java**
**2. MyTripJavaSelenium**


