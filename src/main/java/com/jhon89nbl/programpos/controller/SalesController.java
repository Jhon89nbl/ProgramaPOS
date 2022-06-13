package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.Product;
import com.jhon89nbl.programpos.model.ProductMethods;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.CurrencyStringConverter;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class SalesController implements Initializable {

    private SearchProductController searchProductController;
    private ProductMethods productMethods;
    private Product productSale= null;
    private ObservableList<Product> products;
    private Double totalPrice = 0.0;
    private Double changeTotalPrice=0.0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productMethods = new ProductMethods();
        products = FXCollections.observableArrayList();
        searchProductController = new SearchProductController();
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colAmountInvertory.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colSalePrice.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        colAmountSale.setCellValueFactory(new PropertyValueFactory<>("amountSale"));
        colPhoto.setCellValueFactory(new PropertyValueFactory<>("photo"));
        edtTotalPrice.setEditable(false);
        edtAmount.setTextFormatter(textFormater("other"));
        edtCodeProduct.setTextFormatter(textFormater("other"));
        edtTotalPrice.setTextFormatter(textFormater("Money"));
        edtPay.setTextFormatter(textFormater("Money"));
        URL imageButton = null;
        try {
            imageButton = Paths.get("src/main/resources/com/jhon89nbl/programpos/images/saleicon.png").toUri().toURL();
            ImageView imageView = new ImageView(imageButton.toString());
            imageView.setFitHeight(60);
            imageView.setFitWidth(60);
            btnSave.setGraphic(imageView);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        edtPay.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                Platform.runLater((Runnable) () -> {
                    if (edtPay.isFocused() && !edtPay.getText().isEmpty()) {
                        edtPay.selectAll();

                    }
                });
            }
        });
        edtTotalPrice.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                Platform.runLater((Runnable) () -> {
                    if (edtTotalPrice.isFocused() && !edtTotalPrice.getText().isEmpty()) {
                        edtTotalPrice.selectAll();

                    }
                });
            }
        });

    }
    @FXML
    private Button btnAddProduct;

    @FXML
    private BorderPane btnSale;

    @FXML
    private Button btnSave;
    @FXML
    private CheckBox chekPay;

    @FXML
    private TableColumn<Product, Integer> colAmountInvertory;

    @FXML
    private TableColumn<Product, Integer> colAmountSale;

    @FXML
    private TableColumn<Product, String> colCategory;

    @FXML
    private TableColumn<Product, Long> colCode;

    @FXML
    private TableColumn<Product, String> colDescription;

    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private TableColumn<Product, Double> colSalePrice;
    @FXML
    private TableColumn<Product, ImageView> colPhoto;

    @FXML
    private TextField edtAmount;
    @FXML
    private TextField edtTotalPrice;

    @FXML
    private TextField edtClient;
    @FXML
    private TextField edtPay;

    @FXML
    private TextField edtCodeProduct;

    @FXML
    private TextField edtNameProduct;

    @FXML
    private GridPane paneProduct;

    @FXML
    private TableView<Product> tblSale;

    @FXML
    void addProduct(ActionEvent event) {
        if(productSale!=null){
            if (!products.contains(productSale)) {
                productSale.setAmountSale(Integer.parseInt(edtAmount.getText()));
                if(productSale.getAmount() >= productSale.getAmountSale()) {
                    products.add(productSale);
                    tblSale.setItems(products);
                    totalPrice = totalPrice + (productSale.getAmountSale() * productSale.getSalePrice());
                    edtTotalPrice.setText(String.valueOf(totalPrice));
                    cleanFields();
                }else
                    alertMessage(Alert.AlertType.ERROR,"Error","En inventario el producto " + productSale.getName()+
                            " tiene solo " + productSale.getAmount() + " productos en stock y esta ingresando como cantidad vendida "
                            + productSale.getAmountSale() + " lo cual supera la cantidad en stock");
            } else {
                    productSale.setAmountSale(Integer.parseInt(edtAmount.getText()));
                    if (productSale.getAmount()> productSale.getAmountSale()) {
                        tblSale.refresh();
                        totalPrice = totalPrice - changeTotalPrice + (productSale.getAmountSale() * productSale.getSalePrice());
                        edtTotalPrice.setText(String.valueOf(totalPrice));
                    }else
                        alertMessage(Alert.AlertType.ERROR,"Error","En inventario el producto " + productSale.getName()+
                                " tiene solo " + productSale.getAmount() + " productos en stock y esta ingresando como cantidad vendida "
                                + productSale.getAmountSale() + " lo cual supera la cantidad en stock");
                }
            }

    }

    @FXML
    void deleteSale(KeyEvent event) {
        if(event.getCode() == KeyCode.DELETE){
            Product product = tblSale.getSelectionModel().getSelectedItem();
            if(product!=null){
                totalPrice = totalPrice - (product.getAmountSale() * product.getSalePrice());
                products.remove(product);
                edtTotalPrice.setText(String.valueOf(totalPrice));
                cleanFields();

            }
        }
    }

    @FXML
    void saveSale(ActionEvent event) throws SQLException {
        Double payment = 0.0;
        String pay = edtPay.getText().replace("$","");
        pay = pay.replace(",","");
        payment = Double.parseDouble(pay);
        if(totalPrice<=payment){
            productMethods.saveSales(products,!chekPay.isSelected());
            alertMessage(Alert.AlertType.INFORMATION,
                    "Pago",
                    "Por favor devolver al cliente $" + (payment-totalPrice));
            cleanFields();
            edtTotalPrice.setText(String.valueOf(0.0));
            edtPay.setText(String.valueOf(0.0));
            totalPrice =0.0;
            changeTotalPrice =0.0;
            products.clear();
            tblSale.setItems(products);
        }else {
            alertMessage(Alert.AlertType.ERROR,"Error","El valor ingresado para el pago es menor " +
                    "que el valor a cobrar");
        }


    }

    @FXML
    void selectionSale(MouseEvent event) {
        if (event.getClickCount()==2){
            Product product = tblSale.getSelectionModel().getSelectedItem();
            edtNameProduct.setText(product.getName());
            edtCodeProduct.setText(String.valueOf(product.getCode()));
            edtAmount.setText(String.valueOf(product.getAmountSale()));
            changeTotalPrice = product.getSalePrice()*product.getAmountSale();
            productSale = product;
        }
    }
    @FXML
    void searchProduct(KeyEvent event) {
        if(event.getCode()== KeyCode.ENTER){
            Parent root = null;
            try {
                URL fxmURL = Paths.get("src/main/resources/com/jhon89nbl/programpos/search-product.fxml").toUri().toURL();
                FXMLLoader loader = new FXMLLoader(fxmURL);
                root = loader.load();
                searchProductController =  loader.getController();
                searchProductController.initTable(edtNameProduct.getText());

            }catch (IOException | SQLException e){
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE,null,e);
            }
            Scene scene = new Scene(root,700, 600);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Buscar Producto");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
            Product product = searchProductController.getSelectProduct();
            if(product!=null){
                edtCodeProduct.setText(String.valueOf(product.getCode()));
                edtAmount.requestFocus();
                productSale= product;
            }

        }
    }


    private void cleanFields(){
        edtClient.setText("");
        edtAmount.setText("");
        edtCodeProduct.setText("");
        edtNameProduct.setText("");
        edtNameProduct.requestFocus();
    }

    private TextFormatter textFormater(String type){
        if(type.equals("Money")){
            //formato para moneda
            return new TextFormatter<>(
                    new CurrencyStringConverter(Locale.US),
                    0,
                    change -> {
                        //Selection cannot be before the first character
                        change.setAnchor(Math.max(1, change.getAnchor()));
                        change.setCaretPosition(Math.max(1, change.getCaretPosition()));
                        //Text change range cannot be before the first character
                        change.setRange(Math.max(1, change.getRangeStart()), Math.max(1, change.getRangeEnd()));
                        return change;
                    });
        }else {
            //formato para solo numero
            Pattern notNumberPattern = Pattern.compile("[^0-9]+");
            return new TextFormatter<String>(change -> {
                String newStr = notNumberPattern.matcher(change.getText()).replaceAll("");
                int diffcount = change.getText().length() - newStr.length();
                change.setAnchor(change.getAnchor() - diffcount);
                change.setCaretPosition(change.getCaretPosition() - diffcount);
                change.setText(newStr);
                return change;
            });
        }
    }
    private void alertMessage(Alert.AlertType alertType,String title,String message){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

}
