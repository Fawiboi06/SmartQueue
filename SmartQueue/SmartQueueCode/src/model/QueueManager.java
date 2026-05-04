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

        queue.add(new QueueItem(name.trim()));
        return true;
    }

    public QueueItem completeNextCustomer() {
        if (queue.isEmpty()) {
            return null;
        }

        return queue.poll();
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

        for (QueueItem item : queue) {
            builder.append(position)
                    .append(". ")
                    .append(item.getCustomerName())
                    .append("\n");
            position++;
        }

        return builder.toString();
    }
}
