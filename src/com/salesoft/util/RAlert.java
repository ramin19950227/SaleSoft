/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author Ramin
 */
public class RAlert {

    public static void alertAndExitByCode(Integer kod) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Xəta");
        alert.setHeaderText("Xəta Kodu: " + kod.toString());
        alert.setContentText("Zəhmət olmasa Proqrammistə məlumat verin: Xəta kodunuda deməyi unutmayın");
        alert.initStyle(StageStyle.UNIFIED);

        alert.showAndWait();
        // bu kod eger seyf cixibsa Sistemi dayandirir ki elave seyfler cixmasin
        System.exit(kod);

    }

    public static void alertAndExitByCodeAndContent(Integer kod, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Xəta");
        alert.setHeaderText("Xəta Kodu: " + kod.toString());
        alert.setContentText(content);
        alert.initStyle(StageStyle.UNIFIED);

        alert.showAndWait();
        // bu kod eger seyf cixibsa Sistemi dayandirir ki elave seyfler cixmasin
        System.exit(kod);

    }

    public static void alertContent(Integer kod, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Xəta");
        alert.setHeaderText("Xəta Kodu: " + kod.toString());
        alert.setContentText(content);
        alert.initStyle(StageStyle.UNIFIED);

        alert.showAndWait();
    }

}
