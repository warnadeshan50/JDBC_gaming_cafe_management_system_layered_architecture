package lk.ijse.gaming_arena_system.bo.custom;

import lk.ijse.gaming_arena_system.bo.SuperBO;
import lk.ijse.gaming_arena_system.dao.DAOFactory;
import lk.ijse.gaming_arena_system.dao.custom.CafeComputerDAO;
import lk.ijse.gaming_arena_system.dao.custom.CafeDAO;
import lk.ijse.gaming_arena_system.dto.*;
import lk.ijse.gaming_arena_system.entity.CafeBill;
import lk.ijse.gaming_arena_system.entity.CafeDetails;

import java.sql.SQLException;
import java.util.ArrayList;


public interface CafeBO extends SuperBO {
    public boolean isSaveCafeComputer(ComputerDTO dto) throws SQLException;
    public boolean isUpdateCafeComputer(ComputerDTO dto) throws SQLException;
    public boolean isDeleteCafeComputer(String id) throws SQLException;
    public ComputerDTO searchCafeComputer(String id) throws SQLException;
    public ArrayList<ComputerDTO> getAllComputers() throws SQLException;
    public String generateNextComputerId() throws SQLException;
    public String generateNextCafeBillId() throws SQLException;
    public ArrayList<String> getCustomerIds()throws SQLException;
    public ArrayList<String> getCafeComputerIds()throws SQLException;
    public CustomerDTO searchCustomer(String id)throws SQLException;
    public boolean isSaveCafeBill(CafeBillDTO dto) throws SQLException;
    public boolean isSaveCafeDetails(CafeDetailDTO dto) throws SQLException;

}
