package net.swanndolia.pieces;

import lombok.*;
import net.swanndolia.utils.Color;

@Data
public class Pawn extends Piece implements AllowedMove{
    public Pawn(Color color){
        this.color = color;
        this.shortName = "P";

    }
    @Override
    public void capture() {

    }

    @Override
    public void move() {

    }

    @Override
    public String toString(){
        return super.toString();
    }
}