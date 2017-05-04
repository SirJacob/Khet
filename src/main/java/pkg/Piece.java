package pkg;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author https://github.com/SirJacob/Khet/graphs/contributors
 */
public class Piece {

    private int rotation;
    static final int NORTH = 0;
    static final int EAST = 1;
    static final int SOUTH = 2;
    static final int WEST = 3;

    final boolean COLOR;
    static final boolean SILVER = true;
    static final boolean RED = false;

    final int TYPE;
    static final int PYRAMID = 0;
    static final int PHARAOH = 1;
    static final int ANUBIS = 2;
    static final int SPHINX = 3;
    static final int SCARAB = 4;

     final Image image;

    public Piece(int type, boolean color, int rotation) {
        this.TYPE = type;
        this.COLOR = color;
        this.rotation = rotation;
        this.image = new ImageIcon(this.getClass().getResource(String.format("/pieces/%s_%s.png", getColor().toLowerCase(), getPieceName().toLowerCase()))).getImage();
    }

    void turnLeft() {
        if (rotation == NORTH) {
            rotation = WEST;
        } else {
            rotation--;
        }
    }

    void turnRight() {
        if (rotation == WEST) {
            rotation = NORTH;
        } else {
            rotation++;
        }
    }

    int getRotation() {
        return rotation;
    }

    String getColor() {
        if (COLOR) {
            return "Silver";
        } else {
            return "Red";
        }
    }

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

}
