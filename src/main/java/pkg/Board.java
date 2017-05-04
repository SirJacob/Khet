package pkg;

import java.awt.Graphics;
import javax.swing.JPanel;
//<editor-fold defaultstate="collapsed" desc="Static Imports from Piece">
import static pkg.Piece.NORTH;
import static pkg.Piece.EAST;
import static pkg.Piece.SOUTH;
import static pkg.Piece.WEST;

import static pkg.Piece.SILVER;
import static pkg.Piece.RED;

import static pkg.Piece.PYRAMID;
import static pkg.Piece.PHARAOH;
import static pkg.Piece.ANUBIS;
import static pkg.Piece.SPHINX;
import static pkg.Piece.SCARAB;
//</editor-fold>

/**
 *
 * @author https://github.com/SirJacob/Khet/graphs/contributors
 */
public class Board extends javax.swing.JFrame {

    private Piece[][] board = new Piece[8][10];
    //<editor-fold defaultstate="collapsed" desc="Default Board Setups">
    static final Piece[][] CLASSIC = {
        {new Piece(SPHINX, RED, SOUTH), null, null, null, new Piece(ANUBIS, RED, SOUTH), new Piece(PHARAOH, RED, SOUTH), new Piece(ANUBIS, RED, SOUTH), new Piece(PYRAMID, RED, EAST), null, null},
        {null, null, new Piece(PYRAMID, RED, SOUTH), null, null, null, null, null, null, null},
        {null, null, null, new Piece(PYRAMID, SILVER, WEST), null, null, null, null, null, null},
        {new Piece(PYRAMID, RED, NORTH), null, new Piece(PYRAMID, SILVER, SOUTH), null, new Piece(SCARAB, RED, SOUTH), new Piece(SCARAB, RED, EAST), null, new Piece(PYRAMID, RED, EAST), null, new Piece(PYRAMID, SILVER, WEST)},
        {new Piece(PYRAMID, RED, EAST), null, new Piece(PYRAMID, SILVER, WEST), null, new Piece(SCARAB, SILVER, EAST), new Piece(SCARAB, SILVER, SOUTH), null, new Piece(PYRAMID, RED, NORTH), null, new Piece(PYRAMID, SILVER, SOUTH)},
        {null, null, null, null, null, null, new Piece(PYRAMID, RED, EAST), null, null, null},
        {null, null, null, null, null, null, null, new Piece(PYRAMID, SILVER, NORTH), null, null},
        {null, null, new Piece(PYRAMID, SILVER, WEST), new Piece(ANUBIS, SILVER, NORTH), new Piece(PHARAOH, SILVER, NORTH), new Piece(ANUBIS, SILVER, NORTH), null, null, null, new Piece(SPHINX, SILVER, NORTH)}
    };
    static final Piece[][] IMHOTEP = {
        {new Piece(SPHINX, RED, SOUTH), null, null, null, new Piece(ANUBIS, RED, SOUTH), new Piece(PHARAOH, RED, SOUTH), new Piece(ANUBIS, RED, SOUTH), new Piece(SCARAB, RED, EAST), null, null},
        {null, null, null, null, null, null, null, null, null, null},
        {null, null, null, new Piece(PYRAMID, SILVER, WEST), null, null, new Piece(PYRAMID, RED, NORTH), null, null, null},
        {new Piece(PYRAMID, RED, NORTH), new Piece(PYRAMID, SILVER, SOUTH), null, null, new Piece(PYRAMID, SILVER, EAST), new Piece(SCARAB, RED, EAST), null, null, new Piece(PYRAMID, RED, EAST), new Piece(PYRAMID, SILVER, WEST)},
        {new Piece(PYRAMID, RED, EAST), new Piece(PYRAMID, SILVER, WEST), null, null, new Piece(SCARAB, SILVER, EAST), new Piece(PYRAMID, RED, NORTH), null, null, new Piece(PYRAMID, RED, NORTH), new Piece(PYRAMID, SILVER, SOUTH)},
        {null, null, null, new Piece(PYRAMID, SILVER, SOUTH), null, null, new Piece(PYRAMID, RED, EAST), null, null, null},
        {null, null, null, null, null, null, null, null, null, null},
        {null, null, new Piece(SCARAB, SILVER, EAST), new Piece(ANUBIS, SILVER, NORTH), new Piece(PHARAOH, SILVER, NORTH), new Piece(ANUBIS, SILVER, NORTH), null, null, null, new Piece(SPHINX, SILVER, NORTH)}
    };
    static final Piece[][] DYNASTY = {
        {new Piece(SPHINX, RED, SOUTH), null, null, null, new Piece(PYRAMID, RED, SOUTH), new Piece(ANUBIS, RED, SOUTH), new Piece(PYRAMID, RED, EAST), null, null, null, null},
        {null, null, null, null, null, new Piece(PHARAOH, RED, SOUTH), null, null, null, null, null},
        {new Piece(PYRAMID, RED, NORTH), new Piece(SCARAB, RED, NORTH), null, null, new Piece(PYRAMID, RED, SOUTH), new Piece(ANUBIS, RED, SOUTH), new Piece(PYRAMID, RED, EAST), null, null, null, null},
        {new Piece(PYRAMID, RED, EAST), null, new Piece(PYRAMID, RED, SOUTH), null, new Piece(PYRAMID, SILVER, EAST), null, new Piece(PYRAMID, SILVER, EAST), null, null, null, null},
        {null, null, null, new Piece(PYRAMID, RED, WEST), null, new Piece(PYRAMID, RED, EAST), null, new Piece(SCARAB, SILVER, SOUTH), null, new Piece(PYRAMID, SILVER, EAST), null},
        {null, null, null, new Piece(SCARAB, SILVER, EAST), new Piece(ANUBIS, SILVER, NORTH), new Piece(PYRAMID, SILVER, NORTH), null, null, null, new Piece(PYRAMID, SILVER, SOUTH), null},
        {null, null, null, null, new Piece(PHARAOH, SILVER, NORTH), null, null, null, null, null, null},
        {null, null, null, new Piece(PYRAMID, SILVER, WEST), new Piece(ANUBIS, SILVER, NORTH), new Piece(PYRAMID, SILVER, NORTH), null, null, null, new Piece(SPHINX, SILVER, NORTH), null}
    };
//</editor-fold>

    /**
     * Creates new form Board
     */
    public Board() {
        initComponents();
        setSize(8 * 50, 10 * 50);
        setLocationRelativeTo(null);
        JPanel jPanel = (JPanel) getContentPane();
        jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int r = 0; r < board[r].length; r++) {
                    for (int c = 0; c < board.length; c++) {
                        if (board[r][c] != null) {
                            g.drawImage(board[r][c].image, r * 50, c * 50, 50, 50, null);
                        }
                    }
                }
            }
        };
    }

    void setBoard(Piece[][] board) {
        this.board = board;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
