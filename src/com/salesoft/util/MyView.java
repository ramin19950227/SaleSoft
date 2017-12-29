/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Ramin
 */
public class MyView {

    public void showOk(ImageView view) {
        File file;
        Image image;
        List<Object> list = new ArrayList<>();
        try {
            file = new File("src/com/salesoft/image/ok.png");
            list.add(file);
            image = new Image(new FileInputStream(file));
            list.add(image);
            view.setImage(image);
            list.add(view);

        } catch (FileNotFoundException ex) {
            System.err.println("FileNotFoundException - com.salesoft.util.MyView.showOkOnImageView()");
            System.err.println(ex);
            MyExceptionLogger.logExceptionV2("FileNotFoundException", "File file = new File(\"src/com/salesoft/image/ok.png\");", list, ex);
        }

    }

    public void showNo(ImageView view) {
        File file;
        Image image;

        try {
            file = new File("src/com/salesoft/image/no2.png");
            image = new Image(new FileInputStream(file));
            view.setImage(image);

        } catch (FileNotFoundException ex) {
            System.err.println("FileNotFoundException - com.salesoft.util.MyView.showNoOnImageView()");
            System.err.println(ex);
            MyExceptionLogger.logExceptionV2("FileNotFoundException", "File file = new File(\"src/com/salesoft/image/ok.png\");", "", ex);
        }

    }

    public void showWarning(ImageView view) {
        File file;
        Image image;

        try {
            file = new File("src/com/salesoft/image/w.png");
            image = new Image(new FileInputStream(file));
            view.setImage(image);

        } catch (FileNotFoundException ex) {
            System.err.println("FileNotFoundException - com.salesoft.util.MyView.showWarningOnImageView()");
            System.err.println(ex);
            MyExceptionLogger.logExceptionV2("FileNotFoundException", "File file = new File(\"src/com/salesoft/image/ok.png\");", "", ex);
        }

    }

}
