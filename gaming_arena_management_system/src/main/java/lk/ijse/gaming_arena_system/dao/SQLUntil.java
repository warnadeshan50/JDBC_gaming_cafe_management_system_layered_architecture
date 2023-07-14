package lk.ijse.gaming_arena_system.dao;

import com.mysql.cj.xdevapi.PreparableStatement;
import lk.ijse.gaming_arena_system.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUntil {
    public static <Type>Type execute(String sql,Object...args) throws SQLException {
        Connection connection= DBConnection.getInstance().getConnection();
        PreparedStatement pstm=connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            pstm.setObject((i+1),args[i]);
        }
        if (sql.startsWith("SELECT")){
            return (Type)pstm.executeQuery();
        }else{
            return (Type)(Boolean)(pstm.executeUpdate()>0);
        }
    }
}
