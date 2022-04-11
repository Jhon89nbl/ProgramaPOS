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


public class ProductMethods {
    private final User user = User.getINSTANCE();
    private final DataBaseConnection dataBaseConnection;
    private Connection connection = null;
    private Statement statement = null;
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
        }if (product.getCategory()==-1) {
            fields.add("Categoria");
        }if(product.getProvider()==-1){
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
        //se crea for para recorrrer la lista de productos
        for (Product product:products) {
            try {
                //se crea conexion a la base de datos
                dataBaseConnection.connectionDB();
                connection = dataBaseConnection.getConnection();
                //se valida la conexion
                if(connection!=null){
                    statement = connection.createStatement();
                    //se valida que no exista el codigo ni el nombre del producto en la base de datos
                    ResultSet rs = statement.executeQuery(String.format(QueryDB.REPEATED_PRODUCTS,product.getCode(),product.getName()));
                    if(rs.next()){
                        for (int i =1; i < rs.getMetaData().getColumnCount()+1;i++){
                            productRepeat.add(rs.getObject(i));

                        }
                    }
                    // si no hay productos se guarda el producto en la base de datos
                    if (productRepeat.isEmpty()){
                        // se convierte la imagen a bytes
                        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(product.getPhoto().getImage(),null);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ImageIO.write(bufferedImage,"png",byteArrayOutputStream);
                        byte [] photo = byteArrayOutputStream.toByteArray();
                        byteArrayOutputStream.close();
                        //se carga a la tabla de productos
                        PreparedStatement preparedStatement = connection.prepareStatement(QueryDB.ADD_PRODUCTS);
                        preparedStatement.setLong(1, (product.getCode()));
                        preparedStatement.setString(2,product.getName());
                        preparedStatement.setString(3,product.getDescription());
                        preparedStatement.setDouble(4,product.getSalePrice());
                        preparedStatement.setBoolean(5,product.isIva());
                        preparedStatement.setInt(6,product.getCategory());
                        if(product.isChargePhoto()) {
                            preparedStatement.setBytes(7, photo);
                        }else {
                            preparedStatement.setBytes(7, null);
                        }
                        preparedStatement.setInt(8,user.getIdUser());
                        preparedStatement.executeUpdate();
                        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

                        //se carga a la tabla de store
                        preparedStatement = connection.prepareStatement(QueryDB.STORE);
                        preparedStatement.setInt(1,product.getProvider());
                        preparedStatement.setLong(2,product.getCode());
                        preparedStatement.setString(3,timeStamp);
                        preparedStatement.setDouble(4,product.getCost());
                        preparedStatement.setInt(5,product.getAmount());
                        preparedStatement.setFloat(6,product.getIvaPercent());
                         preparedStatement.executeUpdate();
                        connection.commit();

                    }else{
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
                    assert statement != null;
                    statement.close();
                }
            }
        }

        return fails;
    }

}
