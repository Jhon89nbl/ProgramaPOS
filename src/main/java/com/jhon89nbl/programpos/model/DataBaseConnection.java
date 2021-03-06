package com.jhon89nbl.programpos.model;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.jhon89nbl.programpos.Static.ConfigDB.*;

public class DataBaseConnection {
    private  Connection databaseLink;

    protected void connectionDB(){
        try {
            Class.forName(DRIVER);
            databaseLink = DriverManager.getConnection(URL_BASE_DATOS,USERADMIN,PASSADMIN);
            databaseLink.setAutoCommit(false);

        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void disconnectDB(){
        try {
            if(!databaseLink.isClosed()){
                databaseLink.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return databaseLink;
    }

}
