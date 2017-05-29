/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.ws;

import com.fb.expenses.entity.Expense;
import com.fb.expenses.service.ExpenseDAO;
import com.fb.expenses.service.ExpenseTypeDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Expense> getExpenses() {
        ExpenseDAO dao = getDAO();
        List<Expense> expenses = dao.findAll();
        closeDAO(dao);
        return expenses;
    }

    @GET
    @Path("/bydate/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Expense> getExpensesByDate(@PathParam("date") String date) {
        ExpenseDAO dao = getDAO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateToFilter = LocalDate.parse(date, formatter);
        List<Expense> expenses = dao.findExpensesByDate(dateToFilter);
        closeDAO(dao);
        return expenses;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Expense getExpense(@PathParam("id") long id) {
        ExpenseDAO dao = getDAO();
        Expense expense = dao.find(id);
        closeDAO(dao);
        return expense;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Expense addExpense(Expense type) {
        ExpenseDAO dao = getDAO();
        dao.persist(type);
        closeDAO(dao);
        return type;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Expense updateExpense(Expense type) {
        ExpenseDAO dao = getDAO();
        dao.update(type);
        closeDAO(dao);
        return type;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteExpense(@PathParam("id") long id) {
        ExpenseDAO dao = getDAO();
        dao.delete(id);
        closeDAO(dao);
    }

}
