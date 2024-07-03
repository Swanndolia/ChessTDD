package chessboard;

import lombok.extern.java.Log;
import net.swanndolia.gameboard.ChessBoard;
import net.swanndolia.gameboard.Square;
import net.swanndolia.utils.Color;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


import java.util.Random;

public class ChessBoardTest {
    ChessBoard chessBoard = new ChessBoard();

    @RepeatedTest(50)
    public void gameboardShouldAlternateBlackAndWhiteCells() {
        chessBoard.initChessboard();
        Square square = chessBoard.getGameBoard()[new Random().nextInt(chessBoard.getGameBoardSize() - 1)][new Random().nextInt(
                chessBoard.getGameBoardSize() - 1)];
        if(square.getHorizontalCoordinates() % 2 == 0  && square.getVerticalCoordinates() % 2 == 0 ){
            assertThat(square.getColor().equals(Color.WHITE));
        } else if(square.getHorizontalCoordinates() % 2 != 0  && square.getVerticalCoordinates() % 2 != 0 ){
            assertThat(square.getColor().equals(Color.WHITE));
        }
        else {
            assertThat(square.getColor().equals(Color.BLACK));
        }
    }
}
