/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO;

import com.salesoft.model.Product;
import com.salesoft.model.PurchaseProduct;
import java.util.ArrayList;

/**
 *
 * @author Ramin
 */
public class PurchaseProductDAO {

    public static ArrayList<PurchaseProduct> getAllList() {

        ArrayList<PurchaseProduct> list = new ArrayList<>();
        
        Product p = new Product();
        
        p.setBarCode("987654321");
        p.setName("pName Tests");
        p.setId(0);
        p.setNote("note");
        p.setPurchasePrice(1.4);
        p.setQty(10);
        
        list.add(new PurchaseProduct(1, p));

        return list;
    }

}
