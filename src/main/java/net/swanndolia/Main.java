package net.swanndolia;

import net.swanndolia.gameboard.ChessBoard;

public class Main {
    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.initChessboard();
        while (true) {
            IHM.sendMessageToUser("Write a move or type 'exit' to quit.");
            System.out.println(chessBoard);
            String userInput = IHM.askUserInput();
            if (userInput.contains("exit")) {
                return;
            }
            int[] moveInput = IHM.parseUserInput(userInput);
            if (moveInput != null) {
                if (chessBoard.playMove(moveInput)) {
                    chessBoard.setWhiteToPlay(!chessBoard.isWhiteToPlay());
                    chessBoard.setWhitePiecesTopside(!chessBoard.isWhitePiecesTopside());
                } else {
                    IHM.sendMessageToUser("Invalid Move");
                }
            } else {
                IHM.sendMessageToUser("Invalid Input");
            }
        }
    }
}