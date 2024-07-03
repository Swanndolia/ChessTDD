package net.swanndolia.pieces;

import lombok.*;
import net.swanndolia.gameboard.Square;
import net.swanndolia.moves.MoveDirection;
import net.swanndolia.moves.PieceAction;
import net.swanndolia.utils.Color;

@Data
public class Rook extends Piece implements PieceAction {
    public Rook(Color color, Square square) {
        this.color = color;
        this.shortName = "R";
        this.fullName = "Rook";
        if (color == Color.WHITE) {
            this.icon = "♖";
        } else {
            this.icon = "♜";
        }
        this.allowedMoveDirection.add(MoveDirection.FORWARD);
        this.allowedMoveDirection.add(MoveDirection.BACKWARD);
        this.allowedMoveDirection.add(MoveDirection.LEFT);
        this.allowedMoveDirection.add(MoveDirection.RiGHT);
        this.square = square;
        this.maximumMoveDistance = 7;
    }

    @Override
    public void capture(Square square) {

    }

    @Override
    public boolean move(Square square) {
        if (moveIsValid(square)) {
            this.square.setHorizontalCoordinates(square.getHorizontalCoordinates());
            this.square.setHorizontalCoordinates(square.getHorizontalCoordinates());
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
