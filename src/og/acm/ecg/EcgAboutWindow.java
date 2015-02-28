package og.acm.ecg;/*
 * aboutECG.java
 *
 * See EcgLicense.txt for License terms.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Mauricio Villarroel (m.villarroel@acm.og)
 */

public class EcgAboutWindow extends javax.swing.JDialog {

    private JEditorPane txtEditor;
    /**
     * Creates new form aboutECG
     */
    public EcgAboutWindow(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setSize(400, 530);
        setHelpText();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {
        JDesktopPane jDesktopPane1 = new JDesktopPane();
        JLabel jLabel1 = new JLabel();
        JButton okButton = new JButton();
        JScrollPane jScrollPane1 = new JScrollPane();
        txtEditor = new JEditorPane();
        JLabel jLabel2 = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About ECG...");
        setModal(true);
        jLabel1.setFont(new Font("MS Sans Serif", Font.PLAIN, 18));
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Java ECG Generator");
        jLabel1.setBounds(80, 20, 230, 30);
        jDesktopPane1.add(jLabel1, JLayeredPane.DEFAULT_LAYER);

        okButton.setText("OK");
        okButton.addActionListener(EcgAboutWindow.this::okButtonActionPerformed);

        okButton.setBounds(140, 450, 73, 25);
        jDesktopPane1.add(okButton, JLayeredPane.DEFAULT_LAYER);

        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        txtEditor.setEditable(false);
        txtEditor.setContentType("text/html");
        jScrollPane1.setViewportView(txtEditor);

        jScrollPane1.setBounds(14, 84, 360, 350);
        jDesktopPane1.add(jScrollPane1, JLayeredPane.DEFAULT_LAYER);

        jLabel2.setFont(new Font("MS Sans Serif", Font.PLAIN, 14));
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText("version 1.0");
        jLabel2.setBounds(220, 50, 140, 30);
        jDesktopPane1.add(jLabel2, JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(jDesktopPane1, BorderLayout.CENTER);

        pack();
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void setHelpText() {
        txtEditor.setText("<html> <body>" +
                " <table>" +
                " <tr>" +
                " <td width='300' align='left' valign='top'>" +
                "       <p><strong>&copy;</strong> Java code Copyright by Mauricio Villarroel.</p>" +
                "       <p>Java ECG Generator was developed for ECGSYN.</p>" +
                "       <p><strong>&copy;</strong> ECGSYN Copyright by Patrick E. McSharry and Gari D. Clifford.</p>" +
                "       For the Mathematical Model, see:</p>" +
                "       <table width='100%' border='1' cellspacing='0' cellpadding='5'>" +
                "         <tr> " +
                "           <td align='left' valign='top'>IEEE Transactions On Biomedical Engineering, " +
                "             50(3), 289-294, March 2003</td>" +
                "         </tr>" +
                "       </table>" +
                "       <p> Contact: </p>" +
                "       <ul>" +
                "         <li>Patrck McSharry (<a href='mailto:patrick@mcsharry.net'>patrick@mcsharry.net</a>)</li>" +
                "         <li>Gari Clifford (<a href='mailto:gari@mit.edu'>gari@mit.edu</a>)</li>" +
                "         <li>Mauricio Villarroel (<a href='mailto:m.villarroel@acm.org'>m.villarroel@acm.org</a>)</li>" +
                "       </ul>" +
                "       <p align='justify'>Java ECG Generator and all its components are free software. You can" +
                "          redistribute them or modify it under the terms of the" +
                "          GNU General Public License as published by the Free Software" +
                "          Foundation; either version 2 of the License, or (at your option)" +
                "          any later version.</p></td>" +
                " </tr>" +
                " </table>" +
                "</body> </html>");
    }
}
