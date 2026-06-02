package View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ListView extends JDialog {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton deleteButton;
    private JButton closeButton;
    private TableRowSorter<DefaultTableModel> sorter;

    public ListView(JFrame parent, String title, String helpText, String[] columns, String[][] rows, boolean showDeleteButton) {
        super(parent, title, true);

        Style.applyGlobalStyle();

        setSize(950, 620);
        setMinimumSize(new Dimension(850, 520));
        setLocationRelativeTo(parent);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Style.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel card = Style.cardLayout(new BorderLayout(14, 14));

        JLabel titleLabel = Style.title(title);
        JLabel helpLabel = Style.smallText(helpText);

        JPanel topTextPanel = new JPanel();
        topTextPanel.setBackground(Color.WHITE);
        topTextPanel.setLayout(new BoxLayout(topTextPanel, BoxLayout.Y_AXIS));
        topTextPanel.add(titleLabel);
        topTextPanel.add(Box.createVerticalStrut(6));
        topTextPanel.add(helpLabel);

        searchField = new JTextField();
        searchField.setFont(Style.NORMAL_FONT);
        searchField.setForeground(Style.TEXT);
        searchField.setCaretColor(Style.TEXT);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Style.BORDER),
                BorderFactory.createEmptyBorder(9, 12, 9, 12)
        ));

        JPanel searchPanel = new JPanel(new BorderLayout(0, 6));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(Style.fieldLabel("Search"), BorderLayout.NORTH);
        searchPanel.add(searchField, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout(0, 16));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(topTextPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(rows, columns) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(34);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(241, 245, 249));
        table.getTableHeader().setForeground(Style.DARK);

        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(Style.BORDER));
        tableScrollPane.getViewport().setBackground(Color.WHITE);
        tableScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        deleteButton = Style.dangerButton("Delete selected");
        closeButton = Style.secondaryButton("Close");

        deleteButton.setVisible(showDeleteButton);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(deleteButton);
        buttonPanel.add(closeButton);

        card.add(topPanel, BorderLayout.NORTH);
        card.add(tableScrollPane, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        root.add(card, BorderLayout.CENTER);
        setContentPane(root);
    }

    private void filterTable() {
        String searchText = searchField.getText();

        if (searchText == null || searchText.isBlank()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }

    public int getSelectedNumber() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            return -1;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        String numberText = String.valueOf(tableModel.getValueAt(modelRow, 0));

        try {
            return Integer.parseInt(numberText);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getCloseButton() {
        return closeButton;
    }
}