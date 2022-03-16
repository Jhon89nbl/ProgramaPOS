package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.UserMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class LoginController {
    // se traen los compoenentes de la vista
    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField edtPass;

    @FXML
    private TextField edtUser;

    @FXML
    private Label lblSucces;


    private final UserMethods userModel = new UserMethods();

    @FXML
    void btnLoginEvent(ActionEvent evt) throws SQLException {
        validLogin();
    }
    //evento para validar login con enter
    @FXML
    void edtKeyReleased(KeyEvent event) throws SQLException {
        if(event.getCode().equals(KeyCode.ENTER)){
            validLogin();
        }
    }

    //metodo para validar enter
    private void validLogin() throws SQLException {
        //se captura usuario y contraseña
        String user = edtUser.getText();
        String pass = edtPass.getText();
        //se validan que los campos no esten vacios
        boolean valid = userModel.fieldsEmpty(user,pass);
        //si no estan vacios se valida que los usuarios esten registrados
        if(valid){
            if(userModel.validUSer(user,pass)){
                Alert alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Info");
                alert.setContentText("conexion Valida");
                alert.showAndWait();
            }else {
                lblSucces.setText("Error de usuario o contraseña");
                lblSucces.setVisible(true);
            }

        }else{
            lblSucces.setText("Los campos no pueden estar vacios");
            lblSucces.setVisible(true);
        }
    }


}