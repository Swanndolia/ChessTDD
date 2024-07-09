package net.swanndolia.pieces;

import lombok.Data;
import net.swanndolia.IHM;
import net.swanndolia.gameboard.ChessBoard;
import net.swanndolia.gameboard.Square;
import net.swanndolia.moves.ComputeMove;
import net.swanndolia.moves.MoveDirection;
import net.swanndolia.moves.PieceAction;
import net.swanndolia.utils.Color;

import java.util.ArrayList;
import java.util.List;

import static net.swanndolia.utils.ColorList.*;

@Data
public abstract class Piece implements PieceAction {
    Color color;
    String fullName;
    String shortName;
    String icon;
    Square square;
    List<MoveDirection> allowedMoveDirection = new ArrayList<MoveDirection>();
    int maximumMoveDistance;
    ComputeMove currentMove;
    List<Square> allowedSquares = new ArrayList<Square>();

    public String toString(String backgroundColor) {
        if (this.color == Color.BLACK) {
            return BLACK_TEXT + backgroundColor + this.shortName;
        } else {
            return WHITE_TEXT + backgroundColor + this.shortName;
        }
    }

    public String getColoredIcon(String backgroundColor) {
        if (this.color == Color.BLACK) {
            return BLACK_TEXT_HIGH + backgroundColor + this.icon;
        } else {
            return WHITE_TEXT_HIGH + backgroundColor + this.icon;
        }
    }

    @Override
    public void capture(Square square) {
        if (this.color == Color.WHITE) {
            square.getPiece().getSquare().getGameboard().getCapturedBlackPieces().add(square.getPiece());
            square.getPiece().getSquare().getGameboard().getBlackPieces().remove(square.getPiece());
        } else {
            square.getPiece().getSquare().getGameboard().getCapturedWhitePieces().add(square.getPiece());
            square.getPiece().getSquare().getGameboard().getWhitePieces().remove(square.getPiece());
        }
        this.currentMove.setMoveResult("You captured the enemy " + square.getPiece().getFullName());
    }

    @Override
    public boolean move(Square square) {
        return moveIsValid(square);
    }

    public boolean moveOrCapture(Square square) {
        if (moveIsValid(square)) {
            if (square.getPiece() != null) {
                capture(square);
                IHM.sendMessageToUser(this.getCurrentMove().getMoveResult());
            }
            return true;
        }
        IHM.sendMessageToUser(this.getCurrentMove().getMoveResult());
        return false;
    }

    public boolean moveIsValid(Square square) {
        this.currentMove = new ComputeMove(this, square);
        MoveDirection moveDirection = this.currentMove.getMoveDirection();

        if (this.allowedMoveDirection.contains(moveDirection)) {
            int moveDistance = this.currentMove.getMoveDistance();
            if (moveDistance <= this.getMaximumMoveDistance()) {
                return !this.currentMove.isMoveBlocked();
            }
            this.currentMove.setMoveResult(
                    "Your " + this.fullName + " can only move " + this.getMaximumMoveDistance() + " square " + this.allowedMoveDirection);
            return false;
        }
        this.currentMove.setMoveResult("A " + this.fullName + " can only move: " + this.allowedMoveDirection + " not " + moveDirection);
        return false;
    }

    public String getHighContrastColoredIcon() {
        if (this.color == Color.BLACK) {
            return BLACK_TEXT + WHITE_BACK_HIGH + this.icon;
        } else {
            return WHITE_TEXT_HIGH + BLACK_BACK + this.icon;
        }
    }

    public void checkAttackedSquares() {
        ChessBoard chessBoard = this.getSquare().getGameboard();
        for (MoveDirection moveDirection : this.allowedMoveDirection) {
            Square nextSquare = this.getSquare();
            while (nextSquare != null) {
                try {
                    nextSquare = chessBoard.getNextSquare(nextSquare, moveDirection, this.color);
                }catch (Exception e){
                    break;
                }
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
            }
        }
    }
}