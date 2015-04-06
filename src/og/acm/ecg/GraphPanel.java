package og.acm.ecg;/*
 * plotWindow.java
 *
 * See EcgLicense.txt for License terms.
 */

/**
 *
 * @author Mauricio Villarroel (m.villarroel@acm.og)
 */

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Timer;
import java.util.TimerTask;

public class GraphPanel extends JPanel implements AdjustmentListener {

    /**
     * These constants used in drawText() method for placement 
     * of the text within a given rectangular area.
     */
    private static final int CENTER = 0;
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    
    /**
     * Frame Dimensions.
     */
    private final int posFrameY = 1;
    private final int frameHeight = 290;
    private final int frameAmplitude = frameHeight / 2;
    
    //Coordinates Origin
    private final int posOriginY = posFrameY + (frameHeight / 2);
    
    //X coordinates
    private final int horizontalScaleY = posFrameY + frameHeight;
    private final int horizontalScaleWidth = 100;
    private final int horizontalScaleHeight = 20;
    private final int fScaleNumSize = 9;
    
    /**
     * Colors for the Plotting Components
     */
    private final Color ecgPlotColor = Color.BLUE;
    private final Color frameLineColor = Color.BLACK;
    private final Color frameInsideLineColor = Color.LIGHT_GRAY;
    private final Color axesNumColor = Color.GRAY;
    
    /**
     * Limit below which scale values use decimal format,
     * above which they use scientific format.
     */
    private final double upLimit = 100.0;
    private final double loLimit = 0.01;

    /**
     * Plotting variables
     */
    private boolean readyToPlot;
    private int plotScrollBarValue;
    private double plotZoom = 0.008;
    private final double plotZoomInc = 2;
    private Timer ecgAnimateTimer;
    private final Point ecgAnimateLastPoint = new java.awt.Point(0, 0);
    
    /**
     * Variables to Animate ECG
     */
    /* Total plotting Data Table Row */
    private int ecgAnimateNumRows;
    /* Current plotting Data Table Row */
    private int ecgAnimateCurRow;
    /* Plot Area Panel width */
    private int ecgAnimatePanelWidth;
    /* Starting X axis value to plot*/
    private int ecgAnimateInitialZero;
    private JButton animateStartButton;
    private JButton animateStopButton;
    private JButton clearButton;
    private JScrollPane ecgPlotArea;
    private JButton exportButton;
    private JButton generateButton;
    private JLabel lblMaxAmplitude;
    private JLabel lblMinAmplitude;
    private JScrollBar plotScrollBar;
    private JTable tableValues;
    private JButton zoomInButton;
    private JButton zoomOutButton;
    private final EcgCalc calcOb;
    private EcgCalc.Generator generator;
    private final EcgParameters paramOb;
    private final LogWindow ecgLog;
    private DefaultTableModel tableValuesModel;
    private ECGPanel ecgFrame;
    /**
     * Creates new form plotWindow
     */
    public GraphPanel(EcgParameters parameters, LogWindow logOb) {
        this.paramOb = parameters;
        this.calcOb = new EcgCalc(paramOb, logOb);
        this.ecgLog = logOb;
        
        initComponents();
        initWindow();
    }

