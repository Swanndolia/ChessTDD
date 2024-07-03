package net.swanndolia.pieces;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.swanndolia.gameboard.Square;
import net.swanndolia.moves.PieceAction;
import net.swanndolia.utils.Color;

@Data
public class Knight extends Piece implements PieceAction {
    public Knight (Color color, Square square){
        this.color = color;
        this.shortName = "N";

    }
    @Override
    public void capture(Square square) {

    }

    @Override
    public void move(Square square) {

    }

    @Override
    public String toString(){
        return super.toString();
    }
}