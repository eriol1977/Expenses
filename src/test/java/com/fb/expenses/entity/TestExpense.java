/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.entity;

import com.fb.expenses.service.ExpenseDAO;
import com.fb.expenses.service.ExpenseTypeDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author f.bertolino
 */
public class TestExpense {

    private static ExpenseDAO dao;
    private static ExpenseTypeDAO typeDao;

    @BeforeClass
    public static void setUp() {
        dao = new ExpenseDAO();
        typeDao = new ExpenseTypeDAO();
    }

    @AfterClass
    public static void tearDown() throws IOException {
        dao.close();
        typeDao.close();
    }

    @After
    public void deleteAll() {
        dao.deleteAll();
        typeDao.deleteAll();
    }

    @Test
    public void testPersistExpense() {
        ExpenseType type = new ExpenseType("AL", "Alimentari");
        typeDao.persist(type);

        Expense expense = new Expense(LocalDate.now(), type, 10.99, "some useful observations");
        Long id = dao.persist(expense);

        Expense found = dao.find(id);
        assertEquals("Wrong id", expense.getId(), found.getId());
        assertEquals("Wrong date", expense.getDate(), found.getDate());
        assertEquals("Wrong type", expense.getType(), found.getType());
        assertEquals("Wrong value", expense.getValue(), found.getValue(), 0);
        assertEquals("Wrong notes", expense.getNotes(), found.getNotes());

    }

    @Test
    public void testUpdateExpense() {
        ExpenseType al = new ExpenseType("AL", "Alimentari");
        typeDao.persist(al);
        ExpenseType trasp = new ExpenseType("TRASP", "Trasporti");
        typeDao.persist(trasp);

        Expense expense = new Expense(LocalDate.now(), al, 10.99, "some useful observations");
        Long id = dao.persist(expense);

        expense.setDate(LocalDate.of(1977, 5, 20));
        expense.setType(trasp);
        expense.setValue(34.00);
        expense.setNotes("");
        dao.update(expense);

        Expense found = dao.find(id);
        assertEquals("Wrong id", expense.getId(), found.getId());
        assertEquals("Wrong date", expense.getDate(), found.getDate());
        assertEquals("Wrong type", expense.getType(), found.getType());
        assertEquals("Wrong value", expense.getValue(), found.getValue(), 0);
        assertEquals("Wrong notes", expense.getNotes(), found.getNotes());
    }

    @Test
    public void testDeleteExpense() {
        ExpenseType type = new ExpenseType("AL", "Alimentari");
        Long typeId = typeDao.persist(type);

        Expense expense = new Expense(LocalDate.now(), type, 10.99, "some useful observations");
        Long id = dao.persist(expense);

        Expense found = dao.find(id);
        assertEquals("Wrong id", expense.getId(), found.getId());

        dao.delete(expense);
        found = dao.find(id);
        assertNull(found);

        ExpenseType foundType = typeDao.find(typeId);
        assertNotNull(foundType);
    }

