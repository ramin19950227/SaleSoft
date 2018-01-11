/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.controller;

//import Getway.UsersGetway;
//import controller.application.EmployeController;
//import controller.application.SellController;
//import controller.application.SettingsController;
//import controller.application.StockController;
//import controller.application.home.HomeController;
//import dataBase.DBConnection;
//import dataBase.DBProperties;
import com.salesoft.MainApp;
import com.salesoft.util.MyFXMLLoader;
import com.salesoft.util.MyProperties;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
//import media.userNameMedia;

/**
 * FXML Controller class
 *
 * @author rifat
 */
public class ApplicationController implements Initializable {

    private static ApplicationController applicationController;

    public static ApplicationController getApplicationController() {
        return applicationController;
    }

    @FXML
    private StackPane acContent;
    @FXML
    private ScrollPane leftSideBarScroolPan;
    @FXML
    private ToggleButton sideMenuToogleBtn;
    @FXML
    private ImageView imgMenuBtn;
    @FXML
    private BorderPane appContent;
    @FXML
    private Button btnLogOut;
    @FXML
    private MenuItem miPopOver;
    @FXML
    private AnchorPane acDashBord;
    @FXML
    private AnchorPane acHead;
    @FXML
    private AnchorPane acMain;
    @FXML
    private MenuButton mbtnUsrInfoBox;
    @FXML
    private Button btnHome;
    @FXML
    private ImageView imgHomeBtn;
    @FXML
    private Button btnStore;
    @FXML
    private ImageView imgStoreBtn;
    @FXML
    private Button btnEmplopye;
    @FXML
    private ImageView imgEmployeBtn;
    @FXML
    private Button btnSell;
    @FXML
    private ImageView imgSellBtn;
    @FXML
    private Button btnSettings;
    @FXML
    private ImageView imgSettingsBtn;
    @FXML
    private Button btnAbout;
    @FXML
    private ImageView imgAboutBtn;
    @FXML
    private Label lblUsrName;
    @FXML
    private Label lblUsrNamePopOver;
    @FXML
    private Label lblFullName;
    @FXML
    private Label lblRoleAs;
    @FXML
    private Hyperlink hlEditUpdateAccount;
    @FXML
    private Circle imgUsrTop;
    @FXML
    private Circle circleImgUsr;
    @FXML
    private Label lblUserId;

    String usrName;
    String id;

    //DBConnection dbCon = new DBConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    //Users users = new Users();
//    UsersGetway usersGetway = new UsersGetway();
//
//    private userNameMedia usrNameMedia;
//
//    public userNameMedia getUsrNameMedia() {
//        return usrNameMedia;
//    }
//    public void setUsrNameMedia(userNameMedia usrNameMedia) {
//        lblUserId.setText(usrNameMedia.getId());
//        lblUsrName.setText(usrNameMedia.getUsrName());
//        id = usrNameMedia.getId();
//        usrName = usrNameMedia.getUsrName();
//
//        this.usrNameMedia = usrNameMedia;
//    }
    Image menuImage = new Image("/icon/menu.png");
    Image menuImageRed = new Image("/icon/menuRed.png");
    //Image image;

    String defultStyle = "-fx-border-width: 0px 0px 0px 5px;"
            + "-fx-border-color:none";

    String activeStyle = "-fx-border-width: 0px 0px 0px 5px;"
            + "-fx-border-color:#FF4E3C";

    Image home = new Image("/icon/home.png");
    Image homeRed = new Image("/icon/homeRed.png");
    Image stock = new Image("/icon/stock.png");
    Image stockRed = new Image("/icon/stockRed.png");
    Image sell = new Image("/icon/sell2.png");
    Image sellRed = new Image("/icon/sell2Red.png");
    Image employee = new Image("/icon/employe.png");
    Image employeeRed = new Image("/icon/employeRed.png");
    Image setting = new Image("/icon/settings.png");
    Image settingRed = new Image("/icon/settingsRed.png");
    Image about = new Image("/icon/about.png");
    Image aboutRed = new Image("/icon/aboutRed.png");

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgMenuBtn.setImage(menuImage);
        Image usrImg = new Image("/image/rifat.jpg");

