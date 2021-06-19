package com.travelopedia.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.junit.Assert.*;

public class TripTest {
    public Trip trip;
    public List<Flight.Leg> flightLegs;
    public Flight flight, flight1;
    public Map<LocalDateTime, List<String>> itinerary;

    @Before
    public void initialize() {
        trip = new Trip(1L);
        flight1 = new Flight();
        flight1.addLeg(
                LocalDateTime.of(2021, Month.MAY, 20, 17, 50),
                LocationCode.DEN,
                LocalDateTime.of(2021, Month.MAY, 20, 22, 15),
                LocationCode.CLE, Airline.SERVICEABLE,
                349.0);
        flight1.addLeg(
                LocalDateTime.of(2021, Month.MAY, 22, 9, 15),
                LocationCode.CLE,
                LocalDateTime.of(2021, Month.MAY, 22, 14, 0),
                LocationCode.DEN,
                Airline.SERVICEABLE,
                150.0);

        trip.addFlight(flight1);

        itinerary = new TreeMap<>();
        List<String> leg1 = new ArrayList<>();
        List<String> leg2 = new ArrayList<>();

        leg1.add("2021-05-20T17:50");
        leg1.add("Denver International");
        leg1.add("2021-05-20T22:15");
        leg1.add("Cleveland Hopkins International");
        leg1.add("Serviceable Airlines");
        leg1.add("349.0");

        leg2.add("2021-05-22T09:15");
        leg2.add("Cleveland Hopkins International");
        leg2.add("2021-05-22T14:00");
        leg2.add("Denver International");
        leg2.add("Serviceable Airlines");
        leg2.add("150.0");


        itinerary.put(LocalDateTime.of(2021, Month.MAY, 20, 17, 50), leg1);
        itinerary.put(LocalDateTime.of(2021, Month.MAY, 22, 9, 15), leg2);
    }

    @Test
    public void tripContainsCorrectNumberOfFlights() {
        Collection<Flight> flights = new ArrayList<>();
        flights.add(flight1);
        assertEquals(flights.size(), trip.getFlights().size());
    }

    @Test
    public void tripContainsCorrectFlightInfo() {
        for (Flight flight : trip.getFlights()) {
            assertArrayEquals(flight1.getItinerary().entrySet().toArray(), flight.getItinerary().entrySet().toArray());
        }
    }

}