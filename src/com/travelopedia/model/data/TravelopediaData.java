package com.travelopedia.model.data;

import com.travelopedia.model.Flight;
import com.travelopedia.model.Traveler;
import com.travelopedia.model.Trip;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Data
 * Gets data from a CSV file and holds maps of customers, flights, and trips.
 * Writes data to the CSV file. Should be invoked when a purchase is completed.
 */
public class TravelopediaData {

    static Map<Long, Traveler> customerList = new HashMap<>(); // customer data
    static Map<Long, Flight> flightList = new HashMap<>();  // flights available
    static Map<Long, Trip> tripList = new HashMap<>();      // booked travel
    static final String path = "src/com/travelopedia/model/data/";
    static final String customerFile = "customer.csv";
    static final String flightFile = "flights.csv";
    static final String tripFile = "trips.csv";

    public TravelopediaData() throws IOException {
        List<String> cList = new ArrayList<>();
        List<String> tList = new ArrayList<>();
        List<String> fList = new ArrayList<>();

        //open customer.csv and import to customerList
        cList = loadData(path + customerFile);
        loadCustomerData(cList);
        //open flights.csv and import to flightList
        fList = loadData(path + flightFile);
        loadFlightData(fList);
    }

    private static void loadCustomerData(List<String> cList) {
        for (String line : cList) {
            String[] tempList = line.split(",");
            Long id = Long.valueOf(tempList[0]);
            String fname = tempList[1];
            String lname = tempList[2];
            String email = "";
            String phone = tempList[3];
            String streetAddress = tempList[4];
            String city = tempList[5];
            String state = tempList[6];
            String zip = tempList[7];
            String ccNum = tempList[8];
            customerList.put(id, new Traveler(id, fname, lname, email, phone, streetAddress, city,
                    state, zip, ccNum, null));
        }

    }

    private static void loadFlightData(List<String> fList) {
        for (String line : fList) {
            String[] tempList = line.split(",");
            Long id = Long.valueOf(tempList[0]);
            String airline = tempList[1];
            String departureCity = tempList[2];
            String arrivalCity = tempList[3];
            String departureTime = tempList[4];
            String arrivalTime = tempList[5];
            String seatCapacity = tempList[6];
            String seatsAvailable = tempList[7];
            String seatsBooked = tempList[8];
            flightList.put(id, new Flight(id, airline, departureCity, arrivalCity, departureTime, arrivalTime, seatCapacity, seatsAvailable, seatsBooked));
        }
    }

    private static List<String> loadData(String file) throws IOException {
        return Files.readAllLines(FileSystems.getDefault().getPath(file));
    }

    public static Map<Long, Traveler> getCustomerList() {
        return customerList;
    }

    public static Map<Long, Flight> getFlightList() {
        return flightList;
    }

    public static Map<Long, Trip> getTripList() {
        return tripList;
    }

    public static void writeCustomerData() throws IOException {
        Files.writeString(Paths.get(path, customerFile), "XXXXXXXXXXXXXXXXXXXXXXX\n");
        for (Map.Entry<Long, Traveler> entry : getCustomerList().entrySet()) {
            Traveler thisCustomer = entry.getValue();
            Long id = thisCustomer.getId();
            String fname = thisCustomer.getFname();
            String lname = thisCustomer.getLname();
            String email = thisCustomer.getEmail();
            String phone = thisCustomer.getPhone();
            String streetAddress = thisCustomer.getStreetAddress();
            String city = thisCustomer.getCity();
            String state = thisCustomer.getState();
            String zip = thisCustomer.getZip();
            String ccNum = thisCustomer.getCcNum();
            String output = id + "," + fname + "," + lname  + "," + email + "," + phone + "," + streetAddress + "," +
                    city + "," + state + "," + zip + "," + ccNum + "\n";
            Files.writeString(Paths.get(path, customerFile), output);
        }
    }

    public static void writeFlightData() throws IOException {
        String result;
        for (Map.Entry<Long, Flight> entry : flightList.entrySet()) {
            Path fullPath = Paths.get(path, flightFile);
            System.out.println("About to");
            if (! Files.exists(fullPath)) {
                System.out.println("Trying");
                try {
                    Files.createFile(fullPath);
                } catch (IOException e) {
                    System.out.println("Path does not exist");
                }
            }
            Flight flight = entry.getValue();
            result = flight.dumpFlights();
            Files.writeString(Paths.get(path, flightFile), result);
        }
    }
}