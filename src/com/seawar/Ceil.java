package com.seawar;

public class Ceil {
    public Coord coord;
    public boolean is_death;
    Ceil (int x,int y,boolean is_death)
    {
        coord = new Coord(x,y);
        this.is_death=is_death;
    }
    public Ceil clone()
    {
        return new Ceil(this.coord.x,this.coord.y,this.is_death);
    }
}
