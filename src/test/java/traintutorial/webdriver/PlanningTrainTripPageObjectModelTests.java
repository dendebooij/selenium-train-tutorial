package traintutorial.webdriver;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

//TO DO this leads to a nullpointer. Debug
public class PlanningTrainTripPageObjectModelTests extends BaseTests{

     //fill in departure and arrival stations and select correct train stations
    @Test
    public void should_be_able_to_plan_a_trip(){
        trainPlanningPage.departingFrom("Utrecht");
        trainPlanningPage.selectUtrechtCentraalFromList();
        trainPlanningPage.arrivingAt("Schiphol");
        trainPlanningPage.selectSchipholAirportFromList();

        trainPlanningPage.selectArrivalOption();
        trainPlanningPage.openDatePicker();
        trainPlanningPage.selectTomorrow();
        trainPlanningPage.openTimePicker();
        LocalTime expectedArrivalTime = trainPlanningPage.selectTimeOfNineInTheMorning();

        trainPlanningPage.planTrainTrip();

        assertThat(trainPlanningPage.getTripOptions().size(), is(greaterThan(0)));
        LocalTime actualArrivalTime = trainPlanningPage.getActualArrivalTime();
        assertThat("Arrival is on time.", actualArrivalTime.isBefore(expectedArrivalTime));
    }
}
