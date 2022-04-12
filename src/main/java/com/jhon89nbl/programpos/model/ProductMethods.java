package com.jhon89nbl.programpos.model;

import com.jhon89nbl.programpos.Static.QueryDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;


import javax.imageio.ImageIO;

import static com.jhon89nbl.programpos.Static.QueryDB.CONSULT_LOGIN;


public class ProductMethods {
    private final User user = User.getINSTANCE();
    private final DataBaseConnection dataBaseConnection;
    public ProductMethods(){this.dataBaseConnection= new DataBaseConnection();}

   public List<String> fieldsEmpty(Product product){
        //se valida que ningun campo este vacio y se retorna lista
        List<String> fields = new ArrayList<>();
        if(product.getCode() == -1){
            fields.add("Codigo");
        }if (product.getName().trim().isEmpty()) {
            fields.add("Nombre Producto");
        }if(product.getDescription().trim().isEmpty()){
            fields.add("Descripcion");
        }if (product.getCategory().isEmpty()) {
            fields.add("Categoria");
        }if(product.getProvider().isEmpty()){
            fields.add("Proveedor");
        }if (product.getAmount()==0) {
            fields.add("Cantidad");
        }if(product.getCost()==0){
            fields.add("Costo");
        }if(product.getSalePrice()==0){
            fields.add("Precio Venta");
       }
    return fields ;
    }

    public ObservableList<Product> saveProducts(ObservableList<Product> products) throws SQLException {
        //se crea lista
        ObservableList<Product> fails= FXCollections.observableArrayList();
        List<Object> productRepeat= new ArrayList<>();
        Connection connection = null;
        //se crea for para recorrrer la lista de productos
            try {
                //se crea conexion a la base de datos
                dataBaseConnection.connectionDB();
                connection = dataBaseConnection.getConnection();
                    for (Product product: products) {
                        if (validProduct(product)) {
                            // se convierte la imagen a bytes
                            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(product.getPhoto().getImage(), null);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
                            byte[] photo = byteArrayOutputStream.toByteArray();
                            byteArrayOutputStream.close();
                            //se carga a la tabla de productos
                            PreparedStatement preparedStatement = connection.prepareStatement(QueryDB.ADD_PRODUCTS);
                            preparedStatement.setLong(1, (product.getCode()));
                            preparedStatement.setString(2, product.getName());
                            preparedStatement.setString(3, product.getDescription());
                            preparedStatement.setDouble(4, product.getSalePrice());
                            preparedStatement.setBoolean(5, product.isIva());

                            preparedStatement.setString(6, product.getCategory());
                            if (product.isChargePhoto()) {
                                preparedStatement.setBytes(7, photo);
                            } else {
                                preparedStatement.setBytes(7, null);
                            }
                            preparedStatement.setInt(8, user.getIdUser());
                            preparedStatement.executeUpdate();
                            String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

                            //se carga a la tabla de store
                            preparedStatement = connection.prepareStatement(QueryDB.STORE);
                            preparedStatement.setString(1, product.getProvider());
                            preparedStatement.setLong(2, product.getCode());
                            preparedStatement.setString(3, timeStamp);
                            preparedStatement.setDouble(4, product.getCost());
                            preparedStatement.setInt(5, product.getAmount());
                            preparedStatement.setFloat(6, product.getIvaPercent());
                            preparedStatement.executeUpdate();
                            connection.commit();

                        } else {
                            fails.add(product);
                        }


                    }
            } catch (SQLException | IOException throwables) {
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

    public Boolean validProduct(Product product) throws SQLException{
        boolean isvalid= false;
        // Lista para validar si ya existe eñ campo
        List<Object> productRepeat= new ArrayList<>();
        //se crea variable de conexion y stament y se inicializan en null
        Connection connection = null;
        Statement statement = null;
        try {
            dataBaseConnection.connectionDB();
            connection = dataBaseConnection.getConnection();
            if(connection != null){
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(String.format(QueryDB.REPEATED_PRODUCTS, product.getCode(), product.getName()));
                if (resultSet.next()) {
                    for (int i = 1; i < resultSet.getMetaData().getColumnCount() + 1; i++) {
                        productRepeat.add(resultSet.getObject(i));
                        System.out.println(resultSet.getObject(i));
                    }
                }
                if(productRepeat.isEmpty()){
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