    @Test
    public void testPersistAllExpenses() {
        List<Pair> data = new ArrayList<>();
        data.add(new Pair("AL", "Alimentari"));
        data.add(new Pair("TRASP", "Trasporti"));
        data.add(new Pair("IG", "Igiene/Bellezza"));
        data.add(new Pair("OGG", "Oggetti"));
        List<ExpenseType> types = data.stream().map(d -> new ExpenseType((String) d.x, (String) d.y)).collect(Collectors.toList());
        typeDao.persistAll(types);

        List<Expense> expenses = new ArrayList<>();
        final Expense expense1 = new Expense(LocalDate.now(), types.get(0), 10.99, "some useful observations");
        expenses.add(expense1);
        final Expense expense2 = new Expense(LocalDate.of(1977, 5, 20), types.get(1), 6.75, "");
        expenses.add(expense2);
        final Expense expense3 = new Expense(LocalDate.of(1984, 8, 20), types.get(2), 0.99, "very cheap");
        expenses.add(expense3);
        final Expense expense4 = new Expense(LocalDate.now(), types.get(3), 1679, "credit card");
        expenses.add(expense4);
        List<Long> ids = dao.persistAll(expenses);

        Expense found = dao.find(ids.get(0));
        assertEquals("Wrong id", expense1.getId(), found.getId());
        assertEquals("Wrong date", expense1.getDate(), found.getDate());
        assertEquals("Wrong type", expense1.getType(), found.getType());
        assertEquals("Wrong value", expense1.getValue(), found.getValue(), 0);
        assertEquals("Wrong notes", expense1.getNotes(), found.getNotes());

        found = dao.find(ids.get(1));
        assertEquals("Wrong id", expense2.getId(), found.getId());
        assertEquals("Wrong date", expense2.getDate(), found.getDate());
        assertEquals("Wrong type", expense2.getType(), found.getType());
        assertEquals("Wrong value", expense2.getValue(), found.getValue(), 0);
        assertEquals("Wrong notes", expense2.getNotes(), found.getNotes());

        found = dao.find(ids.get(2));
        assertEquals("Wrong id", expense3.getId(), found.getId());
        assertEquals("Wrong date", expense3.getDate(), found.getDate());
        assertEquals("Wrong type", expense3.getType(), found.getType());
        assertEquals("Wrong value", expense3.getValue(), found.getValue(), 0);
        assertEquals("Wrong notes", expense3.getNotes(), found.getNotes());

        found = dao.find(ids.get(3));
        assertEquals("Wrong id", expense4.getId(), found.getId());
        assertEquals("Wrong date", expense4.getDate(), found.getDate());
        assertEquals("Wrong type", expense4.getType(), found.getType());
        assertEquals("Wrong value", expense4.getValue(), found.getValue(), 0);
        assertEquals("Wrong notes", expense4.getNotes(), found.getNotes());
    }

    @Test
    public void testFindAllExpenses() {
        List<Pair> data = new ArrayList<>();
        data.add(new Pair("DOC", "Documenti"));
        data.add(new Pair("TRASP", "Trasporti"));
        data.add(new Pair("SAL", "Salute"));
        List<ExpenseType> types = data.stream().map(d -> new ExpenseType((String) d.x, (String) d.y)).collect(Collectors.toList());
        typeDao.persistAll(types);

        List<Expense> expenses = new ArrayList<>();
        final Expense expense1 = new Expense(LocalDate.now(), types.get(0), 10.99, "some useful observations");
        expenses.add(expense1);
        final Expense expense2 = new Expense(LocalDate.of(1977, 5, 20), types.get(1), 6.75, "");
        expenses.add(expense2);
        final Expense expense3 = new Expense(LocalDate.of(1984, 8, 20), types.get(2), 0.99, "very cheap");
        expenses.add(expense3);
        dao.persistAll(expenses);

        List<Expense> foundExpenses = dao.findAll();
        assertEquals(3, foundExpenses.size());

        Expense found = foundExpenses.get(0);
        assertEquals("Wrong id", expense1.getId(), found.getId());
        assertEquals("Wrong date", expense1.getDate(), found.getDate());
        assertEquals("Wrong type", expense1.getType(), found.getType());
        assertEquals("Wrong value", expense1.getValue(), found.getValue(), 0);
        assertEquals("Wrong notes", expense1.getNotes(), found.getNotes());

        found = foundExpenses.get(1);
        assertEquals("Wrong id", expense2.getId(), found.getId());
        assertEquals("Wrong date", expense2.getDate(), found.getDate());
        assertEquals("Wrong type", expense2.getType(), found.getType());
        assertEquals("Wrong value", expense2.getValue(), found.getValue(), 0);
        assertEquals("Wrong notes", expense2.getNotes(), found.getNotes());

        found = foundExpenses.get(2);
        assertEquals("Wrong id", expense3.getId(), found.getId());
        assertEquals("Wrong date", expense3.getDate(), found.getDate());
        assertEquals("Wrong type", expense3.getType(), found.getType());
        assertEquals("Wrong value", expense3.getValue(), found.getValue(), 0);
        assertEquals("Wrong notes", expense3.getNotes(), found.getNotes());
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
