package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import javafx.scene.input.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        return this.model.getRowChar();
    }

    public int getColChar() {
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

    public void solveMaze(int rowCharInd, int colCharInd)
    {
        model.solveMaze(rowCharInd, colCharInd);
    }

    public void LoadMaze(File file){
        try{
            this.model.Load(file);
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void Exit(){
        this.model.exit();
    }

    public String getModelMazeGenAlg(){
        return this.model.getMazeGenAlg();
    }

    public String getModelMazeSolveAlg() {
        return this.model.getMazeSolveAlg();
    }

    public String getModelNumOfThreads() {
        return this.model.getNumOfThreads();
    }
}

