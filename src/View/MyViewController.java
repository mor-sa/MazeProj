package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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
    public Pane mazePane;
    public MenuBar TopMenuBar;
    public Menu btn_MenuFile;
    public Menu btn_optionsMenu;
    public Menu btn_helpMenu;
    public Menu btn_aboutMenu;
    public Menu btn_exitMenu;
    public ToggleButton SoundToggle;

    public MediaPlayer mediaPlayer;
    public Media song;
    private MediaPlayer mediaPlayerVideo;
    private MediaView mediaView;
    private Media victoryVideo;

    StringProperty imageFileNameSoundOn = new SimpleStringProperty();
    StringProperty imageFileNameSoundOff = new SimpleStringProperty();

    public String getImageFileNameSoundOn(){ return imageFileNameSoundOn.get(); }
    public String getImageFileNameSoundOff(){ return imageFileNameSoundOff.get(); }

    public void setImageFileNameSoundOn(String imageFileNameSoundOn){ this.imageFileNameSoundOn.set(imageFileNameSoundOn); }
    public void setImageFileNameSoundOff(String imageFileNameSoundOff){ this.imageFileNameSoundOff.set(imageFileNameSoundOff); }


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
                if(!SoundToggle.isSelected()) {
                    if (SolveBtn.isDisable()) {
                        mediaPlayer.stop();
                        Media song = new Media(getClass().getClassLoader().getResource("Audio/The Pink Panther Theme.mp3").toExternalForm());
                        mediaPlayer = new MediaPlayer(song);
                        mediaPlayer.setVolume(1.0);
                        mediaPlayer.play();
                    }
                }
                SolveBtn.setDisable(false);
                SaveBtn.setDisable(false);
                NewBtn.setDisable(false);
            }
        }
        mazeDisplayer.requestFocus();
    }

    public void setViewModel(MyViewModel viewModel) { this.myViewModel = viewModel; }


    public void drawMaze()
    {
        try {
        mazeDisplayer.drawMaze(maze.getArrMaze(),rowCharInd,colCharInd, maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex());
        }
        catch (FileNotFoundException e){
            System.out.println(e.getStackTrace());
            showAlert(e.getMessage(), "Error");
        }
    }

    public void solveMaze(){
        myViewModel.solveMaze(rowCharInd, colCharInd);
        mazeDisplayer.requestFocus();
    }

    public void drawSolution(ArrayList<Position> path){
        try {
            mazeDisplayer.drawSolution(rowCharInd, colCharInd, path);
        }
        catch (FileNotFoundException e){
            System.out.println(e.getStackTrace());
            showAlert(e.getMessage(), "Error");
        }
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
            if (mediaPlayerVideo != null){
                mazePane.getChildren().remove(mediaView);
                mediaPlayerVideo = null;
            }
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
                        if (!SoundToggle.isSelected()) {
                            mediaPlayer.stop();
                            Media song = new Media(getClass().getClassLoader().getResource("Audio/TADASound.mp3").toExternalForm());
                            mediaPlayer = new MediaPlayer(song);
                            mediaPlayer.setVolume(1.0);
                            mediaPlayer.play();
                        }
                        SolveBtn.setDisable(true);
                        SaveBtn.setDisable(true);

                        VictoryVideo();
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

    public void VictoryVideo(){
        victoryVideo = new Media(getClass().getClassLoader().getResource("Videos/newVictoryVideo.mp4").toExternalForm());
        if (victoryVideo != null) {
            mediaPlayerVideo = new MediaPlayer(victoryVideo);
            mediaView = new MediaView();
            mediaView.setMediaPlayer(mediaPlayerVideo);
            mediaView.fitWidthProperty().bind(mazePane.widthProperty());
            mediaView.fitHeightProperty().bind(mazePane.heightProperty());
            mediaView.setPreserveRatio(false);
            mazePane.getChildren().add(mediaView);
            mediaPlayerVideo.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayerVideo.setAutoPlay(true);
        }
    }

    /**
     * This will be called when the user clicks on "Load menuitem in "File" menu.
     * This will open a file chooser, the user will choose a maze from disk and start the game.
     */
    public void loadMaze(){
        this.myViewModel.clearSolPath();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[] { new FileChooser.ExtensionFilter("*.maze", new String[] { "*.maze" }) });
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(this.mazeDisplayer.getScene().getWindow());
        if (file != null) {
            this.myViewModel.LoadMaze(file);
            if (SolveBtn.isDisable()){
                SolveBtn.setDisable(false);
            }
            mazeDisplayer.requestFocus();
        }
        if(!SoundToggle.isSelected()) {
            if (SolveBtn.isDisable()) {
                mediaPlayer.stop();
                Media song = new Media(getClass().getClassLoader().getResource("Audio/The Pink Panther Theme.mp3").toExternalForm());
                mediaPlayer = new MediaPlayer(song);
                mediaPlayer.setVolume(1.0);
                mediaPlayer.play();
            }
        }
    }

    public void Zoom(ScrollEvent scroll) {
        if(scroll.isControlDown() || scroll.isShiftDown()){
            if(scroll.getDeltaY()>0 || scroll.getDeltaX()>0){
                mazeDisplayer.setZoom(mazeDisplayer.getZoom()*1.1);
            }
            if(scroll.getDeltaY()<0 || scroll.getDeltaX()<0){
                mazeDisplayer.setZoom(mazeDisplayer.getZoom()/1.1);
            }
            scroll.consume();
            try {
                chooseDraw();
            }
            catch (FileNotFoundException e){
                System.out.println(e.getStackTrace());
                showAlert(e.getMessage(), "Error");
            }
        }
    }



    public void setResizeEvent(Scene scene) {
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                try {
                    chooseDraw();
                }
                catch (FileNotFoundException e){
                    System.out.println(e.getStackTrace());
                    showAlert(e.getMessage(), "Error");
                }
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                    try {
                        chooseDraw();
                    }
                    catch (FileNotFoundException e){
                        System.out.println(e.getStackTrace());
                        showAlert(e.getMessage(), "Error");
                    }
                }
        });
    }

    /**
     * Chooses what to draw on screen: Empty screen, maze, maze with solution or victory draw
     * @throws FileNotFoundException
     */
    public void chooseDraw() throws FileNotFoundException{
        try{
            if (maze == null){
                mazeDisplayer.drawEmpty();
            }
            else if (SolveBtn.isDisable()){
//                    mazeDisplayer.drawVictory();
                VictoryVideo();
                mediaPlayerVideo = null;
            }
            else if (myViewModel.getSolPath().size() != 0){
                mazeDisplayer.drawSolution(rowCharInd, colCharInd, myViewModel.getSolPath());
            }
            else{
                mazeDisplayer.draw();
            }
        }
        catch(FileNotFoundException e){
                System.out.println(e.getStackTrace());
                showAlert(e.getMessage(),"Error");
        }
    }

    /**
     * This will be called when user clicks on "Save" menuitem in "File" menu.
     * This will save the maze on the users computer.
     **/
    public void saveMaze(){
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
                showAlert("Something went wrong on saving maze process", "Error");
                return;
            }
            showAlert("File saved: " + savedFile.toString(), "Information");
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

    public void setMediaPlayer(MediaPlayer mediaPlayer) { this.mediaPlayer = mediaPlayer; }

    public void setSong(Media song) { this.song = song; }

    /**
     * This will be called when the user clicks on the sound toggle button.
     * If the sound toggle is selected will stop the music, and if it's not selected it will play the music.
     */
    public void handleMusic(){
        if(SoundToggle.isSelected()){
            this.mediaPlayer.pause();

        }
        else{
            this.mediaPlayer.play();

        }
        if (this.maze == null){
            rowInputTextField.requestFocus();
        }
        else {
            mazeDisplayer.requestFocus();
        }
    }

    /**
     * Checks if the key pressed while in inputRow is Enter. If so, generates maze.
     * @param keyEvent
     */
    public void inputKeyPressed(KeyEvent keyEvent){
        if (keyEvent.getCode().equals(KeyCode.ENTER))
        {
            generateMaze();
        }
    }

    /**
     * When user clicks on the mediaView (video) the video plays
     */
    public void mediaVideoMouseClicked(){
        mediaPlayerVideo.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayerVideo.play();
    }
}
