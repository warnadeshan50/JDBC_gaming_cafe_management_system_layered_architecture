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
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gaming_arena_system.bo.BOFactory;
import lk.ijse.gaming_arena_system.bo.custom.CustomerBO;
import lk.ijse.gaming_arena_system.dto.CustomerDTO;
import lk.ijse.gaming_arena_system.tm.CustomerTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CustomerFormController implements Initializable {
    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TableColumn<?, ?> custIDCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> addressCol;

    @FXML
    private TableColumn<?, ?> contactCol;

    @FXML
    private TableColumn<?, ?> emailCol;

    @FXML
    private Button add_customer_btn;

    @FXML
    private JFXComboBox<String> employee_id_cmb;

    @FXML
    private TextField customer_id_txt;

    @FXML
    private TextField name_txt;

    @FXML
    private TextField address_txt;

    @FXML
    private TextField contact_txt;

    @FXML
    private TextField email_txt;

    @FXML
    private Button update_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private Button report_btn;

    @FXML
    private Button search_btn;

    static String customer_id,employee_id,name,address,contact,email;
    Pattern customer_idPattern,namePattern,contactPattern,emailPattern;
    //EmployeeDAO employeeDAO=new EmployeeDAOImpl();
    //CustomerDAO customerDAO=new CustomerDAOImpl();
    CustomerBO customerBO=(CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOType.CUSTOMER);

    public void add_customer_btnOnAction(ActionEvent actionEvent) throws IOException {
        Parent window = FXMLLoader.load(getClass().getResource("/view/add_customer_form.fxml"));
        Scene scene = new Scene(window);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Add Customer ");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    public void employee_id_cmbOnAction(ActionEvent actionEvent) {
        employee_id=employee_id_cmb.getValue();
    }

    public void customer_id_txtOnAction(ActionEvent actionEvent) {
        search_btn.requestFocus();
    }

    public void name_txtOnAction(ActionEvent actionEvent) {
        address_txt.requestFocus();
    }

    public void address_txtOnAction(ActionEvent actionEvent) {
        contact_txt.requestFocus();
    }

    public void contact_txtOnAction(ActionEvent actionEvent) {
        email_txt.requestFocus();
    }

    public void email_txtOnAction(ActionEvent actionEvent) {
        update_btn.requestFocus();
    }

    public void update_btnOnAction(ActionEvent actionEvent) throws SQLException {
        getValuesTxtCmb();
        try{
            boolean update= customerBO.isUpdateCustomer(new CustomerDTO(customer_id,employee_id,name,address,contact,email));
            if(update){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!").show();
            }
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
        }
    }

    public void delete_btnOnAction(ActionEvent actionEvent) {
        try{
            boolean delete=customerBO.isDeleteCustomer(customer_id_txt.getText());
            if(delete){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted !").show();
            }
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "something happened !").show();
        }
    }

    public void report_btnOnAction(ActionEvent actionEvent) {
    }

    public void search_btnOnAction(ActionEvent actionEvent) throws SQLException {
        customer_id=customer_id_txt.getText();
        try {
            CustomerDTO customerDTO = customerBO.searchCustomer(customer_id);
            if (customerDTO != null) {
                hideTxtWhenEnterCustomerId(true);

                name_txt.setText(customerDTO.getCustomer_name());
                address_txt.setText(customerDTO.getCustomer_address());
                contact_txt.setText(customerDTO.getCustomer_contact());
                employee_id_cmb.setValue(customerDTO.getEmployee_id());
                email_txt.setText(customerDTO.getCustomer_email());
            }else {
                new Alert(Alert.AlertType.WARNING, "No Customer found").show();
            }
            }catch (SQLException e){
                System.out.println(e);
                new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
            }
    }

    public void customer_id_txtOnKeyReleased(KeyEvent keyEvent) {
        customer_id=customer_id_txt.getText();
        customer_idPattern=Pattern.compile("^(CU)[0-9]{4,5}$");
        txtFiledRegX(customer_idPattern,customer_id_txt,customer_id);
    }

    public void name_txtOnKeyReleased(KeyEvent keyEvent) {
        name=name_txt.getText();
        namePattern=Pattern.compile("^[A-Z][a-zA-z ]{1,29}$");
        txtFiledRegX(namePattern,name_txt,name);
    }

    public void contact_txtOnKeyReleased(KeyEvent keyEvent) {
        contact=contact_txt.getText();
        contactPattern=Pattern.compile("^[0-9]{10}$");
        txtFiledRegX(contactPattern,contact_txt,contact);
    }

    public void email_txtOnKeyReleased(KeyEvent keyEvent) {
        email=email_txt.getText();
        emailPattern=Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
        txtFiledRegX(emailPattern,email_txt,email);
    }
    public void txtFiledRegX(Pattern pattern , TextField textField, String txt){
        boolean matches=pattern.matcher(txt).matches();
        if(matches){
            textField.setStyle("-fx-text-fill:black");
        }else{
            textField.setStyle("-fx-text-fill:red");
        }
    }
    public void hideTxtWhenEnterCustomerId(Boolean visible){
        employee_id_cmb.setVisible(visible);
        name_txt.setVisible(visible);
        address_txt.setVisible(visible);
        contact_txt.setVisible(visible);
        email_txt.setVisible(visible);
        delete_btn.setVisible(visible);
        update_btn.setVisible(visible);
    }
    private void loadEmployeeIds() {
        try {
            List<String> ids = customerBO.getAllEmployeeId();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            employee_id_cmb.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void getAll() {
        try {
            ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
            List<CustomerDTO> cusList = customerBO.getAllCustomers();

            for(CustomerDTO customer : cusList) {
                obList.add(new CustomerTM(
                        customer.getCustomer_id(),
                        customer.getEmployee_id(),
                        customer.getCustomer_name(),
                        customer.getCustomer_address(),
                        customer.getCustomer_contact(),
                        customer.getCustomer_email()
                ));
            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }
    void setCellValueFactory() {
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customer_address"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("customer_contact"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("customer_email"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideTxtWhenEnterCustomerId(false);
        setCellValueFactory();
        getAll();
        loadEmployeeIds();
    }
    public void getValuesTxtCmb(){
         customer_id = customer_id_txt.getText();
         employee_id = employee_id_cmb.getValue();
         name = name_txt.getText();
         address = address_txt.getText();
         contact= contact_txt.getText();
         email= email_txt.getText();
    }
}
