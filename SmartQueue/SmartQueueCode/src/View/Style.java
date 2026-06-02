package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Style {

    public static final Color BACKGROUND = new Color(245, 247, 250);
    public static final Color CARD = Color.WHITE;
    public static final Color PRIMARY = new Color(37, 99, 235);
    public static final Color PRIMARY_DARK = new Color(30, 64, 175);
    public static final Color DARK = new Color(15, 23, 42);
    public static final Color TEXT = new Color(30, 41, 59);
    public static final Color MUTED = new Color(100, 116, 139);
    public static final Color BORDER = new Color(203, 213, 225);
    public static final Color DANGER = new Color(220, 38, 38);
    public static final Color SUCCESS = new Color(22, 163, 74);

    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 34);
    public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 15);

    public static void applyGlobalStyle() {
        UIManager.put("Button.font", BUTTON_FONT);
        UIManager.put("Label.font", NORMAL_FONT);
        UIManager.put("TextField.font", NORMAL_FONT);
        UIManager.put("PasswordField.font", NORMAL_FONT);
        UIManager.put("TextArea.font", NORMAL_FONT);
        UIManager.put("List.font", NORMAL_FONT);
        UIManager.put("ComboBox.font", NORMAL_FONT);
        UIManager.put("OptionPane.messageFont", NORMAL_FONT);
        UIManager.put("OptionPane.buttonFont", BUTTON_FONT);
    }

    public static JButton primaryButton(String text) {
        JButton button = baseButton(text);
        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        return button;
    }

    public static JButton secondaryButton(String text) {
        JButton button = baseButton(text);
        button.setBackground(new Color(241, 245, 249));
        button.setForeground(TEXT);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        return button;
    }

    public static JButton dangerButton(String text) {
        JButton button = baseButton(text);
        button.setBackground(DANGER);
        button.setForeground(Color.WHITE);
        return button;
    }

    private static JButton baseButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(13, 22, 13, 22));
        return button;
    }

    public static JPanel cardLayout(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(CARD);
        panel.setBorder(cardBorder());
        return panel;
    }

    public static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(30, 42, 30, 42)
        );
    }

    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setForeground(DARK);
        return label;
    }

    public static JLabel subtitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SUBTITLE_FONT);
        label.setForeground(DARK);
        return label;
    }

    public static JLabel smallText(String text) {
        JLabel label = new JLabel(text);
        label.setFont(NORMAL_FONT);
        label.setForeground(MUTED);
        return label;
    }

    public static JLabel fieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(DARK);
        return label;
    }

    public static void styleTextField(JTextField field) {
        field.setFont(NORMAL_FONT);
        field.setForeground(TEXT);
        field.setBackground(Color.WHITE);
        field.setCaretColor(TEXT);

        field.setPreferredSize(new Dimension(520, 46));
        field.setMinimumSize(new Dimension(520, 46));

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }

    public static void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(NORMAL_FONT);
        comboBox.setForeground(TEXT);
        comboBox.setBackground(Color.WHITE);

        comboBox.setPreferredSize(new Dimension(520, 46));
        comboBox.setMinimumSize(new Dimension(520, 46));
    }

    public static void styleTextArea(JTextArea area) {
        area.setFont(NORMAL_FONT);
        area.setForeground(TEXT);
        area.setBackground(new Color(248, 250, 252));
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
    }
}