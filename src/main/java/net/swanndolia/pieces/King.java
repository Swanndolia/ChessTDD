package net.swanndolia.pieces;

import lombok.*;
import net.swanndolia.utils.Color;

@Data
public class King extends Piece implements AllowedMove{
    public King(Color color){
        this.color = color;
        this.shortName = "K";
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