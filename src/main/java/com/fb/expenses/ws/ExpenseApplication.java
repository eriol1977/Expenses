/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.ws;

import com.fb.expenses.config.CORSFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author f.bertolino
 */
@ApplicationPath("/rest")
public class ExpenseApplication extends Application {

    public ExpenseApplication() {
    }

    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.asList(CORSFilter.class, ExpenseTypeWS.class, ExpenseWS.class));
    }
}
