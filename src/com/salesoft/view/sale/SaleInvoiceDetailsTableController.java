/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.view.sale;

import com.salesoft.DAO.InvoiceDAO;
import com.salesoft.MainApp;
import com.salesoft.model.Invoice;
import com.salesoft.model.InvoiceItem;
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

    // Ссылка на главное приложение.
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    //TABLE View Bolumu START
    @FXML
    private TableView<InvoiceItem> invoiceTable;

    @FXML
    private TableColumn<InvoiceItem, Number> idColumn;
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
    private ObservableList<InvoiceItem> invoicetList = FXCollections.observableArrayList();

    // aldigimiz invoice obyektini burda saxlayiriq
    Invoice invoice = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sayColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        meblegColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());
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

}
