package com.jhon89nbl.programpos.model;

import com.jhon89nbl.programpos.Static.QueryDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CategoryMethods {
    private final DataBaseConnection dataBaseConnection;
    private final User user = User.getINSTANCE();

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

    public List<String> fieldsEmpty(Category category){
        //se valida que ningun campo este vacio y se retorna lista
        List<String> fields = new ArrayList<>();
        if(category.getCategory().trim().isEmpty()){
            fields.add("Categoria");
        }if (category.getMaxProfit()==0) {
            fields.add("Ganancia Maxima");
        }if(category.getMinProfit()==0){
            fields.add("Ganancia Minima");
        }
        return fields ;
    }

    public ObservableList<Category> saveCategories(ObservableList<Category> categories) throws SQLException {
        //se crea lista
        ObservableList<Category> fails= FXCollections.observableArrayList();

        Connection connection = null;
        //se crea for para recorrrer la lista de productos
        try {
            //se crea conexion a la base de datos
            dataBaseConnection.connectionDB();
            connection = dataBaseConnection.getConnection();
            for (Category category: categories) {
                if (validCategory(category)) {
                    //se carga a la tabla de productos
                    PreparedStatement preparedStatement = connection.prepareStatement(QueryDB.ADD_CATEGORY);
                    preparedStatement.setString(1, category.getCategory());
                    preparedStatement.setFloat(2, category.getMinProfit());
                    preparedStatement.setFloat(3, category.getMaxProfit());
                    preparedStatement.setInt(4, user.getIdUser());
                    preparedStatement.executeUpdate();
                    connection.commit();

                } else {
                    fails.add(category);
                }


            }
        } catch (SQLException  throwables) {
            connection.rollback();
            throwables.printStackTrace();
        }finally {
            if(connection != null){
                connection.close();
                dataBaseConnection.disconnectDB();
            }
        }


        return fails;
    }

    public Boolean validCategory(Category category) throws SQLException{
        boolean isvalid= false;
        // Lista para validar si ya existe eñ campo
        List<Object> categoryRepeat= new ArrayList<>();
        //se crea variable de conexion y stament y se inicializan en null
        Connection connection = null;
        Statement statement = null;
        try {
            dataBaseConnection.connectionDB();
            connection = dataBaseConnection.getConnection();
            if(connection != null){
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(String.format(QueryDB.REPEATED_CATEGORIES, category.getCategory()));
                if (resultSet.next()) {
                    for (int i = 1; i < resultSet.getMetaData().getColumnCount() + 1; i++) {
                        categoryRepeat.add(resultSet.getObject(i));
                        System.out.println(resultSet.getObject(i));
                    }
                }
                if(categoryRepeat.isEmpty()){
                    isvalid = true;
                }else
                    isvalid= false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if(connection != null){
                connection.close();
                dataBaseConnection.disconnectDB();

                assert statement != null;
                statement.close();
            }
        }
        return isvalid;
    }

}
