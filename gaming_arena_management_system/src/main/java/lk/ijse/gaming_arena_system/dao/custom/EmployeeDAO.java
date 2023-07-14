package lk.ijse.gaming_arena_system.dao.custom;

import lk.ijse.gaming_arena_system.dao.CrudDAO;
import lk.ijse.gaming_arena_system.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO extends CrudDAO<Employee> {
    public  ArrayList<String> getAllIds() throws SQLException;
}
