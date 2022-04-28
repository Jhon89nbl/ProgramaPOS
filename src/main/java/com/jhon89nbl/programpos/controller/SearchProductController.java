package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.Product;
import com.jhon89nbl.programpos.model.ProductMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchProductController implements Initializable {

    private ProductMethods productMethods;
    private ObservableList<Product> products;

    public Product getSelectProduct() {
        return selectProduct;
    }

    private Product selectProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productMethods = new ProductMethods();
        products = FXCollections.observableArrayList();
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("photo"));
        colSalePrice.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
    }

    @FXML
    private Button btnSelectProduct;

    @FXML
    private TableColumn<Product, Integer> colAmount;

    @FXML
    private TableColumn<Product, String> colCategory;

    @FXML
    private TableColumn<Product, Integer> colCode;

    @FXML
    private TableColumn<Product, ImageView> colImage;

    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private TableColumn<Product, Float> colSalePrice;

    @FXML
    private TableView<Product> tblSearchProduct;

    @FXML
    void selectProduct(ActionEvent event) {

    }

    @FXML
    void selectionProduct(MouseEvent event) {
        if(event.getClickCount()==2){
            Product product = tblSearchProduct.getSelectionModel().getSelectedItem();
            if(product!=null){
                selectProduct = product;
                Node source =(Node) event.getSource();
                Stage stage =(Stage) source.getScene().getWindow();
                stage.close();
            }
        }
    }



    public void initTable(String search) throws SQLException {
       products= productMethods.searchProducts(search);
       tblSearchProduct.setItems(products);
    }
}
