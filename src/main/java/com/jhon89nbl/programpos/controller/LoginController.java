package com.jhon89nbl.programpos.controller;


import com.jhon89nbl.programpos.model.UserMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;


import java.util.logging.Level;
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
    void btnLoginEvent(ActionEvent evt) throws SQLException, IOException {
        validLogin();
        closeWindows((Node)evt.getSource());
    }
    //evento para validar login con enter
    @FXML
    void edtKeyReleased(KeyEvent event) throws SQLException, IOException {
        if(event.getCode().equals(KeyCode.ENTER)){
            validLogin();
            closeWindows((Node)event.getSource());

        }
    }

    private void closeWindows(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    //metodo para validar enter
    private void validLogin() throws SQLException, IOException {
        //se captura usuario y contraseña
        String user = edtUser.getText();
        String pass = edtPass.getText();
        //se validan que los campos no esten vacios
        boolean valid = userModel.fieldsEmpty(user,pass);
        //si no estan vacios se valida que los usuarios esten registrados
        if(valid){
            if(userModel.validUSer(user,pass)){
                try {
                    URL fxmURL = Paths.get("src/main/resources/com/jhon89nbl/programpos/principal-view.fxml").toUri().toURL();
                   Parent root = FXMLLoader.load(fxmURL);
                   Scene scene = new Scene(root);
                   Stage stage = new Stage();
                   stage.setTitle("Principal");
                   stage.setScene(scene);
                   stage.show();

                }catch (IOException e){
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE,null,e);
                }

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