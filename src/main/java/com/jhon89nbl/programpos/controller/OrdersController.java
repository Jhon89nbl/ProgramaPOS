package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.Product;
import com.jhon89nbl.programpos.model.ProductMethods;
import com.jhon89nbl.programpos.model.Provider;
import com.jhon89nbl.programpos.model.ProviderMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

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
        System.out.println(order);
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
        rBWeekly.setSelected(true);
    }

    @FXML
    private ComboBox<Provider> cmbProvider;
    @FXML
    private ComboBox<String> cmbPeriodTime;

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
    private RadioButton rBMonthly;

    @FXML
    private RadioButton rBWeekly;

    @FXML
    void orderExport(ActionEvent event) {

    }



    @FXML
    void order(ActionEvent event) {
        String providerSearch = (cmbProvider.getSelectionModel().getSelectedItem()==null)?"":
                String.valueOf(cmbProvider.getSelectionModel().getSelectedItem().getName());
        int dayConsult = (cmbPeriodTime.getSelectionModel().getSelectedItem()==null) ? 0:
                period.get(cmbPeriodTime.getSelectionModel().getSelectedItem());
        if(Objects.equals(providerSearch, "")|| dayConsult==0){
            //todo
        }else {
            float divider =0.0f;
            System.out.println(dayConsult);
            if(rBWeekly.isSelected()){
                divider =(float) dayConsult/7;
            }else {
                divider = (float)dayConsult/30;
            }
            System.out.println(divider);
            products = productMethods.orderProducts(providerSearch,dayConsult);
            for(Product productOrder : products){
                float pedido = (productOrder.getAmountSale()/divider)-productOrder.getAmount();
                if(pedido > 0 || (productOrder.getAmount() <=5 && productOrder.getAmount()>0)){
                    System.out.println(pedido);
                }
            }
            System.out.println(products);
        }
    }


}
