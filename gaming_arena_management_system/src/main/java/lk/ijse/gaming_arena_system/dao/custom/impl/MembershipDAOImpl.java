package lk.ijse.gaming_arena_system.dao.custom.impl;

import lk.ijse.gaming_arena_system.dao.SQLUntil;
import lk.ijse.gaming_arena_system.dao.custom.MembershipDAO;
import lk.ijse.gaming_arena_system.entity.Membership;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MembershipDAOImpl implements MembershipDAO {
    String member_id,customer_id,type;
    double package_price;
    ResultSet rst;
    @Override
    public boolean isSave(Membership entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("INSERT INTO membership(member_id,customer_id,type,package_price) VALUES(? ,? ,? ,? )",member_id,customer_id,type,package_price);
    }

    @Override
    public boolean isUpdate(Membership entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("UPDATE membership SET customer_id = ?, type = ?, package_price= ?  WHERE member_id = ?",customer_id,type,package_price,member_id);
    }

    @Override
    public boolean isDelete(String id) throws SQLException {
        return SQLUntil.execute("DELETE FROM membership WHERE member_id = ?",id);
    }

    @Override
    public Membership search(String id) throws SQLException {
        rst=SQLUntil.execute("SELECT * FROM membership WHERE member_id = ?",id);
        if(rst.next()){
           return new Membership(
            rst.getString(1),
            rst.getString(2),
            rst.getString(3),
            rst.getDouble(4)
            );
        }
        return null;
    }

    @Override
    public String generateNextId() throws SQLException {
        rst=SQLUntil.execute("SELECT member_id FROM membership ORDER BY member_id DESC LIMIT 1");
        if(rst.next()) {
            return splitId(rst.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("MS0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return String.format("MS%04d",+id);
        }
        return "MS0001";
    }

    @Override
    public void createEntity(Membership entity) {
        member_id=entity.getMember_id();
        customer_id=entity.getCustomer_id();
        type=entity.getType();
        package_price=entity.getPackage_price();
    }

    @Override
    public ArrayList<Membership> getAll() throws SQLException {
        rst=SQLUntil.execute("SELECT * FROM membership");
        ArrayList<Membership> memberships=new ArrayList<>();
        while (rst.next()) {
            memberships.add(new Membership(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            ));
        }
            return memberships;
    }

    @Override
    public Membership searchByCustomerId(String id) throws SQLException {
        rst = SQLUntil.execute("SELECT * FROM membership WHERE customer_id = ?", id);

        if (rst.next()) {
            return new Membership(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            );
        }
        return null;
    }
}
