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
            // Sorgumuzu Hazirlayaq
            String SQLQuery = SQL.UserSQL.LOGIN(UsrName, Password);

            //Sorgumuzu Qeyde alaq
            UserOperationLogger.logSQL(SQLQuery);

            //indi ise ishimize davam edek
            ResultSet rs = DBUtil.directExecuteQuery(SQLQuery);
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
            String SQLQuery = SQL.UserSQL.REGISTRATON(userName, userPassword, fullName, status);

            //Sorgumuzu Qeyde alaq
            UserOperationLogger.logSQL(SQLQuery);

            DBUtil.directExecuteUpdate(SQLQuery);
            return true;

        } catch (SQLException ex) {
            MyExceptionLogger.logException("SQLException - UserDAO.registration()", ex);
            return false;
        }

    }

}
