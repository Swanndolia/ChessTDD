package net.swanndolia.gameboard;

import lombok.Data;
import net.swanndolia.IHM;
import net.swanndolia.moves.MoveDirection;
import net.swanndolia.pieces.*;
import net.swanndolia.utils.Color;
import net.swanndolia.utils.ColorList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.swanndolia.utils.ConsoleSpacing.HORIZONTAL_INDEX_SPACING;
import static net.swanndolia.utils.ConsoleSpacing.STARTING_INDEX_SPACING;

@Data
public class ChessBoard {
    public List<Piece> capturedWhitePieces = new ArrayList<Piece>();
    public List<Piece> capturedBlackPieces = new ArrayList<Piece>();
    public List<Piece> whitePieces = new ArrayList<Piece>();
    public List<Piece> blackPieces = new ArrayList<Piece>();
    int gameBoardSize = 8;
    Square[][] gameBoard = new Square[gameBoardSize][gameBoardSize];
    boolean whiteToPlay = true;
    King whiteKing;
    King blackKing;

    public void initChessboard() {
        for (int vertical = 0; vertical < gameBoardSize; vertical++) {
            for (int horizontal = 0; horizontal < gameBoardSize; horizontal++) {
                Square currentSquare = gameBoard[vertical][horizontal] = Square.builder().verticalCoordinates(vertical).horizontalCoordinates(
                        horizontal).color(handleSquareColor(vertical, horizontal)).piece(null).gameboard(this).build();
                if (vertical == 0) {
                    addPieces(currentSquare, Color.WHITE);
                } else if (vertical == 7) {
                    addPieces(currentSquare, Color.BLACK);
                }
            }
        }
        addPawns(gameBoard[1], gameBoard[6]);
    }

    private Color handleSquareColor(int vertical, int horizontal) {
        return (vertical % 2 == 0 && horizontal % 2 == 0) || (vertical % 2 != 0 && horizontal % 2 != 0) ? Color.WHITE : Color.BLACK;
    }

    private void addPieces(Square currentSquare, Color color) {
        Piece piece = null;
        switch (currentSquare.horizontalCoordinates) {
            case 0, 7 -> piece = new Rook(color, currentSquare);
            case 1, 6 -> piece = new Knight(color, currentSquare);
            case 2, 5 -> piece = new Bishop(color, currentSquare);
            case 3 -> piece = new King(color, currentSquare);
            case 4 -> piece = new Queen(color, currentSquare);
        }
        switch (color) {
            case WHITE -> this.whitePieces.add(piece);
            case BLACK -> this.blackPieces.add(piece);
        }
        currentSquare.setPiece(piece);
    }

    private void addPawns(Square[] whitePawnsRow, Square[] blackPawnsRow) {
        Arrays.stream(whitePawnsRow).forEach(square -> {
            Pawn pawn = new Pawn(Color.WHITE, square);
            square.setPiece(pawn);
            whitePieces.add(pawn);
        });
        Arrays.stream(blackPawnsRow).forEach(square -> {
            Pawn pawn = new Pawn(Color.BLACK, square);
            square.setPiece(pawn);
            blackPieces.add(pawn);
        });
    }

