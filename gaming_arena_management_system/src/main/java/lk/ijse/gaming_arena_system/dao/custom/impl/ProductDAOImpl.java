package lk.ijse.gaming_arena_system.dao.custom.impl;

import lk.ijse.gaming_arena_system.dao.SQLUntil;
import lk.ijse.gaming_arena_system.dao.custom.ProductDAO;
import lk.ijse.gaming_arena_system.entity.OrderDetail;
import lk.ijse.gaming_arena_system.entity.Product;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    ResultSet rst;
    String item_id,desciption,type;
    int onHandQty;
    Double oneQtyPrice;

    @Override
    public boolean isSave(Product entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("INSERT INTO Item(item_id, item_description,type, item_on_hand_qty, item_one_qty_price) VALUES(?, ?, ?, ?, ?)",item_id,desciption,type,onHandQty,oneQtyPrice);
    }

    @Override
    public boolean isUpdate(Product entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("UPDATE Item SET item_description = ?, type = ?,item_on_hand_qty = ?, item_one_qty_price = ? WHERE item_id = ?",desciption,type,onHandQty,oneQtyPrice,item_id);
    }

    @Override
    public boolean isDelete(String id) throws SQLException {
        return SQLUntil.execute("DELETE FROM item WHERE item_id = ?",id);
    }

    @Override
    public Product search(String id) throws SQLException {
        rst=SQLUntil.execute("SELECT * FROM Item WHERE item_id = ?",id);

        if(rst.next()) {
            String code = rst.getString(1);
            String description = rst.getString(2);
            String type=rst.getString(3);
            Integer on_hand_qty = rst.getInt(4);
            Double one_qty_price = rst.getDouble(5);

            return new Product(code, description,type,on_hand_qty,one_qty_price);
        }
        return null;
    }

    @Override
    public String generateNextId() throws SQLException {
        rst=SQLUntil.execute("SELECT item_id FROM item ORDER BY item_id DESC LIMIT 1");
        if(rst.next()) {
            return splitId(rst.getString(1));
        }
        return splitId(null);
    }

    @Override
    public ArrayList<Product> getAll() throws SQLException {
        rst= SQLUntil.execute( "SELECT * FROM Item ");
        ArrayList<Product> data = new ArrayList<>();
        while (rst.next()) {
            data.add(new Product(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5)

            ));
        }
        return data;
    }

    @Override
    public List<Product> getItemStatus(String type) throws SQLException {
        rst= SQLUntil.execute( "SELECT * FROM Item WHERE type = ?",type);

        List<Product> data=new ArrayList<>();

        while(rst.next()) {
            data.add(new Product(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5)
            ));
        }
        return data;
    }

    @Override
    public ArrayList<String> getIds() throws SQLException {
        ArrayList<String>ids=new ArrayList<>();
        rst=SQLUntil.execute("SELECT item_id FROM item");
        while (rst.next()) {
            ids.add(rst.getString(1));
        }
        return ids;
    }

    @Override
    public boolean updateQty(OrderDetail entity) throws SQLException {
        return SQLUntil.execute("UPDATE item SET item_on_hand_qty = (item_on_hand_qty - ?) WHERE item_id = ?",entity.getQty(),entity.getItem_id());
    }

    @Override
    public boolean updateQty(List<OrderDetail> cartList) throws SQLException {
        for (OrderDetail entity : cartList) {
            if(!updateQty(entity)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void createEntity(Product entity){
        item_id=entity.getItem_id();
        desciption=entity.getItem_description();
        type=entity.getType();
        onHandQty=entity.getItem_on_hand_qty();
        oneQtyPrice=entity.getItem_one_qty_price();
    }
    @Override
    public String splitId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("IT0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return String.format("IT%04d",+id);
        }
        return "IT0001";
    }
}
