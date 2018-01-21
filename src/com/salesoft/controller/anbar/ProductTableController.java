package com.salesoft.controller.anbar;

import com.salesoft.DAO.impl.ProductDAO;
import com.salesoft.model.Product;
import com.salesoft.util.MyDateConverter;
import com.salesoft.util.UserOperationLogger;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Ramin
 */
public class ProductTableController implements Initializable {

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Number> qtyColumn;
    @FXML
    private TableColumn<Product, Number> purchasePriceColumn;
    @FXML
    private TableColumn<Product, Number> salePriceColumn;
    @FXML
    private TableColumn<Product, String> barCodeColumn;
    @FXML
    private TableColumn<Product, String> noteColumn;

    private final ObservableList<Product> productObservableList = FXCollections.observableArrayList();

    @FXML
    private TextField searchField;

    //cedvelden mehsulu secdikde bura yazilir
    private Product selectedProduct = null;
    private final ProductDAO ProductDAO = new ProductDAO();
    private final PrintWriter LOGWriter = UserOperationLogger.getLogWriter();

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
        LOGWriter.println("");
        LOGWriter.println("__________________________________________________________________________");
        LOGWriter.println("ProductTableController.initialize()");
        LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        purchasePriceColumn.setCellValueFactory(cellData -> cellData.getValue().purchasePriceProperty());
        salePriceColumn.setCellValueFactory(cellData -> cellData.getValue().salePriceProperty());
        barCodeColumn.setCellValueFactory(cellData -> cellData.getValue().barCodeProperty());
        noteColumn.setCellValueFactory(cellData -> cellData.getValue().noteProperty());
        noteColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        productTable.setEditable(true);

