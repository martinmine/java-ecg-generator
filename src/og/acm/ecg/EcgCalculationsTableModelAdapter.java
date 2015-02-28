package og.acm.ecg;

import javax.swing.table.DefaultTableModel;

/**
 * Adapter between the measurements in the EcgCalc and the Swing table.
 */
public class EcgCalculationsTableModelAdapter extends DefaultTableModel {
    private static final Class[] types = new Class[]{String.class, String.class, String.class};
    private EcgCalc ecgCalc;

    public EcgCalculationsTableModelAdapter(EcgCalc ecgCalc) {
        super(new Object[][]{}, new String[]{"Time", "Voltage", "Peak"});
        this.ecgCalc = ecgCalc;
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }

    @Override
    public int getRowCount() {
        if (ecgCalc == null) return 0;
        return ecgCalc.getEcgResultNumRows();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int row, int column) {
         switch (column) {
             case 0: return String.valueOf(this.ecgCalc.getEcgResultTime(row));
             case 1: return String.valueOf(this.ecgCalc.getEcgResultVoltage(row));
             case 2: return String.valueOf(this.ecgCalc.getEcgResultPeak(row));
             default: return null;
         }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
