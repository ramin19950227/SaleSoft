/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * TEKLIFLER
 * XAnalarin validationlarini metoda cixartmaq olar
 *
 */
package com.salesoft.view;

import com.salesoft.DAO.ProductGetDAO;
import com.salesoft.MainApp;
import com.salesoft.model.Cart;
import com.salesoft.model.CartItem;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class Mehsul satishi bolumu Sebet yani
 *
 * @author Ramin
 */
public class ProductSaleCartController implements Initializable {

    private MainApp mainApp;

    /**
     * -----------------------------SAG EDIT PANEL PROPERTIES
     *
     *
     *
     * SagEditPanel Melumatlari
     */
    @FXML
    private Label cartTotalPrice;
    /**
     * SagEditPanel Melumatlari
     */
    @FXML
    private TextField barCodeField;
    /**
     * SagEditPanel Melumatlari
     */
    @FXML
    private TextField nameField;
    /**
     * SagEditPanel Melumatlari
     */
    @FXML
    private TextField qtyField;
    /**
     * SagEditPanel Melumatlari
     */
    @FXML
    private TextField onePrice;
    /**
     * SagEditPanel Melumatlari
     */
    @FXML
    private TextField totalPrice;
    /**
     * Elave Et Duymesi
     */
    @FXML
    private Button addButton;

    /**
     * eger mehsul elave edilirse barcodla bu falsedir yox eger mehsul cedvelden
     * secilirse o zaman yenileme emeliyyati bashlayir ve elave et yox yenile
     * yazilir duymede
     */
    private boolean update = false;

    /**
     * -----------------------------TABLE, COLUMN PROPERTIES
     *
     *
     *
     *
     *
     * Cedvelin Melumatlari
     */
    @FXML
    private TableView<CartItem> cartTable;
    /**
     * Sutun Melumatlari
     */
    @FXML
    private TableColumn<CartItem, String> nameColumn;
    /**
     * Sutun Melumatlari
     */
    @FXML
    private TableColumn<CartItem, Number> sayColumn;
    /**
     * Sutun Melumatlari
     */
    @FXML
    private TableColumn<CartItem, Number> meblegColumn;

    /**
     * -----------------------------OTHER PROPERTIES
     *
     *
     *
     *
     * Sebetimizin Listi ve Obyekti (Cart)
     */
    private final ObservableList<CartItem> productList = FXCollections.observableArrayList();
    private final Cart cart = new Cart();

