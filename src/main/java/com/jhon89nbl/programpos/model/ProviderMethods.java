package com.jhon89nbl.programpos.model;

import com.jhon89nbl.programpos.Static.QueryDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProviderMethods {
    private final DataBaseConnection dataBaseConnection;
    public ProviderMethods(){
        dataBaseConnection = new DataBaseConnection();
    }

    public ObservableList<Provider> listComboProvider() throws SQLException {
        //se crea lista para cargar las categorias
        ObservableList<Provider> providers = FXCollections.observableArrayList();
        //Se alista la conexion
        Connection connection = null;
        Statement statement = null;
        //Se genera la conexion
        try {
            dataBaseConnection.connectionDB();
            connection = dataBaseConnection.getConnection();
            if (connection!= null){
                statement = connection.createStatement();
                //se ejecuta la consulta
                ResultSet rs = statement.executeQuery(QueryDB.CONSULT_PROVIDER_COMBO);
                while (rs.next()){
                    providers.add(new Provider(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
                    ));
                }
            }else {
                //si no hay conexion se muestra mensaje
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Error conexion Base de Datos");
                alert.showAndWait();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //se cierran las conexiones
            if(connection != null){
                connection.close();
                dataBaseConnection.disconnectDB();
                assert statement != null;
                statement.close();
            }
        }
        //se retorna la lista
        return providers;
    }

    public List<String> fieldsEmpty(Provider provider) {
        List<String> fieldsEmpty = new ArrayList<>();
        if (provider.getName().trim().isEmpty()) {
            fieldsEmpty.add("Nombre Proveedor");
        }
        if (provider.getNit().trim().isEmpty()) {
            fieldsEmpty.add("NIT");
        }
        if (provider.getAdress().trim().isEmpty()) {
            fieldsEmpty.add("Direccion");
        }
        if (provider.getPhone().trim().isEmpty()) {
            fieldsEmpty.add(("Telefono"));
        }
        if (provider.getEmail().trim().isEmpty()) {
            fieldsEmpty.add("Email");
        }
        if (provider.getChannel().isEmpty()) {
            fieldsEmpty.add("Metodo Pedido");
        }
        if (provider.getDay().isEmpty()) {
            fieldsEmpty.add("Dias pedido");
        }
        return fieldsEmpty;
    }
}
