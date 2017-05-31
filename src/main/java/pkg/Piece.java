package pkg;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author https://github.com/SirJacob/Khet/graphs/contributors
 */
class Piece {

    private int rotation; //Stores the direction the piece is facing.
    static final int NORTH = 0;
    static final int EAST = 1;
    static final int SOUTH = 2;
    static final int WEST = 3;

    final boolean COLOR; //Stores the color of the piece.
    static final boolean SILVER = true;
    static final boolean RED = false;

    final int TYPE; //Stores the type of piece.
    static final int PYRAMID = 0;
    static final int PHARAOH = 1;
    static final int ANUBIS = 2;
    static final int SPHINX = 3;
    static final int SCARAB = 4;

    final Image image; //Holds the image of the piece in memory.

    @SuppressWarnings("OverridableMethodCallInConstructor")
    Piece(int type, boolean color, int rotation) {
        this.TYPE = type;
        this.COLOR = color;
        this.rotation = rotation;
        this.image = new ImageIcon(this.getClass().getResource(String.format("/%s_%s.png", getColor().toLowerCase(), getPieceName().toLowerCase()))).getImage();
    }

    /**
     * Sets the piece's rotation one turn to the left of its current rotation.
     */
    void turnLeft() {
        rotation = getLeft();
    }

    /**
     * Sets the piece's rotation one turn to the right of its current rotation.
     */
    void turnRight() {
        rotation = getRight();
    }

    /**
     *
     * @return The direction to the left of the position the piece is facing
     * represented as an int.
     */
    int getLeft() {
        if (rotation == NORTH) {
            return WEST;
        }
        return rotation - 1;
    }

    /**
     *
     * @return The direction to the right of the position the piece is facing
     * represented as an int.
     */
    int getRight() {
        if (rotation == WEST) {
            return NORTH;
        }
        return rotation + 1;
    }

    /**
     *
     * @return The opposite direction that the piece is facing represented as an
     * int.
     */
    int getBehind() {
        switch (rotation) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            default:
                return WEST;
        }
    }

    /**
     *
     * @return The direction that the piece is facing as an int.
     */
    int getRotation() {
        return rotation;
    }

    /**
     *
     * @return The direction that the piece is facing as a String.
     */
    String getRotationString() {
        return rotationIntToStr(rotation);
    }

    /**
     *
     * @return true if the color of the piece is red, and false otherwise.
     */
    boolean isRed() {
        return getColor().equals("Red");
    }

    /**
     *
     * @return The color of the piece as a String.
     */
    String getColor() {
        if (COLOR) {
            return "Silver";
        } else {
            return "Red";
        }
    }

    /**
     *
     * @return The name of the piece as a String.
     */
    String getPieceName() {
        switch (TYPE) {
            case 0:
                return "Pyramid";
            case 1:
                return "Pharaoh";
            case 2:
                return "Anubis";
            case 3:
                return "Sphinx";
            case 4:
                return "Scarab";
        }
        return null;
    }

    /**
     *
     * @param rotation
     * @return The given rotation converted from an int to its String
     * counterpart.
     */
    static String rotationIntToStr(int rotation) {
        switch (rotation) {
            case NORTH:
                return "North";
            case EAST:
                return "East";
            case SOUTH:
                return "South";
            default:
                return "West";
        }
    }
}
