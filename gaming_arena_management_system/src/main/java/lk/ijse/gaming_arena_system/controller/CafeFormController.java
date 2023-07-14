package lk.ijse.gaming_arena_system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CafeFormController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane dashboard;

    @FXML
    private AnchorPane management_anchorpane;

    @FXML
    private Label management_lbl;

    @FXML
    private Button print_bill_btn;

    @FXML
    private Button report_btnOnAction;

    @FXML
    private TextField start_time;

    @FXML
    private JFXComboBox<?> cust_cmb;

    @FXML
    private Label bill_no_lbl;

    @FXML
    private Label name_lbl;

    @FXML
    private JFXComboBox<?> comp_id_cmb;

    @FXML
    private Label per_hour_price_lbl;

    @FXML
    private Label total_price_lbl;

    @FXML
    private TextField cash_txt;

    @FXML
    private Label balance_lbl;

    @FXML
    private TextField end_time;

    @FXML
    private Button add_btn;

    @FXML
    private Button add_comp_btn;

    @FXML
    private Button update_comp_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private TextField computer_id;

    @FXML
    private TextField des_txt;

    @FXML
    private TextField amount_txt;

    @FXML
    private Button pay_payment_btn;

    @FXML
    private Label mem_payment_id;

    @FXML
    private JFXComboBox<?> mem_id_cmb;

    @FXML
    private Label mem_name_lbl;

    @FXML
    private Label type_lbl;

    @FXML
    private Label month_lbl;

    @FXML
    private Label payment_lbl;

    @FXML
    private JFXButton cafe_btn;

    @FXML
    private JFXButton membership_btn;

    @FXML
    private JFXButton product_btn;

    @FXML
    private JFXButton billing_btn;

    @FXML
    private JFXButton customer_btn;

    @FXML
    private JFXButton supplier_btn;

    @FXML
    private JFXButton delivery_btn;

    @FXML
    private JFXButton employee_btn;

    @FXML
    private JFXButton logout_btn;

    private void exitPrograme(){
        Alert alert=new Alert(Alert.AlertType.WARNING,"Do you want to exit ?",
                ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.YES)
            javafx.application.Platform.exit();
    }

    public void close_btnOnMouseClicked(MouseEvent mouseEvent) {
        exitPrograme();
    }

    public void cafe_btnOnAction(ActionEvent actionEvent) throws IOException {
        management_anchorpane.setStyle("-fx-background-color:#ff7878");
        AnchorPane parent = FXMLLoader.load(getClass().getResource("/view/cafe_refresh.fxml"));
        root.getChildren().setAll(parent);
    }

    public void membership_btnOnAction(ActionEvent actionEvent) throws IOException {
        management_anchorpane.setStyle("-fx-background-color:#ff7878");
        management_lbl.setText("Membership Management");
        AnchorPane parent = FXMLLoader.load(getClass().getResource("/view/membership_form.fxml"));
        root.getChildren().setAll(parent);
    }

    public void employee_btnOnAction(ActionEvent actionEvent) throws IOException {
        management_anchorpane.setStyle("-fx-background-color:#820000");
        management_lbl.setText("Employee Management");
        AnchorPane parent = FXMLLoader.load(getClass().getResource("/view/employee_form.fxml"));
        root.getChildren().setAll(parent);

    }

    public void deliver_btnOnAction(ActionEvent actionEvent) throws IOException {
        management_anchorpane.setStyle("-fx-background-color:#820000");
        management_lbl.setText("Delivery Management");
        AnchorPane parent = FXMLLoader.load(getClass().getResource("/view/delivery_form.fxml"));
        root.getChildren().setAll(parent);
    }
    public void customer_btnOnAction(ActionEvent actionEvent) throws IOException {
        management_anchorpane.setStyle("-fx-background-color:#e40000");
        management_lbl.setText("Customer Management");
        AnchorPane parent = FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"));
        root.getChildren().setAll(parent);
    }

    public void billing_btnOnAction(ActionEvent actionEvent) throws IOException {
        management_anchorpane.setStyle("-fx-background-color:#ff1616");
        management_lbl.setText("Billing Management");
        AnchorPane parent = FXMLLoader.load(getClass().getResource("/view/billing_form.fxml"));
        root.getChildren().setAll(parent);
    }


    public void product_btnOnAction(ActionEvent actionEvent) throws IOException {
        management_anchorpane.setStyle("-fx-background-color:#ff4747");
        management_lbl.setText("Product Management");
        AnchorPane parent = FXMLLoader.load(getClass().getResource("/view/product_form.fxml"));
        root.getChildren().setAll(parent);
    }

    public void supplier_btnOnAction(ActionEvent actionEvent) throws IOException {
        management_anchorpane.setStyle("-fx-background-color:#b30000");
        management_lbl.setText("Supplier Management");
        AnchorPane parent = FXMLLoader.load(getClass().getResource("/view/supplier_form.fxml"));
        root.getChildren().setAll(parent);
    }

    public void logout_btnOnAction(ActionEvent actionEvent) {
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboard.getStylesheets().add(getClass().getResource("/css/main_css.css").toExternalForm());
    }
}
