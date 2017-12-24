package com.salesoft.DAO.intf;

import com.salesoft.model.PurchaseProduct;
import java.util.ArrayList;

/**
 * Product - Obyektimizle Melumat Bazasi arasindaki Emeliyyatlar
 *
 * @author Ramin
 */
public interface PurchaseProductDAOIntf {

    void create(PurchaseProduct entity);

    void update(PurchaseProduct entity);

    void delete(Integer id);

    PurchaseProduct getById(Integer id);

    ArrayList<PurchaseProduct> getAll();

}
