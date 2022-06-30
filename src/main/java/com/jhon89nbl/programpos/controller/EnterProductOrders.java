package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.Product;
import com.jhon89nbl.programpos.model.ProductMethods;
import com.jhon89nbl.programpos.model.Provider;
import com.jhon89nbl.programpos.model.ProviderMethods;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.CurrencyStringConverter;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnterProductOrders implements Initializable {
    private ObservableList<Product> products;
    private ProductMethods productMethods;
    private ProviderMethods providerMethods;
    private ObservableList<Provider> providers;
    private SearchProductController searchProductController;
    private Product productOrder = null;
    private Double totalCost;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        products = FXCollections.observableArrayList();
        productMethods = new ProductMethods();
        providerMethods = new ProviderMethods();
        providers = FXCollections.observableArrayList();
        searchProductController = new SearchProductController();
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colPhoto.setCellValueFactory(new PropertyValueFactory<>("photo"));
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
        edtIVAPercent.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([.]\\d{0,4})?")) {
                edtIVAPercent.setText(oldValue);
            }
        });
        final ToggleGroup groupIVA = new ToggleGroup();
        rbYesIVA.setToggleGroup(groupIVA);
        rbNoIVA.setToggleGroup(groupIVA);

        groupIVA.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                hBoxIva.setVisible(rbYesIVA.isSelected());
            }
        });

        rbYes.setToggleGroup(group);
        rbNo.setToggleGroup(group);
        rbYes.setSelected(true);
        rbYesIVA.setSelected(true);
        edtTotalCost.setTextFormatter(new TextFormatter<>(
                new CurrencyStringConverter(Locale.US),
                0,
                change -> {
                    //Selection cannot be before the first character
                    change.setAnchor(Math.max(1, change.getAnchor()));
                    change.setCaretPosition(Math.max(1, change.getCaretPosition()));
                    //Text change range cannot be before the first character
                    change.setRange(Math.max(1, change.getRangeStart()), Math.max(1, change.getRangeEnd()));
                    return change;
                }));
        edtTotalCost.setEditable(false);
        totalCost = 0.0;
        edtCost.setTextFormatter(new TextFormatter<>(
                new CurrencyStringConverter(Locale.US),
                0,
                change -> {
                    //Selection cannot be before the first character
                    change.setAnchor(Math.max(1, change.getAnchor()));
                    change.setCaretPosition(Math.max(1, change.getCaretPosition()));
                    //Text change range cannot be before the first character
                    change.setRange(Math.max(1, change.getRangeStart()), Math.max(1, change.getRangeEnd()));
                    return change;
                }));
        edtCost.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                Platform.runLater((Runnable) () -> {
                    if (edtCost.isFocused() && !edtCost.getText().isEmpty()) {
                        edtCost.selectAll();

                    }
                });
            }
        });
        tblenterProductOrders.setItems(products);

    }

    @FXML
    private HBox hBoxIva;
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
    private RadioButton rbNoIVA;

    @FXML
    private RadioButton rbYesIVA;

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
    private TextField edtTotalCost;

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
        if(productOrder != null){
            if(!products.contains(productOrder)) {
                List<String> fieldsEmpty = fieldsEmpty(productOrder);
                if (fieldsEmpty.isEmpty()) {
                    productOrder.setAmount(Integer.parseInt(edtAmount.getText()));
                    //se ajusta los valores de costo y el valor de venta para quitar el fomato de moneda
                    String cost = edtCost.getText().replace("$", "");
                    cost = cost.replaceAll(",", "");

                    if (rbYesIVA.isSelected()) {
                        productOrder.setIva(true);
                        float costProduct = (Float.parseFloat(cost) / productOrder.getAmount());
                        if (rbNo.isSelected()) {
                            productOrder.setIvaValue((costProduct * (Float.parseFloat(edtIVAPercent.getText()) / 100)));
                            productOrder.setCost(costProduct + productOrder.getIvaValue());
                        } else {
                            productOrder.setIvaValue(costProduct - costProduct / ((Float.parseFloat(edtIVAPercent.getText()) / 100) + 1));
                            productOrder.setCost(costProduct);
                        }
                    } else {
                        productOrder.setIva(false);
                        productOrder.setIvaValue(0.0f);
                        productOrder.setCost(Float.parseFloat(cost) / productOrder.getAmount());
                    }
                    productOrder.setProvider((cmbProvider.getSelectionModel().getSelectedItem() == null) ? "" :
                            cmbProvider.getSelectionModel().getSelectedItem().getName());
                    boolean validProduct = false;
                    for (Product productValid : products) {
                        if (productValid.getName().equalsIgnoreCase(productOrder.getName()) ||
                                productValid.getCode() == productOrder.getCode()) {
                            validProduct = true;
                        }
                    }
                    if (validProduct) {
                        alertMessage(Alert.AlertType.INFORMATION, "Validar", "El producto ya se encuentra en la tabla \n" +
                                "seleccionelo de la tabla para modificarlo ");
                        cleanFields();
                    } else {
                        products.add(productOrder);
                        totalCost = totalCost + (productOrder.getCost() * productOrder.getAmount());
                        cleanFields();
                    }
                } else {
                    String message = (fieldsEmpty.contains("Porcentaje IVA") || fieldsEmpty.contains("Cantidad")) ?
                            "Por favor valide que estos campos no esten vacios(Cantidad y Porcentaje del IVA no pueden ser 0) " :
                            "Por favor valide que estos campos no esten vacios ";
                    alertMessage(Alert.AlertType.WARNING, "Validar", message + fieldsEmpty);
                }
            }else {
                alertMessage(Alert.AlertType.WARNING, "Validar", "El producto ya esta en la tabla pulse boton modificar");
            }
        }
        tblenterProductOrders.refresh();
        edtTotalCost.setText(String.valueOf(totalCost));
    }

    @FXML
    void deleteProduct(KeyEvent event) {
        if(event.getCode()==KeyCode.DELETE){
            Product product = tblenterProductOrders.getSelectionModel().getSelectedItem();
            if(product!=null){
                products.remove(product);
                totalCost=totalCost-(product.getCost()* product.getAmount());
                edtTotalCost.setText(String.valueOf(totalCost));
                tblenterProductOrders.refresh();
            }
        }
    }

    @FXML
    void saveEnterOrderProducts(ActionEvent event) {

    }

    @FXML
    void searchProduct(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            // se crea parent para cargar la escena de busqueda de productos
            Parent root = null;
            try{
                //se carga la URL de busqueda de productos
                URL fxmURL = Paths.get("src/main/resources/com/jhon89nbl/programpos/search-product.fxml").toUri().toURL();
                // se crea loader con la URL
                FXMLLoader loader = new FXMLLoader(fxmURL);
                // se carga al parent
                root = loader.load();
                //se crea el controlador y se carga
                searchProductController = loader.getController();
                // se inicia la tabla para la busqueda del producto
                searchProductController.initTable(edtNameProduct.getText());
            } catch (IOException | SQLException e){
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE,null,e);
            }
            // se valida que el root no sea nulo
            assert root != null;
            //se crea la scene con el root y el stage
            Scene scene = new Scene(root,700,600);
            Stage stage = new Stage();
            //se inicia el stage como una aplicacion nodal de la principal
            stage.initModality(Modality.APPLICATION_MODAL);
            // se configurna el stage
            stage.setTitle("Buscar Producto");
            stage.setResizable(false);
            stage.setScene(scene);
            //se queda a la espera que se cierre la aplicacion nodal
            stage.showAndWait();
            Product product = searchProductController.getSelectProduct();
            if(product != null){
                edtNameProduct.setText(product.getName());
                edtCodeProduct.setText(String.valueOf(product.getCode()));
                edtAmount.requestFocus();
                productOrder = product;
            }


        }
    }

    @FXML
    void selectionProduct(MouseEvent event) {
        Product product = tblenterProductOrders.getSelectionModel().getSelectedItem();
        if(event.getClickCount()==2){
            if(product!=null){
                edtNameProduct.setText(product.getName());
                edtNameProduct.setEditable(false);
                edtCodeProduct.setText(String.valueOf(product.getCode()));
                edtCodeProduct.setEditable(false);
                for (Provider provider : providers) {
                    if (Objects.equals(provider.getName(), product.getProvider())) {
                        cmbProvider.getSelectionModel().select(provider);
                    }
                }
                edtAmount.setText(String.valueOf(product.getAmount()));
                edtCost.setText(String.valueOf(product.getCost()*product.getAmount()));
                if(product.isIva()){
                    rbYesIVA.setSelected(true);
                    rbYes.setSelected(true);
                    edtIVAPercent.setText(String.valueOf(product.getIvaValue()/(product.getCost()-product.getIvaValue())*100.0f));
                }else{
                    rbNoIVA.setSelected(true);
                    hBoxIva.setVisible(false);
                    edtIVAPercent.setText("");
                }

            }
        }

    }

    @FXML
    void modifyProduct(ActionEvent event){
        Product modifyProduct = tblenterProductOrders.getSelectionModel().getSelectedItem();
        if(modifyProduct==null){
            alertMessage(Alert.AlertType.ERROR,"Error","Debe seleccionar un producto y modificarlo");
        }else {
            Product tempProduct = chargeProduct(modifyProduct);

        }
    }

    private void cleanFields(){
        edtNameProduct.setText("");
        edtCodeProduct.setText("");
        edtAmount.setText("");
        edtCost.setText("0");
        edtIVAPercent.setText("");
        rbYesIVA.setSelected(true);
        rbYes.setSelected(true);
        cmbProvider.getSelectionModel().clearSelection();
    }

    private List<String> fieldsEmpty(Product product){
        //se valida que ningun campo este vacio y se retorna lista
        List<String> fields = new ArrayList<>();
        if(Objects.equals(edtAmount.getText(), "")|| Objects.equals(edtAmount.getText(), "0")){
            fields.add("Cantidad");
        }if(Objects.equals(edtCost.getText(),"$0.00")) {
            fields.add("Costo");
        }
        if(cmbProvider.getSelectionModel().getSelectedItem()== null){
            fields.add("Proveedor");
        }else {
            if(Objects.equals(cmbProvider.getSelectionModel().getSelectedItem().getName(), "")) {
                fields.add("Proveedor");
            }
        }if(rbYesIVA.isSelected()) {
            if(Objects.equals(edtIVAPercent.getText(), "")||Objects.equals(edtIVAPercent.getText(), "0")) {
                fields.add("Porcentaje IVA");
            }
        }
        return fields ;
    }
    private Product chargeProduct(Product product) {

        return product;
    }
    private void alertMessage(Alert.AlertType alertType,String title,String message){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();

    }

}
