package lk.ijse.gaming_arena_system.dao.custom;

import lk.ijse.gaming_arena_system.dao.CrudDAO;
import lk.ijse.gaming_arena_system.entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface SupplierDAO extends CrudDAO<Supplier> {
    public List<String> getIds() throws SQLException;
}
