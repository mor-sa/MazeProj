package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements IView, Observer {
    private MyViewModel myViewModel;
    @FXML
    public TextField rowInputTextField;
    @FXML
    public TextField colInputTextField;
    @FXML
    public Button GenerateBtn;
    @FXML
    public MazeDisplayer mazeDisplayer;
    @FXML
    public Label lbl_player_row;
    @FXML
    public Label lbl_player_column;
    StringProperty update_player_position_row = new SimpleStringProperty();
    StringProperty update_player_position_col = new SimpleStringProperty();
    private Maze maze;

    public void generateMaze()
    {
        int rows = Integer.valueOf(rowInputTextField.getText());
        int cols = Integer.valueOf(colInputTextField.getText());
        myViewModel.generateMaze(rows,cols);


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

    public void solveMaze()
    {
        myViewModel.solveMaze();

    }


    public void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
                drawMaze();
            }
            else {
                Maze maze = myViewModel.getMaze();

                if (maze == this.maze)//Not generateMaze
                {
                    int rowChar = mazeDisplayer.getRow_player();
                    int colChar = mazeDisplayer.getCol_player();
                    int rowFromViewModel = myViewModel.getRowChar();
                    int colFromViewModel = myViewModel.getColChar();

                    if(rowFromViewModel == rowChar && colFromViewModel == colChar)//Solve Maze
                    {
                        myViewModel.getSolution();
                        showAlert("Solving Maze ... ");
                    }
                    else//Update location
                    {
                        set_update_player_position_row(rowFromViewModel + "");
                        set_update_player_position_col(colFromViewModel + "");
                        this.mazeDisplayer.set_player_position(rowFromViewModel,colFromViewModel);
                    }


                }
                else//GenerateMaze
                {
                    this.maze = maze;
                    drawMaze();
                }
            }
        }
    }

    public void drawMaze()
    {
        mazeDisplayer.drawMaze(maze.getArrMaze());
    }




    @Override
    public void displayMaze(int[][] intArr) {

    }
}
