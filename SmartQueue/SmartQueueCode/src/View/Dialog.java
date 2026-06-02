package View;

import javax.swing.*;
import java.awt.*;

public class Dialog {

    private static final Color BACKGROUND = new Color(245, 247, 250);
    private static final Color CARD = Color.WHITE;
    private static final Color PRIMARY = new Color(37, 99, 235);
    private static final Color TEXT_DARK = new Color(15, 23, 42);
    private static final Color TEXT = new Color(30, 41, 59);
    private static final Color BORDER = new Color(203, 213, 225);

    public static void showSuccess(Component parent, String message) {
        showMessage(parent, "Success", message);
    }

    public static void showError(Component parent, String message) {
        showMessage(parent, "Error", message);
    }

    public static void showInfo(Component parent, String title, String message) {
        showMessage(parent, title, message);
    }

    private static void showMessage(Component parent, String title, String message) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setSize(520, 260);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(BACKGROUND);
        rootPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel cardPanel = new JPanel(new BorderLayout(0, 22));
        cardPanel.setBackground(CARD);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(26, 32, 24, 32)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(TEXT_DARK);

        JTextArea messageArea = new JTextArea(message);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        messageArea.setForeground(TEXT);
        messageArea.setBackground(CARD);
        messageArea.setEditable(false);
        messageArea.setFocusable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(null);

        JPanel textPanel = new JPanel(new BorderLayout(0, 12));
        textPanel.setBackground(CARD);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(messageArea, BorderLayout.CENTER);

        JButton okButton = createPrimaryButton("OK");
        okButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setBackground(CARD);
        buttonPanel.add(okButton);

        cardPanel.add(textPanel, BorderLayout.CENTER);
        cardPanel.add(buttonPanel, BorderLayout.SOUTH);

        rootPanel.add(cardPanel, BorderLayout.CENTER);

        dialog.setContentPane(rootPanel);
        dialog.setVisible(true);
    }

    public static String showInput(Component parent, String title, String message) {
        final String[] result = {null};

        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setSize(560, 300);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(BACKGROUND);
        rootPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel cardPanel = new JPanel(new BorderLayout(0, 20));
        cardPanel.setBackground(CARD);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(24, 28, 24, 28)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_DARK);

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        messageLabel.setForeground(TEXT);

        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputField.setForeground(TEXT);
        inputField.setBackground(Color.WHITE);
        inputField.setCaretColor(TEXT);
        inputField.setPreferredSize(new Dimension(440, 44));
        inputField.setMinimumSize(new Dimension(440, 44));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JPanel fieldPanel = new JPanel(new BorderLayout(0, 10));
        fieldPanel.setBackground(CARD);
        fieldPanel.add(messageLabel, BorderLayout.NORTH);
        fieldPanel.add(inputField, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout(0, 18));
        topPanel.setBackground(CARD);
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(fieldPanel, BorderLayout.CENTER);

        JButton okButton = createPrimaryButton("OK");
        JButton cancelButton = createSecondaryButton("Cancel");

        okButton.addActionListener(e -> {
            result[0] = inputField.getText();
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> {
            result[0] = null;
            dialog.dispose();
        });

        inputField.addActionListener(e -> {
            result[0] = inputField.getText();
            dialog.dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttonPanel.setBackground(CARD);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        cardPanel.add(topPanel, BorderLayout.CENTER);
        cardPanel.add(buttonPanel, BorderLayout.SOUTH);

        rootPanel.add(cardPanel, BorderLayout.CENTER);

        dialog.setContentPane(rootPanel);
        dialog.setVisible(true);

        return result[0];
    }

    private static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 28, 10, 28));
        return button;
    }

    private static JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(TEXT);
        button.setBackground(new Color(241, 245, 249));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(9, 24, 9, 24)
        ));
        return button;
    }
    public static void showLargeInfo(Component parent, String title, String message) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setSize(720, 500);
        dialog.setMinimumSize(new Dimension(650, 420));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(BACKGROUND);
        rootPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel cardPanel = new JPanel(new BorderLayout(0, 18));
        cardPanel.setBackground(CARD);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(24, 28, 24, 28)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_DARK);

        JTextArea messageArea = new JTextArea(message);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        messageArea.setForeground(TEXT);
        messageArea.setBackground(Color.WHITE);
        messageArea.setEditable(false);
        messageArea.setFocusable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JButton okButton = createPrimaryButton("OK");
        okButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setBackground(CARD);
        buttonPanel.add(okButton);

        cardPanel.add(titleLabel, BorderLayout.NORTH);
        cardPanel.add(scrollPane, BorderLayout.CENTER);
        cardPanel.add(buttonPanel, BorderLayout.SOUTH);

        rootPanel.add(cardPanel, BorderLayout.CENTER);

        dialog.setContentPane(rootPanel);
        dialog.setVisible(true);
    }
}