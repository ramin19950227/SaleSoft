/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import com.salesoft.DAO.impl.InvoiceDAO;
import com.salesoft.jasperReport.wrapperModel.InvoiceItemWrapper;
import com.salesoft.model.Invoice;
import com.salesoft.model.InvoiceItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Ramin
 */
public class MyJRViewer {

    public static void showSATISH_QAIMESI(Integer id) {
        System.err.println("START -> showSATISH_QAIMESI (Integer id)");
        showSATISH_QAIMESI(new InvoiceDAO().get(id));
    }

    public static void showSATISH_QAIMESI(Invoice invoice) {
        System.err.println("START -> showSATISH_QAIMESI (Invoice invoice)");

        //Invoice obyektimizde olan butun obyektleri aliriq
        ArrayList<InvoiceItem> itemListInInvoiceObject = invoice.getList();

        /* List to hold Items */
        //bu listde bizim Capagonderilerec obyektlerimizi olacaq
        //yani aldigimiz ItemInvoice-obyektlerini InvoiceItemWrapper obyektine Convert edib bura yazacayiq
        ArrayList<InvoiceItemWrapper> dataList = new ArrayList<>();

        itemListInInvoiceObject.forEach((invoiceItem) -> {
            dataList.add(new InvoiceItemWrapper(invoiceItem));
        });

        String title = "SaleSoft - Satish Proqrami: Satış Qaiməsi";
        String jasperFileAddress = "satish_qaimesi.jasper";

        String qaimeNo = invoice.getId().toString();
        Double totalPrice = invoice.getTotalPrice();
        String tarix = invoice.getStringDate();
        String customerName = invoice.getCustomerName();

        /* Map to hold Jasper report Parameters */
        Map<String, Object> parameters = new HashMap();

        parameters.put("title", title);
        parameters.put("qaimeNo", qaimeNo);
        parameters.put("totalPrice", totalPrice);
        parameters.put("tarix", tarix);
        parameters.put("jasterFileAddress", jasperFileAddress);
        parameters.put("customerName", customerName);

//---------------> Qaimenin  Melumat ve Parametrlerinin Gonderilemesi ve capa hazirlanmasi ve CAP bolumu  <---------------//
        try {

            /* Convert List to JRBeanCollectionDataSource */
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(dataList);

            parameters.put("ItemDataSource", itemsJRBean);


            /* Using compiled version(.jasper) of Jasper report to generate View */
            JasperPrint jasperPrint = JasperFillManager.fillReport(parameters.get("jasterFileAddress").toString(), parameters, new JREmptyDataSource());

            /* outputStream to create PDF */
            //OutputStream outputStream = new FileOutputStream(new File("JasperTableExample.pdf"));
            /* Write content to PDF file */
            //JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            // Bu kod ile Hazirladigimiz JasperPrint Obyektimizi Gosteririk 
            //bu formada olanda Istesek PDF-e ve ya bashqaformatlara cevire bilerik ve hetta capda eve bilerik
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);

            System.out.println("MyJasperViewer is Generated");
        } catch (JRException ex) {
            MyExceptionLogger.logException("JRException - MyJRViewer.showSATISH_QAIMESI(Invoice invoice)", ex);
        }
    }

}
