package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.Provider;
import com.jhon89nbl.programpos.model.ProviderMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
            providers.add(provider);
            tblProvider.refresh();
        }else {
            for (int i = 0; i < fieldsProvider.size(); i++) {
                //if(fieldsProvider.get(i) ==" ")
            }
        }

    }




    @FXML
    void deleteProvider(KeyEvent event) {

    }

    @FXML
    void saveProviders(ActionEvent event) {

    }

    @FXML
    void selectionProvider(MouseEvent event) {

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

}
