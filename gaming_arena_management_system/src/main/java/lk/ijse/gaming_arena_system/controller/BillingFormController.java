package lk.ijse.gaming_arena_system.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gaming_arena_system.bo.BOFactory;
import lk.ijse.gaming_arena_system.bo.custom.BillingBO;
import lk.ijse.gaming_arena_system.db.DBConnection;
import lk.ijse.gaming_arena_system.dto.CustomerDTO;
import lk.ijse.gaming_arena_system.dto.OrderDTO;
import lk.ijse.gaming_arena_system.dto.OrderDetailsDTO;
import lk.ijse.gaming_arena_system.dto.ProductDTO;
import lk.ijse.gaming_arena_system.entity.OrderDetail;
import lk.ijse.gaming_arena_system.tm.CartTM;
import lk.ijse.gaming_arena_system.tm.OrderDetailTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BillingFormController implements Initializable {
    @FXML
    private Label order_id_lbl;

    @FXML
    private JFXComboBox<String> customer_id_cmb;

    @FXML
    private JFXComboBox<String> product_id_cmb;

    @FXML
    private Label name_lbl;

    @FXML
    private Label description_lbl;

    @FXML
    private Label price_lbl;

    @FXML
    private Label one_qty_price_lbl;

    @FXML
    private TextField num_of_qty;

    @FXML
    private TableView<CartTM> billing_tbl;

    @FXML
    private TableColumn<?, ?> product_col;

    @FXML
    private TableColumn<?, ?> description_col;

    @FXML
    private TableColumn<?, ?> one_qty_price_col;

    @FXML
    private TableColumn<?, ?> num_of_qty_col;

    @FXML
    private TableColumn<?, ?> price_col;

    @FXML
    private TableColumn<?, ?> remove_col;

    @FXML
    private Button print_bil_btn;

    @FXML
    private Label total_price_lbl;

    @FXML
    private TextField cash_txt;

    @FXML
    private Button add_new_customer_btn;

    @FXML
    private TableView<OrderDetailTM> order_detail_tbl;

    @FXML
    private TableColumn<?, ?> order_col;

    @FXML
    private TableColumn<?, ?> item_col;

    @FXML
    private TableColumn<?, ?> unit_col;

    @FXML
    private TableColumn<?, ?> tot_price_col;

    @FXML
    private Button report_btn;

    @FXML
    private Button add_cart_btn;

    BillingBO billingBO=(BillingBO) BOFactory.getBOFactory().getBO(BOFactory.BOType.BILLING);
    private ObservableList<CartTM> obList = FXCollections.observableArrayList();

    public void customer_Id_cmbOnAction(ActionEvent actionEvent) {
        String c_id=customer_id_cmb.getValue();
        try{
            CustomerDTO customer =billingBO.searchCustomer(c_id);
            if(customer!=null){
                name_lbl.setText(customer.getCustomer_name());
            }else{
                new Alert(Alert.AlertType.ERROR,"no Customer founded!!!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"something went wrong").show();
        }
    }

    public void product_id_cmbOnAction(ActionEvent actionEvent) {
        String p_id=product_id_cmb.getValue();
        try {
            ProductDTO productDTO =billingBO.searchproduct(p_id);
            if(productDTO!=null){
                description_lbl.setText(productDTO.getItem_description());
                one_qty_price_lbl.setText(String.valueOf(productDTO.getItem_one_qty_price()));
            }else {
                new Alert(Alert.AlertType.ERROR,"no Product founded!!!").show();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"something went wrong!!").show();
        }
    }

    public void num_of_qtyOnAction(ActionEvent actionEvent) {
        int num_unit=Integer.parseInt(num_of_qty.getText());
        double one_price=Double.parseDouble(one_qty_price_lbl.getText());
        double price=num_unit*one_price;
        price_lbl.setText(String.valueOf(price));

    }

    public void print_bil_btnOnAction(ActionEvent actionEvent) {
        String oId = order_id_lbl.getText();
        String cusId = customer_id_cmb.getValue();

        List<OrderDetailsDTO> cartDTOList = new ArrayList<>();

        for (int i = 0; i < billing_tbl.getItems().size(); i++) {
            CartTM tm = obList.get(i);

            OrderDetailsDTO cartDTO = new OrderDetailsDTO(oId,tm.getItem_id(), tm.getQty(), tm.getOnePrice());
            cartDTOList.add(cartDTO);
        }
        try {
            System.out.println(cusId);
            boolean isPlaced = placeOrder(oId, cusId, cartDTOList);
            System.out.println(isPlaced);
            if(isPlaced) {

                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                generateNextOrderId();
                customer_id_cmb.setPromptText("Choose");
                product_id_cmb.setPromptText("Choose");
                one_qty_price_lbl.setText("00.00");
                description_lbl.setText("not yet");
                total_price_lbl.setText("000.00");
                price_lbl.setText("0000.00");
                for ( int i = 0; i<billing_tbl.getItems().size(); i++) {
                    billing_tbl.getItems().clear();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    public void cash_txt_OnAction(ActionEvent actionEvent) {
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

    public void report_btn_OnAction(ActionEvent actionEvent) {
    }

    private void loadCustomerIds() {
        try {
            List<String> ids = billingBO.getCustomerIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            customer_id_cmb.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void loadproductIds() {
        try {
            List<String> ids = billingBO.getItemIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            product_id_cmb.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();
        try {
            if (result.orElse(no) == yes) {

                int index = billing_tbl.getSelectionModel().getSelectedIndex();
                obList.remove(index);

                billing_tbl.refresh();
                calculateNetTotal();
            }
        }catch (Exception exception){
            new Alert(Alert.AlertType.ERROR,"Selete row before click remove btn").show();
        }

        });
    }

    private void generateNextOrderId() {
        try {
            String nextId = billingBO.generateNextBillId();
            order_id_lbl.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void setCellValueFactory() {
        product_col.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        num_of_qty_col.setCellValueFactory(new PropertyValueFactory<>("qty"));
        one_qty_price_col.setCellValueFactory(new PropertyValueFactory<>("onePrice"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("total"));
        remove_col.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCustomerIds();
        loadproductIds();
        generateNextOrderId();
        setCellValueFactory();
    }

    public void add_cart_btnOnAction(ActionEvent actionEvent) {
        String code = product_id_cmb.getValue();
        String description = description_lbl.getText();
        int qty = Integer.parseInt(num_of_qty.getText());
        double onePrice = Double.parseDouble(one_qty_price_lbl.getText());
        double total = qty * onePrice;
        price_lbl.setText(String.valueOf(total));
        Button btnRemove = new Button("Remove ");
        btnRemove.setCursor(Cursor.HAND);

        setRemoveBtnOnAction(btnRemove); /* set action to the btnRemove */

        if (!obList.isEmpty()) {
            for (int i = 0; i < billing_tbl.getItems().size(); i++) {
                if (product_col.getCellData(i).equals(code)) {
                    qty += (int) num_of_qty_col.getCellData(i);
                    total = qty * onePrice;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(total);

                    billing_tbl.refresh();
                    calculateNetTotal();
                    return;
                }
            }
        }

        CartTM tm = new CartTM(code, description,onePrice,qty, total, btnRemove);

        obList.add(tm);
        billing_tbl.setItems(obList);
        calculateNetTotal();

        num_of_qty.setText("");
    }
    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < billing_tbl.getItems().size(); i++) {
            double total  = (double) price_col.getCellData(i);
            netTotal += total;
        }
        total_price_lbl.setText(String.valueOf(netTotal));
    }
    public boolean placeOrder(String oId, String cusId, List<OrderDetailsDTO> cartDTOList) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);
            String date=String.valueOf(LocalDate.now());
            String time=String.valueOf(LocalTime.now());

            boolean isSaved = billingBO.isSaveOrder(new OrderDTO(oId, cusId, date, time));
            if (isSaved) {
                boolean isUpdated = billingBO.updateQty(cartDTOList);
                if (isUpdated) {
                    boolean isOrderDetailSaved = billingBO.save(cartDTOList);
                    if (isOrderDetailSaved) {
                        con.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException er) {
            er.printStackTrace();
            con.rollback();
            return false;
        } finally {
            System.out.println("finally");
            con.setAutoCommit(true);
        }
    }

}
