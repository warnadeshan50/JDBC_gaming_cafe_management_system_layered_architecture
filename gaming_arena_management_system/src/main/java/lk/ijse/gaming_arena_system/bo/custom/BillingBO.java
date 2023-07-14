package lk.ijse.gaming_arena_system.bo.custom;

import lk.ijse.gaming_arena_system.bo.SuperBO;
import lk.ijse.gaming_arena_system.dto.CustomerDTO;
import lk.ijse.gaming_arena_system.dto.OrderDTO;
import lk.ijse.gaming_arena_system.dto.OrderDetailsDTO;
import lk.ijse.gaming_arena_system.dto.ProductDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface BillingBO extends SuperBO {
    public ArrayList<String> getCustomerIds()throws SQLException;
    public String generateNextBillId() throws SQLException;
    public ArrayList<String> getItemIds()throws SQLException;
    public CustomerDTO searchCustomer(String id)throws SQLException;
    public ProductDTO searchproduct(String id)throws SQLException;
    public boolean isSaveOrder(OrderDTO dto)throws SQLException;
    public boolean isSaveOrderDetails(OrderDetailsDTO dto)throws SQLException;
    public boolean isupdateItemQty(OrderDetailsDTO dto )throws SQLException;
    public  boolean updateQty(List<OrderDetailsDTO> cartDTOList) throws SQLException;
    public  boolean save( List<OrderDetailsDTO> cartDTOList) throws SQLException;

}
