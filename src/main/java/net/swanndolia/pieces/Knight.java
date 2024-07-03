package net.swanndolia.pieces;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.swanndolia.utils.Color;

@Data
public class Knight extends Piece implements AllowedMove{
    public Knight (Color color){
        this.color = color;
        this.shortName = "N";

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