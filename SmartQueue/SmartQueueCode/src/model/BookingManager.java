package model;

import java.util.ArrayList;
import java.util.List;

public class BookingManager{

    private List<Booking> bookings;

    public BookingManager(){
        bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking){
        if(booking != null){
            bookings.add(booking);
        }
    }

    public List<Booking> getBookings(){
        return bookings;
    }

    public boolean isEmpty() {
        return bookings.isEmpty();
    }
}
