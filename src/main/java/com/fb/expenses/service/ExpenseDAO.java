/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.service;

import com.fb.expenses.entity.Expense;


/**
 *
 * @author f.bertolino
 */
public class ExpenseDAO extends AbstractDAO<Expense> {

    public ExpenseDAO() {
        super(Expense.class, "FIND_ALL_EXPENSES", "DELETE_ALL_EXPENSES");
    }

    @Override
    protected void updateFields(Expense persisted, Expense updated) {
        persisted.setDate(updated.getDate());
        persisted.setType(updated.getType());
        persisted.setValue(updated.getValue());
        persisted.setNotes(updated.getNotes());
    }

}
