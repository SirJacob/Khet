package pkg;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
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

    private final Image BACKGROUND = new ImageIcon(this.getClass().getResource("/board.png")).getImage();
    private Piece[][] board;
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
        setSize(BACKGROUND.getWidth(null), BACKGROUND.getHeight(null));
        setLocationRelativeTo(null);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                paintComponent(getContentPane().getGraphics());
            }
        }, 500, 1000);
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(BACKGROUND, 0, 0, null);
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 10; c++) {
                if (board[r][c] != null) {
                    if (board[r][c].getRotation() != EAST) {
                        Graphics2D g2d = (Graphics2D) g;
                        AffineTransformOp op = new AffineTransformOp(rotate(board[r][c].image, (c * 125) - c, r * 125, board[r][c].getRotation()), AffineTransformOp.TYPE_BILINEAR);
                        g2d.drawImage(board[r][c].image, op.getTransform(), null);
                    } else {
                        g.drawImage(board[r][c].image, (c * 125) - c, r * 125, 125, 125, null);
                    }
                }
            }
        }
    }

    private AffineTransform rotate(Image source, int x, int y, int newDirection) {
        AffineTransform transform = new AffineTransform();
        transform.scale(.25, .25);
        transform.translate(x * 4, y * 4);

        double theta = 0.0;
        switch (newDirection) {
            case NORTH:
                theta = Math.PI + (Math.PI / 2);
                break;
            case SOUTH:
                theta = Math.PI / 2;
                break;
            case WEST:
                theta = Math.PI;
                break;
        }
        transform.rotate(theta, source.getWidth(null) / 2, source.getHeight(null) / 2);

        return transform;
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
        setUndecorated(true);

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
