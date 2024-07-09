package net.swanndolia.pieces;

import lombok.Data;
import net.swanndolia.IHM;
import net.swanndolia.gameboard.ChessBoard;
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
    public void capture(Square square) {
        super.capture(square);
    }

    @Override
    public boolean move(Square square) {
        return super.moveOrCapture(square);
    }

    @Override
    public void checkAttackedSquares() {
        ChessBoard chessBoard = this.getSquare().getGameboard();
        int        vertical   = this.getSquare().getVerticalCoordinates();
        int        horizontal = this.getSquare().getHorizontalCoordinates();
        try {
            Square currentSquare = chessBoard.getSquare(vertical + 2, horizontal + 1);
            verifySquareIsAttacked(currentSquare);
        } catch (Exception e) {
            //IHM.sendMessageToUser(e.getMessage()); //(out of bonds)
        }
        try {
            Square currentSquare = chessBoard.getSquare(vertical + 2, horizontal - 1);
            verifySquareIsAttacked(currentSquare);
        } catch (Exception e) {
            //IHM.sendMessageToUser(e.getMessage()); //(out of bonds)
        }
        try {
            Square currentSquare = chessBoard.getSquare(vertical + 1, horizontal + 2);
            verifySquareIsAttacked(currentSquare);
        } catch (Exception e) {
            //IHM.sendMessageToUser(e.getMessage()); //(out of bonds)
        }
        try {
            Square currentSquare = chessBoard.getSquare(vertical + 1, horizontal - 2);
            verifySquareIsAttacked(currentSquare);
        } catch (Exception e) {
            //IHM.sendMessageToUser(e.getMessage()); //(out of bonds)
        }
        try {
            Square currentSquare = chessBoard.getSquare(vertical - 1, horizontal + 2);
            verifySquareIsAttacked(currentSquare);
        } catch (Exception e) {
            //IHM.sendMessageToUser(e.getMessage()); //(out of bonds)
        }
        try {
            Square currentSquare = chessBoard.getSquare(vertical - 1, horizontal - 2);
            verifySquareIsAttacked(currentSquare);
        } catch (Exception e) {
            //IHM.sendMessageToUser(e.getMessage()); //(out of bonds)
        }
        try {
            Square currentSquare = chessBoard.getSquare(vertical - 2, horizontal + 1);
            verifySquareIsAttacked(currentSquare);
        } catch (Exception e) {
            //IHM.sendMessageToUser(e.getMessage()); //(out of bonds)
        }
        try {
            Square currentSquare = chessBoard.getSquare(vertical - 2, horizontal - 1);
            verifySquareIsAttacked(currentSquare);
        } catch (Exception e) {
            //IHM.sendMessageToUser(e.getMessage()); //(out of bonds)
        }
    }

    public void verifySquareIsAttacked(Square square) {
        if (square.getPiece() == null || square.getPiece().getColor() != this.color) {
            square.setIsAttacked(this.color, true);
            this.allowedSquares.add(square);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}