        imgUsrTop.setFill(new ImagePattern(usrImg));
        circleImgUsr.setFill(new ImagePattern(usrImg));

        // Proqram ishe dushen kimi Home Duymesi basilmish kimi Simulyasiya edek
        btnHomeOnClick(new ActionEvent());

        //Obyektimizi Statik Deyishkene yaziram ki Qiraqdan muraciet ede bilim
        applicationController = this;
        //artiq referanslar ucun yeni classimiz var
        ControllersRef.ac = this;

    }

    @FXML
    private void sideMenuToogleBtnOnCLick(ActionEvent event) throws IOException {
        if (sideMenuToogleBtn.isSelected()) {
            imgMenuBtn.setImage(menuImageRed);
            TranslateTransition sideMenu = new TranslateTransition(Duration.millis(200.0), acDashBord);
            sideMenu.setByX(-130);
            sideMenu.play();
            acDashBord.getChildren().clear();
        } else {
            imgMenuBtn.setImage(menuImage);
            TranslateTransition sideMenu = new TranslateTransition(Duration.millis(200.0), acDashBord);
            sideMenu.setByX(130);
            sideMenu.play();
            acDashBord.getChildren().add(leftSideBarScroolPan);
        }
    }

    @FXML
    private void btnLogOut(ActionEvent event) throws IOException {
        acContent.getChildren().clear();
        acContent.getChildren().add(FXMLLoader.load(MainApp.class.getResource("view/Login.fxml")));
        acDashBord.getChildren().clear();
        acHead.getChildren().clear();
        acHead.setMaxHeight(0.0);
    }

    @FXML
    private void acMain(KeyEvent event) {
        if (event.getCode() == KeyCode.F11) {
            Stage stage = (Stage) acMain.getScene().getWindow();
            stage.setFullScreenExitHint("Full Ekran modundan çıxmaq üçün (ESC və ya F11) düyməsini basın");

            if (stage.isFullScreen()) {
                stage.setFullScreen(false);
            } else {
                stage.setFullScreen(true);
            }

        }
    }

    /**
     * Home Duymesi basildiqda Bu metod ishe Dushur
     *
     * @param event
     */
    @FXML
    public void btnHomeOnClick(ActionEvent event) {

        //Metodun aciqlamasina baxin
        homeActive();

        //indi ise Home Sehifemizi Gosterek
        // bu sehifenin Controlleri bize lazim deyil
        acContent.getChildren().clear();
        acContent.getChildren().add(MyFXMLLoader.getAnchorPaneFromURL(MyProperties.getURLProperties().getHomeFxmlURL()));

    }

    /**
     *
     */
    @FXML
    public void btnStockOnClick() {
        // bu kod secilen buttonun stilini ve shekllini deyishdirir qirmisi olanla yani selected
        stockActive();

        //bu Sehifemizin Controllerini almaq ucun istifade edeceyik
        FXMLLoader loader = new FXMLLoader();

        // loaderimize sehifemizin unvanini gosteririk
        loader.setLocation(MyProperties.getURLProperties().getAnbarRootLayoutURL());

        //sehifemizi yukleyitik
        // ve yalniz sehifemizi yuledikden sonra Controllerimizi ala bilerik
        // eks halda NullPointerException olacaq
        AnchorPane ap = null;
        try {
            ap = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // controller obyektimizi loaderden aliriq
        AnbarRootLayoutController anbarController = loader.getController();

        //ve controllerimizden bize lazim olan metodu cagiririq
        anbarController.toggleButtonAnbarOnAction();

        acContent.getChildren().clear();
        acContent.getChildren().add(ap);
    }

    @FXML
    public void btnSellOnClick(ActionEvent event) {
        sellActive();

        //bu Sehifemizin Controllerini almaq ucun istifade edeceyik
        FXMLLoader loader = new FXMLLoader();

        // loaderimize sehifemizin unvanini gosteririk
        loader.setLocation(MyProperties.getURLProperties().getSaleRootLayoutURL());

        //sehifemizi yukleyitik
        // ve yalniz sehifemizi yuledikden sonra Controllerimizi ala bilerik
        // eks halda NullPointerException olacaq
        AnchorPane ap = null;
        try {
            ap = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // controller obyektimizi loaderden aliriq
        //SaleRootLayoutController saleController = loader.getController();
        ControllersRef.srlc = loader.getController();

        //ve controllerimizden bize lazim olan metodu cagiririq
        ControllersRef.srlc.toggleButtonSaleOnAction();

        acContent.getChildren().clear();
        acContent.getChildren().add(ap);
    }

    @FXML
    private void btnEmplopyeOnClick(ActionEvent event) throws IOException {
        employeeActive();
//        EmployeController ec = new EmployeController();
//        userNameMedia nm = new userNameMedia();
//        FXMLLoader fXMLLoader = new FXMLLoader();
//        fXMLLoader.load(getClass().getResource("/view/application/Employe.fxml").openStream());
//        nm.setId(id);
//        EmployeController employeController = fXMLLoader.getController();
//        employeController.bpContent.getStylesheets().add("/style/MainStyle.css");
//        employeController.setNameMedia(usrNameMedia);
//        employeController.btnViewEmployeeOnAction(event);
//
//        AnchorPane acPane = fXMLLoader.getRoot();
//
//        acContent.getChildren().clear();
//
//        acContent.getChildren().add(acPane);

    }

    @FXML
    private void btnSettingsOnClick(ActionEvent event) throws IOException {
        settingsActive();
//        //inithilize Setting Controller
//        SettingsController settingController = new SettingsController();
//        //inithilize UserNameMedia
//        userNameMedia usrMedia = new userNameMedia();
//        // Define a loader to inithilize Settings.fxml controller
//        FXMLLoader settingLoader = new FXMLLoader();
//        //set the location of Settings.fxml by fxmlloader
//        settingLoader.load(getClass().getResource("/view/application/Settings.fxml").openStream());
//
//        //Send id to userMedia
//        usrMedia.setId(id);
//        //take control of settingController elements or node
//        SettingsController settingControl = settingLoader.getController();
//        settingControl.bpSettings.getStylesheets().add("/style/MainStyle.css");
//
//        settingControl.setUsrMedia(usrMedia);
//        settingControl.miMyASccountOnClick(event);
//        settingControl.settingPermission();
//
//        AnchorPane acPane = settingLoader.getRoot();
//        //acContent clear and make useul for add next node
//        acContent.getChildren().clear();
//        //add a node call "acPane" to acContent
//        acContent.getChildren().add(acPane);

    }

    @FXML
    private void btnAboutOnClick(ActionEvent event) {
        aboutActive();

//        try {
//            
//            FXMLLoader fXMLLoader = new FXMLLoader();
//            fXMLLoader.load(getClass().getResource("/view/application/about/AboutMe.fxml").openStream());
//
////            SellController sellController = fXMLLoader.getController();
////            sellController.acMainSells.getStylesheets().add("/style/MainStyle.css");
////            sellController.tbtnSellOnAction(event);
//            AnchorPane anchorPane = fXMLLoader.getRoot();
//            acContent.getChildren().clear();
//            acContent.getChildren().add(anchorPane);
//        } catch (IOException ex) {
//            Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @FXML
    private void hlUpdateAccount(ActionEvent event) {
        System.out.println("hlUpdateAccount");
    }

    @FXML
    private void mbtnOnClick(ActionEvent event) {
        System.out.println("mbtnOnClick");
    }
    int i = 1;

    @FXML
    private void acMainOnMouseMove(MouseEvent event) {
        System.out.println("acMainOnMouseMove: " + i++);
    }

//    public void permission() {
//        con = dbCon.geConnection();
//
//        try {
//            pst = con.prepareStatement("select * from " + db + ".UserPermission where UserId=?");
//            pst.setString(1, id);
//            rs = pst.executeQuery();
//            while (rs.next()) {
//                if (rs.getInt(17) == 0) {
//                    btnEmplopye.setDisable(true);
//                }
//                if (rs.getInt(15) == 0) {
//                    btnSell.setDisable(true);
//                } else {
//
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    /**
     * Metod Homre Duymesini Activ edir ve digerlerini Sondurur, Activ etme
     * Processi nedir? Activ etme processi Home Duymesine Qirmizi shekili ve
     * Activ Style(Stil) set edir, Digerlerine ise adi shekil ve adi Stil set
     * edir.
     */
    private void homeActive() {
        imgHomeBtn.setImage(homeRed);
        imgStoreBtn.setImage(stock);
        imgSellBtn.setImage(sell);
        imgEmployeBtn.setImage(employee);
        imgSettingsBtn.setImage(setting);
        imgAboutBtn.setImage(about);
        btnHome.setStyle(activeStyle);
        btnStore.setStyle(defultStyle);
        btnSell.setStyle(defultStyle);
        btnEmplopye.setStyle(defultStyle);
        btnSettings.setStyle(defultStyle);
        btnAbout.setStyle(defultStyle);
    }

    private void stockActive() {
        imgHomeBtn.setImage(home);
        imgStoreBtn.setImage(stockRed);
        imgSellBtn.setImage(sell);
        imgEmployeBtn.setImage(employee);
        imgSettingsBtn.setImage(setting);
        imgAboutBtn.setImage(about);
        btnHome.setStyle(defultStyle);
        btnStore.setStyle(activeStyle);
        btnSell.setStyle(defultStyle);
        btnEmplopye.setStyle(defultStyle);
        btnSettings.setStyle(defultStyle);
        btnAbout.setStyle(defultStyle);
    }

    private void sellActive() {
        imgHomeBtn.setImage(home);
        imgStoreBtn.setImage(stock);
        imgSellBtn.setImage(sellRed);
        imgEmployeBtn.setImage(employee);
        imgSettingsBtn.setImage(setting);
        imgAboutBtn.setImage(about);
        btnHome.setStyle(defultStyle);
        btnStore.setStyle(defultStyle);
        btnSell.setStyle(activeStyle);
        btnEmplopye.setStyle(defultStyle);
        btnSettings.setStyle(defultStyle);
        btnAbout.setStyle(defultStyle);
    }

    private void employeeActive() {
        imgHomeBtn.setImage(home);
        imgStoreBtn.setImage(stock);
        imgSellBtn.setImage(sell);
        imgEmployeBtn.setImage(employeeRed);
        imgSettingsBtn.setImage(setting);
        imgAboutBtn.setImage(about);
        btnHome.setStyle(defultStyle);
        btnStore.setStyle(defultStyle);
        btnSell.setStyle(defultStyle);
        btnEmplopye.setStyle(activeStyle);
        btnSettings.setStyle(defultStyle);
        btnAbout.setStyle(defultStyle);
    }

    private void settingsActive() {
        imgHomeBtn.setImage(home);
        imgStoreBtn.setImage(stock);
        imgSellBtn.setImage(sell);
        imgEmployeBtn.setImage(employee);
        imgSettingsBtn.setImage(settingRed);
        imgAboutBtn.setImage(about);
        btnHome.setStyle(defultStyle);
        btnStore.setStyle(defultStyle);
        btnSell.setStyle(defultStyle);
        btnEmplopye.setStyle(defultStyle);
        btnSettings.setStyle(activeStyle);
        btnAbout.setStyle(defultStyle);
    }

    private void aboutActive() {
        imgHomeBtn.setImage(home);
        imgStoreBtn.setImage(stock);
        imgSellBtn.setImage(sell);
        imgEmployeBtn.setImage(employee);
        imgSettingsBtn.setImage(setting);
        imgAboutBtn.setImage(aboutRed);
        btnHome.setStyle(defultStyle);
        btnStore.setStyle(defultStyle);
        btnSell.setStyle(defultStyle);
        btnEmplopye.setStyle(defultStyle);
        btnSettings.setStyle(defultStyle);
        btnAbout.setStyle(activeStyle);
    }

//    public void viewDetails() {
//        users.id = id;
//        usersGetway.selectedView(users);
//        image = users.image;
//        circleImgUsr.setFill(new ImagePattern(image));
//        imgUsrTop.setFill(new ImagePattern(image));
//        lblFullName.setText(users.fullName);
//        lblUsrNamePopOver.setText(users.userName);
//    }
}
