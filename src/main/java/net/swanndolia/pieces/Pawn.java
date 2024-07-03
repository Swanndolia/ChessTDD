package net.swanndolia.pieces;

import lombok.*;
import net.swanndolia.IHM;
import net.swanndolia.gameboard.Square;
import net.swanndolia.moves.PieceAction;
import net.swanndolia.utils.Color;

@Data
public class Pawn extends Piece implements PieceAction {
    public Pawn(Color color, Square square) {
        this.color = color;
        this.shortName = "P";
    }

    @Override
    public void capture(Square square) {
    }

    @Override
    public boolean move(Square square) {
        if(moveIsValid(square)){
            this.square.setHorizontalCoordinates(square.getHorizontalCoordinates());
            this.square.setHorizontalCoordinates(square.getHorizontalCoordinates());
            return true;
        }
        return false;
    }


    @Override
    public boolean moveIsValid(Square square) {
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}