        //mehsul cedvelden secildikde bu metod ishe dushur
        productTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedProduct = newValue;
        });

        // del duymesi basildiqda mehsulu silir (Secilmish mehsulu Bazadan Silir)
        productTable.setOnKeyReleased((KeyEvent event) -> {
            if (event.getCode() == KeyCode.DELETE) {
                handleDelete();
            }
        });

        nameColumn.setOnEditCommit((CellEditEvent<Product, String> t) -> {

            LOGWriter.println("");
            LOGWriter.println("__________________________________________________________________________");
            LOGWriter.println("ProductTableController.nameColumnOnEditCommit()");
            LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));
            LOGWriter.println("Old Name: " + t.getOldValue());
            LOGWriter.println("New Name: " + t.getNewValue());

            Product oldProduct = t.getRowValue();
            Product newProduct = t.getRowValue().dublicateThisProduct();

            newProduct.setName(t.getNewValue());

            ProductDAO.update(newProduct);

            LOGWriter.println("Product Updated");
            LOGWriter.println("");
            LOGWriter.println("Product OLD State: " + oldProduct);
            LOGWriter.println("Product New State: " + newProduct);

            //redakte tamamlandiqdan sonra yenilenmish melumati ve mehsulun id-sini
            //gonderirik DAO class-ina ki, Melumat bazasinda yenileyek
            //ProductUpdateDAO.updateProductNameById(t.getRowValue().getId(), t.getNewValue());
            // mehsulun yeni adini melumat bazasina gonderib yeniledikden sonra Cedvelimizi yenileyirik
            // yani mehsullarin siyahisini bazadan yeniden yukleyirik
            showAllProductInTable();

            // cedvelimizi bazadan yeniledikden sonra mehsulumuzu 
            //  select edirik, yani secirik ki Edit panelinede dushsun yeni melumatlar
            productTable.getSelectionModel().select(t.getRowValue());
        });

        noteColumn.setOnEditCommit((CellEditEvent<Product, String> t) -> {
            LOGWriter.println("");
            LOGWriter.println("__________________________________________________________________________");
            LOGWriter.println("ProductTableController.noteColumnOnEditCommit()");
            LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

            LOGWriter.println("Old Note: " + t.getOldValue());
            LOGWriter.println("New Note: " + t.getNewValue());

            Product oldProduct = t.getRowValue();
            Product newProduct = t.getRowValue().dublicateThisProduct();

            newProduct.setNote(t.getNewValue());

            ProductDAO.update(newProduct);

            LOGWriter.println("Product Updated");
            LOGWriter.println("");
            LOGWriter.println("Product OLD State: " + oldProduct);
            LOGWriter.println("Product New State: " + newProduct);

            showAllProductInTable();
            productTable.getSelectionModel().select(t.getRowValue());

        });

        showAllProductInTable();
    }

    /**
     * Bu metod Axtarish xanasinda Klaviaturadan her hansi bir duyme basilib
     * buraxildiqdan sonra avtomatik ishe dushur
     */
    @FXML
    private void searchFieldReleased() {
        String searchData = searchField.getText().trim();

        //balaca bir yoxlama edek, eger daxil edilen melumat probeldirse ve ya boshdursa o zaman davam etmeyek
        // bu veziyyetde updateTable(); cagiraq ki sadece butun mehsullari goster yani hec bir axtarish etmiyek
        //ve Metod-dan cixaq return; ile
        if (searchData.isEmpty()) {
            //butun mehsullari goster
            showAllProductInTable();
            //ve bu metodu artiq sonlandir ve davam etme
            return;
        }

        LOGWriter.println("");
        LOGWriter.println("__________________________________________________________________________");
        LOGWriter.println("ProductTableController.searchFieldReleased()");
        LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        LOGWriter.println("searchData: " + searchData);

        if (!ProductDAO.searchByNameLike(searchData).isEmpty()) {//adla axtarib tapdisa bu blok ishe dushecek
            ArrayList<Product> requestList = ProductDAO.searchByNameLike(searchData);
            LOGWriter.println("Find by Name Like: " + searchData);
            LOGWriter.println(requestList);

            productObservableList.clear();
            productObservableList.addAll(requestList);
            productTable.setItems(productObservableList);

        } else if (!ProductDAO.searchByBarcode(searchData).isEmpty()) {// barcodla tapdisa bu blok ishe dushecek
            ArrayList<Product> requestList = ProductDAO.searchByBarcode(searchData);
            LOGWriter.println("Find by Barcode: " + searchData);
            LOGWriter.println(requestList);

            productObservableList.clear();
            productObservableList.addAll(requestList);
            productTable.setItems(productObservableList);

        } else {
            LOGWriter.println("No thins find");
            productTable.setPlaceholder(new Label("Axtardiginiz Melumata ( " + searchData + " ) uygun hecbir mehsul tapilmadi"));
            productTable.setItems(null);
        }
    }

    /**
     * Bu Metod sadece Bazada Olan butun mehsullari gosterir
     */
    private void showAllProductInTable() {

        LOGWriter.println("");
        LOGWriter.println("__________________________________________________________________________");
        LOGWriter.println("ProductTableController.showAllProductInTable()");
        LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        ArrayList<Product> productList = ProductDAO.getAll();

        if (productList == null) {
            LOGWriter.println("productList isNull");

        } else if (productList.isEmpty()) {
            LOGWriter.println("productList isEmpty");

            // cedvel bosh olduqda xeberdarliq cixaririq yani meselen mehsul yoxdur ve s.
            productTable.setPlaceholder(new Label(""
                    + "Anbarda Qeydiyyata alınmış Məhsul Yoxdur \n\n"
                    + "Yeni məhsul Qeydiyyata Almaq üçün  "
                    + "\nÜst Panelde yerləşən Qeydiyyat bölümünə daxil olun"
                    + "\nVə ya Yeni Məhsul Mədaxil edin"));

            productTable.setItems(null);

        } else if (!productList.isEmpty()) {
            LOGWriter.println("productList.size()=" + productList.size());

//            productList.forEach(p -> {
//                LOGWriter.println(p);
//            });
            productObservableList.clear();
            productObservableList.addAll(productList);
            productTable.setItems(productObservableList);
        }
    }

    /**
     * handle Delete cedvelden secilmish mehsulu silir
     */
    @FXML
    private void handleDelete() {
        LOGWriter.println("");
        LOGWriter.println("__________________________________________________________________________");
        LOGWriter.println("ProductTableController.handleDelete()");
        LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        if (selectedProduct == null) {
            LOGWriter.println("Selected Product is NULL");
            LOGWriter.println("Showing Alert");

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mehsulu Secin");
            alert.setHeaderText("Zehmet olmasa Mehsulu Secin");
            alert.setContentText("Zehmet Olmasa Silmek istediyiniz Mehsulu Secin");
            alert.showAndWait();

        } else {
            LOGWriter.println("Selected Product is: " + selectedProduct);
            LOGWriter.println("User Confirmation Waiting");

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Mehsulun Bazadan Silinmesi");
            alert.setHeaderText("Adi: " + selectedProduct.getName() + " - Sayi: " + selectedProduct.getQty());
            alert.setContentText(" - Bu Mehsulu Bazadan silmek isteyirsiniz?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                LOGWriter.println("Deleting Confirmed, OK button is Pressed");
                LOGWriter.println("Deleting Product: " + selectedProduct);
                ProductDAO.delete(selectedProduct.getId());
                LOGWriter.println("DELETED!!!");
            } else {
                LOGWriter.println("Deleting Cancelled");
            }
        }

        showAllProductInTable();
    }
}
