package Controller;

import View.GUImainBody;
import model.Booking;
import java.util.ArrayList;

public class ControllerAll {

    private final GUImainBody view;
    private ArrayList<Booking> bookings;
    private String username;

    public ControllerAll(String username) {
        this.view = new GUImainBody();
        this.username = username;
        this.bookings = new ArrayList<>();
        initController();
    }

    private void initController() {
        view.setVisible(true);
    }
    public void addBooking (String date) {
        Booking booking = new Booking(date, username);
        bookings.add(booking);
        System.out.println("Booking added for " + date);
    }
    public void showBookings() {
        for (Booking b : bookings) {
            System.out.println(b.getDate() +" - " + b.getUsername());
        }
    }
}