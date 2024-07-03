package net.swanndolia.pieces;

import lombok.Data;
import net.swanndolia.gameboard.Square;
import net.swanndolia.moves.PieceAction;
import net.swanndolia.utils.Color;

@Data
public abstract class Piece implements PieceAction {
    Color color;
    String fullName;
    String shortName;
    Square square;

    public String toString(String backgroundColor) {
        String BLACK_BRIGHT = ("\033[0;90m");
        String WHITE_BRIGHT = ("\033[0;97m");
        if (this.color == Color.BLACK) {
            return BLACK_BRIGHT + backgroundColor + this.shortName;

        } else {
            return WHITE_BRIGHT + backgroundColor + this.shortName;
        }
    }
}