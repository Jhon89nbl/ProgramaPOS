package com.jhon89nbl.programpos.model;

import com.jhon89nbl.programpos.Static.QueryDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CategoryMethods {
    private final DataBaseConnection dataBaseConnection;

    public CategoryMethods(){
         this.dataBaseConnection = new DataBaseConnection();
    }

    public ObservableList<Category> listComboCategories() throws SQLException {
        //se crea lista para cargar las categorias
        ObservableList<Category> categorys = FXCollections.observableArrayList();
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
                ResultSet rs = statement.executeQuery(QueryDB.CONSULT_CATEGORY_COMBO);
                while (rs.next()){
                    categorys.add(new Category(
                       rs.getInt(1),
                       rs.getString(2),
                       rs.getFloat(3),
                       rs.getFloat(4)
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
        return  categorys;
    }
}
