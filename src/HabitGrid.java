import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class HabitGrid {
    static class HabitRow {
        String name;
        final boolean[] days = new boolean[7];

        HabitRow(String name) {this.name = name;}
    }

    static class HabitGridModel extends AbstractTableModel {
        private final List<HabitRow> rows = new ArrayList<>();
        private final String[] columnNames = {"Name", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        @Override
        public int getRowCount() {
            return rows.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 0 ? String.class : Boolean.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            HabitRow row = rows.get(rowIndex);
            return (columnIndex == 0) ? row.name : row.days[columnIndex - 1];
        }

        @Override
        public void setValueAt(Object v, int rowIndex, int columnIndex) {
            HabitRow row = rows.get(rowIndex);
            if (columnIndex == 0) {
                row.name = String.valueOf(v);
            } else {
                row.days[columnIndex - 1] = (boolean) v;
                fireTableCellUpdated(rowIndex, columnIndex);
            }
        }

        void addHabit(String name) {
            rows.add(new HabitRow(name));
            fireTableRowsInserted(rows.size()-1, rows.size()-1);
        }
        void removeHabit(int index) {
            rows.remove(index);
            fireTableRowsDeleted(index, index);
        }
    }
}
