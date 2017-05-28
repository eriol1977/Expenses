/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fb.expenses.ws;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Francesco
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String marshal(LocalDate date) throws Exception {
        return date.format(formatter);
    }

    @Override
    public LocalDate unmarshal(String string) throws Exception {
        return LocalDate.parse(string, formatter);
    }

}
