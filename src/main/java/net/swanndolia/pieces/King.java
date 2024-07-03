package net.swanndolia.pieces;

import lombok.*;
import net.swanndolia.gameboard.Square;
import net.swanndolia.moves.PieceAction;
import net.swanndolia.utils.Color;

@Data
public class King extends Piece implements PieceAction {
    public King(Color color, Square square){
        this.color = color;
        this.shortName = "K";
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