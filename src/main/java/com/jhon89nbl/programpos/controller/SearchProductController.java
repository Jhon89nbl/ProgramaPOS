package com.jhon89nbl.programpos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class SearchProductController {

    @FXML
    private Button btnSelectProduct;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colImage;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalePrice;

    @FXML
    private TableView<?> tblSearchProduct;

    @FXML
    void selectProduct(ActionEvent event) {

    }

    @FXML
    void selectionProduct(MouseEvent event) {

    }
}
