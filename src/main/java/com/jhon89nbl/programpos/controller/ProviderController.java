package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.Provider;
import com.jhon89nbl.programpos.model.ProviderMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class ProviderController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        providers = FXCollections.observableArrayList();
        providerMethods = new ProviderMethods();
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNit.setCellValueFactory(new PropertyValueFactory<>("nit"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAdress.setCellValueFactory(new PropertyValueFactory<>("adress"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colOrderDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        colOrderMethod.setCellValueFactory(new PropertyValueFactory<>("channel"));
        tblProvider.setItems(providers);
    }
    private ObservableList<Provider> providers;
    private ProviderMethods providerMethods;


    @FXML
    private TableColumn<Provider, String> colAdress;

    @FXML
    private TableColumn<Provider, String> colEmail;

    @FXML
    private TableColumn<Provider, String> colName;

    @FXML
    private TableColumn<Provider, String> colNit;

    @FXML
    private TableColumn<Provider, Provider.orderDay> colOrderDay;

    @FXML
    private TableColumn<Provider, Provider.channelOrder> colOrderMethod;

    @FXML
    private TableColumn<Provider, String> colPhone;

    @FXML
    private TextField edtAdress;

    @FXML
    private TextField edtPhone;

    @FXML
    private TextField edtEmail;

    @FXML
    private TextField edtNameProvider;

    @FXML
    private TextField edtNit;
    @FXML
    private RadioButton chkEmail;

    @FXML
    private RadioButton chkFri;

    @FXML
    private RadioButton chkMon;

    @FXML
    private RadioButton chkOther;

    @FXML
    private RadioButton chkVisit;

    @FXML
    private RadioButton chkPhone;

    @FXML
    private RadioButton chkSat;

    @FXML
    private RadioButton chkSun;

    @FXML
    private RadioButton chkThu;

    @FXML
    private RadioButton chkTue;

    @FXML
    private RadioButton chkApliccation;

    @FXML
    private RadioButton chkWed;

    @FXML
    private RadioButton chkWhats;


    @FXML
    private TableView<Provider> tblProvider;

    @FXML
    void addProvider(ActionEvent event) {
        Provider provider = chargerProvider();
        List<String> fieldsProvider = providerMethods.fieldsEmpty(provider);
        if(fieldsProvider.isEmpty()){
            boolean validNameProvider = false;
            for(Provider providerValid:providers ){
                if(providerValid.getName().equalsIgnoreCase(provider.getName())||
                        providerValid.getNit().equalsIgnoreCase(provider.getNit())){
                    validNameProvider=true;
                }
            }
            if (validNameProvider){
                alertMessage(Alert.AlertType.INFORMATION,"Validar","El proveedor ya se encuentra en la tabla " +
                        "pulse modificar para guardar los cambios ");
            }else {
                providers.add(provider);
                tblProvider.refresh();
                cleanFields();
            }
        }else {
            alertMessage(Alert.AlertType.WARNING,"Error","Los siguientes campos estan vacios: "+ fieldsProvider);
        }

    }
    @FXML
    void modifyProvider(ActionEvent event) {
        Provider modifyProvider= tblProvider.getSelectionModel().getSelectedItem();
        if(modifyProvider== null){
            alertMessage(Alert.AlertType.ERROR,"Error","Debe seleccionar un proveedor para modificarlo");
        }else {
            Provider tempProvider = chargerProvider();
            List<String> fieldEmpty= providerMethods.fieldsEmpty(tempProvider);
            if(fieldEmpty.isEmpty()){
                if(!providers.contains(tempProvider)){
                    modifyProvider.setName(tempProvider.getName());
                    modifyProvider.setNit(tempProvider.getNit());
                    modifyProvider.setPhone(tempProvider.getPhone());
                    modifyProvider.setEmail(tempProvider.getEmail());
                    modifyProvider.setAdress(tempProvider.getAdress());
                    modifyProvider.setDay(tempProvider.getDay());
                    modifyProvider.setChannel(tempProvider.getChannel());
                    tblProvider.refresh();
                    cleanFields();
                }
            }
        }
    }




    @FXML
    void deleteProvider(KeyEvent event) {
        if(event.getCode()== KeyCode.DELETE){
            Provider provider = tblProvider.getSelectionModel().getSelectedItem();
            providers.remove(provider);
            tblProvider.refresh();
        }
    }

    @FXML
    void saveProviders(ActionEvent event) throws SQLException {
        providers = providerMethods.saveProvider(providers);
        if(providers.isEmpty()){
            alertMessage(Alert.AlertType.INFORMATION,"Exito","Creado Correctamente");
        }else {
            alertMessage(Alert.AlertType.ERROR,"Error","No se pudo crear el proveedor " +
                    "ya que el nombre o el nit ya existen" + providers);
        }
        tblProvider.setItems(providers);

    }

    @FXML
    void selectionProvider(MouseEvent event) {
        if (event.getClickCount()==2) {
            Provider provider = tblProvider.getSelectionModel().getSelectedItem();
            if(provider== null){
                alertMessage(Alert.AlertType.WARNING,"Validar","Seleccione un proveedor de la tabla");
            }else {
                edtNameProvider.setText(provider.getName());
                edtNit.setText(provider.getNit());
                edtAdress.setText(provider.getAdress());
                edtPhone.setText(provider.getPhone());
                edtEmail.setText(provider.getEmail());
                for(Provider.orderDay orderDay: provider.getDay()){
                    switch (orderDay){
                        case LUNES -> chkMon.setSelected(true);
                        case MARTES -> chkTue.setSelected(true);
                        case MIERCOLES -> chkWed.setSelected(true);
                        case JUEVES -> chkThu.setSelected(true);
                        case VIERNES -> chkFri.setSelected(true);
                        case SABADO -> chkSat.setSelected(true);
                        case DOMINGO -> chkSun.setSelected(true);
                    }
                }
                for (Provider.channelOrder channelOrder : provider.getChannel()){
                    switch (channelOrder){
                        case email -> chkEmail.setSelected(true);
                        case phone -> chkPhone.setSelected(true);
                        case application -> chkPhone.setSelected(true);
                        case whatsapp -> chkWhats.setSelected(true);
                        case visitor -> chkVisit.setSelected(true);
                        case other -> chkOther.setSelected(true);
                    }
                }
            }
        }

    }

    private Provider chargerProvider() {
        Provider providerTemp = new Provider();
        providerTemp.setName(edtNameProvider.getText());
        providerTemp.setNit(edtNit.getText());
        providerTemp.setAdress(edtAdress.getText());
        providerTemp.setPhone(edtPhone.getText());
        providerTemp.setEmail(edtEmail.getText());
        providerTemp.setDay(validCheckboxOrderDay());
        providerTemp.setChannel(validCheckboxOrderChanel());
        return providerTemp;

    }

    private List<Provider.orderDay> validCheckboxOrderDay() {
        List<Provider.orderDay> orderDay = new ArrayList<>();

        if(chkMon.isSelected()){
            orderDay.add(Provider.orderDay.LUNES);
        }
        if(chkTue.isSelected()){
            orderDay.add(Provider.orderDay.MARTES);
        }
        if(chkWed.isSelected()){
            orderDay.add(Provider.orderDay.MIERCOLES);
        }
        if(chkThu.isSelected()){
            orderDay.add(Provider.orderDay.JUEVES);
        }
        if(chkFri.isSelected()){
            orderDay.add(Provider.orderDay.VIERNES);
        }
        if(chkSat.isSelected()){
            orderDay.add(Provider.orderDay.SABADO);
        }
        if(chkSun.isSelected()){
            orderDay.add(Provider.orderDay.DOMINGO);
        }
        return orderDay;
    }
    private List<Provider.channelOrder> validCheckboxOrderChanel() {
        List<Provider.channelOrder> orderChannel = new ArrayList<>();
        if(chkEmail.isSelected()){
            orderChannel.add(Provider.channelOrder.email);
        }
        if(chkPhone.isSelected()){
            orderChannel.add(Provider.channelOrder.phone);
        }
        if(chkWhats.isSelected()){
            orderChannel.add(Provider.channelOrder.whatsapp);
        }
        if(chkVisit.isSelected()){
            orderChannel.add(Provider.channelOrder.visitor);
        }
        if(chkApliccation.isSelected()){
            orderChannel.add(Provider.channelOrder.application);
        }
        if(chkOther.isSelected()){
            orderChannel.add(Provider.channelOrder.other);
        }
        return orderChannel;
    }

    private void alertMessage(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
    private void cleanFields() {
        edtNameProvider.setText("");
        edtNit.setText("");
        edtAdress.setText("");
        edtEmail.setText("");
        edtPhone.setText("");
        chkMon.setSelected(false);
        chkTue.setSelected(false);
        chkWed.setSelected(false);
        chkThu.setSelected(false);
        chkFri.setSelected(false);
        chkSat.setSelected(false);
        chkSun.setSelected(false);
        chkEmail.setSelected(false);
        chkVisit.setSelected(false);
        chkApliccation.setSelected(false);
        chkWhats.setSelected(false);
        chkOther.setSelected(false);
        chkPhone.setSelected(false);
    }

}
