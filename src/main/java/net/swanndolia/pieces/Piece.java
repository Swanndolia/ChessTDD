package net.swanndolia.pieces;

import lombok.Data;
import net.swanndolia.IHM;
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
    public boolean capture(Square square) {
        if(this.color == Color.WHITE) {
            square.getPiece().getSquare().getGameboard().getCapturedBlackPieces().add(square.getPiece());
        }else{
            square.getPiece().getSquare().getGameboard().getCapturedWhitePieces().add(square.getPiece());
        }
        return true;
    }

    @Override
    public boolean move(Square square){
        return moveIsValid(square);
    }

    public boolean moveIsValid(Square square) {
        ComputeMove   computeMove   = new ComputeMove(this, square);
        MoveDirection moveDirection = computeMove.getMoveDirection();

        if (this.allowedMoveDirection.contains(moveDirection)) {
            int moveDistance = computeMove.getMoveDistance();
            if (moveDistance <= this.getMaximumMoveDistance()) {
                return !computeMove.isMoveBlocked();
            }
            IHM.sendMessageToUser(
                    "Your " + this.fullName + " can only move " + this.getMaximumMoveDistance() + " square " + this.allowedMoveDirection);
            return false;
        }
        IHM.sendMessageToUser("A " + this.fullName + " can only move: " + this.allowedMoveDirection + " not " + moveDirection);
        return false;
    }

    public String getHighContrastColoredIcon() {
        if (this.color == Color.BLACK) {
            return BLACK_TEXT + WHITE_BACK_HIGH + this.icon;
        } else {
            return WHITE_TEXT_HIGH + BLACK_BACK + this.icon;
        }
    }

}