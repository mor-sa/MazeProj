package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import com.sun.tools.javac.util.StringUtils;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.util.Observable;
import java.util.Observer;

public class MyViewController implements IView, Observer {
    private MyViewModel myViewModel;
    @FXML
    public TextField rowInputTextField;
    @FXML
    public TextField colInputTextField;
    public Button GenerateBtn;
    public Button SolveBtn;
    public MazeDisplayer mazeDisplayer;
    public Label lbl_player_row;
    public Label lbl_player_column;
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
    private boolean showSolution = false;

    public void generateMaze()
    {
        String rowInput = rowInputTextField.getText();
        String colInput = colInputTextField.getText();
        if (!rowInput.matches("\\d*") || !colInput.matches("\\d*") || colInput.trim().isEmpty() || rowInput.trim().isEmpty()){
            showAlert("Please insert numbers between 2 and 1000");
        }
        else {
            int rows = Integer.valueOf(rowInput);
            int cols = Integer.valueOf(colInput);
            if ((rows < 2 || cols < 2 || rows > 1000 || cols > 1000)){
                showAlert("Please insert numbers between 2 and 1000");
            }
            else {
                myViewModel.generateMaze(rows, cols);
            }
        }
        mazeDisplayer.requestFocus();
    }

    public void setViewModel(MyViewModel viewModel) {
        this.myViewModel = viewModel;
    }

    public String get_update_player_position_row() {
        return update_player_position_row.get();
    }

    public void set_update_player_position_row(String update_player_position_row) {
        this.update_player_position_row.set(update_player_position_row);
    }

    public String get_update_player_position_col() {
        return update_player_position_col.get();
    }

    public void set_update_player_position_col(String update_player_position_col) {
        this.update_player_position_col.set(update_player_position_col);
    }

    public void solveMaze(){
        myViewModel.solveMaze();
    }


    public void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Information");
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
                        myViewModel.getSolution();
                        showAlert("Good Job!");
                    }
                    //Check if user asked for solution
                    else if (myViewModel.getSolPath().size() != 0)
                    {
                        drawSolution(myViewModel.getSolPath());
                    }
                    else
                    {
//                        set_update_player_position_row(rowFromViewModel + "");
//                        set_update_player_position_col(colFromViewModel + "");
//                        this.mazeDisplayer.set_player_position(rowFromViewModel,colFromViewModel);
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

    public void drawMaze()
    {
        mazeDisplayer.drawMaze(maze.getArrMaze(),rowCharInd,colCharInd, maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex());
    }

    public void drawSolution(ArrayList<Position> path){
        mazeDisplayer.drawSolution(rowCharInd, colCharInd, path);
    }


    public void exitGame(){
        this.myViewModel.Exit();
        Platform.exit();
    }

    public void NewGame(){

    }

    /**
     * This will be called when user clicks on "Save" menuitem in "File" menu.
     * This will save the maze on the users computer.
     **/
    public void saveMaze(){
        // No Maze was generated
        if(this.myViewModel.getMaze() == null){
            showAlert("Please generate a maze first");
        }
        else{
            FileChooser filechooser = new FileChooser();
            filechooser.setInitialDirectory(new File(System.getProperty("user.home")));
            filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[] { new FileChooser.ExtensionFilter("*.maze", new String[] { "*.maze" }) });
            filechooser.setTitle("Saving the maze");
            File file = filechooser.showSaveDialog(this.mazeDisplayer.getScene().getWindow());
            if (file != null)
                this.myViewModel.Save(file);
            else{
                showAlert("Something went wrong");
            }
        }
    }

    public void loadMaze(){
       // FileInputStream in = new FileInputStream();
        FileChooser fileChooser = new FileChooser();

    }

    @Override
    public void displayMaze(int[][] arrMaze, int rowChar, int colChar) {
        //mazeDisplayer.drawMaze(maze.getArrMaze());
    }
}
