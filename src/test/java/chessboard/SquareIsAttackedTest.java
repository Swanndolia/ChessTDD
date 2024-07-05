package chessboard;

import net.swanndolia.gameboard.ChessBoard;
import net.swanndolia.utils.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SquareIsAttackedTest {

    ChessBoard chessBoard = new ChessBoard();

    @BeforeEach
    public void initChessboard() {
        this.chessBoard.initChessboard();
    }

    @ParameterizedTest
    @EnumSource(Color.class)
    public void givenTheGameJustStartedWhiteAndBlackShouldBothHave16Pieces(Color color) {
        assertThat(color == Color.WHITE ? chessBoard.getWhitePieces().size() : chessBoard.getBlackPieces().size()).isEqualTo(16);
    }
}
