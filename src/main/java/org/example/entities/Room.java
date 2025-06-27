package org.example.entities;

public class Room {
    private int roomNumber;
    private int pricePerNight;
    private  RoomType roomType;
    public Room(int id, int pricePerNight, RoomType roomType) {
        this.roomNumber = id;
        this.pricePerNight = pricePerNight;
        this.roomType = roomType;
    }
    public int getPricePerNight() {
        return pricePerNight;
    }
    public  void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }
    public RoomType getRoomType() {
        return roomType;
    }
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
    public int getId() {
        return roomNumber;
    }
    public void setId(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", type=" + roomType +
                ", pricePerNight=" + pricePerNight +
                '}';
    }


}
