package chessboard;

import net.swanndolia.gameboard.ChessBoard;
import net.swanndolia.gameboard.Square;
import net.swanndolia.moves.MoveDirection;
import net.swanndolia.utils.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

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

    @ParameterizedTest
    @EnumSource(MoveDirection.class)
    public void givenASquareOnAChessBoardIShouldBeAbleToGetTheNextSquareInAnyDirectionFromWhitePerspective(MoveDirection moveDirection) {
        Square square          = chessBoard.getSquare(4, 4);
        Square nextSquare      = chessBoard.getNextSquare(square, moveDirection, Color.WHITE);
        Square expectedSquared = square;
        switch (moveDirection) {
            case LEFT -> expectedSquared = chessBoard.getSquare(4, 5);
            case RIGHT -> expectedSquared = chessBoard.getSquare(4, 3);
            case DIAGONAL_UP_LEFT -> expectedSquared = chessBoard.getSquare(5, 5);
            case DIAGONAL_UP_RIGHT -> expectedSquared = chessBoard.getSquare(5, 3);
            case DIAGONAL_DOWN_LEFT -> expectedSquared = chessBoard.getSquare(3, 5);
            case DIAGONAL_DOWN_RIGHT -> expectedSquared = chessBoard.getSquare(3, 3);
            case FORWARD -> expectedSquared = chessBoard.getSquare(5, 4);
            case BACKWARD -> expectedSquared = chessBoard.getSquare(3, 4);
        }
        assertThat(nextSquare).isEqualTo(expectedSquared);
    }

    @ParameterizedTest
    @EnumSource(MoveDirection.class)
    public void givenASquareOnAChessBoardIShouldBeAbleToGetTheNextSquareInAnyDirectionFromBlackPerspective(MoveDirection moveDirection) {
        Square square          = chessBoard.getSquare(4, 4);
        Square nextSquare      = chessBoard.getNextSquare(square, moveDirection, Color.BLACK);
        Square expectedSquared = square;
        switch (moveDirection) {
            case LEFT -> expectedSquared = chessBoard.getSquare(4, 3);
            case RIGHT -> expectedSquared = chessBoard.getSquare(4, 5);
            case DIAGONAL_UP_LEFT -> expectedSquared = chessBoard.getSquare(3, 3);
            case DIAGONAL_UP_RIGHT -> expectedSquared = chessBoard.getSquare(3, 5);
            case DIAGONAL_DOWN_LEFT -> expectedSquared = chessBoard.getSquare(5, 3);
            case DIAGONAL_DOWN_RIGHT -> expectedSquared = chessBoard.getSquare(5, 5);
            case FORWARD -> expectedSquared = chessBoard.getSquare(3, 4);
            case BACKWARD -> expectedSquared = chessBoard.getSquare(5, 4);
        }
        assertThat(nextSquare).isEqualTo(expectedSquared);
    }
}