    private void initWindow() {
        this.setSize(850, 475);

        /**
         * Init the data Table
         */
        tableValuesModel = new EcgCalculationsTableModelAdapter(this.calcOb);
        tableValues.setModel(tableValuesModel);

        /* Init the ecgFrame */
        ecgFrame = new ECGPanel();
        ecgFrame.setBackground(new java.awt.Color(255, 255, 255));
        ecgPlotArea.setViewportView(ecgFrame);

        /* Set the ScrollBar */
        plotScrollBar.addAdjustmentListener(this);

        /**
         * Reset all Application to an initialized state.
         */
        resetECG();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {
        JToolBar plotToolBar = new JToolBar();
        JSeparator jSeparator2 = new JSeparator();
        JLabel jLabel3 = new JLabel();
        generateButton = new JButton();
        JSeparator jSeparator4 = new JSeparator();
        JLabel jLabel2 = new JLabel();
        zoomInButton = new JButton();
        zoomOutButton = new JButton();
        JSeparator jSeparator3 = new JSeparator();
        JLabel jLabel1 = new JLabel();
        animateStartButton = new JButton();
        animateStopButton = new JButton();
        JSeparator jSeparator1 = new JSeparator();
        JPanel mainPanel = new JPanel();
        ecgPlotArea = new JScrollPane();
        plotScrollBar = new JScrollBar();
        lblMinAmplitude = new JLabel();
        JLabel lblOrigin = new JLabel();
        lblMaxAmplitude = new JLabel();
        JTextArea jTextArea1 = new JTextArea();
        JScrollPane tableScrollPane = new JScrollPane();
        tableValues = new JTable();
        JButton closeButton = new JButton();
        exportButton = new JButton();
        clearButton = new JButton();

        setLayout(new BorderLayout());
        plotToolBar.setBorder(new EtchedBorder(null, Color.lightGray));
        plotToolBar.setRollover(true);
        plotToolBar.setMinimumSize(new Dimension(234, 30));
        plotToolBar.setPreferredSize(new Dimension(234, 30));
        plotToolBar.setAutoscrolls(true);
        jSeparator2.setMaximumSize(new Dimension(1000, 32767));
        plotToolBar.add(jSeparator2);

        jLabel3.setText("Generate:");
        plotToolBar.add(jLabel3);

        generateButton.setIcon(new ImageIcon("RES/img/execute2.gif"));
        generateButton.setToolTipText("Generate System");
        generateButton.setMaximumSize(new Dimension(22, 22));
        generateButton.setMinimumSize(new Dimension(22, 22));
        generateButton.setPreferredSize(new Dimension(22, 22));
        generateButton.addActionListener(this::generateButtonActionPerformed);

        plotToolBar.add(generateButton);

        jSeparator4.setMaximumSize(new Dimension(1000, 32767));
        plotToolBar.add(jSeparator4);

        jLabel2.setText("Zoom:");
        plotToolBar.add(jLabel2);

        zoomInButton.setIcon(new ImageIcon("res/img/tbzoomin.png"));
        zoomInButton.setToolTipText("Zoom In");
        zoomInButton.setMargin(new Insets(0, 0, 0, 0));
        zoomInButton.setMaximumSize(new Dimension(22, 22));
        zoomInButton.setMinimumSize(new Dimension(22, 22));
        zoomInButton.setPreferredSize(new Dimension(22, 22));
        zoomInButton.addActionListener(this::zoomInButtonActionPerformed);
        plotToolBar.add(zoomInButton);

        zoomOutButton.setIcon(new ImageIcon("res/img/tbzoomout.png"));
        zoomOutButton.setToolTipText("Zoom Out");
        zoomOutButton.setMaximumSize(new Dimension(22, 22));
        zoomOutButton.setMinimumSize(new Dimension(22, 22));
        zoomOutButton.setPreferredSize(new Dimension(22, 22));
        zoomOutButton.addActionListener(this::zoomOutButtonActionPerformed);
        plotToolBar.add(zoomOutButton);

        jSeparator3.setMaximumSize(new Dimension(1000, 32767));
        plotToolBar.add(jSeparator3);

        jLabel1.setText("Animate:");
        plotToolBar.add(jLabel1);

        animateStartButton.setIcon(new ImageIcon("res/img/ball_green.gif"));
        animateStartButton.setToolTipText("Start Animation");
        animateStartButton.setMaximumSize(new Dimension(22, 22));
        animateStartButton.setMinimumSize(new Dimension(22, 22));
        animateStartButton.setPreferredSize(new Dimension(22, 22));
        animateStartButton.addActionListener(this::animateStartButtonActionPerformed);
        plotToolBar.add(animateStartButton);

        animateStopButton.setIcon(new ImageIcon("res/img/ball_red.gif"));
        animateStopButton.setToolTipText("Stop Animation");
        animateStopButton.setMaximumSize(new Dimension(22, 22));
        animateStopButton.setMinimumSize(new Dimension(22, 22));
        animateStopButton.setPreferredSize(new Dimension(22, 22));
        animateStopButton.addActionListener(this::animateStopButtonActionPerformed);
        plotToolBar.add(animateStopButton);

        plotToolBar.add(jSeparator1);
        add(plotToolBar, BorderLayout.NORTH);
        mainPanel.setLayout(null);

        ecgPlotArea.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED), "Plot Area", TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
        ecgPlotArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ecgPlotArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        mainPanel.add(ecgPlotArea);
        ecgPlotArea.setBounds(73, 2, 580, 350);

        plotScrollBar.setMaximum(0);
        plotScrollBar.setOrientation(JScrollBar.HORIZONTAL);
        plotScrollBar.setName("timeScroll");
        mainPanel.add(plotScrollBar);
        plotScrollBar.setBounds(73, 352, 580, 16);

        lblMinAmplitude.setFont(new java.awt.Font("Dialog", 1, 9));
        lblMinAmplitude.setText("-0.001");
        mainPanel.add(lblMinAmplitude);
        lblMinAmplitude.setBounds(30, 309, 40, 12);

        lblOrigin.setFont(new java.awt.Font("Dialog", 1, 9));
        lblOrigin.setText("0.00");
        mainPanel.add(lblOrigin);
        lblOrigin.setBounds(30, 167, 40, 12);

        lblMaxAmplitude.setFont(new java.awt.Font("Dialog", 1, 9));
        lblMaxAmplitude.setText("0.001");
        mainPanel.add(lblMaxAmplitude);
        lblMaxAmplitude.setBounds(30, 25, 40, 12);

        jTextArea1.setBackground(new java.awt.Color(212, 208, 200));
        jTextArea1.setLineWrap(true);
        jTextArea1.setText("Voltage");
        mainPanel.add(jTextArea1);
        jTextArea1.setBounds(5, 120, 10, 130);

        tableScrollPane.setBorder(new TitledBorder(null, "Data Table", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));
        tableScrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        tableScrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableValues.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableScrollPane.setViewportView(tableValues);

        mainPanel.add(tableScrollPane);
        tableScrollPane.setBounds(660, 0, 180, 340);

        exportButton.setIcon(new ImageIcon("res/img/disk.gif"));
        exportButton.setText("Export Data...");
        exportButton.setToolTipText("Export Table Data to a File");
        exportButton.setIconTextGap(15);
        exportButton.addActionListener(GraphPanel.this::exportButtonActionPerformed);

        mainPanel.add(exportButton);
        exportButton.setBounds(660, 343, 180, 25);

        clearButton.setText("Clear");
        clearButton.addActionListener(GraphPanel.this::clearButtonActionPerformed);

        mainPanel.add(clearButton);
        clearButton.setBounds(630, 380, 80, 25);

        add(mainPanel, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));

