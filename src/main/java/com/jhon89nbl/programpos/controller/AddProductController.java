package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.*;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javafx.util.converter.CurrencyStringConverter;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddProductController implements Initializable {

    private CategoryMethods categoryMethods;
    private ProviderMethods providerMethods;
    private ProductMethods productMethods;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productMethods = new ProductMethods();
        categoryMethods = new CategoryMethods();
        providerMethods = new ProviderMethods();
        edtIVAPercente.setVisible(false);

        products = FXCollections.observableArrayList();

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colProvider.setCellValueFactory(new PropertyValueFactory<>("provider"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colSalePrice.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        colIva.setCellValueFactory(new PropertyValueFactory<>("iva"));
        colIvaPercent.setCellValueFactory(new PropertyValueFactory<>("ivaPercent"));
        colPhoto.setCellValueFactory(new PropertyValueFactory<>("photo"));
        categories = FXCollections.observableArrayList();
        try {
            categories = categoryMethods.listComboCategories();
            providers = providerMethods.listComboProvider();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cmbCategory.setConverter(new StringConverter<>() {
            @Override
            public String toString(Category category) {
                return (category.getCategory());
            }

            @Override
            public Category fromString(String s) {
                throw new UnsupportedOperationException("no soportado todavia");
            }
        });
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
        cmbCategory.setButtonCell(new ListCell<Category>(){
            protected void updateItem(Category category,boolean empty){
                super.updateItem(category,empty);
                if(category== null|| empty){
                    setText(cmbCategory.getPromptText());
                }else {
                    setText(category.getCategory());
                }

            }
        });
        cmbCategory.setItems(categories);
        cmbProvider.setButtonCell(new ListCell<Provider>(){
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
        edtCost.setTextFormatter(textFormater("Money"));
        edtSalePrice.setTextFormatter(textFormater("Money"));
        edtAmount.setTextFormatter(textFormater("other"));


    }

    private TextFormatter textFormater(String type){
        if(type.equals("Money")){
            TextFormatter<Number> currencyFormmater = new TextFormatter<>(
                    new CurrencyStringConverter(Locale.US),
                    0,
                    change -> {
                        //Selection cannot be before the first character
                        change.setAnchor(Math.max(1, change.getAnchor()));
                        change.setCaretPosition(Math.max(1, change.getCaretPosition()));
                        //Text change range cannot be before the first character
                        change.setRange(Math.max(1, change.getRangeStart()), Math.max(1, change.getRangeEnd()));
                        return change;
                    });
            return currencyFormmater;
        }else {
            Pattern notNumberPattern = Pattern.compile("[^0-9]+");
            TextFormatter<String> lowerFormatter = new TextFormatter<>(change -> {
                String newStr = notNumberPattern.matcher(change.getText()).replaceAll("");
                int diffcount = change.getText().length() - newStr.length();
                change.setAnchor(change.getAnchor() - diffcount);
                change.setCaretPosition(change.getCaretPosition() - diffcount);
                change.setText(newStr);
                return change;
            });
            return lowerFormatter;
        }
    }


    private ObservableList<Product> products;
    private ObservableList<Category> categories;
    private ObservableList<Provider> providers;
    @FXML
    private CheckBox chkIsIVa;

    @FXML
    private TableView<Product> tblProduct;
    @FXML
    private TableColumn<Product, Integer> colAmount;

    @FXML
    private TableColumn<Product, String> colCategory;

    @FXML
    private TableColumn<Product, String> colCode;

    @FXML
    private TableColumn<Product, Double> colCost;

    @FXML
    private TableColumn<Product, String > colDescription;

    @FXML
    private TableColumn<Product, Boolean> colIva;

    @FXML
    private TableColumn<Product, Float> colIvaPercent;

    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private TableColumn<Product, String> colProvider;

    @FXML
    private TableColumn<Product, Integer> colSalePrice;
    @FXML
    private TableColumn<Product, ImageView> colPhoto;
    @FXML
    private ComboBox<Category> cmbCategory;

    @FXML
    private ComboBox<Provider> cmbProvider;
    @FXML
    private TextField edtSalePrice;

    @FXML
    private TextField edtPhoto;
    @FXML
    private TextField edtIVAPercente;
    @FXML
    private TextField edtAmount;
    @FXML
    private TextField edtCost;
    @FXML
    private TextArea edtDescription;
    @FXML
    private TextField edtCode;
    @FXML
    private TextField edtName;


    @FXML
    void tabField(KeyEvent event) {
        if(event.getCode() == KeyCode.TAB){
            System.out.println(KeyCode.TAB);
           edtDescription.setText(edtDescription.getText().trim());
            cmbCategory.requestFocus();
        }
    }
    @FXML
    void checkIVAPressed(KeyEvent event) {
        if(event.getCode()==KeyCode.ENTER){
            edtIVAPercente.setVisible(!chkIsIVa.isSelected());
        }
    }
    @FXML
    void blockPercent(MouseEvent event) {
        edtIVAPercente.setVisible(chkIsIVa.isSelected());
    }

    @FXML
    void choosePhoto(ActionEvent event) {
        //se crea file chooser para escojer la foto
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Imagen");
        //se filtra solo los archivos que se pueden seleccionar
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        //se obtiene el archivo
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow() );
        //se valida si es nulo
        if(selectedFile!=null){
            edtPhoto.setText(selectedFile.toString());
        }
    }

    @FXML
    void saveProducts(ActionEvent event) {

    }
    @FXML
    void addProduct(ActionEvent event) {
        Product product = new Product();
        product.setCode(edtCode.getText());
        product.setName(edtName.getText());
        product.setDescription(edtDescription.getText());
        product.setCategory((cmbCategory.getSelectionModel().getSelectedItem()==null)?"":
                cmbCategory.getSelectionModel().getSelectedItem().getCategory());
        product.setProvider((cmbProvider.getSelectionModel().getSelectedItem()==null)?"":
                cmbProvider.getSelectionModel().getSelectedItem().getName()  );
       if(edtAmount.getText()==""){
           product.setAmount(0);
       }else
           product.setAmount(Integer.parseInt(edtAmount.getText()));
        String cost= edtCost.getText().replace("$","");
        cost = cost.replaceAll(",","");
        product.setCost(Double.parseDouble(cost));
        String salePrice = edtSalePrice.getText().replace("$","");
        salePrice = salePrice.replaceAll(",","");
        product.setSalePrice(Double.parseDouble(salePrice));

        if(chkIsIVa.isSelected()){
            product.setIva(true);
            product.setIvaPercent(Float.parseFloat(edtIVAPercente.getText()));
        }else {
            product.setIva(false);
            product.setIvaPercent(0.0f);
        }
        if(edtPhoto.getText().trim().isEmpty()){
            URL imageURL = null;
            try {
                imageURL = Paths.get("src/main/resources/com/jhon89nbl/programpos/images/notimage.png").toUri().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            product.setPhoto(imageURL.toString());
        }else{
            product.setPhoto(edtPhoto.getText());
        }
       List<String> fieldsEmpty= productMethods.fieldsEmpty(product);
        if(fieldsEmpty.size() >0){
            alertMessage(Alert.AlertType.WARNING,"Campos Vacios","Los siguientes campos estan vacios " + fieldsEmpty.toString());

        }else {
            for (Category category: categories) {
                if(category.getCategory().equals(product.getCategory())){
                    float percentPrice= (float) ((product.getSalePrice()/product.getCost())-1)*100;
                    boolean correctPrice= (category.getMaxProfit()>=percentPrice && category.getMinProfit()<=percentPrice);
                    if(correctPrice){
                        products.add(product);
                        tblProduct.setItems(products);
                        cleanFields();
                    }else {
                        alertMessage(
                                Alert.AlertType.WARNING,
                                "Error Precio venta",
                                "La ganacia debe estar entre "+ category.getMaxProfit()+"% y "
                                        + category.getMinProfit()+ "% actualmente esta en " + percentPrice +"%¿"
                        );
                    }
                }
            }

        }

    }

    private void cleanFields() {
        edtCode.setText("");
        edtName.setText("");
        edtDescription.setText("");
        cmbCategory.getSelectionModel().clearSelection();

        cmbProvider.getSelectionModel().select(-1);
        cmbProvider.setPromptText("Proveedor");
        edtAmount.setText("");
        edtCost.setText("0");
        edtSalePrice.setText("0");
        if(chkIsIVa.isSelected()){
            chkIsIVa.setSelected(false);
            edtIVAPercente.setText("");
        }
        edtPhoto.setText("");

    }
    private void alertMessage(Alert.AlertType alertType,String title,String message){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();

    }

}