    //barcod vuranda producttu burda saxlayir
    private Product barCodeEnteredProduct = null;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

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
                    errorAlert("Zehmet Olmasa Mehsulu Secin", "Zehmet Olmasa Mehsulu Secin", "Zehmet Olmasa Mehsulu Secin");
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
                    if (newValue != null) {
                        barCodeField.setText(newValue.getProduct().getBarCode());
                        onActionBarcodeField();
                        qtyField.setText(newValue.getQty().toString());
                        onePrice.setText(newValue.getSalePrice().toString());
                        totalPrice.setText(newValue.getTotalPrice().toString());
                        
                    } else {
                        System.out.println("cartTable Selection PRODUCT NULL AUTOCALL");
                    }
                });
    }

    /**
     * -----------------------------ACTIONS
     *
     *
     *
     *
     * Barcod daxil edib enter basdiqda bu metod ishe dushur
     */
    @FXML
    private void onActionBarcodeField() {

        String barCode = barCodeField.getText();

        if (barCode == null || barCode.equals("")) {
            errorAlert("Zehmet Olmaza Barcodu Daxil Edib Sonra Enteri basin!!!!", "Barcodu Daxil edin", "Barcodu Daxil edin");
        }
        //aldigimiz mehsulu vururuq yaddasha
        this.barCodeEnteredProduct = ProductGetDAO.getProductByBarCode(barCode);

        //Mehsulu aldiqdan sonra yoxlayiriq eger sebette varsa ne edek?
        // sayini 1 ed artiraqmi
        // yoxsa gosterek sag panelde ve update edekmi?
        if (barCodeEnteredProduct != null && !cart.containsKey(barCodeEnteredProduct.getId())) {

            clearFields();
            setProductToEditFields(barCodeEnteredProduct);
            updateStatusChange(false);

        } else if (barCodeEnteredProduct != null && cart.containsKey(barCodeEnteredProduct.getId())) {

            clearFields();
            setProductToEditFields(barCodeEnteredProduct);
            updateStatusChange(true);

        } else {
            errorAlert("Bu BarCod-la Mehsul Tapilmadi", "Mehsul Tapilmadi", "Mehsul Tapilmadi");
            clearFields();
        }

    }

    /**
     * Barcodsuz Mehsulun adini daxil edib enter basdiqda bu metod ishe dushur
     */
    @FXML
    private void onActionNameField() {
        System.out.println("onActionNameField");
    }

    /**
     * Mehsulun Sayini daxil edib enter basdiqda bu metod ishe dushur
     */
    @FXML
    private void onActionQtyField() {
        Integer say, maxSay;
        System.out.println("onActionQtyField");

        //xanada yaziln sayi aliriq
        String sayString = qtyField.getText();

        System.out.println("sayString=:" + sayString);
        if (sayString == null || sayString.equals("") || Integer.valueOf(sayString) <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Zehmet Olmaza Sayi Daxil Edib Sonra Enteri basin");
            alert.setHeaderText("Sayi Daxil edin");
            alert.setContentText("Sayi Daxil Edin");
            alert.showAndWait();
            return;
        } else {
            say = Integer.valueOf(sayString);
        }
        if (barCodeEnteredProduct != null && barCodeEnteredProduct.getQty() >= say) {
            //mehsulun maks sayini barcod vurulanda alinan mehsuldan oyrenirik
            maxSay = barCodeEnteredProduct.getQty();

            //mehsulun 1- ededinin qiymetini  alriq TextField-den
            Double onePrice = Double.valueOf(this.onePrice.getText());

            //Meblegi hesablayiriq
            Double mebleg = say * onePrice;
            totalPrice.setText(mebleg.toString());
            this.onePrice.requestFocus();

        } else if (barCodeEnteredProduct != null && barCodeEnteredProduct.getQty() < say) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Zehmet Olmaza Sayi Dogru Daxil Edin");
            alert.setHeaderText("Bazada Bu sayda mal yoxdur");
            alert.setContentText("Siz Daxil etdiyiniz say: " + say + "\n Bazada Olan say: " + barCodeEnteredProduct.getQty());
            alert.showAndWait();
            qtyField.setText(null);
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
            errorAlert("Zehmet Olmaza MEblegi Dogru Daxil Edib Sonra Enteri basin", "Meblegi Daxil edin", "Meblegi Daxil edin");
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
            errorAlert("Mehsulun sayini Daxil edin", "Mehsulun sayini Daxil edin", "Mehsulun sayini Daxil edin");

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
            nameField.setEditable(true);
        } else if (update) {
            Integer say = Integer.valueOf(qtyField.getText());
            Double birininQiymeti = Double.valueOf(onePrice.getText());
            cart.updCartitem(new CartItem(barCodeEnteredProduct, say, birininQiymeti));
            updateTable();
            clearFields();
            barCodeField.requestFocus();
            nameField.setEditable(true);
        }

    }

    /**
     * Mehsulun Melumatlar olan bolumunu tam temizleyir
     */
    @FXML
    private void onActionClearFields() {
        clearFields();
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
        nameField.setEditable(false);
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
     * onePrice Gette and Validator
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
     * mebleg Getter and Validator
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

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        barCodeField.requestFocus();
    }

    /**
     * Parametr olaraq verilen Product-i set edir saxdaki Xanalara
     * (TextField-lere)
     *
     * @param product
     */
    private void setProductToEditFields(Product product) {
        barCodeField.setText(product.getBarCode());
        nameField.setText(product.getName());
        nameField.setEditable(false);
        qtyField.setText(product.getQty().toString());
        qtyField.requestFocus();
        onePrice.setText(product.getPurchasePrice().toString());
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
            addButton.setText("Update");
        } else {
            addButton.setText("Elave et");
        }

    }

    private void calcCartTotalPrice() {
        if (cart.getArrayList() != null) {
            Double ctp = 0.0;
            for (CartItem ci : cart.getArrayList()) {
                ctp += ci.getTotalPrice();
            }
            cartTotalPrice.setText(ctp.toString());
        }

    }
}
