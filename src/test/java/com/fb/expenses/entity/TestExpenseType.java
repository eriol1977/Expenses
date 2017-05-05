/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.entity;

import com.fb.expenses.service.ExpenseTypeDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author f.bertolino
 */
public class TestExpenseType {

    private static ExpenseTypeDAO dao;

    @BeforeClass
    public static void setUp() {
        dao = new ExpenseTypeDAO();
    }

    @AfterClass
    public static void tearDown() throws IOException {
        dao.close();
    }

    @After
    public void deleteAll() {
        dao.deleteAll();
    }

    @Test
    public void testPersistExpenseType() {
        ExpenseType type = new ExpenseType("AL", "Alimentari");
        Long id = dao.persist(type);
        ExpenseType found = dao.find(id);
        assertEquals("Wrong id", type.getId(), found.getId());
        assertEquals("Wrong code", type.getCode(), found.getCode());
        assertEquals("Wrong description", type.getDescription(), found.getDescription());
    }
    
    @Test
    public void testFindExpenseTypeByCode() {
        ExpenseType type = new ExpenseType("AL", "Alimentari");
        dao.persist(type);
        ExpenseType type2 = new ExpenseType("TRASP", "Trasporti");
        dao.persist(type2);
        ExpenseType found = dao.findByCode("AL");
        assertEquals("Wrong id", type.getId(), found.getId());
        assertEquals("Wrong code", type.getCode(), found.getCode());
        assertEquals("Wrong description", type.getDescription(), found.getDescription());
    }
    
    @Test
    public void testUpdateExpenseType() {
        ExpenseType type = new ExpenseType("AL", "Alimentari");
        Long id = dao.persist(type);

        type.setDescription("Cibo e Bevande");
        dao.update(type);

        ExpenseType found = dao.find(id);
        assertEquals("Wrong id", type.getId(), found.getId());
        assertEquals("Wrong code", type.getCode(), found.getCode());
        assertEquals("Wrong description", "Cibo e Bevande", found.getDescription());
    }

    @Test
    public void testDeleteExpenseType() {
        ExpenseType type = new ExpenseType("AL", "Alimentari");
        Long id = dao.persist(type);
        ExpenseType found = dao.find(id);
        assertEquals("Wrong id", type.getId(), found.getId());

        dao.delete(type);
        found = dao.find(id);
        Assert.assertNull(found);
    }

    @Test
    public void testPersistAllExpenseTypes() {
        List<Pair> data = new ArrayList<>();
        data.add(new Pair("AL", "Alimentari"));
        data.add(new Pair("TRASP", "Trasporti"));
        data.add(new Pair("IG", "Igiene/Bellezza"));
        data.add(new Pair("OGG", "Oggetti"));

        List<ExpenseType> types = data.stream().map(d -> new ExpenseType((String) d.x, (String) d.y)).collect(Collectors.toList());

        List<Long> ids = dao.persistAll(types);

        List<Pair> foundData = ids.stream().map(id -> {
            ExpenseType found = dao.find(id);
            return new Pair(found.getCode(), found.getDescription());
        }).collect(Collectors.toList());

        assertEquals(data.size(), foundData.size());
        for (int i = 0; i < data.size(); i++) {
            assertEquals(data.get(i).x, foundData.get(i).x);
            assertEquals(data.get(i).y, foundData.get(i).y);
        }
    }

    @Test
    public void testFindAllExpenseTypes() {
        List<Pair> data = new ArrayList<>();
        data.add(new Pair("DOC", "Documenti"));
        data.add(new Pair("TRASP", "Trasporti"));
        data.add(new Pair("SAL", "Salute"));

        List<ExpenseType> types = data.stream().map(d -> new ExpenseType((String) d.x, (String) d.y)).collect(Collectors.toList());

        dao.persistAll(types);

        List<ExpenseType> foundTypes = dao.findAll();

        List<Pair> foundData = foundTypes.stream().map(found -> {
            return new Pair(found.getCode(), found.getDescription());
        }).collect(Collectors.toList());

        assertEquals(data.size(), foundData.size());
        for (int i = 0; i < data.size(); i++) {
            assertEquals(data.get(i).x, foundData.get(i).x);
            assertEquals(data.get(i).y, foundData.get(i).y);
        }
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
