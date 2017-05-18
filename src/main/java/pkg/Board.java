package pkg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import static java.awt.event.KeyEvent.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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

    private boolean isRedTurn = false; //Rule #2
    private Point selectedPiece = new Point(-1, -1);
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
        {new Piece(PYRAMID, RED, EAST), new Piece(PYRAMID, SILVER, WEST), null, null, new Piece(SCARAB, SILVER, EAST), new Piece(PYRAMID, RED, WEST), null, null, new Piece(PYRAMID, RED, NORTH), new Piece(PYRAMID, SILVER, SOUTH)},
        {null, null, null, new Piece(PYRAMID, SILVER, SOUTH), null, null, new Piece(PYRAMID, RED, EAST), null, null, null},
        {null, null, null, null, null, null, null, null, null, null},
        {null, null, new Piece(SCARAB, SILVER, EAST), new Piece(ANUBIS, SILVER, NORTH), new Piece(PHARAOH, SILVER, NORTH), new Piece(ANUBIS, SILVER, NORTH), null, null, null, new Piece(SPHINX, SILVER, NORTH)}
    };
    static final Piece[][] DYNASTY = {
        {new Piece(SPHINX, RED, SOUTH), null, null, null, new Piece(PYRAMID, RED, SOUTH), new Piece(ANUBIS, RED, SOUTH), new Piece(PYRAMID, RED, EAST), null, null, null},
        {null, null, null, null, null, new Piece(PHARAOH, RED, SOUTH), null, null, null, null},
        {new Piece(PYRAMID, RED, NORTH), null, null, null, new Piece(PYRAMID, RED, SOUTH), new Piece(ANUBIS, RED, SOUTH), new Piece(PYRAMID, RED, EAST), null, null, null},
        {new Piece(PYRAMID, RED, EAST), null, new Piece(SCARAB, RED, SOUTH), null, new Piece(PYRAMID, SILVER, WEST), null, new Piece(PYRAMID, SILVER, EAST), null, null, null},
        {null, null, null, new Piece(PYRAMID, RED, WEST), null, new Piece(PYRAMID, RED, EAST), null, new Piece(SCARAB, SILVER, SOUTH), null, new Piece(PYRAMID, SILVER, WEST)},
        {null, null, null, new Piece(SCARAB, SILVER, EAST), new Piece(ANUBIS, SILVER, NORTH), new Piece(PYRAMID, SILVER, NORTH), null, null, null, new Piece(PYRAMID, SILVER, SOUTH)},
        {null, null, null, null, new Piece(PHARAOH, SILVER, NORTH), null, null, null, null, null},
        {null, null, null, new Piece(PYRAMID, SILVER, WEST), new Piece(ANUBIS, SILVER, NORTH), new Piece(PYRAMID, SILVER, NORTH), null, null, null, new Piece(SPHINX, SILVER, NORTH)}
    };
