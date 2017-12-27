package com.salesoft.DAO.intf;

import java.util.ArrayList;

/**
 *
 * @author teyyub , Dec 24, 2017 , 10:05:07 AM
 */
public interface InvoiceDAOIntf<Type, Key1, Key2> {

    public void create(Type invoice);

    public void update(Type invoice);

    public void delete(Type invoice);

    public Type get(Key1 id);

    ArrayList<Type> getAll();

    Key1 getLastId();

    ArrayList<Type> getAllByNameLike(Key2 name);
}
