package lk.ijse.gaming_arena_system.dao.custom.impl;

import lk.ijse.gaming_arena_system.dao.SQLUntil;
import lk.ijse.gaming_arena_system.dao.custom.SupplierDAO;
import lk.ijse.gaming_arena_system.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    String supplier_id, supplier_name, company, supplier_contact,supplier_email;
    ResultSet rst;
    @Override
    public boolean isSave(Supplier entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("INSERT INTO supplier(supplier_id, supplier_name, company, supplier_contact,supplier_email) VALUES(?, ?, ?, ?, ?)",supplier_id, supplier_name, company, supplier_contact,supplier_email);
    }

    @Override
    public boolean isUpdate(Supplier entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("UPDATE supplier SET supplier_name = ?, company = ?, supplier_contact = ?,supplier_email  WHERE supplier_id = ?", supplier_name, company, supplier_contact,supplier_email,supplier_id);
    }

    @Override
    public boolean isDelete(String id) throws SQLException {
        return SQLUntil.execute("DELETE FROM supplier WHERE supplier_id = ?");
    }

    @Override
    public Supplier search(String id) throws SQLException {
        rst=SQLUntil.execute( "SELECT * FROM supplier WHERE supplier_id = ?",id);
        if(rst.next()){
            return new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    @Override
    public String generateNextId() throws SQLException {
        rst=SQLUntil.execute("SELECT supplier_id FROM supplier ORDER BY supplier_id DESC LIMIT 1");
        if(rst.next()) {
            return splitId(rst.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("SP0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return String.format("SP0%03d",+id);
        }
        return "SP0001";
    }

    @Override
    public void createEntity(Supplier entity) {
        supplier_id=entity.getSupplier_id();
        supplier_name=entity.getSupplier_name();
        company=entity.getSupplier_company();
        supplier_email=entity.getSupplier_email();
        supplier_contact=entity.getSupplier_contact();
    }

    @Override
    public ArrayList<Supplier> getAll() throws SQLException {
       ArrayList<Supplier>suppliers=new ArrayList<>();
        if(rst.next()){
            suppliers.add(new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return suppliers;
    }

    @Override
    public ArrayList<String> getIds() throws SQLException {
        ArrayList<String> ids=new ArrayList<>();
        rst=SQLUntil.execute("SELECT * FROM supplier");
        while(rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }
}
