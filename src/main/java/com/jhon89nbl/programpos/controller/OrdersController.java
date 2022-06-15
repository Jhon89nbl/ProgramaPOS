package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.Product;
import com.jhon89nbl.programpos.model.ProductMethods;
import com.jhon89nbl.programpos.model.Provider;
import com.jhon89nbl.programpos.model.ProviderMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class OrdersController implements Initializable {
    private ProviderMethods providerMethods;
    private ProductMethods productMethods;
    private ObservableList<Provider> providers;
    private ObservableList<Product> products;
    private HashMap<String,Integer> period;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // se inicializan variables
        providerMethods = new ProviderMethods();
        productMethods = new ProductMethods();
        products = FXCollections.observableArrayList();
        //cargar la tabla
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colAmountSale.setCellValueFactory(new PropertyValueFactory<>("amountSale"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        //se crea hasmap para los dias a consultar
        period = new HashMap<>();
        period.put("1 Semana",7);
        period.put("15 dias",15);
        period.put("1 Mes",30);
        period.put("Trimestre",90);
        period.put("Semestre",180);
        period.put("Año",365);
        HashMap<String,Integer> order= period.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (older,newOrder) -> older, LinkedHashMap::new
                ));
        //Se carga la lista de proveedores
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

        // se crea lista con los valores de los dias
        ArrayList<String> values = new ArrayList<>(order.keySet());

        // se crea lista observable para cargar los dias a consultar
        ObservableList <String> listCombo = FXCollections.observableArrayList();
        listCombo.addAll(values);
        listCombo.sorted();
        //se carga el combo de los dias y de los proveedores
        cmbPeriodTime.setItems(listCombo);
        //se carga los items del combox de proveedores
        cmbProvider.setItems(providers);
        final ToggleGroup group = new ToggleGroup();
        rBMonthly.setToggleGroup(group);
        rBWeekly.setToggleGroup(group);
        rBBiWeekly.setToggleGroup(group);
        rBWeekly.setSelected(true);
        tblProduct.setItems(products);
    }

    @FXML
    private ComboBox<Provider> cmbProvider;
    @FXML
    private ComboBox<String> cmbPeriodTime;

    @FXML
    private TableColumn<Product, Integer> colAmount;

    @FXML
    private TableColumn<Product, String> colAmountSale;

    @FXML
    private TableColumn<Product, String> colCode;



    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private GridPane paneProduct;

    @FXML
    private TableView<Product> tblProduct;
    @FXML
    private RadioButton rBMonthly;
    @FXML
    private RadioButton rBBiWeekly;
    @FXML
    private RadioButton rBWeekly;

    @FXML
    void orderExport(ActionEvent event) {
        // se crea file chooser para seleccionar carpeta donde se va a guardar
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione La carpeta");
        // se crea filtro para darle el formato del archivo
        FileChooser.ExtensionFilter exfilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(exfilter);
        //Se espera a que se seleccione el archivo
        File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());
        if(selectedFile!=null){
            //se crea archivo de excel
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            //se crea hoja con el nombre de ordenes
            HSSFSheet hssfSheet = hssfWorkbook.createSheet("ordenes");
            //se crea la fila 0 para los nombres
            HSSFRow firstRow = hssfSheet.createRow(0);
            //se obtienen los nombres de la tabla y se cra columna con cada nombre
            for (int i =0; i<tblProduct.getColumns().size(); i++){
                firstRow.createCell(i).setCellValue(tblProduct.getColumns().get(i).getText());
            }
            for (int row = 0; row < tblProduct.getItems().size(); row++) {
                HSSFRow hssfRow = hssfSheet.createRow(row+1);
                for (int col = 0; col < tblProduct.getColumns().size() ; col++) {
                    Object celvalue = tblProduct.getColumns().get(col).getCellObservableValue(row).getValue();
                    try {
                        if(celvalue != null && Double.parseDouble(celvalue.toString()) !=0.0){
                            hssfRow.createCell(col).setCellValue(Double.parseDouble(celvalue.toString()));
                        }
                    }catch (NumberFormatException e){
                        hssfRow.createCell(col).setCellValue(celvalue.toString());
                    }
                }
            }

            try{

                hssfWorkbook.write(new FileOutputStream(selectedFile));
                hssfWorkbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }



    @FXML
    void order(ActionEvent event) {
        String providerSearch = (cmbProvider.getSelectionModel().getSelectedItem()==null)?"":
                String.valueOf(cmbProvider.getSelectionModel().getSelectedItem().getName());
        int dayConsult = (cmbPeriodTime.getSelectionModel().getSelectedItem()==null) ? 0:
                period.get(cmbPeriodTime.getSelectionModel().getSelectedItem());
        if(Objects.equals(providerSearch, "")|| dayConsult==0){
            alertMessage(Alert.AlertType.ERROR,"Error","Valide que se ha seleccionado " +
                    "un proveedor o un producto y el periodo de ventas ");
        }else {
            int daysOrder =0;
            if(rBWeekly.isSelected()){
                daysOrder =7;
            }else if(rBBiWeekly.isSelected()) {
                daysOrder = 15;
            }else {
                daysOrder = 30;
            }
            products = productMethods.orderProducts(providerSearch,dayConsult);
            for(Product productOrder : products){
                float saleDiary = (float)productOrder.getAmountSale()/dayConsult;
                if(saleDiary >= 0.22 || productOrder.getAmount()<2){
                    float sale = saleDiary*daysOrder;
                    if(sale>productOrder.getAmount() || (productOrder.getAmount()<2 && sale > 0)){
                        productOrder.setAmountSale((int)sale-productOrder.getAmount());
                    }
                    else {
                        productOrder.setAmountSale(0);
                    }
                }else
                    productOrder.setAmountSale(0);

            }
            products.removeIf(product -> product.getAmountSale()==0);
            tblProduct.setItems(products);
        }
    }

    private void alertMessage(Alert.AlertType alertType,String title,String message){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

}
