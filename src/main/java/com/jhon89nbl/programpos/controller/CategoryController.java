package com.jhon89nbl.programpos.controller;


import com.jhon89nbl.programpos.model.Category;
import com.jhon89nbl.programpos.model.CategoryMethods;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javax.swing.event.ChangeEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CategoryController implements Initializable {
    private CategoryMethods categoryMethods;
    private ObservableList<Category> categories;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryMethods = new CategoryMethods();
        categories = FXCollections.observableArrayList();
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colMaxProfit.setCellValueFactory(new PropertyValueFactory<>("maxProfit"));
        colMinProfit.setCellValueFactory(new PropertyValueFactory<>("minProfit"));
        edtMinProfit.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    edtMinProfit.setText(oldValue);
                }
            }
        });
        edtMaxProfit.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    edtMaxProfit.setText(oldValue);
                }
            }
        });
    }



    @FXML
    private Button btnAddCategory;

    @FXML
    private Button btnSave;

    @FXML
    private TableColumn<Category, String> colCategory;

    @FXML
    private TableColumn<Category, Float> colMaxProfit;

    @FXML
    private TableColumn<Category, Float> colMinProfit;

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
    private TableView<Category> tblCategory;

    @FXML
    void addCategory(ActionEvent event) {
        Category category = chargeCategory();
        List<String> fieldsEmpty = categoryMethods.fieldsEmpty(category);
        if(fieldsEmpty.size()>0){
            alertMessage(Alert.AlertType.WARNING,"Campos Vacios","Los siguientes campos estan vacios " + fieldsEmpty);
        }else {
            if(category.getMaxProfit()>100){
                alertMessage(Alert.AlertType.WARNING,"Error","La ganacia maxima no puede ser mayor a 100 " );
            }if(category.getMinProfit()<=5){
                alertMessage(Alert.AlertType.WARNING,"Error","La ganacia minima no puede ser menor a 5" );
            }else {
                boolean validTitle=false;
                for (Category categorySearch: categories){
                    if(categorySearch.getCategory().equalsIgnoreCase(category.getCategory())){
                        validTitle=true;
                    }
                }
                if(!validTitle) {
                    categories.add(category);
                    tblCategory.setItems(categories);
                    cleanfields();
                }else {
                    alertMessage(Alert.AlertType.WARNING,"Error","Ya existe en la tabla la categoria" );
                }
            }

        }
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

    private Category chargeCategory(){
        Category category = new Category();
        category.setCategory(edtCategory.getText());
        category.setMaxProfit(Float.parseFloat(edtMaxProfit.getText()));
        category.setMinProfit(Float.parseFloat(edtMinProfit.getText()));
        return category;
    }

    private void alertMessage(Alert.AlertType alertType,String title,String message){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
    private void cleanfields() {
        edtCategory.setText("");
        edtMaxProfit.setText("");
        edtMinProfit.setText("");
        edtCategory.requestFocus();
    }




}
