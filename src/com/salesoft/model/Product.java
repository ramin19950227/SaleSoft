package com.salesoft.model;

//<editor-fold defaultstate="collapsed" desc="import">
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="JavaDoc">
/**
 *
 * Product Model Class-idir yani bu klassimizla modelimizin nece olacagini
 * tesvir edirik Programimiza deyirik ki Product deye bir obyekt var ve bu - bu
 * - bu Propertileri var bu - bu metodlardan istifade olunacaq metolar ise get
 * ve set metodlaridir
 *
 * @author Ramin
 */
//</editor-fold>
public class Product {

//<editor-fold defaultstate="collapsed" desc="comment">
    //modellerin Property-lerini javaFX de nece lzimdirsa ele yigiram
    //cunki adi qayda ile int a, String b, tipli yazsam irelide
    //problemle rastlashacam Cedvelde Redkte
//</editor-fold>
    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty qty;
    private final DoubleProperty purchasePrice;
    private final StringProperty barCode;
    private final StringProperty note;
    private final DoubleProperty salePrice;

    public Product() {
        this.id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty("");
        this.qty = new SimpleIntegerProperty(0);
        this.purchasePrice = new SimpleDoubleProperty(0.0);
        this.barCode = new SimpleStringProperty("");
        this.note = new SimpleStringProperty("");
        this.salePrice = new SimpleDoubleProperty(0.0);
    }

//<editor-fold defaultstate="collapsed" desc="JavaDoc">
    /**
     * Kohne
     *
     * @param id
     * @param name
     * @param qty
     * @param purchasePrice
     * @param barCode
     * @param note
     * @deprecated
     */
//</editor-fold>
    public Product(Integer id, String name, Integer qty, Double purchasePrice, String barCode, String note) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.qty = new SimpleIntegerProperty(qty);
        this.purchasePrice = new SimpleDoubleProperty(purchasePrice);
        this.barCode = new SimpleStringProperty(barCode);
        this.note = new SimpleStringProperty(note);
        this.salePrice = new SimpleDoubleProperty(0.0);
    }

//<editor-fold defaultstate="collapsed" desc="JavaDoc">
    /**
     * Yeni Constructor
     *
     * @param id
     * @param name
     * @param qty
     * @param purchasePrice
     * @param barCode
     * @param note
     * @param salePrice
     */
//</editor-fold>
    public Product(Integer id, String name, Integer qty, Double purchasePrice, Double salePrice, String barCode, String note) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.qty = new SimpleIntegerProperty(qty);
        this.purchasePrice = new SimpleDoubleProperty(purchasePrice);
        this.salePrice = new SimpleDoubleProperty(salePrice);

        this.barCode = new SimpleStringProperty(barCode);
        this.note = new SimpleStringProperty(note);
    }

//<editor-fold defaultstate="collapsed" desc="JavaDoc">
    /**
     *
     * Metod Bu Obyektin Yeni Clonunu Qaytarir Yeni Obyekt Bu obyekt ile eyni
     * Unvana yonllendirmiller, bir soznen Heap-da yeni obyekt qurur ve unvanini
     * qaytarir
     *
     * @return
     */
//</editor-fold>
    public Product dublicateThisProduct() {
        Product p = new Product(
                this.getId(),
                this.getName(),
                this.getQty(),
                this.getPurchasePrice(),
                this.getSalePrice(),
                this.getBarCode(),
                this.getNote());

        return p;
    }

    public final void setId(Integer value) {
        id.set(value);
    }

    public final Integer getId() {
        return id.get();
    }

    public final IntegerProperty idProperty() {
        return id;
    }

    public final void setName(String value) {
        name.set(value);
    }

    public final String getName() {
        return name.get();
    }

    public final StringProperty nameProperty() {
        return name;
    }

    public final void setQty(Integer value) {
        qty.set(value);
    }

    public final Integer getQty() {
        return qty.get();
    }

    public final IntegerProperty qtyProperty() {
        return qty;
    }

    public final void setPurchasePrice(Double value) {
        purchasePrice.set(value);
    }

    public final Double getPurchasePrice() {
        return purchasePrice.get();
    }

    public final DoubleProperty purchasePriceProperty() {
        return purchasePrice;
    }

    public final void setBarCode(String value) {
        barCode.set(value);
    }

    public final String getBarCode() {
        return barCode.get();
    }

    public final StringProperty barCodeProperty() {
        return barCode;
    }

    public final void setNote(String value) {
        note.set(value);
    }

    public final String getNote() {
        return note.get();
    }

    public final StringProperty noteProperty() {
        return note;
    }

    public final void setSalePrice(Double value) {
        salePrice.set(value);
    }

    public final Double getSalePrice() {
        return salePrice.get();
    }

    public final DoubleProperty salePriceProperty() {
        return salePrice;
    }

    @Override
    public String toString() {
        return "Product{"
                + "id=" + id.get()
                + ", name=" + name.get()
                + ", qty=" + qty.get()
                + ", purchasePrice=" + purchasePrice.get()
                + ", barCode=" + barCode.get()
                + ", note=" + note.get()
                + ", salePrice=" + salePrice.get() + '}';
    }

//<editor-fold defaultstate="collapsed" desc="JavaDoc">
    /**
     * Yeni Metod Mehsulun indiki sayina verilen sayi elave edir yani Plus
     *
     * @param qty
     */
//</editor-fold>
    public void plusQty(Integer qty) {
        setQty(getQty() + qty);
    }

    public void minusQty(Integer qty) {
        setQty(getQty() - qty);
    }
}
