package lk.ijse.gaming_arena_system.dao.custom;

import lk.ijse.gaming_arena_system.dao.CrudDAO;
import lk.ijse.gaming_arena_system.dto.OrderDetailsDTO;
import lk.ijse.gaming_arena_system.entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {
    public  boolean save( List<OrderDetail> cartDTOList) throws SQLException;
}
