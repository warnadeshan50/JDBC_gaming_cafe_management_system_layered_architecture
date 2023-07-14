package lk.ijse.gaming_arena_system.dao.custom.impl;

import lk.ijse.gaming_arena_system.dao.SQLUntil;
import lk.ijse.gaming_arena_system.dao.custom.CafeComputerDAO;
import lk.ijse.gaming_arena_system.entity.CafeComputers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CafeComputerDAOImpl implements CafeComputerDAO {
    String computer_id,description;
    double per_hour_price;
    ResultSet rst;
    @Override
    public boolean isSave(CafeComputers entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("INSERT INTO cafe_computers (computer_id,description,one_hour_price) VALUES ( ?, ?, ?)",computer_id,description,per_hour_price);
    }

    @Override
    public boolean isUpdate(CafeComputers entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("UPDATE cafe_computers SET  description = ?, one_hour_price = ? WHERE computer_id = ?",description,per_hour_price,computer_id);
    }

    @Override
    public boolean isDelete(String id) throws SQLException {
        return SQLUntil.execute("DELETE FROM cafe_computers WHERE computer_id = ?",id);
    }

    @Override
    public CafeComputers search(String id) throws SQLException {
        rst=SQLUntil.execute("SELECT * FROM cafe_computers WHERE computer_id = ?",id);
        if (rst.next()){
            return new CafeComputers(
            rst.getString(1),
            rst.getString(2),
            rst.getDouble(3)
            );
        }
        return null;
    }

    @Override
    public String generateNextId() throws SQLException {
        rst=SQLUntil.execute("SELECT computer_id FROM cafe_computers ORDER BY computer_id DESC LIMIT 1");
        if(rst.next()) {
            return splitId(rst.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("GG0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return String.format("GG%04d",+id);
        }
        return "GG0001";
    }

    @Override
    public void createEntity(CafeComputers entity) {
        computer_id=entity.getComputer_id();
        description=entity.getComputer_description();
        per_hour_price=entity.getComputer_amount_per_hour();
    }

    @Override
    public ArrayList<CafeComputers> getAll() throws SQLException {
        ArrayList<CafeComputers>cafeComputers=new ArrayList<>();
        rst=SQLUntil.execute("SELECT * FROM cafe_computers");
        while (rst.next()){
            cafeComputers.add(new CafeComputers(
               rst.getString(1),
               rst.getString(2),
               rst.getDouble(3)
            ));
        }
        return cafeComputers;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ArrayList<String>computerIds=new ArrayList<>();
        rst=SQLUntil.execute("SELECT computer_id FROM cafe_computers" );
        while (rst.next()) {
            computerIds.add(rst.getString(1));
        }
        return computerIds;
    }
}
