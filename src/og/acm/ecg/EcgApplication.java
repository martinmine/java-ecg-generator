package og.acm.ecg;
/*
 * ecgApplication.java
 *
 * See EcgLicense.txt for License terms.
 */

/**
 *
 * @author Mauricio Villarroel (m.villarroel@acm.og)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EcgApplication extends javax.swing.JFrame {

    /* Main Calculation Objects */
    private EcgParam paramOb;

    /* Main GUI-Window Objects*/
    private EcgParamWindow paramWin;
    private EcgLogWindow logWin;
    private JMenuItem aboutMenu;
    private JMenu appMenu;
    private JMenuItem generateMenu;
    private JMenu helpMenu;
    private JMenuBar jMenuBar1;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JLabel mainLabel;
    private JMenuItem paramMenu;
    private JMenuItem quitMenu;
    private JMenu settingsMenu;
    private JMenuItem sysLogMenu;
    private JMenu systemMenu;
    /**
     * Creates new form ecgApplication
     */
    public EcgApplication() {
        initComponents();
        initClasses();
        initWindow();
        initGraphing();
    }

    private void initGraphing() {
        EcgPlotWindow plotWin = new EcgPlotWindow(paramOb, logWin, this);
        add(plotWin, BorderLayout.CENTER);
        //plotWin.show();
}

    /**
     * Main entry for the application
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        EcgApplication app = new EcgApplication();
        app.setVisible(true);
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /*
     * Init Child Classes
     */
    private void initClasses() {
        paramOb = new EcgParam();
        logWin = new EcgLogWindow();
        paramWin = new EcgParamWindow(paramOb, logWin);
    }

    private void initWindow() {
        this.setSize(880, 600);
        setLayout(new BorderLayout());
        mainLabel.setSize(this.getWidth(), this.getHeight());

        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new FlowLayout());

        JButton settingBtn = new JButton("Parameters");
        settingBtn.addActionListener((ActionEvent e) -> paramWin.setVisible(true));
        toolPanel.add(settingBtn);

        JButton logBtn = new JButton("Show log");
        logBtn.addActionListener((ActionEvent e) -> logWin.setVisible(true));
        toolPanel.add(logBtn);

        add(toolPanel, BorderLayout.SOUTH);
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {
        mainLabel = new JLabel();
        jMenuBar1 = new JMenuBar();
        appMenu = new JMenu();
        jSeparator1 = new JSeparator();
        quitMenu = new JMenuItem();
        settingsMenu = new JMenu();
        paramMenu = new JMenuItem();
        systemMenu = new JMenu();
        generateMenu = new JMenuItem();
        sysLogMenu = new JMenuItem();
        helpMenu = new JMenu();
        jSeparator2 = new JSeparator();
        aboutMenu = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Java ECG Generator");
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitForm(evt);
            }
        });

        /*mainLabel.setFont(new Font("MS Sans Serif", Font.PLAIN, 36));
        mainLabel.setForeground(new Color(255, 255, 255));
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainLabel.setText("Java ECG Generator");
        mainLabel.setBounds(0, 0, 530, 260);
        mainDesktop.add(mainLabel, JLayeredPane.DEFAULT_LAYER);*/

        appMenu.setText("Application");
        appMenu.add(jSeparator1);

        quitMenu.setText("Quit");
        quitMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                quitMenuActionPerformed(evt);
            }
        });

        appMenu.add(quitMenu);
        jMenuBar1.add(appMenu);

        settingsMenu.setText("Settings");
        paramMenu.setText("ECG Parameters");
        paramMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                paramMenuActionPerformed(evt);
            }
        });

        settingsMenu.add(paramMenu);
        jMenuBar1.add(settingsMenu);

        systemMenu.setText("ECG");
        generateMenu.setText("Generate ECG");
        generateMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                generateMenuActionPerformed(evt);
            }
        });

        systemMenu.add(generateMenu);
        sysLogMenu.setText("ECG System Log");
        sysLogMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                sysLogMenuActionPerformed(evt);
            }
        });

        systemMenu.add(sysLogMenu);
        jMenuBar1.add(systemMenu);

        helpMenu.setText("Help");
        helpMenu.add(jSeparator2);

        aboutMenu.setText("About ECG ...");
        aboutMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                aboutMenuActionPerformed(evt);
            }
        });

        helpMenu.add(aboutMenu);
        jMenuBar1.add(helpMenu);
        setJMenuBar(jMenuBar1);
        pack();
    }

    private void formComponentResized(ComponentEvent evt) {
        mainLabel.setSize(this.getWidth(), this.getHeight());
    }

    private void sysLogMenuActionPerformed(ActionEvent evt) {
        logWin.show();
    }

    private void generateMenuActionPerformed(ActionEvent evt) {
    }

    private void paramMenuActionPerformed(ActionEvent evt) {
        paramWin.show();
    }

    private void aboutMenuActionPerformed(ActionEvent evt) {
        EcgAboutWindow aboutDialog = new EcgAboutWindow(this, true);
        aboutDialog.setVisible(true);
    }

    private void quitMenuActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    /**
     * Exit the Application
     */
    private void exitForm(WindowEvent evt) {
        this.dispose();
    }
}
