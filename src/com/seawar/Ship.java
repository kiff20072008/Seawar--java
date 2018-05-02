package com.seawar;

import java.util.Vector;

public class Ship
{
    public Vector<Ceil> ship;
    private Direction dir;

    private void updateCoord()
    {
        Coord offset = getOffset(this.dir);

        for(int i=1;i<ship.size();++i)
        {
            ship.elementAt(i).coord.x=ship.elementAt(0).coord.x+offset.x*i;
            ship.elementAt(i).coord.y=ship.elementAt(0).coord.y+offset.y*i;
        }

    }
    private boolean checkCoord()
    {
        updateCoord();
        for(Ceil it:ship)
        {
            if(it.coord.x>9||it.coord.y>9 || it.coord.x<0 ||it.coord.y<0)
            {
                return false;
            }
        }
        return true;
    }

    Ship(int size,int x,int y,Direction dir) throws Exception {
        if(size>4 || size<1)
        {
            throw new Exception("Wrong ship size");
        }
        ship = new Vector<>(size);
        for(int i=0;i<size;++i)
        {
            ship.add(new Ceil(x,y,false));
        }
        this.dir=dir;
        if(!checkCoord())
        {
           throw new Exception("Wrong coordinates");
        }
    }
    public boolean isDeath()
    {
        for(Ceil it:ship)
        {
            if(!it.is_death)
            {
                return false;
            }
        }
        return true;
    }
    public void turnShip()
    {
        dir=dir.next();
        if(!checkCoord())
        {
            dir=dir.prev();
        }
        updateCoord();
    }
    public boolean shoot(Coord coord)
    {
        for(int i=0;i<ship.size();++i)
        {
            if(coord.isEquals(ship.elementAt(i).coord))
            {
                System.out.println("Shoot!!!");
                ship.elementAt(i).is_death=true;
                return true;
            }
        }
        return false;
    }
    public void moveShip(Direction dir)
    {
        Coord offset=getOffset(dir);

        ship.elementAt(0).coord.x+=offset.x;
        ship.elementAt(0).coord.y+=offset.y;
        if(!checkCoord())
        {
            ship.elementAt(0).coord.x-=offset.x;
            ship.elementAt(0).coord.y-=offset.y;
        }
        updateCoord();
    }
    public boolean setupShip(Coord coord,Direction dir)
    {
        ship.elementAt(0).coord=coord.clone();
        this.dir=dir;
        return checkCoord();

    }
    public Coord getShipOffset()
    {
        return getOffset(dir);

    }
    public static Coord getOffset(Direction dir)
    {
        int gorizontal_offset = ((dir == Direction.UP) || (dir == Direction.DOWN)) ? 0 : (dir == Direction.RIGHT ? 1 : -1);
        int vertikal_offset = ((dir == Direction.LEFT) || (dir == Direction.RIGHT)) ? 0 : (dir == Direction.DOWN ? 1 : -1);
        return new Coord(gorizontal_offset, vertikal_offset);

    }

}
