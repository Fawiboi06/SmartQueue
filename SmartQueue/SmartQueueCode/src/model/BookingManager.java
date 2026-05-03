package model;

import java.util.ArrayList;
import java.util.List;

public class BookingManager {

    private List<Booking> bookings;

    public BookingManager() {
        bookings = new ArrayList<>();
    }

    public boolean addBooking(Booking booking) {
        if (booking == null) {
            return false;
        }

        if (booking.getDate() == null || booking.getDate().isBlank()
                || booking.getTime() == null || booking.getTime().isBlank()
                || booking.getUsername() == null || booking.getUsername().isBlank()) {
            return false;
        }

        for (Booking b : bookings) {
            if (b.getDate().equals(booking.getDate()) &&
                    b.getTime().equals(booking.getTime())) {
                return false;
            }
        }

        bookings.add(booking);
        return true;
    }

    public boolean removeFirstBooking() {
        if (bookings.isEmpty()) {
            return false;
        }

        bookings.remove(0);
        return true;
    }

    public boolean removeBooking(Booking booking) {
        if (booking == null) {
            return false;
        }

        return bookings.remove(booking);
    }

    public List<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }

    public boolean isEmpty() {
        return bookings.isEmpty();
    }
}