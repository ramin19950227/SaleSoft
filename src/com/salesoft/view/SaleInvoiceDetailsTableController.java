/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.view;

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
        System.out.println("initDataById  id = " + id);
        //1 ne edirem Haydi
        // 1-ci id ni xanadan aliram
        // 2-ci id ile melumatlari toplayib set edirem tableye ve s. @@@@@@@@@@@@@@@@@@@@@@@@@
        // id ile melumatlari almiram eeeeeeeeeeeee
        // ay adam Sen JAVADA ishleyirsen
        // ve dogrusu budur DINLE
        // id ile Invoice obyektini al sonra doldur lazimi yerlere

        Invoice invoice = InvoiceDAO.getInvoiceById(id);

        cutomerNameLabel.setText(invoice.getCustomerName());
        tarixLabel.setText(invoice.getDate());
        idField.setText(invoice.getId().toString());
        meblegLabel.setText(invoice.getTotalPrice().toString());

        invoicetList.clear();
//		if(invoice.getList() != null){

        invoicetList.addAll(invoice.getList());
        System.out.println(invoice.getList().get(0).getName());
//		}

        invoiceTable.setItems(invoicetList);
    }

    /**
     * Goster Duymesinde enter basdiqda bu acilir ve initData ni yazilan id-ile
     * gosterir
     */
    @FXML
    private void onActionShowInvoiceButton() {
        Integer id = getIdFromField();
        if (id == null){
            errorAlert("Id Daxil edin", "Id Daxil edin", "Id Daxil edin");
        }else{
            initDataById(id);
        }
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

    /**
     * ID Getter and Validator for null
     *
     */
    private Integer getIdFromField() {
        String field = idField.getText();
        if (field != null && !field.equals("")) {
            Integer say = Integer.valueOf(idField.getText());
            if (say >= 0) {
                return say;
            }
        }
        return null;
    }

}
