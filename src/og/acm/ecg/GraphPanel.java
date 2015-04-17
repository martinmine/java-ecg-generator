package og.acm.ecg;/*
 * plotWindow.java
 *
 * See EcgLicense.txt for License terms.
 */

/**
 *
 * @author Mauricio Villarroel (m.villarroel@acm.og)
 */

import no.hig.imt3591.ecg.EcgProvider;
import no.hig.imt3591.ecg.PullService;

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

public class GraphPanel extends JPanel implements AdjustmentListener, EcgProvider {

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


    private double lastVoltage;


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

    private PullService pullService;

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
        zoomInButton = new JButton();
        zoomOutButton = new JButton();
        animateStartButton = new JButton();
        animateStopButton = new JButton();
        JPanel mainPanel = new JPanel();
        ecgPlotArea = new JScrollPane();
        plotScrollBar = new JScrollBar();
        lblMinAmplitude = new JLabel();
        JLabel lblOrigin = new JLabel();
        lblMaxAmplitude = new JLabel();
        JTextArea jTextArea1 = new JTextArea();
        tableValues = new JTable();
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

        add(mainPanel, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        /*
         * Clear Status text.
         */
        /*ecgLog.println("************************************************************");
        ecgLog.println("ECGSYN:\nA program for generating a realistic synthetic ECG\n");
        ecgLog.println("Copyright (c) 2003 by Patrick McSharry & Gari Clifford.");
        ecgLog.println("All rights reserved.");
        ecgLog.println("See IEEE Transactions On Biomedical Engineering, 50(3),\n289-294, March 2003.\n");
        ecgLog.println("Contact:\nP. McSharry (patrick@mcsharry.net)\nG. Clifford (gari@mit.edu)");
        ecgLog.println("************************************************************\n");*/
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
         * Call the ECG function to calculate the data into the Data Table.
         */
        ecgLog.println("Starting to generate values");
        long startTime = System.currentTimeMillis();
        this.generator = calcOb.calculateEcg();

        ecgLog.println("Completed generating values (" + (System.currentTimeMillis() - startTime) + " ms)");

        /*
         * Initialize ECG Animate variables
         */
        ecgAnimateNumRows = tableValuesModel.getRowCount();
        //ecgAnimateCurRow = 0;
        ecgAnimatePanelWidth = ecgFrame.getBounds().width;
        //ecgAnimateInitialZero = 0;


        /* Schedule the Animate Plotting Task */
        if (ecgAnimateTimer != null) {
            ecgAnimateTimer.cancel();
            ecgAnimateTimer.purge();
        }

        ecgAnimateTimer = new Timer();
        if (ecgAnimate == null) {
            ecgAnimate = new ECGAnimate();
        } else {
            ecgAnimate = new ECGAnimate(ecgAnimate);
        }

        ecgAnimateTimer.scheduleAtFixedRate(ecgAnimate, 0, paramOb.getEcgAnimateInterval());

        if (pullService == null) {
            pullService = new PullService(this);
        }
    }

    private ECGAnimate ecgAnimate;

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

    @Override
    public double getVoltage() {
        return this.lastVoltage;
    }

    @Override
    public double getPulse() {
        return paramOb.getHrMean();
    }

    @Override
    public double getOxygenSaturation() {
        return 0;
    }

    @Override
    public double getSkinResistance() {
        return 0;
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

    @Override
    public Point getLastPoint() {
        return ecgAnimateLastPoint;
    }

    @Override
    public Graphics getGraphGraphics() {
        return ecgFrame.getGraphics();
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
        private double totTime;
        private double curTime;

        public ECGAnimate() {
            this.measure = generator.next();
            this.y =  posOriginY - (int) ((measure.voltage) * frameAmplitude / paramOb.getAmplitude());
        }

        public ECGAnimate(ECGAnimate parent) {
            this.x = parent.x;
            this.y = parent.y;
            this.curSecond = parent.curSecond;
            this.lastSecond = parent.lastSecond;
            this.measure = generator.next();
            this.totTime = parent.totTime + parent.curTime;
        }

        public void run() {
            curTime = measure.time;
            lastVoltage = measure.voltage;
            final double t = curTime + totTime;

            curSecond = (int)t;

            if (curSecond > lastSecond) {
                lastSecond = curSecond;
                /*
                 * Plot the X axes number values (the Time).
                 */
                ga.setColor(axesNumColor);
                drawText(ga, NumberFormatter.toString(t, upLimit, loLimit, 2),
                        x, horizontalScaleY, horizontalScaleWidth, horizontalScaleHeight,
                        fScaleNumSize, LEFT);

                ga.setColor(frameInsideLineColor);
                ga.drawLine(x, posFrameY, x, horizontalScaleY + 5);
            }

            ga.setColor(ecgPlotColor);
            ga.drawLine(ecgAnimateLastPoint.x, ecgAnimateLastPoint.y, x, y);
            ecgAnimateCurRow++;
            measure = generator.next();

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
                ecgAnimateInitialZero = (int) (t / plotZoom);
                ecgAnimateLastPoint.setLocation(x, y);
                //curSecond  = 0;
                //lastSecond = 0;
            } else {
                ecgAnimateLastPoint.setLocation(x, y);
                x = (int) (t / plotZoom) - ecgAnimateInitialZero;
                y = posOriginY - (int) (measure.voltage * frameAmplitude / paramOb.getAmplitude());
            }
        }
    }
}
