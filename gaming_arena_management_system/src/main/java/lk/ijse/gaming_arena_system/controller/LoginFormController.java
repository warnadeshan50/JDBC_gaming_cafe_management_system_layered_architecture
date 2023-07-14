package lk.ijse.gaming_arena_system.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    @FXML
    private Button login_btn;

    @FXML
    private Button exit_btn;

    @FXML
    private PasswordField password_txt;

    @FXML
    private TextField username_txt;

    @FXML
    private FontAwesomeIconView close_btn;

    @FXML
    private Label valid_lbl;
    static String user,password;
    double x,y=0;
    public void login_btnOnAction(ActionEvent actionEvent) throws IOException {
        checkPassowrd();
    }

    public void login_btnOnMouseExited(MouseEvent mouseEvent) {
        login_btn.setStyle("-fx-background-color: #bdc3c7 ;-fx-background-radius: 5");
    }

    public void login_btnOnMouseMoved(MouseEvent mouseEvent) {
        login_btn.setStyle("-fx-border-color:blue ;-fx-border-radius: 5;-fx-background-radius: 5");
    }

    public void exit_btnOnAction(ActionEvent actionEvent) {
        exitPrograme();
    }

    public void password_txtOnAction(ActionEvent actionEvent) {

    }

    public void loginWhenEnterOnKeyPressed(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
            checkPassowrd();
        }
    }

    public void usename_txtOnAction(ActionEvent actionEvent) {
    }

    public void nextTxtFieldOnKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
            password_txt.requestFocus();
        }
    }

    private void exitPrograme(){
        Alert alert=new Alert(Alert.AlertType.WARNING,"Do you want to exit ?",
                ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.YES)
            javafx.application.Platform.exit();
    }
    private void checkPassowrd() throws IOException {
        user = username_txt.getText();
        password = password_txt.getText();

        if(user.equals("Admin") & password.equals("1234")){
            Scene stage = new Scene(FXMLLoader.load(getClass().getResource("/view/Cafe_form.fxml")));
           // stage.getStylesheets().add(getClass().getResource("/css/main_css.css").toExternalForm());
            Stage window = (Stage) login_btn.getScene().getWindow();


            stage.setOnMousePressed(event -> {
                x= event.getSceneX();
                y= event.getSceneY();
            });
            stage.setOnMouseDragged(event -> {
                window.setX(event.getScreenX()-x);
                window.setY(event.getScreenY()-y);
            });
            window.setTitle("Gaming Arena Management System");
            window.setScene(stage);
            window.centerOnScreen();
        }else{
            new Alert(Alert.AlertType.ERROR,"Check your username & password").show();
            valid_lbl.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        valid_lbl.setVisible(false);
    }

    public void closeOnmouseClicked(MouseEvent event) {
        exitPrograme();
    }
}
