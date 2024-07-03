package net.swanndolia;

import net.swanndolia.gameboard.ChessBoard;

public class Main {
    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.initChessboard();
        while (true) {
            String userInput;
            if (chessBoard.isWhiteToPlay()) {
                IHM.sendMessageToUser("White to play; Write a move or type 'exit' to quit.");
                chessBoard.setWhitePiecesTopside(false);
                System.out.println(chessBoard);
                userInput = IHM.askUserInput();
                if (userInput.contains("exit")) {
                    return;
                }
                int[] moveInput = IHM.parseUserInput(userInput);
                if (moveInput != null) {
                    chessBoard.playMove(moveInput);
                    chessBoard.setWhiteToPlay(false);
                } else {
                    IHM.sendMessageToUser("Invalid Input");
                }
            } else {
                IHM.sendMessageToUser("Black to play; Write a move or type 'exit' to quit.");
                chessBoard.setWhitePiecesTopside(true);
                System.out.println(chessBoard);
                userInput = IHM.askUserInput();
                if (userInput.contains("exit")) {
                    return;
                }
                int[] moveInput = IHM.parseUserInput(userInput);
                if (moveInput != null) {
                    chessBoard.playMove(moveInput);
                    chessBoard.setWhiteToPlay(true);
                } else {
                    IHM.sendMessageToUser("Invalid Input");
                }
            }

        }
    }
}