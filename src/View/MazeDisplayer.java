package View;

import algorithms.mazeGenerators.Position;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MazeDisplayer extends Canvas {

    private int [][] maze;
    private int row_player = 0;
    private int col_player = 0;
    private int row_goal = 0;
    private int col_goal = 0;

    private Image wallImage;
    private Image passageImage;
    private Image playerImage;
    private Image goalImage;
    private Image stepImage;
    private Image victoryImage;

    private double zoom = 1;

    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNamePassage = new SimpleStringProperty();
    StringProperty imageFileNameGoal = new SimpleStringProperty();
    StringProperty imageFileNameStep = new SimpleStringProperty();
    StringProperty imageFileNameVictory = new SimpleStringProperty();

    public String getImageFileNameWall() { return imageFileNameWall.get(); }
    public String getImageFileNamePassage(){ return imageFileNamePassage.get(); }
    public String getImageFileNamePlayer() { return imageFileNamePlayer.get(); }
    public String getImageFileNameGoal() { return imageFileNameGoal.get(); }
    public String getImageFileNameStep() { return imageFileNameStep.get(); }
    public String getImageFileNameVictory(){ return imageFileNameVictory.get(); }

    public void setImageFileNameWall(String imageFileNameWall) { this.imageFileNameWall.set(imageFileNameWall); }
    public void setImageFileNamePassage(String imageFileNamePassage){ this.imageFileNamePassage.set(imageFileNamePassage);}
    public void setImageFileNamePlayer(String imageFileNamePlayer) {this.imageFileNamePlayer.set(imageFileNamePlayer); }
    public void setImageFileNameGoal(String imageFileNameGoal) { this.imageFileNameGoal.set(imageFileNameGoal); }
    public void setImageFileNameStep(String imageFileNameStep) { this.imageFileNameStep.set(imageFileNameStep); }
    public void setImageFileNameVictory(String imageFileNameVictory){ this.imageFileNameVictory.set(imageFileNameVictory); }

    public int getRow_player() { return row_player; }
    public int getCol_player() { return col_player; }
    public int getRow_goal() { return row_goal; }
    public int getCol_goal() { return col_goal; }

    public void set_player_position(int row, int col){
        this.row_player = row;
        this.col_player = col;
    }

    public void set_goal_position(int row, int col){
        this.row_goal = row;
        this.col_goal= col;
    }

    public void drawMaze(int [][] maze, int rowCharInd, int colCharInd, int rowGoalInd, int colGoalInd) throws FileNotFoundException
    {
        set_player_position(rowCharInd, colCharInd);
        set_goal_position(rowGoalInd, colGoalInd);
        this.maze = maze;
        try {
            draw();
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

    public void draw() throws FileNotFoundException
    {
        if( maze!=null)
        {
            int row = maze.length;
            int col = maze[0].length;
            double cellHeight = getHeight()/row * zoom;
            double cellWidth = getWidth()/col * zoom;
            GraphicsContext graphicsContext = getGraphicsContext2D();
            graphicsContext.clearRect(0,0,getWidth(),getHeight());
            graphicsContext.setFill(Color.LIGHTPINK);
            double w,h;
            //Draw Maze
            try { goalImage = new Image(new FileInputStream(getImageFileNameGoal())); }
            catch (FileNotFoundException e) {
                throw new FileNotFoundException("Missing a Goal image file that is needed to generate a maze.");
            }
            try { playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));}
            catch (FileNotFoundException e) {
                throw new FileNotFoundException("Missing a Player image file that is needed to generate a maze.");
            }
            try{ wallImage = new Image(new FileInputStream(getImageFileNameWall()));}
            catch (FileNotFoundException e) {
                throw new FileNotFoundException("Missing a Wall image file that is needed to generate a maze.");
            }
            try{ passageImage = new Image(new FileInputStream(getImageFileNamePassage())); } catch (FileNotFoundException e) {
                throw new FileNotFoundException("Missing a Passage image file that is needed to generate a maze.");
            }
            graphicsContext.setStroke(Color.DARKGREY);
            graphicsContext.stroke();
            for(int i=0;i<row;i++)
            {
                for(int j=0;j<col;j++)
                {
                    if(maze[i][j] == 1) // Wall
                    {
                        h = i * cellHeight;
                        w = j * cellWidth;
                        graphicsContext.drawImage(wallImage,w,h,cellWidth,cellHeight);
                    }
                    else{
                        h = i * cellHeight;
                        w = j * cellWidth;
                        graphicsContext.drawImage(passageImage, w, h, cellWidth, cellHeight);
                    }
                }
            }
            //Draw Player
            double h_player = getRow_player() * cellHeight;
            double w_player = getCol_player() * cellWidth;
            graphicsContext.drawImage(playerImage,w_player,h_player,cellWidth,cellHeight);
            //Draw Target
            double h_goal = getRow_goal() * cellHeight;
            double w_goal = getCol_goal() * cellWidth;
            graphicsContext.drawImage(goalImage,w_goal,h_goal,cellWidth,cellHeight);
        }
    }

    public void drawSolution(int rowCharInd, int colCharInd, ArrayList<Position> path) throws FileNotFoundException{
        try {
            stepImage = new Image(new FileInputStream(getImageFileNameStep()));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Missing a Step image file that is needed to show a soltion.");
        }
        int row = maze.length;
        int col = maze[0].length;
        double cellHeight = getHeight()/row;
        double cellWidth = getWidth()/col;
        GraphicsContext graphicsContext = getGraphicsContext2D();
        graphicsContext.clearRect(0,0,getWidth(),getHeight());
        set_player_position(rowCharInd, colCharInd);
        try {
            draw();
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        }
        for (int i = 1; i < path.size() - 1; i++){
            if (path.get(i).getColumnIndex() == colCharInd && path.get(i).getRowIndex() == rowCharInd){
                graphicsContext.drawImage(playerImage, path.get(i).getColumnIndex() * cellWidth, path.get(i).getRowIndex() * cellHeight, getWidth()/maze[0].length, getHeight()/maze.length);
            }
            else{
                graphicsContext.drawImage(stepImage, path.get(i).getColumnIndex() * cellWidth, path.get(i).getRowIndex() * cellHeight, getWidth()/maze[0].length, getHeight()/maze.length);
            }
        }
    }

    public void drawVictory() throws FileNotFoundException{
        GraphicsContext graphicsContext = getGraphicsContext2D();
        graphicsContext.clearRect(0,0,getWidth(),getHeight());
        graphicsContext.setFill(Color.LIGHTPINK);
        try {
            victoryImage = new Image(new FileInputStream(getImageFileNameVictory()));
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Missing a Victory image file that is needed to show victory.");
        }
        graphicsContext.drawImage(victoryImage,0,0,getWidth(),getHeight());
    }

    public void drawEmpty(){
        GraphicsContext graphicsContext = getGraphicsContext2D();
        graphicsContext.clearRect(0,0,getWidth(),getHeight());
        graphicsContext.setFill(Color.web("f6d3da"));
        graphicsContext.fillRect(0,0,getWidth(),getHeight());
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public double getZoom() {
        return zoom;
    }
}
