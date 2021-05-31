package edu.iis.mto.bdd.trains.junit;

import edu.iis.mto.bdd.trains.model.Line;
import edu.iis.mto.bdd.trains.services.BasicIntineraryService;
import edu.iis.mto.bdd.trains.services.IntineraryService;
import edu.iis.mto.bdd.trains.services.TimetableService;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WhenCalculatingArrivalTimes {
    private TimetableService mockTimetableService;
    private Line line;
    private final String departureFrom = "Epping";
    private final String destination = "Central";

    @Before
    public void setUp() {
        mockTimetableService = mock(TimetableService.class);
        line = Line.named("test").departingFrom(departureFrom).withStations(departureFrom, destination);
        when(mockTimetableService.findLinesThrough(departureFrom, destination)).thenReturn(Arrays.asList(line));
    }

    @Test
    public void shouldFindZeroDepartures() {
        List<LocalTime> departuresTimes = Arrays.asList(
                new LocalTime(8, 59),
                new LocalTime(9, 0),
                new LocalTime(9, 30),
                new LocalTime(9, 40)
        );
        int timeWindow = 30;

        when(mockTimetableService.findArrivalTimes(line, departureFrom)).thenReturn(departuresTimes);

        IntineraryService intineraryService = new BasicIntineraryService(mockTimetableService, timeWindow);

        List<LocalTime> expected = new ArrayList<>();
        assertThat(intineraryService.findNextDepartures(departureFrom, destination, new LocalTime(9, 0)), equalTo(expected));
    }

    @Test
    public void shouldFindOneDeparture() {
        List<LocalTime> departuresTimes = Arrays.asList(
                new LocalTime(10, 59),
                new LocalTime(11, 0),
                new LocalTime(11, 10),
                new LocalTime(11, 30),
                new LocalTime(11, 31)
        );
        int timeWindow = 30;

        when(mockTimetableService.findArrivalTimes(line, departureFrom)).thenReturn(departuresTimes);

        IntineraryService intineraryService = new BasicIntineraryService(mockTimetableService, timeWindow);

        List<LocalTime> expected = Arrays.asList(new LocalTime(11, 10));
        assertThat(intineraryService.findNextDepartures(departureFrom, destination, new LocalTime(11, 0)), equalTo(expected));
    }

    @Test
    public void shouldFindAllOfDepartures() {
        List<LocalTime> departuresTimes = Arrays.asList(
                new LocalTime(7, 59),
                new LocalTime(8, 0),
                new LocalTime(8, 1),
                new LocalTime(8, 5),
                new LocalTime(8, 6),
                new LocalTime(8, 7),
                new LocalTime(8, 9),
                new LocalTime(8, 10),
                new LocalTime(8, 11)
        );
        int timeWindow = 30;

        when(mockTimetableService.findArrivalTimes(line, departureFrom)).thenReturn(departuresTimes);

        IntineraryService intineraryService = new BasicIntineraryService(mockTimetableService, timeWindow);

        List<LocalTime> expected = Arrays.asList(
                new LocalTime(7, 59),
                new LocalTime(8, 0),
                new LocalTime(8, 1),
                new LocalTime(8, 5),
                new LocalTime(8, 6),
                new LocalTime(8, 7),
                new LocalTime(8, 9),
                new LocalTime(8, 10),
                new LocalTime(8, 11)
        );
        assertThat(intineraryService.findNextDepartures(departureFrom, destination, new LocalTime(7, 58)), equalTo(expected));
    }
}