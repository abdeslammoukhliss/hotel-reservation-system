package org.example.entities;

import java.util.Date;

public class Booking {
    private int roomNumber;
    private final User user;
    private final Room room;
    private int nights;
    private Date checkInDate;
    private  Date checkOutDate;

    public Booking(int id, User user, Room room, int nights) {
        this.roomNumber = id;
        this.user = new User(user.getId(), user.getBalance());
        this.room = new Room(room.getId(), room.getPricePerNight(), room.getRoomType());
        this.nights = nights;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public int getNights() {
        return nights;
    }
    public void setNights(int nights) {
        this.nights = nights;
    }
    public Date getCheckInDate() {
        return checkInDate;
    }
    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }
    public Date getCheckOutDate() {
        return checkOutDate;
    }
    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    @Override
    public String toString() {
        return "Booking{" +
                "user=" + user +
                ", room=" + room +
                ", checkIn=" + checkInDate +
                ", checkOut=" + checkInDate +
                '}';
    }
}
