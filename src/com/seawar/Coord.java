package com.seawar;

public class Coord {
    public int x,y;
    Coord(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public Coord clone()
    {
        return new Coord(this.x,this.y);
    }
    public boolean isEquals(Coord coord)
    {
        return (this.x==coord.x && this.y==coord.y);
    }
    Coord() { this.x=this.y=0;}
}
