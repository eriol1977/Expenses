/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.service;

import com.fb.expenses.entity.Expense;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Query;


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

    public List<Expense> findExpensesByDate(final LocalDate date) {
        Query query = em.createNamedQuery("FIND_EXPENSES_BY_DATE");
        query.setParameter("date", date);
        return query.getResultList();
    }
}
