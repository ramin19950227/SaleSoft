package com.salesoft.controller;

import com.salesoft.DAO.impl.UserDAO;
import com.salesoft.custom.CustomPf;
import com.salesoft.custom.CustomTf;
import com.salesoft.util.MyFXMLLoader;
import com.salesoft.util.MyProperties;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author rifat
 */
public class RegistrationController implements Initializable {

    @FXML
    private Hyperlink hlLogin;
    @FXML
    private TextField tfUserName;
    @FXML
    private TextField tfFullName;
    @FXML
    private PasswordField pfUserPassword;
    @FXML
    private PasswordField pfReUserPassword;
    @FXML
    private Button btnClearUserName;
    @FXML
    private Button btnClearFullName;
    @FXML
    private Button btnClearPass;
    @FXML
    private Button btnClearRePass;
    @FXML
    private Button btnSignUp;

    private Stage stage;

    private final UserDAO UserDAO = new UserDAO();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CustomPf cPF = new CustomPf();
        CustomTf cTF = new CustomTf();

        cTF.clearTextFieldByButton(tfUserName, btnClearUserName);
        //cTF.clearTextFieldByButton(tfFullName, btnClearFullName);
        cPF.clearPassFieldByButton(pfUserPassword, btnClearPass);
        cPF.clearPassFieldByButton(pfReUserPassword, btnClearRePass);

        BooleanBinding boolenBinding = tfUserName.textProperty().isEmpty()
                // .or(tfFullName.textProperty().isEmpty()
                .or(pfUserPassword.textProperty().isEmpty())
                .or(pfReUserPassword.textProperty().isEmpty()) //)
                ;

        btnSignUp.disableProperty().bind(boolenBinding);
    }

    @FXML
    private void hlLogin(ActionEvent event) throws IOException {

        Stage nStage = new Stage();
        String loginViewTitle = MyProperties.getUIProperties().getApplicationTitle();

        /**
         * Login Sehifemizin URL addressi
         */
        URL loginViewURL = MyProperties.getURLProperties().getLoginFxmlURL();

        /**
         * Login Sehifemizin Scene obyekti
         */
        Scene loginViewScene = MyFXMLLoader.getSceneFromURL(loginViewURL);

        nStage.setScene(loginViewScene);

        nStage.setTitle(loginViewTitle);

        nStage.getIcons().add(new Image("com/salesoft/image/icon.png"));
        nStage.setMaximized(true);

        nStage.show();

        Stage hlLoginStage = (Stage) hlLogin.getScene().getWindow();
        hlLoginStage.close();
    }

    @FXML
    private void btnRegistration(ActionEvent event) {
        if (isValidCondition()) {
            String userName = tfUserName.getText();
            String fullName = tfUserName.getText();
            String password = pfUserPassword.getText();

            if (UserDAO.registration(userName, password, fullName, 1)) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Daxil Ol");
                alert.setHeaderText("Daxil Ol");
                alert.setContentText("Sizin admin account-unuz Ugurla Qeydiyyata Alindi \n Daxil olmaq ucun OK-duymesine basin");
                alert.initStyle(StageStyle.UNDECORATED);
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Stage stage = (Stage) btnClearPass.getScene().getWindow();
                    stage.close();
                }
            };

        }
    }

    private boolean isValidCondition() {
        boolean registration = false;
        if (nullChecq() && passMatch()) {
            System.out.println("Condition valid");
            registration = true;
        } else {
            System.out.println("Condition Invalid");
            registration = false;
        }
        return registration;
    }

    private boolean nullChecq() {
        boolean nullChecq = false;
        if (tfUserName.getText().trim().isEmpty()
                // || tfFullName.getText().trim().isEmpty() - heleki full nameni yigishdiriram
                || pfUserPassword.getText().isEmpty()
                || pfReUserPassword.getText().isEmpty()) {

            System.out.println("Empty user Name");
            return false;
        } else {
            System.out.println("User Name not Empty");
            return true;
        }
    }

    private boolean passMatch() {
        boolean passMatch = false;
        String password = pfUserPassword.getText();
        String rePass = pfReUserPassword.getText();

        if (password.matches(rePass)) {
            System.out.println("Password Match");
            passMatch = true;

        } else {
            System.out.println("Password Not Match");
            passMatch = false;
        }
        return passMatch;

    }

    @FXML
    private void pfKeyTyped(KeyEvent event) {
        if (pfUserPassword.getText().matches(pfReUserPassword.getText())) {
            System.out.println("Match");
        } else {
            System.out.println("Not Match");
        }
    }

}
