package lk.ijse.gaming_arena_system.bo.custom;

import lk.ijse.gaming_arena_system.bo.SuperBO;
import lk.ijse.gaming_arena_system.dto.CustomerDTO;


import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    public boolean isSaveCustomer(CustomerDTO dto) throws SQLException;
    public boolean isUpdateCustomer(CustomerDTO dto) throws SQLException;
    public boolean isDeleteCustomer(String id) throws SQLException;
    public CustomerDTO searchCustomer(String id) throws SQLException;
    public String generateNextCustomerId() throws SQLException;
    public void createCustomerDTO(CustomerDTO dto);
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException;
    public ArrayList<String>getAllEmployeeId() throws SQLException;
}
