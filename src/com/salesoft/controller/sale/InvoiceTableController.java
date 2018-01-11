package com.salesoft.controller.sale;

import com.salesoft.DAO.impl.InvoiceDAO;
import com.salesoft.controller.ControllersRef;
import com.salesoft.model.Invoice;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Ramin
 */
public class InvoiceTableController implements Initializable {

    @FXML
    private TableColumn<Invoice, Number> idColumn;
    @FXML
    private TableColumn<Invoice, String> nameColumn;
    @FXML
    private TableColumn<Invoice, Number> totalPriceColumn;
    @FXML
    private TableColumn<Invoice, String> dateColumn;

    @FXML
    private TableView<Invoice> invoiceTable;
    private final ObservableList<Invoice> invoicetList = FXCollections.observableArrayList();

    private final InvoiceDAO invoiceDao = new InvoiceDAO();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().stringDateProperty());

        invoiceTable.setOnMousePressed((MouseEvent event) -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                handleShowInvoiceDetails();
            }
        });

        invoiceTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {

                    // bazada axtarish verdikde Error cixirdi
                    // bu JavaFx-de table view cox qeribedir eee oz ozune avtomatik bu metodu cagirir
                    // ona gore birinci yoxlayiram sonra.
                    // yoxsa gonderirem ve error cixir nullPointerException
                    if (newValue != null) {
                        setInvoiceToEdit(newValue);
                    }
                });

        updateTable();

    }

    private void updateTable() {
        ArrayList<Invoice> invoiceList = invoiceDao.getAll();
        invoicetList.clear();
        if (invoicetList != null) {
            invoicetList.addAll(invoiceList);
            invoiceTable.setItems(invoicetList);
        }
    }

    private void updateTable(String data) {
        data = data.trim();

        if (invoiceDao.getAllByNameLike(data) != null) {//adla axtarib tapdisa bu blok ishe dushecek

            ArrayList<Invoice> requestList = invoiceDao.getAllByNameLike(data);
            invoicetList.clear();
            invoicetList.addAll(requestList);
            invoiceTable.setItems(invoicetList);

        } else {
            updateTable();
        }
    }

    /**
     * Edit Bolumu //////////////////////////////////////////////////////
     *
     *
     *
     */
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField totalPriceField;
    @FXML
    private TextField dateField;

    @FXML
    private TextField searchField;

    public static Invoice selectedInvoice = null;

    /**
     * searchFieldReleased
     */
    @FXML
    private void searchFieldReleased() {
        String data = searchField.getText();
        System.out.println(data);
        updateTable(data);
    }

    /**
     * edit bolumunde yadda saxla duymesini basanda bu ishde dushur
     */
    @FXML
    private void handleSave() {
//        if (isInputValid()) {
//            if (idField.getText() == null || idField.getText().length() == 0) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Cedvelde Qaimeni Secin");
//                alert.setHeaderText("Zehmet olmasa Qaimeni Cedvelden secin");
//                alert.setContentText("Qaimeni Secib Sonra Duymeye basin");
//                alert.showAndWait();
//            } else {
//                invoiceDao.update(new Invoice(Integer.valueOf(idField.getText()), nameField.getText()));
//                updateTable(nameField.getText());
//            }
//
//        }
    }

    /**
     * edit bolumunde legv et duymesini basdiqda bu ishde dushur
     */
    @FXML
    private void handleCansel() {
        setInvoiceToEdit(null);
        searchField.requestFocus();
        searchField.selectAll();
        updateTable();
    }

    /**
     * edit bolumunde Satish Siyahisina bax duymesini basdiqda ishde Dushur
     */
    @FXML
    private void handleShowInvoiceDetails() {

        if (selectedInvoice == null) {
            System.out.println("null");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cedvelde Qaimeni Secin");
            alert.setHeaderText("Zehmet olmasa Qaimeni Cedvelden secin");
            alert.setContentText("Qaimeni Secib Sonra Duymeye basin");
            alert.showAndWait();
        } else {
            ControllersRef.srlc.toggleButtonSaleInvoiceDetailsOnAction();
        }
    }

    /**
     * Заполняет все текстовые поля, отображая подробности об адресате. Если
     * указанный адресат = null, то все текстовые поля очищаются.
     *
     * @param product — адресат типа product или null
     */
    private void setInvoiceToEdit(Invoice invoice) {
        selectedInvoice = invoice;

        if (invoice != null) {
            // Заполняем метки информацией из объекта person.
            idField.setText(invoice.getId().toString());
            nameField.setText(invoice.getCustomerName());
            totalPriceField.setText(invoice.getTotalPrice().toString());
            dateField.setText(invoice.getStringDate());
        } else {
            // Если Person = null, то убираем весь текст.
            idField.setText("");
            nameField.setText("");
            totalPriceField.setText("");
            dateField.setText("");

            nameField.setStyle("-fx-border-color: white;-fx-border-width: 0;");

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
            errorMessage += "Mushterinin Adini dogru daxil edin!\n";
            nameField.setStyle("-fx-border-color: red;-fx-border-width: 5;");
            nameField.requestFocus();
        }

        if (errorMessage.length() == 0) {
            nameField.setStyle("-fx-border-color: white;-fx-border-width: 0");
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dogru Daxil edin");
            alert.setHeaderText("Zehmet olmasa melumatlarini dogru daxil edin");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
