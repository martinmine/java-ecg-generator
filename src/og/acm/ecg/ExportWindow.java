package og.acm.ecg;/*
 * EcgExportWindow.java
 *
 * See EcgLicense.txt for License terms.
 */

/**
 *
 * @author Mauricio Villarroel (m.villarroel@acm.og)
 */

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportWindow extends javax.swing.JDialog {
    private javax.swing.JRadioButton csvRB;
    private javax.swing.JTextArea exportFile;
    private javax.swing.JRadioButton tabRB;
    private javax.swing.JTextField txtOtherChar;

    private final EcgCalc calcOb;
    private File file;
    /**
     * Creates new form EcgExportWindow
     */
    public ExportWindow(java.awt.Frame parent, boolean modal, EcgParameters parameters, EcgCalc ecgCalcOb, LogWindow logOb) {
        super(parent, modal);
        initComponents();
        EcgParameters paramOb = parameters;
        calcOb = ecgCalcOb;
        LogWindow ecgLog = logOb;
        initWindow();
    }

    private void initWindow() {
        this.setSize(380, 345);
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {
        JPanel mainPanel = new JPanel();
        JPanel exportFormatPanel = new JPanel();
        csvRB = new javax.swing.JRadioButton();
        tabRB = new javax.swing.JRadioButton();
        JRadioButton otherRB = new JRadioButton();
        txtOtherChar = new javax.swing.JTextField();
        JLabel jLabel1 = new JLabel();
        JButton browseButton = new JButton();
        JButton exportButton = new JButton();
        JButton closeButton = new JButton();
        exportFile = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        mainPanel.setLayout(null);

        exportFormatPanel.setLayout(null);

        exportFormatPanel.setBorder(new javax.swing.border.TitledBorder("Choose export format: "));
        csvRB.setSelected(true);
        csvRB.setText("Comma Separated Value (CSV)");
        exportFormatPanel.add(csvRB);
        csvRB.setBounds(20, 20, 230, 23);

        tabRB.setText("Tab Separated");
        exportFormatPanel.add(tabRB);
        tabRB.setBounds(20, 50, 170, 23);

        otherRB.setText("Other Character:");
        exportFormatPanel.add(otherRB);
        otherRB.setBounds(20, 80, 120, 23);

        exportFormatPanel.add(txtOtherChar);
        txtOtherChar.setBounds(140, 80, 20, 21);

        mainPanel.add(exportFormatPanel);
        exportFormatPanel.setBounds(10, 10, 340, 120);

        jLabel1.setText("Export Path:");
        mainPanel.add(jLabel1);
        jLabel1.setBounds(13, 150, 120, 15);

        browseButton.setText("Browse...");
        browseButton.addActionListener(ExportWindow.this::browseButtonActionPerformed);

        mainPanel.add(browseButton);
        browseButton.setBounds(247, 140, 100, 25);

        exportButton.setText("Export Data");
        exportButton.addActionListener(ExportWindow.this::exportButtonActionPerformed);

        mainPanel.add(exportButton);
        exportButton.setBounds(140, 280, 120, 25);

        closeButton.setText("Close");
        closeButton.addActionListener(ExportWindow.this::CloseButtonActionPerformed);

        mainPanel.add(closeButton);
        closeButton.setBounds(270, 280, 73, 25);

        exportFile.setEditable(false);
        exportFile.setLineWrap(true);
        mainPanel.add(exportFile);
        exportFile.setBounds(13, 173, 333, 90);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        pack();
    }

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {
        closeWindow();
    }

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
        if (file != null) {
            char car;
            /* lets select the column separator */
            if (csvRB.isSelected()) {
                /* Comma separated value file */
                car = ',';

            } else if (tabRB.isSelected()) {
                /* Tab separated value file */
                car = '\t';

            } else {
                /* Other separator */
                car = txtOtherChar.getText().charAt(0);
            }

            try {
                FileWriter fw = new FileWriter(file);
                fw.write("Time" + car + "Voltage" + car + "Peak\r\n");
                for (int i = 0; i < calcOb.getEcgResultNumRows(); i++) {
                    fw.write(Double.toString(calcOb.getEcgResultTime(i)) + car + Double.toString(calcOb.getEcgResultVoltage(i)) + car + Integer.toString(calcOb.getEcgResultPeak(i)) + "\r\n");
                }
                fw.close();
                JOptionPane.showMessageDialog(this, "it was generated " + calcOb.getEcgResultNumRows() + " rows.\nFile was saved successfully!");
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        } else {
            JOptionPane.showMessageDialog(this, "You have to choose a file to save firs!");
        }
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
        closeWindow();
    }

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser c = new JFileChooser();
        /* Open "Save" dialog: */
        int rVal = c.showSaveDialog(this);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            file = c.getSelectedFile();
            exportFile.setText(file.getAbsolutePath());
        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
            //exportPath.setText(null);
            exportFile.setText(null);
        }
    }

    private void closeWindow() {
        this.dispose();
    }
}