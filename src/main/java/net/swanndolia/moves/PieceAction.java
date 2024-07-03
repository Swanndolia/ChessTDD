package net.swanndolia.moves;

import net.swanndolia.gameboard.Square;

public interface PieceAction {
    void capture(Square square);
    void move(Square square);
}
