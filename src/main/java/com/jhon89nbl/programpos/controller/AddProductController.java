package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.Product;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        product = FXCollections.observableArrayList();

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));



    }

    private ObservableList<Product> product;
    @FXML
    private Button btnPrueba;
    @FXML
    private TableView<Product> tblProduct;
    @FXML
    private TableColumn<Product, String> colCode;

    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private GridPane paneProduct;

    @FXML
    void addProduct(ActionEvent event) {
        Product pro= new Product("1","1");
        product.add(pro);
        tblProduct.setItems(product);
    }


}
