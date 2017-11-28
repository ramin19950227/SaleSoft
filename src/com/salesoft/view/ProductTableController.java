/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.view;

import com.salesoft.DAO.ProductGetDAO;
import com.salesoft.MainApp;
import com.salesoft.model.Product;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Ramin
 */
public class ProductTableController implements Initializable {

    /**
     * mainApp bu properti esas Java Klass olan MainApp klasinin Obyektinin
     * linkini saxlayacaq ozunde bunu setMainApp(MainApp mainApp) metodu ile
     * sehifeni Yukledimiz zaman linkin set edirik ki bize bashqa bir sehifeni
     * yuklemek lazim olduqda bunu rahatliqla ede bilek
     */
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * bu properti ozunde cedvelde gosterilecek melumatlari saxlayir ve Bazadan
     * yenilendikden sonra bu ObservableList-de saxlanacaq
     *
     */
    private ObservableList<Product> productList = FXCollections.observableArrayList();

    /**
     * productTable - cedvelimizin obyekti
     */
    @FXML
    private TableView<Product> productTable;

    /**
     * nameColumn - Ad Sutunu
     */
    @FXML
    private TableColumn<Product, String> nameColumn;
    /**
     * qtyColumn - Say Sutunu diqqet integer tipli sutun ucun Product, Integer
     * yox Product, Number yazmaq lazimdir
     */
    @FXML
    private TableColumn<Product, Number> qtyColumn;
    /**
     * purchasePriceColumn - Alih Qiymeti Sutunu diqqet Double tipli sutun ucun
     * Product, Double yox Product, Number yazmaq lazimdir
     */
    @FXML
    private TableColumn<Product, Number> purchasePriceColumn;
    /**
     * barCodeColumn - Barcod Sutunu
     */
    @FXML
    private TableColumn<Product, String> barCodeColumn;
    /**
     * noteColumn - Qeyd Sutunu
     */
    @FXML
    private TableColumn<Product, String> noteColumn;

    /**
     * ??
     */
    @FXML
    private TextField searchField;

    /**
     * infoFieldReleased
     */
    @FXML
    private void searchFieldReleased() {
        String data = searchField.getText();
        System.out.println(data);
        updateTable(data);
    }

    /**
     * Controllerin inicializasiyasi ucun bu metod istifade olunur bu sehife
     * yuklenende avtomatik cagrilir burada biz tablemizi init ede bilerik yani
     * hazirlaya bilerik Mehsullari gosteren sehife yuklenen kimi bosh
     * gorsenmemesi ucun yuklenmemeish melumatlari bazadan alib Tableye setItem
     * etmeliyik yani datalari cedvelimize yerleshdirmeliyik Cedvelin
     * yenilenmesini de Bashqa bir metodda realize edecem cunki her emeliyyatdan
     * sonra cedveli yenilemeye bezen ehtiyac olur.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Cedvelimizin sutunlarini inicializasiya edirik
        //adlarimiz cedvelde gosteririk
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        //saylari gosteririk
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());

        //qiymetleri gosteririk
        purchasePriceColumn.setCellValueFactory(cellData -> cellData.getValue().purchasePriceProperty());

        //barcodlarimizi gosteririk
        barCodeColumn.setCellValueFactory(cellData -> cellData.getValue().barCodeProperty());

        //qeydlerimizi gosterek
        noteColumn.setCellValueFactory(cellData -> cellData.getValue().noteProperty());

        updateTable("");

    }

    /**
     * Cedveli Yenilemek ucun bu metodu cagirmaq lazimdir bu metod
     * ProductDAO-dan malumatlari ArrayListde alir ve yoxlayir bosh deyilse o
     * zaman Tableye yerleshdirir.
     *
     * @param data - demeli ele bu metod bele ishleyir, daxil olan melumati
     * birinci probelleri silir evvelden ve axirdan tirim() ile sonra onu
     * yoxlayir eger *(Ulduz)-dursa ve ya boshdursa o zaman hamsini gosterir YOX
     * EGER deyilse o zaman barcod olub olmadigini yoxlayir ve EGER barcod ile
     * axtarish ugurlu alinirsa o zaman o neticeni gosterir YOX EGER barcod
     * alinmadisa o zaman ad ile axtarish edir axtarishi da GLOBAL (global match
     * % ? %) edir bu o demek dirki evvelden ortadan ve ya axirdan her hansi
     * hisse uygun gelse onu gosterecek meselen b herfi yazsam hansi mehsulun
     * adinda b varsa onlari gosterecek YOX EGER ad ile axtarishda ugurlu
     * alinmadisa o zaman hecne gostermir ObservableList-i .clear() edir yani
     * temizleyir ve bosh listi cedvele yerleshdirir yani hecne gostermir bosh
     * cedvel gorsenir ve bu da o demekdirki hecne tapmadiq :)))
     *
     *
     */
    private void updateTable(String data) {
        data = data.trim();
        //LOG yaziriq metodun cagirilmasi ile elaqedar
        MainApp.getLogger().info("ProductTableController.updateTable(\"*\" or \"\")(ALL) - called Parameter (String data) =:" + data);

        if (data.equals("*") || data.equals("")) {//eger daxil olan  * dursa ve ya bosh setirdirse o zaman hamsini goster
            //eger daxil olan data ULDUZ-durza (*) onda hamsini goster
            //bazya sorgu edirik ve butun mehsullari isteyirik
            //varsa qaytaracaq yoxdursa null qaytaracaq
            ArrayList<Product> requestList = ProductGetDAO.getAllProductList();
            // yoxlayiriq eger requestList bosh deyilse
            // onda if blokundaki emirleri edirik
            if (requestList != null) {

                //LOG aldigimiz Product obyektini sayini yaziriq
                MainApp.getLogger().log(Level.SEVERE, "ProductTableController.updateTable() \n"
                        + "requestList items=:" + requestList.size());

                // Listimizi temizleyirik
                productList.clear();

                productList.addAll(requestList);

                //Observable Listde olan melumatlari 
                //cedvelimize yerleshdiririk
                productTable.setItems(productList);
            } else {
                //LOG 
                MainApp.getLogger().log(Level.INFO, "ProductTableController.updateTable() \n"
                        + "ProductDAO.getAllProductList() == null");

            }
        } else if (ProductGetDAO.getAllProductListByBarCode(data) != null) {// barcodla tapdisa bu blok ishe dushecek
            ArrayList<Product> requestList = ProductGetDAO.getAllProductListByBarCode(data);

            MainApp.getLogger().log(Level.SEVERE, "ProductTableController.updateTable(BARCODE) \n"
                    + "BarCode=: " + data
                    + "requestList items=:" + requestList.size());

            productList.clear();
            productList.addAll(requestList);
            productTable.setItems(productList);
        } else if (ProductGetDAO.getAllProductListByNameLike(data) != null) {//adla axtarib tapdisa bu blok ishe dushecek
            ArrayList<Product> requestList = ProductGetDAO.getAllProductListByNameLike(data);

            MainApp.getLogger().log(Level.SEVERE, "ProductTableController.updateTable(BARCODE) \n"
                    + "BarCode=: " + data
                    + "requestList items=:" + requestList.size());

            productList.clear();
            productList.addAll(requestList);
            productTable.setItems(productList);
        } else { // hec biri deyilse o zaman bu ishe dushecek
            productList.clear();
            productTable.setItems(productList);
        }
    }
}
