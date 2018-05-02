package com.seawar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener{
    private JButton computer_field[][];
    private JTextField player_field[][];
    private JButton button_r,button_m;
    private JButton button_up,button_down,button_left,button_right,button_turn,button_apply;
    private  Field field;
    private static int curr_size=4;
    private boolean is_initial,is_manual;
    private static boolean is_game_over=false;
    Main()  {
        JFrame frame = new JFrame("Seawar");
        frame.setSize(1280,720);
        frame.getContentPane().setLayout(new GridLayout(0,20));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        computer_field = new JButton[10][10];
        player_field = new JTextField[10][10];
        field= new Field();
        is_initial=true;
        is_manual=false;

        for(int i=0;i<10;++i)
        {
            for(int j=0;j<10;++j)
            {
                player_field[i][j]=new JTextField();
                player_field[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                player_field[i][j].setHorizontalAlignment(JTextField.CENTER);
                player_field[i][j].setAlignmentY(JTextField.CENTER);
                player_field[i][j].setEditable(false);
                player_field[i][j].setFont(new Font(null,Font.ITALIC,30));
                frame.getContentPane().add(player_field[i][j]);
            }
            for(int j=0;j<10;++j)
            {
                computer_field[i][j]=new JButton(" ");
                computer_field[i][j].setName(Integer.toString(i*10+j));
                computer_field[i][j].setFont(new Font(null,Font.ITALIC,30));
                frame.getContentPane().add(computer_field[i][j]);
                computer_field[i][j].addActionListener(this);
            }


        }
        button_up =new JButton("Up");
        button_down=new JButton("Down");
        button_left=new JButton("Left");
        button_right=new JButton("Right");
        button_turn=new JButton("Turn");
        button_apply=new JButton("Apply");
        button_m=new JButton("Manual");
        button_r=new JButton("Random");

        button_up.setName("Up");
        button_down.setName("Down");
        button_left.setName("Left");
        button_right.setName("Right");
        button_turn.setName("Turn");
        button_apply.setName("Apply");
        button_m.setName("Manual");
        button_r.setName("Random");

        button_up.setVisible(false);
        button_down.setVisible(false);
        button_left.setVisible(false);
        button_right.setVisible(false);
        button_turn.setVisible(false);
        button_apply.setVisible(false);

        button_up.addActionListener(this);
        button_down.addActionListener(this);
        button_left.addActionListener(this);
        button_right.addActionListener(this);
        button_turn.addActionListener(this);
        button_apply.addActionListener(this);
        button_m.addActionListener(this);
        button_r.addActionListener(this);



        frame.getContentPane().add(button_m);
        frame.getContentPane().add(button_r);
        frame.getContentPane().add(button_up);
        frame.getContentPane().add(button_down);
        frame.getContentPane().add(button_left);
        frame.getContentPane().add(button_right);
        frame.getContentPane().add(button_turn);
        frame.getContentPane().add(button_apply);

        frame.setVisible(true);
    }
    private void resetPlayerField()
    {
        for(int i=0;i<10;++i) {
            for (int j = 0; j < 10; ++j)
            {

                player_field[i][j].setText(" ");
            }
        }

    }
    private void setFields()
    {

        for(int i=0;i<10;++i)
        {
            for(int j=0;j<10;++j)
            {
                if(field.field_player[i][j].is_shooted)
                {
                    player_field[i][j].setText(".");
                }
                if(field.field_computer[i][j].is_shooted)
                {
                    computer_field[i][j].setText(".");
                }
            }
        }
        for(Ship it:field.player_ships)
        {
            for(Ceil it2:it.ship)
            {
                if(it2.is_death)
                {
                   player_field[it2.coord.y][it2.coord.x].setText("X");
                }
                else
                {
                    player_field[it2.coord.y][it2.coord.x].setText("@");
                }
            }
        }
        for(Ship it:field.computer_ships)
        {
            for(Ceil it2:it.ship)
            {
                if(it2.is_death)
                {
                    computer_field[it2.coord.y][it2.coord.x].setText("X");
                }
                else
                {
                    computer_field[it2.coord.y][it2.coord.x].setText(" ");
                }
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Main();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }
    private boolean initiliazePlayerShipsManual(String action)
    {
        resetPlayerField();
        switch (action)
        {
            case "Up":
            {
                field.player_ships.lastElement().moveShip(Direction.UP);
                break;
            }
            case "Down":
            {
                field.player_ships.lastElement().moveShip(Direction.DOWN);
                break;
            }
            case "Left":
            {
                field.player_ships.lastElement().moveShip(Direction.LEFT);
                break;
            }
            case "Right":
            {
                field.player_ships.lastElement().moveShip(Direction.RIGHT);
                break;
            }
            case "Turn":
            {
                field.player_ships.lastElement().turnShip();
                break;
            }
            case "Apply":
            {
                return field.checkShipOverlaped(true,field.player_ships.lastElement());
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(is_game_over)
        {
            return;
        }
        JButton btn = (JButton) e.getSource();
        if(is_manual)
        {
            if(initiliazePlayerShipsManual(btn.getName()))
            {
                field.setField(true,field.player_ships.lastElement(),true);

                if(field.player_ships.size()==10)
                {
                    is_manual=false;
                    button_up.setVisible(false);
                    button_down.setVisible(false);
                    button_left.setVisible(false);
                    button_right.setVisible(false);
                    button_turn.setVisible(false);
                    button_apply.setVisible(false);
                    setFields();
                    return;
                }
                if(field.player_ships.size()==1||field.player_ships.size()==3||field.player_ships.size()==6)
                {
                    --curr_size;
                }
                try {
                    if(field.player_ships.size()!=10) {
                        field.player_ships.addElement(new Ship(curr_size, 0, 0, Direction.DOWN));
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            setFields();
            return;
        }
        if(is_initial)
        {
            if(btn.getName().equals("Random")||btn.getName().equals("Manual"))
            {
                button_r.setVisible(false);
                button_m.setVisible(false);
                try {
                    field.initiliaze(false);
                if(btn.getName().equals("Random"))
                {
                    field.initiliaze(true);
                }
                else
                {
                    button_up.setVisible(true);
                    button_down.setVisible(true);
                    button_left.setVisible(true);
                    button_right.setVisible(true);
                    button_turn.setVisible(true);
                    button_apply.setVisible(true);
                    is_manual=true;
                    try {
                        field.player_ships.addElement(new Ship(curr_size,0,0,Direction.DOWN));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                }
                catch (Exception e1) {
                e1.printStackTrace();
            }
            setFields();
            is_initial=false;
                return;
            }
            else
            {
                return;
            }
        }

        int x,y;
        String name = btn.getName();
        if(name.length()==1)
        {
            y=0;
            x=Integer.parseInt(name.substring(0,1));
        }
        else
        {
            y=Integer.parseInt(name.substring(0,1));
            x=Integer.parseInt(name.substring(1,2));
        }
        if(field.field_computer[y][x].is_shooted)
        {
            return;
        }
        System.out.println("Player shooted "+x+" "+ y);
        boolean is_shooted=false;
        for(int i=0;i<field.computer_ships.size();++i)
        {
            if(field.computer_ships.elementAt(i).shoot(new Coord(x,y)))
            {
                if(field.computer_ships.elementAt(i).isDeath())
                {
                    System.out.println("Dead");
                    field.setField(false,field.computer_ships.elementAt(i),false);
                }
                is_shooted=true;
                setFields();
                break;
            }
        }
        if(is_shooted)
        {
            if(field.checkDeadShips(false))
            {
                setFields();
                System.out.println("Congratulations! You Win!!!");
                is_game_over=true;
                return;
            }
        }
        field.field_computer[y][x].is_shooted=true;
        setFields();
        if(!is_shooted)
        {

            while(field.computerMove()) {
                setFields();
            }
            if(field.checkDeadShips(true))
            {
                setFields();
                System.out.println("You loose this game!!!");
                is_game_over=true;
                return;
            }

        }
        setFields();
    }
}
