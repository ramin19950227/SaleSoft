package com.salesoft.DAO.intf;

import com.salesoft.model.User;
import java.util.ArrayList;

/**
 *
 * @author teyyub , Dec 24, 2017 , 10:09:53 AM
 */
public interface UserDAOIntf {

    void create(User entity);

    void update(User entity);

    void delete(Integer id);

    User getById(Integer id);

    ArrayList<User> getAll();

    boolean login(String UsrName, String Password);

    boolean registration(String userName, String userPassword, String fullName, Integer status);

}
