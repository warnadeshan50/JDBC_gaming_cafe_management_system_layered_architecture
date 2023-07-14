package lk.ijse.gaming_arena_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Launcher extends Application {
    double x,y=0;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        parent.setOnMousePressed(event -> {
            x= event.getSceneX();
            y= event.getSceneY();
        });
        parent.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX()-x);
            primaryStage.setY(event.getScreenY()-y);
        });
        primaryStage.setTitle("Login Form");
        primaryStage.centerOnScreen();

        primaryStage.show();
    }
}
