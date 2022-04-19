package com.jhon89nbl.programpos.controller;


import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CategoryController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //edtMaxProfit.
        edtMinProfit.setTextFormatter(decimalFormater());
    }

    @FXML
    private Button btnAddCategory;

    @FXML
    private Button btnSave;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TextField edtCategory;

    @FXML
    private TextField edtMaxProfit;

    @FXML
    private TextField edtMinProfit;

    @FXML
    private Button modifyCategory;

    @FXML
    private GridPane paneProduct;

    @FXML
    private TableView<?> tblCategory;

    @FXML
    void addCategory(ActionEvent event) {

    }

    @FXML
    void deleteCategory(KeyEvent event) {

    }

    @FXML
    void modifyCategory(ActionEvent event) {

    }

    @FXML
    void saveCategory(ActionEvent event) {

    }

    @FXML
    void selectionCategory(MouseEvent event) {

    }


    private TextFormatter decimalFormater(){
        //formato para solo numero
        Pattern notNumberPattern = Pattern.compile("-?\\d*(\\.\\d{0,3})?");
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
