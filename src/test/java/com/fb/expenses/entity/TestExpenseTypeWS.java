/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.entity;

import com.fb.expenses.service.ExpenseTypeDAO;
import com.fb.expenses.ws.ExpenseTypeWS;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author f.bertolino
 */
public class TestExpenseTypeWS {

    private final Gson gson = new Gson();

    private ExpenseTypeDAO dao;

    private ExpenseTypeWS ws;

    /**
     * Sets up a mock WS equipped with a mock DAO, so that the WS methods can be
     * fully tested without writing/reading from a DB.
     */
    @Before
    public void setUp() {
        dao = new ExpenseTypeDAO() {
            @Override
            public List<ExpenseType> findAll() {
                List<ExpenseType> types = new ArrayList<>();
                types.add(new ExpenseType("AL", "Alimentari"));
                types.add(new ExpenseType("IGB", "Igiene/Bellezza"));
                types.add(new ExpenseType("TRASP", "Trasporti"));
                return types; //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Long persist(ExpenseType obj) {
                return 123l;
            }

            @Override
            public ExpenseType findByCode(String code) {
                return new ExpenseType(code, "Something");
            }

            @Override
            public void update(ExpenseType obj) {
                // do nothing
            }

            @Override
            public void delete(ExpenseType obj) {
                // do nothing
            }

        };

        ws = new ExpenseTypeWS() {
            @Override
            protected ExpenseTypeDAO getDAO() {
                return dao;
            }
        };
    }

    @Test
    public void testGetAll() {
        String allTypesJson = ws.getall();
        ExpenseType[] types = gson.fromJson(allTypesJson, ExpenseType[].class);
        assertEquals(3, types.length);
        assertEquals("AL", types[0].getCode());
        assertEquals("Alimentari", types[0].getDescription());
        assertEquals("IGB", types[1].getCode());
        assertEquals("Igiene/Bellezza", types[1].getDescription());
        assertEquals("TRASP", types[2].getCode());
        assertEquals("Trasporti", types[2].getDescription());
    }

    @Test
    public void testAdd() {
        String typeJson = ws.add("XXX", "Test");
        ExpenseType type = gson.fromJson(typeJson, ExpenseType.class);
        assertEquals("XXX", type.getCode());
        assertEquals("Test", type.getDescription());
    }

    @Test
    public void testUpdate() {
        String typeJson = ws.update("DOC", "XXX", "Test");
        ExpenseType type = gson.fromJson(typeJson, ExpenseType.class);
        assertEquals("XXX", type.getCode());
        assertEquals("Test", type.getDescription());
    }

    @Test
    public void testDelete() {
        String result = ws.delete("DOC");
        assertEquals("Expense Type DOC deleted.", result);
    }
}
