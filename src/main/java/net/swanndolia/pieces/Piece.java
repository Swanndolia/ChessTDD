package net.swanndolia.pieces;

import net.swanndolia.utils.Color;

public abstract class Piece {
    Color color;
    String fullName;
    String shortName;

    @Override
    public String toString() {
        return this.shortName.concat(this.color.name().substring(0, 1));
    }
}
