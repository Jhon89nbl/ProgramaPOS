package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.Product;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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
    private TextField edtPhoto;

    @FXML
    void addProduct(ActionEvent event) {
        Product pro= new Product("1","1");
        product.add(pro);
        tblProduct.setItems(product);
    }

    @FXML
    void choosePhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Imagen");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        File selectedFile = fileChooser.showOpenDialog((Stage)((Node) event.getSource()).getScene().getWindow() );
        if(selectedFile!=null){
            edtPhoto.setText(selectedFile.toString());
        }
    }

}
