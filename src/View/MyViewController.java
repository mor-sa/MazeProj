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
import javafx.scene.control.*;

import java.io.*;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public MenuBar TopMenuBar;
    public Menu btn_MenuFile;
    public Menu btn_optionsMenu;
    public Menu btn_helpMenu;
    public Menu btn_aboutMenu;
    public Menu btn_exitMenu;

    StringProperty update_player_position_row = new SimpleStringProperty();
    StringProperty update_player_position_col = new SimpleStringProperty();
    private Maze maze;
    private int rowCharInd;
    private int colCharInd;

    /**
     * This will generate a maze depending on the user's inputs
     */
    public void generateMaze()
    {
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
                SolveBtn.setDisable(false);

                /// DOESNT WORK FOR NOW!!!!!!! is enabled from the start.
                SaveBtn.setDisable(false);
                NewBtn.setDisable(false);
            }
        }
        mazeDisplayer.requestFocus();
    }

    public void setViewModel(MyViewModel viewModel) { this.myViewModel = viewModel; }

    public String get_update_player_position_row() { return update_player_position_row.get(); }
    public String get_update_player_position_col() { return update_player_position_col.get(); }

    public void set_update_player_position_row(String update_player_position_row) {
        this.update_player_position_row.set(update_player_position_row);
    }

    public void set_update_player_position_col(String update_player_position_col) {
        this.update_player_position_col.set(update_player_position_col);
    }

    public void drawMaze()
    {
        mazeDisplayer.drawMaze(maze.getArrMaze(),rowCharInd,colCharInd, maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex());
    }

    public void solveMaze(){
        myViewModel.solveMaze(rowCharInd, colCharInd);
        mazeDisplayer.requestFocus();
    }

    public void drawSolution(ArrayList<Position> path){
        mazeDisplayer.drawSolution(rowCharInd, colCharInd, path);
    }

    /**
     * This method will define the alerts on this program
     * @param message - the alert's message content
     * @param title - the alert's title
     */
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
                        //myViewModel.getSolution();
                        mazeDisplayer.drawVictory();
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
        mazeDisplayer.requestFocus();
    }

    /**
     * This will be called when the user clicks on "Load menuitem in "File" menu.
     * This will open a file chooser, the user will choose a maze from disk and start the game.
     */
    public void loadMaze(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[] { new FileChooser.ExtensionFilter("*.maze", new String[] { "*.maze" }) });
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(this.mazeDisplayer.getScene().getWindow());
        if (file != null) {
            this.myViewModel.LoadMaze(file);

            mazeDisplayer.requestFocus();
        }
        else{
            showAlert("Something went wrong","Load error");
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

    /**
     * This will be called when user clicks on "Save" menuitem in "File" menu.
     * This will save the maze on the users computer.
     **/
    public void saveMaze(){
        // No Maze was generated
        if(this.myViewModel.getMaze() == null){
            showAlert("Error","Please generate a maze first");
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

    /**
     * This will be called when the user clicks on "Exit" in the "Exit" menu.
     * This will close the program properly.
     */
    public void Exit(){
        this.myViewModel.Exit();
        Platform.exit();
    }

    /**
     * This will be called when the user clicks on "About us" in the "About" menu.
     * This will show an alert that explain how to play
     */
    public void Help(){
        String message = "Maze game: the Panther wants to find the diamond\n" +
                "Player is panter, goal is the diamond\n" +
                "You can move vertically, horizontally and diagonally as long as there is no wall blocking you\n" +
                "Diagonal movement is allowed only if you can get to the same position by two movements, horizontal and vertical";
        String title = "Help - how to play";
        showAlert(message, title);
    }

    /**
     * This will be called when the user clicks on "About us" in the "About" menu.
     * This will show an alert that says about us, the developers
     */
    public void About(){
        String message = "Algorithm used to create the maze is Prim's algorithn\n" +
                "Solutions can be searched with 3 algorithms: BFS, DFS and BFS with priority queue\n" +
                "Brought to you by Ronen Aranovich and Mor Saranga";
        String title = "About";
        showAlert(message, title);
    }

    /**
     * This will be called when the user clicks on "Properties" in the "Options" menu.
     * This will show an alert that says the maze's properties
     */
    public void showProperties(){
        String mazeGenAlgText = "The Maze generating algorithm is: "+ this.myViewModel.getModelMazeGenAlg();
        String mazeSolveAlgText = "The Maze solving algorithm is: " + this.myViewModel.getModelMazeSolveAlg();
        String propContent = mazeGenAlgText + "\n" + mazeSolveAlgText + "\n";
        showAlert(propContent,"Maze Properties");
    }

    @Override
    public void displayMaze(int[][] arrMaze, int rowChar, int colChar) {
        //mazeDisplayer.drawMaze(maze.getArrMaze());
    }
}
