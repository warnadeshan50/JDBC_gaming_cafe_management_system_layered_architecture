package lk.ijse.gaming_arena_system.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gaming_arena_system.bo.BOFactory;
import lk.ijse.gaming_arena_system.bo.custom.CafeBO;
import lk.ijse.gaming_arena_system.dto.CafeBillDTO;
import lk.ijse.gaming_arena_system.dto.CafeDetailDTO;
import lk.ijse.gaming_arena_system.dto.ComputerDTO;
import lk.ijse.gaming_arena_system.dto.CustomerDTO;
import lk.ijse.gaming_arena_system.entity.CafeComputers;
import lk.ijse.gaming_arena_system.tm.CafeComputerTM;
import lk.ijse.gaming_arena_system.tm.CustomerTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CafeRefreshFormController implements Initializable {
    @FXML
    private TextField start_time_txt;

    @FXML
    private JFXComboBox<String> cust_cmb;

    @FXML
    private Label bill_no_lbl;

    @FXML
    private Label name_lbl;

    @FXML
    private JFXComboBox<String> comp_id_cmb;

    @FXML
    private Label per_hour_price_lbl;

    @FXML
    private Label total_price_lbl;

    @FXML
    private TextField cash_txt;

    @FXML
    private Label balance_lbl;

    @FXML
    private TextField end_time_txt;

    @FXML
    private Button cafe_bill_add_customer_btn;

    @FXML
    private Button print_btn;

    @FXML
    private Button report_btn;

    @FXML
    private TableView<CafeComputerTM> computer_tbl;

    @FXML
    private TableColumn<?, ?> computer_id_col;

    @FXML
    private TableColumn<?, ?> description_col;

    @FXML
    private TableColumn<?, ?> amount_per_hour_col;

    @FXML
    private TextField computer_id_txt;

    @FXML
    private TextField des_txt;

    @FXML
    private TextField amount_txt;

    @FXML
    private Button computer_add_btn;

    @FXML
    private Button update_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private Button search_btn;

    @FXML
    private Button pay_payment_report_btn;

    @FXML
    private Label mem_payment_id;

    @FXML
    private Label mem_name_lbl;

    @FXML
    private Label type_lbl;

    @FXML
    private Label month_lbl;

    @FXML
    private Label payment_lbl;

    @FXML
    private ComboBox<String> member_id_cmb;

    @FXML
    private Button pay_payment_btn;

    Pattern timePattern,cashPattern,computer_idPattern;
    double start_time,end_time,cash,balance,payment,per_hour_price,total_price,amount_per_hour;
    String bill_no,customer_id,customer_name,computer_id,description,member_id,membership_id,member_customer_name,type,month,sTime,eTime,bill_computer_id;

    CafeBO cafeBO=(CafeBO) BOFactory.getBOFactory().getBO(BOFactory.BOType.CAFE);

    public void start_time_txtOnAction(ActionEvent actionEvent) {
        end_time_txt.requestFocus();
    }

    public void start_time_txtOnKeyReleased(KeyEvent keyEvent) {
        start_time=Double.parseDouble(start_time_txt.getText());
        timePattern=Pattern.compile("^[0-9]{1,2}(.)[0-9]{2,2}$");
        String s=String.valueOf(start_time_txt.getText());
        txtFiledRegX(timePattern,start_time_txt,s);
    }

    public void cust_cmbOnAction(ActionEvent actionEvent) {
        customer_id=cust_cmb.getValue();
        start_time_txt.requestFocus();
        try {
            CustomerDTO customerDTO=cafeBO.searchCustomer(customer_id);
            if(customerDTO!=null){
                name_lbl.setText(String.valueOf(customerDTO.getCustomer_name()));
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Somthing went Worng");
        }
    }

    public void computer_id_cmbOnAction(ActionEvent actionEvent) {
        bill_computer_id=comp_id_cmb.getValue();
        try {
            ComputerDTO computerDTO=cafeBO.searchCafeComputer(bill_computer_id);
            if(computerDTO!=null){
                per_hour_price_lbl.setText(String.valueOf(computerDTO.getOne_hour_price()));
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Somthing went Worng");
        }

        cash_txt.requestFocus();
    }

    public void cash_txtOnAction(ActionEvent actionEvent) {
        print_btn.requestFocus();
        cash=Double.parseDouble(cash_txt.getText());
        total_price=Double.parseDouble(total_price_lbl.getText());
        balance=cash-total_price;
        balance_lbl.setText(String.valueOf(balance));

    }

    public void cash_txtOnKeyReleased(KeyEvent keyEvent) {
        cash=Double.parseDouble(cash_txt.getText());
        cashPattern=Pattern.compile("^[0-9]{1,20}(.)[0-9]{2,2}$");
        String c=String.valueOf(cash_txt.getText());
        txtFiledRegX(cashPattern,cash_txt,c);
    }

    public void end_time_txtOnAction(ActionEvent actionEvent) {
        start_time=Double.parseDouble(start_time_txt.getText());
        end_time=Double.parseDouble(end_time_txt.getText());
        double t=end_time-start_time;
        per_hour_price=Double.parseDouble(per_hour_price_lbl.getText());
        total_price=t*per_hour_price;
        total_price_lbl.setText(String.valueOf(total_price));
        comp_id_cmb.requestFocus();
    }

    public void end_time_txtOnKeyReleased(KeyEvent keyEvent) {
        end_time=Double.parseDouble(end_time_txt.getText());
        timePattern=Pattern.compile("^[0-9]{1,2}(.)[0-9]{2,2}$");
        String e=String.valueOf(end_time_txt.getText());
        txtFiledRegX(timePattern,end_time_txt,e);
    }

    public void cafe_bill_add_customer_btnOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent =FXMLLoader.load(getClass().getResource("/view/add_customer_form.fxml"));
        Stage stage=new Stage();
        Scene scene=new Scene(parent);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Add Customer ");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void print_btnOnAction(ActionEvent actionEvent) {
        getBillDetailsInTxtsLblCmb();
        try {
            boolean bill_save=cafeBO.isSaveCafeBill(new CafeBillDTO(bill_no,customer_id));
            if (bill_save){
                boolean details_save=cafeBO.isSaveCafeDetails(new CafeDetailDTO(bill_no,bill_computer_id,start_time,end_time,total_price));
                if(details_save){
                    new Alert(Alert.AlertType.CONFIRMATION,"Bill Details Add").show();
                }
                System.out.println(details_save);
                new Alert(Alert.AlertType.ERROR,"no Details added").show();
            }

        }catch (SQLException e){
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR,"Something went Wrong").show();
        }


    }

    public void computer_id_txtOnAction(ActionEvent actionEvent) {
        des_txt.requestFocus();
    }

    public void computer_Id_txtOnkeyReleased(KeyEvent keyEvent) {
        computer_id=computer_id_txt.getText();
        computer_idPattern=Pattern.compile("^(GG)[0-9]{4,5}$");
        txtFiledRegX(computer_idPattern,computer_id_txt,computer_id);
    }

    public void description_txtOnAction(ActionEvent actionEvent) {
        amount_txt.requestFocus();
    }

    public void amount_txtOnAction(ActionEvent actionEvent) {
        computer_add_btn.requestFocus();
    }

    public void amount_per_hour_txtOnKeyReleased(KeyEvent keyEvent) {
        cashPattern=Pattern.compile(("^[0-9]{1,20}(.)[0-9]{2,2}$"));
        String aph=amount_txt.getText();
        txtFiledRegX(cashPattern,amount_txt,aph);
    }

    public void computer_add_btnOnAction(ActionEvent actionEvent) throws SQLException {
        getTxtNCmb();
        try {
            boolean save=cafeBO.isSaveCafeComputer(new ComputerDTO(computer_id, description, amount_per_hour));
            if(save){
                new Alert(Alert.AlertType.CONFIRMATION, "Computer saved!!!").show();
                clearComputerTxts();
                generateNextComputerId();
                setValuesComputer_tbl();
                setCellValueFactory();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"OOOPSSS!!! something went Wrong");
        }
    }

    public void update_btnOnAction(ActionEvent actionEvent) {
        getTxtNCmb();
        try{
            boolean update=cafeBO.isUpdateCafeComputer(new ComputerDTO(computer_id,description,amount_per_hour));
            if (update){
                new Alert(Alert.AlertType.CONFIRMATION,"Computer Updated!!!").show();
                clearComputerTxts();
                generateNextComputerId();
                setValuesComputer_tbl();
                setCellValueFactory();
            }
        }catch (SQLException e){
            System.out.println(amount_per_hour);
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR,"OOOPSSS!!! something went Wrong").show();
        }
    }

    public void delete_btnOnAction(ActionEvent actionEvent) {
        computer_id=computer_id_txt.getText();
        try {
            boolean delete=cafeBO.isDeleteCafeComputer(computer_id);
            if (delete){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!!!");
                clearComputerTxts();
                generateNextComputerId();
                setValuesComputer_tbl();
                setCellValueFactory();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"OOOPSSS!!! something went Wrong").show();
        }
    }

    public void search_btnOnAction(ActionEvent actionEvent) {
        computer_id=computer_id_txt.getText();
        try {
            ComputerDTO computerDTO=cafeBO.searchCafeComputer(computer_id);
            if (computerDTO!=null){
                computer_id_txt.setText(computerDTO.getComputer_id());
                des_txt.setText(computerDTO.getDescription());
                amount_txt.setText(String.valueOf(computerDTO.getOne_hour_price()));
            }else {
                new Alert(Alert.AlertType.ERROR, "No computer Found");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"oopsss!! something went Wrong!!!!").show();
        }
    }

    public void pay_payment_report_btn(ActionEvent actionEvent) {
    }

    public void member_id_cmbOnAction(ActionEvent actionEvent) {
    }

    public void pay_payment_btn(ActionEvent actionEvent) {
    }
    public void txtFiledRegX(Pattern pattern , TextField textField, String txt){
        boolean matches=pattern.matcher(txt).matches();
        if(matches){
            textField.setStyle("-fx-text-fill:black");
        }else{
            textField.setStyle("-fx-text-fill:red");
        }
    }
    public void getTxtNCmb(){
        computer_id=computer_id_txt.getText();
        description=des_txt.getText();
        amount_per_hour=Double.parseDouble(amount_txt.getText());
    }

    public void setValuesComputer_tbl(){
        try{
            ObservableList<CafeComputerTM> obList = FXCollections.observableArrayList();
            ArrayList<ComputerDTO> computerDTOs=cafeBO.getAllComputers();
            for(ComputerDTO computerDTO : computerDTOs) {
            obList.add(new CafeComputerTM(
                    computerDTO.getComputer_id(),
                    computerDTO.getDescription(),
                    computerDTO.getOne_hour_price()

            ));
        }
        computer_tbl.setItems(obList);
    } catch (SQLException e) {
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }
    void setCellValueFactory() {
        computer_id_col.setCellValueFactory(new PropertyValueFactory<>("computer_id"));
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        amount_per_hour_col.setCellValueFactory(new PropertyValueFactory<>("price_for_per_hour"));
    }
    public void generateNextComputerId(){
        try {
            String nextId = cafeBO.generateNextComputerId();
            computer_id_txt.setText(nextId);
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
    public void generateNextCafeBillId(){
        try {
            String nextId = cafeBO.generateNextCafeBillId();
            bill_no_lbl.setText(nextId);
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
    public void loadCustomerIds(){
        try {
            List<String> ids = cafeBO.getCustomerIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cust_cmb.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    public void loadComputerIds(){
        try {
            List<String> ids = cafeBO.getCafeComputerIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            comp_id_cmb.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    public void clearComputerTxts(){
        computer_id_txt.clear();
        des_txt.clear();
        amount_txt.clear();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        setValuesComputer_tbl();
        generateNextComputerId();
        generateNextCafeBillId();
        loadCustomerIds();
        loadComputerIds();
    }

    public void getBillDetailsInTxtsLblCmb(){
        bill_no=bill_no_lbl.getText();
        customer_id=cust_cmb.getValue();
        customer_name=name_lbl.getText();
        bill_computer_id=comp_id_cmb.getValue();
        start_time=Double.parseDouble(start_time_txt.getText());
        end_time=Double.parseDouble(end_time_txt.getText());
        per_hour_price=Double.parseDouble(per_hour_price_lbl.getText());
        total_price=Double.parseDouble(total_price_lbl.getText());
        cash=Double.parseDouble(cash_txt.getText());
        balance=Double.parseDouble(balance_lbl.getText());
    }
}
