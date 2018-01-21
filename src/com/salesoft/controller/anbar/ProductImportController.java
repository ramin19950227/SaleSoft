package com.salesoft.controller.anbar;

import com.salesoft.DAO.impl.ProductDAO;
import com.salesoft.DAO.impl.PurchaseProductDAO;
import com.salesoft.controller.ApplicationController;
import com.salesoft.model.Product;
import com.salesoft.model.ProductImportWrapper;
import com.salesoft.util.MyDateConverter;
import com.salesoft.util.MyView;
import com.salesoft.util.TextFieldValidator;
import com.salesoft.util.UserOperationLogger;
import java.io.PrintWriter;
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

    @FXML
    private TextField bTF, nTF, qTF, pTF, sTF;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea nTA;
    @FXML
    private ImageView bIV, nIV, qIV, pIV, sIV, dIV;
    @FXML
    private Label bWL, nWL, qWL, pWL, sWL, dWL;
    @FXML
    private Button saveButton, clearButton;

    private final ProductDAO productDAO = new ProductDAO();
    private final PurchaseProductDAO purchaseProductDAO = new PurchaseProductDAO();
    private final MyView myView = new MyView();
    private final TextFieldValidator TFValidator = new TextFieldValidator();
    private final Set<String> barCodeSet = new HashSet<>();

    private AutoCompletionBinding<String> bindAutoCompletion;
    private Product product;
    private final PrintWriter LOGWriter = UserOperationLogger.getLogWriter();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LOGWriter.println("");
        LOGWriter.println("__________________________________________________________________________");
        LOGWriter.println("ProductImportController.initialize()");
        LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

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
            //heleki bele qalsin mene ne lazimdir? secim edirsen vsyoda olur bitir
            bTFOnKeyReleased();
            qTF.requestFocus();
        });

        // Default olaraq Sondurek ki yoxlamadan kecse ancaq aclsin ve ishlesin
        BooleanBinding binding = bTF.textProperty().isEmpty()
                .or(nTF.textProperty().isEmpty())
                .or(qTF.textProperty().isEmpty())
                .or(pTF.textProperty().isEmpty())
                .or(sTF.textProperty().isEmpty());

        saveButton.disableProperty().bind(binding);

        // DatePicker-e bugunku tarixi set edek
        datePicker.setValue(LocalDate.now());
        datePicker.setOnAction(event -> {
            //for Testing
//            LocalDate date = datePicker.getValue();
//            //LOGWriter.println("Selected LocalDate: " + date);
//            Date d = MyDateConverter.asDate(date);
//            //LOGWriter.println("Selected util.Date: " + d);

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
            if (TFValidator.isCorrectDouble(pTF)) {
                myView.showOk(pIV);
                pWL.setText(null);

            } else {
                myView.showNo(pIV);
                pWL.setText("Zehmet Olmasa Keçərli Qiymet daxil edin");

                pTF.setText("");
            }
        });

        pTF.setOnAction(value -> {
            sTF.requestFocus();
        });

        sTF.setOnKeyReleased(value -> {
            if (TFValidator.isCorrectDouble(sTF)) {
                myView.showOk(sIV);
                sWL.setText(null);

            } else {
                myView.showNo(sIV);
                sWL.setText("Zehmet Olmasa Keçərli Qiymet daxil edin");

                sTF.setText("");
            }
        });

        sTF.setOnAction(value -> {
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
        LOGWriter.println("");
        LOGWriter.println("__________________________________________________________________________");
        LOGWriter.println("ProductImportController.saveButtonOnAction()");
        LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        LOGWriter.println("Fields Status");
        LOGWriter.println("BarCode Field: " + bTF.getText());
        LOGWriter.println("Name Field: " + nTF.getText());
        LOGWriter.println("QTY Field: " + qTF.getText());
        LOGWriter.println("price Field: " + pTF.getText());

        if (product != null) {
            LOGWriter.println("Product is Exist and will Imported (Updated)");
            LOGWriter.println("Product Fist State");
            LOGWriter.println(product);

            product.setQty(Integer.parseInt(qTF.getText()));
            product.setPurchasePrice(Double.parseDouble(pTF.getText()));
            product.setSalePrice(Double.parseDouble(sTF.getText()));
            product.setNote(nTA.getText());

            LocalDate localDate = datePicker.getValue();
            Date date = MyDateConverter.asDate(localDate);

            //birinci alish haqqinda melumati yazaq sonra ise mehsulun sayini cemleyib yenileyek
            ProductImportWrapper pp = new ProductImportWrapper(date, product);

            LOGWriter.println("Product is READY to Write Import History");
            LOGWriter.println(product);
            LOGWriter.println(pp);

            purchaseProductDAO.create(pp);

            //saylari yenileyek ki evvelki say silinmesin
            Integer oldQty = productDAO.getByBarcode(product.getBarCode()).getQty();
            product.plusQty(oldQty);

            LOGWriter.println("Product old qty= " + oldQty);
            LOGWriter.println("Product Last State Is: ");
            LOGWriter.println(product);

            productDAO.update(product);
            LOGWriter.println("Product is Succes Imported");

            ApplicationController.getApplicationController().btnStockOnClick();

        } else {
            System.err.println("Product is NULL");

            //ilk olaraq Mehsulu Qeydiyyatdan kecirek ve sonra alish edek
            product = new Product();

            product.setBarCode(bTF.getText());
            product.setName(nTF.getText());

            LOGWriter.println("Registering Product" + product);
            productDAO.create(product);
            LOGWriter.println("Product Registered");

            LOGWriter.println("getting product");
            // indi ise yeni qeydiyyatdan kecen mehsulumuzu alaq yeni id-si ile ve alish emri verek sonra
            product = productDAO.getByBarcode(bTF.getText());

            LOGWriter.println("product Received: " + product);

            LOGWriter.println("settings qty, purchase and note data's"
                    + Integer.parseInt(qTF.getText())
                    + Double.parseDouble(pTF.getText())
                    + nTA.getText());

            // indi ise alish edek
            product.setQty(Integer.parseInt(qTF.getText()));
            product.setPurchasePrice(Double.parseDouble(pTF.getText()));
            product.setSalePrice(Double.parseDouble(sTF.getText()));
            product.setNote(nTA.getText());

            LOGWriter.println("get date from date picker");

            LocalDate localDate = datePicker.getValue();

            LOGWriter.println("localDate is: " + localDate);

            LOGWriter.println("converting localDate to util.Date");

            Date date = MyDateConverter.asDate(localDate);

            LOGWriter.println("util.Date is: " + date);

            LOGWriter.println("Creating Wrapper for Import");
            //birinci alish haqqinda melumati yazaq sonra ise mehsulun sayini cemleyib yenileyek
            ProductImportWrapper pp = new ProductImportWrapper(date, product);

            LOGWriter.println("wrapper created and is: " + pp);

            purchaseProductDAO.create(pp);

            LOGWriter.println("wrapper created and is: " + pp);

            //saylari yenileyek ki evvelki say silinmesin
            Integer oldQty = productDAO.getByBarcode(product.getBarCode()).getQty();

            product.plusQty(oldQty);

            LOGWriter.println("Saveing Product do DB: " + product);

            productDAO.update(product);

            LOGWriter.println("Import is end:");

            LOGWriter.println("Showing Stock");

            ApplicationController.getApplicationController().btnStockOnClick();

            System.err.println("Method END: saveButtonOnAction()");
        }

    }

    private void clearAll() {
        LOGWriter.println("");
        LOGWriter.println("__________________________________________________________________________");
        LOGWriter.println("ProductImportController.clearAll()");
        LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        // Clear TextField's
        bTF.setText("");
        nTF.setText("");
        qTF.setText("");
        pTF.setText("");
        sTF.setText("");

        //ad xanasini Enable edek
        nTF.setDisable(false);

        //null to TextAre = note
        //bura null yox bosh String yazacam ki Product cedveline de null dushmesin yoxsa hele sozle null yazilir
        nTA.setText("");

        //tarixe Bugungu tarixi yaziriq
        datePicker.setValue(LocalDate.now());

        //shekileri sifirlayaq
        bIV.setImage(null);
        nIV.setImage(null);
        qIV.setImage(null);
        pIV.setImage(null);
        sIV.setImage(null);
        dIV.setImage(null);

        //labelleri sifirlayaq
        bWL.setText("");
        nWL.setText(null);
        qWL.setText(null);
        pWL.setText(null);
        sWL.setText(null);
        dWL.setText(null);

    }

    private void clearFields() {
        LOGWriter.println("");
        LOGWriter.println("__________________________________________________________________________");
        LOGWriter.println("ProductImportController.clearTextFields()");
        LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        // Clear TextField's
        bTF.setText("");
        nTF.setText("");
        qTF.setText("");
        pTF.setText("");
        sTF.setText("");
        nTA.setText("");

        //ad xanasini Enable edek
        nTF.setDisable(false);

        //tarixe Bugungu tarixi yaziriq
        datePicker.setValue(LocalDate.now());
    }

    private void bTFOnKeyReleased() {
        LOGWriter.println("");
        LOGWriter.println("__________________________________________________________________________");
        LOGWriter.println("ProductImportController.bTFOnKeyReleased()");
        LOGWriter.println("Action Date: " + MyDateConverter.utilDate.toString(new Date()));

        LOGWriter.println("Fields:");
        LOGWriter.println("BarCode Field: " + bTF.getText());

        String input = bTF.getText();

        clearAll();

        //Exception in thread "JavaFX Application Thread" java.lang.NullPointerException
        if (input == null) {
            bTF.appendText("");
        } else {
            bTF.appendText(input);
        }

        Boolean isBarCodeTextFieldInputValid = TFValidator.isNotNull(bTF);
        LOGWriter.println("isBarCodeTextFieldInputValid: " + isBarCodeTextFieldInputValid);

        if (isBarCodeTextFieldInputValid) {

            product = productDAO.getByBarcode(bTF.getText());

            if (product != null) {
                myView.showOk(bIV);
                bWL.setText("Mehsul Qeydiyyatdan Kecib");
                LOGWriter.println("Mehsul Qeydiyyatdan Kecib");
                LOGWriter.println(product);

                //melumatlari Productdan Alib Set edek
                nTF.setText(product.getName());

                //SUPER bir shey gelib aglima mehsul sayini Promt text olarq gosterecem say xanasinda
                qTF.setPromptText("Anbarda olan say: " + product.getQty());

                //eger qiymet 0.0 dirsa demeli mehsul yeni qeydiyytdan kecib ve alish olmayib ve qiymeti set etmirik
                // yox eger qiymet 0.0-dan coxdursa demeli ok Alish olub ve qiymet var ve Qiymeti set edirik
                if (product.getPurchasePrice() > 0.0) {
                    pTF.setText(product.getPurchasePrice().toString());
                }

                //eger qiymet 0.0 dirsa demeli mehsul yeni qeydiyytdan kecib ve alish olmayib ve qiymeti set etmirik
                // yox eger qiymet 0.0-dan coxdursa demeli ok Alish olub ve qiymet var ve Qiymeti set edirik
                if (product.getSalePrice() > 0.0) {
                    sTF.setText(product.getSalePrice().toString());
                }

                //ad xanasini sondurek ki redakte etmek olmasin
                nTF.setDisable(true);

                // ve ad xanasinin ounde Ok isharesi gosterek ki ok yani ad qeydiyyatda artiq daxil edilib
                myView.showOk(nIV);

            } else {
                myView.showWarning(bIV);
                bWL.setText("Mehsul Qeydiyyatdan KECMEYIB, Yeni olaraq Qeydiyyata Alinacaq");
                LOGWriter.println("Mehsul Qeydiyyatdan KECMEYIB, Yeni olaraq Qeydiyyata Alinacaq");
                LOGWriter.println(product);

                qTF.setPromptText("Sayı daxil edin");
            }

        } else {
            bWL.setText("Zehmet Olmasa barCod Daxil Edin");
            myView.showNo(bIV);

            qTF.setPromptText("Sayı daxil edin");
        }
    }

}
