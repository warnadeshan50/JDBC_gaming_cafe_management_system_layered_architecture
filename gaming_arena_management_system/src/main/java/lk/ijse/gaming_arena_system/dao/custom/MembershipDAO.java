package lk.ijse.gaming_arena_system.dao.custom;

import lk.ijse.gaming_arena_system.dao.CrudDAO;
import lk.ijse.gaming_arena_system.entity.Membership;

import java.sql.SQLException;

public interface MembershipDAO extends CrudDAO<Membership> {
    public Membership searchByCustomerId(String id)throws SQLException;
}
