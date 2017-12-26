package com.salesoft.controller.sale;

import com.salesoft.DAO.impl.InvoiceDAO;
import com.salesoft.MainApp;
import com.salesoft.model.Invoice;
import com.salesoft.model.InvoiceItem;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.Printer.MarginType;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class PrintInvoice {

    private final Stage myStage;
    private final Invoice invoice;
    private final InvoiceDAO invoiceDAO;

    // bu evvel sitifade etdiyim construktordur
    // bunu heleki silmirem silsem gerek bashqa yerlerdede deyishiklik edim
    public PrintInvoice(Stage s, Invoice i) {
        this.invoiceDAO = new InvoiceDAO();
        myStage = s;
        invoice = i;
    }

    //bu da yeni ve daha rahat constructor
    public PrintInvoice(Integer historyId) {
        this.invoiceDAO = new InvoiceDAO();
        myStage = MainApp.getPrimaryStage();
        invoice = invoiceDAO.get(historyId);
    }

    CheckBox cbA4 = new CheckBox("A4");
    CheckBox cbA5 = new CheckBox("A5");

    public void start() {
        cbA4.setSelected(true);
        VBox root = new VBox(5);

        Label textLbl = new Label("Qaime:");
        TextArea text = new TextArea();
        text.setBackground(Background.EMPTY);
        text.setPrefRowCount(30);
        text.setPrefColumnCount(30);
        text.setWrapText(true);

        text.setFont(new Font("Times New Roman", 15));

        text.appendText("Qaime №: " + invoice.getId().toString() + " \n");
        text.appendText("Müştəri: " + invoice.getCustomerName() + " \n");
        text.appendText("Tarix: " + invoice.getStringDate()+ " \n");
        text.appendText("Mebleg: " + invoice.getTotalPrice() + " AZN\n");
        text.appendText(" \n");
        text.appendText(" \n");

        int counter = 1;
        for (InvoiceItem invoiceItem : invoice.getList()) {
            text.appendText(counter++ + "\t\t" + invoiceItem.getProduct().getName() + "\t\t" + invoiceItem.getProduct().getQty() + "\t" + invoiceItem.getTotalPrice() + " \n");
        }

        // Button to print the TextArea node
        Button printTextBtn = new Button("Cap Et");

        printTextBtn.setOnAction(e -> printMethod2(text));

        // Button to print the entire scene
        Button printSceneBtn = new Button("Cap Et (Elave)");
        printSceneBtn.setOnAction(e -> printMethod2(root));

        HBox buttonBox = new HBox(5, printTextBtn, printSceneBtn);

        root.getChildren().addAll(textLbl, cbA4, cbA5, text, buttonBox);
        Scene scene = new Scene(root);
        myStage.setScene(scene);
        myStage.setTitle("Printing Nodes");
        myStage.show();
        myStage.setWidth(0);
    }

    public void pt(Node n) {

        Printer p = Printer.getDefaultPrinter();

        //PageLayout layout = p.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, MarginType.HARDWARE_MINIMUM);
        PrinterJob job = PrinterJob.createPrinterJob(p);
        //job.getJobSettings().setPageLayout(layout);

        boolean printed = printViewPage(job, n);
        if (printed) {
            job.endJob();
            System.out.println("printed");
        } else {
            System.out.println("Printing failed.");
        }
    }

    public void newPrint(Node node) {

        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null) {
            job.getJobSettings().setPageLayout(job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, MarginType.DEFAULT));

            System.out.println(job.jobStatusProperty().asString());

            //boolean printed = job.printPage(node);
            //job.showPageSetupDialog(myStage);
            boolean printed = printViewPage(job, node);
            if (printed) {
                job.endJob();
            } else {
                System.out.println("Printing failed.");
            }
        } else {
            System.out.println("Could not create a printer job.");
        }
    }

    /**
     * Prints the specified view node completely to one single page.
     *
     * @param printerJob printer job which defines the printer, page layout, ...
     * @param view view node to print
     * @return true when the view was printed successfully
     */
    private boolean printViewPage(final PrinterJob printerJob, final Node view) {

        // the view needs to be scaled to fit the selected page layout of the PrinterJob
        // => the passed view node can't be scaled, this would scale the displayed UI
        // => solution: create a snapshot image for printing and scale this image
        final WritableImage snapshot = view.snapshot(null, null);
        final ImageView ivSnapshot = new ImageView(snapshot);

        // compute the needed scaling (aspect ratio must be kept)
        final PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();
        final double scaleX = pageLayout.getPrintableWidth() / ivSnapshot.getImage().getWidth();
        final double scaleY = pageLayout.getPrintableHeight() / ivSnapshot.getImage().getHeight();
        final double scale = Math.min(scaleX, scaleY);

        // scale the calendar image only when it's too big for the selected page
        if (scale < 1.0) {
            ivSnapshot.getTransforms().add(new Scale(scale, scale));
        }

        return printerJob.printPage(ivSnapshot);
    }

    private void print(Node node) {
        System.out.println("Creating a printer job...");

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            System.out.println(job.jobStatusProperty().asString());

            boolean printed = job.printPage(node);
            if (printed) {
                job.endJob();
            } else {
                System.out.println("Printing failed.");
            }
        } else {
            System.out.println("Could not create a printer job.");
        }
    }

    public void printMethod1(Node node) {
        PrinterJob job = PrinterJob.createPrinterJob();
        job.showPageSetupDialog(myStage);
        job.showPrintDialog(myStage);
        boolean printed = printViewPage(job, node);
        if (printed) {
            job.endJob();
            System.out.println("printed");
        } else {
            System.out.println("Printing failed.");
        }
    }

    public void printMethod2(Node node) {
        PrinterJob job = PrinterJob.createPrinterJob();

        if (cbA4.isSelected()) {
            job.getJobSettings().setPageLayout(job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, 20, 20, 20, 20));
        } else if (cbA5.isSelected()) {
            job.getJobSettings().setPageLayout(job.getPrinter().createPageLayout(Paper.A5, PageOrientation.PORTRAIT, 20, 20, 20, 20));

        }
        job.showPrintDialog(myStage);
        job.getJobSettings().setJobName("Qaime Nomre" + invoice.getId() + "Mushteri =" + invoice.getCustomerName());

        boolean printed = printViewPage(job, node);
        if (printed) {
            job.endJob();
            System.out.println("printed");
        } else {
            System.out.println("Printing failed.");
        }
    }

}
