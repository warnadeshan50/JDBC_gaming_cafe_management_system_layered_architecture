package lk.ijse.gaming_arena_system.dao.custom.impl;

import lk.ijse.gaming_arena_system.dao.SQLUntil;
import lk.ijse.gaming_arena_system.dao.custom.OrderDAO;
import lk.ijse.gaming_arena_system.entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    String order_id,customer_id,date,time;
    ResultSet rst;
    @Override
    public boolean isSave(Orders entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("INSERT INTO Orders(order_id,customer_id,date,time) VALUES (?, ?, ?, ?)",order_id,customer_id,date,time);
    }

    @Override
    public boolean isUpdate(Orders entity) {
        return false;
    }

    @Override
    public boolean isDelete(String id) throws SQLException {
        return false;
    }

    @Override
    public Orders search(String id) {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException {
        rst=SQLUntil.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");
        if(rst.next()) {
            return splitId(rst.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("OR0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return String.format("OR0%03d",+id);
        }
        return "OR0001";
    }

    @Override
    public void createEntity(Orders entity) {
        order_id=entity.getOrder_id();
        customer_id=entity.getCustomer_id();
        date=entity.getDate();
        time=entity.getTime();
    }

    @Override
    public ArrayList<Orders> getAll() {
        return null;
    }

}
