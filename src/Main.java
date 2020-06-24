//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//public class Main extends Application {
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("../View/MyView.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//    }
//
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}

import Model.MyModel;
import View.MyViewController;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;


public class Main extends Application {

    //**********
    int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

    int initialX;
    int initialY;

    //***********

    @Override
    public void start(Stage primaryStage) throws Exception{

        MyModel model = new MyModel();
        model.startServers();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);

        primaryStage.setTitle("Pink Panther Maze");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View/MyView.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 800, 600);

        // Moving
        scene.setOnMousePressed(m -> {
            if (m.getButton() == MouseButton.PRIMARY) {
                scene.setCursor(Cursor.MOVE);
                initialX = (int) (primaryStage.getX() - m.getScreenX());
                initialY = (int) (primaryStage.getY() - m.getScreenY());
            }
        });

        scene.setOnMouseDragged(m -> {
            if (m.getButton() == MouseButton.PRIMARY) {
                primaryStage.setX(m.getScreenX() + initialX);
                primaryStage.setY(m.getScreenY() + initialY);
            }
        });

        scene.setOnMouseReleased(m -> {
            scene.setCursor(Cursor.DEFAULT);
        });
        primaryStage.setScene(scene);

        MyViewController viewController = fxmlLoader.getController();

        javafx.scene.media.Media song=new Media(getClass().getClassLoader().getResource("Audio/The Pink Panther Theme.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(song);
        mediaPlayer.setVolume(1.0);
        viewController.setMediaPlayer(mediaPlayer);
        viewController.setSong(song);
        viewController.mediaPlayer.play();

        viewController.setResizeEvent(scene);
        viewController.setViewModel(viewModel);
        viewModel.addObserver(viewController);
        SetStageCloseEvent(primaryStage, model);
        primaryStage.show();
    }

    private void SetStageCloseEvent(Stage primaryStage, MyModel model) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit?");
                alert.setHeaderText(null);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    // Close program
                    model.stopServers();
                    primaryStage.close();
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}