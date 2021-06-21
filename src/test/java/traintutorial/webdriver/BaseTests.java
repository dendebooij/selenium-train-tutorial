package traintutorial.webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.core.annotations.findby.By;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.TrainPlanningPage;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class BaseTests {

    private WebDriver driver;
    protected TrainPlanningPage trainPlanningPage;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(getChromeOptions());

        //implicit waits are not advisable as a waiting strategy. Find a better way.
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        trainPlanningPage = new TrainPlanningPage(driver);
    }

    public void goToTrainPlanningPage() {
        driver.get("https://www.ns.nl/en");
    }

    @AfterClass
    public void shutdown() {
        driver.quit();
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Arrays.asList(
                "enable-automation",
                "disable-popup-blocking"));
        options.addArguments("disable-notifications");
        return options;
    }

    public void acceptCookieStatement(){
        driver.findElement(By.cssSelector(".cookie-notice__btn-accept[data-cookie-notice-action='accept-all']")).click();
    }
}