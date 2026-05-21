package model;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class WaitingQueueManager {


    private Map<String, Queue<QueueItem>> waitingQueues;


    public WaitingQueueManager() {
        waitingQueues = new HashMap<>();
    }

    public boolean addToWaitingQueue(String date, String time, User user) {
        if (date == null || date.isBlank() || time == null || time.isBlank() || user == null) {
            return false;
        }

        String key = createKey(date, time);

        waitingQueues.putIfAbsent(key, new LinkedList<>());

        Queue<QueueItem> queue = waitingQueues.get(key);

        for (QueueItem item : queue) {
            if (item.getUsername().equalsIgnoreCase(user.getUsername())) {
                return false;
            }
        }

        queue.add(new QueueItem(
                user.getUsername(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getEmail()
        ));
        return true;
    }

    public QueueItem getNextInQueue(String date, String time) {
        String key = createKey(date, time);

        Queue<QueueItem> queue = waitingQueues.get(key);
        if (queue == null || queue.isEmpty()) {
            return null;
        }

        return queue.poll();
    }

    public int getQueuePosition(String date, String time, String username) {
        String key = createKey(date, time);

        Queue<QueueItem> queue = waitingQueues.get(key);

        if (queue == null || queue.isEmpty()) {
            return -1;
        }

        int position = 1;

        for (QueueItem item : queue) {
            if (item.getUsername().equalsIgnoreCase(username)) {
                return position;
            }

            position++;
        }

        return -1;
    }

    public boolean isUserWaiting(String date, String time, String username) {
        return getQueuePosition(date, time, username) != -1;
    } // Kolla om den behövs implementeras eller raderas

    public String getQueueInfoForTime(String date, String time, boolean admin) {
        String key = createKey(date, time);

        Queue<QueueItem> queue = waitingQueues.get(key);

        if (queue == null || queue.isEmpty()) {
            return "No waiting queue for this time.";
        }

        StringBuilder builder = new StringBuilder();

        builder.append("Waiting queue for ")
                .append(date)
                .append(" ")
                .append(time)
                .append(":\n");

        int position = 1;

        for (QueueItem item : queue) {
            builder.append(position)
                    .append(". ")
                    .append(item.getFullName())
                    .append(" (")
                    .append(item.getUsername())
                    .append(")");

            if (admin) {
                builder.append(" | Phone: ").append(item.getPhoneNumber());
            }

            builder.append("\n");
            position++;
        }

        return builder.toString();
    }

    public String getAllQueuesInfo(boolean admin) {
        if (waitingQueues.isEmpty()) {
            return "No waiting queues.";
        }

        StringBuilder builder = new StringBuilder();

        for (String key : waitingQueues.keySet()) {
            Queue<QueueItem> queue = waitingQueues.get(key);

            if (queue == null || queue.isEmpty()) {
                continue;
            }

            builder.append(key).append("\n");

            int position = 1;

            for (QueueItem item : queue) {
                builder.append("  ")
                        .append(position)
                        .append(". ")
                        .append(item.getFullName())
                        .append(" (")
                        .append(item.getUsername())
                        .append(")");

                if (admin) {
                    builder.append(" | Phone: ").append(item.getPhoneNumber());
                }

                builder.append("\n");
                position++;
            }

            builder.append("\n");
        }

        if (builder.length() == 0) {
            return "No waiting queues.";
        }

        return builder.toString();
    }

    public String getUserQueueInfo(String username) {
        StringBuilder builder = new StringBuilder();

        for (String key : waitingQueues.keySet()) {
            Queue<QueueItem> queue = waitingQueues.get(key);

            if (queue == null || queue.isEmpty()) {
                continue;
            }

            int position = 1;

            for (QueueItem item : queue) {
                if (item.getUsername().equalsIgnoreCase(username)) {
                    builder.append(key)
                            .append(" | Queue position: ")
                            .append(position)
                            .append("\n");
                }

                position++;
            }
        }

        if (builder.length() == 0) {
            return "You are not waiting for any time.";
        }

        return builder.toString();
    }

    private String createKey(String date, String time) {
        return date + " " + time;
    }
}
