package com.salesoft.DAO.intf;

import com.salesoft.model.ProductImportWrapper;
import java.util.ArrayList;

/**
 * Product - Obyektimizle Melumat Bazasi arasindaki Emeliyyatlar
 *
 * @author Ramin
 */
public interface PurchaseProductDAOIntf {

    void create(ProductImportWrapper purchaseProduct);

    void update(ProductImportWrapper purchaseProduct);

    void delete(Integer id);

    ProductImportWrapper getById(Integer id);

    ArrayList<ProductImportWrapper> getAll();

}
