package net.swanndolia.pieces;

import lombok.Data;
import net.swanndolia.gameboard.ChessBoard;
import net.swanndolia.gameboard.Square;
import net.swanndolia.moves.MoveDirection;
import net.swanndolia.moves.PieceAction;
import net.swanndolia.utils.Color;

@Data
public class King extends Piece implements PieceAction {
    public King(Color color, Square square) {
        this.color = color;
        this.shortName = "K";
        this.fullName = "King";
        if (color == Color.WHITE) {
            this.icon = "♔";
        } else {
            this.icon = "♚";
        }
        this.allowedMoveDirection.add(MoveDirection.DIAGONAL_DOWN_LEFT);
        this.allowedMoveDirection.add(MoveDirection.DIAGONAL_DOWN_RIGHT);
        this.allowedMoveDirection.add(MoveDirection.DIAGONAL_UP_LEFT);
        this.allowedMoveDirection.add(MoveDirection.DIAGONAL_UP_RIGHT);
        this.allowedMoveDirection.add(MoveDirection.FORWARD);
        this.allowedMoveDirection.add(MoveDirection.BACKWARD);
        this.allowedMoveDirection.add(MoveDirection.LEFT);
        this.allowedMoveDirection.add(MoveDirection.RIGHT);
        this.square = square;
        this.maximumMoveDistance = 1;
    }

    @Override
    public void capture(Square square) {
        super.capture(square);
    }

    @Override
    public boolean move(Square square) {
        return super.moveOrCapture(square);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void checkAttackedSquares() {
        this.allowedSquares.clear();
        ChessBoard chessBoard = this.getSquare().getGameboard();
        for (MoveDirection moveDirection : this.allowedMoveDirection) {
            Square nextSquare = this.getSquare();
            try {
                nextSquare = chessBoard.getNextSquare(nextSquare, moveDirection, this.color);
                if (nextSquare.getPiece() == null) {
                    nextSquare.setIsAttacked(this.color, true);
                    this.allowedSquares.add(nextSquare);
                } else if (nextSquare.getPiece().getColor() != this.color) {
                    nextSquare.setIsAttacked(this.color, true);
                    this.allowedSquares.add(nextSquare);
                    break;
                } else {
                    break;
                }
            } catch (Exception e) {
                break;
            }
        }
    }
}