    public boolean playMove(int[] moveInput) {
        Square initialSquare     = gameBoard[moveInput[0]][moveInput[1]];
        Square destinationSquare = gameBoard[moveInput[2]][moveInput[3]];
        Piece  pieceToMove       = initialSquare.getPiece();
        if (pieceToMove != null) {
            if (pieceToMove.getColor() == Color.WHITE && isWhiteToPlay() || pieceToMove.getColor() == Color.BLACK && !isWhiteToPlay()) {
                boolean moveIsValid = pieceToMove.move(destinationSquare);
                if (moveIsValid) {
                    Piece backupDestPiece = destinationSquare.getPiece();
                    updateCheckedSquaresAfterMove(initialSquare, destinationSquare);
                                        if (this.whiteToPlay && this.whiteKing.getSquare().getIsAttacked().get(Color.BLACK) ||
                            !this.whiteToPlay && this.blackKing.getSquare().getIsAttacked().get(Color.WHITE)) {
                        initialSquare.setPiece(destinationSquare.getPiece());
                        destinationSquare.setPiece(backupDestPiece);
                        initialSquare.getPiece().setSquare(initialSquare);
                        IHM.sendMessageToUser("You can't be in check at the end of your turn");
                        return false;
                    } else if (!this.whiteToPlay && this.whiteKing.getSquare().getIsAttacked().get(Color.BLACK)) {
                        IHM.sendMessageToUser("White king Check !");
                        return true;
                    } else if (this.whiteToPlay && this.blackKing.getSquare().getIsAttacked().get(Color.WHITE)) {
                        IHM.sendMessageToUser("Black king Check !");
                        return true;
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private void updateCheckedSquaresAfterMove(Square initialSquare, Square destinationSquare) {
        destinationSquare.setPiece(initialSquare.getPiece());
        initialSquare.emptySquare();
        destinationSquare.getPiece().setSquare(destinationSquare);
        for (Square[] squareRow : gameBoard) {
            for (Square square : squareRow) {
                for (Color color : Color.values()) {
                    square.setIsAttacked(color, false);
                }
            }
        }
        for (Piece piece : this.whitePieces) {
            if (piece.getClass() == King.class) {
                this.whiteKing = (King) piece;
            }
            piece.checkAttackedSquares();
        }
        for (Piece piece : this.blackPieces) {
            if (piece.getClass() == King.class) {
                this.blackKing = (King) piece;
            }
            piece.checkAttackedSquares();
        }
    }

    public Square getSquare(int verticalCoordinate, int horizontalCoordinate) {
        return this.gameBoard[verticalCoordinate][horizontalCoordinate];
    }

    public Square getNextSquare(Square square, MoveDirection moveDirection, Color color) {
        int horizontal = 1;
        int vertical   = 1;
        if (color == Color.WHITE) {
            horizontal = -horizontal;
        } else {
            vertical = -vertical;
        }
        switch (moveDirection) {
            case LEFT -> square = getSquare(square.getVerticalCoordinates(), square.getHorizontalCoordinates() - horizontal);
            case RIGHT -> square = getSquare(square.getVerticalCoordinates(), square.getHorizontalCoordinates() + horizontal);
            case DIAGONAL_UP_LEFT -> square = getSquare(square.getVerticalCoordinates() + vertical, square.getHorizontalCoordinates() - horizontal);
            case DIAGONAL_UP_RIGHT -> square = getSquare(square.getVerticalCoordinates() + vertical, square.getHorizontalCoordinates() + horizontal);
            case DIAGONAL_DOWN_LEFT -> square = getSquare(square.getVerticalCoordinates() - vertical, square.getHorizontalCoordinates() - horizontal);
            case DIAGONAL_DOWN_RIGHT ->
                    square = getSquare(square.getVerticalCoordinates() - vertical, square.getHorizontalCoordinates() + horizontal);
            case FORWARD -> square = getSquare(square.getVerticalCoordinates() + vertical, square.getHorizontalCoordinates());
            case BACKWARD -> square = getSquare(square.getVerticalCoordinates() - vertical, square.getHorizontalCoordinates());
        }
        return square;
    }

    @Override
    public String toString() {
        String        gameboard          = "";
        StringBuilder horizontalNotation = new StringBuilder(STARTING_INDEX_SPACING + "H,G,F,E,D,C,B,A" + STARTING_INDEX_SPACING);
        if (!whiteToPlay) {
            for (int vertical = 0; vertical < gameBoardSize; vertical++) {
                gameboard = gameboard.concat(vertical + 1 + " ");
                for (int horizontal = 0; horizontal < gameBoardSize; horizontal++) {
                    gameboard = gameboard.concat(this.gameBoard[vertical][horizontal].toString());
                }
                gameboard = gameboard.concat("\n");
            }
            gameboard = gameboard.concat(horizontalNotation.toString().replaceAll(",", HORIZONTAL_INDEX_SPACING));
        } else {
            for (int vertical = gameBoardSize - 1; vertical >= 0; vertical--) {
                gameboard = gameboard.concat(vertical + 1 + " ");
                for (int horizontal = gameBoardSize - 1; horizontal >= 0; horizontal--) {
                    gameboard = gameboard.concat(this.gameBoard[vertical][horizontal].toString());
                }
                gameboard = gameboard.concat("\n");
            }
            gameboard = gameboard.concat(horizontalNotation.reverse().toString().replaceAll(",", HORIZONTAL_INDEX_SPACING));
        }
        if (!this.capturedWhitePieces.isEmpty()) {
            gameboard = gameboard.concat("\n");
            for (Piece piece : this.capturedWhitePieces) {
                gameboard = gameboard.concat(ColorList.WHITE_TEXT_HIGH + piece.getHighContrastColoredIcon() + ColorList.RESET);
            }
        }
        if (!this.capturedBlackPieces.isEmpty()) {
            gameboard = gameboard.concat("\n");
            for (Piece piece : this.capturedBlackPieces) {
                gameboard = gameboard.concat(ColorList.BLACK_TEXT + piece.getHighContrastColoredIcon() + ColorList.RESET);
            }
        }
        return gameboard;
    }
}
