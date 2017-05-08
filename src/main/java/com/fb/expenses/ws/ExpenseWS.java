/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.ws;

import com.fb.expenses.entity.Expense;
import com.fb.expenses.entity.ExpenseType;
import com.fb.expenses.service.ExpenseDAO;
import com.fb.expenses.service.ExpenseTypeDAO;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Usage examples:
 *
 * http://localhost:8081/Expenses/rest/expenses/getall
 * http://localhost:8081/Expenses/rest/expenses/add?date=2017-05-20&type=ALI&value=25.99&notes=testing%20expenses
 * http://localhost:8081/Expenses/rest/expenses/update?id=123456&date=2016-08-20&type=IGB&value=2.15&notes=
 * http://localhost:8081/Expenses/rest/expenses/delete?id=123456
 *
 * @author f.bertolino
 */
@Path("/expenses")
public class ExpenseWS extends AbstractWS {

    @Override
    protected ExpenseDAO getDAO() {
        return new ExpenseDAO();
    }

    protected ExpenseTypeDAO getTypeDAO() {
        return new ExpenseTypeDAO();
    }

    // URI: <contextPath>/expenses/getall
    @GET
    @Path("/getall")
    @Produces(MediaType.APPLICATION_JSON)
    public String getall() {
        ExpenseDAO dao = getDAO();
        List<Expense> expenses = dao.findAll();
        closeDAO(dao);
        return gson.toJson(expenses);
    }

    // URI: <contextPath>/expenses/add
    @GET
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public String add(@QueryParam("date") String date, @QueryParam("type") String type, @QueryParam("value") double value, @QueryParam("notes") String notes) throws IOException {
        ExpenseType expType = getTypeDAO().findByCode(type);
        Expense expense = new Expense(date, expType, value, notes);
        ExpenseDAO dao = getDAO();
        dao.persist(expense);
        closeDAO(dao);
        return gson.toJson(expense);
    }

    // URI: <contextPath>/expenses/update
    @GET
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@QueryParam("id") Long id, @QueryParam("date") String date, @QueryParam("type") String type, @QueryParam("value") double value, @QueryParam("notes") String notes) throws IOException {
        ExpenseType expType = getTypeDAO().findByCode(type);
        ExpenseDAO dao = getDAO();
        Expense expense = dao.find(id);
        expense.setDate(date);
        expense.setType(expType);
        expense.setValue(value);
        expense.setNotes(notes);
        dao.update(expense);
        closeDAO(dao);
        return gson.toJson(expense);
    }

    // URI: <contextPath>/expenses/delete
    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_PLAIN)
    public String delete(@QueryParam("id") Long id) {
        ExpenseDAO dao = getDAO();
        Expense expense = dao.find(id);
        dao.delete(expense);
        closeDAO(dao);
        return "Expense " + id + " deleted.";
    }

}
