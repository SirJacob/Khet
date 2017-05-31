package pkg;

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
import static java.awt.event.KeyEvent.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author https://github.com/SirJacob/Khet/graphs/contributors
 */
class Board extends javax.swing.JFrame {

    private boolean isRedTurn = false; //Keeps track of whose turn it is.
    private final Point selectedPiece = new Point(-1, -1); //Holds the location in the array of the selected piece.
    private final Image BACKGROUND = new ImageIcon(this.getClass().getResource("/board.png")).getImage(); //Loads the board background image into memory.
    private Piece[][] board; //2D array that holds information about all the pieces on the board.
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
    //Displays all the red pieces facing all four directions. Not to be used during production gameplay.
    private static final Piece[][] DEBUG = {
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

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Board() {
        initComponents();
        setSize(BACKGROUND.getWidth(null), BACKGROUND.getHeight(null)); //Sets the window size equal to the size of the background image.
        setLocationRelativeTo(null); //Moves the frame to the center of the screen.
    }

    /**
     * Controls all the rendering to the JFrame.
     *
     * @param g Accepts a Graphics object to paint to.
     */
    private void paintComponent(final Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(BACKGROUND, 0, 0, null); //Draws background.
        //for loop to render every piece in the board.
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 10; c++) {
                if (board[r][c] != null) {
                    if (board[r][c].getRotation() != EAST) { //Image rotation required. Transform image and then draw.
                        AffineTransformOp op = new AffineTransformOp(rotate(board[r][c].image, (c * 125) - c, r * 125, board[r][c].getRotation()), AffineTransformOp.TYPE_BILINEAR);
                        g2d.drawImage(board[r][c].image, op.getTransform(), null);
                    } else { //else: No rotation required. Draw image without modification.
                        g.drawImage(board[r][c].image, (c * 125) - c, r * 125, 125, 125, null);
                    }
                }
            }
        }
        paintSelectedBorder();
    }

    /**
     * Draws a yellow border around the selected piece.
     */
    private void paintSelectedBorder() {
        Graphics g = getContentPane().getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        if (isPieceSelected()) {
            g.setColor(Color.YELLOW);
            g2d.setStroke(new BasicStroke(5)); //Sets border thickness.
            g2d.drawRect((selectedPiece.y * 125) - selectedPiece.y + 2, (selectedPiece.x * 125) + 2, 120, 120);
        }
    }

    /**
     * Switches to the other player's turn by: un-selecting the selected piece,
     * firing the laser at the end of the turn (as required by the rules), and
     * updating the isRedTurn boolean.
     */
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

    /**
     * Called at the end of the game. Displays the winner via JOptionPane and
     * then closes the game.
     *
     * @param isRedLoser Accepts the loser as a boolean.
     */
    private void endGame(final boolean isRedLoser) {
        JOptionPane.showMessageDialog(this, isRedLoser ? "Silver" : "Red" + " has won!", "Khet - Game Over", JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    }

    /**
     *
     * @param sourceR R value of source piece that the laser beam is coming from
     * (either reflected or originated from).
     * @param sourceC C value of source piece that the laser beam is coming from
     * (either reflected or originated from).
     * @param fireDirection Direction that the laser is going (N, S, E, or W).
     */
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

    /**
     * Searches through the 2D board array for a collision between the laser and
     * a piece; if a collision is not found then the laser went out of bounds.
     *
     * @param sourceR R value of source piece that the laser beam is coming from
     * (either reflected or originated from).
     * @param direction Direction that the laser is going. -1 = North and 1 =
     * South.
     * @param sourceC C value of source piece that the laser beam is coming from
     * (either reflected or originated from).
     */
    private void fireLaserSearchY(final int sourceR, final int direction, final int sourceC) {
        for (int r = sourceR + direction; r >= 0 && r <= 7; r += direction) {
            if (board[r][sourceC] != null) {
                fireLaserCollide(sourceR, sourceC, r, sourceC, direction == 1 ? NORTH : SOUTH);
                return;
            }
        }
        System.out.println("The laser went out of bounds.");
        if (direction != 1) { //Draw the laser going off the screen.
            drawLaser(sourceC * 125 + 60, sourceR * 125 + 60, sourceC * 125 + 60, 0);
        } else {
            drawLaser(sourceC * 125 + 60, sourceR * 125 + 60, sourceC * 125 + 60, getHeight());
        }
    }

    /**
     * Searches through the 2D board array for a collision between the laser and
     * a piece; if a collision is not found then the laser went out of bounds.
     *
     * @param sourceC C value of source piece that the laser beam is coming from
     * (either reflected or originated from).
     * @param direction Direction that the laser is going. -1 = West and 1 =
     * East.
     * @param sourceR R value of source piece that the laser beam is coming from
     * (either reflected or originated from).
     */
    private void fireLaserSearchX(final int sourceC, final int direction, final int sourceR) {
        for (int c = sourceC + direction; c >= 0 && c <= 9; c += direction) {
            if (board[sourceR][c] != null) {
                fireLaserCollide(sourceR, sourceC, sourceR, c, direction == 1 ? WEST : EAST);
                return;
            }
        }
        System.out.println("The laser went out of bounds.");
        if (direction != 1) { //Draw the laser going off the screen.
            drawLaser(sourceC * 125 + 60, sourceR * 125 + 60, 0, sourceR * 125 + 60);
        } else {
            drawLaser(sourceC * 125 + 60, sourceR * 125 + 60, getWidth(), sourceR * 125 + 60);
        }
    }

    /**
     * Contains all the logic for dealing with a laser striking a piece.
     *
     * @param sourceR R value of source piece that the laser beam is coming from
     * (either reflected or originated from).
     * @param sourceC C value of source piece that the laser beam is coming from
     * (either reflected or originated from).
     * @param targetR R value of the piece that has been struck by the laser
     * beam.
     * @param targetC C value of the piece that has been struck by the laser
     * beam.
     * @param impact Direction that the targetPiece has been struck from by the
     * laser beam.
     */
    private void fireLaserCollide(final int sourceR, final int sourceC, final int targetR, final int targetC, final int impact) {
        Piece sourcePiece = board[sourceR][sourceC];
        Piece targetPiece = board[targetR][targetC];

        String debug = String.format("%s %s [%s,%s] looking %s hit %s %s [%s,%s] looking %s. Impact from the %s side.",
                sourcePiece.getColor(), sourcePiece.getPieceName(), sourceR, sourceC, sourcePiece.getRotationString(),
                targetPiece.getColor(), targetPiece.getPieceName(), targetR, targetC, targetPiece.getRotationString(), Piece.rotationIntToStr(impact));
        System.out.println(debug);

        drawLaser(sourceC * 125 + 60, sourceR * 125 + 60, targetC * 125 + 60, targetR * 125 + 60); //Draws the laser hitting the targetPiece.

        int fireDirection;
        if (((targetPiece.TYPE == PYRAMID || targetPiece.TYPE == SCARAB) && impact == targetPiece.getRotation())) { //A Pyramid or Scarab piece was struck by the laser and reflected it to its right.
            fireDirection = targetPiece.getRight();
        } else if (((targetPiece.TYPE == PYRAMID || targetPiece.TYPE == SCARAB) && impact == targetPiece.getRight())) { //A Pyramid or Scarab piece was struck by the laser and reflected it in the direction it is facing.
            fireDirection = targetPiece.getRotation();
        } else if (targetPiece.TYPE == SCARAB && impact == targetPiece.getBehind()) { //The Scarab piece was struck by the laser and reflected it to its left.
            fireDirection = targetPiece.getLeft();
        } else if (targetPiece.TYPE == SCARAB && impact == targetPiece.getLeft()) { //The Scarab piece was struck by the laser and reflected it behind itself.
            fireDirection = targetPiece.getBehind();
        } else if ((targetPiece.TYPE == ANUBIS && impact == targetPiece.getRotation()) || targetPiece.TYPE == SPHINX) { //A piece was struck by the laser, but it had no effect on the piece.
            System.out.println(String.format("%s %s [%d,%d] was hit by a laser and negated it.", targetPiece.getColor(), targetPiece.getPieceName(), targetR, targetC));
            return;
        } else if (targetPiece.TYPE == PHARAOH) { //Pharaoh was struck by the laser.
            endGame(targetPiece.isRed());
            return;
        } else { //A piece was struck by the laser and removed from play.
            System.out.println(String.format("%s %s was hit and removed from play. Hit from %s while facing %s.", targetPiece.getColor(), targetPiece.getPieceName(), Piece.rotationIntToStr(impact), targetPiece.getRotationString()));
            board[targetR][targetC] = null;
            return;
        }
        fireLaser(targetR, targetC, fireDirection); //The laser beam was reflected.
    }

    /**
     * Draws the laser shooting from one piece (x1, y1) to another piece (x2,
     * y2).
     *
     * @param x1 X value of source piece.
     * @param y1 Y value of source piece.
     * @param x2 X value of target piece.
     * @param y2 Y value of target piece.
     */
    private void drawLaser(final int x1, final int y1, final int x2, final int y2) {
        Graphics g = getContentPane().getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(5)); //Sets the thickness of the laser.

        g2d.drawLine(x1, y1, x2, y2);

        try {
            Thread.sleep(500); //Pauses after drawing the laser to allow time to see laser movement changes.
        } catch (InterruptedException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return Returns true if a piece is currently selected, false otherwise.
     */
    private boolean isPieceSelected() {
        return selectedPiece.x >= 0 && selectedPiece.y >= 0;
    }

    /**
     * @return Returns the currently selected piece or null if no piece is
     * selected.
     */
    private Piece getSelectedPiece() {
        return isPieceSelected() ? board[selectedPiece.x][selectedPiece.y] : null;
    }

    /**
     * Performs all the logic to determine if the selectedPiece can move to the
     * given [r][c].
     *
     * @param r The X value of the destination.
     * @param c The Y value of the destination.
     */
    private void movePiece(final int r, final int c) {
        if (r >= 0 && r <= 7 && c >= 0 && c <= 9 //Check if the given [r][c] is in bounds.
                && (board[r][c] == null || scarabCheck(r, c)) //Check if the [r][c] is empty or if the Scarab can switch to that location.
                && getSelectedPiece().TYPE != SPHINX //Check if not a SPHINX (which is an unmoveable piece).
                && ankhCheck(r, c)) { //Check for Ankh or Eye of Horus squares.
            if (scarabCheck(r, c)) { //Scarab special swap ability to switch places with another piece.
                Piece temp = board[r][c];
                board[r][c] = getSelectedPiece();
                board[selectedPiece.x][selectedPiece.y] = temp;
            } else { //Normally move the piece to its new location.
                board[r][c] = getSelectedPiece();
                board[selectedPiece.x][selectedPiece.y] = null;
            }
            switchTurn();
        }
    }

    /**
     * @param r The X value of the destination.
     * @param c The Y value of the destination.
     * @return If the selected piece can move to the given [r][c] as a scarab.
     */
    private boolean scarabCheck(final int r, final int c) {
        return board[r][c] == null || (getSelectedPiece().TYPE == SCARAB && (board[r][c].TYPE == PYRAMID || board[r][c].TYPE == ANUBIS));
    }

    /**
     *
     * @param r The X value of the destination.
     * @param c The Y value of the destination.
     * @return If movement by selected piece is allowed based on Ankh or Eye of
     * Horus squares.
     */
    private boolean ankhCheck(final int r, final int c) { //Rule #5
        boolean isSelectedPieceRed = board[selectedPiece.x][selectedPiece.y].isRed();
        return !((isSelectedPieceRed && (c == 9 || (r == 0 && c == 1) || (r == 7 && c == 1))) || (!isSelectedPieceRed && (c == 0 || (r == 0 && c == 8) || (r == 7 && c == 8))));
    }

    /**
     * Shortcut that calls paintComponent to render the screen.
     */
    private void redraw() {
        paintComponent(getContentPane().getGraphics());
    }

    /**
     * @param source The Image of the piece to be rotated.
     * @param x The X value of the piece to be rotated.
     * @param y The Y value of the piece to be rotated.
     * @param newDirection Accepts the new direction the piece should be facing.
     * @return Returns the AffineTransform needed when rendering the rotated
     * image.
     */
    private AffineTransform rotate(final Image source, final int x, final int y, final int newDirection) {
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

    /**
     * Sets the playing board equal to one of the default board setups.
     *
     * @param board Accepts a default board setup to use.
     */
    void setBoard(final Piece[][] board) {
        this.board = board;
    }

    /**
     * Disposes of the current JFrame and opens a new MainMenu JFrame.
     */
    void returnToMainMenu() {
        new MainMenu().setVisible(true);
        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
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
    /**
     * Handles mouse input. Finds the piece clicked and assigns its r an c
     * values to selectedPiece.
     *
     * @param evt Accepts MouseEvent which is used to get the location that the
     * mouse clicked on the screen.
     */
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        final int x = evt.getX();
        final int y = evt.getY();
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
    /**
     * Handles key input.
     *
     * @param evt Accepts KeyEvent to check what key is being pressed.
     */
    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if (evt.getKeyCode() == VK_ESCAPE) {
            returnToMainMenu();
        }

        if (isPieceSelected()) {
            final int r = selectedPiece.x;
            final int c = selectedPiece.y;
            switch (evt.getKeyCode()) {
                //Cases for rotating the piece left or right.
                case VK_LEFT:
                    if ((board[r][c].TYPE == SPHINX && board[r][c].isRed() && board[r][c].getRotation() == EAST)
                            || (board[r][c].TYPE == SPHINX && !board[r][c].isRed() && board[r][c].getRotation() == WEST)) {
                        break;
                    }
                    board[r][c].turnLeft();
                    switchTurn();
                    break;
                case VK_RIGHT:
                    if ((board[r][c].TYPE == SPHINX && board[r][c].isRed() && board[r][c].getRotation() == SOUTH)
                            || (board[r][c].TYPE == SPHINX && !board[r][c].isRed() && board[r][c].getRotation() == NORTH)) {
                        break;
                    }
                    board[r][c].turnRight();
                    switchTurn();
                    break;
                //Cases for movement of piece up, down, left, and right. 
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
                //Cases for moving the piece diagonally.
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
    /**
     * Renders the JFrame when the window gains focus. Implemented as a bug fix
     * to the frame not rendering at startup.
     *
     * @param evt
     */
    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        redraw();
    }//GEN-LAST:event_formWindowGainedFocus

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
