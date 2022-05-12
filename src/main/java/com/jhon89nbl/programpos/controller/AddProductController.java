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
    private ObservableList<Product> products;
    private ObservableList<Category> categories;
    private ObservableList<Provider> providers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //se crear las variables de los modelos
        productMethods = new ProductMethods();
        categoryMethods = new CategoryMethods();
        providerMethods = new ProviderMethods();
        //se oculta el campo del porcentaje de iva
        edtIVAPercente.setVisible(false);

        // se crea arraylist para cargar los productos
        products = FXCollections.observableArrayList();
        //se ajusta el nombre de las columnas de la tabla para cargar las lista de productos
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
        //se crea arraylist de las categorias
        categories = FXCollections.observableArrayList();
        //se llama los metodos de los modelos para  cargar los metodos
        try {
            categories = categoryMethods.listComboCategories();
            providers = providerMethods.listComboProvider();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Estos metodos cargan solo el nombre de la categoria del objeto categoria
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
        //Este metodo permite mostrar el promttext cada que se reinicie el combobox o cuando se inicia el programa
        cmbCategory.setButtonCell(new ListCell<>() {
            protected void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                if (category == null || empty) {
                    setText(cmbCategory.getPromptText());
                } else {
                    setText(category.getCategory());
                }

            }
        });
        //Este metodo permite mostrar el promttext cada que se reinicie el combobox o cuando se inicia el programa
        cmbCategory.setItems(categories);
        //se carga el nombre
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
        // se utiliza metodo para dar formato a los campos de numero para moneda o solo numero
        edtCost.setTextFormatter(textFormater("Money"));
        edtSalePrice.setTextFormatter(textFormater("Money"));
        edtAmount.setTextFormatter(textFormater("other"));
        edtCode.setTextFormatter(textFormater("other"));


    }
    //metodo para dar formato
    private TextFormatter textFormater(String type){
        if(type.equals("Money")){
            //formato para moneda
            return new TextFormatter<>(
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
        }else {
            //formato para solo numero
            Pattern notNumberPattern = Pattern.compile("[^0-9]+");
            return new TextFormatter<String>(change -> {
                String newStr = notNumberPattern.matcher(change.getText()).replaceAll("");
                int diffcount = change.getText().length() - newStr.length();
                change.setAnchor(change.getAnchor() - diffcount);
                change.setCaretPosition(change.getCaretPosition() - diffcount);
                change.setText(newStr);
                return change;
            });
        }
    }
    @FXML
    void tabField(KeyEvent event) {
        //evento para poder utilizar tab en el campo descripcion
        if(event.getCode() == KeyCode.TAB){
            edtDescription.setText(edtDescription.getText().trim());
            cmbCategory.requestFocus();
        }
    }

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
    private Button modifyProduct;


    @FXML
    void checkIVAPressed(KeyEvent event) {
        //evento para validar que el check box es seleccionado con el enter
        if(event.getCode()==KeyCode.ENTER){
            edtIVAPercente.setVisible(!chkIsIVa.isSelected());
        }
    }
    @FXML
    void blockPercent(MouseEvent event) {
        //evento para validar que el check box es seleccionado con el mouse
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
        //se valida si no es nulo
        if(selectedFile!=null){
            edtPhoto.setText(selectedFile.toString());
        }
    }


    @FXML
    void addProduct(ActionEvent event) {
        //validamos en el modelo si hay campos vacios
        Product product = chargeProduct();
       List<String> fieldsEmpty= productMethods.fieldsEmpty(product);
        if(fieldsEmpty.isEmpty()){
            boolean validProduct = false;
            for(Product productValid:products){
                if (productValid.getName().equalsIgnoreCase(product.getName())||
                productValid.getCode()==product.getCode()){
                    validProduct = true;
                }
            }
            if (validProduct){
                alertMessage(Alert.AlertType.INFORMATION,"Validar","El producto ya se encuentra en la tabla \" +\n" +
                        "                        \"pulse modificar para guardar los cambios");
            }else {
                /* si no esta vacio se valida la categoria selecciona y que el precio de venta se encuentre entre
            los valores de porcentajes seleccionados*/
                for (Category category: categories) {
                    if(Objects.equals(category.getCategory(), product.getCategory())){
                        float percentPrice= (float) ((product.getSalePrice()/product.getCost())-1)*100;
                        boolean correctPrice= (category.getMaxProfit()>=percentPrice && category.getMinProfit()<=percentPrice);
                        //se valida si es correcto se añade a lista de producto y a la tabla y se limpian los campos
                        if(correctPrice){
                            products.add(product);
                            tblProduct.setItems(products);
                            cleanFields();
                        }else {
                            //en caso de ser falso se muestra mensaje de error
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

        }else {
            alertMessage(Alert.AlertType.WARNING,"Campos Vacios","Los siguientes campos estan vacios " + fieldsEmpty);

        }

    }
    @FXML
    void saveProducts(ActionEvent event) throws SQLException {
        products = productMethods.saveProducts(products);
        if(products.isEmpty())
        {
            alertMessage(Alert.AlertType.INFORMATION,"Exito","Creado Correctamente");
        }else {
            alertMessage(Alert.AlertType.ERROR,"Error","No se pudo crear el producto " +
                    "ya que el nombre o el codigo ya existen" + products);
        }

        tblProduct.setItems(products);


    }
    @FXML
    void modifyProduct(ActionEvent event) {
        Product modifyProduct = tblProduct.getSelectionModel().getSelectedItem();
        if(modifyProduct==null){
            alertMessage(Alert.AlertType.ERROR,"Error","Debe seleccionar un producto y modificarlo");
        }else {
            Product tempProduct = chargeProduct();
            List<String> listProducts = productMethods.fieldsEmpty(tempProduct);
            if(listProducts.isEmpty()) {
                if (!products.contains(tempProduct)) {
                    modifyProduct.setCode(tempProduct.getCode());
                    modifyProduct.setName(tempProduct.getName());
                    modifyProduct.setDescription(tempProduct.getDescription());
                    modifyProduct.setCategory(tempProduct.getCategory());
                    modifyProduct.setProvider(tempProduct.getProvider());
                    modifyProduct.setAmount(tempProduct.getAmount());
                    modifyProduct.setCost(tempProduct.getCost());
                    modifyProduct.setSalePrice(tempProduct.getSalePrice());
                    modifyProduct.setIva(tempProduct.isIva());
                    modifyProduct.setIvaPercent(tempProduct.getIvaPercent());
                    modifyProduct.setImage(tempProduct.getPhoto());
                    modifyProduct.setChargePhoto(tempProduct.isChargePhoto());

                    tblProduct.refresh();
                    cleanFields();
                }
            }else {
                alertMessage(Alert.AlertType.WARNING,"Validar","Debe dar doble click sobre un producto de la tabla para poder modififcar");
            }
        }
    }
    @FXML
    void selectionProduct(MouseEvent event) {
        Product product = tblProduct.getSelectionModel().getSelectedItem();
        if(event.getClickCount()==2) {
            if (product != null) {
                edtCode.setText(String.valueOf(product.getCode()));
                edtName.setText(product.getName());
                edtDescription.setText(product.getDescription());
                for (Category category : categories) {
                    if (Objects.equals(category.getCategory(), product.getCategory())) {
                        cmbCategory.getSelectionModel().select(category);
                    }
                }
                for (Provider provider : providers) {
                    if (Objects.equals(provider.getName(), product.getProvider())) {
                        cmbProvider.getSelectionModel().select(provider);
                    }
                }

                edtAmount.setText(String.valueOf(product.getAmount()));
                edtCost.setText(String.valueOf(product.getCost()));
                edtSalePrice.setText(String.valueOf(product.getSalePrice()));
                if (product.isIva()) {
                    chkIsIVa.setSelected(true);
                    edtIVAPercente.setText(String.valueOf(product.getIvaPercent()));
                    edtIVAPercente.setVisible(true);
                } else {
                    chkIsIVa.setSelected(false);
                }
            }
        }
    }
    @FXML
    void deleteProduct(KeyEvent event) {
        if(event.getCode()==KeyCode.DELETE){
            Product product = tblProduct.getSelectionModel().getSelectedItem();
            if(product !=null){
                products.remove(product);
                cleanFields();
            }
        }
    }

    private Product chargeProduct(){
        Product product = new Product();
        product.setCode((Objects.equals(edtCode.getText(), ""))? -1 :Long.parseLong(edtCode.getText()));
        product.setName(edtName.getText());
        product.setDescription(edtDescription.getText());
        //se valida si se selecciono algo del combo box en caso de no seleecionar nada se carga ""
        product.setCategory((cmbCategory.getSelectionModel().getSelectedItem()==null)? "" :
                cmbCategory.getSelectionModel().getSelectedItem().getCategory());
        //se valida si se selecciono algo del combo box en caso de no seleecionar nada se carga ""
        product.setProvider((cmbProvider.getSelectionModel().getSelectedItem()==null)? "" :
                cmbProvider.getSelectionModel().getSelectedItem().getName()  );
        //se valida si la cantidad esta vacia en caso de si se asigna 0
        if(Objects.equals(edtAmount.getText(), "")){
            product.setAmount(0);
        }else
            product.setAmount(Integer.parseInt(edtAmount.getText()));
        //se ajusta los valores de costo y el valor de venta para quitar el fomato de moneda
        String cost= edtCost.getText().replace("$","");
        cost = cost.replaceAll(",","");
        product.setCost(Double.parseDouble(cost));
        String salePrice = edtSalePrice.getText().replace("$","");
        salePrice = salePrice.replaceAll(",","");
        product.setSalePrice(Double.parseDouble(salePrice));
        // se valida si el cehck box esta seleccionado
        if(chkIsIVa.isSelected()){
            product.setIva(true);
            product.setIvaPercent(Float.parseFloat(edtIVAPercente.getText()));
        }else {
            product.setIva(false);
            product.setIvaPercent(0.0f);
        }
        //se valida si el campo foto esta vacio
        if(edtPhoto.getText().trim().isEmpty()){
            //si esta vacio se carga imagen almacenada;
            URL imageURL = null;
            try {
                imageURL = Paths.get("src/main/resources/com/jhon89nbl/programpos/images/notimage.png").toUri().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            //se guarda la imagen y en la clase se convierte en imagen la ruta
            assert imageURL != null;
            product.setPhoto(imageURL.toString());
            product.setChargePhoto(false);
        }else{
            //si no esta vacia se carga la imagen
            product.setPhoto(edtPhoto.getText());
            product.setChargePhoto(true);
        }
        return product;
    }
    private void cleanFields() {
        //se limpian todos los campos
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
    //metodo para las alertas de mensajes
    private void alertMessage(Alert.AlertType alertType,String title,String message){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();

    }

    //Metodo para cargar productos a la lista


}
