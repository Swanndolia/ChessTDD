package net.swanndolia.pieces;

import lombok.*;
import net.swanndolia.IHM;
import net.swanndolia.gameboard.Square;
import net.swanndolia.moves.MoveDirection;
import net.swanndolia.moves.PieceAction;
import net.swanndolia.utils.Color;

@Data
public class Pawn extends Piece implements PieceAction {
    boolean asMoved = false;
    public Pawn(Color color, Square square) {
        this.color = color;
        this.shortName = "P";
        this.fullName = "Pawn";
        if (color == Color.WHITE) {
            this.icon = "♙";
        } else {
            this.icon = "♟";
        }
        this.allowedMoveDirection.add(MoveDirection.FORWARD);
        this.square = square;
        this.maximumMoveDistance = 1;
    }

    @Override
    public void capture(Square square) {
        this.asMoved = true;
    }

    @Override
    public boolean move(Square square) {
        if (moveIsValid(square)) {
            this.asMoved = true;
            this.square.setHorizontalCoordinates(square.getHorizontalCoordinates());
            this.square.setHorizontalCoordinates(square.getHorizontalCoordinates());
            return true;
        }
        return false;
    }

    @Override
    public int getMaximumMoveDistance() {
        return this.asMoved ? this.maximumMoveDistance : this.maximumMoveDistance + 1;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}