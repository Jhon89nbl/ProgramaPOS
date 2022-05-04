package com.jhon89nbl.programpos.controller;


import com.jhon89nbl.programpos.model.Category;
import com.jhon89nbl.programpos.model.CategoryMethods;

import com.jhon89nbl.programpos.model.Provider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;



import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


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
        edtMinProfit.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([.]\\d{0,4})?")) {
                edtMinProfit.setText(oldValue);
            }
        });
        edtMaxProfit.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([.]\\d{0,4})?")) {
                edtMaxProfit.setText(oldValue);
            }
        });
    }


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
                    if (categorySearch.getCategory().equalsIgnoreCase(category.getCategory())) {
                        validTitle = true;
                        break;
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
        if(event.getCode()== KeyCode.DELETE){
            Category category = tblCategory.getSelectionModel().getSelectedItem();
            if(category!=null){
                categories.remove(category);
                cleanfields();
            }
        }
    }
    @FXML
    void modifyCategory(ActionEvent event) {
        Category modifyCategory = tblCategory.getSelectionModel().getSelectedItem();
        if (modifyCategory==null){
            alertMessage(Alert.AlertType.ERROR,"Error","Debe seleccionar una categoria para modificarla");
        }else {
            Category temp = chargeCategory();
            if(!categories.contains(temp)){
                modifyCategory.setCategory(temp.getCategory());
                modifyCategory.setMinProfit(temp.getMinProfit());
                modifyCategory.setMaxProfit(temp.getMaxProfit());
                tblCategory.refresh();
            }
        }
    }
    @FXML
    void saveCategory(ActionEvent event) throws SQLException {
        categories = categoryMethods.saveCategories(categories);
        if(categories.isEmpty()){
            alertMessage(Alert.AlertType.INFORMATION,"Exito","Creado Correctamente");
        }else {
            alertMessage(Alert.AlertType.ERROR,"Error","No se pudo crear la categoria " +
                    "ya que ya se encuentra creada" + categories);
        }

        tblCategory.setItems(categories);
    }
    @FXML
    void selectionCategory(MouseEvent event) {
        if(event.getClickCount()==2){
            Category category= tblCategory.getSelectionModel().getSelectedItem();
            if(category != null){
                edtCategory.setText(category.getCategory());
                edtMinProfit.setText(String.valueOf(category.getMinProfit()));
                edtMaxProfit.setText(String.valueOf(category.getMaxProfit()));
            }
        }
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
