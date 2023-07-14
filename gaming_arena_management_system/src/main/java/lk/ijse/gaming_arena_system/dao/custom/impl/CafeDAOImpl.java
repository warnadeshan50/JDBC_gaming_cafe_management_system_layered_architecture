package lk.ijse.gaming_arena_system.dao.custom.impl;

import lk.ijse.gaming_arena_system.dao.SQLUntil;
import lk.ijse.gaming_arena_system.dao.custom.CafeDAO;
import lk.ijse.gaming_arena_system.entity.CafeBill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CafeDAOImpl implements CafeDAO {
    String customer_id,bill_id,date;
    ResultSet rst;
    @Override
    public boolean isSave(CafeBill entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("INSERT INTO cafe_bill(bill_id, customer_id,date) VALUES (?, ?, ?)",bill_id,customer_id,date);
    }

    @Override
    public boolean isUpdate(CafeBill entity) {
        return false;
    }

    @Override
    public boolean isDelete(String id) {
        return false;
    }

    @Override
    public CafeBill search(String id) {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException {
        rst=SQLUntil.execute("SELECT bill_id FROM cafe_bill ORDER BY bill_id DESC LIMIT 1");
        if(rst.next()) {
            return splitId(rst.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("CB0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return  String.format("CB%04d",+id);
        }
        return "CB0001";
    }

    @Override
    public void createEntity(CafeBill entity) {
        customer_id=entity.getCustomer_id();
        bill_id=entity.getBill_id();
        date=entity.getDate();
    }

    @Override
    public ArrayList<CafeBill> getAll() {
        return null;
    }


}
