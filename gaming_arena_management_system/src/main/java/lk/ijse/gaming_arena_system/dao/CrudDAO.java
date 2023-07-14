package lk.ijse.gaming_arena_system.dao;

import lk.ijse.gaming_arena_system.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <Type> extends SuperDAO {
    public boolean isSave(Type entity) throws SQLException;
    public boolean isUpdate(Type entity) throws SQLException;
    public boolean isDelete(String id) throws SQLException;
    public Type search(String id) throws SQLException;
    public String generateNextId() throws SQLException;
    public String splitId(String currentId);
    public void createEntity(Type entity);
    public ArrayList<Type> getAll() throws SQLException;

}
