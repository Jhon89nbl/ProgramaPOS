package com.jhon89nbl.programpos.controller;


import com.jhon89nbl.programpos.model.UserMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;


import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LoginController implements Initializable {
    // se traen los compoenentes de la vista
    @FXML
    private PasswordField edtPass;

    @FXML
    private TextField edtUser;

    @FXML
    private Label lblSucces;

    private final UserMethods userModel = new UserMethods();

    @FXML
    void btnLoginEvent(ActionEvent evt) throws SQLException  {
        validLogin((Node)evt.getSource());
    }
    //evento para validar login con enter
    @FXML
    void edtKeyReleased(KeyEvent event) throws SQLException  {
        if(event.getCode().equals(KeyCode.ENTER)){
            validLogin((Node)event.getSource());
        }
    }

    private void closeWindows(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    //metodo para validar enter
    private void validLogin(Node node) throws SQLException {
        //se captura usuario y contraseña
        String user = edtUser.getText();
        String pass = edtPass.getText();
        //se validan que los campos no esten vacios
        boolean valid = userModel.fieldsEmpty(user,pass);
        //si no estan vacios se valida que los usuarios esten registrados
        if(valid){
            if(userModel.validUSer(user,pass)){
                Parent root = null;
                try {
                    URL fxmURL = Paths.get("src/main/resources/com/jhon89nbl/programpos/principal-view.fxml").toUri().toURL();
                    root = FXMLLoader.load(fxmURL);

                }catch (IOException e){
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE,null,e);
                }
                Rectangle2D screenBounds = Screen.getPrimary().getBounds();
                int widthScreen = (int) screenBounds.getWidth();
                int heigthScreen = (int) screenBounds.getHeight();
                Scene scene = new Scene(root,widthScreen*0.90, heigthScreen*0.90);
                Stage stage = new Stage();
                stage.setTitle("Principal");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
                closeWindows(node);

            }else {
                lblSucces.setText("Error de usuario o contraseña");
                lblSucces.setVisible(true);
            }

        }else{
            lblSucces.setText("Los campos no pueden estar vacios");
            lblSucces.setVisible(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        edtPass.requestFocus();
    }
}