package lk.ijse.gaming_arena_system.dao.custom;

import lk.ijse.gaming_arena_system.dao.CrudDAO;
import lk.ijse.gaming_arena_system.entity.CafeComputers;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CafeComputerDAO extends CrudDAO<CafeComputers> {
    public ArrayList<String> getAllIds()throws SQLException;
}
