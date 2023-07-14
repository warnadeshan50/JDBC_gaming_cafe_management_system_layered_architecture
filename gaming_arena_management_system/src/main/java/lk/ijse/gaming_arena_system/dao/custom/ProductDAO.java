package lk.ijse.gaming_arena_system.dao.custom;

import lk.ijse.gaming_arena_system.dao.CrudDAO;
import lk.ijse.gaming_arena_system.entity.OrderDetail;
import lk.ijse.gaming_arena_system.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductDAO extends CrudDAO<Product> {
    public List<Product> getItemStatus(String type) throws SQLException;
    public ArrayList<String> getIds() throws SQLException;
    public boolean updateQty(OrderDetail entity) throws SQLException;
    public boolean updateQty(List<OrderDetail> cartDTOList) throws SQLException;

}
