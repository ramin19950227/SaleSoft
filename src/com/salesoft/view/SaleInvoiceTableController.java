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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Ramin
 */
public class SaleInvoiceTableController implements Initializable {

    @FXML
    private TableView<Invoice> invoiceTable;

    @FXML
    private TableColumn<Invoice, Number> idColumn;
    @FXML
    private TableColumn<Invoice, String> nameColumn;
    @FXML
    private TableColumn<Invoice, Number> totalPriceColumn;
    @FXML
    private TableColumn<Invoice, String> dateColumn;

    private ObservableList<Invoice> invoicetList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        invoiceTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Integer invoice_id = invoiceTable.getSelectionModel().getSelectedItem().getId();
                    System.out.println(invoice_id);
                    mainApp.showSaleInvoiceDetailsTable(invoice_id);
                    //System.out.print(InvoiceDAO.getAllInvoiceItemListById(invoice_id).get(0).getName());
                }
            }
        });

        updateTable();

    }

    private void updateTable() {
        ArrayList<Invoice> invoiceList = InvoiceDAO.getAllInvoice();

        invoicetList.clear();

        if (invoicetList != null) {

            invoicetList.addAll(invoiceList);
            invoiceTable.setItems(invoicetList);

        }

    }

    // Ссылка на главное приложение.
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    

}
