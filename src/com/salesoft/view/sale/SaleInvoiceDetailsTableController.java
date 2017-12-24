// <editor-fold defaultstate="collapsed" desc="Package declare and Import Block">

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.view.sale;

import com.salesoft.DAO.impl.InvoiceDAO;
import com.salesoft.DAO.impl.ProductDAO;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//</editor-fold>

/**
 * FXML Controller class Bu Class Qaime nomresi ile Qaimelere baxish ve redakte
 * etme hemcinin cap emeleiyyatiin heyata kecirilmesi ucun istifade edilir
 *
 * @author Ramin
 */
public class SaleInvoiceDetailsTableController implements Initializable {

    // <editor-fold defaultstate="collapsed" desc="Declaration Block">
    /**
     * Declaration Class Properties lazim olan propertileri elan edirik
     */
    /**
     * InvoiceItem Edit Properties
     */
    //geri qaytarilma sayinin yazilacagi xana
    @FXML
    private TextField productReturnQty;

    //geri qaytarilma sebebinin yazilacagi xana
    @FXML
    private TextField productReturnNote;

    @FXML
    private Button returnButton;

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
    private final ProductDAO ProductDAO = new ProductDAO();
    private  InvoiceDAO invoiceDAO = new InvoiceDAO();

    // aldigimiz invoice obyektini burda saxlayiriq
    Invoice invoice = null;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="initialize() - method Block">
    /**
     * Initializes the controller class. ishe bashlamazdan evvel bezi
     * obyektlerimizi hazirlayiriq
     *
     * @param url = bu fxml faylimizin unvanin qaytarir URL olaraq
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        barCodeColumn.setCellValueFactory(cellData -> cellData.getValue().productBarCodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sayColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        meblegColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());

        invoiceTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {

                    // burda yoxlayiriq secim olub amma obyekt null deyilse
                    //gosteririk sag Redakte xanasinda
                    // cunki bezen avtomatik null gelir bura oz-ozune 
                    if (newValue != null) {

                        setInvoiceItemEditFields(newValue);
                        selectedInvoiceItem = newValue;

                    } else {
                        System.out.println("cartTable Selection PRODUCT NULL AUTOCALL");
                    }

                });
        // HE Super Ishleyirrmish
        clearField();

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="initDataById(int id)">
    /**
     * Bu metoda verilen id ile axtarish edecek ve qeyd tapacaqsa gosterecek
     * cedvelde, yox eger tpa bilmese xeberdarliq cixaracaq
     *
     * @param id
     */
    @FXML
    public void initDataById(int id) {
        invoice = invoiceDAO.getInvoiceById(id);

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

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="onActionShowInvoiceButton()">
    /**
     * Mushteri Qaime nomresini yazib enteri basdiqda bu Metod yazilan qaime
     * nomresini initDataById() metoduna gonderecek
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

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="errorAlert(String title, String header, String content)">
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
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="onActionPrintButton()">
    /**
     * Qaime nomresini yazib Cap et duymesini basdiqda bu metot cap ishlemini
     * bashladir
     */
    @FXML
    private void onActionPrintButton() {
        if (invoice != null) {
            new PrintInvoice(new Stage(), invoice).start();
        } else {
            errorAlert("Zehmet olmasa Qaime nomresini yazin", "Zehmet olmasa Qaime nomresini yazin", "Sonra qaimeni gosterin daha sonra cap edin");
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="onActionReturnButton()">                          
    /**
     * Geri qaytarilma duymesi basildiqda Bu metod ishe dushur
     */
    @FXML
    private void onActionReturnButton() {

        /**
         * Heleki bele Edirem mehsulu id ile deyil bar cod ile yoxlayiram bazada
         * olub olmadigini eger varsa ... qaytarma emeliyyatini basha catdiriram
         */
        /**
         * Heleki eger mehsul varsa Qaytarma emeliyyatini hazirlayim, Sonra ise
         * mehsul satilib sonrada bazadan silinib indi ise qaytarmaq
         * isteyirlerse... ne etmek lazimdir? --> , bu Funksiyani daha sonra
         * Yigacam inshaAllah
         */
        // eger cedvelden mehsul secilmeyibse xeberdarliq cixaririq
        if (selectedInvoiceItem == null) {
            errorAlert("Zehmet olmasa Cedvelden Mehsul Secin", "Qaytarmaq istediyiniz Mehsulu Secin", "Mehsulu Secin");
            return;
        }

        // sayi yoxla gor invoice listde olan saydan cox deyil ve0-dan azdeyil 0deyil
        // sonra malin sayini invoice listde yazildigi qeder azald ve Mehsul bazasindada hemin qeder artir
        // 1-ci mehsulu bazada artiririq eger bazada mehsul yoxdursa silinibse? 
        // yeni alish nece ediremse ocur mi edim? gorem
        // heleki o olasiligi dushunmurem
        // InvoiceItemi-in satish sayini alaq ve serhed qoyaq ki ondan cox qaytarablmesin 
        Integer itemQty = selectedInvoiceItem.getQty();

        if (isInputValid()) {
            Integer enteredQty = Integer.valueOf(productReturnQty.getText());

            //eslinde bu yoxlamaya ehtiyyac yoxdur artiq
            if (enteredQty <= itemQty && enteredQty > 0) {

                // mehsulun barcodunu aliriq
                String productBarCode = selectedInvoiceItem.getProductBarCode();

                //eger yoxlamani kecdise yani xanaya kecerli melumat yaiibsa yazdigi say satish sayina beraber
                //ve ya ondan az dirsa ve eyni zamanda 0-dan coxdursa
                //ashagidakileri ele
                //indi mehsulumuzu alaqki sayini bilek sonra onun ustune qaytarilani yazaq
                Product returnedProduct = ProductDAO.getByBarcode(productBarCode);

                //yoxlyiriq eger bu barcod- ile bazada mehsul varsa davam edirik 
                //yoxdursa NullPointerException cixacaq. bu olmasin deye yoxlayiram -
                if (returnedProduct != null) {

                    // mehsulumuzun faktiki sayini aliriq
                    Integer productCurrentQty = returnedProduct.getQty();

                    // vee faktiki sayin ustune qaytarilan sayi yaziram neticeni bazaya yenilemeye gonderecem
                    Integer productResultQty = productCurrentQty + enteredQty;

                    returnedProduct.setQty(productResultQty);

                    //aldgimiz neticeni bazaya gonderirik ki id ile uygun olan mehsulun sayini yenile
                    ProductDAO.update(returnedProduct);

                    // indi ise InvoiceItem-in sayini azaltmaliyiq
                    // ashagida itemimizin hal hazirdaki sayini aliriq
                    Integer invoiceItemCurrentQty = selectedInvoiceItem.getQty();

                    //Itemimizin - indiki meblegini yazaq birqiraga
                    Double qaytarmadanEvvelkiMebleg = selectedInvoiceItem.getTotalPrice();

                    // Item-imizin sayini aldiqdan sonra qaytarma sayini (daxil edilen sayi) cixiriq 
                    Integer itemQaliqSay; // inglicse qaliq ne deyilir Oyrenecem ))

                    // ve qaliq sayini elde edirik
                    itemQaliqSay = invoiceItemCurrentQty - enteredQty;

                    //Qaytardigimiz mehsulun qaliq sayini set etdirik ( meselen 5 idi 3-unu qaytardiq 2 qaldi
                    // o 2-nide set edirik obyektimize
                    selectedInvoiceItem.setQty(itemQaliqSay);

                    //Item-in mebleginide hesablamaliyiq onu unutmushuq
                    // mehsul 5 eded idi qiymeti 1 manatdan mebleg 5 edir
                    // indi ise 3 qaytardiq 2 qaldi
                    // satish qiymetini aliriq == 1 azn
                    // qaliq sayina vururuq 2*1=2 AZN
                    // ve aldigimiz meblegi yaziriq item-i
                    // Bir problem var Item-imizide satish qiymeti yoxdur heleki
                    // CIXISH YOLU - meblegi alib bolurem evvelki saya
                    Double alishQiymeti = selectedInvoiceItem.getTotalPrice() / invoiceItemCurrentQty;

                    selectedInvoiceItem.setTotalPrice(selectedInvoiceItem.getQty() * alishQiymeti);

                    // indi ise hazir Item-obyektimizi gonderirik yenilemeye))
                    invoiceDAO.updateInvoiceItem(selectedInvoiceItem);

                    //DIQQET: item-in meblegini hesablayiriq amma ki Invoice-nin meblegi qaldi onuda duzeltmek lazimdir
                    //burda qaytarmadan sonraki meblegi aliriq
                    Double qaytarmadanSonrakiMebleg = selectedInvoiceItem.getTotalPrice();

                    // ve burda evvelki meblegden Sonraki meblegi cixiriq ve neticede FerqMeblegini aliriq
                    Double ferqMeblegi = qaytarmadanEvvelkiMebleg - qaytarmadanSonrakiMebleg;
                    System.out.println("evvelki mebleg: " + qaytarmadanEvvelkiMebleg);
                    System.out.println("sonraki mebleg: " + qaytarmadanSonrakiMebleg);

                    System.out.println("ferq mebleg: " + ferqMeblegi);

                    //indi ise Invoce - obyektimizi alib onu yenilemeliyik
                    //obyektin evvelki meblegini aliriq
                    Double invoiceEvvelkiMebleg = invoice.getTotalPrice();

                    // invoice-obyektinden ferq meblegi cixib gonderirik yenilenmeye
                    Double invoiceSonMebleg = invoiceEvvelkiMebleg - ferqMeblegi;

                    // aldigimiz son meblegi yaziriq invoice obyektimize
                    invoice.setTotalPrice(invoiceSonMebleg);

                    // ve hazir Invoice Obyektimizi yenileyirik
                    invoiceDAO.updateInvoice(invoice);

                    //yenilemeler bitdikden sonra indi Invoice Obyektimizi de yenilemeliyik
                    initDataById(invoice.getId());

                } else {
                    System.err.println("Returner Product Not Found");
                    errorAlert("XETA", "Bu barcodla Bazada Mehsul Tapilmadi", "Mehsulu Yeniden Qeydiyyatdan kecirdin");

                }
            } else {
                System.err.println("Returner Product Qty is Not Correct");
                errorAlert("Zehmet olmasa Qaytarma Sayini dogru Daxil edin", "Qaytarmaq sayini Dogru daxil edin", "Sayi Daxil edin");
            }
        } else {
            System.err.println("Inputi is Not Valid");
        }

        //Qaytar duymesini basdiqdan sonra qaytarma emeliyyatidabitdikden sonra
        // xanalarimizi yenede evvelki veziyyetine getiririk
        clearField();

        //gorek bu metodnece ishleyecek 
        //adina gore secimi legv etmelidir
        // SUPER bu da ishleyir amma bu lazim deyil qoy qaytarilan item- hele secili qalsin ki
        //adam gorsun ne getdi ne qaldi
        //invoiceTable.getSelectionModel().clearSelection();
        //ve bir meselede var meselen say 5-dir adam 3-nu qaytardi,
        // qaytarma emeliyyatindan sonra xanalar clearFields() metodu cagrildigi ucun
        // sonulu qalir, qaytarmadan sonra yene setedek eger 0-dirsa sondursun yoxdeilse
        // o zaman xanalari aktiv elesin
        setInvoiceItemEditFields(selectedInvoiceItem);
        //super ishledi
        //.
    }//  </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="setInvoiceItemEditFields(InvoiceItem invoiceItem)">                          
    /**
     * Aldigimiz InvoiceItem - obyektini bumetoda verdikde onu yandaki Redakte
     * Paneline yazir
     *
     * @param invoiceItem
     */
    private void setInvoiceItemEditFields(InvoiceItem invoiceItem) {
        productReturnQty.setText(invoiceItem.getQty().toString());
        returnButton.requestFocus();

        // burda yoxlayiriq eger cedvelden secdiyimiz ve redakte etmek istediyimiz
        // item-in sayi 0 dirsa o zaman xanalari evvelki veziyyetine qaytaririrq
        //yani sondururuk, sonulu veziyyete qaytaririq
        if (invoiceItem.getQty() == 0) {
            // emeliyyat ucun bu metodu cagiririq
            clearField();

            //yox eger say 0-deyilse o zaman xanalari aktiv edirik
            // bu yontemle biz bir cox Seyfelerin ve Problemlerin qarshisini aliriq
        } else {
            // say xanasini ktiv edirik
            productReturnQty.setDisable(false);

            // qaytar duymesini aktiv edirik
            returnButton.setDisable(false);

        }

    }//  </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="isInputValid()">                          
    /**
     * Bu Metod Xanalarimiza melumatin dogru daxil olub olmadigin yoxlayir eger
     * melumatlar dogru daxil edilibse o zaman true qaytarir yox eger seyf varsa
     * Alert Gosterir ve seyfleri qeyd edir
     *
     * @return
     */
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
                    errorMessage += "Sayi 1-dən az Ola bilmez!\n";
                } else if (qty > selectedInvoiceItem.getQty()) {
                    errorMessage += "Sayi Dogru daxil edin. Satish sayindan cox qaytarmaq olmaz!\n";
                    errorMessage += "Mehsul: \t" + selectedInvoiceItem.getName() + "\n";
                    errorMessage += "Qaytara Bileceyiniz Maksimal say Say: \t " + selectedInvoiceItem.getQty() + "\n";
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
    }//  </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="clearField()">                          
    /**
     * Bu metod xanalari evvelki veziyyetine getirir, eger hec bir secim
     * edilmeyibse o zaman say bolumune hecne daxil etmeye etiyyac yoxdur ve
     * qaytar duymesini de basmaga ehtiyyac yoxdur
     */
    private void clearField() {
        // eger hec bir secim edilmeyibse Qaytar duymesini 
        // default olacaq sondururk yani normalda sonulu olsun 
        // mehsul secib ve qaytara bileceyimzsay oldugu zaman
        // aktiv ele
        returnButton.setDisable(true);

        // say xanasini sondururk, secim etdikde aktiv edeceyik
        productReturnQty.setDisable(true);

        //Heleki bu Funksiya hazir deyil deye sondururem
        //ve birdeki adlandirmada seyf buraxmisham 
        //productReturnNote - yoc
        // noteField - sadece olsaydi bes edirdi mene ele gelir
        productReturnNote.setDisable(true);

    }//  </editor-fold> 
}
