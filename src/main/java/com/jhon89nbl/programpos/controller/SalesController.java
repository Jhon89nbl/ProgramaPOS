package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalesController implements Initializable {

    private SearchProductController searchProductController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchProductController = new SearchProductController();
    }
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
            }

        }
    }


}
