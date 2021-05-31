package edu.iis.mto.bdd.trains.services;

import edu.iis.mto.bdd.trains.model.Line;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class BasicIntineraryService implements IntineraryService {

    private final TimetableService timetableService;
    private final int period;

    public BasicIntineraryService(TimetableService timetableService, int period) {
        this.timetableService = timetableService;
        this.period = period;
    }

    @Override
    public List<LocalTime> findNextDepartures(String departure, String destination, LocalTime startTime) {
        List<LocalTime> departures = new ArrayList<>();

        for(Line line : timetableService.findLinesThrough(departure, destination)) {
            for(LocalTime time : timetableService.findArrivalTimes(line, departure)) {
                if(startTime.isBefore(time) && startTime.plusMinutes(period).isAfter(time)) {
                    departures.add(time);
                }
            }
        }

        return departures;
    }
}
