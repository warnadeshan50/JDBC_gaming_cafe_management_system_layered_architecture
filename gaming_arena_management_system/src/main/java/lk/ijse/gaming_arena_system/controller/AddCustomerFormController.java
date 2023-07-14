package lk.ijse.gaming_arena_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import lk.ijse.gaming_arena_system.bo.BOFactory;
import lk.ijse.gaming_arena_system.bo.custom.CustomerBO;
import lk.ijse.gaming_arena_system.dto.CustomerDTO;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddCustomerFormController implements Initializable {
    @FXML
    private Label customer_id_lbl;

    @FXML
    private TextField name_txt;

    @FXML
    private TextField address_txt;

    @FXML
    private TextField contact_txt;

    @FXML
    private TextField email_txt;

    @FXML
    private ComboBox<String> employee_id_cmb;

    @FXML
    private Button add_btn;

    String customer_id,name,address,contact,email,employee_id;
    Pattern namePattern,contactPattern,emailPattern;
    //CustomerDAO customerDAO=new CustomerDAOImpl();
    //EmployeeDAO employeeDAO=new EmployeeDAOImpl();
    CustomerBO customerBO=(CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOType.CUSTOMER);

    public void name_txtOnAction(ActionEvent actionEvent) {
        name=name_txt.getText();
        address_txt.requestFocus();
    }

    public void address_txtOnAction(ActionEvent actionEvent) {
        address=address_txt.getText();
        contact_txt.requestFocus();
    }

    public void contact_txtOnAction(ActionEvent actionEvent) {
        contact=contact_txt.getText();
        email_txt.requestFocus();
    }

    public void email_txtOnAction(ActionEvent actionEvent) {
        email=email_txt.getText();
        employee_id_cmb.requestFocus();
    }

    public void employee_id_cmbOnAction(ActionEvent actionEvent) {
        employee_id=employee_id_cmb.getValue();
    }

    public void add_btnOnAction(ActionEvent actionEvent) throws SQLException {
        customer_id=customer_id_lbl.getText();
        name=name_txt.getText();
        address=address_txt.getText();
        contact=contact_txt.getText();
        email=email_txt.getText();
        try {
            customerBO.isSaveCustomer(new CustomerDTO(customer_id, employee_id, name, address, contact, email));
            generateNextCustomerId();

        } catch (SQLException e) {
        new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened").show();
        }
        clearTextField();
        new Alert(Alert.AlertType.CONFIRMATION,"Successfully add Customer").show();
    }
    private void generateNextCustomerId() {
        try {
            String nextId = customerBO.generateNextCustomerId();
            customer_id_lbl.setText(nextId);
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadEmployeeIds();
        generateNextCustomerId();
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
    public void txtFiledRegX(Pattern pattern ,TextField textField,String txt){
        boolean matches=pattern.matcher(txt).matches();
        if(matches){
            textField.setStyle("-fx-text-fill:black");
        }else{
            textField.setStyle("-fx-text-fill:red");
        }
    }
    public void clearTextField(){
        name_txt.clear();
        address_txt.clear();
        contact_txt.clear();
        email_txt.clear();
        employee_id_cmb.setValue("Choose");
    }
}
