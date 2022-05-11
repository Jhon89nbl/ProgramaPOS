package com.jhon89nbl.programpos.model;

import com.jhon89nbl.programpos.Static.QueryDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProviderMethods {
    private final DataBaseConnection dataBaseConnection;
    private final User user = User.getINSTANCE();
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
    public ObservableList<Provider> saveProvider(ObservableList<Provider> providers) throws SQLException {
        boolean isvalid = false;
        ObservableList<Provider> providerRepeat = FXCollections.observableArrayList();
        Connection connection = null;
        Statement statement=null;
        try{
            dataBaseConnection.connectionDB();
            connection = dataBaseConnection.getConnection();
            if (connection!=null){
                statement = connection.createStatement();
                for(Provider tempProvider: providers) {
                    ResultSet resultSet = statement.executeQuery(String.format(QueryDB.CONSULT_PROVIDER_REPEAT,tempProvider.getName(),tempProvider.getNit()));
                    if (resultSet.next()){
                        isvalid = true;
                    }
                    if (isvalid){
                        providerRepeat.add(tempProvider);
                    }else {
                        String channel = "";
                        String day = "";
                        for (Provider.orderDay orderDay : tempProvider.getDay()) {
                            switch (orderDay) {
                                case LUNES -> day = day + "1,";
                                case MARTES -> day = day + "2,";
                                case MIERCOLES -> day = day + "3,";
                                case JUEVES -> day = day + "4,";
                                case VIERNES -> day = day + "5,";
                                case SABADO -> day = day + "6,";
                                case DOMINGO -> day = day + "7,";
                            }
                        }
                        day=day.replaceFirst(".$", "");
                        System.out.println(day);
                        for (Provider.channelOrder channelOrder : tempProvider.getChannel()) {
                            switch (channelOrder) {
                                case application -> channel = channel + "application,";
                                case phone -> channel = channel + "phone,";
                                case email -> channel = channel + "email,";
                                case whatsapp -> channel = channel + "whatsapp,";
                                case visitor -> channel = channel + "visitor,";
                                case other -> channel = channel + "other,";
                            }
                        }
                        channel = channel.replaceFirst(".$", "");
                        System.out.println(channel);
                        PreparedStatement preparedStatement = connection.prepareStatement(QueryDB.INSERT_PROVIDER);
                        preparedStatement.setString(1,tempProvider.getName());
                        preparedStatement.setString(2,tempProvider.getNit());
                        preparedStatement.setString(3,tempProvider.getPhone());
                        preparedStatement.setString(4,tempProvider.getAdress());
                        preparedStatement.setString(5, day);
                        preparedStatement.setString(6,tempProvider.getEmail());
                        preparedStatement.setString(7, channel);
                        preparedStatement.setInt(8,user.getIdUser());
                        preparedStatement.executeUpdate();
                        connection.commit();

                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }finally {
            if (connection != null) {
                connection.close();
                dataBaseConnection.disconnectDB();
                assert statement != null;
                statement.close();
            }
        }
        return providerRepeat;

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
