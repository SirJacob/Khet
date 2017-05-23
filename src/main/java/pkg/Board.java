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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
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
class Board extends javax.swing.JFrame {

    private boolean isRedTurn = false; //Rule #2
    private final Point selectedPiece = new Point(-1, -1);
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
    static final Piece[][] DEBUG = {
        {new Piece(PYRAMID, RED, NORTH), new Piece(PYRAMID, RED, EAST), new Piece(PYRAMID, RED, SOUTH), new Piece(PYRAMID, RED, WEST), null, null, null, null, null, null},
        {new Piece(PHARAOH, RED, NORTH), new Piece(PHARAOH, RED, EAST), new Piece(PHARAOH, RED, SOUTH), new Piece(PHARAOH, RED, WEST), null, null, null, null, null, null},
        {new Piece(ANUBIS, RED, NORTH), new Piece(ANUBIS, RED, EAST), new Piece(ANUBIS, RED, SOUTH), new Piece(ANUBIS, RED, WEST), null, null, null, null, null, null},
        {new Piece(SPHINX, RED, NORTH), new Piece(SPHINX, RED, EAST), new Piece(SPHINX, RED, SOUTH), new Piece(SPHINX, RED, WEST), null, null, null, null, null, null},
        {new Piece(SCARAB, RED, NORTH), new Piece(SCARAB, RED, EAST), new Piece(SCARAB, RED, SOUTH), new Piece(SCARAB, RED, WEST), null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null}
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

    private void paintComponent(Graphics g) {
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
        paintSelectedBorder();
    }

    private void paintSelectedBorder() {
        Graphics g = getContentPane().getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        if (isPieceSelected()) {
            g.setColor(Color.YELLOW);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRect((selectedPiece.y * 125) - selectedPiece.y + 2, (selectedPiece.x * 125) + 2, 120, 120);
        }
    }

    //Rule #2
    private void switchTurn() {
        selectedPiece.setLocation(-1, -1);
        redraw();
        if (isRedTurn) {
            fireLaser(0, 0, board[0][0].getRotation());
        } else {
            fireLaser(7, 9, board[7][9].getRotation());
        }
        isRedTurn = !isRedTurn;
        redraw();
    }

    private void endGame(boolean isRedLoser) {
        JOptionPane.showMessageDialog(this, isRedLoser ? "Silver" : "Red" + " has won!", "Khet - Game Over", JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    }

    private void fireLaser(final int sourceR, final int sourceC, final int fireDirection) {
        switch (fireDirection) {
            case NORTH:
                fireLaserSearchY(sourceR, -1, sourceC);
                break;
            case SOUTH:
                fireLaserSearchY(sourceR, 1, sourceC);
                break;
            case WEST:
                fireLaserSearchX(sourceC, -1, sourceR);
                break;
            case EAST:
                fireLaserSearchX(sourceC, 1, sourceR);
                break;
        }
    }

    private void fireLaserSearchY(final int sourceR, final int direction, final int sourceC) {
        for (int r = sourceR + direction; r >= 0 && r <= 7; r += direction) {
            if (board[r][sourceC] != null) {
                fireLaserCollide(sourceR, sourceC, r, sourceC, direction == 1 ? NORTH : SOUTH);
                return;
            }
        }
        System.out.println("The laser went out of bounds.");
        if (direction != 1) {
            drawLaser(sourceC * 125 + 60, sourceR * 125 + 60, sourceC * 125 + 60, 0);
        } else {
            drawLaser(sourceC * 125 + 60, sourceR * 125 + 60, sourceC * 125 + 60, getHeight());
        }
    }

    private void fireLaserSearchX(final int sourceC, final int direction, final int sourceR) {
        for (int c = sourceC + direction; c >= 0 && c <= 9; c += direction) {
            if (board[sourceR][c] != null) {
                fireLaserCollide(sourceR, sourceC, sourceR, c, direction == 1 ? WEST : EAST);
                return;
            }
        }
        System.out.println("The laser went out of bounds.");
        if (direction != 1) {
            drawLaser(sourceC * 125 + 60, sourceR * 125 + 60, 0, sourceR * 125 + 60);
        } else {
            drawLaser(sourceC * 125 + 60, sourceR * 125 + 60, getWidth(), sourceR * 125 + 60);
        }
    }

    private void fireLaserCollide(final int sourceR, final int sourceC, final int targetR, final int targetC, final int impact) {
        Piece sourcePiece = board[sourceR][sourceC];
        Piece targetPiece = board[targetR][targetC];
        String debug = String.format("%s %s [%s,%s] looking %s hit %s %s [%s,%s] looking %s. Impact from the %s side.",
                sourcePiece.getColor(), sourcePiece.getPieceName(), sourceR, sourceC, sourcePiece.getRotationString(),
                targetPiece.getColor(), targetPiece.getPieceName(), targetR, targetC, targetPiece.getRotationString(), Piece.rotationIntToStr(impact));
        System.out.println(debug);

        drawLaser(sourceC * 125 + 60, sourceR * 125 + 60, targetC * 125 + 60, targetR * 125 + 60);

        int fireDirection;
        if (((targetPiece.TYPE == PYRAMID || targetPiece.TYPE == SCARAB) && impact == targetPiece.getRotation())) {
            fireDirection = targetPiece.getRight();
        } else if (((targetPiece.TYPE == PYRAMID || targetPiece.TYPE == SCARAB) && impact == targetPiece.getRight())) {
            fireDirection = targetPiece.getRotation();
        } else if (targetPiece.TYPE == SCARAB && impact == targetPiece.getBehind()) {
            fireDirection = targetPiece.getLeft();
        } else if (targetPiece.TYPE == SCARAB && impact == targetPiece.getLeft()) {
            fireDirection = targetPiece.getBehind();
        } else if ((targetPiece.TYPE == ANUBIS && impact == targetPiece.getRotation()) || targetPiece.TYPE == SPHINX) {
            System.out.println(targetPiece.getPieceName() + " was hit by a laser and negated it.");
            return;
        } else if (targetPiece.TYPE == PHARAOH) {
            endGame(targetPiece.isRed());
            return;
        } else {
            System.out.println(targetPiece.getPieceName() + " was hit and removed from play. Hit from " + impact + " while facaing " + targetPiece.getRotationString() + ".");
            board[targetR][targetC] = null;
            return;
        }
        fireLaser(targetR, targetC, fireDirection);
    }

    private void drawLaser(final int x1, final int y1, final int x2, final int y2) {
        Graphics g = getContentPane().getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(5));

        g2d.drawLine(x1, y1, x2, y2);

        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isPieceSelected() {
        return selectedPiece.x >= 0 && selectedPiece.y >= 0;
    }

    private Piece getSelectedPiece() {
        return isPieceSelected() ? board[selectedPiece.x][selectedPiece.y] : null;
    }

    private void movePiece(int r, int c) {
        if (r >= 0 && r <= 7 && c >= 0 && c <= 9 //Check in bounds
                && (board[r][c] == null || scarabCheck(r, c)) //Check if spot is empty or if the Scarab can switch
                && getSelectedPiece().TYPE != SPHINX //Check if not SPHINX (unmoveable)
                && ankhCheck(r, c)) //Check for Ankh or Eye of Horus squares
        {
            if (scarabCheck(r, c)) {
                Piece temp = board[r][c];
                board[r][c] = getSelectedPiece();
                board[selectedPiece.x][selectedPiece.y] = temp;
            } else {
                board[r][c] = getSelectedPiece();
                board[selectedPiece.x][selectedPiece.y] = null;
            }
            switchTurn();
        }
    }

    private boolean scarabCheck(int r, int c) {
        return board[r][c] == null || (getSelectedPiece().TYPE == SCARAB && (board[r][c].TYPE == PYRAMID || board[r][c].TYPE == ANUBIS));
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

    void returnToMainMenu() {
        new MainMenu().setVisible(true);
        dispose();
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
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
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
                    //System.out.println(String.format("Selected:\t board[%d][%d]", r - 1, c - 1));
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
            returnToMainMenu();
        }
        //Rule #4
        if (isPieceSelected()) {
            int r = selectedPiece.x;
            int c = selectedPiece.y;
            switch (evt.getKeyCode()) {
                case VK_LEFT:
                    //Rule #3
                    if ((board[r][c].TYPE == SPHINX && board[r][c].isRed() && board[r][c].getRotation() == EAST)
                            || (board[r][c].TYPE == SPHINX && !board[r][c].isRed() && board[r][c].getRotation() == WEST)) {
                        break;
                    }
                    board[r][c].turnLeft();
                    switchTurn();
                    break;
                case VK_RIGHT:
                    //Rule #3
                    if ((board[r][c].TYPE == SPHINX && board[r][c].isRed() && board[r][c].getRotation() == SOUTH)
                            || (board[r][c].TYPE == SPHINX && !board[r][c].isRed() && board[r][c].getRotation() == NORTH)) {
                        break;
                    }
                    board[r][c].turnRight();
                    switchTurn();
                    break;
                //`board[r][c].TYPE != SPHINX` - Rule #3
                case VK_W: //Up
                    movePiece(r - 1, c);
                    break;
                case VK_A: //Left
                    movePiece(r, c - 1);
                    break;
                case VK_X: //Down
                    movePiece(r + 1, c);
                    break;
                case VK_D: //Right
                    movePiece(r, c + 1);
                    break;
                //Move Diagonally
                case VK_Q: //Up, Left
                    movePiece(r - 1, c - 1);
                    break;
                case VK_E: //Up, Right
                    movePiece(r - 1, c + 1);
                    break;
                case VK_Z: //Down, Left
                    movePiece(r + 1, c - 1);
                    break;
                case VK_C: //Down, Right
                    movePiece(r + 1, c + 1);
                    break;
            }
        }
    }//GEN-LAST:event_formKeyReleased

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        redraw();
    }//GEN-LAST:event_formWindowGainedFocus

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
