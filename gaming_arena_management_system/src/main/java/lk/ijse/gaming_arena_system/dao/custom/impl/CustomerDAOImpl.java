package lk.ijse.gaming_arena_system.dao.custom.impl;

import lk.ijse.gaming_arena_system.dao.SQLUntil;
import lk.ijse.gaming_arena_system.dao.custom.CustomerDAO;
import lk.ijse.gaming_arena_system.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    static String cust_id,emp_id,name,address,contact,email;
    ResultSet rst;
    @Override
    public boolean isSave(Customer entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("INSERT INTO customer(customer_id,employee_id, customer_name, customer_address, customer_contact,customer_email) VALUES(?, ?, ?, ?, ?, ?)",cust_id,emp_id,name,address,contact,email);
    }

    @Override
    public boolean isUpdate(Customer entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("UPDATE customer SET employee_id = ?, customer_name = ?, customer_address = ?, customer_contact=?, customer_email = ? WHERE customer_id = ?",emp_id,name,address,contact,email,cust_id);
    }

    @Override
    public boolean isDelete(String id) throws SQLException {
        return SQLUntil.execute("DELETE FROM customer WHERE customer_id = ?",id);
    }

    @Override
    public Customer search(String id) throws SQLException {
        rst=SQLUntil.execute("SELECT * FROM Customer WHERE customer_id = ?",id);
        if(rst.next()){
            String customer_id = rst.getString(1);
            String employee_id = rst.getString(2);
            String customer_name = rst.getString(3);
            String customer_address = rst.getString(4);
            String customer_contact = rst.getString(5);
            String customer_email = rst.getString(6);
            return new Customer(customer_id, employee_id, customer_name, customer_address, customer_contact, customer_email);
        }
        return null;
    }

    @Override
    public String generateNextId() throws SQLException {
        rst=SQLUntil.execute("SELECT customer_id FROM customer ORDER BY customer_id DESC LIMIT 1");
        if(rst.next()) {
            return splitId(rst.getString(1));
        }
        return splitId(null);
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException {
        rst=SQLUntil.execute("SELECT * FROM Customer");
        ArrayList<Customer> data = new ArrayList<>();
        while (rst.next()) {
            data.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
        }
        return data;
    }
    @Override
    public void createEntity(Customer entity){
        cust_id=entity.getCustomer_id();
        emp_id=entity.getEmployee_id();
        name=entity.getCustomer_name();
        address=entity.getCustomer_address();
        contact=entity.getCustomer_contact();
        email=entity.getCustomer_email();
    }
    @Override
    public String splitId(String currentCustomerId) {
        if(currentCustomerId != null) {
            String[] strings = currentCustomerId.split("CU0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return String.format("CU%04d",+id);
        }
        return "CU0001";
    }

    @Override
    public ArrayList<String> gellAllIds() throws SQLException {
        rst=SQLUntil.execute("SELECT customer_id FROM customer");
        ArrayList<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(rst.getString(1));
        }
        return ids;
    }
}
