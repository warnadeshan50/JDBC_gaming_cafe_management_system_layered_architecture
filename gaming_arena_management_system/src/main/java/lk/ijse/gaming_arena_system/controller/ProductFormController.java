package lk.ijse.gaming_arena_system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.gaming_arena_system.bo.BOFactory;
import lk.ijse.gaming_arena_system.bo.custom.ProductBO;
import lk.ijse.gaming_arena_system.dto.ProductDTO;
import lk.ijse.gaming_arena_system.tm.ProductStatusTM;
import lk.ijse.gaming_arena_system.tm.ProductTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ProductFormController implements Initializable {
    @FXML
    private TableView<ProductStatusTM> item_tbl;

    @FXML
    private TableColumn<?, ?> id_col;

    @FXML
    private TableColumn<?, ?> desc_col;

    @FXML
    private TableColumn<?, ?> price_col;

    @FXML
    private TableColumn<?, ?> status_col;

    @FXML
    private Label type_lbl;

    @FXML
    private JFXButton motherBoard_btn;

    @FXML
    private JFXButton ram_btn;

    @FXML
    private JFXButton storage_btn;

    @FXML
    private JFXButton vga_btn;

    @FXML
    private JFXButton cooler_btn;

    @FXML
    private JFXButton monitor_btn;

    @FXML
    private JFXButton casing_btn;

    @FXML
    private JFXButton laptop_btn;

    @FXML
    private JFXButton cpu_btn;

    @FXML
    private Label add_item_id_lbl;

    @FXML
    private TextField add_description_txt;

    @FXML
    private JFXComboBox<String> add_type_cmb;

    @FXML
    private TextField add_onHandQty_txt;

    @FXML
    private TextField add_oneQtyPrice_txt;

    @FXML
    private Button add_btn;
    @FXML
    private Button search_btn;

    @FXML
    private TextField update_item_id_txt;

    @FXML
    private TextField update_desciption_txt;

    @FXML
    private TextField update_onHandQty_txt;

    @FXML
    private TextField update_OneQtyPrice_txt;

    @FXML
    private JFXComboBox<String> update_type_cmb;

    @FXML
    private TableView<ProductTM> product_tbl;

    @FXML
    private TableColumn<?, ?> item_id_col;

    @FXML
    private TableColumn<?, ?> description_col;

    @FXML
    private TableColumn<?, ?> type_col;

    @FXML
    private TableColumn<?, ?> onHandQty_col;

    @FXML
    private TableColumn<?, ?> oneQtyPrice_col;

    @FXML
    private Button update_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private Button report_btn;

    Pattern productIdPattern,oneQtyPricePattern,onHandQtyPattern;
    String addProductId,addDescription,addType,updateProductId,update_item_id,updatedescription,updateType;
    int addOnHandQty,updateHandQty;
    double addOneQtyPrice,updateOneQtyPrice;
    //ProductDAO productDAO=new ProductDAOImpl();
    ProductBO productBO=(ProductBO) BOFactory.getBOFactory().getBO(BOFactory.BOType.PRODUCT);

    public void ram_btnOnAction(ActionEvent actionEvent) {
        setDetails("Ram");
    }

    public void storage_btnOnAction(ActionEvent actionEvent) {
        setDetails("Storage");
    }

    public void vga_btnOnAction(ActionEvent actionEvent) {
        setDetails("Vga");
    }

    public void cooler_btnOnAction(ActionEvent actionEvent) {
        setDetails("Cooler");
    }

    public void monitor_btnOnAction(ActionEvent actionEvent) {
        setDetails("Monitor");
    }

    public void casing_btnOnAction(ActionEvent actionEvent) {
        setDetails("Casing");
    }

    public void laptop_btnOnAction(ActionEvent actionEvent) {
        setDetails("Laptop");
    }

    public void motherBoard_btnOnAction(ActionEvent actionEvent) {
        setDetails("Mother Boards");
    }

    public void cpu_btnOnAction(ActionEvent actionEvent) {
        setDetails("CPU");
    }

    public void add_btnOnAction(ActionEvent actionEvent) throws SQLException {
        addProductGetTextValues();
        try {
            boolean save=productBO.isSaveProduct(new ProductDTO(addProductId, addDescription, addType, addOnHandQty, addOneQtyPrice));
            if(save){
                clearTextAndGenerateNewId();
                new Alert(Alert.AlertType.CONFIRMATION, "Item saved!!!").show();
                setCellValueFactoryAllProducts();
                getAllProducts();
            }
        }catch (SQLException e){
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private void clearTextAndGenerateNewId() {
        generateNextProductId();
        add_description_txt.clear();
        add_type_cmb.setValue("Choose");
        add_onHandQty_txt.clear();
        add_oneQtyPrice_txt.clear();
    }

    public void item_id_txtOnAction(ActionEvent actionEvent) {
        search_btn.requestFocus();
    }

    public void item_id_txtOnKeyReleased(KeyEvent keyEvent) {
        updateProductId=update_item_id_txt.getText();
        productIdPattern=Pattern.compile("^(IT)[0-9]{4,5}$");
        txtFiledRegX(productIdPattern,update_item_id_txt,updateProductId);
    }
    public void search_btnOnAction(ActionEvent actionEvent) throws SQLException {
        update_item_id=update_item_id_txt.getText();
        try{
        ProductDTO productDTO=productBO.searchProduct(update_item_id);
        if (productDTO != null) {
            hideTxtWhenEnterProductId(true);
            update_item_id_txt.setText(productDTO.getItem_id());
            update_desciption_txt.setText(productDTO.getItem_description());
            update_type_cmb.setValue(productDTO.getType());
            System.out.println(productDTO.getType());
            update_OneQtyPrice_txt.setText(String.valueOf(productDTO.getItem_one_qty_price()));
            update_onHandQty_txt.setText(String.valueOf(productDTO.getItem_on_hand_qty()));
        }else {
            new Alert(Alert.AlertType.WARNING, "No Customer found").show();
        }
    }catch (SQLException e){
        System.out.println(e);
        new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
    }
    }

    public void update_btnOnAction(ActionEvent actionEvent) {
        updateProductGetTextValues();
        try{
            boolean update= productBO.isUpdateProduct(new ProductDTO(update_item_id,updatedescription,updateType,updateHandQty,updateOneQtyPrice));

            if(update){
                new Alert(Alert.AlertType.CONFIRMATION, "Product Updated!").show();
                getAllProducts();
                setCellValueFactoryAllProducts();

            }
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
        }
    }

    public void delete_btnOnAction(ActionEvent actionEvent) {
        try{
            boolean delete=productBO.isDeleteProduct(update_item_id_txt.getText());
            if(delete){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted !").show();
                clearAndSetInvisible();
                getAllProducts();
                setCellValueFactoryAllProducts();
            }
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "something happened !").show();
        }
    }

    public void report_btnOnAction(ActionEvent actionEvent) {
    }

    void setCellValueFactory() {
        id_col.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        desc_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("onePrice"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    private void getValuesForType(String type) {
        try {
            ObservableList<ProductStatusTM> obList = FXCollections.observableArrayList();
            List<ProductDTO> podList = productBO.getItemStatus(type);

            for (ProductDTO productDTO : podList) {
                obList.add(new ProductStatusTM(
                        productDTO.getItem_id(),
                        productDTO.getItem_description(),
                        productDTO.getItem_one_qty_price(),
                        productDTO.setStatus(productDTO.getItem_on_hand_qty())
                ));
            }
            item_tbl.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }
    private void setDetails(String type){
        type_lbl.setText(type);
        item_tbl.getItems().clear();
        getValuesForType(type);
        setCellValueFactory();
    }
    public void txtFiledRegX(Pattern pattern ,TextField textField,String txt){
        boolean matches=pattern.matcher(txt).matches();
        if(matches){
            textField.setStyle("-fx-text-fill:black");
        }else{
            textField.setStyle("-fx-text-fill:red");
        }
    }

    public void addDescription_txtOnAction(ActionEvent actionEvent) {
        add_type_cmb.requestFocus();
    }

    public void addType_cmbOnAction(ActionEvent actionEvent) {
        addType=add_type_cmb.getValue();
    }

    public void addOnHandQty_txtOnAction(ActionEvent actionEvent) {
        add_oneQtyPrice_txt.requestFocus();
    }

    public void addQtyPrice_txtOnAction(ActionEvent actionEvent) {
        add_btn.requestFocus();
    }

    public void addOnHandQty_txtOnKeyReleaased(KeyEvent keyEvent) {
        try {
            addOnHandQty = Integer.parseInt(add_onHandQty_txt.getText());
        }catch (Exception e){
            add_onHandQty_txt.clear();
            new Alert(Alert.AlertType.ERROR, "Enter Only Numbers").show();
        }
        onHandQtyPattern=Pattern.compile("^[0-9]{1,6}$");
        txtFiledRegX(onHandQtyPattern,add_onHandQty_txt,String.valueOf(addOnHandQty));
    }

    public void addOneQtyPrice_txtOnKeyReleased(KeyEvent keyEvent) {
        try {
            addOneQtyPrice = Double.parseDouble(add_oneQtyPrice_txt.getText());
        }catch (Exception e){
            add_oneQtyPrice_txt.clear();
            new Alert(Alert.AlertType.ERROR, "Enter Only Descimal Numbers").show();
        }
        oneQtyPricePattern=Pattern.compile("^[0-9]{1,10}(.)[0-9]{2,2}$");
        txtFiledRegX(oneQtyPricePattern,add_oneQtyPrice_txt,String.valueOf(addOneQtyPrice));
    }

    public void updateDescription_txtOnAction(ActionEvent actionEvent) {
    }

    public void updateOnHandQtyOnAction(ActionEvent actionEvent) {
    }

    public void updateOnHandQty_txtOnKeyReleased(KeyEvent keyEvent) {
    }

    public void updateOneQtyPrice_txtOnKeyReleased(KeyEvent keyEvent) {
    }

    public void updateOneQtyPrice_txtOnAction(ActionEvent actionEvent) {
    }

    public void updateType_cmbOnAction(ActionEvent actionEvent) {
    }
    public void addProductGetTextValues(){
        addProductId=add_item_id_lbl.getText();
        addOneQtyPrice=Double.parseDouble(add_oneQtyPrice_txt.getText());
        addType=add_type_cmb.getValue();
        addDescription=add_description_txt.getText();
        addOnHandQty=Integer.parseInt(add_onHandQty_txt.getText());
    }
    private void loadTypes(){

        ObservableList<String> types= FXCollections.observableArrayList();
        types.add("Cpu");
        types.add("Processors");
        types.add("RAM");
        types.add("VGA");
        types.add("Monitors");
        types.add("Laptops");
        types.add("Mother Boards");
        types.add("Storage");
        types.add("Cooler");
        types.add("Casing");
        add_type_cmb.setItems(types);
        update_type_cmb.setItems(types);
    }
    public void generateNextProductId() {
        try {
            String nextId = productBO.generateNextProductId();
            add_item_id_lbl.setText(nextId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void setCellValueFactoryAllProducts() {
        item_id_col.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        onHandQty_col.setCellValueFactory(new PropertyValueFactory<>("on_hand_qty"));
        oneQtyPrice_col.setCellValueFactory(new PropertyValueFactory<>("one_qty_price"));
    }

    private void getAllProducts() {
        try {
            ObservableList<ProductTM> productTMS = FXCollections.observableArrayList();
            List<ProductDTO> podList = productBO.getAllProducts();

            for (ProductDTO productDTO : podList) {
                productTMS.add(new ProductTM(
                        productDTO.getItem_id(),
                        productDTO.getItem_description(),
                        productDTO.getType(),
                        productDTO.getItem_on_hand_qty(),
                        productDTO.getItem_one_qty_price()
                ));
            }
            product_tbl.setItems(productTMS);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }
    private void hideTxtWhenEnterProductId(boolean visible){
        update_desciption_txt.setVisible(visible);
        update_type_cmb.setVisible(visible);
        update_OneQtyPrice_txt.setVisible(visible);
        update_onHandQty_txt.setVisible(visible);
    }
    public void updateProductGetTextValues(){
        updateProductId=update_item_id_txt.getText();
        updateOneQtyPrice=Double.parseDouble(update_OneQtyPrice_txt.getText());
        updateType=update_type_cmb.getValue();
        updatedescription=update_desciption_txt.getText();
        updateHandQty=Integer.parseInt(update_onHandQty_txt.getText());
    }
    private void clearAndSetInvisible(){
        update_item_id_txt.clear();
        update_desciption_txt.clear();
        update_type_cmb.setValue("Choose");
        update_OneQtyPrice_txt.clear();
        update_onHandQty_txt.clear();
        hideTxtWhenEnterProductId(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTypes();
        generateNextProductId();
        setCellValueFactoryAllProducts();
        getAllProducts();
        hideTxtWhenEnterProductId(false);
    }
}
