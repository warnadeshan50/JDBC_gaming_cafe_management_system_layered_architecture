package lk.ijse.gaming_arena_system.bo.custom;

import lk.ijse.gaming_arena_system.bo.SuperBO;
import lk.ijse.gaming_arena_system.dto.CustomerDTO;
import lk.ijse.gaming_arena_system.dto.MembershipDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MembershipBO extends SuperBO {
    public ArrayList<String> getCustomerIds()throws SQLException;
    public String generateNextMembershipId() throws SQLException;
    public boolean isSaveMembership(MembershipDTO dto)throws SQLException;
    public CustomerDTO searchCustomer(String id)throws SQLException;
    public MembershipDTO searchMember(String id)throws SQLException;
    public MembershipDTO searchMemberByCustomerId(String id)throws SQLException;
    public ArrayList<MembershipDTO> getAllMemberships() throws SQLException;
    public boolean isUpdateMembership(MembershipDTO dto) throws SQLException;
    public boolean isDeleteMembership(String id)throws SQLException;
}
