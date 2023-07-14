package lk.ijse.gaming_arena_system.dao.custom.impl;

import lk.ijse.gaming_arena_system.dao.SQLUntil;
import lk.ijse.gaming_arena_system.dao.custom.DeliveryDAO;
import lk.ijse.gaming_arena_system.entity.Delivery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryDAOImpl implements DeliveryDAO {
    String delivery_id,order_id,delivery_name,location,contact;
    ResultSet rst;
    @Override
    public boolean isSave(Delivery entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("INSERT INTO delivery(delivery_id,order_id,delivery_name,location,contact) VALUES (?, ?, ?, ?, ?)",delivery_id,order_id,delivery_name,location,contact);
    }

    @Override
    public boolean isUpdate(Delivery entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("UPDATE delivery SET oder_id = ?, delivery_name = ?, location = ?, delivery_contact=? WHERE order_id = ?",delivery_id,delivery_name,location,contact,order_id);
    }

    @Override
    public boolean isDelete(String id) throws SQLException {
        return SQLUntil.execute("DELETE FROM delivery WHERE delivery_id = ?",id);
    }

    @Override
    public Delivery search(String id) throws SQLException {
        rst= SQLUntil.execute("SELECT * FROM delivery WHERE delivery_id = ?",id);
        if(rst.next()){
            delivery_id= rst.getString(1);
            order_id=rst.getString(2);
            delivery_name=rst.getString(3);
            location=rst.getString(4);
            contact=rst.getString(5);
            return new Delivery(delivery_id,order_id,delivery_name,location,contact);
        }
        return null;
    }

    @Override
    public String generateNextId() throws SQLException {
        rst=SQLUntil.execute("SELECT delivery_id FROM delivery ORDER BY delivery_id DESC LIMIT 1");
        if(rst.next()){
            return splitId(rst.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("DV0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return String.format("DV%04d",+id);
        }
        return "DV0001";
    }

    @Override
    public void createEntity(Delivery entity) {
        delivery_id=entity.getDelivery_id();
        order_id=entity.getOrder_id();
        delivery_name=entity.getDeliver_name();
        location=entity.getLocation();
        contact=entity.getContact();
    }

    @Override
    public ArrayList<Delivery> getAll() {
        return null;
    }

}
