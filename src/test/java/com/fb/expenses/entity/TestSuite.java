/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.entity;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    TestExpenseType.class,
    TestExpense.class,
    TestExpenseTypeWS.class,
    TestExpensesWS.class
})

public class TestSuite {
    
}
