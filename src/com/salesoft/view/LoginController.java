/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.view;

import com.salesoft.DAO.DatabaseConnection;
import com.salesoft.MainApp;
import com.salesoft.custom.CustomPf;
import com.salesoft.custom.CustomTf;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Ramin
 */
public class LoginController implements Initializable {

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    //esas AnchorPane . MainStyle buna yuklenir
    @FXML
    private AnchorPane apMother;

    @FXML
    private AnchorPane apDesignPane;

    //istifadeci adi yazilan TextField
    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField userPasswordField;

    @FXML
    private Button btnUserNameFieldClear;
    @FXML
    private Button btnUserPasswordFieldClear;

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink hlCreateAccount;

    @FXML
    private Hyperlink hlDatabase;

    CustomTf cTF = new CustomTf();
    CustomPf cPF = new CustomPf();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cTF.clearTextFieldByButton(userNameField, btnUserNameFieldClear);
        cPF.clearPassFieldByButton(userPasswordField, btnUserPasswordFieldClear);
        BooleanBinding boolenBinding = userNameField.textProperty().isEmpty()
                .or(userPasswordField.textProperty().isEmpty());

        btnLogin.disableProperty().bind(boolenBinding);
        userNameField.requestFocus();

    }

    @FXML
    private void btnLogin(ActionEvent event) throws IOException {
        con = DatabaseConnection.getConnection();
        String db = DatabaseConnection.DB_NAME;

        if (con != null) {
            if (isValidCondition()) {
                try {
                    pst = con.prepareStatement("select * from " + db + ".User where UsrName=? and Password=? and Status=1");
                    pst.setString(1, userNameField.getText());
                    pst.setString(2, userPasswordField.getText());
                    rs = pst.executeQuery();
                    if (rs.next()) {

                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(MainApp.class.getResource("view/Application.fxml"));

                            Parent root = (Parent) loader.load();
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("SaleSoft - a Xöş Gəlmisiniz: " + rs.getString(3));
                            stage.getIcons().add(new Image("com/salesoft/image/icon.png"));
                            stage.setMaximized(true);
                            stage.show();

                        } catch (IOException ex) {
                            System.out.println(ex.getLocalizedMessage());
                        }

                        Stage stage = (Stage) btnLogin.getScene().getWindow();
                        stage.close();
                    } else {
                        System.out.println("Password Not Match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Password Not Match");
                        alert.setHeaderText("Error : Name or Pass Not matched");
                        alert.setContentText("User Name or Password not matched \ntry Again");
                        alert.initStyle(StageStyle.UNDECORATED);
                        alert.showAndWait();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error : Server Not Found");
            alert.setContentText("Make sure your mysql is Start properly, \n");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        }

    }

    private boolean isValidCondition() {
        boolean validCondition;
        if (userNameField.getText().trim().isEmpty()
                || userPasswordField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING :");
            alert.setHeaderText("WARNING !!");
            alert.setContentText("Please Fill Text Field And Password Properly");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();

            validCondition = false;
        } else {
            validCondition = true;
        }
        return validCondition;
    }

    @FXML
    private void hlCreateAnAccount(ActionEvent event) throws IOException {
        con = DatabaseConnection.getConnection();

        String db = DatabaseConnection.DB_NAME;

        if (con != null) {
            try {
                pst = con.prepareStatement("SELECT Id FROM " + db + ".User ORDER BY Id ASC LIMIT 1");
                rs = pst.executeQuery();
                if (rs.next()) {
                    apMother.setOpacity(0.7);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("You can't create an account without admin \n permission");
                    alert.initStyle(StageStyle.UNDECORATED);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        apMother.setOpacity(1.0);
                    }
                    return;
                }
                con.close();
                pst.close();
                rs.close();
                loadRegistration();

            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error : Server Not Found");
            alert.setContentText("Make sure your mysql is Start properly, \n");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        }

    }

    private void loadRegistration() {
        Parent root = null;
        try {
            root = FXMLLoader.load(MainApp.class.getResource("/view/Registration.fxml"));
            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setScene(scene);
            nStage.setMaximized(true);
            nStage.setTitle("Registration - Sale Soft");
            nStage.show();
            Stage stage = (Stage) hlCreateAccount.getScene().getWindow();
            stage.close();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void hlDbOnAction(ActionEvent event) {
        System.out.println("com.salesoft.view.LoginController.hlDbOnAction()");
        Parent root;
        try {
            root = FXMLLoader.load(MainApp.class.getResource("view/Server.fxml"));
            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setScene(scene);
            nStage.setMaximized(false);
            nStage.setTitle("Server Configure - Sale Soft");
            nStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
