/**
 * TEKLIFLER
 * XAnalarin validationlarini metoda cixartmaq olar
 *
 */
package com.salesoft.controller.sale;

import com.salesoft.DAO.impl.InvoiceDAO;
import com.salesoft.DAO.impl.InvoiceItemDAO;
import com.salesoft.DAO.impl.ProductDAO;
import com.salesoft.controller.ControllersRef;
import com.salesoft.model.Cart;
import com.salesoft.model.CartItem;
import com.salesoft.model.Invoice;
import com.salesoft.model.InvoiceItem;
import com.salesoft.model.Product;
import com.salesoft.util.MyJRViewer;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class Mehsul satishi bolumu Sebet yani
 *
 * @author Ramin
 */
public class ProductSaleCartController implements Initializable {

    @FXML
    private TextField barCodeField, nameField, qtyField, onePrice, totalPrice;

    @FXML
    private Label cartTotalPrice;
    @FXML
    private Button addButton;

    /**
     * eger mehsul elave edilirse barcodla bu falsedir yox eger mehsul cedvelden
     * secilirse o zaman yenileme emeliyyati bashlayir ve elave et yox yenile
     * yazilir duymede
     */
    private boolean update = false;

    //-----------------------------SAG EDIT PANEL PROPERTIES
    /**
     * Mushteri adi
     */
    @FXML
    private TextField customerName;

    @FXML
    private TableView<CartItem> cartTable;
    @FXML
    private TableColumn<CartItem, String> nameColumn;
    @FXML
    private TableColumn<CartItem, Number> sayColumn, meblegColumn;

    private final ObservableList<CartItem> productList = FXCollections.observableArrayList();
    private final Cart cart = new Cart();

    //barcod vuranda producttu burda saxlayir
    private Product barCodeEnteredProduct = null;

    // barcodsuz mehsulu burda saxlayacam
    private Product nameEnteredProduct = null;
    private Integer nameEnteredProductCounter = 0;
    private final ProductDAO productDAO = new ProductDAO();
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final InvoiceItemDAO invoiceItemDAO = new InvoiceItemDAO();

    //barcodu daxil elemeye bashladiqda avtomatik teklif elesin
    private AutoCompletionBinding<String> bindAutoCompletion;
    private final Set<String> barCodeSet = new HashSet<>();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<Product> list = productDAO.getAll();

        list.forEach((p) -> {
            barCodeSet.add(p.getBarCode());
        });

        bindAutoCompletion = TextFields.bindAutoCompletion(barCodeField, barCodeSet);

        bindAutoCompletion.addEventHandler(javafx.event.EventType.ROOT, (event) -> {
            //barCodeField.setText("");
            onActionBarcodeField();
        });

        barCodeField.setPromptText("Barcodu Daxil edin");

        barCodeField.setOnMouseClicked((ecent) -> {
            barCodeField.selectAll();
            barCodeField.requestFocus();
        });

        qtyField.setPromptText("Say daxil edin");

        //mebleg Label-imizi sifirlayiriq
        cartTotalPrice.setText("0.00");

