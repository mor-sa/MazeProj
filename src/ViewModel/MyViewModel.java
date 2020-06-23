package ViewModel;

import Model.IModel;
import View.MazeDisplayer;
import algorithms.mazeGenerators.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.io.File;
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
      //  this.maze = model.getMaze();
    }

    public Maze getMaze() {
        return this.model.getMaze();
    }


    public ArrayList<Position> getSolPath() {
        return model.getSolutionPath();
    }

    public int getRowChar() {
//        return rowCharInd;
        return this.model.getRowChar();
    }
    public int getColChar() {
//        return colCharInd;
        return this.model.getColChar();
    }

    public int getRowGoal(){
        return this.model.getMaze().getGoalPosition().getRowIndex();
    }

    public int getColGoal(){
        return this.model.getMaze().getGoalPosition().getColumnIndex();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o == this.model)
        {
            //this.colCharInd = this.model.getColChar();
            //this.rowCharInd = this.model.getRowChar();
            setChanged();
            notifyObservers();
        }
    }


    public void generateMaze(int row,int col)
    {
        this.model.generateMaze(row,col);
    }

    /**
     * This method is using the model's save method to save the file.
     * @param file
     */
    public void Save(File file){
        this.model.Save(file);
    }

    public void moveCharacter(KeyEvent keyEvent)
    {
        int direction = -1;

        switch (keyEvent.getCode()){
            case UP:
                direction = 8;
                break;
            case DOWN:
                direction = 2;
                break;
            case LEFT:
                direction = 4;
                break;
            case RIGHT:
                direction = 6;
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

    }

    public void Exit(){
        this.model.exit();
    }


}

