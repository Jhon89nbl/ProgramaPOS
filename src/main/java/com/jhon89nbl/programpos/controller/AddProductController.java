package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddProductController implements Initializable {

    private CategoryMethods categoryMethods;
    private ProviderMethods providerMethods;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        cmbCategory.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                return (category.getCategory());
            }

            @Override
            public Category fromString(String s) {
                throw new UnsupportedOperationException("no soportado todavia");
            }
        });
        cmbProvider.setConverter(new StringConverter<Provider>() {
            @Override
            public String toString(Provider provider) {
                return (provider.getName());
            }

            @Override
            public Provider fromString(String s) {
                throw new UnsupportedOperationException("no soportado todavia");
            }
        });
        cmbCategory.setItems(categories);
        cmbProvider.setItems(providers);
        confTextFields();

    }

    private void confTextFields() {
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
        edtSalePrice.setTextFormatter(currencyFormmater);
        edtCost.setTextFormatter(currencyFormmater);
        Pattern notNumberPattern = Pattern.compile("[^0-9]+");
        TextFormatter<String> lowerFormatter = new TextFormatter<>(change -> {
            String newStr = notNumberPattern.matcher(change.getText()).replaceAll("");
            int diffcount = change.getText().length() - newStr.length();
            change.setAnchor(change.getAnchor() - diffcount);
            change.setCaretPosition(change.getCaretPosition() - diffcount);
            change.setText(newStr);
            return change;
        });
        edtAmount.setTextFormatter(lowerFormatter);
       /* edtAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue,
                                String newValue) {
                if(!newValue.matches("\\d")){
                       edtAmount.setText(oldValue);

                }
            }
        });*/
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
    void addProduct(ActionEvent event) {

    }
    @FXML
    void tabField(KeyEvent event) {
        if(event.getCode() == KeyCode.TAB){

        }
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
    void blockPercent(MouseEvent event) {
        edtIVAPercente.setVisible(chkIsIVa.isSelected());
    }
    @FXML
    void saveProducts(ActionEvent event) {

    }

}
