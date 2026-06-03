package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class WaitingQueueManager {

    private Map<String, Queue<QueueItem>> waitingQueues;

    public WaitingQueueManager() {
        waitingQueues = new HashMap<>();
    }

    public boolean addToWaitingQueue(String date, String time, User user) {
        if (user == null) {
            return false;
        }

        return addToWaitingQueue(
                date,
                time,
                user.getUsername(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getEmail()
        );
    }

    public boolean addToWaitingQueue(String date, String time, String username,
                                     String fullName, String phoneNumber, String email) {

        if (isBlank(date) || isBlank(time) || isBlank(username)
                || isBlank(fullName) || isBlank(phoneNumber) || isBlank(email)) {
            return false;
        }

        String key = createKey(date, time);

        waitingQueues.putIfAbsent(key, new LinkedList<>());

        Queue<QueueItem> queue = waitingQueues.get(key);

        for (QueueItem item : queue) {
            if (item.getUsername().equalsIgnoreCase(username.trim())) {
                return false;
            }
        }

        queue.add(new QueueItem(
                username.trim(),
                fullName.trim(),
                phoneNumber.trim(),
                email.trim()
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

    public Map<String, List<QueueItem>> getWaitingQueuesSnapshot() {
        Map<String, List<QueueItem>> snapshot = new HashMap<>();

        for (String key : waitingQueues.keySet()) {
            Queue<QueueItem> queue = waitingQueues.get(key);

            if (queue == null || queue.isEmpty()) {
                continue;
            }

            snapshot.put(key, new ArrayList<>(queue));
        }

        return snapshot;
    }

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
                builder.append(" | Phone: ")
                        .append(item.getPhoneNumber())
                        .append(" | Email: ")
                        .append(item.getEmail());
            }

            builder.append("\n");
            position++;
        }

        return builder.toString();
    }

    private String createKey(String date, String time) {
        return date + " " + time;
    }

    private boolean isBlank(String text) {
        return text == null || text.isBlank();
    }
}