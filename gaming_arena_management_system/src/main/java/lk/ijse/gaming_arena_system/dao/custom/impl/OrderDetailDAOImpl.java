package lk.ijse.gaming_arena_system.dao.custom.impl;

import lk.ijse.gaming_arena_system.dao.SQLUntil;
import lk.ijse.gaming_arena_system.dao.custom.OrderDetailDAO;
import lk.ijse.gaming_arena_system.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    String order_id, item_id;
    int num_of_qty;
    double total_price;
    @Override
    public boolean isSave(OrderDetail entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("INSERT INTO orderdetails(order_id, item_id, qty, total_price) VALUES (?, ?, ?, ?)",order_id,item_id,num_of_qty,total_price);
    }

    @Override
    public boolean isUpdate(OrderDetail entity) {
        return false;
    }

    @Override
    public boolean isDelete(String id) throws SQLException {
        return false;
    }

    @Override
    public OrderDetail search(String id) {
        return null;
    }

    @Override
    public String generateNextId() {
        return null;
    }

    @Override
    public String splitId(String currentId) {
        return null;
    }

    @Override
    public void createEntity(OrderDetail entity) {
        order_id=entity.getOder_id();
        item_id=entity.getItem_id();
        num_of_qty=entity.getQty();
        total_price=entity.getTotal_price();
    }

    @Override
    public ArrayList<OrderDetail> getAll() {
        return null;
    }

    @Override
    public boolean save(List<OrderDetail> cartList) throws SQLException {
        for(OrderDetail entity :  cartList) {
            if(!isSave( entity)) {
                return false;
            }
        }
        return true;
    }


}
