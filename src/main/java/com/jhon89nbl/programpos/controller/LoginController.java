package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.UserMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    void validUser(ActionEvent evt) throws SQLException {
        String user = edtUser.getText();
        String pass = edtPass.getText();
        boolean valid = userModel.fieldsEmpty(user,pass);
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