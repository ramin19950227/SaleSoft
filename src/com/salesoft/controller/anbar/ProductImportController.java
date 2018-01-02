package com.salesoft.controller.anbar;

import com.salesoft.DAO.impl.ProductDAO;
import com.salesoft.DAO.impl.PurchaseProductDAO;
import com.salesoft.controller.ApplicationController;
import com.salesoft.model.Product;
import com.salesoft.model.ProductImportWrapper;
import com.salesoft.util.MyDateConverter;
import com.salesoft.util.MyView;
import com.salesoft.util.TextFieldValidator;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Ramin
 */
public class ProductImportController implements Initializable {

    /**
     * TextField-s
     */
    @FXML
    private TextField bTF, nTF, qTF, pTF;

    /**
     * DatePicer
     */
    @FXML
    private DatePicker datePicker;

    /**
     * TextArea - Note
     */
    @FXML
    private TextArea nTA;

    /**
     * ImageView-s
     */
    @FXML
    private ImageView bIV, nIV, qIV, pIV, dIV;

    /**
     * Label-s For Warnings
     */
    @FXML
    private Label bWL, nWL, qWL, pWL, dWL;

    /**
     * Buttons
     */
    @FXML
    private Button saveButton, clearButton;

    /**
     * ProductDAO
     */
    private final ProductDAO productDAO = new ProductDAO();
    /**
     * PurchaseProductDAO
     */
    private final PurchaseProductDAO purchaseProductDAO = new PurchaseProductDAO();

    /**
     * Util's
     */
    private final MyView myView = new MyView();
    private final TextFieldValidator TFValidator = new TextFieldValidator();
    private Product product;
    private AutoCompletionBinding<String> bindAutoCompletion;
    Set<String> barCodeSet = new HashSet<>();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // barcodlari saxlamaq ucun setimizi elan edirik

        //butun mehsullari aliriq ve butun mehsullarin barcodlarini yaaziriq set'e 
        // hele ki
        // butun mehsullarin barcodlarini bu cur alacam amma irelide lazim gelse optimize edecem
        ArrayList<Product> list = productDAO.getAll();

        list.forEach((p) -> {
            barCodeSet.add(p.getBarCode());
        });

        //ve aldigimiz barcodlari bind edirik TextField-imize ki adam bir iki herif yazanda teklif elesin ozu
        // ola biler adam el ile yazacaq ve ya barCod-la deyil adi mehsul kodlari ile ishleyecek
        bindAutoCompletion = TextFields.bindAutoCompletion(bTF, barCodeSet);

        // Bu Kod Ancaq Vehshiler ucundur ))))
        // demeli bele bu ne edir burda deyirik ki (bindAutoCompletion) yani avtomatik tamamlama funksiya
        // ile eger teklifolunan Stringlerden hansisa biri secilerse bu Metod ishe dushsun ve 
        // bu metodda ne edirik
        // bu metodda (bindAutoCompletion =null) yani artiq bize lazim deyil avtomatik teklif cunki artiq secdik
        // ve ardinca bTFOnKeyReleased() metodunu ishe saliriq bar kod ile lazimi obyekti alir ve lazimi melumatlari set edir
        // ve hetta bundan sonra Say xanasini fokusda ede bilerik
        bindAutoCompletion.addEventHandler(javafx.event.EventType.ROOT, (event) -> {
            //bindAutoCompletion = null; -- bu nese ishe yaramadi yada ola biler men tam anlamamisham
            //heleki bele qalsin mene ne lazimdir? secim edirsen vsyodaolur bitir
            bTFOnKeyReleased();
            qTF.requestFocus();
        });

        // Default olaraq Sondurek ki yoxlamadan kecse ancaq aclsin ve ishlesin
        BooleanBinding binding = bTF.textProperty().isEmpty()
                .or(nTF.textProperty().isEmpty())
                .or(qTF.textProperty().isEmpty())
                .or(pTF.textProperty().isEmpty());

        saveButton.disableProperty().bind(binding);

        // DatePicker-e bugunku tarixi set edek
        datePicker.setValue(LocalDate.now());
        datePicker.setOnAction(event -> {
            //for Testing
//            LocalDate date = datePicker.getValue();
//            //System.out.println("Selected LocalDate: " + date);
//            Date d = MyDateConverter.asDate(date);
//            //System.out.println("Selected util.Date: " + d);

        });

