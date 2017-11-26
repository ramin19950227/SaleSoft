package com.salesoft;

import com.salesoft.view.ProductTableController;
import com.salesoft.view.RootLayoutController;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     *
     * Bu metod mainApp obyekti yani bu obyekt istifade olunaraq rootLayoutdan
     * cagirilacaq Sehifeleri RootLayoutun ortasina yukleyecek bu metod root
     * layoute bize lazim olan sehifeni yukleyir diget sehifelerde bu prinsiple
     * ishleyecek bu Yontemin ustunluyu ondadir ki bu cur metoda girish
     * parametride qoymaq mumkundur Meselen irelide ProductEdit sehifesini
     * duzeldende Productu redakte etmek ucun o sehifeni yuklemek lazim olaca
     * amma biz hardan bileceyik ki biz hansi productu redakte etmek isteyirik
     * bunnan otru bu metodun girisine int dipli productun id-sini vereceyik ve
     * controllerin obyektini alaraq ordan bize lazim olan metodu acacayiq en
     * gozeli irelide goreceyik nece ishlediyini
     */
    public void showProductTable() {
        try {
            // ��������� �������� �� ���������.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ProductTable.fxml"));
            AnchorPane productOverview = (AnchorPane) loader.load();

            // �������� �������� �� ��������� � ����� ��������� ������.
            rootLayout.setCenter(productOverview);

            // ��� ����������� ������ � �������� ����������.
            ProductTableController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        //Programi full screen-de acir 
        this.primaryStage.setFullScreen(true);

        initRootLayout();

        showProductTable();
    }

    /**
     * Sol menyu v ust menu hissesini inicializasiya edir ve yukleyir sonra bize
     * lazim olan hisseni bunun ortasina yukleyerek gostereceyik bizim
     * sehifelerimiz deyishecek amma bu sol menu ve ust menu deyishmeyecek cunki
     * bu Ana Paneldir nece deyeller Bu yuklenir sonra bunun ortasinda setCenter
     * metodu ile yukleyib gostermek istediyimiz hisseni gonderirik parametrinde
     * Meselen: rootLayout.setCenter(Gostermek istediyimiz AnchorPane ve ya her
     * neise Node deyilir);
     */
    public void initRootLayout() {
        try {
            // fxml fayli tapib yukleyirik AnchorPane tipli Deyishkene.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            // Yuklediyimiz deyishkeni Ekrana gostermesi ucun Stage obyektine gonderib
            // show metodunu cagiraraq ekrana cixmasini isteyirik.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     *
     * @return
     *
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
