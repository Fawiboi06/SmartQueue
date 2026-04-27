package model;

import java.util.ArrayList;
import java.util.List;

public class BookingManager{

    private List<Booking> bookings;

    public BookingManager(){
        bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        if (booking == null) {
            return;
        }

        if (booking.getDate() == null || booking.getDate().isBlank()
                || booking.getTime() == null || booking.getTime().isBlank()
                || booking.getUsername() == null || booking.getUsername().isBlank()) {
            return;
        }

        bookings.add(booking);
    }

    public List<Booking> getBookings(){
        return new ArrayList<>(bookings);
    }

    public boolean isEmpty() {
        return bookings.isEmpty();
    }
}
