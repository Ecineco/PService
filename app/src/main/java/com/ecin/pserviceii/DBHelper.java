package com.ecin.pserviceii;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private static Connection con;
    public DBHelper(){
        this.con = getConnection();
    }
    public static Connection getConnection(){
        try{
            Class cls = Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://cdb-eweriryb.gz.tencentcdb.com:10116/PServiceII?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = "7464Qing45+";
            con = DriverManager.getConnection(url,user,password);
        } catch(ClassNotFoundException e){
            //TODO
            e.printStackTrace();
        } catch(SQLException e){
            //TODO
            e.printStackTrace();
        } catch(Exception e){
            //TODO
            e.printStackTrace();
        }
        return con;
    }
}
