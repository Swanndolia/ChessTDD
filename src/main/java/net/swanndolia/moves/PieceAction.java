package net.swanndolia.moves;

import net.swanndolia.gameboard.Square;

public interface PieceAction {
    boolean capture(Square square);
    boolean move(Square square);
}
