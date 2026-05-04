package model;

import java.util.LinkedList;
import java.util.Queue;

public class QueueManager {
    private Queue<QueueItem> queue;

    public QueueManager() {
        queue = new LinkedList<>();
    }

    public boolean addCustomer(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }

        String cleanName = name.trim();

        if (cleanName.length() < 2) {
            return false;
        }

        if (customerAlreadyWaiting(cleanName)) {
            return false;
        }

        queue.add(new QueueItem(cleanName));
        return true;
    }

    public boolean customerAlreadyWaiting(String name) {
        for (QueueItem item : queue) {
            if (item.getCustomerName().equalsIgnoreCase(name.trim())) {
                return true;
            }
        }

        return false;
    }

    public QueueItem completeNextCustomer() {
        if (queue.isEmpty()) {
            return null;
        }

        return queue.poll();
    }

    public int getQueueSize() {
        return queue.size();
    }

    public String getNextCustomerName() {
        QueueItem next = queue.peek();

        if (next == null) {
            return "No customer waiting";
        }

        return next.getCustomerName();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public String getQueueInfo() {
        if (queue.isEmpty()) {
            return "Queue is empty.";
        }

        StringBuilder builder = new StringBuilder();
        int position = 1;

        builder.append("Current queue:\n");

        for (QueueItem item : queue) {
            builder.append(position)
                    .append(". ")
                    .append(item.getCustomerName())
                    .append("\n");
            position++;
        }

        builder.append("\nNext customer: ")
                .append(getNextCustomerName())
                .append("\nWaiting customers: ")
                .append(getQueueSize());

        return builder.toString();
    }
}