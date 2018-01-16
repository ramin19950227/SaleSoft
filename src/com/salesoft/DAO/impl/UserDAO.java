package com.salesoft.DAO.impl;

import com.salesoft.DAO.intf.UserDAOIntf;
import com.salesoft.database.DBUtil;
import com.salesoft.database.SQL;
import com.salesoft.model.User;
import com.salesoft.util.MyExceptionLogger;
import com.salesoft.util.UserOperationLogger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ramin
 */
public class UserDAO implements UserDAOIntf {

    @Override
    public void create(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<User> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean login(String UsrName, String Password) {
        try {
            ////////////////////////////////////////////MYSQL/////////////////////////////////////////////
//            String SQLQuery = SQL.UserSQL.LOGIN(UsrName, Password);
//            UserOperationLogger.logSQL(SQLQuery);
//            ResultSet rs = DBUtil.mySQLExecuteQuery(SQLQuery);

            ////////////////////////////////////////////MS ACCESS/////////////////////////////////////////////
            String SQLQuery = SQL.UserSQL.LOGIN(UsrName, Password);
//            UserOperationLogger.logSQL(SQLQuery);
            ResultSet rs = DBUtil.msAccessExecuteQuery("select * from User where name='" + UsrName + "' and password='" + Password + "'");

            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - UserDAO.login()", ex);
            return false;
        }
    }

    @Override
    public boolean registration(String userName, String userPassword, String fullName, Integer status) {

        try {
            ////////////////////////////////////////////MYSQL/////////////////////////////////////////////

//            String SQLQuery = SQL.UserSQL.REGISTRATON(userName, userPassword, fullName, status);
//            UserOperationLogger.logSQL(SQLQuery);
//            DBUtil.mySQLExecuteUpdate(SQLQuery);
            ////////////////////////////////////////////MS ACCESS/////////////////////////////////////////////
            DBUtil.msAccessExecuteUpdate("INSERT INTO User VALUES (null, '" + userName + "','" + userPassword + "'," + status + " );");

            return true;

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - UserDAO.registration()", ex);
            return false;
        }

    }

}