//</editor-fold>

    /**
     * Creates new form Board
     */
    public Board() {
        initComponents();
        setSize(BACKGROUND.getWidth(null), BACKGROUND.getHeight(null));
        setLocationRelativeTo(null);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(BACKGROUND, 0, 0, null);
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 10; c++) {
                if (board[r][c] != null) {
                    if (board[r][c].getRotation() != EAST) {
                        AffineTransformOp op = new AffineTransformOp(rotate(board[r][c].image, (c * 125) - c, r * 125, board[r][c].getRotation()), AffineTransformOp.TYPE_BILINEAR);
                        g2d.drawImage(board[r][c].image, op.getTransform(), null);
                    } else {
                        g.drawImage(board[r][c].image, (c * 125) - c, r * 125, 125, 125, null);
                    }
                }
            }
        }
        if (isSelected()) {
            g.setColor(Color.YELLOW);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRect((selectedPiece.y * 125) - selectedPiece.y + 2, (selectedPiece.x * 125) + 2, 120, 120);
        }
    }

    //Rule #2
    private void switchTurn() {
        isRedTurn = !isRedTurn;
        selectedPiece.setLocation(-1, -1);
        redraw();
    }

    private boolean isSelected() {
        return selectedPiece.x >= 0 && selectedPiece.y >= 0;
    }

    /**
     *
     * @param r
     * @param c
     * @return If movement by selected piece is allowed based on Ankh or Eye of
     * Horus squares.
     */
    private boolean ankhCheck(int r, int c) { //Rule #5
        int x = selectedPiece.x;
        int y = selectedPiece.y;
        boolean red = board[x][y].isRed();

        if (!red && c == 0) {
            return false;
        } else if (red && c == 9) {
            return false;
        } else if (red && r == 0 && c == 1) {
            return false;
        } else if (red && r == 7 && c == 1) {
            return false;
        } else if (!red && r == 0 && c == 8) {
            return false;
        } else if (!red && r == 7 && c == 8) {
            return false;
        }

        return true;
    }

    private void redraw() {
        paintComponent(getContentPane().getGraphics());
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

    //Rule #1
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

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

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        int x = evt.getX();
        int y = evt.getY();
        for (int r = 0; r < 8 + 1; r++) {
            for (int c = 0; c < 10 + 1; c++) {
                if (x <= (c * 125) - c
                        && x >= ((c - 1) * 125) - (c - 1)
                        && y <= r * 125
                        && y >= (r - 1) * 125) {
                    System.out.println(String.format("Selected:\t board[%d][%d]", r - 1, c - 1));
                    //Rule #2
                    if (board[r - 1][c - 1] != null && board[r - 1][c - 1].isRed() == isRedTurn) {
                        selectedPiece.setLocation(r - 1, c - 1);
                    } else {
                        selectedPiece.setLocation(-1, -1);
                    }
                }
            }
        }
        redraw();
    }//GEN-LAST:event_formMouseReleased

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if (evt.getKeyCode() == VK_ESCAPE) {
            new MainMenu().setVisible(true);
            dispose();
        }
        //Rule #4
        if (isSelected()) {
            int r = selectedPiece.x;
            int c = selectedPiece.y;
            switch (evt.getKeyCode()) {
                case VK_Q:
                    //Rule #3
                    if ((board[r][c].TYPE == SPHINX && board[r][c].isRed() && board[r][c].getRotation() == EAST)
                            || (board[r][c].TYPE == SPHINX && !board[r][c].isRed() && board[r][c].getRotation() == WEST)) {
                        break;
                    }
                    board[r][c].turnLeft();
                    switchTurn();
                    break;
                case VK_E:
                    //Rule #3
                    if ((board[r][c].TYPE == SPHINX && board[r][c].isRed() && board[r][c].getRotation() == SOUTH)
                            || (board[r][c].TYPE == SPHINX && !board[r][c].isRed() && board[r][c].getRotation() == NORTH)) {
                        break;
                    }
                    board[r][c].turnRight();
                    switchTurn();
                    break;
                //`board[r][c].TYPE != SPHINX` - Rule #3
                case VK_W:
                    if (board[r - 1][c] == null
                            && ankhCheck(r - 1, c)
                            && board[r][c].TYPE != SPHINX) {
                        board[r - 1][c] = board[r][c];
                        board[r][c] = null;
                        switchTurn();
                    }
                    break;
                case VK_A:
                    if (board[r][c - 1] == null
                            && ankhCheck(r, c - 1)
                            && board[r][c].TYPE != SPHINX) {
                        board[r][c - 1] = board[r][c];
                        board[r][c] = null;
                        switchTurn();
                    }
                    break;
                case VK_S:
                    if (board[r + 1][c] == null
                            && ankhCheck(r + 1, c)
                            && board[r][c].TYPE != SPHINX) {
                        board[r + 1][c] = board[r][c];
                        board[r][c] = null;
                        switchTurn();
                    }
                    break;
                case VK_D:
                    if (board[r][c + 1] == null
                            && ankhCheck(r, c + 1)
                            && board[r][c].TYPE != SPHINX) {
                        board[r][c + 1] = board[r][c];
                        board[r][c] = null;
                        switchTurn();
                    }
                    break;
            }
        }
    }//GEN-LAST:event_formKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
