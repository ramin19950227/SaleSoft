package com.salesoft.controller.anbar;

import com.salesoft.DAO.impl.ProductDAO;
import com.salesoft.DAO.impl.PurchaseProductDAO;
import com.salesoft.controller.ApplicationController;
import com.salesoft.model.Product;
import com.salesoft.model.PurchaseProduct;
import com.salesoft.util.MyDateConverter;
import com.salesoft.util.MyView;
import com.salesoft.util.TextFieldValidator;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // DatePicker-e bugunku tarixi set edek
        datePicker.setValue(LocalDate.now());
        datePicker.setOnAction(event -> {
            //for Testing
            LocalDate date = datePicker.getValue();
            //System.out.println("Selected LocalDate: " + date);
            Date d = MyDateConverter.asDate(date);
            //System.out.println("Selected util.Date: " + d);

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

        // adam Barcodu daxil edib enteri basdiqda yoxlama edirik ve lazimi xanani Focus edirik
        bTF.setOnAction(value -> {
            bTFOnAction();
        });

        nTF.setOnKeyReleased(value -> {
            nTFOnKeyReleased();
        });

        //ad xanassinda enter basildiqda eger bosh deyiles say xanasini fokus ele
        nTF.setOnAction(value -> {
            if (TFValidator.isNotNull(nTF)) {
                qTF.requestFocus();
            }
        });

        //say xanasinda enter basdiqda yoxlayaq tam eded daxil edilibse davamedek
        qTF.setOnAction(value -> {
            if (qTFOnKeyReleased()) {
                pTF.requestFocus();
            }
        });

        qTF.setOnKeyReleased(value -> {
            qTFOnKeyReleased();
        });

        pTF.setOnAction(value -> {
            if (TFValidator.isCorrectInt(pTF)) {
                myView.showOk(pIV);
                pWL.setText(null);
                datePicker.requestFocus();
            } else {
                myView.showNo(pIV);
                pWL.setText("Zehmet Olmasa Qiymeti daxil edin (Keçərli Tamm eded ve ya (Double=?))");
            }

        });

        pTF.setOnKeyReleased(value -> {
            pTFOnKeyReleased();
        });

        clearButton.setOnAction(value -> {
            clearAll();

        });

        saveButton.setOnAction(value -> {
            saveButtonOnAction();
        });

    }

    private void bTFOnKeyReleased() {
        product = null;
        bWL.setText(null);
        String bTemp = bTF.getText();
        clearAll();
        bTF.appendText(bTemp);

        if (TFValidator.isNotNull(bTF)) {
            if (TFValidator.isCorrectInt(bTF)) {

                product = productDAO.getByBarcode(bTF.getText());

                if (product != null) {
                    myView.showOk(bIV);
                    bWL.setText("Mehsul Qeydiyyatdan Kecib");

                    //melumatlari Productdan Alib Set edek
                    nTF.setText(product.getName());
                    qTF.setText("0");
                    pTF.setText(product.getPurchasePrice().toString());

                    //ad xanasini sondurek ki redakte etmek olmasin
                    nTF.setDisable(true);

                } else {
                    myView.showWarning(bIV);
                    bWL.setText("Mehsul Qeydiyyatdan KECMEYIB, Yeni olaraq Qeydiyyata Alinacaq");
                    clearTextFields();
                    bTF.appendText(bTemp);
                }

            } else {
                bWL.setText("Zehmet Olmasa Keçərli barCod Daxil Edin (Tam Ədəd Olmalıdır)");
                myView.showNo(bIV);

                //clearAll(); - Bu metoducagirdiqda barcoduda sifirlayir
            }
        } else {
            bWL.setText("Zehmet Olmasa barCod Daxil Edin");
            myView.showNo(bIV);
            clearTextFields();
        }

    }

    private void bTFOnAction() {
        //yoxlayaq eger barcod yazilibsa 
        if (TFValidator.isCorrectInt(bTF)) {
            //eger daxil edilen barcod bazada varsa Product null deyil ve null deyilse demeli ad xanasinda melumat var
            // ad xanasinda melumat varsa ve barcod xanasinda enter basilibsa
            // say xanasina fokus ele. yox mehsul bazada yoxdursa ve barcod xanasinda enter basilibsa
            // o zaman ad daxil etmek ucun ad xanasni Fokus ele
            if (product != null) {
                qTF.requestFocus();
            } else {
                nTF.requestFocus();
            }
        }
    }

    private void nTFOnKeyReleased() {
        if (TFValidator.isNotNull(nTF)) {
            myView.showOk(nIV);
            nWL.setText("");
        } else {
            myView.showNo(nIV);
            nWL.setText("Zehmet Olmasa ad daxil edin");
        }

    }

    private boolean qTFOnKeyReleased() {
        if (TFValidator.isCorrectInt(qTF)) {
            myView.showOk(qIV);
            nWL.setText(null);
            return true;
        } else {
            myView.showNo(qIV);
            qWL.setText("Zehmet Olmasa Sayi daxil edin (Keçərli Tamam eded)");
            return false;
        }
    }

    private boolean pTFOnKeyReleased() {
        if (TFValidator.isCorrectDouble(pTF)) {
            myView.showOk(pIV);
            pWL.setText(null);
            return true;
        } else {
            myView.showNo(pIV);
            pWL.setText("Zehmet Olmasa Qiymeti daxil edin (Keçərli Tamm eded ve ya (Double=?))");
            return false;
        }
    }

    private void saveButtonOnAction() {

        if (product != null) {

            product.setQty(Integer.parseInt(qTF.getText()));
            product.setPurchasePrice(Double.parseDouble(pTF.getText()));
            product.setNote(nTA.getText());

            LocalDate localDate = datePicker.getValue();
            Date date = MyDateConverter.asDate(localDate);

            //birinci alish haqqinda melumati yazaq sonra ise mehsulun sayini cemleyib yenileyek
            PurchaseProduct pp = new PurchaseProduct(date, product);
            purchaseProductDAO.create(pp);

            //saylari yenileyek ki evvelki say silinmesin
            Integer oldQty = productDAO.getByBarcode(product.getBarCode()).getQty();
            product.plusQty(oldQty);

            productDAO.update(product);

            ApplicationController.getApplicationController().btnStockOnClick();

        } else {

            //ilk olaraq Mehsulu Qeydiyyatdan kecirek ve sonra alish edek
            product = new Product();

            product.setBarCode(bTF.getText());
            product.setName(nTF.getText());

            productDAO.create(product);

            // indi ise yeni qeydiyyatdan kecen mehsulumuzu alaq yeni id-si ile ve alish emri verek sonra
            product = productDAO.getByBarcode(bTF.getText());

            // indi ise alish edek
            product.setQty(Integer.parseInt(qTF.getText()));
            product.setPurchasePrice(Double.parseDouble(pTF.getText()));
            product.setNote(nTA.getText());

            LocalDate localDate = datePicker.getValue();
            Date date = MyDateConverter.asDate(localDate);

            //birinci alish haqqinda melumati yazaq sonra ise mehsulun sayini cemleyib yenileyek
            PurchaseProduct pp = new PurchaseProduct(date, product);
            purchaseProductDAO.create(pp);

            //saylari yenileyek ki evvelki say silinmesin
            Integer oldQty = productDAO.getByBarcode(product.getBarCode()).getQty();
            product.plusQty(oldQty);

            productDAO.update(product);

            ApplicationController.getApplicationController().btnStockOnClick();

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

}
