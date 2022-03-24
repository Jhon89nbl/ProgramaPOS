package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.stage.FileChooser;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        edtIVAPercente.setVisible(false);
        products = FXCollections.observableArrayList();

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colProvider.setCellValueFactory(new PropertyValueFactory<>("provider"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colSalePrice.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        colIva.setCellValueFactory(new PropertyValueFactory<>("iva"));
        colIvaPercent.setCellValueFactory(new PropertyValueFactory<>("ivaPercent"));
        colPhoto.setCellValueFactory(new PropertyValueFactory<>("photo"));

    }

    private ObservableList<Product> products;
    @FXML
    private CheckBox chkIsIVa;

    @FXML
    private TableView<Product> tblProduct;
    @FXML
    private TableColumn<Product, Integer> colAmount;

    @FXML
    private TableColumn<Product, String> colCategory;

    @FXML
    private TableColumn<Product, String> colCode;

    @FXML
    private TableColumn<Product, Double> colCost;

    @FXML
    private TableColumn<Product, String > colDescription;

    @FXML
    private TableColumn<Product, Boolean> colIva;

    @FXML
    private TableColumn<Product, Float> colIvaPercent;

    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private TableColumn<Product, String> colProvider;

    @FXML
    private TableColumn<Product, Integer> colSalePrice;
    @FXML
    private TableColumn<Product, ImageView> colPhoto;


    @FXML
    private TextField edtPhoto;
    @FXML
    private TextField edtIVAPercente;


    @FXML
    void addProduct(ActionEvent event) {

    }

    @FXML
    void choosePhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Imagen");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow() );
        if(selectedFile!=null){
            edtPhoto.setText(selectedFile.toString());
        }
    }
    @FXML
    void blockPercent(MouseEvent event) {
        edtIVAPercente.setVisible(chkIsIVa.isSelected());
    }
    @FXML
    void saveProducts(ActionEvent event) {

    }

}
