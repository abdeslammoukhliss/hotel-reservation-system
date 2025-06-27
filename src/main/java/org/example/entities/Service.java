package org.example.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Service {
    List<User> users = new ArrayList<>() ;
    List<Room> rooms = new ArrayList<>();
    List<Booking> bookings = new  ArrayList<>();

    public void setRoom(int roomNumber, int pricePerNight, RoomType roomType) throws Exception {

       for (Room room : rooms) {
           if (roomNumber == room.getId()) {
               try {
                   throw new Exception("Room already Exist");
               } catch (Exception e) {
                   throw new RuntimeException(e);
               }

           }
           else if (room.getRoomType() == roomType && room.getPricePerNight() == pricePerNight) {
                throw new Exception("Two rooms can have the same type but different prices per night");

           }
       }
       rooms.add(new Room(roomNumber,pricePerNight, roomType));
    }
    public void setUser(int userId, int balance) {
        User user =  null;
        if (findUserById(userId) == null) {
            user = new User(userId, balance);
            users.add(user);
            System.out.println("SUCCESS: User " + userId + " added" );
        }
    }
    public void bookRoom(int userId,int roomNumber, Date checkIn, Date checkOut) throws Exception {
        User user = findUserById(userId);
        if (user == null) {
            throw new Exception("User not found");

        }
        Room room = findRoomById(roomNumber);
        if (room == null) {
            throw new Exception("Room not found");

        }
        if (checkIn == null || checkOut == null) {
            throw new Exception("Check-in and check-out dates cannot be null");
        }
        int nights = (int) ((checkOut.getTime() - checkIn.getTime()) / (1000 * 60 * 60 * 24));
        int totalCost = nights * room.getPricePerNight();

        if (user.getBalance() < totalCost) {
            throw new Exception("Insufficient balance");
        }
        if (!checkRoomAvailability(roomNumber, checkIn, checkOut)) {
            throw new Exception("Insufficient room availability");
        }
        if (checkIn.after(checkOut) || checkIn.equals(checkOut)) {
            throw new Exception("BOOKING FAILED: Invalid dates - Check-in must be before check-out");
        }

        user.setBalance(user.getBalance() - totalCost);
        Booking booking = new Booking(bookings.size() + 1, user, room, nights);
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut);
        bookings.add(booking);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("SUCCESS: Booking confirmed! Room " + roomNumber +
                " booked from " + sdf.format(checkIn) + " to " + sdf.format(checkOut) +
                " for " + nights + " nights. Total cost: " + totalCost +
                ". Remaining balance: " + user.getBalance());

    }

    public void printAll() {
        System.out.println("\n--- Rooms (latest to oldest) ---");
        for (Room r : rooms) {
            System.out.println(r);
        }

        System.out.println("\n--- Bookings (latest to oldest) ---");
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }
    public void printAllUsers() {
        System.out.println("\n--- Users (latest to oldest) ---");
        for (User user : users) {
            System.out.println(user);
        }
    }


    private User findUserById(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }
    private Room findRoomById(int roomId) {
        for (Room room : rooms) {
            if (room.getId() == roomId) {
                return room;
            }
        }
        return null;
    }
    private boolean checkRoomAvailability(int roomNumber, Date checkIn, Date checkOut) {
        for (Booking booking : bookings) {
            if (booking.getRoom().getId() == roomNumber) {
                Date existingCheckIn = booking.getCheckInDate();
                Date existingCheckOut = booking.getCheckOutDate();

                boolean overlap = checkIn.before(existingCheckOut) && existingCheckIn.before(checkOut);
                if (overlap) {
                    return false;
                }
            }
        }
        return true;
    }
    public List<Booking> getBookings() {
        return bookings;
    }
    public List<User> getUsers() {
        return users;
    }
    public List<Room> getRooms() {
        return rooms;
    }
}
