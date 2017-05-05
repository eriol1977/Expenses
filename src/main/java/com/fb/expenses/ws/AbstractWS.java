/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.ws;

import com.fb.expenses.config.ExpensesContextListener;
import com.fb.expenses.service.AbstractDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author f.bertolino
 */
public class AbstractWS {

    protected final Gson gson = new Gson();

    protected void closeDAO(AbstractDAO dao) {
        try {
            dao.close();
        } catch (IOException ex) {
            Logger.getLogger(ExpensesContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
