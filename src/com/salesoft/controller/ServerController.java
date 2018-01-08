package com.salesoft.controller;

import com.salesoft.database.DBUtil;
import com.salesoft.model.Properties.DBProperties;
import com.salesoft.util.Initializator;
import com.salesoft.util.MyProperties;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ServerController implements Initializable {

    @FXML
    private TextField tfHost;
    @FXML
    private TextField thPort;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Button btnConnect;
    @FXML
    private Button btnReset;
    @FXML
    private Label lablServerStatus;
    @FXML
    private TextField tfDBName;
    @FXML
    private TextField tfUserName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // serverle elaqeni yoxlayaq ve Labelde neticeni gosterek:
        if (DBUtil.mySQLIsServerRunning()) {
            lablServerStatus.setText("Server Ile Elaqe QURULDU");
        } else {
            lablServerStatus.setText("Server ile Elaqe Yoxdur");
        }

        loadProperties();
    }

    @FXML
    private void btnConnectOnAction(ActionEvent event) {
        DBProperties dbp = getDBPropertiesFromFields();
        if (dbp != null) {
            //melumatlarimizi yaziriq yadasha
            MyProperties.saveDBProperties(dbp);

            // ayarlama penceresini baglayiriq 
            Stage stage = (Stage) thPort.getScene().getWindow();
            stage.close();

            //ve bashlayiriq InitDataBase() - metoduna bashdan yoxlamaga
            //Initializator.initDataBase();
        } else {
            System.err.println("Kecerli Melumat Daxil edin");
        }

    }

    @FXML
    private void btnResetOnAction(ActionEvent event) {
    }

    /**
     * Metod Alinan DBProperties obyektini Xanalara doldurur
     *
     * @param dbp
     */
    public void setDBPropertiesToFields(DBProperties dbp) {

        tfHost.setText(dbp.getHost());
        tfDBName.setText(dbp.getDbName());
        tfUserName.setText(dbp.getUser());
        pfPassword.setText(dbp.getPassword());
        thPort.setText(dbp.getPort().toString());

    }

    public DBProperties getDBPropertiesFromFields() {

        // eger kecerli melumatlar daxil edilibse
        if (isInputValid()) {
            String user = tfUserName.getText().trim();
            String password = pfPassword.getText().trim();
            String host = tfHost.getText().trim();
            Integer port = Integer.parseInt(thPort.getText().trim());
            String dbName = tfDBName.getText().trim();

            return new DBProperties(user, password, host, port, dbName);
        } else {
            return null;
        }

//        try {
//            if (dbConnect()) {
//                con.close();
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Server connect successfully");
//                alert.setHeaderText("Login now");
//                alert.setContentText("Server has been connected sucessfully \n to login now click ok");
//                alert.initStyle(StageStyle.UNDECORATED);
//                Optional<ButtonType> result = alert.showAndWait();
//                if (result.isPresent() && result.get() == ButtonType.OK) {
//                    Stage stage = (Stage) thPort.getScene().getWindow();
//                    stage.close();
//                }
//            } else {
//                Alert error_alert = new Alert(Alert.AlertType.ERROR);
//                error_alert.setTitle("Can't connect with mysql");
//                error_alert.setHeaderText("Can't connect to mysql server");
//                error_alert.setContentText("Try again");
//                error_alert.initStyle(StageStyle.UNDECORATED);
//                error_alert.show();
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void loadProperties() {

        DBProperties dbp = MyProperties.getDBProperties();
        setDBPropertiesToFields(dbp);
    }

    private boolean dbConenct() {

        return false;
    }

    /**
     * Xanalara daxil edilmish melumatlari yoxlayir ve Melumatlar Dogru daxil
     * edilibse yani portyerine meselen herif yazmayibsa ve ya xanani bosh
     * buraxmayibsa o zaman true qaytarir
     *
     * @return
     */
    private Boolean isInputValid() {
        System.out.println("com.salesoft.controller.ServerController.isInputValid()");
        System.err.println("TODO - yaxinda hazirlayacam");
        //heleki avtonomnu ishleyir sonradan duzeldecem
        return true;
    }

}
