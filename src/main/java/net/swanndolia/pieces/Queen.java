package net.swanndolia.pieces;

import lombok.*;
import net.swanndolia.utils.Color;

@Data
public class Queen extends Piece implements AllowedMove{
    public Queen(Color color){
        this.color = color;
        this.shortName = "Q";

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
