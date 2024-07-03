package net.swanndolia.gameboard;

import lombok.Data;
import net.swanndolia.IHM;
import net.swanndolia.pieces.*;
import net.swanndolia.utils.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ChessBoard {
    int gameBoardSize = 8;
    boolean whitePiecesTopside = false;
    Square[][] gameBoard = new Square[gameBoardSize][gameBoardSize];
    boolean whiteToPlay = true;
    List<Piece> whitePieces = new ArrayList<>();
    List<Piece> blackPieces = new ArrayList<>();

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
        return (vertical % 2 == 0 && horizontal % 2 == 0) || (vertical % 2 != 0 && horizontal % 2 != 0) ? Color.WHITE : Color.BLACK;
    }

    private void addPieces(Square currentSquare, Color color) {
        switch (currentSquare.horizontalCoordinates) {
            case 0, 7 -> {
                Rook rook = new Rook(color, currentSquare);
                if (color == Color.WHITE) {
                    whitePieces.add(rook);
                } else {
                    blackPieces.add(rook);
                }
                currentSquare.setPiece(rook);
            }
            case 1, 6 -> {
                Knight knight = new Knight(color, currentSquare);
                if (color == Color.WHITE) {
                    whitePieces.add(knight);
                } else {
                    blackPieces.add(knight);
                }
                currentSquare.setPiece(new Knight(color, currentSquare));
            }
            case 2, 5 -> {
                Bishop bishop = new Bishop(color, currentSquare);
                if (color == Color.WHITE) {
                    whitePieces.add(bishop);
                } else {
                    blackPieces.add(bishop);
                }
                currentSquare.setPiece(bishop);
            }
            case 3 -> {
                King king = new King(color, currentSquare);
                if (color == Color.WHITE) {
                    whitePieces.add(king);
                } else {
                    blackPieces.add(king);
                }
                currentSquare.setPiece(king);
            }
            case 4 -> {
                Queen queen = new Queen(color, currentSquare);
                if (color == Color.WHITE) {
                    whitePieces.add(queen);
                } else {
                    blackPieces.add(queen);
                }
                currentSquare.setPiece(queen);
            }
        }
    }

    private void addPawns(Square[] whitePawnsRow, Square[] blackPawnsRow) {
        Arrays.stream(whitePawnsRow).forEach(square -> square.setPiece(new Pawn(Color.WHITE, square)));
        Arrays.stream(blackPawnsRow).forEach(square -> square.setPiece(new Pawn(Color.BLACK, square)));
    }

    @Override
    public String toString() {
        String        gameboard          = "";
        StringBuilder horizontalNotation = new StringBuilder("   H  G  F  E  D  C  B  A  ");
        if (whitePiecesTopside) {
            for (int vertical = 0; vertical < gameBoardSize; vertical++) {
                gameboard = gameboard.concat(vertical + 1 + " ");
                for (int horizontal = 0; horizontal < gameBoardSize; horizontal++) {
                    gameboard = gameboard.concat(this.gameBoard[vertical][horizontal].toString());
                }
                gameboard = gameboard.concat("\n");
            }
            gameboard = gameboard.concat(horizontalNotation.toString());
        } else {
            for (int vertical = gameBoardSize - 1; vertical >= 0; vertical--) {
                gameboard = gameboard.concat(vertical + 1 + " ");
                for (int horizontal = gameBoardSize - 1; horizontal >= 0; horizontal--) {
                    gameboard = gameboard.concat(this.gameBoard[vertical][horizontal].toString());
                }
                gameboard = gameboard.concat("\n");
            }
            gameboard = gameboard.concat(horizontalNotation.reverse().toString());
        }
        return gameboard;
    }

    public boolean playMove(int[] moveInput) {
        Square initialSquare     = gameBoard[moveInput[0]][moveInput[1]];
        Square destinationSquare = gameBoard[moveInput[2]][moveInput[3]];
        Piece  pieceToMove       = initialSquare.getPiece();
        if (pieceToMove != null) {
            if (pieceToMove.getColor() == Color.WHITE && isWhiteToPlay() || pieceToMove.getColor() == Color.BLACK && !isWhiteToPlay()) {
                /*boolean moveIsValid = pieceToMove.move(destinationSquare);
                if (moveIsValid) {*/
                initialSquare.emptySquare();
                destinationSquare.setPiece(pieceToMove);
                return true;
                //}
            }
            return false;
        }
        return false;
    }
}
