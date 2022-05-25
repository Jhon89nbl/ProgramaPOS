package com.jhon89nbl.programpos.model;

import com.jhon89nbl.programpos.Static.QueryDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javax.imageio.ImageIO;


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
        // Lista para validar si ya existe en campo
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

    public  ObservableList <Product> searchProducts(String searchProduct) throws SQLException {
        ObservableList<Product> products = FXCollections.observableArrayList();
        Connection connection = null;
        try {
            dataBaseConnection.connectionDB();
            connection = dataBaseConnection.getConnection();
            if(connection!=null){
                PreparedStatement preparedStatement = connection.prepareStatement(QueryDB.SEARCH_PRODUCTS);
                preparedStatement.setString(1,"%" + searchProduct+ "%");
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    Product product = new Product();
                    product.setCode(resultSet.getInt("code"));
                    product.setName(resultSet.getString("name"));
                    product.setDescription(resultSet.getString("description"));
                    product.setSalePrice(resultSet.getFloat("sale_price"));
                    boolean iva= resultSet.getInt("iva") == 1;
                    product.setIva(iva);
                    product.setCategory(String.valueOf(resultSet.getInt("categoria_idcategoria")));
                    byte[] byteImage = null;
                    Blob blob =resultSet.getBlob("photo");
                    if(blob!= null){
                        byteImage = blob.getBytes(1,(int)blob.length());
                        Image image = new Image(new ByteArrayInputStream(byteImage));
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(80);
                        imageView.setFitWidth(120);
                        product.setImage(imageView);
                    }else {
                        URL imageURL = null;
                        try {
                            imageURL = Paths.get("src/main/resources/com/jhon89nbl/programpos/images/notimage.png").toUri().toURL();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        assert imageURL != null;
                        product.setPhoto(imageURL.toString());
                    }
                    products.add(product);

                }
                for (Product product: products) {
                    preparedStatement=connection.prepareStatement(QueryDB.CONSULT_PRODUCT_AMOUNT);
                    preparedStatement.setLong(1,product.getCode());
                    preparedStatement.setLong(2,product.getCode());
                    resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        product.setAmount(resultSet.getInt("available"));
                    }

                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (connection != null) {
                connection.close();
                dataBaseConnection.disconnectDB();
            }
        }
        return products;
    }

    public void saveSales(ObservableList<Product> products,boolean paidOut) throws SQLException {
        //se crea conexion
        Connection connection = null;
        try {
            dataBaseConnection.connectionDB();
            connection = dataBaseConnection.getConnection();
            for (Product product : products) {
                String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
                PreparedStatement preparedStatement = connection.prepareStatement(QueryDB.INSERT_SALE);
                preparedStatement.setInt(1, (paidOut) ? 1 : 0);
                preparedStatement.setString(2, timeStamp);
                preparedStatement.setInt(3, user.getIdUser());
                preparedStatement.setInt(4, 0);
                int create = preparedStatement.executeUpdate();
                preparedStatement = connection.prepareStatement(QueryDB.INSERT_SALE_DETAIL);
                preparedStatement.setLong(1, product.getCode());
                preparedStatement.setInt(2, product.getAmountSale());
                preparedStatement.executeUpdate();
                connection.commit();

            }
        } catch (SQLException e) {
            System.out.println("error");
            e.printStackTrace();
            connection.rollback();

        }finally {
            if (connection != null) {
                connection.close();
                dataBaseConnection.disconnectDB();

            }
        }

    }

    public ObservableList<Product> orderProducts(String providerSearch) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        Connection connection = null;
        try {
            dataBaseConnection.connectionDB();
            connection = dataBaseConnection.getConnection();
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(QueryDB.CODE_PRODUCT_ORDERS);
                preparedStatement.setString(1, providerSearch);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setCode(resultSet.getInt("product_code"));
                    products.add(product);
                }
                for (Product product: products){
                    preparedStatement=connection.prepareStatement(QueryDB.CONSULT_PRODUCT_AMOUNT);
                    preparedStatement.setLong(1,product.getCode());
                    preparedStatement.setLong(2,product.getCode());
                    resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        product.setAmount(resultSet.getInt("available"));

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