        // Delete the DataTable
        clearDataTable();
        resetECG();
        ecgFrame.repaint();

        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_clearButtonActionPerformed

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        ExportWindow exportWin = new ExportWindow(null, true, paramOb, calcOb, ecgLog);
        exportWin.setVisible(true);
    }//GEN-LAST:event_exportButtonActionPerformed
    // End of variables declaration//GEN-END:variables

    private void animateStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_animateStopButtonActionPerformed
        /* Stop the Animate Plotting Task */
        ecgAnimateTimer.cancel();
        /* Enable automatic plot */
        readyToPlot = true;
        /* Repaint Plot Area */
        ecgFrame.repaint();

        /* Set the Animate Buttons */
        stopECGAnimationSetControls();
    }//GEN-LAST:event_animateStopButtonActionPerformed

    private void animateStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_animateStartButtonActionPerformed
        // Disabling automatic plot
        readyToPlot = false;
        ecgFrame.repaint();

        /*
         * Initialize ECG Animate variables
         */
        // time, voltage, (peak)

        ecgAnimateNumRows = tableValuesModel.getRowCount();
        ecgAnimateCurRow = 0;
        ecgAnimatePanelWidth = ecgFrame.getBounds().width;
        ecgAnimateInitialZero = 0;
        ecgAnimateLastPoint.setLocation(0, posOriginY - (int) (generator.next().voltage * frameAmplitude / paramOb.getAmplitude()));

        /* Create Timer */
        ecgAnimateTimer = new Timer();
        /* Schedule the Animate Plotting Task */
        ecgAnimateTimer.scheduleAtFixedRate(new ECGAnimate(), 0, paramOb.getEcgAnimateInterval());

        /* Set the Animate Buttons */
        startECGAnimationSetControls();
    }//GEN-LAST:event_animateStartButtonActionPerformed

    private void zoomInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomInButtonActionPerformed
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));

        plotZoom = plotZoom / plotZoomInc;
        ecgFrame.repaint();

        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_zoomInButtonActionPerformed

    private void zoomOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomOutButtonActionPerformed
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));

        plotZoom = plotZoom * plotZoomInc;
        ecgFrame.repaint();

        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_zoomOutButtonActionPerformed

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));

        /*
         * Clear Status text.
         */
        ecgLog.println("************************************************************");
        ecgLog.println("ECGSYN:\nA program for generating a realistic synthetic ECG\n");
        ecgLog.println("Copyright (c) 2003 by Patrick McSharry & Gari Clifford.");
        ecgLog.println("All rights reserved.");
        ecgLog.println("See IEEE Transactions On Biomedical Engineering, 50(3),\n289-294, March 2003.\n");
        ecgLog.println("Contact:\nP. McSharry (patrick@mcsharry.net)\nG. Clifford (gari@mit.edu)");
        ecgLog.println("************************************************************\n");
        ecgLog.println("ECG process started.\n");
        ecgLog.println("Starting to clear table data and widgets values....");

        /*
         * Set the Amplitude labels
         */
        lblMaxAmplitude.setText(Double.toString(paramOb.getAmplitude()));
        lblMinAmplitude.setText("-" + Double.toString(paramOb.getAmplitude()));

        /*
         * Re init the plot state.
         * Disable repaint for the moment, until we finish the FFT function.
         */
        readyToPlot = false;
        plotScrollBarValue = 0;
        plotScrollBar.setMaximum(0);

        /* Delete any data on the Data Table. */
        clearDataTable();

        ecgLog.println("Finished clearing table data and widgets values.\n");
        /*
         * Call the ECG funtion to calculate the data into the Data Table.
         */
        this.generator = calcOb.calculateEcg();

        //this.tableValuesModel.fireTableDataChanged();
        //ecgLog.println("Starting to plot ECG table data....");

        /*
        * if the # Data Table rows is less than the ecgFrame width, we do not
        * need the scrollbar
        */
        //int rows = tableValuesModel.getRowCount();
        //if (rows > ecgFrame.getBounds().width) {
        //    //JOptionPane.showMessageDialog(this, "Entro a: rows > ecgFrame.getBounds().width");
        //    plotScrollBar.setMaximum(rows - ecgFrame.getBounds().width - 1);
        //}

        /*
        * Only plot if there's data in the table.
        */
        /*if (rows > 0) {
            //JOptionPane.showMessageDialog(this, "Entro a: rows > 0");
            readyToPlot = true;
            enableButtons();
        } else {
            ecgLog.println("No data to plot!.");
        }*/
        readyToPlot = true;
        enableButtons();

        ecgFrame.repaint();
        ecgLog.println("Finished plotting ECG table data.\n");

        ecgLog.println("Finsihed ECG process.");
        ecgLog.println("************************************************************");

        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_generateButtonActionPerformed

    private void resetECG() {
        resetPlotArea();
        resetButtons();
    }

    private void clearDataTable() {
        // Delete the DataTable
        tableValuesModel.setRowCount(0);
    }

    private void resetButtons() {
        animateStopButton.setEnabled(false);
        animateStartButton.setEnabled(false);
        exportButton.setEnabled(false);
        clearButton.setEnabled(false);
        zoomInButton.setEnabled(false);
        zoomOutButton.setEnabled(false);
    }

    /* Enable the buttons after generating the ecg Data. */
    private void enableButtons() {
        animateStartButton.setEnabled(true);
        exportButton.setEnabled(true);
        clearButton.setEnabled(true);
        zoomInButton.setEnabled(true);
        zoomOutButton.setEnabled(true);
    }

    private void resetPlotArea() {
        lblMaxAmplitude.setText("1.4");
        lblMinAmplitude.setText("-1.4");
        readyToPlot = false;
        plotScrollBarValue = 0;
    }

    /*
     * Set the appropiate state of the controls for start the ECG Animation
     */
    private void startECGAnimationSetControls() {
        animateStopButton.setEnabled(true);

        //exportButton.setEnabled(true);
        clearButton.setEnabled(false);
        generateButton.setEnabled(false);

        zoomInButton.setEnabled(false);
        zoomOutButton.setEnabled(false);

        animateStartButton.setEnabled(false);
    }

    /*
     * Set the appropiate state of the controls for stop the ECG Animation
     */
    private void stopECGAnimationSetControls() {
        animateStartButton.setEnabled(true);

        clearButton.setEnabled(true);
        generateButton.setEnabled(true);
        zoomInButton.setEnabled(true);
        zoomOutButton.setEnabled(true);

        animateStopButton.setEnabled(false);
    }

    /*private void fillDataTable() {
        // Delete the DataTable
        for (int i = 0; i < calcOb.getEcgResultNumRows(); i++) {
            Vector<String> newRow = new Vector<>(3);
            newRow.addElement(Double.toString(calcOb.getEcgResultTime(i)));
            newRow.addElement(Double.toString(calcOb.getEcgResultVoltage(i)));
            newRow.addElement(Integer.toString(calcOb.getEcgResultPeak(i)));
            tableValuesModel.addRow(newRow);
        }
    }*/

    /**
     * Draw a string in the center of a given box.
     * Reduce the font size if necessary to fit. Can
     * fix the type size to a value passed as an argument.
     * The position of the string within the box passed
     * as LEFT, CENTER or RIGHT constant value.
     * Don't draw the strings if they do not fit.
     */
    private int drawText(Graphics g, String msg, int xBox, int yBox, int boxWidth, int boxHeight,
                         int fixedTypeSizeValue, int position) {
        boolean fixedTypeSize = false;
        int typeSize = 24;

        // Fixed to a particular type size.
        if (fixedTypeSizeValue > 0) {
            fixedTypeSize = true;
            typeSize = fixedTypeSizeValue;
        }

        int typeSizeMin = 8;
        int x = xBox, y = yBox;
        do {
            // Create the font and pass it to the  Graphics context
            g.setFont(new Font("Monospaced", Font.PLAIN, typeSize));

            // Get measures needed to center the message
            FontMetrics fm = g.getFontMetrics();

            // How many pixels wide is the string
            int msgWidth = fm.stringWidth(msg);

            // How tall is the text?
            int msgHeight = fm.getHeight();

            // See if the text will fit in the allotted
            // vertical limits
            if (msgHeight < boxHeight && msgWidth < boxWidth) {
                y = yBox + boxHeight / 2 + (msgHeight / 2);
                if (position == CENTER)
                    x = xBox + boxWidth / 2 - (msgWidth / 2);
                else if (position == RIGHT)
                    x = xBox + boxWidth - msgWidth;
                else
                    x = xBox;

                break;
            }

            // If fixedTypeSize and wouldn't fit, don't draw.
            if (fixedTypeSize) return -1;

            // Try smaller type
            typeSize -= 2;

        } while (typeSize >= typeSizeMin);

        // Don't display the numbers if they did not fit
        if (typeSize < typeSizeMin) return -1;

        // Otherwise, draw and return positive signal.
        g.drawString(msg, x, y);
//                ecgFrame.revalidate();
//                ecgFrame.repaint();        
        return typeSize;
    }

    /*
    * This class is the AdjustmentListener for the
    * scroll bar. So the events come here when the
    * scroll bar is moved.
    */
    public void adjustmentValueChanged(AdjustmentEvent evt) {
        plotScrollBarValue = plotScrollBar.getValue();
        ecgFrame.repaint();
    }

    private class ECGPanel extends JPanel {

        public void paintComponent(Graphics g) {
            // First call the paintComponent of the
            // superclass, in this case JPanel.
            super.paintComponent(g);

            /* Draw the plot frame. */
            g.setColor(frameLineColor);
            g.drawLine(0, posFrameY, ecgFrame.getBounds().width, posFrameY);
            g.drawLine(0, posOriginY, this.getBounds().width, posOriginY);
            g.drawLine(0, horizontalScaleY, this.getBounds().width, horizontalScaleY);

            if (false) {
                int rows = tableValuesModel.getRowCount();
                int x, y, i;
                int plotLimit;
                int initialZero;
                int curSecond, lastSecond;
                String strValue;

                /*
                 * Set the first point to the current Table row
                 */
                initialZero = (int) (Double.valueOf(tableValues.getValueAt(plotScrollBarValue, 0).toString()) / plotZoom);
                lastSecond = (int) (Double.valueOf(tableValues.getValueAt(plotScrollBarValue, 0).toString()).doubleValue());
                x = 0;
                y = posOriginY - (int) (Double.valueOf(tableValues.getValueAt(plotScrollBarValue, 1).toString()) * frameAmplitude / paramOb.getAmplitude());
                Point lastPoint = new java.awt.Point(x, y);
                i = plotScrollBarValue;

                while ((x <= this.getBounds().width) && (i <= rows)) {
                    curSecond = (int) (Double.valueOf(tableValues.getValueAt(i, 0).toString()).doubleValue());
                    if (curSecond > lastSecond) {
                        lastSecond = curSecond;
                        // Convert the x value to a string
                        strValue = NumberFormatter.toString(Double.valueOf(tableValues.getValueAt(i, 0).toString()), upLimit, loLimit, 2);
                        /*
                         * Plot the X axes number values (the Time).
                         */
                        g.setColor(axesNumColor);
                        drawText(g, strValue,
                                x, horizontalScaleY, horizontalScaleWidth, horizontalScaleHeight,
                                fScaleNumSize, LEFT);
                        g.setColor(frameInsideLineColor);
                        g.drawLine(x, posFrameY, x, horizontalScaleY + 5);
                    }

                    /*
                     * Plot a line between the las point and the current point.
                     * This to create a illusion to connect the two points.
                     */
                    g.setColor(ecgPlotColor);
                    g.drawLine(lastPoint.x, lastPoint.y, x, y);

                    /*
                     * Set the current point to be the last, and 
                     * get a new point to plot in the following loop.
                     */
                    lastPoint.setLocation(x, y);
                    i += 1;
                    x = (int) (Double.valueOf(tableValues.getValueAt(i, 0).toString()) / plotZoom) - initialZero;
                    y = posOriginY - (int) (Double.valueOf(tableValues.getValueAt(i, 1).toString()) * frameAmplitude / paramOb.getAmplitude());
                }
            }
        }
    }


    /*
     * Class to plot the ECG animation
     */
    class ECGAnimate extends TimerTask {
        private int x = 0;
        private int y;
        private int curSecond = 0;
        private double lastSecond = 0;
        private final Graphics ga = ecgFrame.getGraphics();
        private EcgCalc.EcgMeasurement measure;

        public ECGAnimate() {
            this.measure = generator.next();
            this.y =  posOriginY - (int) ((measure.voltage) * frameAmplitude / paramOb.getAmplitude());
        }

        public void run() {
            curSecond = (int)measure.time;

            if (curSecond > lastSecond) {
                lastSecond = curSecond;
                /*
                 * Plot the X axes number values (the Time).
                 */
                ga.setColor(axesNumColor);
                drawText(ga, NumberFormatter.toString(measure.time, upLimit, loLimit, 2),
                        x, horizontalScaleY, horizontalScaleWidth, horizontalScaleHeight,
                        fScaleNumSize, LEFT);

                ga.setColor(frameInsideLineColor);
                ga.drawLine(x, posFrameY, x, horizontalScaleY + 5);
            }

            ga.setColor(ecgPlotColor);
            ga.drawLine(ecgAnimateLastPoint.x, ecgAnimateLastPoint.y, x, y);
            ecgAnimateCurRow += 1;
            measure =  generator.next();
            if (ecgAnimateCurRow >= ecgAnimateNumRows) {
                /*
                 * If we reach the end of the Data Table, loop again entire table.
                 */
                ecgFrame.repaint();
                ecgAnimateCurRow = 0;
                ecgAnimateInitialZero = 0;
                x = 0;

                y = posOriginY - (int) (measure.voltage * frameAmplitude / paramOb.getAmplitude());
                ecgAnimateLastPoint.setLocation(x, y);
                curSecond = 0;
                lastSecond = 0;

            } else if (x > ecgAnimatePanelWidth) {
                /*
                 * If we not reached the end of the Data Table, but we reach to the limit of
                 * the Plot Area. so reset the X coordinate to begin again.
                 */
                ecgFrame.repaint();
                x = 0;
                y = posOriginY - (int) (measure.voltage * frameAmplitude / paramOb.getAmplitude());
                ecgAnimateInitialZero = (int) (measure.time / plotZoom);
                ecgAnimateLastPoint.setLocation(x, y);
                //curSecond  = 0;
                //lastSecond = 0;
            } else {
                ecgAnimateLastPoint.setLocation(x, y);
                x = (int) (measure.time / plotZoom) - ecgAnimateInitialZero;
                y = posOriginY - (int) (measure.voltage * frameAmplitude / paramOb.getAmplitude());
            }
        }
    }
}
