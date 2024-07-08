package net.swanndolia.moves;

import lombok.Getter;
import lombok.Setter;
import net.swanndolia.IHM;
import net.swanndolia.gameboard.ChessBoard;
import net.swanndolia.gameboard.Square;
import net.swanndolia.pieces.Piece;
import net.swanndolia.utils.Color;

import java.util.HashMap;
import java.util.Map;

public class ComputeMove {
    Piece piece;
    Square square;
    MoveDirection moveDirection;
    int horizontalMovement;
    int verticalMovement;
    int moveDistance;
    @Setter
    @Getter
    String moveResult;

    public ComputeMove(Piece piece, Square square) {
        this.piece = piece;
        this.square = square;
    }

    public static int[] convertMoveCoordinate(String userInput) {
        Map<Character, Integer> columnToIntMap = new HashMap<>() {{
            put('a', 7);
            put('b', 6);
            put('c', 5);
            put('d', 4);
            put('e', 3);
            put('f', 2);
            put('g', 1);
            put('h', 0);
        }};
        Map<Character, Integer> rowToIntMap = new HashMap<>() {{
            put('1', 0);
            put('2', 1);
            put('3', 2);
            put('4', 3);
            put('5', 4);
            put('6', 5);
            put('7', 6);
            put('8', 7);
        }};
        int[] move = new int[4];
        try {
            move[0] = rowToIntMap.get(userInput.charAt(1));
            move[1] = columnToIntMap.get(userInput.charAt(0));
            move[2] = rowToIntMap.get(userInput.charAt(3));
            move[3] = columnToIntMap.get(userInput.charAt(2));
            for (int coordinate : move) {
                if (coordinate < 0 || coordinate > 7) {
                    return null;
                }
            }
            return move;
        } catch (Exception e) {
            IHM.sendMessageToUser(e.getMessage());
            return null;
        }
    }

    public MoveDirection getMoveDirection() {

        this.horizontalMovement = this.piece.getSquare().getHorizontalCoordinates() - this.square.getHorizontalCoordinates();
        this.verticalMovement = this.piece.getSquare().getVerticalCoordinates() - this.square.getVerticalCoordinates();
        getBoardOrientationRelativeMoveDirection();

        if (this.horizontalMovement == 0 && this.verticalMovement == 0) {
            this.moveDirection = MoveDirection.INVALID;
        } else if (this.horizontalMovement == this.verticalMovement) {
            this.moveDirection = this.horizontalMovement > 0 ? MoveDirection.DIAGONAL_UP_RIGHT : MoveDirection.DIAGONAL_DOWN_LEFT;
        } else if (this.horizontalMovement == -this.verticalMovement) {
            this.moveDirection = this.horizontalMovement > 0 ? MoveDirection.DIAGONAL_DOWN_RIGHT : MoveDirection.DIAGONAL_UP_LEFT;
        } else if (Math.abs(this.verticalMovement) == 1 && Math.abs(this.horizontalMovement) == 2 ||
                Math.abs(this.verticalMovement) == 2 && Math.abs(this.horizontalMovement) == 1) {
            this.moveDirection = MoveDirection.KNIGHT;
        } else if (this.horizontalMovement == 0) {
            this.moveDirection = this.verticalMovement > 0 ? MoveDirection.FORWARD : MoveDirection.BACKWARD;
        } else if (this.verticalMovement == 0) {
            this.moveDirection = this.horizontalMovement > 0 ? MoveDirection.RIGHT : MoveDirection.LEFT;
        } else {
            this.moveDirection = MoveDirection.INVALID;
        }
        return this.moveDirection;
    }

    private void getBoardOrientationRelativeMoveDirection() {
        if (this.piece.getColor() == Color.WHITE) {
            this.verticalMovement = this.square.getVerticalCoordinates() - this.piece.getSquare().getVerticalCoordinates();
        }
        if (this.piece.getColor() == Color.BLACK) {
            this.horizontalMovement = this.square.getHorizontalCoordinates() - this.piece.getSquare().getHorizontalCoordinates();
        }
    }

    public int getMoveDistance() {
        switch (this.moveDirection) {
            case DIAGONAL_DOWN_LEFT, DIAGONAL_UP_LEFT, DIAGONAL_DOWN_RIGHT, DIAGONAL_UP_RIGHT, LEFT, RIGHT ->
                    this.moveDistance = this.piece.getSquare().getHorizontalCoordinates() - this.square.getHorizontalCoordinates();
            case FORWARD, BACKWARD -> this.moveDistance = this.piece.getSquare().getVerticalCoordinates() - this.square.getVerticalCoordinates();
        }
        this.moveDistance = Math.abs(this.moveDistance);
        return this.moveDistance;
    }

    public boolean isMoveBlocked() {
        if (this.square.getPiece() != null) {
            if (this.square.getPiece().getColor() == this.piece.getColor()) {
                IHM.sendMessageToUser("The destination cell already contain your: " + this.square.getPiece().getFullName());
                return true;
            } else {
                return arePiecesBlockingPath();
            }
        } else {
            return arePiecesBlockingPath();
        }
    }
    private boolean arePiecesBlockingPath() {
        ChessBoard chessBoard = this.piece.getSquare().getGameboard();
        Square square = this.piece.getSquare();
        for (int i = 1; i < this.moveDistance; i++) {
            IHM.sendMessageToUser(this.moveDirection + " " + this.piece.getColor());
            square = chessBoard.getNextSquare(square, this.moveDirection, this.piece.getColor());
            if (square.getPiece() != null) {
                this.setMoveResult("this piece is blocked by " + square.getPiece().getFullName());
                return true;
            }
        }
        return false;
    }
}
