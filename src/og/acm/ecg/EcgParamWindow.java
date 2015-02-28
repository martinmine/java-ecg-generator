package og.acm.ecg;/*
 * paramWindow.java
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

public class EcgParamWindow extends JInternalFrame {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ExtremaLabelScrollPane;
    private javax.swing.JTable ExtremaLabelTable;
    private javax.swing.JScrollPane aiScrollPane;
    private javax.swing.JTable aiTable;
    private javax.swing.JButton applyButton;
    private javax.swing.JScrollPane biScrollPane;
    private javax.swing.JTable biTable;
    private javax.swing.JButton closeParamDialogButton;
    private javax.swing.JPanel extremaPanel;
    private javax.swing.JPanel generalInterfacePanel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblANoise;
    private javax.swing.JLabel lblAmplitude;
    private javax.swing.JLabel lblFHi;
    private javax.swing.JLabel lblFHiStd;
    private javax.swing.JLabel lblFLo;
    private javax.swing.JLabel lblFLoStd;
    private javax.swing.JLabel lblGeneralTitle;
    private javax.swing.JLabel lblHrMean;
    private javax.swing.JLabel lblHrStd;
    private javax.swing.JLabel lblLfHfRatio;
    private javax.swing.JLabel lblMorphologyTitle;
    private javax.swing.JLabel lblN;
    private javax.swing.JLabel lblSeed;
    private javax.swing.JLabel lblSf;
    private javax.swing.JLabel lblSfEcg;
    private javax.swing.JLabel lblSpectralTitle;
    private javax.swing.JTabbedPane paramTabbedPane;
    private javax.swing.JButton resetParamDialogButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JPanel spectralCharacteristicsPanel;
    private javax.swing.JScrollPane tiScrollPane;
    private javax.swing.JTable tiTable;
    private javax.swing.JTextField txtANoise;
    private javax.swing.JTextField txtAmplitude;
    private javax.swing.JTextField txtFHi;
    private javax.swing.JTextField txtFHiStd;
    private javax.swing.JTextField txtFLo;
    private javax.swing.JTextField txtFLoStd;
    private javax.swing.JTextField txtHrMean;
    private javax.swing.JTextField txtHrStd;
    private javax.swing.JTextField txtLfHfRatio;
    private javax.swing.JTextField txtN;
    private javax.swing.JTextField txtSeed;
    private javax.swing.JTextField txtSf;
    private javax.swing.JTextField txtSfEcg;
    private EcgParam paramOb;
    private EcgLogWindow ecgLog;
    /**
     * Creates new form paramWindow
     */
    public EcgParamWindow(EcgParam parameters, EcgLogWindow logOb) {
        initComponents();
        this.setSize(450, 410);
        paramOb = parameters;
        ecgLog = logOb;
        resetParameters();
    }

    /*
     * Set the source for the parameter object
     */
    public void setParamOb(EcgParam parameters) {
        paramOb = parameters;
        resetParameters();
    }

    /*
     * Reste and get all parameters
     */
    private void resetParameters() {
        paramOb.resetParameters();
        /* General Intergace parameters */
        txtN.setText(Integer.toString(paramOb.getN()));
        txtSfEcg.setText(Integer.toString(paramOb.getSfEcg()));
        txtSf.setText(Integer.toString(paramOb.getSf()));
        txtANoise.setText(Double.toString(paramOb.getANoise()));
        txtHrMean.setText(Double.toString(paramOb.getHrMean()));
        txtHrStd.setText(Double.toString(paramOb.getHrStd()));
        txtSeed.setText(Integer.toString(paramOb.getSeed()));
        txtAmplitude.setText(Double.toString(paramOb.getAmplitude()));
        /* Spectral Characteristics parameters */
        txtFLo.setText(Double.toString(paramOb.getFLo()));
        txtFHi.setText(Double.toString(paramOb.getFHi()));
        txtFLoStd.setText(Double.toString(paramOb.getFLoStd()));
        txtFHiStd.setText(Double.toString(paramOb.getFHiStd()));
        txtLfHfRatio.setText(Double.toString(paramOb.getLfHfRatio()));
        /*
         * ECG morphology: Order of extrema: [P Q R S T]
         */
        //data tables
        tiTable.getModel().setValueAt(new Double(paramOb.getTheta(0)), 0, 0);
        tiTable.getModel().setValueAt(new Double(paramOb.getTheta(1)), 1, 0);
        tiTable.getModel().setValueAt(new Double(paramOb.getTheta(2)), 2, 0);
        tiTable.getModel().setValueAt(new Double(paramOb.getTheta(3)), 3, 0);
        tiTable.getModel().setValueAt(new Double(paramOb.getTheta(4)), 4, 0);

        aiTable.getModel().setValueAt(new Double(paramOb.getA(0)), 0, 0);
        aiTable.getModel().setValueAt(new Double(paramOb.getA(1)), 1, 0);
        aiTable.getModel().setValueAt(new Double(paramOb.getA(2)), 2, 0);
        aiTable.getModel().setValueAt(new Double(paramOb.getA(3)), 3, 0);
        aiTable.getModel().setValueAt(new Double(paramOb.getA(4)), 4, 0);

        biTable.getModel().setValueAt(new Double(paramOb.getB(0)), 0, 0);
        biTable.getModel().setValueAt(new Double(paramOb.getB(1)), 1, 0);
        biTable.getModel().setValueAt(new Double(paramOb.getB(2)), 2, 0);
        biTable.getModel().setValueAt(new Double(paramOb.getB(3)), 3, 0);
        biTable.getModel().setValueAt(new Double(paramOb.getB(4)), 4, 0);

    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel2 = new javax.swing.JPanel();
        paramTabbedPane = new javax.swing.JTabbedPane();
        generalInterfacePanel = new javax.swing.JPanel();
        txtSf = new javax.swing.JTextField();
        lblSf = new javax.swing.JLabel();
        lblN = new javax.swing.JLabel();
        txtN = new javax.swing.JTextField();
        lblHrMean = new javax.swing.JLabel();
        txtHrMean = new javax.swing.JTextField();
        lblHrStd = new javax.swing.JLabel();
        txtHrStd = new javax.swing.JTextField();
        lblAmplitude = new javax.swing.JLabel();
        txtAmplitude = new javax.swing.JTextField();
        lblGeneralTitle = new javax.swing.JLabel();
        lblANoise = new javax.swing.JLabel();
        txtANoise = new javax.swing.JTextField();
        lblSfEcg = new javax.swing.JLabel();
        txtSfEcg = new javax.swing.JTextField();
        lblSeed = new javax.swing.JLabel();
        txtSeed = new javax.swing.JTextField();
        spectralCharacteristicsPanel = new javax.swing.JPanel();
        lblSpectralTitle = new javax.swing.JLabel();
        lblLfHfRatio = new javax.swing.JLabel();
        txtLfHfRatio = new javax.swing.JTextField();
        lblFLo = new javax.swing.JLabel();
        txtFLo = new javax.swing.JTextField();
        lblFHi = new javax.swing.JLabel();
        txtFHi = new javax.swing.JTextField();
        lblFLoStd = new javax.swing.JLabel();
        lblFHiStd = new javax.swing.JLabel();
        txtFHiStd = new javax.swing.JTextField();
        txtFLoStd = new javax.swing.JTextField();
        extremaPanel = new javax.swing.JPanel();
        lblMorphologyTitle = new javax.swing.JLabel();
        tiScrollPane = new javax.swing.JScrollPane();
        tiTable = new javax.swing.JTable();
        aiScrollPane = new javax.swing.JScrollPane();
        aiTable = new javax.swing.JTable();
        biScrollPane = new javax.swing.JScrollPane();
        biTable = new javax.swing.JTable();
        ExtremaLabelScrollPane = new javax.swing.JScrollPane();
        ExtremaLabelTable = new javax.swing.JTable();
        closeParamDialogButton = new javax.swing.JButton();
        resetParamDialogButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        applyButton = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setResizable(true);
        setTitle("Set parameters for ECG");
        jPanel2.setLayout(null);

        paramTabbedPane.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
        paramTabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        paramTabbedPane.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        generalInterfacePanel.setLayout(null);

        generalInterfacePanel.setName("generalInterface");
        txtSf.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        txtSf.setToolTipText("");
        generalInterfacePanel.add(txtSf);
        txtSf.setBounds(280, 110, 110, 20);

        lblSf.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        lblSf.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSf.setText("Internal Sampling frequency [Hz]");
        generalInterfacePanel.add(lblSf);
        lblSf.setBounds(10, 110, 250, 14);

        lblN.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        lblN.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblN.setText("Approximate number of heart beats");
        generalInterfacePanel.add(lblN);
        lblN.setBounds(10, 50, 250, 14);

        txtN.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        txtN.setToolTipText("");
        generalInterfacePanel.add(txtN);
        txtN.setBounds(280, 50, 110, 20);

        lblHrMean.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        lblHrMean.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblHrMean.setText("Heart rate mean [bpm]");
        generalInterfacePanel.add(lblHrMean);
        lblHrMean.setBounds(10, 170, 250, 14);

        txtHrMean.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        txtHrMean.setToolTipText("");
        generalInterfacePanel.add(txtHrMean);
        txtHrMean.setBounds(280, 170, 110, 20);

        lblHrStd.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        lblHrStd.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblHrStd.setText("Heart rate standard deviation [bpm]");
        generalInterfacePanel.add(lblHrStd);
        lblHrStd.setBounds(10, 200, 250, 14);

        txtHrStd.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        txtHrStd.setToolTipText("");
        generalInterfacePanel.add(txtHrStd);
        txtHrStd.setBounds(280, 200, 110, 20);

        lblAmplitude.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        lblAmplitude.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAmplitude.setText("Plot area Amplitude");
        generalInterfacePanel.add(lblAmplitude);
        lblAmplitude.setBounds(10, 260, 250, 14);

        txtAmplitude.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        txtAmplitude.setToolTipText("");
        generalInterfacePanel.add(txtAmplitude);
        txtAmplitude.setBounds(280, 260, 110, 20);

        lblGeneralTitle.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
        lblGeneralTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGeneralTitle.setText("General Interface Parameters");
        generalInterfacePanel.add(lblGeneralTitle);
        lblGeneralTitle.setBounds(40, 10, 350, 20);

        lblANoise.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        lblANoise.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblANoise.setText("Amplitude of additive uniform noise [mV]");
        lblANoise.setToolTipText("");
        generalInterfacePanel.add(lblANoise);
        lblANoise.setBounds(10, 140, 250, 14);

        txtANoise.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        txtANoise.setToolTipText("");
        txtANoise.setName("Anoise");
        generalInterfacePanel.add(txtANoise);
        txtANoise.setBounds(280, 140, 110, 20);

        lblSfEcg.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        lblSfEcg.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSfEcg.setText("ECG Sampling Frequency [Hz]");
        generalInterfacePanel.add(lblSfEcg);
        lblSfEcg.setBounds(10, 80, 250, 14);

        txtSfEcg.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        txtSfEcg.setToolTipText("");
        generalInterfacePanel.add(txtSfEcg);
        txtSfEcg.setBounds(280, 80, 110, 20);

        lblSeed.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        lblSeed.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSeed.setText("Seed");
        generalInterfacePanel.add(lblSeed);
        lblSeed.setBounds(10, 230, 250, 14);

        txtSeed.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        txtSeed.setToolTipText("");
        generalInterfacePanel.add(txtSeed);
        txtSeed.setBounds(280, 230, 110, 20);

        paramTabbedPane.addTab("General Interface", generalInterfacePanel);

        spectralCharacteristicsPanel.setLayout(null);

        spectralCharacteristicsPanel.setName("spectralCharacteristics");
        lblSpectralTitle.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
        lblSpectralTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSpectralTitle.setText("Spectral Characteristics Parameters");
        spectralCharacteristicsPanel.add(lblSpectralTitle);
        lblSpectralTitle.setBounds(30, 10, 350, 20);

        lblLfHfRatio.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        lblLfHfRatio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLfHfRatio.setText("LF/HF ratio");
        spectralCharacteristicsPanel.add(lblLfHfRatio);
        lblLfHfRatio.setBounds(20, 170, 250, 14);

        txtLfHfRatio.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        txtLfHfRatio.setToolTipText("Low Frequency / High Frequency ratio");
        spectralCharacteristicsPanel.add(txtLfHfRatio);
        txtLfHfRatio.setBounds(290, 170, 110, 20);

        lblFLo.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        lblFLo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFLo.setText("Low frequency [Hz]");
        spectralCharacteristicsPanel.add(lblFLo);
        lblFLo.setBounds(20, 50, 250, 14);

        txtFLo.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        txtFLo.setToolTipText("");
        spectralCharacteristicsPanel.add(txtFLo);
        txtFLo.setBounds(290, 50, 110, 20);

        lblFHi.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        lblFHi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFHi.setText("High frequency [Hz]");
        spectralCharacteristicsPanel.add(lblFHi);
        lblFHi.setBounds(20, 80, 250, 14);

        txtFHi.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        txtFHi.setToolTipText("");
        spectralCharacteristicsPanel.add(txtFHi);
        txtFHi.setBounds(290, 80, 110, 20);

        lblFLoStd.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        lblFLoStd.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFLoStd.setText("Low frequency standard deviation [Hz]");
        spectralCharacteristicsPanel.add(lblFLoStd);
        lblFLoStd.setBounds(20, 110, 250, 14);

        lblFHiStd.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        lblFHiStd.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFHiStd.setText("High frequency standard deviation [Hz]");
        lblFHiStd.setToolTipText("");
        spectralCharacteristicsPanel.add(lblFHiStd);
        lblFHiStd.setBounds(20, 140, 250, 14);

        txtFHiStd.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        txtFHiStd.setToolTipText("");
        txtFHiStd.setName("Anoise");
        spectralCharacteristicsPanel.add(txtFHiStd);
        txtFHiStd.setBounds(290, 140, 110, 20);

        txtFLoStd.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        txtFLoStd.setToolTipText("");
        spectralCharacteristicsPanel.add(txtFLoStd);
        txtFLoStd.setBounds(290, 110, 110, 20);

        paramTabbedPane.addTab("Spectral Characteristics", spectralCharacteristicsPanel);

        extremaPanel.setLayout(null);

        lblMorphologyTitle.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
        lblMorphologyTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMorphologyTitle.setText("Order of Extrema");
        extremaPanel.add(lblMorphologyTitle);
        lblMorphologyTitle.setBounds(40, 10, 350, 20);

        tiScrollPane.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        tiScrollPane.setViewportBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        tiTable.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        tiTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null},
                        {null},
                        {null},
                        {null},
                        {null}
                },
                new String[]{
                        "Theta"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        tiScrollPane.setViewportView(tiTable);

        extremaPanel.add(tiScrollPane);
        tiScrollPane.setBounds(130, 80, 80, 120);

        aiScrollPane.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        aiScrollPane.setViewportBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        aiTable.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        aiTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null},
                        {null},
                        {null},
                        {null},
                        {null}
                },
                new String[]{
                        "a"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        aiScrollPane.setViewportView(aiTable);

        extremaPanel.add(aiScrollPane);
        aiScrollPane.setBounds(220, 80, 80, 120);

        biScrollPane.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        biScrollPane.setViewportBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        biTable.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        biTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null},
                        {null},
                        {null},
                        {null},
                        {null}
                },
                new String[]{
                        "b"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        biScrollPane.setViewportView(biTable);

        extremaPanel.add(biScrollPane);
        biScrollPane.setBounds(310, 80, 80, 120);

        ExtremaLabelScrollPane.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        ExtremaLabelScrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ExtremaLabelScrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        ExtremaLabelScrollPane.setViewportBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        ExtremaLabelScrollPane.setEnabled(false);
        ExtremaLabelTable.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        ExtremaLabelTable.setFont(new java.awt.Font("Dialog", 1, 12));
        ExtremaLabelTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {"          P(1) :"},
                        {"          Q(2) :"},
                        {"          R(3) :"},
                        {"          S(4) :"},
                        {"          T(5) :"}
                },
                new String[]{
                        "peak label"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                    false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        ExtremaLabelTable.setGridColor(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        ExtremaLabelTable.setEnabled(false);
        ExtremaLabelScrollPane.setViewportView(ExtremaLabelTable);

        extremaPanel.add(ExtremaLabelScrollPane);
        ExtremaLabelScrollPane.setBounds(10, 80, 110, 120);

        paramTabbedPane.addTab("ECG Morphology", extremaPanel);

        jPanel2.add(paramTabbedPane);
        paramTabbedPane.setBounds(0, 0, 440, 330);

        closeParamDialogButton.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        closeParamDialogButton.setText("Close");
        closeParamDialogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeParamDialogButtonActionPerformed(evt);
            }
        });

        jPanel2.add(closeParamDialogButton);
        closeParamDialogButton.setBounds(350, 340, 80, 25);

        resetParamDialogButton.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        resetParamDialogButton.setText("Reset");
        resetParamDialogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetParamDialogButtonActionPerformed(evt);
            }
        });

        jPanel2.add(resetParamDialogButton);
        resetParamDialogButton.setBounds(10, 340, 80, 25);

        saveButton.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        saveButton.setText("Save Values");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        jPanel2.add(saveButton);
        saveButton.setBounds(220, 340, 100, 25);

        applyButton.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        applyButton.setText("Apply");
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonActionPerformed(evt);
            }
        });

        jPanel2.add(applyButton);
        applyButton.setBounds(110, 340, 80, 25);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        JFileChooser c = new JFileChooser();
        /* Open "Save" dialog: */
        int rVal = c.showSaveDialog(this);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            File file = c.getSelectedFile();
            try {
                FileWriter fw = new FileWriter(file);
                fw.write("ECG Parameters:\r\n");
                fw.write("Approximate number of heart beats: " + paramOb.getN() + "\r\n");
                fw.write("ECG sampling frequency: " + paramOb.getSfEcg() + " Hertz\r\n");
                fw.write("Internal sampling frequency: " + paramOb.getSf() + " Hertz\r\n");
                fw.write("Amplitude of additive uniformly distributed noise: " + paramOb.getANoise() + " mV\r\n");
                fw.write("Heart rate mean: " + paramOb.getHrMean() + " beats per minute\r\n");
                fw.write("Heart rate std: " + paramOb.getHrStd() + " beats per minute\r\n");
                fw.write("Low frequency: " + paramOb.getFLo() + " Hertz\r\n");
                fw.write("High frequency std: " + paramOb.getFHiStd() + " Hertz\r\n");
                fw.write("Low frequency std: " + paramOb.getFLoStd() + " Hertz\r\n");
                fw.write("High frequency: " + paramOb.getFHi() + " Hertz\r\n");
                fw.write("LF/HF ratio: " + paramOb.getLfHfRatio() + "\r\n");
                fw.write("time step milliseconds: " + paramOb.getEcgAnimateInterval() + "\r\n");
                fw.write("Order of Extrema:\r\n");
                fw.write("      theta\r\n");
                fw.write("P: [1] = " + paramOb.getTheta(0) + "\r\n");
                fw.write("Q: [2] = " + paramOb.getTheta(1) + "\r\n");
                fw.write("R: [3] = " + paramOb.getTheta(2) + "\r\n");
                fw.write("S: [4] = " + paramOb.getTheta(3) + "\r\n");
                fw.write("T: [5] = " + paramOb.getTheta(4) + "\r\n");
                fw.write("      a\r\n");
                fw.write("P: [1] = " + paramOb.getA(0) + "\r\n");
                fw.write("Q: [2] = " + paramOb.getA(1) + "\r\n");
                fw.write("R: [3] = " + paramOb.getA(2) + "\r\n");
                fw.write("S: [4] = " + paramOb.getA(3) + "\r\n");
                fw.write("T: [5] = " + paramOb.getA(4) + "\r\n");
                fw.write("      b\r\n");
                fw.write("P: [1] = " + paramOb.getB(0) + "\r\n");
                fw.write("Q: [2] = " + paramOb.getB(1) + "\r\n");
                fw.write("R: [3] = " + paramOb.getB(2) + "\r\n");
                fw.write("S: [4] = " + paramOb.getB(3) + "\r\n");
                fw.write("T: [5] = " + paramOb.getB(4) + "\r\n");
                fw.close();
                JOptionPane.showMessageDialog(this, "Parameters were saved successfully!");
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void resetParamDialogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetParamDialogButtonActionPerformed
        // TODO add your handling code here:
        resetParameters();
    }//GEN-LAST:event_resetParamDialogButtonActionPerformed
    // End of variables declaration//GEN-END:variables

    private void closeParamDialogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeParamDialogButtonActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_closeParamDialogButtonActionPerformed

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonActionPerformed
        boolean i = checkParameters();
    }//GEN-LAST:event_applyButtonActionPerformed

    private boolean checkParameters() {
        ecgLog.println("Starting to check ECG parameters...");
        String errorStr = "";

        boolean RetValue = true;
        //ECG Sampling frequency flag
        boolean sfecg_flg = true;
        //Internal Sampling frequency flag
        boolean sf_flg = true;

        /* General Interface parameters */
        try {
            paramOb.setN(Integer.valueOf(txtN.getText()).intValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'Approximate number of heart beats' entered, please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //txtN.setText(Integer.toString(paramOb.getN()));
            RetValue = false;
        }

        try {
            paramOb.setSfEcg(Integer.valueOf(txtSfEcg.getText()).intValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'ECG Sampling Frequency' entered, please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //txtSfEcg.setText(Integer.toString(paramOb.getSfEcg()));
            sfecg_flg = false;
            RetValue = false;
        }

        if (sfecg_flg) {
            try {
                paramOb.setSf(Integer.valueOf(txtSf.getText()).intValue());
            } catch (java.lang.NumberFormatException e) {
                errorStr += "Incorrect 'Internal Sampling Frequency' entered, please correct it!\n";
                errorStr += "Exception Error : " + e + "\n";
                //txtSf.setText(Integer.toString(paramOb.getSf()));
                sf_flg = false;
                RetValue = false;
            }
        }

        // Check the Internal frequency respect to ECG frequency
        if (sfecg_flg && sf_flg) {
            if (((int) Math.IEEEremainder(Integer.valueOf(txtSf.getText()).intValue(), Integer.valueOf(txtSfEcg.getText()).intValue())) != 0) {
                errorStr += "Internal sampling frequency must be an integer multiple of the\n";
                errorStr += "ECG sampling frequency!\n";
                //txtSfEcg.setText(Integer.toString(paramOb.getSfEcg()));
                //txtSf.setText(Integer.toString(paramOb.getSf()));
                RetValue = false;
            }
        }

        try {
            paramOb.setANoise(Double.valueOf(txtANoise.getText()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'Amplitude of additive uniform noise' entered, please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //txtANoise.setText(Double.toString(paramOb.getANoise()));
            RetValue = false;
        }

        try {
            paramOb.setHrMean(Double.valueOf(txtHrMean.getText()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'Heart rate mean' entered, please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //txtHrMean.setText(Double.toString(paramOb.getHrMean()));
            RetValue = false;
        }

        try {
            paramOb.setHrStd(Double.valueOf(txtHrStd.getText()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'Heart rate standard deviation' entered, please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //txtHrStd.setText(Double.toString(paramOb.getHrStd()));
            RetValue = false;
        }

        try {
            paramOb.setSeed(Integer.valueOf(txtSeed.getText()).intValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'seed' entered, please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //txtSeed.setText(Integer.toString(paramOb.getSeed()));
            RetValue = false;
        }

        try {
            paramOb.setAmplitude(Double.valueOf(txtAmplitude.getText()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'Plot Area Amplitude' entered, please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //txtAmplitude.setText(Double.toString(paramOb.getAmplitude()));
            RetValue = false;
        }

        /* Spectral Characteristics parameters */

        try {
            paramOb.setFLo(Double.valueOf(txtFLo.getText()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'Low frequency' entered, please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //txtFLo.setText(Double.toString(paramOb.getFLo()));
            RetValue = false;
        }

        try {
            paramOb.setFHi(Double.valueOf(txtFHi.getText()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'High frequency' entered, please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //txtFHi.setText(Double.toString(paramOb.getFHi()));
            RetValue = false;
        }

        try {
            paramOb.setFLoStd(Double.valueOf(txtFLoStd.getText()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'Low frequency standard deviation' entered, please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //txtFLoStd.setText(Double.toString(paramOb.getFLo()));
            RetValue = false;
        }

        try {
            paramOb.setFHiStd(Double.valueOf(txtFHiStd.getText()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'High frequency standard deviation' entered, please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //txtFHiStd.setText(Double.toString(paramOb.getFHiStd()));
            RetValue = false;
        }

        try {
            paramOb.setLfHfRatio(Double.valueOf(txtLfHfRatio.getText()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'LF/HF ratio' entered, please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //txtLfHfRatio.setText(Double.toString(paramOb.getLfHfRatio()));
            RetValue = false;
        }

        /* ECG morphology: Order of extrema: [P Q R S T] */
        // theta
        try {
            paramOb.setTheta(0, Double.valueOf(tiTable.getValueAt(0, 0).toString()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'theta' value entered (position 1), please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //tiTable.getModel().setValueAt(new Double(paramOb.getTheta(1)), 0, 0);
            RetValue = false;
        }
        try {
            paramOb.setTheta(1, Double.valueOf(tiTable.getValueAt(1, 0).toString()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'theta' value entered (position 2), please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //tiTable.getModel().setValueAt(new Double(paramOb.getTheta(2)), 1, 0);
            RetValue = false;
        }
        try {
            paramOb.setTheta(2, Double.valueOf(tiTable.getValueAt(2, 0).toString()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'theta' value entered (position 3), please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //tiTable.getModel().setValueAt(new Double(paramOb.getTheta(3)), 2, 0);
            RetValue = false;
        }
        try {
            paramOb.setTheta(3, Double.valueOf(tiTable.getValueAt(3, 0).toString()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'theta' value entered (position 4), please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //tiTable.getModel().setValueAt(new Double(paramOb.getTheta(4)), 3, 0);
            RetValue = false;
        }
        try {
            paramOb.setTheta(4, Double.valueOf(tiTable.getValueAt(4, 0).toString()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'theta' value entered (position 5), please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //tiTable.getModel().setValueAt(new Double(paramOb.getTheta(5)), 4, 0);
            RetValue = false;
        }

        // a
        try {
            paramOb.setA(0, Double.valueOf(aiTable.getValueAt(0, 0).toString()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'a' value entered (position 1), please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //aiTable.getModel().setValueAt(new Double(paramOb.getTheta(1)), 0, 0);
            RetValue = false;
        }
        try {
            paramOb.setA(1, Double.valueOf(aiTable.getValueAt(1, 0).toString()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'a' value entered (position 2), please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //aiTable.getModel().setValueAt(new Double(paramOb.getTheta(2)), 1, 0);
            RetValue = false;
        }
        try {
            paramOb.setA(2, Double.valueOf(aiTable.getValueAt(2, 0).toString()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'a' value entered (position 3), please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //aiTable.getModel().setValueAt(new Double(paramOb.getTheta(3)), 2, 0);
            RetValue = false;
        }
        try {
            paramOb.setA(3, Double.valueOf(aiTable.getValueAt(3, 0).toString()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'a' value entered (position 4), please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //aiTable.getModel().setValueAt(new Double(paramOb.getTheta(4)), 3, 0);
            RetValue = false;
        }
        try {
            paramOb.setA(4, Double.valueOf(aiTable.getValueAt(4, 0).toString()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'a' value entered (position 5), please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //aiTable.getModel().setValueAt(new Double(paramOb.getTheta(5)), 4, 0);
            RetValue = false;
        }

        // b
        try {
            paramOb.setB(0, Double.valueOf(biTable.getValueAt(0, 0).toString()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'b' value entered (position 1), please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //biTable.getModel().setValueAt(new Double(paramOb.getTheta(1)), 0, 0);
            RetValue = false;
        }
        try {
            paramOb.setB(1, Double.valueOf(biTable.getValueAt(1, 0).toString()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'b' value entered (position 2), please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //biTable.getModel().setValueAt(new Double(paramOb.getTheta(2)), 1, 0);
            RetValue = false;
        }
        try {
            paramOb.setB(2, Double.valueOf(biTable.getValueAt(2, 0).toString()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'b' value entered (position 3), please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //biTable.getModel().setValueAt(new Double(paramOb.getTheta(3)), 2, 0);
            RetValue = false;
        }
        try {
            paramOb.setB(3, Double.valueOf(biTable.getValueAt(3, 0).toString()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'b' value entered (position 4), please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //biTable.getModel().setValueAt(new Double(paramOb.getTheta(4)), 3, 0);
            RetValue = false;
        }
        try {
            paramOb.setB(4, Double.valueOf(biTable.getValueAt(4, 0).toString()).doubleValue());
        } catch (java.lang.NumberFormatException e) {
            errorStr += "Incorrect 'b' value entered (position 5), please correct it!\n";
            errorStr += "Exception Error : " + e + "\n";
            //biTable.getModel().setValueAt(new Double(paramOb.getTheta(5)), 4, 0);
            RetValue = false;
        }

        /* Check aditional Parameters' internal Rules*/
        if (!paramOb.checkParameters()) {
            errorStr += "There were errors in internal validation rules!:.\n";
            RetValue = false;
        }

        if (RetValue) {
            errorStr = "All Parameters are valid, you can run ECG!.";
            ecgLog.println(errorStr);
        } else {
            errorStr = "There were errors in some parameters!:.\n" + errorStr;
            ecgLog.print(errorStr);
        }
        JOptionPane.showMessageDialog(this, errorStr);

        ecgLog.println("Finished checking ECG parameters.\n");

        return (RetValue);
    }
}
