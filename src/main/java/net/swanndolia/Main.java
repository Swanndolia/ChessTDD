package net.swanndolia;

import net.swanndolia.gameboard.ChessBoard;
import net.swanndolia.gameboard.Square;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.initChessboard();
        autoPlay(chessBoard);
        //realPlay(chessBoard);
    }

    private static void realPlay(ChessBoard chessBoard) {
        while (true) {
            if (chessBoard.isWhiteToPlay()) {
                IHM.sendMessageToUser("White to Play");
            } else {
                IHM.sendMessageToUser("Black to Play");
            }
            IHM.sendMessageToUser(chessBoard.toString());
            String userInput = IHM.askUserInput();
            if (userInput.contains("exit")) {
                return;
            }
            int[] moveInput = IHM.parseUserInput(userInput);
            if (moveInput != null) {
                if (chessBoard.playMove(moveInput)) {
                    chessBoard.setWhiteToPlay(!chessBoard.isWhiteToPlay());
                }
            } else {
                IHM.sendMessageToUser("Invalid Input");
            }
        }
    }

    private static void autoPlay(ChessBoard chessBoard) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 50000; i++) {
                    int[]  moveInput = new int[4];
                    Square square;
                    if (chessBoard.isWhiteToPlay()) {
                        square = chessBoard.getWhitePieces().get(new Random().nextInt(chessBoard.getWhitePieces().size())).getSquare();
                    } else {
                        square = chessBoard.getBlackPieces().get(new Random().nextInt(chessBoard.getBlackPieces().size())).getSquare();
                    }
                    try {
                        Square destSquare = square.getPiece().getAllowedSquares().get(
                                new Random().nextInt(square.getPiece().getAllowedSquares().size()));
                        moveInput[0] = square.getVerticalCoordinates();
                        moveInput[1] = square.getHorizontalCoordinates();
                        moveInput[2] = destSquare.getVerticalCoordinates();
                        moveInput[3] = destSquare.getHorizontalCoordinates();
                    } catch (Exception e) {
                        IHM.sendMessageToUser("No available squares");
                    }
                    IHM.sendMessageToUser(String.valueOf(i));
                    IHM.sendMessageToUser(chessBoard.toString());
                    if (chessBoard.playMove(moveInput)) {
                        chessBoard.setWhiteToPlay(!chessBoard.isWhiteToPlay());
                    }
                    try {
                        Thread.sleep(12);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        thread.start();
    }
}