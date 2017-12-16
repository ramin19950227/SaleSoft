/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.view.sale;

import com.salesoft.DAO.InvoiceDAO;
import com.salesoft.DAO.ProductGetDAO;
import com.salesoft.DAO.ProductUpdateDAO;
import com.salesoft.model.Invoice;
import com.salesoft.model.InvoiceItem;
import com.salesoft.model.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ramin
 */
public class SaleInvoiceDetailsTableController implements Initializable {

    /**
     * InvoiceItem Edit Properties
     */
    //geri qaytarilma sayinin yazilacagi xana
    @FXML
    private TextField productReturnQty;

    //geri qaytarilma sebebinin yazilacagi xana
    @FXML
    private TextField productReturnNote;

    private InvoiceItem selectedInvoiceItem = null;

    //TABLE View Bolumu START
    @FXML
    private TableView<InvoiceItem> invoiceTable;

    @FXML
    private TableColumn<InvoiceItem, String> barCodeColumn;
    @FXML
    private TableColumn<InvoiceItem, String> nameColumn;
    @FXML
    private TableColumn<InvoiceItem, Number> sayColumn;
    @FXML
    private TableColumn<InvoiceItem, Number> meblegColumn;

    // melumatlar Invoicenin propertileri
    @FXML
    private Label cutomerNameLabel;
    @FXML
    private Label tarixLabel;
    @FXML
    private TextField idField;
    @FXML
    private Label meblegLabel;

    //
    private final ObservableList<InvoiceItem> invoicetList = FXCollections.observableArrayList();

    // aldigimiz invoice obyektini burda saxlayiriq
    Invoice invoice = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        barCodeColumn.setCellValueFactory(cellData -> cellData.getValue().productBarCodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sayColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        meblegColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());

        invoiceTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {

                    if (newValue != null && newValue.getQty() > 0) {
                        setInvoiceItemEditFields(newValue);
                        selectedInvoiceItem = newValue;
                    } else {
                        System.out.println("cartTable Selection PRODUCT NULL AUTOCALL");
                    }
                });
    }

    /**
     * mushteri history id yazacaq ve enteri basanda bu metod lazimi melumatlari
     * lazimi xanalara dolduracaq.
     *
     * @param id
     */
    @FXML
    public void initDataById(int id) {
        invoice = InvoiceDAO.getInvoiceById(id);

        if (invoice != null) {
            cutomerNameLabel.setText(invoice.getCustomerName());
            tarixLabel.setText(invoice.getDate());
            idField.setText(invoice.getId().toString());
            meblegLabel.setText(invoice.getTotalPrice().toString() + " AZN");

            invoicetList.clear();
            if (invoice.getList() != null) {
                invoicetList.addAll(invoice.getList());
                invoiceTable.setItems(invoicetList);
            }
        } else {
            errorAlert("Bu Nomre ile Qaime Tapilmadi", "Bu Nomre ile Qaime Tapilmadi", "Bu Nomre ile Qaime Tapilmadi");
        }

    }

    /**
     * Goster Duymesinde enter basdiqda bu acilir ve initData ni yazilan id-ile
     * gosterir
     */
    @FXML
    private void onActionShowInvoiceButton() {
        System.err.println("com.salesoft.view.sale.SaleInvoiceDetailsTableController.onActionShowInvoiceButton()");
        if (idField.getText() == null || idField.getText().length() == 0) {
            errorAlert("Qaime nomresini daxil edin", "Qaime nomresini daxil edin", "Qaime nomresini daxil edin");
            idField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
        } else {
            // пытаемся преобразовать почтовый код в int.
            try {
                Integer.parseInt(idField.getText());
                initDataById(Integer.parseInt(idField.getText()));
                idField.setStyle("-fx-border-color: white;-fx-border-width: 0;");
            } catch (NumberFormatException e) {
                errorAlert("Kecerli Qaime nomresini daxil edin", "Kecerli Qaime nomresini daxil edin", "Kecerli Qaime nomresini daxil edin (Tam eded olmalidir)");
                idField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
            }
        }
        idField.requestFocus();
        idField.selectAll();

    }

    /**
     * Bu metod error xeberdarligi cixartmaq ucun istifade olunur
     *
     * @param title
     * @param header
     * @param content
     */
    private void errorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void onActionPrintButton() {
        new PrintInvoice(new Stage(), invoice).start();
    }

    /**
     * Geri qaytarilma duymesi basildiqda
     */
    @FXML
    private void onActionReturnButton() {
        if (selectedInvoiceItem == null) {
            System.err.println("Operated Item is not selected");
            return;
        }
        System.out.println("com.salesoft.view.sale.SaleInvoiceDetailsTableController.onActionReturnButton()");
        // sayi yoxla gor invoice listde olan saydan cox deyil ve0-dan azdeyil 0deyil
        // sonra malin sayini invoice listde yazildigi qeder azald ve Mehsul bazasindada hemin qeder artir

        //1-ci mehsulu bazada artiririq eger bazada mehsul yoxdursa silinibse? yeni alish nece ediremse ocur mi edim? gorem
        // heleki o olasiligi dushunmurem
        // InvoiceItemi-in satish sayini alaq ve serhed qoyaq ki ondan cox qaytarablmesin 
        Integer itemQty = selectedInvoiceItem.getQty();

        if (isInputValid()) {
            Integer enteredQty = Integer.valueOf(productReturnQty.getText());
            System.out.println("enteredQty: " + enteredQty);

            if (enteredQty <= itemQty && enteredQty > 0) {
                System.out.println("enteredQty <= itemQty && enteredQty > 0 ->> result true");

                System.out.println("itemQty: " + itemQty);

                Integer productId = selectedInvoiceItem.getProductId();
                System.out.println("productId: " + productId);

                //eger yoxlamani kecdise yani xanaya kecerli melumat yaiibsa yazdigi say satish sayina beraber
                //ve ya ondan az dirsa ve eyni zamanda 0-dan coxdursa
                //ashagidakileri ele
                //indi mehsulumuzu alaqki sayini bilek sonra onun ustune qaytarilani yazaq
                Product returnedProduct = ProductGetDAO.getProductById(productId);

                //yoxlyiriq eger bu id- ile bazada mehsul varsa davam edirik 
                //yoxdursa NullPointerException cixacaq. bu olmasin deye yoxlayiram -
                if (returnedProduct != null) {
                    System.out.println("returnedProduct.getName" + returnedProduct.getName());

                    // mehsulumuzun faktiki sayini aliriq
                    Integer productCurrentQty = returnedProduct.getQty();
                    System.out.println("productCurrentQty: " + productCurrentQty);

                    // vee faktiki sayin ustune qaytarilan sayi yaziram neticeni bazaya yenilemeye gonderecem
                    Integer productResultQry = productCurrentQty + enteredQty;
                    System.out.println("productResultQry: " + productResultQry);

                    //aldgimiz neticeni bazaya gonderirik ki id ile uygun olan mehsulun sayini yenile
                    //ProductUpdateDAO.updateProductQtyById(resultQry, itemId);
                    // indi ise InvoiceItem-in sayini azaltmaliyiq
                    Integer invoiceItemCurrentQty = selectedInvoiceItem.getQty();
                    System.out.println("invoiceItemCurrentQty: " + invoiceItemCurrentQty);

                } else {
                    System.err.println("Returner Product Not Found");
                }
            } else {
                System.err.println("Returner Product Qty is Not Correct");
            }
        } else {
            System.err.println("Inputi is Not Valid");
        }
    }

    private void setInvoiceItemEditFields(InvoiceItem invoiceItem) {
        productReturnQty.setText(invoiceItem.getQty().toString());
        productReturnQty.requestFocus();

    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (productReturnQty.getText() == null || productReturnQty.getText().length() == 0) {
            errorMessage += "Mehsulun Sayini dogru daxil edin!\n";
            productReturnQty.setStyle("-fx-border-color: red;-fx-border-width: 5;");
        } else {
            // пытаемся преобразовать в int.
            try {
                Integer qty = Integer.parseInt(productReturnQty.getText());

                if (qty <= 0) {
                    errorMessage += "Say 1-dən az Ola bilmez!\n";
                }

            } catch (NumberFormatException e) {
                errorMessage += "Kecərli say daxil edin (Tam eded olmalidir)!\n";
                productReturnQty.setStyle("-fx-border-color: red;-fx-border-width: 5;");
            }
        }

        if (errorMessage.length() == 0) {
            productReturnQty.setStyle("-fx-border-color: white;-fx-border-width: 0");
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dogru Daxil edin");
            alert.setHeaderText("Zehmet olmasa Mehsulun melumatlarini dogru daxil edin");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
