/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.controller;

import com.salesoft.DAO.DatabaseConnection;
import com.salesoft.DAO.UserDAO;
import com.salesoft.custom.CustomPf;
import com.salesoft.custom.CustomTf;
import com.salesoft.database.DBUtil;
import com.salesoft.util.MyFXMLLoader;
import com.salesoft.util.MyLogger;
import com.salesoft.util.MyProperties;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    UserDAO UserDAO = new UserDAO();

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

        if (isInputValid()) {

            boolean isUserValid = UserDAO.login(userNameField.getText(), userPasswordField.getText());

            if (isUserValid) {

                Stage stage = new Stage();
                stage.centerOnScreen();
                stage.setScene(MyFXMLLoader.getSceneFromURL(MyProperties.getURLProperties().getApplicationFxmlURL()));
                stage.setTitle("SaleSoft - a Xöş Gəlmisiniz: ");
                stage.getIcons().add(new Image("com/salesoft/image/icon.png"));
                stage.setMaximized(true);
                stage.show();

                Stage oldStage = (Stage) btnLogin.getScene().getWindow();
                oldStage.close();

            } else {
                System.out.println("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("İstifadəçi adı və ya Şifrə Yanlışdır");
                alert.setHeaderText("Xəta : İstifadəçi adı və ya Şifrə Yanlışdır");
                alert.setContentText("İstifadəçi adı və ya Şifrə Yanlışdır \nYenidən cəhd edin");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
            }

        }
    }

    private boolean isInputValid() {
        boolean validCondition;
        if (userNameField.getText().trim().isEmpty()
                || userPasswordField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Xəta :");
            alert.setHeaderText("Xəta !!");
            alert.setContentText("Zəhmət Olmasa İstifadəçi adı və Şifrəni Daxil edin");
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

        String db = MyProperties.getDBProperties().getDbName();

        try {
            ResultSet rs = DBUtil.directExecuteQuery(("SELECT Id FROM " + db + ".User ORDER BY Id ASC LIMIT 1"));
            if (rs.next()) {
                System.out.println(rs.getString(1));
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
            DBUtil.allDisconnect();

            loadRegistration();
        } catch (SQLException ex) {
            System.out.println("SQLException -  LoginController.hlCreateAnAccount(): " + ex);
            MyLogger.logException("SQLException - LoginController.hlCreateAnAccount()", ex);
        }

    }

    private void loadRegistration() {
        Stage nStage = new Stage();
        nStage.setScene(MyFXMLLoader.getSceneFromURL(MyProperties.getURLProperties().getRegistrationURL()));
        nStage.setMaximized(true);
        nStage.setTitle("Qeydiyyat - Sale Soft");
        nStage.show();

        Stage stage = (Stage) hlCreateAccount.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void hlDbOnAction(ActionEvent event) {
        DBUtil.showServerConfigView();
    }

}
