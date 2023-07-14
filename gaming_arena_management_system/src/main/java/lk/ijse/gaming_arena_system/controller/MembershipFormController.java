package lk.ijse.gaming_arena_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import lk.ijse.gaming_arena_system.bo.custom.MembershipBO;
import lk.ijse.gaming_arena_system.dto.CustomerDTO;
import lk.ijse.gaming_arena_system.dto.MembershipDTO;
import lk.ijse.gaming_arena_system.tm.CustomerTM;
import lk.ijse.gaming_arena_system.tm.MembershipTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class MembershipFormController implements Initializable {
    @FXML
    private TextField s_member_id_txt;

    @FXML
    private TextField s_customer_id_txt;

    @FXML
    private Label s_name_lbl;

    @FXML
    private Label s_address_lbl;

    @FXML
    private Label s_contact_lbl;

    @FXML
    private Label s_email_lbl;

    @FXML
    private Label s_type_lbl;

    @FXML
    private Button s_member_search_btn;

    @FXML
    private Button s_customer_search_btn;

    @FXML
    private Label s_packagePrice_lbl;

    @FXML
    private Label a_member_id_lbl;

    @FXML
    private ComboBox<String> a_customer_id_cmb;

    @FXML
    private ComboBox<String> a_type_cmb;

    @FXML
    private Label a_packagePrice_lbl;

    @FXML
    private Button add_new_customer_btn;

    @FXML
    private Button a_add_btn;

    @FXML
    private TableView<MembershipTM> membership_tbl;

    @FXML
    private TableColumn<?, ?> memship_col;

    @FXML
    private TableColumn<?, ?> customer_col;

    @FXML
    private TableColumn<?, ?> type_col;

    @FXML
    private TableColumn<?, ?> packagePrice_col;

    @FXML
    private Label u_package_price_lbl;

    @FXML
    private ComboBox<String> u_type_cmb;

    @FXML
    private TextField u_member_id_txt;

    @FXML
    private TextField u_customer_id_txt;

    @FXML
    private Button update_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private Button u_member_search_btn;

    String s_member_id,s_customer_id;
    Pattern membership_idPattern,customer_idPattern;
    MembershipBO membershipBO= (MembershipBO)BOFactory.getBOFactory().getBO(BOFactory.BOType.MEMBERSHIP);

    public void s_member_idtxtOnAction(ActionEvent actionEvent) {
        s_member_search_btn.requestFocus();
    }

    public void s_member_idtxtOnKeyReleased(KeyEvent keyEvent) {
        s_member_id=s_member_id_txt.getText();
        membership_idPattern= Pattern.compile("^(MS)[0-9]{4,5}$");
        txtFiledRegX(membership_idPattern,s_member_id_txt,s_member_id);
    }

    public void s_customer_id_txtOnAction(ActionEvent actionEvent) {
        s_customer_search_btn.requestFocus();
    }

    public void s_customer_id_txtOnKeyReleased(KeyEvent keyEvent) {
        s_customer_id=s_customer_id_txt.getText();
        customer_idPattern=Pattern.compile("^(CU)[0-9]{4,5}$");
        txtFiledRegX(customer_idPattern,s_customer_id_txt,s_customer_id);
    }

    public void a_customer_id_cmbOnAction(ActionEvent actionEvent) {
        a_customer_id_cmb.getValue();
    }

    public void a_type_cmbOnAction(ActionEvent actionEvent) {
        String type=a_type_cmb.getValue();
        Double price=choosePrice(type);
        a_packagePrice_lbl.setText(String.valueOf(price));

    }

    public void add_new_customer_btnOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/add_customer_form.fxml"));
        Stage stage=new Stage();
        Scene scene=new Scene(parent);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Add Customer ");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void a_add_btnOnAction(ActionEvent actionEvent) {
        String mem_id=a_member_id_lbl.getText();
        String cust_id=a_customer_id_cmb.getValue();
        String t=a_type_cmb.getValue();
        Double price=Double.valueOf(a_packagePrice_lbl.getText());
        try{
            boolean save=membershipBO.isSaveMembership(new MembershipDTO(mem_id,cust_id,t,price));
            System.out.println(save);
            if(save){
                new Alert(Alert.AlertType.CONFIRMATION,"Membership added!!!").show();
                generateNextMemberId();
                a_type_cmb.setValue("Choose");
                a_customer_id_cmb.setValue("Choose");
                a_packagePrice_lbl.setText("000.00");
                getAllMemebers();
                setCellValueFactory();
            }
        }catch (SQLException e){
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR,"Something went Wrong!!!").show();
        }
    }

    public void u_type_cmbOnAction(ActionEvent actionEvent) {
    }

    public void u_member_id_txtOnAction(ActionEvent actionEvent) {
        u_member_search_btn.requestFocus();
    }

    public void u_member_id_txtOnKeyReleased(KeyEvent keyEvent) {
        String u_member_id=u_member_id_txt.getText();
        membership_idPattern= Pattern.compile("^(MS)[0-9]{4,5}$");
        txtFiledRegX(membership_idPattern,u_member_id_txt,u_member_id);
    }

    public void u_customer_id_txtOnKeyReleased(KeyEvent keyEvent) {
    }

    public void u_customer_id_txt(ActionEvent actionEvent) {
        String u_customer_id=u_customer_id_txt.getText();
        customer_idPattern=Pattern.compile("^(CU)[0-9]{4,5}$");
        txtFiledRegX(customer_idPattern,u_customer_id_txt,u_customer_id);
    }

    public void u_member_search_btnOnAction(ActionEvent actionEvent) {
        String id=u_member_id_txt.getText();
        try {
            MembershipDTO membershipDTO = membershipBO.searchMember(id);
            if (membershipDTO != null) {
                u_customer_id_txt.setText(membershipDTO.getCustomer_id());
                u_type_cmb.setValue(membershipDTO.getType());
                u_package_price_lbl.setText(String.valueOf(membershipDTO.getPackage_price()));
            }else{
                new Alert(Alert.AlertType.ERROR,"No member Founded").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }
    public void txtFiledRegX(Pattern pattern , TextField textField, String txt){
        boolean matches=pattern.matcher(txt).matches();
        if(matches){
            textField.setStyle("-fx-text-fill:black");
        }else{
            textField.setStyle("-fx-text-fill:red");
        }
    }

    public void s_member_search_btnOnAction(ActionEvent actionEvent) {
        String id=s_member_id_txt.getText();
        try {
            MembershipDTO membershipDTO=membershipBO.searchMember(id);
            if(membershipDTO!=null){
                s_customer_id_txt.setText(membershipDTO.getCustomer_id());
                s_type_lbl.setText(membershipDTO.getType());
                s_packagePrice_lbl.setText(String.valueOf(membershipDTO.getPackage_price()));
                String c_id= s_customer_id_txt.getText();
                try {
                    CustomerDTO customerDTO = membershipBO.searchCustomer(c_id);
                    if(customerDTO!=null) {
                        s_name_lbl.setText(customerDTO.getCustomer_id());
                        s_address_lbl.setText(customerDTO.getCustomer_address());
                        s_contact_lbl.setText(customerDTO.getCustomer_contact());
                        s_email_lbl.setText(customerDTO.getCustomer_email());
                    }else{
                        new Alert(Alert.AlertType.ERROR,"No customer Found!!!");
                    }
                }catch (SQLException e){
                    new Alert(Alert.AlertType.ERROR,"Something went Wrong");
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"No membership found");
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

    public void s_customer_search_btnOnAction(ActionEvent actionEvent) {
        String c_id=s_customer_id_txt.getText();
        try{
            CustomerDTO customerDTO=membershipBO.searchCustomer(c_id);
            if(customerDTO!=null){
                s_name_lbl.setText(customerDTO.getCustomer_name());
                s_address_lbl.setText(customerDTO.getCustomer_address());
                s_contact_lbl.setText(customerDTO.getCustomer_contact());
                s_email_lbl.setText(customerDTO.getCustomer_email());
                try {
                    MembershipDTO membershipDTO = membershipBO.searchMemberByCustomerId(c_id);
                    if (membershipDTO != null) {
                        s_member_id_txt.setText(membershipDTO.getMember_id());
                        s_type_lbl.setText(membershipDTO.getType());
                        s_packagePrice_lbl.setText(String.valueOf(membershipDTO.getPackage_price()));
                    }else {
                        new Alert(Alert.AlertType.ERROR,"No member found").show();
                    }
                }catch (SQLException e){
                    new Alert(Alert.AlertType.ERROR,"something went wrong").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"No customer found").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"something went wrong").show();
        }
    }

    public void update_btnOnAction(ActionEvent actionEvent) {
        String m_id=u_member_id_txt.getText();
        String c_id=u_customer_id_txt.getText();
        String t=u_type_cmb.getValue();
        Double price=Double.valueOf(u_package_price_lbl.getText());
        updateBeforeMsg(m_id,c_id,t,price);
        /*try{
            boolean update=membershipBO.isUpdateMembership(new MembershipDTO(m_id,c_id,t,price));
            if (update) {
                new Alert(Alert.AlertType.CONFIRMATION,"Membership updated!!!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"something went wrong!!!!").show();
        }*/
    }

    public void delete_btnOnAction(ActionEvent actionEvent) {
        String m_id=u_member_id_txt.getText();
        deleteBeforeMsg(m_id);
            /*try {
                boolean delete = membershipBO.isDeleteMembership(m_id);
                if (delete) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Membership deleted!!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "something went wrong!!!!").show();
            }*/
    }
    public double choosePrice(String type){
        switch (type){
            case "Bronze":
                return 500.00;
            case "Silver":
                return 1000.00;
            case "Gold":
                return 1500.00;
            case "Platinum":
                return 2000.00;
            case "Immortal":
                return 3000.00;
            default:
                return 0000.00;
        }
    }
    private void loadTypes() {
        ObservableList<String> types= FXCollections.observableArrayList();

        types.add("Bronze");
        types.add("Silver");
        types.add("Gold");
        types.add("Platinum");
        types.add("Immortal");
        a_type_cmb.setItems(types);
        u_type_cmb.setItems(types);
    }
    private void generateNextMemberId() {
        try {
            String nextId = membershipBO.generateNextMembershipId();
            a_member_id_lbl.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getCustomerIDs(){
        try {
            ArrayList<String> ids = membershipBO.getCustomerIds();
            ObservableList<String> obList = FXCollections.observableArrayList();
            for (String id : ids) {
                obList.add(id);
            }
            a_customer_id_cmb.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void getAllMemebers() {
        try {
            ObservableList<MembershipTM> obList = FXCollections.observableArrayList();
            List<MembershipDTO> memList = membershipBO.getAllMemberships();

            for (MembershipDTO membershipDTO : memList) {
                obList.add(new MembershipTM(
                        membershipDTO.getMember_id(),
                        membershipDTO.getCustomer_id(),
                        membershipDTO.getType(),
                        membershipDTO.getPackage_price()
                ));
            }
            membership_tbl.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }
    void setCellValueFactory() {
        memship_col.setCellValueFactory(new PropertyValueFactory<>("member_id"));
        customer_col.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        packagePrice_col.setCellValueFactory(new PropertyValueFactory<>("package_price"));
    }
    public void deleteBeforeMsg(String m_id){
        Alert alert=new Alert(Alert.AlertType.WARNING,"Do you want to Deleted  ?",
                ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.YES)
            try {
                boolean delete = membershipBO.isDeleteMembership(m_id);
                if (delete) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Membership deleted!!!").show();
                    getAllMemebers();
                    setCellValueFactory();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "something went wrong!!!!").show();
            }
    }
    public void updateBeforeMsg(String m_id,String c_id,String t,Double price) {

        Alert alert = new Alert(Alert.AlertType.WARNING, "Do you want to Update  ?",
                ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES)
        try{
            boolean update=membershipBO.isUpdateMembership(new MembershipDTO(m_id,c_id,t,price));
            if (update) {
                new Alert(Alert.AlertType.CONFIRMATION,"Membership updated!!!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"something went wrong!!!!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTypes();
        generateNextMemberId();
        getCustomerIDs();
        getAllMemebers();
        setCellValueFactory();
    }

}
