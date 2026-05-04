package model;
public class QueueItem {
    private final String customerName;

    public QueueItem(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String toString() {
        return customerName;
    }
}
