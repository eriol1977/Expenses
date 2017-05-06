/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.entity;

import com.fb.expenses.service.ExpenseDAO;
import com.fb.expenses.service.ExpenseTypeDAO;
import com.fb.expenses.ws.ExpenseWS;
import com.google.gson.Gson;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Francesco
 */
public class TestExpensesWS {

    private final Gson gson = new Gson();

    private ExpenseDAO dao;

    private ExpenseTypeDAO typeDao;

    private ExpenseWS ws;

    private static List<ExpenseType> types;

    @BeforeClass
    public static void setUpClass() {
        types = new ArrayList<>();
        types.add(new ExpenseType("AL", "Alimentari"));
        types.add(new ExpenseType("IGB", "Igiene/Bellezza"));
    }

    /**
     * Sets up a mock WS equipped with a mock DAO, so that the WS methods can be
     * fully tested without writing/reading from a DB.
     */
    @Before
    public void setUp() {
        dao = new ExpenseDAO() {
            @Override
            public List<Expense> findAll() {
                List<Expense> expenses = new ArrayList<>();
                expenses.add(new Expense(LocalDate.now(), types.get(0), 10.99, "some useful observations"));
                expenses.add(new Expense(LocalDate.of(1977, 5, 20), types.get(1), 6.75, ""));

                return expenses;
            }

            @Override
            public Long persist(Expense obj) {
                return 123l;
            }

            @Override
            public Expense find(Long id) {
                return new Expense(LocalDate.now(), types.get(0), 10.99, "some useful observations");
            }

            @Override
            public void update(Expense obj) {
                // do nothing
            }

            @Override
            public void delete(Expense obj) {
                // do nothing
            }

        };

        typeDao = new ExpenseTypeDAO() {
            @Override
            public ExpenseType findByCode(String code) {
                return types.get(0);
            }

        };

        ws = new ExpenseWS() {
            @Override
            protected ExpenseDAO getDAO() {
                return dao;
            }

            @Override
            protected ExpenseTypeDAO getTypeDAO() {
                return typeDao;
            }
        };
    }

    @Test
    public void testGetAll() {
        String allJson = ws.getall();
        Expense[] expenses = gson.fromJson(allJson, Expense[].class);

        assertEquals(2, expenses.length);

        assertEquals(LocalDate.now(), expenses[0].getDate());
        assertEquals(types.get(0), expenses[0].getType());
        assertEquals(10.99, expenses[0].getValue(), 0);
        assertEquals("some useful observations", expenses[0].getNotes());

        assertEquals(LocalDate.of(1977, 5, 20), expenses[1].getDate());
        assertEquals(types.get(1), expenses[1].getType());
        assertEquals(6.75, expenses[1].getValue(), 0);
        assertEquals("", expenses[1].getNotes());
    }

    @Test
    public void testAdd() throws IOException {
        String json = ws.add("1988-12-01", "AL", 9.99, "bla bla bla");
        Expense expense = gson.fromJson(json, Expense.class);
        assertEquals(LocalDate.of(1988, 12, 1), expense.getDate());
        assertEquals(types.get(0), expense.getType());
        assertEquals(9.99, expense.getValue(), 0);
        assertEquals("bla bla bla", expense.getNotes());
    }
    
    @Test
    public void testUpdate() throws IOException {
        String json = ws.update(123l,"1988-12-01", "AL", 9.99, "bla bla bla");
        Expense expense = gson.fromJson(json, Expense.class);
        assertEquals(LocalDate.of(1988, 12, 1), expense.getDate());
        assertEquals(types.get(0), expense.getType());
        assertEquals(9.99, expense.getValue(), 0);
        assertEquals("bla bla bla", expense.getNotes());
    }
    
    @Test
    public void testDelete() {
        String result = ws.delete(123l);
        assertEquals("Expense 123 deleted.", result);
    }
}
