package com.jhon89nbl.programpos.model;




import javafx.scene.control.Alert;

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
    //se inicalizan las variables
    public UserMethods(){
        this.user = User.getINSTANCE();
        this.dataBaseConnection = new DataBaseConnection();
    }
    //metodo para validar que los campos no esten vacios
    public boolean fieldsEmpty(String user,String pass) {

        return !user.trim().isEmpty() && !pass.trim().isEmpty();
    }
    //metodo para validar que el usuario y la clase esten registradas
    public boolean validUSer(String user,String pass) throws SQLException {
        //se crea lista para capturar la consulta
        List<Object> listUser = new ArrayList<>();
        //se crea variable de conexion y stament y se inicializan en null
        Connection connection = null;
        Statement statement = null;
        boolean validuser = false;
        try {
            //se crea coneccion a la bd
            dataBaseConnection.connectionDB();
            //se obtiene la conexion
            connection = dataBaseConnection.getConnection();
            //se valida que la conexion no sea nula
            if (connection != null) {
                //se crea statement y se ejecuta consulta
                statement= connection.createStatement();
                ResultSet resultSet = statement.executeQuery(String.format(CONSULT_LOGIN, user, pass));
                //se recorre el resultado de la consulta
                if (resultSet.next()) {
                    //se recorre el resultado de la consulta y se adiciona cada posicion a la lista
                    for (int i = 1; i < resultSet.getMetaData().getColumnCount()+1; i++) {
                        listUser.add(resultSet.getObject(i));
                    }
                }
                //si la lista no esta vacia se carga el usuario, se cierra la conexion
                if (!listUser.isEmpty()) {
                    chargerUser(listUser);
                    connection.close();
                    //se asigna a variable para retornar el valor de verdadero
                    validuser = true;
                }
            }else {
                //si no hay conexion se muestra mensaje
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Error conexion Base de Datos");
                alert.showAndWait();

            }
        }catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }finally {
            //se cierran las conexiones
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