        // Label-lerimizi Sifirlayaq
        bWL.setText(null);
        nWL.setText(null);
        qWL.setText(null);
        pWL.setText(null);
        dWL.setText(null);

        bTF.setOnKeyReleased(value -> {
            bTFOnKeyReleased();
        });

        bTF.setOnAction(value -> {

            bTFOnKeyReleased();
            if (nTF.isDisabled()) {
                qTF.requestFocus();
            } else {
                nTF.requestFocus();
            }
        });

        nTF.setOnKeyReleased(value -> {
            if (TFValidator.isNotNull(nTF)) {
                myView.showOk(nIV);
                nWL.setText("");

            } else {
                myView.showNo(nIV);
                nWL.setText("Zehmet Olmasa ad daxil edin");

            }
        });

        nTF.setOnAction(value -> {
            qTF.requestFocus();
        });

        qTF.setOnKeyReleased(value -> {

            if (TFValidator.isCorrectInt(qTF)) {
                myView.showOk(qIV);
                qWL.setText(null);

            } else {
                myView.showNo(qIV);
                qWL.setText("Zehmet Olmasa Keçərli Say daxil edin");

                qTF.setText("");
            }
        });

        //say xanasinda enter basdiqda yoxlayaq tam eded daxil edilibse davamedek
        qTF.setOnAction(value -> {
            pTF.requestFocus();

        });

        pTF.setOnKeyReleased(value -> {
            if (TFValidator.isCorrectDouble(pTF) && TFValidator.isNotZero(pTF)) {
                myView.showOk(pIV);
                pWL.setText(null);

            } else {
                myView.showNo(pIV);
                pWL.setText("Zehmet Olmasa Keçərli Qiymet daxil edin");

                pTF.setText("");
            }
        });

        pTF.setOnAction(value -> {
            datePicker.requestFocus();
        });

        saveButton.setOnAction(value -> {
            saveButtonOnAction();
        });

