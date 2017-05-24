/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.ws;

import com.fb.expenses.config.ExpensesContextListener;
import com.fb.expenses.service.AbstractDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author f.bertolino
 */
public abstract class AbstractWS {

    protected abstract AbstractDAO getDAO();

    protected void closeDAO(AbstractDAO dao) {
        try {
            dao.close();
        } catch (IOException ex) {
            Logger.getLogger(ExpensesContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
