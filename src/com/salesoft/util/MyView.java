/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Ramin
 */
public class MyView {

    public void showOk(ImageView view) {
        view.setImage(new Image("com/salesoft/image/ok.png"));
    }

    public void showNo(ImageView view) {
        view.setImage(new Image("com/salesoft/image/no2.png"));
    }

    public void showWarning(ImageView view) {
        view.setImage(new Image("com/salesoft/image/w.png"));

    }

}
