/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.ws;

import com.fb.expenses.entity.ExpenseType;
import com.fb.expenses.service.ExpenseTypeDAO;
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
 *
 * @author Francesco
 */
@Path("/types")
public class ExpenseTypeWS extends AbstractWS {

    @Override
    protected ExpenseTypeDAO getDAO() {
        return new ExpenseTypeDAO();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ExpenseType> getTypes() {
        ExpenseTypeDAO typeDAO = getDAO();
        List<ExpenseType> types = typeDAO.findAll();
        closeDAO(typeDAO);
        return types;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ExpenseType getTypeById(@PathParam("id") long id) {
        ExpenseTypeDAO typeDAO = getDAO();
        final ExpenseType type = typeDAO.find(id);
        closeDAO(typeDAO);
        return type;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public ExpenseType addType(ExpenseType type) {
        ExpenseTypeDAO typeDAO = getDAO();
        typeDAO.persist(type);
        closeDAO(typeDAO);
        return type;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public ExpenseType updateType(ExpenseType type) {
        ExpenseTypeDAO typeDAO = getDAO();
        typeDAO.update(type);
        closeDAO(typeDAO);
        return type;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteType(@PathParam("id") long id) {
        ExpenseTypeDAO typeDAO = getDAO();
        typeDAO.delete(id);
        closeDAO(typeDAO);
    }
}
