package net.swanndolia;

import net.swanndolia.gameboard.ChessBoard;

public class Main {
    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.initChessboard();
        System.out.println(chessBoard);
    }
}