package lk.ijse.gaming_arena_system.dao.custom.impl;

import lk.ijse.gaming_arena_system.dao.SQLUntil;
import lk.ijse.gaming_arena_system.dao.custom.CafeDetailDAO;
import lk.ijse.gaming_arena_system.entity.CafeDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class CafeDetailDAOImpl implements CafeDetailDAO {
    String bill_id,computer_id;
    double start_time,end_time,payment;
    @Override
    public boolean isSave(CafeDetails entity) throws SQLException {
        createEntity(entity);
        return SQLUntil.execute("INSERT INTO cafe_detail(bill_id, computer_id,start_time, end_time,payment) VALUES (?, ?, ?, ?, ?)",bill_id,computer_id,start_time,end_time,payment);
    }

    @Override
    public boolean isUpdate(CafeDetails entity) {
        return false;
    }

    @Override
    public boolean isDelete(String Id) {
        return false;
    }

    @Override
    public CafeDetails search(String id) {
        return null;
    }

    @Override
    public String generateNextId() {
        return null;
    }

    @Override
    public String splitId(String currentId) {
        return null;
    }

    @Override
    public void createEntity(CafeDetails entity) {
        bill_id=entity.getBill_id();
        computer_id=entity.getComputer_id();
        start_time=entity.getStart_time();
        end_time=entity.getEnd_time();
        payment=entity.getPayment();

    }

    @Override
    public ArrayList<CafeDetails> getAll() {
        return null;
    }

}
