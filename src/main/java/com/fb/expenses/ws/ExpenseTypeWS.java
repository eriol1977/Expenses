/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.ws;

import com.fb.expenses.entity.ExpenseType;
import com.fb.expenses.service.ExpenseTypeDAO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Usage examples:
 * 
 * http://localhost:8081/Expenses/types/getall
 * http://localhost:8081/Expenses/types/add?code=TST&description=Teeeeest
 * http://localhost:8081/Expenses/types/update?code=TST&newCode=NEW&description=Ciaaoooo
 * http://localhost:8081/Expenses/types/delete?code=NEW
 * 
 * @author f.bertolino
 */
@Path("/types")
public class ExpenseTypeWS extends AbstractWS {

    // URI: <contextPath>/types/getall
    @GET
    @Path("/getall")
    @Produces(MediaType.APPLICATION_JSON)
    public String getall() {
        ExpenseTypeDAO typeDAO = new ExpenseTypeDAO();
        List<ExpenseType> types = typeDAO.findAll();
        closeDAO(typeDAO);
        return gson.toJson(types);
    }

    // URI: <contextPath>/types/add
    @GET
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public String add(@QueryParam("code") String code, @QueryParam("description") String description) {
        ExpenseType type = new ExpenseType(code, description);
        ExpenseTypeDAO typeDAO = new ExpenseTypeDAO();
        typeDAO.persist(type);
        closeDAO(typeDAO);
        return gson.toJson(type);
    }

    // URI: <contextPath>/types/update
    @GET
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@QueryParam("code") String code, @QueryParam("newCode") String newCode, @QueryParam("description") String description) {
        ExpenseTypeDAO typeDAO = new ExpenseTypeDAO();
        ExpenseType type = typeDAO.findByCode(code);
        type.setCode(newCode);
        type.setDescription(description);
        typeDAO.update(type);
        closeDAO(typeDAO);
        return gson.toJson(type);
    }

    // URI: <contextPath>/types/delete
    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_PLAIN)
    public String delete(@QueryParam("code") String code) {
        ExpenseTypeDAO typeDAO = new ExpenseTypeDAO();
        ExpenseType type = typeDAO.findByCode(code);
        typeDAO.delete(type);
        closeDAO(typeDAO);
        return "Expense Type " + type.getCode() + " deleted.";
    }

}
