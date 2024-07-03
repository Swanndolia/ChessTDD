package net.swanndolia.pieces;

import lombok.*;
import net.swanndolia.utils.Color;

@Data
public class Rook extends Piece implements AllowedMove{
    public Rook(Color color){
        this.color = color;
        this.shortName = "R";

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
