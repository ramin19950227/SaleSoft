/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.DAO;

import java.util.ArrayList;

/**
 * ILK Abstract Class-im )))
 *
 * @author Ramin
 * @param <E>
 */
public abstract class AbstractDAO<E, K> {


    public abstract boolean create(E entity);

    public abstract E update(E entity);

    public abstract boolean delete(K id);

    public abstract E getById(K id);

    public abstract ArrayList<E> getAll();

}
