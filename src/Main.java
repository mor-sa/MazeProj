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

import Model.IModel;
import Model.MyModel;
import View.MazeDisplayer;
import View.MyViewController;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Observer;

public class Main extends Application {

    //**********
    int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();

//    Stage stage;
//    Scene scene;

    int initialX;
    int initialY;

    //***********

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View/MyView.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Pink Panther Maze");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();

        //**************************************
        // root
//        BorderPane root = new BorderPane();
//        root.setStyle("-fx-background-color:rgb(186,153,122,0.7); -fx-background-radius:30;");

        // Responsive Design
//        int sceneWidth = 0;
//        int sceneHeight = 0;
//        if (screenWidth <= 800 && screenHeight <= 600) {
//            sceneWidth = 600;
//            sceneHeight = 350;
//        } else if (screenWidth <= 1280 && screenHeight <= 768) {
//            sceneWidth = 800;
//            sceneHeight = 450;
//        } else if (screenWidth <= 1920 && screenHeight <= 1080) {
//            sceneWidth = 1000;
//            sceneHeight = 650;
//        }

        // Scene
//        stage = new Stage();
//        stage.initStyle(StageStyle.TRANSPARENT);
//        scene = new Scene(root, sceneWidth, sceneHeight, Color.TRANSPARENT);
//        Scene scene = new Scene(root, 500, 500, Color.TRANSPARENT);
//
//        // Moving
//        scene.setOnMousePressed(m -> {
//            if (m.getButton() == MouseButton.PRIMARY) {
//                scene.setCursor(Cursor.MOVE);
//                initialX = (int) (primaryStage.getX() - m.getScreenX());
//                initialY = (int) (primaryStage.getY() - m.getScreenY());
//            }
//        });
//
//        scene.setOnMouseDragged(m -> {
//            if (m.getButton() == MouseButton.PRIMARY) {
//                primaryStage.setX(m.getScreenX() + initialX);
//                primaryStage.setY(m.getScreenY() + initialY);
//            }
//        });
//
//        scene.setOnMouseReleased(m -> {
//            scene.setCursor(Cursor.DEFAULT);
//        });
//
//        primaryStage.setTitle("Pink Panther Maze");
//        primaryStage.setScene(scene);
//        primaryStage.show();
        //*****************************************************

        MyModel model = new MyModel();
        model.startServers();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);
        MyViewController viewController = fxmlLoader.getController();
        viewController.setViewModel(viewModel);
        viewModel.addObserver(viewController);
    }


    public static void main(String[] args) {
        launch(args);
    }
}




