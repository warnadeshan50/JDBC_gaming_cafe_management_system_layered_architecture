package lk.ijse.gaming_arena_system.dao.custom.impl;

import lk.ijse.gaming_arena_system.dao.SQLUntil;
import lk.ijse.gaming_arena_system.dao.custom.SupplierItemDAO;
import lk.ijse.gaming_arena_system.entity.SupplierItem;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierItemDAOImpl implements SupplierItemDAO{

    String item_id, supplier_id,date;
    int number_of_unit;
    double one_qty_price;
    @Override
    public boolean isSave(SupplierItem entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("INSERT INTO supplier_items(item_id, supplier_id,number_of_unit,one_qty_price,date) VALUES (?, ?, ?, ?, ?)",supplier_id,item_id,number_of_unit,one_qty_price,date);
    }

    @Override
    public boolean isUpdate(SupplierItem entity) {
        return false;
    }

    @Override
    public boolean isDelete(String id) throws SQLException {
        return false;
    }

    @Override
    public SupplierItem search(String id) {
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
    public void createEntity(SupplierItem entity) {
        item_id=entity.getItem_id();
        supplier_id=entity.getSupplier_id();
        one_qty_price=entity.getOne_qty_price();
        number_of_unit=entity.getNumber_of_unit();
        date=entity.getDate();
    }

    @Override
    public ArrayList<SupplierItem> getAll() {
        return null;
    }

}
