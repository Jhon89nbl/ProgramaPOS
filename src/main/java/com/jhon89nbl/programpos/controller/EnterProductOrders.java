package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.Product;
import com.jhon89nbl.programpos.model.ProductMethods;
import com.jhon89nbl.programpos.model.Provider;
import com.jhon89nbl.programpos.model.ProviderMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EnterProductOrders implements Initializable {
    private ObservableList<Product> products;
    private ProductMethods productMethods;
    private ProviderMethods providerMethods;
    private ObservableList<Provider> providers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        products = FXCollections.observableArrayList();
        productMethods = new ProductMethods();
        providerMethods = new ProviderMethods();
        providers = FXCollections.observableArrayList();
        //Se carga la lista de proveedores
        try {
            providers = providerMethods.listComboProvider();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cmbProvider.setConverter(new StringConverter<>() {
            @Override
            public String toString(Provider provider) {
                return (provider.getName());
            }

            @Override
            public Provider fromString(String s) {
                throw new UnsupportedOperationException("no soportado todavia");
            }
        });
        //Este metodo permite mostrar el promt text cada que se reinicie el combo box o cuando se inicia el programa
        cmbProvider.setButtonCell(new ListCell<>(){
            protected void updateItem(Provider provider, boolean empty){
                super.updateItem(provider,empty);
                if(provider==null||empty){
                    setText(cmbProvider.getPromptText());
                }else{
                    setText(provider.getName());
                }
            }
        });
        cmbProvider.setItems(providers);
        final ToggleGroup group = new ToggleGroup();
        rbYes.setToggleGroup(group);
        rbNo.setToggleGroup(group);

    }

    @FXML
    private Button btnAddProduct;

    @FXML
    private BorderPane btnSale;

    @FXML
    private Button btnSave;

    @FXML
    private RadioButton rbNo;

    @FXML
    private RadioButton rbYes;

    @FXML
    private ComboBox<Provider> cmbProvider;

    @FXML
    private TableColumn<Product, Integer> colAmount;

    @FXML
    private TableColumn<Product, Long> colCode;

    @FXML
    private TableColumn<Product, Double> colCost;

    @FXML
    private TableColumn<Product, String> colDescription;

    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private TableColumn<Product, ImageView> colPhoto;

    @FXML
    private TextField edtAmount;

    @FXML
    private TextField edtCodeProduct;

    @FXML
    private TextField edtCost;

    @FXML
    private TextField edtIVAPercent;

    @FXML
    private TextField edtNameProduct;

    @FXML
    private GridPane paneProduct;

    @FXML
    private TableView<Product> tblenterProductOrders;

    @FXML
    void addProduct(ActionEvent event) {

    }

    @FXML
    void deleteProduct(KeyEvent event) {

    }

    @FXML
    void saveEnterOrderProducts(ActionEvent event) {

    }

    @FXML
    void searchProduct(KeyEvent event) {

    }

    @FXML
    void selectionProduct(MouseEvent event) {

    }


}
