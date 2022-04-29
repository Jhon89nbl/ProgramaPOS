package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.CurrencyStringConverter;

import javax.swing.text.html.ImageView;
import java.io.IOException;
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
    private Product productSale= null;
    private ObservableList<Product> products;
    private Double totalPrice = 0.0;
    private Double changeTotalPrice=0.0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    }
    @FXML
    private Button btnAddProduct;

    @FXML
    private BorderPane btnSale;

    @FXML
    private Button btnSave;

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
                if(productSale.getAmount()> productSale.getAmountSale()) {
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
    void saveProducts(ActionEvent event) {

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
