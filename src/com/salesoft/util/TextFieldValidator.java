/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import javafx.scene.control.TextField;

/**
 * Bu Class TextField-leri Yoxlamaq Ucundur
 *
 * @author Ramin
 */
public class TextFieldValidator {

    /**
     * Paramert olaraq Verilen TextFieldin Null olub olmadigini yoxlayir
     *
     * @param field
     * @return
     */
    public Boolean isNotNull(TextField field) {
        if (field.getText() == null || field.getText().length() == 0 || field.getText().equals("")) {
            return false;
        }
        return true;
    }

    public Boolean isNull(TextField field) {
        if (field.getText() == null || field.getText().length() == 0 || field.getText().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * Verilen TextField-in icindeki yazi Kecerli Tam eded olub olmadigini
     * yoxlayir
     *
     * @param field
     * @return
     */
    public Boolean isCorrectInt(TextField field) {
        if (isNull(field)) {
            return false;
        }
        try {
            Integer.parseInt(field.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Boolean isCorrectDouble(TextField field) {
        if (isNull(field)) {
            return false;
        }
        try {
            Double.parseDouble(field.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Boolean isNotZero(TextField field) {
        if (isNull(field)) {
            return false;
        }
        if (field.getText().equals("0") || field.getText().equals("0.0")) {
            return false;
        }
        return true;
    }

    //buna ehtiyyac yoxdur is correctInt metodu hele bunu evezleyir
    // cunki eger xanada hecne yoxdur sa parseInt() exception cixaracaq ki empity 
    // yani boshdursa ve ya null dursa onsuzda isCorrectInt deyil o da false qaytacaq
//    public Boolean isCorrectIntANDisNotZERO(TextField field) {
//        if (isCorrectInt(field)) {
//            if (Integer.parseInt(field.getText()) > 0) {
//                return true;
//            }
//        }
//        return false;
//    }
//    public Boolean isCorrectDoubleANDisNotZERO(TextField field) {
//        if (isCorrectDouble(field)) {
//            if (Double.parseDouble(field.getText()) > 0) {
//                return true;
//            }
//        }
//        return false;
//    }
}
