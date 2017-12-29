/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import javafx.scene.control.TextField;

/**
 *
 * @author Ramin
 */
public class MyValidator {

    /**
     * Bu Class TextField-leri Yoxlamaq Ucundur
     */
    public class textField {

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

        /**
         * Verilen TextField-in icindeki yazi Kecerli Tam eded olub olmadigini
         * yoxlayir
         *
         * @param field
         * @return
         */
        public Boolean isCorrectInt(TextField field) {
            try {
                Integer.parseInt(field.getText());
                return true;
            } catch (NumberFormatException e) {
                return false;
            }

        }

        /**
         * Verilen TextField-in hem bosh olmadigini hemde Kecerli Integer
         * oldugunu yoxlayi
         *
         * @param field
         * @return
         */
        public Boolean isNotNullANDisCorrectInt(TextField field) {
            
            return isNotNull(field) & isCorrectInt(field);
        }
    }

}
