package com.salesoft.util;

import com.salesoft.MainApp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bu metodu Yeniledim Bu Yeni Loger metedumdur ve Heleki Tekce Exceptionlari
 * Loglayacaq, calishacam tarixide normal bir veziyyete getitim
 *
 * @author Ramin
 */
public class RLogger {

    // Bu obyekt ile Biz faylimiza setirleri yaza bileceyik ve Consolu yazmaq ucun
    // System -e bu obyekti vereceyik
    private static PrintStream out = null;

    public static void logException(String exceptionName, Exception e) {
        PrintStream out = null;
        try {
            LocalDateTime timePoint = LocalDateTime.now();

            // TODO code application logic here
            out = new PrintStream(new FileOutputStream("Log\\Exceptions\\" + (timePoint.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replaceAll(":", "-")) + " = " + exceptionName + ".txt"));
            out.println("Start Write Console");
            System.setOut(out);
            System.setErr(out);

            String localizedMessage = e.getLocalizedMessage();
            String message = e.getMessage();

            System.err.println("localizedMessage" + localizedMessage);
            System.err.println("message" + message);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(RLogger.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static PrintStream logConsoleToFile() {
        try {
            
            //her ehtimal burdada yoxlayiram loggovlugunu
            File f = new File("Log\\Exceptions\\");
            if (!f.exists()) {
                System.err.println("Creating Folder for Exception Logs ");
                f.mkdirs();
            }

            LocalDateTime timePoint = LocalDateTime.now();

            // TODO code application logic here
            out = new PrintStream(new FileOutputStream("Log/console " + (timePoint.format(DateTimeFormatter.ISO_DATE_TIME).replaceAll(":", "-")) + ".txt"));
            System.setOut(out);
            System.setErr(out);

        } catch (FileNotFoundException ex) {
            RAlert.alertAndExitByCodeAndContent(1, "Consol fayli ile Elaqedar Problem");
            return null;
        }
        return out;
    }

    /**
     * Bu metodla Biz Consola Yazilanlari Fayla yazacayiq, bu bize Programi jar
     * fay halina getirdikden sonra neler bash verir gosterecek
     */
    // bu Classi deyishmek isteyirem
    // Loglari Bazaya yazacam eyni zamanda da TXT fayla yazacam conssolda ne bash verirse
    // esas Izleme Baza seviyyesinde olacaq, amma baza ile elaqedar Exceptionlar cixdiqda onlar TXTfaylda qalacaq
    // her emeliyyat gun-ay-il saat-deqiqe-saniye )) emeliyyat eden istifacedi emeliyyat tipi vesaileri her shey yazacaq.....
    // Teyyub Muellim Duz deyir ESAS LOG-lardir, sonra niye kimese neyise subut etmeye calishasan ki, her shey ortada olduqdan sonra
    //emeliiyatlarimizin tiplerini yazaq
    //LoginForm
    // login
    //
    //  USER OPERATION TABLE
    //  ID  TimeStamp           Type            Location        Action      Result      DATA 
    //  1   15.12.2017 5:28:31  Login           LoginForm       Login       Succes      userName=Ramin, userPassword=123
    //  2   15.12.2017 5:29:50  EditProduct     ProductTable    EditName    Succes      pId=54, pOldName=Baraban 12, pNewName=Barabal 12A, -> pQty=5, pPurchasePrice=1.6, pNote=Kainat
    //Product-a aid olanlar
    // add,updateFormTable, updateFromEditPanel, deleteWithDelKey, deleteWithDeleteButton, 
}
