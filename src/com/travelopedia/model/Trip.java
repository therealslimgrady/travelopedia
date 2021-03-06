package com.travelopedia.model;

import java.util.ArrayList;
import java.util.Collection;

public class Trip {
    Long tripId = Long.valueOf(0);
    Long customerId = Long.valueOf(0);
    Collection<Flight> flights;

    // Hotel reservations TBD

    public Trip(long id, long customerId) {
        setTripId(Long.valueOf(id));
        setCustomerId(Long.valueOf(customerId));
        flights = new ArrayList<>();
    }

    public Trip(long id, long customerId, Collection<Flight> flights) {
        this(id, customerId);
        this.flights = flights;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void setCustomerId(Long id) {
        customerId = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = Long.valueOf(tripId);
    }

    public Collection<Flight> getFlights() {
        return this.flights;
    }

    public void setFlightIds(Collection<Flight> flightIds) {
        this.flights = flights;
    }

    @Override
    public String toString() {
        String result = "";
        for (Flight flight : getFlights()) {
            result += tripId + "," + customerId + "," + flight.getId() + "\n";
        }
        return result;
    }
}