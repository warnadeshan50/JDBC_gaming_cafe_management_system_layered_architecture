package lk.ijse.gaming_arena_system.bo.custom.impl;

import lk.ijse.gaming_arena_system.bo.custom.ProductBO;
import lk.ijse.gaming_arena_system.dao.DAOFactory;
import lk.ijse.gaming_arena_system.dao.custom.ProductDAO;
import lk.ijse.gaming_arena_system.dto.ProductDTO;
import lk.ijse.gaming_arena_system.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBOImpl implements ProductBO {
    ProductDAO productDAO=(ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);
    @Override
    public boolean isSaveProduct(ProductDTO dto) throws SQLException {
        return productDAO.isSave(new Product(dto.getItem_id(),dto.getItem_description(),dto.getType(),dto.getItem_on_hand_qty(),dto.getItem_one_qty_price()));
    }

    @Override
    public boolean isUpdateProduct(ProductDTO dto) throws SQLException {
        return productDAO.isUpdate(new Product(dto.getItem_id(),dto.getItem_description(),dto.getType(),dto.getItem_on_hand_qty(),dto.getItem_one_qty_price()));
    }

    @Override
    public boolean isDeleteProduct(String id) throws SQLException {
        return productDAO.isDelete(id);
    }

    @Override
    public ProductDTO searchProduct(String id) throws SQLException {
        Product product=productDAO.search(id);
        if (product!=null){
          return new ProductDTO(product.getItem_id(),product.getItem_description(),product.getType(),product.getItem_on_hand_qty(),product.getItem_one_qty_price());
        }
        return null;
    }

    @Override
    public String generateNextProductId() throws SQLException {
        return productDAO.generateNextId();
    }

    @Override
    public ArrayList<ProductDTO> getAllProducts() throws SQLException {
        ArrayList<ProductDTO>productDTOS=new ArrayList<>();
        ArrayList<Product>products=productDAO.getAll();
        for(Product product:products) {
            productDTOS.add(new ProductDTO(product.getItem_id(),product.getItem_description(),product.getType(),product.getItem_on_hand_qty(),product.getItem_one_qty_price()));
        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> getItemStatus(String type) throws SQLException {
        List<Product>types=productDAO.getItemStatus(type);
        List<ProductDTO>productDTOTypes=new ArrayList<>();
        for (Product product:types){
            productDTOTypes.add(new ProductDTO(product.getItem_id(),product.getItem_description(),product.getType(),product.getItem_on_hand_qty(),product.getItem_one_qty_price()));
        }
        return productDTOTypes;
    }
}
