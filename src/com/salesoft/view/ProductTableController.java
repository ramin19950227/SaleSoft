/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.view;

import com.salesoft.DAO.ProductGetDAO;
import com.salesoft.DAO.ProductUpdateDAO;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellEditEvent;

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
        searchField.requestFocus();
    }

    /**
     * bu properti ozunde cedvelde gosterilecek melumatlari saxlayir ve Bazadan
     * yenilendikden sonra bu ObservableList-de saxlanacaq
     *
     */
    private final ObservableList<Product> productList = FXCollections.observableArrayList();

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
     * Axtarish ucun istifade olunur
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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //cedvelimizde Excelde oldugu kimi xanalari REDAKTE ede bilmek ucun
        //bu metodun parametrine true vermek lazimdir 
        productTable.setEditable(true);

        productTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {

                    // bazada axtarish verdikde Error cixirdi
                    // bu JavaFx-de table view cox qeribedir eee oz ozune avtomatik bu metodu cagirir
                    // ona gore birinci yoxlayiram sonra.
                    // yoxsa gonderirem ve error cixir nullPointerException
                    if (newValue != null) {
                        setProductToEdit(newValue);
                    } else {
                        System.out.println("productTable Selection PRODUCT NULL AUTOCALL");
                    }
                });

        //Cedvelimizin sutunlarini inicializasiya edirik
        //adlarimiz cedvelde gosteririk
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        //mehsul adini edit etmek ucun bu metodlardan istifade olunur
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // xanaya tiklayib deyishiklik edib ve enteri basdiqda 
        // bu metod acilir
        nameColumn.setOnEditCommit((CellEditEvent<Product, String> t) -> {
            //mehsulumuz redakte olunduqdn sonra enteri basdiqda bu metod ishe dushur

            //redakte tamamlandiqdan sonra yenilenmish melumati ve mehsulun id-sini
            //gonderirik DAO class-ina ki, Melumat bazasinda yenileyek
            ProductUpdateDAO.updateProductNameById(t.getRowValue().getId(), t.getNewValue());

            // mehsulun yeni adini melumat bazasina gonderib yeniledikden sonra Cedvelimizi yenileyirik
            // yani mehsullarin siyahisini bazadan yeniden yukleyirik
            updateTable("");

            // cedvelimizi bazadan yeniledikden sonra mehsulumuzu 
            //  select edirik, yani secirik ki Edit panelinede dushsun yeni melumatlar
            productTable.getSelectionModel().select(t.getRowValue());
        });

        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        qtyColumn.setOnEditCommit((CellEditEvent<Product, Number> t) -> {
            ProductUpdateDAO.updateProductQtyById(t.getRowValue().getId(), t.getNewValue().intValue());
            updateTable("");
            productTable.getSelectionModel().select(t.getRowValue());

        });

        purchasePriceColumn.setCellValueFactory(cellData -> cellData.getValue().purchasePriceProperty());
        purchasePriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        purchasePriceColumn.setOnEditCommit((CellEditEvent<Product, Number> t) -> {
            ProductUpdateDAO.updateProductPurchasePriceById(t.getRowValue().getId(), t.getNewValue().doubleValue());
            updateTable("");
            productTable.getSelectionModel().select(t.getRowValue());

        });

        barCodeColumn.setCellValueFactory(cellData -> cellData.getValue().barCodeProperty());
        barCodeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        barCodeColumn.setOnEditCommit((CellEditEvent<Product, String> t) -> {
            ProductUpdateDAO.updateProductBarCodeById(t.getRowValue().getId(), t.getNewValue());
            updateTable("");
            productTable.getSelectionModel().select(t.getRowValue());

        });

        noteColumn.setCellValueFactory(cellData -> cellData.getValue().noteProperty());
        noteColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        noteColumn.setOnEditCommit((CellEditEvent<Product, String> t) -> {
            ProductUpdateDAO.updateProductNoteById(t.getRowValue().getId(), t.getNewValue());
            updateTable("");
            productTable.getSelectionModel().select(t.getRowValue());

        });

        // bu cedvel bosh olduqda xeberdarliq cixarir yani meselen mehsul yoxdur ve s.
        productTable.setPlaceholder(new Label("Bazada Mehsul Tapilmadi"));

        updateTable("");
        setProductToEdit(null);
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
        MainApp.getLogger().log(Level.INFO, "ProductTableController.updateTable(\"*\" or \"\")(ALL) - called Parameter (String data) =:{0}", data);

        if (data.equals("*") || data.equals("")) {//eger daxil olan  * dursa ve ya bosh setirdirse o zaman hamsini goster
            //eger daxil olan data ULDUZ-durza (*) onda hamsini goster
            //bazya sorgu edirik ve butun mehsullari isteyirik
            //varsa qaytaracaq yoxdursa null qaytaracaq
            ArrayList<Product> requestList = ProductGetDAO.getAllProductList();
            // yoxlayiriq eger requestList bosh deyilse
            // onda if blokundaki emirleri edirik
            if (requestList != null) {

                //LOG aldigimiz Product obyektini sayini yaziriq
                MainApp.getLogger().log(Level.SEVERE, "ProductTableController.updateTable() \nrequestList items=:{0}", requestList.size());

                // Listimizi temizleyirik
                productList.clear();

                // Bazadan DAO sayesinde aldigimiz listi -> ObservableList-e keciririk
                productList.addAll(requestList);

                //Observable Listde olan melumatlari 
                //cedvelimize yerleshdiririk
                productTable.setItems(productList);
            } else {
                //LOG 
                MainApp.getLogger().log(Level.INFO, "ProductDAO.getAllProductList() == null");

            }
            //yoxlama Eger daxil edilen melumat barcoddursa o zaman songu gonderib  cavabini
            // yoxlayiriq ve eger barcoddursao zaman sorgunun cavabina mehsul Listi gelmelidir
        } else if (ProductGetDAO.getAllProductListByBarCode(data) != null) {// barcodla tapdisa bu blok ishe dushecek
            ArrayList<Product> requestList = ProductGetDAO.getAllProductListByBarCode(data);

            MainApp.getLogger().log(Level.SEVERE, "ProductTableController.updateTable(BARCODE) \nBarCode=: {0}requestList items=:{1}", new Object[]{data, requestList.size()});

            productList.clear();
            productList.addAll(requestList);
            productTable.setItems(productList);
        } else if (ProductGetDAO.getAllProductListByNameLike(data) != null) {//adla axtarib tapdisa bu blok ishe dushecek
            ArrayList<Product> requestList = ProductGetDAO.getAllProductListByNameLike(data);

            MainApp.getLogger().log(Level.SEVERE, "ProductTableController.updateTable(BARCODE) \nBarCode=: {0}requestList items=:{1}", new Object[]{data, requestList.size()});

            productList.clear();
            productList.addAll(requestList);
            productTable.setItems(productList);
        } else { // hec biri deyilse o zaman bu ishe dushecek
            productList.clear();
            productTable.setItems(productList);
        }
    }

    /**
     * Edit Bolumu
     * ///////////////////////////////////////////////////////////////////////////////////////////////
     *
     *
     *
     */
    @FXML
    private Label idLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField qtyField;
    @FXML
    private TextField purchasePriceField;
    @FXML
    private TextField barCodeField;
    @FXML
    private TextField noteField;

    /**
     * edit bolumunde yadda saxla duymesini basanda bu ishde dushur
     */
    @FXML
    private void handleSave() {
        if (isInputValid()) {

            ProductUpdateDAO.updateProductNameById(Integer.valueOf(idLabel.getText()), nameField.getText());
            ProductUpdateDAO.updateProductQtyById(Integer.valueOf(idLabel.getText()), Integer.valueOf(qtyField.getText()));
            ProductUpdateDAO.updateProductPurchasePriceById(Integer.valueOf(idLabel.getText()), Double.valueOf(purchasePriceField.getText()));
            ProductUpdateDAO.updateProductBarCodeById(Integer.valueOf(idLabel.getText()), barCodeField.getText());
            ProductUpdateDAO.updateProductNoteById(Integer.valueOf(idLabel.getText()), noteField.getText());

            updateTable("");
        }
    }

    /**
     * edit bolumunde legv et duymesini basdiqda bu ishde dushur
     */
    @FXML
    private void handleCansel() {
        setProductToEdit(null);
        searchField.requestFocus();
    }

    /**
     * Заполняет все текстовые поля, отображая подробности об адресате. Если
     * указанный адресат = null, то все текстовые поля очищаются.
     *
     * @param product — адресат типа product или null
     */
    private void setProductToEdit(Product product) {

        if (product != null) {
            // Заполняем метки информацией из объекта person.
            idLabel.setText(product.getId().toString());
            nameField.setText(product.getName());
            qtyField.setText(product.getQty().toString());
            purchasePriceField.setText(product.getPurchasePrice().toString());
            barCodeField.setText(product.getBarCode());
            noteField.setText(product.getNote());

            // TODO: Нам нужен способ для перевода дня рождения в тип String! 
            // birthdayLabel.setText(...);
        } else {
            // Если Person = null, то убираем весь текст.
            idLabel.setText("");
            nameField.setText("");
            qtyField.setText("");
            purchasePriceField.setText("");
            barCodeField.setText("");
            noteField.setText("");
            nameField.setStyle("-fx-border-color: white;-fx-border-width: 0");
            qtyField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
            purchasePriceField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
            barCodeField.setStyle("-fx-border-color: white;-fx-border-width: 0;");

        }

    }

    /**
     * Проверяет пользовательский ввод в текстовых полях.
     *
     * @return true, если пользовательский ввод корректен
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Mehsulun Adini dogru daxil edin!\n";
            nameField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
        }

        if (qtyField.getText() == null || qtyField.getText().length() == 0) {
            errorMessage += "Mehsulun Sayini dogru daxil edin!\n";
            qtyField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
        } else {
            // пытаемся преобразовать почтовый код в int.
            try {
                Integer.parseInt(qtyField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Kecərli say daxil edin (Tam eded olmalidir)!\n";
                qtyField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
            }
        }

        if (purchasePriceField.getText() == null || purchasePriceField.getText().length() == 0) {
            errorMessage += "Mehsulun Qiymetini dogru daxil edin!\n";
            purchasePriceField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
        } else {
            // пытаемся преобразовать почтовый код в int.
            try {
                Double.parseDouble(purchasePriceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Kecərli Qiymət daxil edin!\n";
                purchasePriceField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
            }
        }

        if (barCodeField.getText() == null || barCodeField.getText().length() == 0) {
            errorMessage += "Mehsulun Barcodunu dogru daxil edin!\n";
            barCodeField.setStyle("-fx-border-color: red;-fx-border-width: 5;");

        }

        if (errorMessage.length() == 0) {
            nameField.setStyle("-fx-border-color: white;-fx-border-width: 0");
            qtyField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
            purchasePriceField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
            barCodeField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Dogru Daxil edin");
            alert.setHeaderText("Zehmet olmasa Mehsulun melumatlarini dogru daxil edin");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
