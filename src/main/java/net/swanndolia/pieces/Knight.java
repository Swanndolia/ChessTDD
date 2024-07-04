package net.swanndolia.pieces;

import lombok.*;
import net.swanndolia.gameboard.Square;
import net.swanndolia.moves.MoveDirection;
import net.swanndolia.moves.PieceAction;
import net.swanndolia.utils.Color;

@Data
public class Knight extends Piece implements PieceAction {
    public Knight(Color color, Square square) {
        this.color = color;
        this.shortName = "N";
        this.fullName = "Knight";
        if (color == Color.WHITE) {
            this.icon = "♘";
        } else {
            this.icon = "♞";
        }
        this.allowedMoveDirection.add(MoveDirection.KNIGHT);
        this.square = square;
        this.maximumMoveDistance = 2;
    }

    @Override
    public boolean capture(Square square) {
        return true;
    }

    @Override
    public boolean move(Square square) {
        return moveIsValid(square);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}