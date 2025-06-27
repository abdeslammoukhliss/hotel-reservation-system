package org.example.services;

import org.example.entities.Booking;
import org.example.entities.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    private Service service;
    private SimpleDateFormat sdf;

    @BeforeEach
    void setup() {
        service = new Service();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Test
    void testSetUser_CreatesNewUser() {
        service.setUser(1, 5000);
        assertEquals(1, service.getUsers().size());
        assertEquals(5000, service.getUsers().get(0).getBalance());
    }

    @Test
    void testSetUser_DuplicateUserNotAdded() {
        service.setUser(1, 5000);
        service.setUser(1, 9999);
        assertEquals(1, service.getUsers().size());
    }

    @Test
    void testSetRoom_Success() throws Exception {
        service.setRoom(101, 1000, RoomType.STANDARD_SUITE);
        assertEquals(1, service.getRooms().size());
    }

    @Test
    void testSetRoom_DuplicateIdThrows() {
        assertThrows(RuntimeException.class, () -> {
            service.setRoom(101, 1000, RoomType.STANDARD_SUITE);
            service.setRoom(101, 1500, RoomType.JUNIOR_SUITE);
        });
    }

    @Test
    void testSetRoom_SameTypeSamePriceThrows() {
        assertThrows(Exception.class, () -> {
            service.setRoom(101, 2000, RoomType.JUNIOR_SUITE);
            service.setRoom(102, 2000, RoomType.JUNIOR_SUITE);
        });
    }

    @Test
    void testBookRoom_SuccessfulBooking() throws Exception {
        service.setUser(1, 10000);
        service.setRoom(101, 2000, RoomType.JUNIOR_SUITE);

        Date checkIn = sdf.parse("01/07/2026");
        Date checkOut = sdf.parse("03/07/2026");

        service.bookRoom(1, 101, checkIn, checkOut);

        assertEquals(1, service.getBookings().size());
        Booking booking = service.getBookings().get(0);
        assertEquals(1, booking.getUser().getId());
        assertEquals(101, booking.getRoom().getId());
    }

    @Test
    void testBookRoom_UserNotFound() {
        Exception exception = assertThrows(Exception.class, () -> {
            service.bookRoom(99, 101, new Date(), new Date());
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testBookRoom_RoomNotFound() throws Exception {
        service.setUser(1, 5000);
        Exception exception = assertThrows(Exception.class, () -> {
            service.bookRoom(1, 999, new Date(), new Date());
        });
        assertEquals("Room not found", exception.getMessage());
    }

    @Test
    void testBookRoom_NullDates() throws Exception {
        service.setUser(1, 5000);
        service.setRoom(101, 1000, RoomType.STANDARD_SUITE);
        Exception exception = assertThrows(Exception.class, () -> {
            service.bookRoom(1, 101, null, new Date());
        });
        assertEquals("Check-in and check-out dates cannot be null", exception.getMessage());
    }

    @Test
    void testBookRoom_InsufficientBalance() throws Exception {
        service.setUser(1, 1000); // not enough for 2 nights * 2000
        service.setRoom(101, 2000, RoomType.DELUXE_SUITE);
        Date checkIn = sdf.parse("01/07/2026");
        Date checkOut = sdf.parse("03/07/2026");

        Exception exception = assertThrows(Exception.class, () -> {
            service.bookRoom(1, 101, checkIn, checkOut);
        });

        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    void testBookRoom_OverlapThrows() throws Exception {
        service.setUser(1, 10000);
        service.setUser(2, 10000);
        service.setRoom(101, 1000, RoomType.STANDARD_SUITE);

        Date checkIn1 = sdf.parse("01/07/2026");
        Date checkOut1 = sdf.parse("03/07/2026");
        Date checkIn2 = sdf.parse("02/07/2026");
        Date checkOut2 = sdf.parse("04/07/2026");

        service.bookRoom(1, 101, checkIn1, checkOut1);

        Exception exception = assertThrows(Exception.class, () -> {
            service.bookRoom(2, 101, checkIn2, checkOut2);
        });

        assertEquals("Insufficient room availability", exception.getMessage());
    }
}
