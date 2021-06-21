package traintutorial.webdriver;

import org.openqa.selenium.By;

public enum TravelDay {
    Today(By.xpath("//button[contains(@class,'contains-today')]")),
    Tomorrow(By.xpath("(//button[contains(@class,'calendar-date-button')][preceding::button[contains(@class, 'contains-today')]])[1]"));

    private final By dayOfTheWeek;

    TravelDay(By dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public By getDayOfTheWeek() {
        return dayOfTheWeek;
    }
}

