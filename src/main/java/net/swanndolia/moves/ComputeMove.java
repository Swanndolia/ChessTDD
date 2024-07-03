package net.swanndolia.moves;

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

    public ComputeMove(Piece piece, Square square) {
        this.piece = piece;
        this.square = square;
    }

    public MoveDirection getMoveDirection() {

        this.horizontalMovement = this.piece.getSquare().getHorizontalCoordinates() - this.square.getHorizontalCoordinates();
        this.verticalMovement = this.piece.getSquare().getVerticalCoordinates() - this.square.getVerticalCoordinates();

        if (this.piece.getColor() == Color.WHITE) {
            this.verticalMovement = -this.verticalMovement;
        }

        if (this.horizontalMovement == 0) {
            if (this.verticalMovement == 0) {
                this.moveDirection = MoveDirection.INVALID;
            } else {
                this.moveDirection = this.verticalMovement > 0 ? MoveDirection.FORWARD : MoveDirection.BACKWARD;
            }
        } else if (this.verticalMovement == 0) {
            this.moveDirection = this.horizontalMovement > 0 ? MoveDirection.RiGHT : MoveDirection.LEFT;
        } else if (this.horizontalMovement == this.verticalMovement) {
            this.moveDirection = this.horizontalMovement > 0 ? MoveDirection.DIAGONAL_UP_RIGHT : MoveDirection.DIAGONAL_DOWN_LEFT;
        } else if (this.horizontalMovement == -this.verticalMovement) {
            this.moveDirection = this.horizontalMovement > 0 ? MoveDirection.DIAGONAL_DOWN_RIGHT : MoveDirection.DIAGONAL_UP_LEFT;
        } else if (Math.abs(this.verticalMovement) == 1 && Math.abs(this.horizontalMovement) == 2 ||
                Math.abs(this.verticalMovement) == 2 && Math.abs(this.horizontalMovement) == 1) {
            this.moveDirection = MoveDirection.KNIGHT;
        } else {
            this.moveDirection = MoveDirection.INVALID;
        }
        return this.moveDirection;
    }

    public int getMoveDistance() {
        this.moveDistance = Math.abs(this.horizontalMovement) > Math.abs(this.verticalMovement) ? this.horizontalMovement : this.verticalMovement;
        return this.moveDistance;
    }

    public boolean isMoveBlocked() {
        boolean moveIsBlocked = false;
        if (this.square.getPiece() != null && this.square.getPiece().getColor() == this.piece.getColor()) {
            moveIsBlocked = true;
            IHM.sendMessageToUser("The destination cell already contain your: " + square.getPiece().getFullName());
        } else {
            ChessBoard chessBoard = this.square.getGameboard();
            for (int i = 1; i <= this.moveDistance; i++) {
                if (this.piece.getColor() == Color.BLACK) {
                    i = -i;
                }
                switch (this.moveDirection) {
                    case LEFT -> moveIsBlocked =
                            chessBoard.getSquare(square.getVerticalCoordinates(), square.getHorizontalCoordinates() - i).getPiece() != null;
                    case RiGHT -> moveIsBlocked =
                            chessBoard.getSquare(square.getVerticalCoordinates(), square.getHorizontalCoordinates() + i).getPiece() != null;
                    case DIAGONAL_UP_LEFT -> moveIsBlocked =
                            chessBoard.getSquare(square.getVerticalCoordinates() + i, square.getHorizontalCoordinates() - i).getPiece() != null;
                    case DIAGONAL_UP_RIGHT -> moveIsBlocked =
                            chessBoard.getSquare(square.getVerticalCoordinates() + i, square.getHorizontalCoordinates() + i).getPiece() != null;
                    case DIAGONAL_DOWN_LEFT -> moveIsBlocked =
                            chessBoard.getSquare(square.getVerticalCoordinates() - i, square.getHorizontalCoordinates() - i).getPiece() != null;
                    case DIAGONAL_DOWN_RIGHT -> moveIsBlocked =
                            chessBoard.getSquare(square.getVerticalCoordinates() - i, square.getHorizontalCoordinates() + i).getPiece() != null;
                    case FORWARD -> moveIsBlocked =
                            chessBoard.getSquare(square.getVerticalCoordinates() + i, square.getHorizontalCoordinates()).getPiece() != null;
                    case BACKWARD -> moveIsBlocked =
                            chessBoard.getSquare(square.getVerticalCoordinates() - i, square.getHorizontalCoordinates()).getPiece() != null;
                    case KNIGHT -> moveIsBlocked = false;
                    case INVALID -> moveIsBlocked = true;
                }
                if (this.piece.getColor() == Color.BLACK) {
                    i = -i;
                }
            }

        }
        if (moveIsBlocked) {
            IHM.sendMessageToUser("The destination cell is blocked ");
        }
        return moveIsBlocked;
    }

    public static int[] convertMoveCoordinate(String userInput) {
        Map<Character, Integer> columnToIntMap = new HashMap<Character, Integer>() {{//RENAME
            put('a', 7);
            put('b', 6);
            put('c', 5);
            put('d', 4);
            put('e', 3);
            put('f', 2);
            put('g', 1);
            put('h', 0);
        }};
        Map<Character, Integer> rowToIntMap = new HashMap<Character, Integer>() {{//RENAME
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
}
