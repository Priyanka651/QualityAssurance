

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
