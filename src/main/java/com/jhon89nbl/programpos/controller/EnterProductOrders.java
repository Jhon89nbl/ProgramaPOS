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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.CurrencyStringConverter;

import java.io.IOException;
import java.net.MalformedURLException;
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
        edtIVAPercent.setVisible(false);
        rbYes.setToggleGroup(group);
        rbNo.setToggleGroup(group);
        rbYes.setSelected(true);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                edtIVAPercent.setVisible(rbNo.isSelected());
            }
        });
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
        if(productOrder != null){
            List<String> fieldsEmpty = fieldsEmpty(productOrder);
            if(fieldsEmpty.isEmpty()){
                productOrder.setAmount(Integer.parseInt(edtAmount.getText()));
                productOrder.setIva(!rbNo.isSelected());
                productOrder.setIvaPercent(
                        (edtIVAPercent.getText()!=null && !Objects.equals(edtIVAPercent.getText(), ""))?
                                Float.parseFloat(edtIVAPercent.getText()):0.0f
                );
                //se ajusta los valores de costo y el valor de venta para quitar el fomato de moneda
                String cost= edtCost.getText().replace("$","");
                cost = cost.replaceAll(",","");
                if(productOrder.isIva()){
                    productOrder.setCost(Double.parseDouble(cost)/productOrder.getAmount());
                }else {
                    double costUnit= (Double.parseDouble(cost)/productOrder.getAmount())+
                            (Double.parseDouble(cost)*productOrder.getIvaPercent());
                    productOrder.setCost(costUnit);
                }
                productOrder.setProvider((cmbProvider.getSelectionModel().getSelectedItem()==null)? "" :
                        cmbProvider.getSelectionModel().getSelectedItem().getName()  );
                products.add(productOrder);
                cleanFields();

            }else {
                alertMessage(Alert.AlertType.WARNING,"Validar","Por favor valide que estos campos no esten vacios " + fieldsEmpty);
            }

        }
        tblenterProductOrders.refresh();

    }

    @FXML
    void deleteProduct(KeyEvent event) {

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

    }

    private void cleanFields(){
        edtNameProduct.setText("");
        edtCodeProduct.setText("");
        edtAmount.setText("");
        edtCost.setText("0");
        edtIVAPercent.setText("");
        rbYes.setSelected(true);
        cmbProvider.getSelectionModel().clearSelection();
    }

    private List<String> fieldsEmpty(Product product){
        //se valida que ningun campo este vacio y se retorna lista
        List<String> fields = new ArrayList<>();
        if(Objects.equals(edtAmount.getText(), "")){
            fields.add("Cantidad");
        }if (Objects.equals(edtCost.getText(),"$0.00")) {
            fields.add("Costo");
        }
        if(cmbProvider.getSelectionModel().getSelectedItem()== null){
            fields.add("Proveedor");
        }else {
            if (Objects.equals(cmbProvider.getSelectionModel().getSelectedItem().getName(), "")) {
                fields.add("Proveedor");
            }
        }if(rbNo.isSelected() && !Objects.equals(edtIVAPercent.getText(), "")){
            fields.add("Porcentaje IVA");
        }
        return fields ;
    }

    private void alertMessage(Alert.AlertType alertType,String title,String message){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();

    }

}
