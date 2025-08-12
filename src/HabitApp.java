import javax.swing.*;
import java.awt.*;

public class HabitApp {
    private void initUI() {
        //Window
        JFrame frame = new JFrame("Habit Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(8, 8));

        //Top panel
        JPanel top = new JPanel(new BorderLayout(8,0));
        top.setBorder(BorderFactory.createEmptyBorder(10,12,6,12));
        //Title
        JLabel title = new JLabel("HABIT TRACKER");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        //Date
        String today = java.time.LocalDate.now().toString();
        JLabel date = new JLabel(today);
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        datePanel.add(new JLabel("Date"));
        datePanel.add(date);
        //Top panel
        top.add(title, BorderLayout.WEST);
        top.add(datePanel, BorderLayout.EAST);
        frame.add(top, BorderLayout.NORTH);

        // Table
        HabitGrid.HabitGridModel grid = new HabitGrid.HabitGridModel();
        JTable table = new JTable(grid);

        // Header: white, bold, no bevel, centered text
        table.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                var c = (JLabel) super.getTableCellRendererComponent(tbl, value, false, false, row, column);
                c.setFont(c.getFont().deriveFont(Font.BOLD, 14f));
                c.setHorizontalAlignment(SwingConstants.CENTER);
                c.setBackground(Color.WHITE);
                c.setOpaque(true);
                c.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8)); // removes grey/bevel border look
                return c;
            }
        });

        // Rows
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(20, 0));
        table.setShowGrid(false);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setBackground(Color.WHITE);
        table.setOpaque(true);

        // Column widths (simple)
        table.getColumnModel().getColumn(0).setPreferredWidth(200); // Name
        for (int c = 1; c < table.getColumnCount(); c++) {
            table.getColumnModel().getColumn(c).setPreferredWidth(110); // Days
        }

        table.setFillsViewportHeight(true);

        // Use the SAME scrollPane you configure (white viewport)
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        frame.add(scrollPane, BorderLayout.CENTER);


        //Bottom panel
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        JTextField newHabitField = new JTextField(18);
        //Add habit
        JButton addButton = new JButton("Add");
        bottom.add(new JLabel("Habit:"));
        bottom.add(newHabitField);
        bottom.add(addButton);
        //Remove habit
        JButton removeButton = new JButton("Remove Row");
        bottom.add(removeButton);
        //Bottom panel
        frame.add(bottom, BorderLayout.SOUTH);

        //Actions
        addButton.addActionListener(e -> {
            String name = newHabitField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter a habit name");
                return;
            }
            grid.addHabit(name);
            newHabitField.setText("");
        });

        newHabitField.addActionListener(e -> addButton.doClick());

        removeButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(frame, "Select a row to remove");
                return;
            }
            grid.removeHabit(row);
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new HabitApp().initUI();
    }
}
