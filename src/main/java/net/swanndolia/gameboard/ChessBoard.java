package net.swanndolia.gameboard;

import lombok.Data;
import net.swanndolia.pieces.*;
import net.swanndolia.utils.Color;

import java.util.Arrays;

@Data
public class ChessBoard {
    int gameBoardSize = 8;
    Square[][] gameBoard = new Square[gameBoardSize][gameBoardSize];

    public void initChessboard() {
        for (int vertical = 0; vertical < gameBoardSize; vertical++) {
            for (int horizontal = 0; horizontal < gameBoardSize; horizontal++) {
                Square currentSquare = gameBoard[vertical][horizontal] = Square.builder().verticalCoordinates(vertical).horizontalCoordinates(
                        horizontal).color(handleSquareColor(vertical, horizontal)).piece(null).build();
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
        return vertical % 2 == 0 && horizontal % 2 == 0 ? Color.WHITE : Color.BLACK;
    }

    private void addPieces(Square currentSquare, Color color) {
        switch (currentSquare.horizontalCoordinates) {
            case 0, 7 -> currentSquare.setPiece(new Rook(color));
            case 1, 6 -> currentSquare.setPiece(new Knight(color));
            case 2, 5 -> currentSquare.setPiece(new Bishop(color));
            case 3 -> currentSquare.setPiece(new King(color));
            case 4 -> currentSquare.setPiece(new Queen(color));
        }
    }

    private void addPawns(Square[] whitePawnsRow, Square[] blackPawnsRow) {
        Arrays.stream(whitePawnsRow).forEach(square -> square.setPiece(new Pawn(Color.WHITE)));
        Arrays.stream(blackPawnsRow).forEach(square -> square.setPiece(new Pawn(Color.BLACK)));
    }

    @Override
    public String toString(){
        String gameboard = "";
        for (int vertical = 0; vertical < gameBoardSize; vertical++) {
            for (int horizontal = 0; horizontal < gameBoardSize; horizontal++) {
                gameboard = gameboard.concat(this.gameBoard[vertical][horizontal].toString()).concat(" , ");
            }
            gameboard = gameboard.concat("\n");
        }
        return gameboard;
    }
}
