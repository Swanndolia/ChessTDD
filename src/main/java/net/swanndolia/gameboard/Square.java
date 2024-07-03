package net.swanndolia.gameboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.swanndolia.pieces.Piece;
import net.swanndolia.utils.Color;

import static net.swanndolia.utils.ColorList.*;
import static net.swanndolia.utils.ConsoleSpacing.EMPTY_CELL_SPACING;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Square {
    ChessBoard gameboard;
    Color color;
    Piece piece;
    int horizontalCoordinates;
    int verticalCoordinates;

    public void emptySquare() {
        this.piece = null;
    }

    @Override
    public String toString() {
        if (this.color == Color.BLACK) {
            if (this.piece != null) {
                return WHITE_TEXT + BLACK_BACK + " " + this.piece.getColoredIcon(BLACK_BACK) + WHITE_TEXT + BLACK_BACK + " " + RESET;
            }
            return BLACK_BACK  + EMPTY_CELL_SPACING + RESET;
        } else {
            if (this.piece != null) {
                return BLACK_TEXT + WHITE_BACK + " " + this.piece.getColoredIcon(WHITE_BACK) + BLACK_TEXT + WHITE_BACK + " " + RESET;
            }
            return WHITE_BACK + EMPTY_CELL_SPACING + RESET;
        }
    }
}
