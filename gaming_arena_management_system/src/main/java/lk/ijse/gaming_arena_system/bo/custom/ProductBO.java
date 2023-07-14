package lk.ijse.gaming_arena_system.bo.custom;

import lk.ijse.gaming_arena_system.bo.SuperBO;
import lk.ijse.gaming_arena_system.dto.CustomerDTO;
import lk.ijse.gaming_arena_system.dto.ProductDTO;
import lk.ijse.gaming_arena_system.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductBO extends SuperBO {
    public boolean isSaveProduct(ProductDTO dto) throws SQLException;
    public boolean isUpdateProduct(ProductDTO dto) throws SQLException;
    public boolean isDeleteProduct(String id) throws SQLException;
    public ProductDTO searchProduct(String id) throws SQLException;
    public String generateNextProductId() throws SQLException;
    public ArrayList<ProductDTO> getAllProducts() throws SQLException;
    public List<ProductDTO> getItemStatus(String type) throws SQLException;
}
