package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TrainPlanningPage {
    private final WebDriver driver;
    private final By departureInputField = By.id("location-input-FROM-POSITIONED");
    private final By departureSelection = By.xpath("//span[contains(text(), 'Utrecht Centraal')]");
    private final By arrivalInputField = By.id("location-input-TO-POSITIONED");
    private final By arrivalSelection = By.xpath("//span[contains(text(), 'Schiphol Airport')]");
    private final By arrivalRadioButton = By.cssSelector("label[for='radioArrival']");
    private final By openDatePickerButton = By.className("date-planner");
    private final By datePickerTomorrow = By.cssSelector("div[class='calendar-container'] button[tabindex='0']");
    private final By timeInput = By.id("time-input");
    private final By expectedArrivalTimeButton = By.xpath("//div[@class='rppb-timepicker-container']//button[contains(text(), '09:00')]");
    private final By planTripButton = By.xpath("//button[contains(@aria-controls, 'planningIndicator')]");
    private final By tripOptions = By.cssSelector("div.rio-jp-travel-options");
    private final By actualArrivalTime = By.xpath("(//span[@class='rio-jp-travel-option-arrival'])[2]");
    DateTimeFormatter hoursAndMinutesFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public TrainPlanningPage(WebDriver driver) {
        this.driver = driver;
    }

    public void departingFrom(String departureLocation) {
        driver.findElement(departureInputField).sendKeys(departureLocation);
    }

    public void selectUtrechtCentraalFromList() {
        driver.findElement(departureSelection).click();
    }

    public void arrivingAt(String arrivalLocation) {
        driver.findElement(arrivalInputField).sendKeys(arrivalLocation);
    }

    public void selectSchipholAirportFromList() {
        driver.findElement(arrivalSelection).click();
    }

    public void selectArrivalOption() {
        driver.findElement(arrivalRadioButton).click();
    }

    public void openDatePicker() {
        driver.findElement(openDatePickerButton).click();
    }

    public void selectTomorrow() {
        driver.findElement(datePickerTomorrow).click();
    }

    public void openTimePicker() {
        driver.findElement(timeInput).click();
    }

    //refactor so formatter is in a utils directory
    public LocalTime selectTimeOfNineInTheMorning() {
        LocalTime expectedArrivalTime = LocalTime.parse(driver.findElement(expectedArrivalTimeButton).getText(), hoursAndMinutesFormatter);
        driver.findElement(expectedArrivalTimeButton).click();
        return expectedArrivalTime;
    }

    public void planTrainTrip() {
        driver.findElement(planTripButton).click();
    }

    public List<WebElement> getTripOptions(){
        return driver.findElements(tripOptions);
    }

    public LocalTime getActualArrivalTime() {
        String actualArrivalTimeString;
        actualArrivalTimeString = driver.findElement(actualArrivalTime).getText();
        return LocalTime.parse(actualArrivalTimeString, hoursAndMinutesFormatter);
    }
}
