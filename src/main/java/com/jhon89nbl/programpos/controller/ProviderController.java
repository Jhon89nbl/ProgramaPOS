package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.Provider;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class ProviderController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



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
    private TextField edtCost;

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
    private RadioButton chkVisit;

    @FXML
    private RadioButton chkWed;

    @FXML
    private RadioButton chkWhats;


    @FXML
    private TableView<Provider> tblProvider;

    @FXML
    void addProvider(ActionEvent event) {

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

}
