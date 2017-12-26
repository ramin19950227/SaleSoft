package com.salesoft.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Bu metodu Yeniledim Bu Yeni Loger metedumdur ve Heleki Tekce Exceptionlari
 * Loglayacaq, calishacam tarixide normal bir veziyyete getitim
 *
 * @author Ramin
 */
public class MyExceptionLogger {

    /*NUMUNE:
            System.out.println("SQLException -  DBUtil.dbExecuteQuery(): " + ex);
            MyExceptionLogger.logException("SQLException - DBUtil.dbExecuteQuery()", ex);
     */
    // Bu obyekt ile Biz faylimiza setirleri yaza bileceyik ve Consolu yazmaq ucun
    // System -e bu obyekti vereceyik
    private static PrintStream out = null;

    public static void logException(String exceptionName, Exception e) {
        PrintStream out = null;
        try {
            LocalDateTime timePoint = LocalDateTime.now();

            // TODO code application logic here
            out = new PrintStream(new FileOutputStream("Log\\Exceptions\\" + (timePoint.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replaceAll(":", "-")) + " = " + exceptionName + ".txt"));

            out.println("START Exception FILE");
            out.println();

            out.println("LocalizedMessage: " + e.getLocalizedMessage());
            out.println("out.println(e): " + e);

            out.println("-----------------------------------");
            out.println("e.printStackTrace(out); -->>");
            e.printStackTrace(out);

            out.println();
            out.println("END Exception FILE");

        } catch (FileNotFoundException ex) {
            MyAlert.alertAndExitByCodeAndContent(44, "FileNotFoundException - MyLogger.logException() ===" + ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * logException - Metodunun Yeni Versiyasi Loglara Obyektin neticesini ve
     * elave melumat yazir
     *
     * @param exceptionName
     * @param object
     * @param additionalData
     * @param e
     */
    public static void logExceptionV2(String exceptionName, String additionalData, Object object, Exception e) {
        PrintStream out = null;
        try {
            LocalDateTime timePoint = LocalDateTime.now();

            // TODO code application logic here
            out = new PrintStream(
                    new FileOutputStream(
                            "Log\\Exceptions\\" + "V2LOG - "
                            + (timePoint.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replaceAll(":", "-"))
                            + " = " + exceptionName + ".txt")
            );

            out.println("START Exception FILE");
            out.println();

            out.println("LocalizedMessage: " + e.getLocalizedMessage());
            out.println("out.println(e): " + e);
            out.println();

            out.println("Object Datalari: -_>> ");
            out.println();
            out.println(object);
            out.println();

            out.println("String additionalData: -->>");
            out.println(additionalData);
            out.println();

            out.println("-----------------------------------");
            out.println("e.printStackTrace(out); -->>");
            e.printStackTrace(out);
            out.println();

            out.println("END Exception FILE");

        } catch (FileNotFoundException ex) {
            MyAlert.alertAndExitByCodeAndContent(44, "FileNotFoundException - MyLogger.logExceptionV2() ===" + ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * Bu metod Consolu Fayla Cap edir. Bu Bize Proqrami ishe saldiqdan sonra
     * neler bash verdiyi haqda melumat verecek. Bunu JAR fayli ishe salmazdan
     * evvel hazirlamaq lazimdir MainApp-da. Amma IDE-de ishlediyimiz zaman buna
     * ehtiyyac yoxdur onsuzda Consolu goruruk
     *
     * @return
     */
    public static PrintStream logConsoleToFile() {
        try {
            LocalDateTime timePoint = LocalDateTime.now();

            out = new PrintStream(new FileOutputStream("Log/console " + (timePoint.format(DateTimeFormatter.ISO_DATE_TIME).replaceAll(":", "-")) + ".txt"));
            System.setOut(out);
            System.setErr(out);

        } catch (FileNotFoundException ex) {
            MyAlert.alertAndExitByCodeAndContent(1, "Consol fayli ile Elaqedar Problem");
            return null;
        }
        return out;
    }

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
