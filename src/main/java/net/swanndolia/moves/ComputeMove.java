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

    public MoveDirection getMoveDirection() {

        this.horizontalMovement = this.piece.getSquare().getHorizontalCoordinates() - this.square.getHorizontalCoordinates();
        this.verticalMovement = this.piece.getSquare().getVerticalCoordinates() - this.square.getVerticalCoordinates();

        if (this.piece.getColor() == Color.WHITE) {
            this.verticalMovement = -verticalMovement;
        } //fix rook and bishop invalid movement, also fix pawn cant move 2 times

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

    public int getMoveDistance() {
        switch (this.moveDirection) {
            case DIAGONAL_DOWN_LEFT, DIAGONAL_UP_LEFT, DIAGONAL_DOWN_RIGHT, DIAGONAL_UP_RIGHT, LEFT, RIGHT -> {
                this.moveDistance = this.piece.getSquare().getHorizontalCoordinates() - this.square.getHorizontalCoordinates();
            }
            case FORWARD, BACKWARD -> this.moveDistance = this.piece.getSquare().getVerticalCoordinates() - this.square.getVerticalCoordinates();
        }
        return this.moveDistance;
    }

    public boolean isMoveBlocked() {
        boolean moveIsBlocked = false;
        if (this.square.getPiece() != null && this.square.getPiece().getColor() == this.piece.getColor()) {
            moveIsBlocked = true;
            IHM.sendMessageToUser("The destination cell already contain your: " + this.square.getPiece().getFullName());
        } else {
            ChessBoard chessBoard = this.piece.getSquare().getGameboard();
            for (int i = 1; i <= this.moveDistance; i++) {
                if (this.piece.getColor() == Color.BLACK) {
                    i = -i;
                }
                IHM.sendMessageToUser(
                        this.piece.getSquare().getVerticalCoordinates() - i + " " + (this.piece.getSquare().getHorizontalCoordinates() - i));
                switch (this.moveDirection) {
                    case LEFT -> {
                        if (chessBoard.getSquare(this.piece.getSquare().getVerticalCoordinates(),
                                                 this.piece.getSquare().getHorizontalCoordinates() - i
                        ).getPiece() != null) {
                            return true;
                        }
                    }
                    case RIGHT -> {
                        if (chessBoard.getSquare(this.piece.getSquare().getVerticalCoordinates(),
                                                 this.piece.getSquare().getHorizontalCoordinates() + i
                        ).getPiece() != null) {
                            return true;
                        }
                    }
                    case DIAGONAL_UP_LEFT -> {
                        if (chessBoard.getSquare(this.piece.getSquare().getVerticalCoordinates() + i,
                                                 this.piece.getSquare().getHorizontalCoordinates() - i
                        ).getPiece() != null) {
                            return true;
                        }
                    }
                    case DIAGONAL_UP_RIGHT -> {
                        if (chessBoard.getSquare(this.piece.getSquare().getVerticalCoordinates() + i,
                                                 this.piece.getSquare().getHorizontalCoordinates() + i
                        ).getPiece() != null) {
                            return true;
                        }
                    }
                    case DIAGONAL_DOWN_LEFT -> {
                        if (chessBoard.getSquare(this.piece.getSquare().getVerticalCoordinates() - i,
                                                 this.piece.getSquare().getHorizontalCoordinates() - i
                        ).getPiece() != null) {
                            return true;
                        }
                    }
                    case DIAGONAL_DOWN_RIGHT -> {
                        if (chessBoard.getSquare(this.piece.getSquare().getVerticalCoordinates() - i,
                                                 this.piece.getSquare().getHorizontalCoordinates() + i
                        ).getPiece() != null) {
                            return true;
                        }
                    }
                    case FORWARD -> {
                        if (chessBoard.getSquare(this.piece.getSquare().getVerticalCoordinates() + i,
                                                 this.piece.getSquare().getHorizontalCoordinates()
                        ).getPiece() != null) {
                            return true;
                        }
                    }
                    case BACKWARD -> {
                        if (chessBoard.getSquare(this.piece.getSquare().getVerticalCoordinates() - i,
                                                 this.piece.getSquare().getHorizontalCoordinates()
                        ).getPiece() != null) {
                            return true;
                        }
                    }
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
}
