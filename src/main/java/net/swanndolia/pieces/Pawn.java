package net.swanndolia.pieces;

import lombok.Data;
import net.swanndolia.IHM;
import net.swanndolia.gameboard.ChessBoard;
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
        super.capture(square);
    }

    @Override
    public boolean move(Square square) {
        if (super.move(square)) {
            if (square.getPiece() == null) {
                this.asMoved = true;
                return true;
            } else {
                IHM.sendMessageToUser("A pawn can't capture FORWARD but only DIAGONAL_UP");
                return false;
            }
        } else if (this.currentMove.getMoveDistance() == 1 && (
                this.currentMove.getMoveDirection() == MoveDirection.DIAGONAL_UP_RIGHT ||
                        this.currentMove.getMoveDirection() == MoveDirection.DIAGONAL_UP_LEFT
        )) {
            try {
                if (square.getPiece() != null && square.getPiece().getColor() != this.color) {
                    capture(square);
                    this.asMoved = true;
                    IHM.sendMessageToUser(this.currentMove.getMoveResult());
                    return true;
                }
            } catch (Exception e) {
                IHM.sendMessageToUser("You can't capture your own " + square.getPiece().getFullName());
                return false;
            }
            IHM.sendMessageToUser("This move is valid only if it's a capture");
        }
        IHM.sendMessageToUser(this.currentMove.getMoveResult());
        return false;
    }

    @Override
    public void checkAttackedSquares() {
        ChessBoard chessBoard = this.getSquare().getGameboard();
        try {
            Square nextSquareUpRight = chessBoard.getNextSquare(this.square, MoveDirection.DIAGONAL_UP_RIGHT, this.color);
            if (nextSquareUpRight.getPiece() == null || nextSquareUpRight.getPiece().getColor() != this.color) {
                nextSquareUpRight.setIsAttacked(this.color, true);
            }
        } catch (Exception e) {
            //IHM.sendMessageToUser(e.getMessage()); //(out of bonds)
        }
        try {
            Square nextSquareUpLeft = chessBoard.getNextSquare(this.square, MoveDirection.DIAGONAL_UP_LEFT, this.color);
            if (nextSquareUpLeft.getPiece() == null || nextSquareUpLeft.getPiece().getColor() != this.color) {
                nextSquareUpLeft.setIsAttacked(this.color, true);
            }
        } catch (Exception e) {
            //IHM.sendMessageToUser(e.getMessage()); //(out of bonds)
        }
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