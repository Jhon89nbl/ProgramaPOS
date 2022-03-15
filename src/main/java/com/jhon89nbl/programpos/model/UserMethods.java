package com.jhon89nbl.programpos.model;




import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import static com.jhon89nbl.programpos.Static.QueryDB.CONSULT_LOGIN;

public class UserMethods {
    private final DataBaseConnection dataBaseConnection;
    private final User user;

    public UserMethods(){
        this.user = User.getINSTANCE();
        this.dataBaseConnection = new DataBaseConnection();
    }

    public boolean fieldsEmpty(String user,String pass) {

        return !user.trim().isEmpty() && !pass.trim().isEmpty();
    }
    public boolean validUSer(String user,String pass) throws SQLException {

        List<Object> listUser = new ArrayList<>();
            Connection connection = null;
        Statement statement = null;
        boolean validuser = false;
        try {
            connection = dataBaseConnection.getConnection();
            if (connection != null) {
                 statement= connection.createStatement();
                ResultSet resultSet = statement.executeQuery(String.format(CONSULT_LOGIN, user, pass));
                if (resultSet.next()) {
                    for (int i = 1; i < resultSet.getMetaData().getColumnCount(); i++) {
                        listUser.add(resultSet.getObject(i));
                    }
                }
                if (!listUser.isEmpty()) {
                    chargerUser(listUser);
                    connection.close();

                    validuser = true;
                }
            }
        }catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                dataBaseConnection.disconnectDB();
                assert statement != null;
                statement.close();
            }
        }

        return validuser;
    }

    private void chargerUser(List<Object> listUser) {
        user.setIdUser(Integer.parseInt(listUser.get(0).toString()));
        user.setName(listUser.get(1).toString());
        user.setLastName(listUser.get(2).toString());
        user.setUser(listUser.get(3).toString());
        user.setIdRol(Integer.parseInt(listUser.get(4).toString()));
    }
}
