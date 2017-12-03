/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Sebet Obyekti, HashMap-ile yigilib
 *
 * @author Ramin
 */
public class Cart {

    /**
     * Sebet Obyektimizin daxili qurulush obyekti HashMap-ile Yigilib
     */
    Map<Integer, CartItem> map = new HashMap<>();

    /**
     * Sebeti HashMap obyekti formasinda Qaytarir
     *
     * @return
     */
    public Map<Integer, CartItem> getHashMap() {
        return map;
    }

    /**
     * Bu metod Map-da olan Yani Sebetde olan CartItemi Qaytarir key ile Bu
     * metodu optimie etmek olar
     *
     * @param id
     * @return
     */
    public CartItem getCartitem(int id) {
        return map.get(id);
    }

    /**
     * Bu metod verilen key ile bazada CartItem olub olmadigini yoxlayir Varsa
     * true qaytarir yoxdursa false
     *
     * @param key
     * @return
     */
    public boolean containsKey(int key) {
        CartItem ci = map.get(key);
        return ci != null;
    }

    /**
     * Bu metod obyektimizi ArrayList Formasinda qaytarir bu cedvelde gostermek
     * ucun istifade olunacaq
     *
     * @return
     */
    public ArrayList<CartItem> getArrayList() {
        return new ArrayList<>(map.values());
    }

    /**
     * Bu Metod sadece elave edir, CartItem sebette yoxdursa elave edir true
     * qaytarir, Yox Eger varsa o zaman false qaytarir
     *
     * @param cartItem
     * @return true - elave olundu,
     * @return false - elave olunmadi
     *
     * @returnTrue elave olundu Cart-a
     *
     * @returnFalse Elave olunmadi cunki Cartda bu id-de mehsul var
     *
     */
    public boolean addCartitem(CartItem cartItem) {
        if (map.get(cartItem.getId()) == null) {
            map.put(cartItem.getId(), cartItem);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Bu Metod Sadece Cartda key ile olan CartItem-i Evezleyir kohneni silir
     * yenini yazir. map.replace(CartItem, key)
     *
     * @param cartitem
     * @return true - yenilendi, false - yenilenmedi cunki mapda bele key ile
     * item yoxdur
     *
     */
    public boolean updCartitem(CartItem cartitem) {
        if (map.get(cartitem.getId()) != null) {
            map.replace(cartitem.getId(), cartitem);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Bu Metodmehsulu Cart-dan silir key esasinda
     *
     * @param key
     */
    public void deleteCartitem(int key) {
        System.out.println("deleteCartitem");
        System.out.println("map.remove(key);-sart");
        map.remove(key);
        System.out.println("map.remove(key);-end");
    }

}
