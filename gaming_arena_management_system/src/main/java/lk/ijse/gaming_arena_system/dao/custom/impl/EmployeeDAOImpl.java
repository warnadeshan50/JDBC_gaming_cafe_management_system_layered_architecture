package lk.ijse.gaming_arena_system.dao.custom.impl;

import lk.ijse.gaming_arena_system.dao.SQLUntil;
import lk.ijse.gaming_arena_system.dao.custom.EmployeeDAO;
import lk.ijse.gaming_arena_system.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    ResultSet rst;
    double salary;
    String employee_id,employee_address,employee_contact,employee_role,employee_name;

    @Override
    public boolean isSave(Employee entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("INSERT INTO employee(employee_id,  employee_name, attendent_id, employee_address,employee_contact, employee_role) VALUES(?, ?, ?, ?, ?, ?)",employee_id,employee_name,employee_address,employee_contact,employee_role,salary);
    }

    @Override
    public boolean isUpdate(Employee entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("UPDATE employee SET employee_name = ?, attendent_id = ?, employee_address = ?, employee_contact=?, employee_role = ? WHERE supplier_id = ?",employee_name,employee_address,employee_contact,employee_role,salary,employee_id);
    }

    @Override
    public boolean isDelete(String id) throws SQLException {
        return SQLUntil.execute("DELETE FROM employee WHERE employee_id = ?",id);
    }

    @Override
    public Employee search(String id) throws SQLException {
        rst=SQLUntil.execute("SELECT * FROM employee WHERE employee_id = ?");
        if (rst.next()){
            employee_id= rst.getString(1);
            employee_name= rst.getString(2);
            employee_address= rst.getString(3);
            employee_contact= rst.getString(4);
            employee_role= rst.getString(5);
            salary= rst.getDouble(6);
        }
        return null;
    }

    @Override
    public String generateNextId() throws SQLException {
        rst=SQLUntil.execute("SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1");
        if (rst.next()){
            return splitId(rst.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("EM0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return String.format("EM%04d",+id);
        }
        return "EM0001";
    }

    @Override
    public void createEntity(Employee entity) {
        employee_id=entity.getEmployee_id();
        employee_name=entity.getEmployee_name();
        employee_address=entity.getEmployee_address();
        employee_contact=entity.getEmployee_contact();
        employee_role=entity.getEmployee_role();
        salary=entity.getEmployee_salary();
    }

    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        rst=SQLUntil.execute("SELECT * FROM Customers");
        ArrayList<Employee>allEmployee=new ArrayList<>();

        while (rst.next()){
            allEmployee.add(new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6)
            ));
        }
        return allEmployee;
    }

    @Override
    public  ArrayList<String> getAllIds() throws SQLException {
        rst=SQLUntil.execute("SELECT employee_id FROM employee");
        ArrayList<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(rst.getString(1));
        }
        return ids;
    }
}
