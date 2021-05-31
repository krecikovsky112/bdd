package edu.iis.mto.bdd.trains.cucumber.steps;

import java.util.List;

import edu.iis.mto.bdd.trains.model.Line;
import edu.iis.mto.bdd.trains.services.InMemoryTimetableService;
import edu.iis.mto.bdd.trains.services.IntineraryService;
import edu.iis.mto.bdd.trains.services.TimetableService;
import org.hamcrest.Matchers;
import org.joda.time.LocalTime;

import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;

public class OptimalItinerarySteps {
    private IntineraryService intineraryService;
    private List<LocalTime> arrivalTimes;


    @Given("^pociągi linii \"(.*)\" z \"(.*)\" odjeżdżają ze stacji \"(.*)\" do \"(.*)\" o$")
    public void givenArrivingTrains(String line, String lineStart, String departure, String destination,
            @Transform(JodaLocalTimeConverter.class) List<LocalTime> departureTimes) {
        throw new PendingException();

    }

    @When("^chcę podróżować z \"([^\"]*)\" do \"([^\"]*)\" o (.*)$")
    public void whenIWantToTravel(String departure, String destination, @Transform(JodaLocalTimeConverter.class) LocalTime startTime) {
        this.arrivalTimes = intineraryService.findNextDepartures(departure, destination, startTime);
    }

    @Then("^powinienem uzyskać informację o pociągach o:$")
    public void shouldBeInformedAbout(@Transform(JodaLocalTimeConverter.class) List<LocalTime> expectedTrainTimes) {
        assertThat(arrivalTimes, Matchers.is(expectedTrainTimes));
    }
}
