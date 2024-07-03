package net.swanndolia.pieces;

import lombok.*;
import net.swanndolia.utils.Color;

@Data
public class Bishop extends Piece implements AllowedMove{
    public Bishop(Color color){
        this.color = color;
        this.shortName = "B";

    }
    @Override
    public void capture() {

    }

    @Override
    public void move() {

    }

    @Override
    public String toString() {
        return super.toString();
    }
}