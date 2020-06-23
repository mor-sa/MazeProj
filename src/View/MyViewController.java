package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class MyViewController implements IView, Observer {

    @FXML
    public TextField rowInputTextField;
    public TextField colInputTextField;
    public Button GenerateBtn;
    public Button SolveBtn;
    public MenuItem NewBtn;
    public MenuItem SaveBtn;

    private MyViewModel myViewModel;
    public MazeDisplayer mazeDisplayer;
    StringProperty update_player_position_row = new SimpleStringProperty();
    StringProperty update_player_position_col = new SimpleStringProperty();
    private Maze maze;
    private int rowCharInd;
    private int colCharInd;

    public void generateMaze()
    {
        SolveBtn.setDisable(false);
        SaveBtn.setDisable(false);
        NewBtn.setDisable(false);
        String rowInput = rowInputTextField.getText();
        String colInput = colInputTextField.getText();
        if (!rowInput.matches("\\d*") || !colInput.matches("\\d*") || rowInput.equals("") || colInput.equals("")){
            showAlert("Please insert numbers between 2 and 1000", "Warning");
        }
        else {
            int rows = Integer.parseInt(rowInput);
            int cols = Integer.parseInt(colInput);
            if ((rows < 2 || cols < 2 || rows > 1000 || cols > 1000)){
                showAlert("Please insert numbers between 2 and 1000", "Warning");
            }
            else {
                myViewModel.generateMaze(rows, cols);
            }
        }
    }

    public void drawMaze()
    {
        mazeDisplayer.drawMaze(maze.getArrMaze(),rowCharInd,colCharInd, maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex());
    }

    public void solveMaze(){
        myViewModel.solveMaze(rowCharInd, colCharInd);
    }

    public void drawSolution(ArrayList<Position> path){
        mazeDisplayer.drawSolution(rowCharInd, colCharInd, path);
    }

    public void setViewModel(MyViewModel viewModel) {
        this.myViewModel = viewModel;
    }

    public void showAlert(String message, String title)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);;
        alert.show();
    }

    public void keyPressed(KeyEvent keyEvent) {
        myViewModel.moveCharacter(keyEvent);
        keyEvent.consume();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }


    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof MyViewModel)
        {
            if(maze == null)//generateMaze
            {
                this.maze = myViewModel.getMaze();
                this.rowCharInd = myViewModel.getRowChar();
                this.colCharInd = myViewModel.getColChar();
                drawMaze();
            }
            else
            {
                Maze maze = myViewModel.getMaze();
                if (maze == this.maze)//Not generateMaze
                {
                    //Update location
                    this.rowCharInd = myViewModel.getRowChar();
                    this.colCharInd = myViewModel.getColChar();
                    //Check if solved
                    if (rowCharInd == maze.getGoalPosition().getRowIndex() && colCharInd == maze.getGoalPosition().getColumnIndex())//Solve Maze
                    {
                        showAlert("You found the diamond!", "Congratulations!");
                    }
                    //Check if user asked for solution
                    else if (myViewModel.getSolPath().size() != 0)
                    {
                        drawSolution(myViewModel.getSolPath());
                    }
                    else
                    {
                        drawMaze();
                    }
                }
                else//GenerateMaze
                {
                    this.maze = maze;
                    this.rowCharInd = myViewModel.getRowChar();
                    this.colCharInd = myViewModel.getColChar();
                    drawMaze();
                }
            }
        }
    }

    public void setResizeEvent(Scene scene) {
        long width = 0;
        long height = 0;
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                if (myViewModel.getSolPath().size() != 0)
                {
                    MyViewController.this.drawSolution(myViewModel.getSolPath());
                }
                else{
                    MyViewController.this.drawMaze();
                }
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                if (myViewModel.getSolPath().size() != 0)
                {
                    MyViewController.this.drawSolution(myViewModel.getSolPath());
                }
                else{
                    MyViewController.this.drawMaze();
                }
            }
        });
    }

    public void saveMaze(){
        // No Maze was generated
        if(this.myViewModel.getMaze() == null){
            showAlert("Please generate a maze first", "Error");
        }
        else{
            FileChooser filechooser = new FileChooser();
            filechooser.setInitialDirectory(new File(System.getProperty("user.home")));
            filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[] { new FileChooser.ExtensionFilter("*.maze", new String[] { "*.maze" }) });
            filechooser.setTitle("Saving the maze");
            File savedFile = filechooser.showSaveDialog(this.mazeDisplayer.getScene().getWindow());
            if (savedFile != null) {
                try {
                    this.myViewModel.Save(savedFile);
                }
                catch(Exception e) {
                    e.printStackTrace();
                    showAlert("Something went wrong", "Error");
                    return;
                }
                showAlert("File saved: " + savedFile.toString(), "Information");
            }
        }
    }

    public void Exit(){
        this.myViewModel.Exit();
        Platform.exit();
    }

    public void Help(){
        String message = "Maze game: the Panther wants to find the diamond\n" +
                "Player is panter, goal is the diamond\n" +
                "You can move vertically, horizontally and diagonally as long as there is no wall blocking you\n" +
                "Diagonal movement is allowed only if you can get to the same position by two movements, horizontal and vertical";
        String title = "About";
        showAlert(message, title);
    }

    public void About(){
        String message = "Algorithm used to create the maze is Prim's algorithn\n" +
                "Solutions can be searched with 3 algorithms: BFS, DFS and BFS with priority queue\n" +
                "Brought to you by Ronen Aranovich and Mor Saranga";
        String title = "About";
        showAlert(message, title);
    }

    @Override
    public void displayMaze(int[][] arrMaze, int rowChar, int colChar) {
        //mazeDisplayer.drawMaze(maze.getArrMaze());
    }
}
