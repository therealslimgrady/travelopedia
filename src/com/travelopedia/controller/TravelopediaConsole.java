package com.travelopedia.controller;

import com.travelopedia.model.Airport;
import com.travelopedia.model.Flight;
import com.travelopedia.model.data.TravelopediaData;
import com.travelopedia.view.MainMenu;
import com.travelopedia.view.SearchMenu;
import com.travelopedia.view.ViewMenu;

import java.util.List;
import java.util.Scanner;

public class TravelopediaConsole {
    MainMenu mainMenu;
    SearchMenu searchMenu;
    ViewMenu viewMenu;

    public TravelopediaConsole() {
        mainMenu = new MainMenu();
    }

    public void consoleStart() {
        mainMenu.displayMenu();
        String choice = getInput().toLowerCase();
        while (!choice.equals("x")) {
            switch (choice) {
                case "s":
                    searchMenu = new SearchMenu();
                    searchMenu.doMenu();
                    choice = getInput().toLowerCase();
                    break;
                case "v":
                    viewMenu = new ViewMenu();
                    viewMenu.doMenu();
                    choice = getInput().toLowerCase();
                    break;
                case "d":
                    runSearch();
                    choice = "m";
                    break;
                case "e":
                    Long customerId = TravelerAccess.getCustIdByEmail();
                    List<Flight> flights = TripAccess.getAllCustomerTripsList(customerId);
                    Long flightID = viewMenu.chooseFlight(flights);
                    if (flightID.equals(0L)) {
                        System.out.println("\nSorry, no flights were found for this email address.\n");
                    } else {
                        printFlightDetails(flightID);
                    }
                    choice = nextChoice();
                    break;
                case "i":
                    viewMenu.promptForItinerary();
                    Long viewFlightID = Long.parseLong(getInput());
                    printFlightDetails(viewFlightID);
                    choice = nextChoice();
                    break;
                default:
                    choice = nextChoice();
            }
        }
        System.out.println("Thank you for traveling with Travelopedia!");
        new TravelopediaData().record();
    }

    public void runSearch() {
        Airport depart = null;
        String departureLoc = searchMenu.getDepartureLoc();
        try {
            depart = Airport.valueOf(departureLoc);
        } catch (IllegalArgumentException e) {
            System.out.println("Sorry, we do not serve that location. Pick another, please.");
            searchMenu.doMenu();
        }
        String input = searchMenu.getDate();
        if (input.equals("x")) {
            return;
        }
        searchMenu.getSearchInfo(input, depart);
    }

    private void printFlightDetails(Long flightID) {
        System.out.println(FlightsAccess.getPrintableItinerary(flightID));
    }

    private String nextChoice() {
        String choice;
        System.out.println("\n\n\nTRAVELOPEDIA\n");
        mainMenu.displayMenu();
        System.out.println("What would you like to do next? ");
        choice = getInput().toLowerCase();
        return choice;
    }

    private String getInput() {
        System.out.print("> ");
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }
}