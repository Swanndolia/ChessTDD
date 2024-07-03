package net.swanndolia.gameboard;

import lombok.Data;
import net.swanndolia.IHM;
import net.swanndolia.pieces.*;
import net.swanndolia.utils.Color;

import java.util.Arrays;

import static net.swanndolia.utils.ConsoleSpacing.*;

@Data
public class ChessBoard {
    int gameBoardSize = 8;
    Square[][] gameBoard = new Square[gameBoardSize][gameBoardSize];
    boolean whiteToPlay = true;

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
        switch (currentSquare.horizontalCoordinates) {
            case 0, 7 -> currentSquare.setPiece(new Rook(color, currentSquare));
            case 1, 6 -> currentSquare.setPiece(new Knight(color, currentSquare));
            case 2, 5 -> currentSquare.setPiece(new Bishop(color, currentSquare));
            case 3 -> currentSquare.setPiece(new King(color, currentSquare));
            case 4 -> currentSquare.setPiece(new Queen(color, currentSquare));

        }
    }

    private void addPawns(Square[] whitePawnsRow, Square[] blackPawnsRow) {
        Arrays.stream(whitePawnsRow).forEach(square -> square.setPiece(new Pawn(Color.WHITE, square)));
        Arrays.stream(blackPawnsRow).forEach(square -> square.setPiece(new Pawn(Color.BLACK, square)));
    }

    public boolean playMove(int[] moveInput) {
        Square initialSquare     = gameBoard[moveInput[0]][moveInput[1]];
        Square destinationSquare = gameBoard[moveInput[2]][moveInput[3]];
        Piece  pieceToMove       = initialSquare.getPiece();
        if (pieceToMove != null) {
            if (pieceToMove.getColor() == Color.WHITE && isWhiteToPlay() || pieceToMove.getColor() == Color.BLACK && !isWhiteToPlay()) {
                boolean moveIsValid = pieceToMove.move(destinationSquare);
                if (moveIsValid) {
                    initialSquare.emptySquare();
                    destinationSquare.setPiece(pieceToMove);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public Square getSquare(int verticalCoordinate, int horizontalCoordinate){
        return this.gameBoard[verticalCoordinate][horizontalCoordinate];
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
        return gameboard;
    }
}
