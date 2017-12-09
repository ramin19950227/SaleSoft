package com.salesoft;

import com.salesoft.util.MyLogger;
import com.salesoft.view.ProductSaleCartController;
import com.salesoft.view.ProductTableController;
import com.salesoft.view.RootLayoutController;
import com.salesoft.view.SaleInvoiceDetailsTableController;
import com.salesoft.view.SaleInvoiceTableController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * MainApp - Proqramin Esas main olan Class-i dir butun proqram burdan bashlayir
 *
 * @author Ramin
 */
public class MainApp extends Application {

    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Ana sehifemizi elan edirik
     */
    private BorderPane rootLayout;

    /**
     * MyLogger Obyektini elan edirik ve yaradiriq, adini XXXXXXX-AppLog.txt
     * qoyuruq static ve final edirik eks teqdirde error cixirdi bilmedim niye
     */
    private static final Logger logger = new MyLogger("AppLog").getLogger();

    /**
     * MyLogger - Proqramimizin esas MyLogger obyektinin linkini almaq ucun
     * istifade olunur bunun sayesinde esas AppLog faylina qeydlerimizi ede
     * bilerik proqram baglanana qeder ne ish gorulecekse qeyd ede bilerk
     *
     * @return
     */
    public static Logger getLogger() {
        return logger;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        //Proqramin adini yazir acilan pencerenin yuxari sol kuncunde yazilan ad
        this.primaryStage.setTitle("Sale Soft");

        //Programi full screen-de acir 
        this.primaryStage.setFullScreen(true);

        //azca inglis dili bilen ashagidaki metod ne ish gorduyunu anlayar
        this.primaryStage.setFullScreenExitHint("Full Ekran Modundan Çıxmaq üçün Klaviaturadan ESC düyməsini basın!");

        //penceremizinn olcusune limit qoyuruq ki bu olculerden ashagi azalda bilmesin
        this.primaryStage.setMinHeight(600);
        this.primaryStage.setMinWidth(800);

        //Ana sehifemizi hazirlayiriq ve Ilkin goruntu hazirliqlarini edirik
        initRootLayout();

        // ProductTable - Mehsul olan cedvelimizi Ana sehifeye  yukleyirik
        // irelide proqram acilan kimi bashqa sehife gostermek istesek eger
        // o zaman bunu deyishib gostermek istediyimiz sehifenin metodunu 
        // bura yazacayiq
        showProductTable();
    }

    /**
     * initRootLayout() - Sol menyu ve ust menu hissesini inicializasiya edir ve
     * yukleyir sonra bize lazim olan hisseni bunun ortasina yukleyerek
     * gostereceyik bizim sehifelerimiz deyishecek amma bu sol menu ve ust menu
     * deyishmeyecek cunki bu Ana Paneldir nece deyeller Bu yuklenir sonra bunun
     * ortasinda setCenter metodu ile yukleyib gostermek istediyimiz hisseni
     * gonderirik parametrinde Meselen: rootLayout.setCenter(Gostermek
     * istediyimiz AnchorPane ve ya her neise Node deyilir);
     */
    public void initRootLayout() {
        try {
            // fxml fayli tapib yukleyirik AnchorPane tipli Deyishkene.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Sehifemizin Controllerini aliriq ve
            // Controllerimize Esas Class-in yani MainApp-in linni veririk
            // bu irelide Main appin icinde olan sehifeleri deyishmek imkani verecek.
            // MyLogger obyektine log yazmaq imkani verecek ve s.
            RootLayoutController controller = loader.getController();

            //MainApp -in linkini this deyerek veririk Controllere
            controller.setMainApp(this);

            // Yuklediyimiz deyishkeni Ekrana gostermesi ucun Stage obyektine gonderib
            // show metodunu cagiraraq ekrana cixmasini isteyirik.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            new MyLogger("MainApp.initRootLayout() - IOException").getLogger().log(Level.SEVERE, "IOException", ex);
        }
    }

    /**
     * showProductTable() - Bu metod mainApp obyekti istifade olunaraq
     * cagirilacaq Sehifeni(Scena-ni) RootLayoutun ortasina yukleyecek diger
     * sehifelerde bu prinsiple ishleyecek bu Yontemin ustunluyu ondadir ki bu
     * cur metoda girish parametride qoymaq mumkundur Meselen irelide
     * ProductEdit sehifesini duzeldende Productu redakte etmek ucun o sehifeni
     * yuklemek lazim olacaq amma biz hardan bileceyik ki biz hansi Product-i
     * redakte etmek isteyirik bunnan otru bu metodun girisine int dipli
     * Product-in id-sini vereceyik ve Controllerin obyektini alaraq ordan bize
     * lazim olan metodu acacayiq inshaAllah irelide goreceyik nece ishlediyini
     */
    public void showProductTable() {
        try {
            // gostermek istedyimiz sehifeni FXML faylini aliriq.
            // AnchorPane -e yukleyirik obyektin adi (productView)
            FXMLLoader loaderLeft = new FXMLLoader();
            loaderLeft.setLocation(MainApp.class.getResource("view/ProductTable.fxml"));
            AnchorPane productView = (AnchorPane) loaderLeft.load();

            // aldigimiz (productView) sehife obyektini yukleyirik Ana sehifemizin ortasina
            // solunda ve Yuxarisinda ise bizim menumuz var
            rootLayout.setCenter(productView);

            // Sehifemizin Controllerini aliriq ve
            // Controllerimize Esas Class-in yani MainApp-in linni veririk
            // bu irelide Main appin icinde olan sehifeleri deyishmek imkani verecek.
            // MyLogger obyektine log yazmaq imkani verecek ve s.
            ProductTableController controller = loaderLeft.getController();

            //MainApp -in linkini this deyerek veririk Controllere
            controller.setMainApp(this);

        } catch (IOException ex) {
            new MyLogger("MainApp.showProductTable() - IOException").getLogger().log(Level.SEVERE, "IOException", ex);
        }
    }

    /**
     * Satish Sebetini Gosterir
     */
    public void showProductSaleCart() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ProductSaleCart.fxml"));
            AnchorPane productSaleCart = (AnchorPane) loader.load();
            rootLayout.setCenter(productSaleCart);
            ProductSaleCartController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException ex) {
            new MyLogger("IOException in -  MainApp.showProductSaleCart()").getLogger().log(Level.SEVERE, "IOException", ex);
        }
    }

    /**
     * Satishlarin Siyahisini Gosterir
     */
    public void showProductSaleInvoice() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SaleInvoiceTable.fxml"));
            AnchorPane productSaleCart = (AnchorPane) loader.load();
            rootLayout.setCenter(productSaleCart);
            SaleInvoiceTableController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException ex) {
            new MyLogger("IOException in -  MainApp.showProductSaleCart()").getLogger().log(Level.SEVERE, "IOException", ex);
        }
    }

    /**
     * satishlar haqqinda melumat ve mehsullarin Siyahisini Gosterir
     */
    public void showSaleInvoiceDetailsTable(Integer id) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SaleInvoiceDetailsTable.fxml"));
            AnchorPane productSaleCart = (AnchorPane) loader.load();
            rootLayout.setCenter(productSaleCart);
            SaleInvoiceDetailsTableController controller = loader.getController();
            controller.setMainApp(this);
            controller.initDataById(id);
        } catch (IOException ex) {
            new MyLogger("IOException in -  MainApp.showProductSaleCart()").getLogger().log(Level.SEVERE, "IOException", ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
