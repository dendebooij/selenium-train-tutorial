package tutorial.webdriver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.time.LocalTime.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static tutorial.webdriver.DeparturePreference.ArriveBefore;
import static tutorial.webdriver.DeparturePreference.LeaveAfter;

public class PlanningTrainTripRefactoredTests {
    WebDriver driver;

    @Before
    public void setup() {
        driver = new ChromeDriver(getChromeOptions());

        //implicit waits are not advisable as a waiting strategy. Find a better way.
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get("https://www.ns.nl/en");
        driver.findElement(By.cssSelector(".cookie-notice__btn-accept[data-cookie-notice-action='accept-all']")).click();
    }

    @Test
    public void should_be_able_to_plan_a_trip_tomorrow_with_arrival_deadline() {

        departureStationIs("Utrecht Centraal");
        destinationStationIs("Schiphol Airport");
        theTrainShould(ArriveBefore, 10, 30, TravelDay.Tomorrow);
        planTrip();

        List<WebElement> tripOptions = driver.findElements(By.cssSelector("div.rio-jp-travel-options"));
        String actualArrivalTimeText = driver.findElement(By.xpath("(//div[contains(@class, 'rio-jp-arrival-time')])[1]")).getText();
        LocalTime actualArrivalTime = parse(actualArrivalTimeText, DateTimeFormatter.ofPattern("HH:mm"));
        String arrivalStation = driver.findElement(By.xpath("(//button[contains(@aria-haspopup,'true')]//span[contains(@class, 'rio-jp-stop-name')])[2]")).getText();

        assertThat(tripOptions.size(), is(greaterThan(0)));
        assertThat(actualArrivalTime, lessThanOrEqualTo(parse("10:30")));
        assertThat(arrivalStation, containsString("Schiphol Airport"));
    }

    private void planTrip() {
        driver.findElement(By.xpath("//button[contains(@aria-controls, 'planningIndicator')]")).click();
    }

    static final HashMap<DeparturePreference, By> DEPARTURE_RADIO_BUTTONS =
            new HashMap<>();

    static {
        DEPARTURE_RADIO_BUTTONS.put(ArriveBefore, By.cssSelector("label[for='radioArrival']"));
        DEPARTURE_RADIO_BUTTONS.put(LeaveAfter, By.cssSelector("label[for='radioDeparture']"));
    }

    private void theTrainShould(DeparturePreference departurePreference,
                                int hour,
                                int minute,
                                TravelDay travelDay) {
        driver.findElement(DEPARTURE_RADIO_BUTTONS.get(departurePreference)).click();

        driver.findElement(By.className("date-planner")).click();
        driver.findElement(travelDay.getDayOfTheWeek()).click();

        driver.findElement(By.id("time-input")).click();
        driver.findElement(By.xpath("//button[contains(text(),'" + hour + ":" + minute + "')]")).click();
    }

    private void departureStationIs(String station) {
        driver.findElement(By.id("location-input-FROM-POSITIONED")).sendKeys(station);
        driver.findElement((By.xpath("//span[contains(@class, 'autosuggestItem')][contains(., '" + station + "')]"))).click();
    }

    private void destinationStationIs(String station) {
        driver.findElement(By.id("location-input-TO-POSITIONED")).sendKeys(station);
        driver.findElement((By.xpath("//span[contains(@class, 'autosuggestItem')][contains(., '" + station + "')]"))).click();
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
