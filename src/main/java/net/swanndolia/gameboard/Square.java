package net.swanndolia.gameboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.swanndolia.pieces.Piece;
import net.swanndolia.utils.Color;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Square {
    Color color;
    Piece piece;
    int horizontalCoordinates;
    int verticalCoordinates;

    @Override
    public String toString() {
        final String ANSI_BLACK            = "\u001B[30m";
        final String ANSI_WHITE            = "\u001B[37m";
        final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
        final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
        if (this.color == Color.BLACK) {
            if (this.piece != null) {
                return ANSI_WHITE + ANSI_BLACK_BACKGROUND + " " + this.piece.toString(ANSI_BLACK_BACKGROUND) + ANSI_WHITE + ANSI_BLACK_BACKGROUND +
                        " " + ANSI_WHITE + ANSI_BLACK_BACKGROUND;
            }
            return ANSI_WHITE + ANSI_BLACK_BACKGROUND + "   " + ANSI_WHITE + ANSI_BLACK_BACKGROUND;
        } else {
            if (this.piece != null) {
                return ANSI_BLACK + ANSI_WHITE_BACKGROUND + " " + this.piece.toString(ANSI_WHITE_BACKGROUND) + ANSI_BLACK + ANSI_WHITE_BACKGROUND +
                        " " + ANSI_WHITE + ANSI_BLACK_BACKGROUND;
            }
            return ANSI_BLACK + ANSI_WHITE_BACKGROUND + "   " + ANSI_WHITE + ANSI_BLACK_BACKGROUND;
        }
    }
}
