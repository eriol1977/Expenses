/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.config;

import com.fb.expenses.entity.ExpenseType;
import com.fb.expenses.service.ExpenseTypeDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author f.bertolino
 */
@WebListener
public class ExpensesContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
       
        // creates the expense types, if they don't already exist
        ExpenseTypeDAO dao = new ExpenseTypeDAO();
        List<ExpenseType> types = dao.findAll();
        if (types.isEmpty()) {
            List<Pair> data = new ArrayList<>();
            data.add(new Pair("ABB", "Abbigliamento"));
            data.add(new Pair("ALI", "Alimentari"));
            data.add(new Pair("DOC", "Documenti"));
            data.add(new Pair("IGB", "Igiene/Bellezza"));
            data.add(new Pair("INT", "Intrattenimento"));
            data.add(new Pair("OGG", "Oggetti"));
            data.add(new Pair("SAL", "Salute"));
            data.add(new Pair("TRA", "Trasporti"));
            types = data.stream().map(d -> new ExpenseType((String) d.x, (String) d.y)).collect(Collectors.toList());
            dao.persistAll(types);
        }

        try {
            dao.close();
        } catch (IOException ex) {
            Logger.getLogger(ExpensesContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Shutting down!");
    }

    class Pair<X, Y> {

        public final X x;
        public final Y y;

        public Pair(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }
}
