package chessboard;

import net.swanndolia.gameboard.ChessBoard;
import net.swanndolia.gameboard.Square;
import net.swanndolia.pieces.*;
import net.swanndolia.utils.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ChessBoardTest {
    ChessBoard chessBoard = new ChessBoard();

    @BeforeEach
    public void initChessboard() {
        this.chessBoard.initChessboard();
    }

    @RepeatedTest(50)
    public void gameboardShouldAlternateBlackAndWhiteCells() {
        Color squareColor;
        Square square = this.chessBoard.getGameBoard()[new Random().nextInt(this.chessBoard.getGameBoardSize() - 1)][new Random().nextInt(
                this.chessBoard.getGameBoardSize() - 1)];
        if (square.getHorizontalCoordinates() % 2 == 0 && square.getVerticalCoordinates() % 2 == 0) {
            squareColor = Color.WHITE;
        } else if (square.getHorizontalCoordinates() % 2 != 0 && square.getVerticalCoordinates() % 2 != 0) {
            squareColor = Color.WHITE;
        } else {
            squareColor = Color.BLACK;
        }
        assertThat(square.getColor()).isEqualTo(squareColor);
    }

    @Test
    public void gameBoardShouldBeFilledWithAllStartingPiecesAtCorrectPosition() {
        Piece piece = null;
        Color color = Color.WHITE;
        for (int vertical = 0; vertical < this.chessBoard.getGameBoardSize(); vertical++) {
            if (vertical == 6) {
                color = Color.BLACK;
            }
            for (int horizontal = 0; horizontal < chessBoard.getGameBoardSize(); horizontal++) {
                if (vertical == 0 || vertical == 7) {
                    switch (horizontal) {
                        case 0, 7 -> piece = new Rook(color, this.chessBoard.getSquare(vertical, horizontal));
                        case 1, 6 -> piece = new Knight(color, this.chessBoard.getSquare(vertical, horizontal));
                        case 2, 5 -> piece = new Bishop(color, this.chessBoard.getSquare(vertical, horizontal));
                        case 3 -> piece = new King(color, this.chessBoard.getSquare(vertical, horizontal));
                        case 4 -> piece = new Queen(color, this.chessBoard.getSquare(vertical, horizontal));
                    }
                } else if (vertical == 1 || vertical == 6) {
                    piece = new Pawn(color, this.chessBoard.getSquare(vertical, horizontal));
                } else {
                    piece = null;
                }
                assertThat(piece).isEqualTo(this.chessBoard.getSquare(vertical, horizontal).getPiece());
            }
        }
    }
}
