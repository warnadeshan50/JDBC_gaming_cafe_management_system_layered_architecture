package lk.ijse.gaming_arena_system.bo.custom.impl;

import lk.ijse.gaming_arena_system.bo.custom.CafeBO;
import lk.ijse.gaming_arena_system.dao.DAOFactory;
import lk.ijse.gaming_arena_system.dao.custom.CafeComputerDAO;
import lk.ijse.gaming_arena_system.dao.custom.CafeDAO;
import lk.ijse.gaming_arena_system.dao.custom.CafeDetailDAO;
import lk.ijse.gaming_arena_system.dao.custom.CustomerDAO;
import lk.ijse.gaming_arena_system.dto.CafeBillDTO;
import lk.ijse.gaming_arena_system.dto.CafeDetailDTO;
import lk.ijse.gaming_arena_system.dto.ComputerDTO;
import lk.ijse.gaming_arena_system.dto.CustomerDTO;
import lk.ijse.gaming_arena_system.entity.CafeBill;
import lk.ijse.gaming_arena_system.entity.CafeComputers;
import lk.ijse.gaming_arena_system.entity.CafeDetails;
import lk.ijse.gaming_arena_system.entity.Customer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CafeBOImpl implements CafeBO {
    CafeComputerDAO cafeComputerDAO=(CafeComputerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CAFE_COMPUTER);
    CafeDAO cafeDAO=(CafeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CAFE);
    CustomerDAO customerDAO=(CustomerDAO)DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    CafeDetailDAO cafeDetailDAO=(CafeDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CAFEDETAILS);
    @Override
    public boolean isSaveCafeComputer(ComputerDTO dto) throws SQLException {
        return cafeComputerDAO.isSave(new CafeComputers(dto.getComputer_id(), dto.getDescription(),dto.getOne_hour_price()));
    }

    @Override
    public boolean isUpdateCafeComputer(ComputerDTO dto) throws SQLException {
        return cafeComputerDAO.isUpdate(new CafeComputers(dto.getComputer_id(), dto.getDescription(),dto.getOne_hour_price()));
    }

    @Override
    public boolean isDeleteCafeComputer(String id) throws SQLException {
        return cafeComputerDAO.isDelete(id);
    }

    @Override
    public ComputerDTO searchCafeComputer(String id) throws SQLException {
        CafeComputers cafeComputer= cafeComputerDAO.search(id);
        if (cafeComputer!=null) {
            return new ComputerDTO(cafeComputer.getComputer_id(), cafeComputer.getComputer_description(), cafeComputer.getComputer_amount_per_hour());
        }
        return null;
    }

    @Override
    public ArrayList<ComputerDTO> getAllComputers() throws SQLException {
        ArrayList<ComputerDTO>computerDTOS=new ArrayList<>();
        ArrayList<CafeComputers>cafeComputers=cafeComputerDAO.getAll();
        for (CafeComputers cafeComputer:cafeComputers) {
            computerDTOS.add(new ComputerDTO(
                    cafeComputer.getComputer_id(),
                    cafeComputer.getComputer_description(),
                    cafeComputer.getComputer_amount_per_hour()
            ));
        }
        return computerDTOS;
    }

    @Override
    public String generateNextComputerId() throws SQLException {
        return cafeComputerDAO.generateNextId();
    }

    @Override
    public String generateNextCafeBillId() throws SQLException {
        return cafeDAO.generateNextId();
    }

    @Override
    public ArrayList<String> getCustomerIds() throws SQLException {
        ArrayList<String> allCustomerIds=new ArrayList<>();
        ArrayList<String>all=customerDAO.gellAllIds();
        for (String id:all) {
            allCustomerIds.add(id);
        }
        return allCustomerIds;
    }

    @Override
    public ArrayList<String> getCafeComputerIds() throws SQLException {
        ArrayList<String> allComputerIds=new ArrayList<>();
        ArrayList<String>all=cafeComputerDAO.getAllIds();
        for (String id:all) {
            allComputerIds.add(id);
        }
        return allComputerIds;
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
    public boolean isSaveCafeBill(CafeBillDTO dto) throws SQLException {
        LocalDate date_local=LocalDate.now();
        String date=String.valueOf(date_local);
        return cafeDAO.isSave(new CafeBill(dto.getBill_id(), dto.getCustomer_id(),date));
    }

    @Override
    public boolean isSaveCafeDetails(CafeDetailDTO dto) throws SQLException {
        return cafeDetailDAO.isSave(new CafeDetails(dto.getBill_id(),dto.getComputer_id(), dto.getStart_time(), dto.getEnd_time(), dto.getPayment() ));
    }
}
