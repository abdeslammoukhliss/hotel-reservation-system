package org.example;

import org.example.entities.RoomType;
import org.example.entities.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            System.out.println("=== HOTEL RESERVATION SYSTEM TEST ===\n");

            // Create 3 rooms
            service.setRoom(1, 1000, RoomType.STANDARD_SUITE);
            service.setRoom(2, 2000, RoomType.JUNIOR_SUITE);
            service.setRoom(3, 3000, RoomType.DELUXE_SUITE);

            // Create 2 users
            service.setUser(1, 5000);
            service.setUser(2, 10000);
        } catch (Exception e) {
            System.out.println("Error in test execution: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            System.out.println("\n=== BOOKING ATTEMPTS ===");
            // User 1 tries booking Room 2 from 30/06/2026 to 07/07/2026
            Date date1 = sdf.parse("30/06/2026");
            Date date2 = sdf.parse("07/07/2026");
            service.bookRoom(1, 2, date1, date2);
        } catch (Exception e) {
            System.out.println("Error in test execution: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            // User 1 tries booking Room 2 from 07/07/2026 to 30/06/2026
            Date date3 = sdf.parse("07/07/2026");
            Date date4 = sdf.parse("30/06/2026");
            service.bookRoom(1, 2, date3, date4);
        } catch (Exception e) {
            System.out.println("Error in test execution: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            // User 1 tries booking Room 1 from 07/07/2026 to 08/07/2026
            Date date5 = sdf.parse("07/07/2026");
            Date date6 = sdf.parse("08/07/2026");
            service.bookRoom(1, 1, date5, date6);

        } catch (Exception e) {
            System.out.println("Error in test execution: " + e.getMessage());
            e.printStackTrace();
        }
            // User 2 tries booking Room 1 from 07/07/2026 to 09/07/2026 (2 nights)

         try {
             Date date7 = sdf.parse("07/07/2026");
            Date date8 = sdf.parse("09/07/2026");
            service.bookRoom(2, 1, date7, date8);
         } catch (Exception e) {
             System.out.println("Error in test execution: " + e.getMessage());
             e.printStackTrace();
         }
            // User 2 tries booking Room 3 from 07/07/2026 to 08/07/2026 (1 night)
            try {
            Date date9 = sdf.parse("07/07/2026");
            Date date10 = sdf.parse("08/07/2026");
            service.bookRoom(2, 3, date9, date10);
            } catch (Exception e) {
                System.out.println("Error in test execution: " + e.getMessage());
                e.printStackTrace();
            }

            try {
            // setRoom(1, suite, 10000)
            System.out.println("\n=== UPDATING ROOM 1 ===");
            service.setRoom(1, 10000, RoomType.DELUXE_SUITE);
            } catch (Exception e) {
                System.out.println("Error in test execution: " + e.getMessage());
                e.printStackTrace();
            }
            // Print results
            service.printAll();
            service.printAllUsers();


    }
}