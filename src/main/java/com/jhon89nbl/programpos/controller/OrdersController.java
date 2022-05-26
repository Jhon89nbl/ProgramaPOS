package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.Product;
import com.jhon89nbl.programpos.model.ProductMethods;
import com.jhon89nbl.programpos.model.Provider;
import com.jhon89nbl.programpos.model.ProviderMethods;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {
    private ProviderMethods providerMethods;
    private ProductMethods productMethods;
    private ObservableList<Provider> providers;
    private ObservableList<Product> products;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        providerMethods = new ProviderMethods();
        productMethods = new ProductMethods();
        try {
            providers = providerMethods.listComboProvider();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Estos metodos cargan solo el nombre de la proveedor del objeto categoria
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
        //se carga los items del combox de proveedores
        cmbProvider.setItems(providers);
    }

    @FXML
    private ComboBox<Provider> cmbProvider;

    @FXML
    private TableColumn<Product, Integer> colAmount;

    @FXML
    private TableColumn<Product, String> colCategory;

    @FXML
    private TableColumn<Product, String> colCode;

    @FXML
    private TableColumn<Product, Double> colCost;

    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private GridPane paneProduct;

    @FXML
    private TableView<Product> tblProduct;

    @FXML
    void orderExport(ActionEvent event) {

    }

    @FXML
    void orderProduct(ActionEvent event) {

    }

    @FXML
    void orderProvider(ActionEvent event) {
        String providerSearch = (cmbProvider.getSelectionModel().getSelectedItem()==null)?"":
                String.valueOf(cmbProvider.getSelectionModel().getSelectedItem().getName());
        if(Objects.equals(providerSearch, "")){
            System.out.println("prueba");
        }else {
            products = productMethods.orderProducts(providerSearch);
            for(Product productOrder : products){
                int pedido = (productOrder.getAmountSale()/4)-productOrder.getAmount();
                if(pedido > 0 || productOrder.getAmount() <=5){
                    //products.remove(productOrder);
                    System.out.println(pedido);
                }
            }
            System.out.println(products);
        }
    }


}
