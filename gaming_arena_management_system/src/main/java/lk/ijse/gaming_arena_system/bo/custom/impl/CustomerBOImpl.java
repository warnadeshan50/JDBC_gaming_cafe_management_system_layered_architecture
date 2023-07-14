package lk.ijse.gaming_arena_system.bo.custom.impl;

import lk.ijse.gaming_arena_system.bo.custom.CustomerBO;
import lk.ijse.gaming_arena_system.dao.DAOFactory;
import lk.ijse.gaming_arena_system.dao.custom.CustomerDAO;
import lk.ijse.gaming_arena_system.dao.custom.EmployeeDAO;
import lk.ijse.gaming_arena_system.dto.CustomerDTO;
import lk.ijse.gaming_arena_system.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    static String cust_id,emp_id,name,address,contact,email;
    CustomerDAO customerDAO= (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    EmployeeDAO employeeDAO=(EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public boolean isSaveCustomer(CustomerDTO dto) throws SQLException {
        createCustomerDTO(dto);
        return customerDAO.isSave(new Customer(cust_id,emp_id,name,address,contact,email)) ;
    }

    @Override
    public boolean isUpdateCustomer(CustomerDTO dto) throws SQLException {
        createCustomerDTO(dto);
        return customerDAO.isUpdate(new Customer(cust_id,emp_id,name,address,contact,email));
    }

    @Override
    public boolean isDeleteCustomer(String id) throws SQLException {
        return customerDAO.isDelete(id);
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException {
        Customer customer=customerDAO.search(id);
        if(customer!=null) {
            return new CustomerDTO(customer.getCustomer_id(), customer.getEmployee_id(), customer.getCustomer_name(), customer.getCustomer_address(), customer.getCustomer_contact(), customer.getCustomer_email());
        }
        return null;
    }

    @Override
    public String generateNextCustomerId() throws SQLException {
        return customerDAO.generateNextId();
    }

    @Override
    public void createCustomerDTO(CustomerDTO dto) {
        cust_id=dto.getCustomer_id();
        emp_id=dto.getEmployee_id();
        name= dto.getCustomer_name();
        address= dto.getCustomer_address();
        contact=dto.getCustomer_contact();
        email=dto.getCustomer_email();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
        ArrayList<CustomerDTO> allCustomers=new ArrayList<>();
        ArrayList<Customer>all=customerDAO.getAll();
        for (Customer customer:all) {
            allCustomers.add(new CustomerDTO(customer.getCustomer_id(),customer.getEmployee_id(),customer.getCustomer_name(),customer.getCustomer_address(),customer.getCustomer_contact(),customer.getCustomer_email()));
        }
        return allCustomers;
    }

    @Override
    public ArrayList<String> getAllEmployeeId() throws SQLException {
        ArrayList<String> allEmployeeIds=new ArrayList<>();
        ArrayList<String>all=employeeDAO.getAllIds();
        for (String id:all) {
            allEmployeeIds.add(id);
        }
        return allEmployeeIds;
    }
}
