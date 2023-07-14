package lk.ijse.gaming_arena_system.bo.custom.impl;

import lk.ijse.gaming_arena_system.bo.custom.MembershipBO;
import lk.ijse.gaming_arena_system.dao.DAOFactory;
import lk.ijse.gaming_arena_system.dao.custom.CustomerDAO;
import lk.ijse.gaming_arena_system.dao.custom.MembershipDAO;
import lk.ijse.gaming_arena_system.dto.CustomerDTO;
import lk.ijse.gaming_arena_system.dto.MembershipDTO;
import lk.ijse.gaming_arena_system.entity.Customer;
import lk.ijse.gaming_arena_system.entity.Membership;

import java.sql.SQLException;
import java.util.ArrayList;

public class MembershipBOImpl implements MembershipBO {
    MembershipDAO membershipDAO=(MembershipDAO)DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MEMBERSHIP);
    CustomerDAO customerDAO= (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public ArrayList<String> getCustomerIds() throws SQLException {
        return customerDAO.gellAllIds();
    }

    @Override
    public String generateNextMembershipId() throws SQLException {
        return membershipDAO.generateNextId();
    }

    @Override
    public boolean isSaveMembership(MembershipDTO dto) throws SQLException {
        return membershipDAO.isSave(new Membership(dto.getMember_id(),dto.getCustomer_id(),dto.getType(),dto.getPackage_price()));
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
    public MembershipDTO searchMember(String id) throws SQLException {
        Membership membership=membershipDAO.search(id);
        if(membership!=null){
            return new MembershipDTO(membership.getMember_id(),membership.getCustomer_id(),membership.getType(),membership.getPackage_price());
        }
        return null;
    }

    @Override
    public MembershipDTO searchMemberByCustomerId(String id) throws SQLException {
        Membership membership=membershipDAO.searchByCustomerId(id);
        if(membership!=null){
            return new MembershipDTO(membership.getMember_id(),membership.getCustomer_id(),membership.getType(),membership.getPackage_price());
        }
        return null;
    }

    @Override
    public ArrayList<MembershipDTO> getAllMemberships() throws SQLException {
        ArrayList<Membership>memberships=membershipDAO.getAll();
        ArrayList<MembershipDTO>membershipDTOS=new ArrayList<>();
        for (Membership membership:memberships) {
            membershipDTOS.add(new MembershipDTO(membership.getMember_id(),membership.getCustomer_id(),membership.getType(),membership.getPackage_price()));
        }
        return membershipDTOS;
    }

    @Override
    public boolean isUpdateMembership(MembershipDTO dto) throws SQLException {
        return membershipDAO.isUpdate(new Membership(dto.getMember_id(), dto.getCustomer_id(), dto.getType(), dto.getPackage_price()));
    }

    @Override
    public boolean isDeleteMembership(String id) throws SQLException {
        return membershipDAO.isDelete(id);
    }
}
