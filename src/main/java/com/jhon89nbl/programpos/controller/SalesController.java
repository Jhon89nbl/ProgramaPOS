package com.jhon89nbl.programpos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class SalesController {

    @FXML
    private Button btnAddProduct;

    @FXML
    private BorderPane btnSale;

    @FXML
    private Button btnSave;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colIva;

    @FXML
    private TableColumn<?, ?> colIvaPercent;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhoto;

    @FXML
    private TableColumn<?, ?> colProvider;

    @FXML
    private TableColumn<?, ?> colSalePrice;

    @FXML
    private TextField edtAmount;

    @FXML
    private TextField edtClient;

    @FXML
    private TextField edtCodeProduct;

    @FXML
    private TextField edtNameProduct;

    @FXML
    private GridPane paneProduct;

    @FXML
    private TableView<?> tblSale;

    @FXML
    void addProduct(ActionEvent event) {

    }

    @FXML
    void deleteSale(KeyEvent event) {

    }

    @FXML
    void saveProducts(ActionEvent event) {

    }

    @FXML
    void selectionSale(MouseEvent event) {

    }
}
