package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.*;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel model;
    private Maze maze;
    private int rowCharInd;
    private int colCharInd;


    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this);
        this.maze = null;
    }

    public Maze getMaze() {
        return this.model.getMaze();
    }
    public int getRowChar() {
        return rowCharInd;
    }
    public int getColChar() {
        return colCharInd;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o == this.model)
        {
            this.colCharInd = this.model.getColChar();
            this.rowCharInd = this.model.getRowChar();
            setChanged();
            notifyObservers();
        }
    }


    public void generateMaze(int row,int col)
    {
        this.model.generateMaze(row,col);
    }

    public void moveCharacter(KeyEvent keyEvent)
    {
        int direction = -1;

        switch (keyEvent.getCode()){
            case UP:
                direction = 1;
                break;
            case DOWN:
                direction = 2;
                break;
            case LEFT:
                direction = 3;
                break;
            case RIGHT:
                direction = 4;
                break;
        }

        model.updateCharacterLocation(direction);
    }

    public void solveMaze()
    {
        model.solveMaze();
    }

    public void getSolution()
    {
        model.getSolution();
    }
}

