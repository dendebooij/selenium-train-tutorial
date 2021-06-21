package tutorial.webdriver;

import net.serenitybdd.core.annotations.findby.By;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class PlanningTrainTripRefactoringExercise {
    WebDriver driver;

    @Before
    public void setup() {
        driver = new ChromeDriver(getChromeOptions());

        //implicit waits are not advisable as a waiting strategy. Find a better way.
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get("https://www.ns.nl/en");
        driver.findElement(By.cssSelector(".cookie-notice__btn-accept[data-cookie-notice-action=\"accept-all\"]")).click();
    }

    @Test
    public void should_be_able_to_plan_a_trip() {

        //This code is unreadable. Refactor it so it becomes readable.

        //fill in departure and arrival stations
        driver.findElement(By.id("location-input-FROM-POSITIONED")).sendKeys("Utrecht");
        driver.findElement((By.xpath("//span[contains(@class, 'autosuggestItem')][contains(., 'Utrecht Centraal')]"))).click();
        driver.findElement(By.id("location-input-TO-POSITIONED")).sendKeys("Schiphol");
        driver.findElement((By.xpath("//span[contains(@class, 'autosuggestItem')][contains(., 'Schiphol Airport')]"))).click();

        //select to arrive today over one hour
        driver.findElement(By.cssSelector("label[for=\"radioArrival\"]")).click();
        driver.findElement(By.className("date-planner")).click();
        String currentDayOfTheMonth = driver.findElement(By.xpath("//button[contains(@class, 'contains-today')]")).getText();
        System.out.println("Current date: "+ currentDayOfTheMonth);
        driver.findElement(By.xpath("//button[contains(@class, 'calendar-date-button')][contains(@tabindex, '0')]")).click();
        driver.findElement(By.id("time-input")).click();
        String highlightedTimeString = driver.findElement(By.cssSelector("button.rppb-timepicker-button-highlighted")).getText();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime highLightedTime = LocalTime.parse(highlightedTimeString, timeFormatter);
        LocalTime expectedArrivalTime = highLightedTime.plusHours(1);
        driver.findElement(By.xpath("//*[contains(text(),'" + expectedArrivalTime + "')]")).click();

        //get schedule
        driver.findElement(By.xpath("//button[contains(@aria-controls, \"planningIndicator\")]")).click();

        //Check that the trip time is tomorrow and that we will arrive on time
        List<WebElement> tripOptions = driver.findElements(By.cssSelector("div.rio-jp-travel-options"));
        String actualArrivalTimeString = driver.findElement(By.xpath("(//span[@class=\"rio-jp-travel-option-arrival\"])[2]")).getText();

        assertThat(tripOptions.size(), is(greaterThan(0)));
        LocalTime actualArrivalTime = LocalTime.parse(actualArrivalTimeString, timeFormatter);
        assertThat("Arrival is on time.", actualArrivalTime.isBefore(expectedArrivalTime));
    }

    @After
    public void shutdown() {
        driver.quit();
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Arrays.asList(
                "enable-automation",
                "disable-popup-blocking"));
        options.addArguments("disable-notifications");
        //options.addArguments("\"incognito\"");

        return options;
    }
}