        clearButton.setOnAction(value -> {
            clearAll();
        });

    }

    private void saveButtonOnAction() {
        System.err.println("Method Start: saveButtonOnAction()");
        System.out.println("Fields Status");
        System.out.println("bTF: " + bTF.getText());
        System.out.println("nTF: " + nTF.getText());
        System.out.println("qTF: " + qTF.getText());
        System.out.println("pTF: " + pTF.getText());

        if (product != null) {
            System.out.println("product NOT NULL");

            System.out.println("Setting qty price and note  to product" + product);
            product.setQty(Integer.parseInt(qTF.getText()));
            product.setPurchasePrice(Double.parseDouble(pTF.getText()));
            product.setNote(nTA.getText());

            System.out.println("data is Settedto product: " + product);

            LocalDate localDate = datePicker.getValue();
            Date date = MyDateConverter.asDate(localDate);

            System.out.println("date is: " + date);

            //birinci alish haqqinda melumati yazaq sonra ise mehsulun sayini cemleyib yenileyek
            ProductImportWrapper pp = new ProductImportWrapper(date, product);
            purchaseProductDAO.create(pp);

            System.out.println("Product Import Wrapper is: " + pp);

            //saylari yenileyek ki evvelki say silinmesin
            Integer oldQty = productDAO.getByBarcode(product.getBarCode()).getQty();
            product.plusQty(oldQty);

            productDAO.update(product);

            System.out.println("product updated and writed to DB.Product: " + product);

            ApplicationController.getApplicationController().btnStockOnClick();

            System.err.println("Method END: saveButtonOnAction()");

        } else {
            System.err.println("Product is NULL");

            //ilk olaraq Mehsulu Qeydiyyatdan kecirek ve sonra alish edek
            product = new Product();

            product.setBarCode(bTF.getText());
            product.setName(nTF.getText());

            System.out.println("Registering Product" + product);
            productDAO.create(product);
            System.out.println("Product Registered");

            System.out.println("getting product");
            // indi ise yeni qeydiyyatdan kecen mehsulumuzu alaq yeni id-si ile ve alish emri verek sonra
            product = productDAO.getByBarcode(bTF.getText());

            System.out.println("product Received: " + product);

            System.out.println("settings qty, purchase and note data's"
                    + Integer.parseInt(qTF.getText())
                    + Double.parseDouble(pTF.getText())
                    + nTA.getText());

            // indi ise alish edek
            product.setQty(Integer.parseInt(qTF.getText()));
            product.setPurchasePrice(Double.parseDouble(pTF.getText()));
            product.setNote(nTA.getText());

            System.out.println("get date from date picker");

            LocalDate localDate = datePicker.getValue();

            System.out.println("localDate is: " + localDate);

            System.out.println("converting localDate to util.Date");

            Date date = MyDateConverter.asDate(localDate);

            System.out.println("util.Date is: " + date);

            System.out.println("Creating Wrapper for Import");
            //birinci alish haqqinda melumati yazaq sonra ise mehsulun sayini cemleyib yenileyek
            ProductImportWrapper pp = new ProductImportWrapper(date, product);

            System.out.println("wrapper created and is: " + pp);

            purchaseProductDAO.create(pp);

            System.out.println("wrapper created and is: " + pp);

            //saylari yenileyek ki evvelki say silinmesin
            Integer oldQty = productDAO.getByBarcode(product.getBarCode()).getQty();

            product.plusQty(oldQty);

            System.out.println("Saveing Product do DB: " + product);

            productDAO.update(product);

            System.out.println("Import is end:");

            System.out.println("Showing Stock");

            ApplicationController.getApplicationController().btnStockOnClick();

            System.err.println("Method END: saveButtonOnAction()");
        }

    }

    private void clearAll() {
        // null to TextField's
        bTF.setText(null);
        nTF.setText(null);
        qTF.setText(null);
        pTF.setText(null);

        //ad xanasini Enable edek
        nTF.setDisable(false);

        //null to TextAre = note
        nTA.setText(null);

        //tarixe Bugungu tarixi yaziriq
        datePicker.setValue(LocalDate.now());

        //shekileri sifirlayaq
        bIV.setImage(null);
        nIV.setImage(null);
        qIV.setImage(null);
        pIV.setImage(null);
        dIV.setImage(null);

        //labelleri sifirlayaq
        bWL.setText(null);
        nWL.setText(null);
        qWL.setText(null);
        pWL.setText(null);
        dWL.setText(null);

    }

    private void clearTextFields() {
        // null to TextField's
        bTF.setText(null);
        nTF.setText(null);
        qTF.setText(null);
        pTF.setText(null);

        //ad xanasini Enable edek
        nTF.setDisable(false);

        //null to TextAre = note
        nTA.setText(null);

        //tarixe Bugungu tarixi yaziriq
        datePicker.setValue(LocalDate.now());
    }

    private void bTFOnKeyReleased() {

        String input = bTF.getText();
        System.err.println("input: " + input);

        clearAll();

        //Exception in thread "JavaFX Application Thread" java.lang.NullPointerException
        if (input == null) {
            bTF.appendText("");
        } else {
            bTF.appendText(input);
        }

        if (TFValidator.isNotNull(bTF)) {

            product = productDAO.getByBarcode(bTF.getText());

            if (product != null) {
                myView.showOk(bIV);
                bWL.setText("Mehsul Qeydiyyatdan Kecib");

                //melumatlari Productdan Alib Set edek
                nTF.setText(product.getName());

                //eger qiymet 0.0 dirsa demeli mehsul yeni qeydiyytdan kecib ve alish olmayib ve qiymeti set etmirik
                // yox eger qiymet 0.0-dan coxdursa demeli ok Alish olub ve qiymet var ve Qiymeti set edirik
                if (product.getPurchasePrice() > 0.0) {
                    pTF.setText(product.getPurchasePrice().toString());
                }

                //SUPER bir shey gelib aglima mehsul sayini Promt text olarq gosterecem say xanasinda
                qTF.setPromptText("Anbarda olan say: " + product.getQty());

                //ad xanasini sondurek ki redakte etmek olmasin
                nTF.setDisable(true);

                // ve ad xanasinin ounde Ok isharesi gosterek ki ok yani ad qeydiyyatda artiq daxil edilib
                myView.showOk(nIV);

            } else {
                myView.showWarning(bIV);
                bWL.setText("Mehsul Qeydiyyatdan KECMEYIB, Yeni olaraq Qeydiyyata Alinacaq");

                qTF.setPromptText("Sayı daxil edin");
            }

        } else {
            bWL.setText("Zehmet Olmasa barCod Daxil Edin");
            myView.showNo(bIV);

            qTF.setPromptText("Sayı daxil edin");
        }
    }

}