        /**
         * Sutularimizin ve Cedvelin Hazirlanmasi
         */
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sayColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        meblegColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());

        cartTable.setOnKeyReleased((KeyEvent t) -> {
            KeyCode key = t.getCode();
            if (key == KeyCode.DELETE) {
                if (cartTable.getSelectionModel().getSelectedItem() == null) {
                    MyAlert("Zehmet Olmasa Mehsulu Secin", "Zehmet Olmasa Mehsulu Secin", "Zehmet Olmasa Mehsulu Secin");
                } else {
                    int key2 = (int) cartTable.getSelectionModel().getSelectedItem().getId();
                    System.out.println("line 157, key2=" + key2);
                    cart.deleteCartitem(key2);
                    updateTable();
                    clearFields();

                }
            }

        });

        cartTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {

                    // bazada axtarish verdikde Error cixirdi
                    // bu JavaFx-de table view cox qeribedir eee oz ozune avtomatik bu metodu cagirir
                    // ona gore birinci yoxlayiram sonra.
                    // yoxsa gonderirem ve error cixir nullPointerException
                    // eger mehsul barcodlu mehsuldursa ve id-si varsa
                    // yani 0-dan ashagi olan barcodsuz bazada olmayan adli mehsulardir
                    if (newValue != null && newValue.getId() >= 0) {
                        barCodeField.setText(newValue.getProduct().getBarCode());
                        onActionBarcodeField();
                        qtyField.setText(newValue.getQty().toString());
                        onePrice.setText(newValue.getSalePrice().toString());
                        totalPrice.setText(newValue.getTotalPrice().toString());

                        //eger mehsul id-si 0-dan azdirsa
                    } else if (newValue != null && newValue.getId() < 0) {
                        //mehsulumuzu aliriq ve saxlayiriq
                        nameEnteredProduct = newValue.getProduct();

                        //barcod xanamiza yaziriq ki mehsul barcodsuz mehuldur
                        barCodeField.setText("Barcodsuz Məhsul");

                        //barcod xanasini sondururuk yani Disable edirik
                        barCodeField.setDisable(true);

                        nameField.setText(newValue.getName());
                        qtyField.setText(newValue.getQty().toString());
                        onePrice.setText(newValue.getSalePrice().toString());
                        totalPrice.setText(newValue.getTotalPrice().toString());
                    } else {
                        System.out.println("cartTable Selection PRODUCT NULL AUTOCALL");
                    }
                });
    }

    /**
     * Barcod daxil edib enter basdiqda bu metod ishe dushur
     */
    @FXML
    private void onActionBarcodeField() {

        if (barCodeField.getText() == null || barCodeField.getText().trim().isEmpty()) {
            clearFields();
            barCodeField.requestFocus();

            //Sistemin standart error sesi - dit edir
            Toolkit.getDefaultToolkit().beep();

            return;
        }

        String barCode = barCodeField.getText();

        //aldigimiz mehsulu vururuq yaddasha
        barCodeEnteredProduct = productDAO.getByBarcode(barCode);

        // xanalarda daha evvelden qalmish melumatlar ola biler
        //onlari silek sonra emeliyyat aparaq
        clearFields();

        //Mehsulu aldiqdan sonra yoxlayiriq eger sebette varsa ne edek?
        // sayini 1 ed artiraqmi
        // yoxsa gosterek sag panelde ve update edekmi?
        if (barCodeEnteredProduct != null && barCodeEnteredProduct.getQty() != 0) {

            if (!cart.containsKey(barCodeEnteredProduct.getId())) {
                setProductToEditFields(barCodeEnteredProduct);
                updateStatusChange(false);
            } else {
                setProductToEditFields(barCodeEnteredProduct);
                updateStatusChange(true);
            }

        } else if (barCodeEnteredProduct != null && barCodeEnteredProduct.getQty() == 0) {

            nameField.setText("Məhsul Qalmayıb");
            barCodeField.selectAll();

            Toolkit.getDefaultToolkit().beep();

        } else {

            nameField.setText("Məhsul Tapılmadı");
            barCodeField.selectAll();

            Toolkit.getDefaultToolkit().beep();

        }

    }

    /**
     * Barcodsuz Mehsulun adini daxil edib enter basdiqda bu metod ishe dushur
     */
    @FXML
    private void onActionNameField() {
        if (barCodeEnteredProduct == null && nameField.getText().length() > 0) {

            Product p = new Product();
            p.setId(--nameEnteredProductCounter);
            p.setName(nameField.getText());
            p.setQty(1000);
            p.setPurchasePrice(1.5);
            p.setBarCode("");
            nameEnteredProduct = p;
            cart.addCartitem(new CartItem(nameEnteredProduct, 1, 10));

            updateTable();
            clearFields();
            barCodeField.requestFocus();

        }
    }

    /**
     * Mehsulun Sayini daxil edib enter basdiqda bu metod ishe dushur
     */
    @FXML
    private void onActionQtyField() {
        //Burda Menqtiqi Seyf Qurmusham Duzeldek
        //demeli birinci ne etmek lazimdir
        //1. Xanaya daxil edilen melumatin valid olub olmadigini yoxlayaq
        //2. Sonra Daxil Edilen mehsul Barcodlu Mehsuldursa - bizde barcodsuz mehsulda daxil edib satmaq olacaq
        // demeli barcodlu mehsuldursa 
        // sayini yoxlayiriq If Blokunun icinde ve sayda Validdirse o zaman emeliyyati davam eele
        String sayString = qtyField.getText();
        Integer say;

        //1 - eger validdirsa true deyilse false
        if (!(sayString == null || sayString.equals("") || sayString.trim().length() == 0)) {
            try {
                say = Integer.parseInt(sayString);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Zehmet Olmasa Kecerli say Daxil edin");
                alert.setHeaderText("Sayi Daxil edin");
                alert.setContentText("Sayi Daxil Edin");
                alert.showAndWait();
                return;
            }

            // bu yerde say dogru daxil edilib indi bashlayaq say emeliyyatina
            //eger mehsul barcodludursa ....
            //yox eger adlidirsa
            //eksi teqdirde mehsulun barcodunu daxil edin
            if (barCodeEnteredProduct != null) {
                int maxSay = barCodeEnteredProduct.getQty();

                if (say <= maxSay) {
                    //mehsulun 1- ededinin qiymetini  alriq TextField-den
                    Double salePrice = Double.valueOf(onePrice.getText());

                    //Meblegi hesablayiriq
                    Double mebleg = say * salePrice;
                    totalPrice.setText(mebleg.toString());
                    onePrice.requestFocus();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Zehmet Olmaza Sayi Dogru Daxil Edin");
                    alert.setHeaderText("Anbarda Bu sayda Mehsul yoxdur");
                    alert.setContentText("Siz Daxil etdiyiniz say: " + say + "\n Anbarda Olan say: " + maxSay);
                    alert.showAndWait();
                    qtyField.setText(null);
                }
            } else if (nameEnteredProduct != null) {
                //mehsulun 1- ededinin qiymetini  alriq TextField-den
                Double salePrice = Double.valueOf(onePrice.getText());

                //Meblegi hesablayiriq
                Double mebleg = say * salePrice;
                totalPrice.setText(mebleg.toString());
                onePrice.requestFocus();
            } else {
                clearFields();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Zehmet Olmasa Sayi Daxil Edib Sonra Enteri basin");
            alert.setHeaderText("Sayi Daxil edin");
            alert.setContentText("Sayi Daxil Edin");
            alert.showAndWait();
        }
    }

    /**
     * Mehsulun 1-edediinn alish qiymetin daxil edib enter basdiqda bu metod
     * ishe dushur
     */
    @FXML
    private void onActionOnePriceField() {
        System.out.println("onActionOnePriceField");
        //mebleg hesabla

        String onePriceString = this.onePrice.getText();

        System.out.println("onePriceString=:" + onePriceString);
        if (onePriceString == null || onePriceString.equals("")) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Zehmet Olmaza Qiymeti Daxil Edib Sonra Enteri basin");
            alert.setHeaderText("Qiymeti Daxil edin");
            alert.setContentText("Qiymeti Daxil Edin");
            alert.showAndWait();
        } else {
            //mehsulun 1- ededinin qiymetini  alriq TextField-den
            Double onePriceDouble = Double.valueOf(this.onePrice.getText());

            //mehsulun sayini aliriq
            Integer say = Integer.valueOf(this.qtyField.getText());

            //Meblegi hesablayiriq
            Double mebleg = say * onePriceDouble;
            totalPrice.setText(mebleg.toString());
            this.totalPrice.requestFocus();
        }

    }

    /**
     * Mehsulun Cemi Meblegini daxil edib enter basdiqda bu metod ishe dushur
     */
    @FXML
    private void onActionTotalPriceField() {
        System.out.println("onActionTotalPriceField");
        //burda 1-ededin qiymetini hesablayiriq one hesabla

        Double mebleg = null, birEdediQiymeti = null;

        //xanada yaziln sayi aliriq
        String meblegString = totalPrice.getText();

        System.out.println("meblegString=:" + meblegString);
        if (meblegString == null || meblegString.equals("") || Double.valueOf(meblegString) <= 0) {
            MyAlert("Zehmet Olmaza MEblegi Dogru Daxil Edib Sonra Enteri basin", "Meblegi Daxil edin", "Meblegi Daxil edin");
        } else {
            mebleg = Double.valueOf(meblegString);
        }

        // mebleg 50.00
        // ne edirik sayi aliriq
        Integer say = getQtyFromField();

        if (say != null && mebleg != null) {
            birEdediQiymeti = mebleg / say;
            onePrice.setText(birEdediQiymeti.toString());
        } else if (say == null) {
            MyAlert("Mehsulun sayini Daxil edin", "Mehsulun sayini Daxil edin", "Mehsulun sayini Daxil edin");

            qtyField.requestFocus();
            return;
        }

        addButton.requestFocus();
    }

    /**
     * Mehsulun Melumatlarini tam daxil edib Elave et duymsini basdiqda bu metod
     * ishe dushur
     */
    @FXML
    private void onActionProductAddButton() {
        // bu bolumde barcod daxil edilibse mehsulu sebete elave etmeliyik
        //xanalarda yazdigimiz say ve 1-ed qiymetini veririk

        // heleki barcodlu mehsulun satihsini realize edirem
        if (barCodeEnteredProduct != null && !update) {
            Integer say = Integer.valueOf(qtyField.getText());
            Double birininQiymeti = Double.valueOf(onePrice.getText());
            cart.addCartitem(new CartItem(barCodeEnteredProduct, say, birininQiymeti));
            updateTable();
            clearFields();
            barCodeField.requestFocus();
        } else if (update) {
            Integer say = Integer.valueOf(qtyField.getText());
            Double birininQiymeti = Double.valueOf(onePrice.getText());
            cart.updCartitem(new CartItem(barCodeEnteredProduct, say, birininQiymeti));
            updateTable();
            clearFields();
            barCodeField.requestFocus();
        }

        //barcodsuz mehsulun yenilenmesi
        if (barCodeEnteredProduct == null && nameEnteredProduct != null) {
            if (cart.containsKey(nameEnteredProduct.getId())) {
                Integer say = Integer.valueOf(qtyField.getText());
                Double birininQiymeti = Double.valueOf(onePrice.getText());
                cart.updCartitem(new CartItem(nameEnteredProduct, say, birininQiymeti));
                updateTable();
                clearFields();
                barCodeField.requestFocus();
            }
            if (barCodeEnteredProduct == null && nameField.getText().length() > 0) {

                Product p = new Product();
                p.setId(--nameEnteredProductCounter);
                p.setName(nameField.getText());
                p.setQty(1000);
                p.setPurchasePrice(1.5);
                nameEnteredProduct = p;
                cart.addCartitem(new CartItem(nameEnteredProduct, Integer.valueOf(qtyField.getText()), Double.valueOf(totalPrice.getText())));

                updateTable();
                clearFields();
                barCodeField.requestFocus();

            }

        }
        barCodeEnteredProduct = null;
        nameEnteredProduct = null;
    }

    /**
     * Mehsulun Melumatlar olan bolumunu tam temizleyir
     */
    @FXML
    private void onActionClearFields() {
        clearFields();
        barCodeField.requestFocus();
    }

    //-----------------------------Additional Metods
    /**
     *
     *
     *
     *
     *
     * Xanalari Temizleyir
     */
    private void clearFields() {
        barCodeField.setText(null);
        barCodeField.setDisable(false);
        barCodeField.requestFocus();

        nameField.setEditable(true);
        nameField.setText(null);
        qtyField.setText(null);
        onePrice.setText(null); //onePrice yox // onePriceField olmali idi Duzelderem
        totalPrice.setText(null);

    }

    /**
     * sayGetter and Validator
     */
    private Integer getQtyFromField() {
        String filed = qtyField.getText();
        if (filed != null && !filed.equals("")) {
            Integer say = Integer.valueOf(qtyField.getText());
            if (say > 0) {
                return say;
            }
        }
        return null;
    }

    /**
     * salePrice Gette and Validator
     *
     * @return
     */
    private Double getOnePriceFormField() {
        String filed = onePrice.getText();
        if (filed != null && !filed.equals("")) {
            Double one = Double.valueOf(onePrice.getText());
            if (one > 0) {
                return one;
            }
        }
        return null;
    }

    /**
     * xanada yazilan mebleg Getter and Validator
     *
     */
    private Double getTotalPriceFormField() {
        String filed = totalPrice.getText();
        if (filed != null && !filed.equals("")) {
            Double total = Double.valueOf(totalPrice.getText());
            if (total > 0) {
                return total;
            }
        }
        return null;
    }

    /**
     * Mehsulllarin hamsinin cemi meblegi Getter and Validator
     *
     */
    private Double getTotalPriceFormCart() {
        String filed = cartTotalPrice.getText();
        if (filed != null && !filed.equals("")) {
            Double total = Double.valueOf(cartTotalPrice.getText());
            if (total > 0) {
                return total;
            }
        }
        return null;
    }

    /**
     * Mushteri adi Getter and Validator for null
     *
     */
    private String getCustomerNameFormField() {
        String filed = customerName.getText();
        if (filed != null && !filed.equals("")) {
            return filed;
        }
        return null;
    }

    /**
     * Bu metod error xeberdarligi cixartmaq ucun istifade olunur
     *
     * @param title
     * @param header
     * @param content
     */
    private void MyAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Cedvelimizi Cart obyektinde olan itemlerle doldurur yenileyir
     */
    private void updateTable() {
        if (cart.getArrayList() != null) {
            productList.clear();
            productList.addAll(cart.getArrayList());
            cartTable.setItems(productList);
            calcCartTotalPrice();
        }
    }

    /**
     * Parametr olaraq verilen Product-i set edir saxdaki Xanalara
     * (TextField-lere)
     *
     * @param product
     */
    private void setProductToEditFields(Product product) {

        if (product.getBarCode().isEmpty()) {
            barCodeField.setPromptText("Barcodsuz Məhsul");
            barCodeField.setDisable(true);
        }

        barCodeField.setText(product.getBarCode());
        nameField.setText(product.getName());
        nameField.setEditable(false);
        qtyField.setPromptText("Anbarda:" + product.getQty().toString());
        qtyField.requestFocus();
        onePrice.setText(product.getSalePrice().toString());
        totalPrice.setText(Double.toString(product.getPurchasePrice() * product.getQty()));
    }

    /**
     * duymenin veziyyetini deyishmek ucun
     *
     * @param b
     */
    private void updateStatusChange(boolean b) {
        update = b;
        if (b) {
            addButton.setText("Yenilə");
        } else {
            addButton.setText("Elave et");
        }

    }

    /**
     * Cart obyektinde olan mehsullarin meblegini cemleyib gosterir
     */
    private void calcCartTotalPrice() {
        if (cart.getArrayList() != null) {
            Double ctp = 0.0;
            for (CartItem ci : cart.getArrayList()) {
                ctp += ci.getTotalPrice();
            }
            cartTotalPrice.setText(ctp.toString());
        }
    }

    /**
     * Satishi Tamamlayan metod
     */
    @FXML
    private void saleAllProductInCart() {

        //satisha bashladiqda ilk once qaime nomresi almaq lazimdir
        // Mushteri adi ve mebleg ile qaime siyahisina melumatlari yaziriq ve id-sini aliriq
        // mushteri adimizi alar
        String customerName;
        if (getCustomerNameFormField() == null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DIQQET");
            alert.setHeaderText("DIQQET Mushteri ADini Daxil etmemisiniz");
            alert.setContentText("Daxil etmek ucun Cansell-duymesine, davam etmek ucun ise Ok duymesine basin");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                customerName = "Client" + new Date().getTime();
            } else {
                this.customerName.requestFocus();
                return;
            }
        } else {
            customerName = getCustomerNameFormField();
        }

        //meblegi aliriq
        Double totalPrice;
        if (getTotalPriceFormCart() == null) {
            MyAlert("Satish Meblegi 0-dir", "Satish Meblegi 0-dir", "Satish Meblegi 0-dir");
            return;
        } else {
            totalPrice = getTotalPriceFormCart();
        }

        Invoice invoice = new Invoice();
        invoice.setCustomerName(customerName);
        invoice.setTotalPrice(totalPrice);
        invoice.setDate(new Date());

        // Qaime syahisina yaziriq ve id aliriq
        invoiceDAO.create(invoice);

        // son elave etdiyimiz qaimeninn nomresi
        Integer invoiceId = invoiceDAO.getLastId();

        //Qaime nomremiz var indi satish edirik
        //mehsulumuzu dovrile bir bir satacayiq ve satish siyahimiza qeydlerimizi edeceyik
        cart.getArrayList().forEach((ci) -> {
            //barcodlu mehsulun satishi
            if (ci.getId() >= 0) {

                // saylarimiz haqqinda melumati aliriqq
                Integer evvelki_say = ci.getProduct().getQty();
                Integer satish_sayi = ci.getQty();
                Integer qaliq_say = evvelki_say - satish_sayi;

                Product product = ci.getProduct();
                product.setQty(qaliq_say);

                //mehsulumuzu satiriq yani sayini yenileyirik
                // buna ProductDAO-da satish funksyasida yiga bilerik ve bir satish sayini vere bilerik
                //qalanini ozude ede biler
                // indi ise satishi elememisheden evvel mehsulumuzun sayini azaldaq
                productDAO.update(product);

                //Inner Object Product data's IN InvoiceItem
                //Integer id = ci.getId();
                //String name = ci.getName();
                Integer qty = ci.getQty();
                Double price = ci.getSalePrice();
                //String barCode = ci.getProduct().getBarCode();
                //String note = ci.getProduct().getNote();

                //Product saleProduct = new Product(id, name, qty, price, barCode, note);
                Product saleProduct = ci.getProduct();
                saleProduct.setQty(qty);
                saleProduct.setSalePrice(price);

                InvoiceItem saleItem = new InvoiceItem(0, invoiceId, ci.getTotalPrice(), saleProduct);

                //indi satish haqqinda melumati yazaq bazaya brbir
                invoiceItemDAO.create(saleItem);

                //barcodsuz mehsulun satishi
            } else {
                // bazamizda olmadigi ucun mehsul say hesabi da etmirik 
                //sadece satish yaziriq ve birdi

                //Integer id = ci.getId();
                //String name = ci.getName();
                Integer qty = ci.getQty();
                Double price = ci.getSalePrice();
                //String barCode = ci.getProduct().getBarCode();
                //String note = ci.getProduct().getNote();

                //Product saleProduct = new Product(id, name, qty, price, barCode, note);
                Product saleProduct = ci.getProduct();
                saleProduct.setQty(qty);
                saleProduct.setSalePrice(price);
                
                InvoiceItem saleItem = new InvoiceItem(0, invoiceId, ci.getTotalPrice(), saleProduct);

                //indi satish haqqinda melumati yazaq bazaya brbir
                invoiceItemDAO.create(saleItem);
            }
        });

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Satish Tamamlandi");
        alert.setHeaderText("Qaime Cap etmek isteyirsiniz?");
        alert.setContentText("Qaime №: " + invoiceId + ", Müştəri: " + customerName);

        InvoiceTableController.selectedInvoice = invoiceDAO.get(invoiceId);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            MyJRViewer.showSATISH_QAIMESI(invoiceId);
        }
        ControllersRef.srlc.toggleButtonSaleInvoiceDetailsOnAction();

    }
}
