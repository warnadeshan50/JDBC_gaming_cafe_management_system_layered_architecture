package lk.ijse.gaming_arena_system.bo.custom.impl;

import lk.ijse.gaming_arena_system.bo.custom.BillingBO;
import lk.ijse.gaming_arena_system.dao.DAOFactory;
import lk.ijse.gaming_arena_system.dao.custom.CustomerDAO;
import lk.ijse.gaming_arena_system.dao.custom.OrderDAO;
import lk.ijse.gaming_arena_system.dao.custom.OrderDetailDAO;
import lk.ijse.gaming_arena_system.dao.custom.ProductDAO;
import lk.ijse.gaming_arena_system.dto.CustomerDTO;
import lk.ijse.gaming_arena_system.dto.OrderDTO;
import lk.ijse.gaming_arena_system.dto.OrderDetailsDTO;
import lk.ijse.gaming_arena_system.dto.ProductDTO;
import lk.ijse.gaming_arena_system.entity.Customer;
import lk.ijse.gaming_arena_system.entity.OrderDetail;
import lk.ijse.gaming_arena_system.entity.Orders;
import lk.ijse.gaming_arena_system.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillingBOImpl implements BillingBO {
    CustomerDAO customerDAO=(CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    OrderDAO orderDAO=(OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    ProductDAO productDAO=(ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);
    OrderDetailDAO orderDetailDAO=(OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);
    @Override
    public ArrayList<String> getCustomerIds() throws SQLException {
        return customerDAO.gellAllIds();
    }

    @Override
    public String generateNextBillId() throws SQLException {
        return orderDAO.generateNextId();
    }

    @Override
    public ArrayList<String> getItemIds() throws SQLException {
        return productDAO.getIds();
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException {
        Customer customer = customerDAO.search(id);
        if (customer != null) {
            return new CustomerDTO(customer.getCustomer_id(), customer.getEmployee_id(), customer.getCustomer_name(), customer.getCustomer_address(), customer.getCustomer_contact(), customer.getCustomer_email());
        }
        return null;
    }

    @Override
    public ProductDTO searchproduct(String id) throws SQLException {
        Product product = productDAO.search(id);
        if (product != null) {
            return new ProductDTO(product.getItem_id(),product.getItem_description(),product.getType(),product.getItem_on_hand_qty(),product.getItem_one_qty_price());
        }
        return null;
    }

    @Override
    public boolean isSaveOrder(OrderDTO dto) throws SQLException {
        return orderDAO.isSave(new Orders(dto.getOrder_id(), dto.getCustomer_id(), dto.getDate(), dto.getTime()));
    }

    @Override
    public boolean isSaveOrderDetails(OrderDetailsDTO dto) throws SQLException {
        return orderDetailDAO.isSave(new OrderDetail(dto.getOder_id(),dto.getItem_id(),dto.getQty(),dto.getTotal_price()));
    }

    @Override
    public boolean isupdateItemQty(OrderDetailsDTO dto) throws SQLException {
        return productDAO.updateQty(new OrderDetail(dto.getOder_id(), dto.getItem_id(), dto.getQty(), dto.getTotal_price()));
    }

    @Override
    public boolean updateQty(List<OrderDetailsDTO> cartDTOList) throws SQLException {
        for (OrderDetailsDTO dto : cartDTOList) {
            if(!isupdateItemQty(dto)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean save(List<OrderDetailsDTO> cartDTOList) throws SQLException {
        for(OrderDetailsDTO dto :  cartDTOList) {
            if(!isSaveOrderDetails(dto)) {
                return false;
            }
        }
        return true;
    }
}
