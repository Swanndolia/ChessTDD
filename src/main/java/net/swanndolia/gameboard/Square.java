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
        return this.piece != null ? this.color.name().substring(0, 1).toLowerCase().concat(this.piece.toString()) :
                " " + this.color.name().substring(0, 1).toLowerCase() + " ";
    }
}
