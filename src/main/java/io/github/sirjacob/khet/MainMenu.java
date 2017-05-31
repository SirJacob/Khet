package io.github.sirjacob.khet;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author https://github.com/SirJacob/Khet/graphs/contributors
 */
class MainMenu extends JFrame {

    private static final int CUSTOM_WIDTH = 600;
    private static final int CUSTOM_HEIGHT = 382;

    /**
     * Creates the new form MainMenu
     */
    public MainMenu() {
        initComponents();
        //Sets the size of the window and background.
        setSize(CUSTOM_WIDTH, CUSTOM_HEIGHT);
        lblBackground.setSize(CUSTOM_WIDTH, CUSTOM_HEIGHT);

        lblBackground.setLocation(0, 0);
        setLocationRelativeTo(null); //Centers the window in the middle of the screen.

        /* Customizes the look and location of the JButtons */
        makeTransparentBtn(btnClassic);
        btnClassic.setBounds(0, 0, CUSTOM_WIDTH, 90);

        makeTransparentBtn(btnImhotep);
        btnImhotep.setBounds(0, 90, CUSTOM_WIDTH, 90);

        makeTransparentBtn(btnDynasty);
        btnDynasty.setBounds(0, 180, CUSTOM_WIDTH, 90);

        makeTransparentBtn(btnQuit);
        btnQuit.setBounds(0, CUSTOM_HEIGHT - 90, CUSTOM_WIDTH, 90);
    }

    /**
     * Alters a JButton to remove all decorations surrounding the text of the
     * button.
     *
     * @param jButton Applies modifications to the given JButton.
     */
    private static void makeTransparentBtn(JButton jButton) {
        jButton.setOpaque(false);
        jButton.setContentAreaFilled(false);
        jButton.setBorderPainted(false);
    }

    /**
     * Creates a new Board, makes it visible, tells the new Board instance what
     * default board setup it should use, and then disposes of the MainMenu
     * instance.
     *
     * @param selectedBoard What default board setup the new instance of Board
     * should use.
     */
    private void setup(Piece[][] selectedBoard) {
        Board board = new Board();
        board.setVisible(true);
        board.setBoard(selectedBoard);
        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnClassic = new javax.swing.JButton();
        btnImhotep = new javax.swing.JButton();
        btnDynasty = new javax.swing.JButton();
        btnQuit = new javax.swing.JButton();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        getContentPane().setLayout(null);

        btnClassic.setFont(new java.awt.Font("Maiandra GD", 1, 24)); // NOI18N
        btnClassic.setForeground(new java.awt.Color(255, 255, 255));
        btnClassic.setText("Classic");
        btnClassic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClassicActionPerformed(evt);
            }
        });
        getContentPane().add(btnClassic);
        btnClassic.setBounds(10, 6, 380, 39);

        btnImhotep.setFont(new java.awt.Font("Maiandra GD", 1, 24)); // NOI18N
        btnImhotep.setForeground(new java.awt.Color(255, 255, 255));
        btnImhotep.setText("Imhotep");
        btnImhotep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImhotepActionPerformed(evt);
            }
        });
        getContentPane().add(btnImhotep);
        btnImhotep.setBounds(10, 51, 380, 39);

        btnDynasty.setFont(new java.awt.Font("Maiandra GD", 1, 24)); // NOI18N
        btnDynasty.setForeground(new java.awt.Color(255, 255, 255));
        btnDynasty.setText("Dynasty");
        btnDynasty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDynastyActionPerformed(evt);
            }
        });
        getContentPane().add(btnDynasty);
        btnDynasty.setBounds(10, 96, 380, 39);

        btnQuit.setFont(new java.awt.Font("Maiandra GD", 1, 24)); // NOI18N
        btnQuit.setForeground(new java.awt.Color(255, 255, 255));
        btnQuit.setText("Quit");
        btnQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitActionPerformed(evt);
            }
        });
        getContentPane().add(btnQuit);
        btnQuit.setBounds(10, 153, 380, 39);

        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/egyptian-hieroglyphics.jpg"))); // NOI18N
        getContentPane().add(lblBackground);
        lblBackground.setBounds(0, 0, 410, 200);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Handles pressing the "Classic" JButton.
     *
     * @param evt
     */
    private void btnClassicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClassicActionPerformed
        setup(Board.CLASSIC);
    }//GEN-LAST:event_btnClassicActionPerformed
    /**
     * Handles pressing the "Imhotep" JButton.
     *
     * @param evt
     */
    private void btnImhotepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImhotepActionPerformed
        setup(Board.IMHOTEP);
    }//GEN-LAST:event_btnImhotepActionPerformed
    /**
     * Handles pressing the "Dynasty" JButton.
     *
     * @param evt
     */
    private void btnDynastyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDynastyActionPerformed
        setup(Board.DYNASTY);
    }//GEN-LAST:event_btnDynastyActionPerformed
    /**
     * Handles pressing the "Quit" JButton. Exits the application.
     *
     * @param evt
     */
    private void btnQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnQuitActionPerformed

    /**
     * Only main method within the program; used to create a new instance of
     * MainMenu and make it visible.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClassic;
    private javax.swing.JButton btnDynasty;
    private javax.swing.JButton btnImhotep;
    private javax.swing.JButton btnQuit;
    private javax.swing.JLabel lblBackground;
    // End of variables declaration//GEN-END:variables
}
