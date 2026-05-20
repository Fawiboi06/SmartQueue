package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookingManager {

    private List<Booking> bookings;

    public BookingManager() {
        bookings = new ArrayList<>();
    }

    public boolean addBooking(Booking booking) {
        if(!isValidBooking(booking)){
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
        for (Booking booking : bookings) {
            if (booking.getDate().equals(date) && booking.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }

    public Booking getBookingAt(String date, String time) {
        for (Booking booking : bookings) {
            if (booking.getDate().equals(date) && booking.getTime().equals(time)) {
                return booking;
            }
        }
        return null;
    }

    public List<Booking> getBookingsForDate(String date) {
        List<Booking> result = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getDate().equals(date)) {
                result.add(booking);
            }
        }

        result.sort(Comparator.comparing(Booking::getTime));
        return result;
    }

    public List<Booking> getBookingsForUser(String username){
        List<Booking> result = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getUsername().equalsIgnoreCase(username)) {
                result.add(booking);
            }
        }

        result.sort(Comparator.comparing(b -> b.getDate() + " " + b.getTime()));
        return result;
    }

    public Booking getBookingByNumberForUser(int number, String username) {
        List<Booking> userBookings = getBookingsForUser(username);
        if (number < 1 || number > userBookings.size()) {
            return null;
        }
        return userBookings.get(number - 1);
    }

    public Booking getBookingByNumberForAdmin(int number) {
        List<Booking> allBookings = getBookings();
        if (number < 1 || number > allBookings.size()) {
            return null;
        }
        return allBookings.get(number - 1);
    }

    public boolean removeBooking(Booking booking) {
        if (booking == null) {
            return false;
        }

        return bookings.remove(booking);
    }

    public List<Booking> getBookings() {
        List<Booking> copy = new ArrayList<>(bookings);
        copy.sort(Comparator.comparing(b -> b.getDate() + " " + b.getTime()));
        return copy;
    }

    public boolean isEmpty() {
        return bookings.isEmpty();
    }

    private boolean isValidBooking(Booking booking) {
        if (booking == null) {
            return false;
        }

        if (isBlank(booking.getDate()) ||
                isBlank(booking.getTime()) ||
                isBlank(booking.getUsername()) ||
                isBlank(booking.getFullName())) {
            return false;
        }

        return booking.getUsername().trim().length() >= 2;
    }

    private void sortBookings() {
        bookings.sort(Comparator.comparing(b -> b.getDate() + " " + b.getTime()));
    }

    private boolean isBlank(String text) {
        return text == null || text.isBlank();
    }
}





