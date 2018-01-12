/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private TableColumn<Product, String> barCodeColumn;
    @FXML
    private TableColumn<Product, String> noteColumn;

    private final ObservableList<Product> productObservableList = FXCollections.observableArrayList();

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

        //Heleki Qerara aldiq ki ad ve Qeyd-den bashqa hecneyi Redakte etmek olmasin
        qtyField.setEditable(false);
        purchasePriceField.setEditable(false);
        barCodeField.setEditable(false);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        purchasePriceColumn.setCellValueFactory(cellData -> cellData.getValue().purchasePriceProperty());
        barCodeColumn.setCellValueFactory(cellData -> cellData.getValue().barCodeProperty());
        noteColumn.setCellValueFactory(cellData -> cellData.getValue().noteProperty());
        noteColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        productTable.setEditable(true);
        productTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setProductToEdit(newValue);
            }
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
        setProductToEdit(null);
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
     * edit bolumunde yadda saxla duymesini basanda bu ishde dushur
     */
    @FXML
    private void handleSave() {
        LOGWriter.println("");
        LOGWriter.println("__________________________________________________________________________");
        LOGWriter.println("ProductTableController.handleSave()");
        LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        LOGWriter.println("nameField: " + nameField.getText());
        LOGWriter.println("qtyField: " + qtyField.getText());
        LOGWriter.println("purchasePriceField: " + purchasePriceField.getText());
        LOGWriter.println("barCodeField: " + barCodeField.getText());
        LOGWriter.println("noteField: " + noteField.getText());

        if (isInputValid()) {
            LOGWriter.println("");
            LOGWriter.println("Input is Valid");

            if (selectedProduct != null) {

                LOGWriter.println("Product is Selected");
                LOGWriter.println("Product Updated");

                LOGWriter.println("Product OLD state: " + selectedProduct);

                selectedProduct.setName(nameField.getText());
                selectedProduct.setQty(Integer.valueOf(qtyField.getText()));
                selectedProduct.setPurchasePrice(Double.valueOf(purchasePriceField.getText()));
                selectedProduct.setBarCode(barCodeField.getText());
                selectedProduct.setNote(noteField.getText());

                ProductDAO.update(selectedProduct);
                LOGWriter.println("Product NEW state: " + selectedProduct);

                showAllProductInTable();

            } else if (selectedProduct == null) {
                LOGWriter.println("");
                LOGWriter.println("Selected Product is Null");
                LOGWriter.println("Showing Alert");

                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Mehsulu Secin");
                alert.setHeaderText("Zehmet olmasa Mehsulu Cedvelden Secin");
                alert.setContentText("Redakte etmek istediyiniz mehsulu secin");

                alert.showAndWait();
            }
        } else {
            LOGWriter.println("");
            LOGWriter.println("Input is NOT Valid");
        }
    }

    /**
     * edit bolumunde legv et duymesini basdiqda bu ishde dushur
     */
    @FXML
    private void handleCansel() {
        LOGWriter.println("");
        LOGWriter.println("__________________________________________________________________________");
        LOGWriter.println("ProductTableController.handleCansel()");
        LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        productTable.getSelectionModel().clearSelection();
        setProductToEdit(null);
        searchField.requestFocus();
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

        //Mesulu Sildikden sonra xanalari da temizleyek
        handleCansel();
    }

    /**
     * Заполняет все текстовые поля, отображая подробности об адресате. Если
     * указанный адресат = null, то все текстовые поля очищаются.
     *
     * @param product — адресат типа product или null
     */
    private void setProductToEdit(Product product) {
        selectedProduct = product;
        if (product != null) {

            LOGWriter.println("");
            LOGWriter.println("__________________________________________________________________________");
            LOGWriter.println("ProductTableController.setProductToEdit()");
            LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

            LOGWriter.println("Selected Product is: " + product);

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
            alert.setTitle("Dogru Daxil edin");
            alert.setHeaderText("Zehmet olmasa Mehsulun melumatlarini dogru daxil edin");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
