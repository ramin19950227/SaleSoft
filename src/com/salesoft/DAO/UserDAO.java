 
package com.salesoft.DAO;

import com.salesoft.database.DBUtil;
import com.salesoft.database.SQL;
import com.salesoft.model.User;
import com.salesoft.util.MyLogger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ramin 
 */
public class UserDAO extends AbstractDAO<User, Integer> {

    @Override
    public boolean create(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Integer id) {
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

    public boolean login(String UsrName, String Password) {
        try {
            ResultSet rs = DBUtil.directExecuteQuery(SQL.UserSQL.LOGIN(UsrName, Password));
            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("SQLException -  UserDAO.login(): " + ex);
            MyLogger.logException("SQLException - UserDAO.login()", ex);
            return false;
        }
    }

    public boolean registration(String userName, String userPassword, String fullName, Integer status) {

        try {
            DBUtil.directExecuteUpdate(SQL.UserSQL.REGISTRATON(userName, userPassword, fullName, status));
            return true;

        } catch (SQLException ex) {
            System.out.println("SQLException -  UserDAO.registration(): " + ex);
            MyLogger.logException("SQLException - UserDAO.registration()", ex);
            return false;
        }

    }

}
