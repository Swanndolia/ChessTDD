package net.swanndolia.pieces;

import lombok.*;
import net.swanndolia.gameboard.Square;
import net.swanndolia.moves.MoveDirection;
import net.swanndolia.moves.PieceAction;
import net.swanndolia.utils.Color;

@Data
public class Bishop extends Piece implements PieceAction {
    public Bishop(Color color, Square square) {
        this.color = color;
        this.shortName = "B";
        this.fullName = "Bishop";
        if (color == Color.WHITE) {
            this.icon = "♗";
        } else {
            this.icon = "♝";
        }
        this.allowedMoveDirection.add(MoveDirection.DIAGONAL_DOWN_LEFT);
        this.allowedMoveDirection.add(MoveDirection.DIAGONAL_DOWN_RIGHT);
        this.allowedMoveDirection.add(MoveDirection.DIAGONAL_UP_LEFT);
        this.allowedMoveDirection.add(MoveDirection.DIAGONAL_UP_RIGHT);
        this.square = square;
        this.maximumMoveDistance = 7;
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