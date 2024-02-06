package com.example.codecity;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Buildings{
    private ArrayList<Rectangle> buildings;

    public Buildings(){
        buildings = new ArrayList<>();
    }

    public void makeSmallBuilding(int sizex, int sizey, int posx, int posy){
        // have height as an option to add later
        // possibly color
        // Add to a list of buildings or objects in area.
        Rectangle rec = new Rectangle(sizex, sizey);
        rec.setX(posx);
        rec.setY(posy);
        buildings.add(rec);

    }

    public void makeLargeBuilding(int sizex, int sizey, int posx, int posy){
        Rectangle rec = new Rectangle(sizex, sizey);
        rec.setX(posx);
        rec.setY(posy);
        buildings.add(rec);
    }

    public ArrayList<Rectangle> getBuilding(){
        return buildings;
    }
}
