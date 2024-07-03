package net.swanndolia.pieces;

import lombok.*;
import net.swanndolia.gameboard.Square;
import net.swanndolia.moves.MoveDirection;
import net.swanndolia.moves.PieceAction;
import net.swanndolia.utils.Color;

@Data
public class Pawn extends Piece implements PieceAction {
    public Pawn(Color color, Square square){
        this.color = color;
        this.shortName = "P";
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