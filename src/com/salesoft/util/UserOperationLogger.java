/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.util;

import com.salesoft.database.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bu Class - Istifadeci emeliyyatlarini ve Neticelerini LOGA alacaq ve sonra ne
 * ish gorur ne edir Baxa bileceyik hara ne yazib neyi nece edib, Bu ilk once
 * tehlukesizlik ucun lazimdir Cunki istifadeci istemeden bele hansisa mehsulu
 * silse sonra da dese ki Proqramda nese problem cixib mehsulum silinib onu ona
 * necese gostermek lazimdir ki sen silmisen yada senin yerinde oturan kimse
 * silib bax bu tarixde bu saatda bu deqiqede
 *
 * @author Ramin
 */
public class UserOperationLogger {
    // ISTIFADECI EMELIYYATLARI + NETICELERI

    //ilk olaraq SQL-sorgulari qeydiyyata alacayiq sonra daha deqiq loglara bashlayacayiq
    // SQL loqlari bazaya vuracam - eger sql zamani Exception cixasa o zaman MyExceptionLogger onu fayla yazacaq
    public static void logSQL(String SQL) {

        // heleki username null gelecek sonra onu alib verecem bura
        String userName = "null";
        try {
            // SQL sorgumuzu alaq
            // sonra onu yazaq bazaya
            // heleki bunun ucun DAO duzeltmirem irelide lazim olsa ederem

            SQL = SQL.replaceAll("'", "");

            DBUtil.directExecuteUpdate(
                    "INSERT INTO `salesoft`.`userOperationLogger` "
                    + "(`userName`, `sql`) VALUES "
                    + "('" + userName + "', '" + SQL + "');");

            //            Connection conn = DBUtil.directConnect();
            //
            //            PreparedStatement pst = conn.prepareStatement(
            //                    "INSERT INTO `salesoft`.`userOperationLogger` "
            //                    + "(`userName`, `sql`) VALUES "
            //                    + "('?', '?');");
            //
            //            pst.setString(1, userName);
            //            pst.setString(2, SQL);
            //
            //            pst.executeUpdate();
        } catch (SQLException ex) {
            MyExceptionLogger.logExceptionV2("SQLException - UserOperationLogger.logSQL(String userName, String SQL)", "userName: " + userName, "SQL: " + SQL, ex);
        }

    }

}
