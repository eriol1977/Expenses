/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.service;

import com.fb.expenses.entity.ExpenseType;
import javax.persistence.Query;

/**
 *
 * @author f.bertolino
 */
public class ExpenseTypeDAO extends AbstractDAO<ExpenseType> {

    public ExpenseTypeDAO() {
        super(ExpenseType.class, "FIND_ALL_EXPENSE_TYPES", "DELETE_ALL_EXPENSE_TYPES");
    }

    @Override
    protected void updateFields(ExpenseType persisted, ExpenseType updated) {
        persisted.setCode(updated.getCode());
        persisted.setDescription(updated.getDescription());
    }

    public ExpenseType findByCode(String code) {
        Query query = em.createNamedQuery("FIND_EXPENSE_TYPE_BY_CODE");
        query.setParameter("code", code);
        return (ExpenseType) query.getSingleResult();
    }
}
