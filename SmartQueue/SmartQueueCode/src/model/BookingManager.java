package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

        if (booking.getUsername().trim().length() < 2) {
            return false;
        }

        if (hasBookingAt(booking.getDate(), booking.getTime())) {
            return false;
        }

        bookings.add(booking);
        sortBookings();
        return true;
    }

    public boolean hasBookingAt(String date, String time) {
        for (Booking b : bookings) {
            if (b.getDate().equals(date) && b.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Booking> getBookingsForDate(String date) {
        ArrayList<Booking> result = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getDate().equals(date)) {
                result.add(booking);
            }
        }

        return result;
    }

    public boolean removeBookingByNumber(int number) {
        if (number < 1 || number > bookings.size()) {
            return false;
        }

        bookings.remove(number - 1);
        return true;
    }

    public Booking getBookingByNumber(int number) {
        if (number < 1 || number > bookings.size()) {
            return null;
        }

        return bookings.get(number - 1);
    }

    public int getBookingCount() {
        return bookings.size();
    }

    private void sortBookings() {
        Collections.sort(bookings, new Comparator<Booking>() {
            @Override
            public int compare(Booking b1, Booking b2) {
                String first = b1.getDate() + " " + b1.getTime();
                String second = b2.getDate() + " " + b2.getTime();
                return first.compareTo(second);
            }
        });
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