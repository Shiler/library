package com.epam.library.domain;

import com.epam.library.dao.Identified;

import java.sql.Date;

/**
 * Created by Evgeny Yushkevich on 15.03.2017.
 */
public class Employee implements Identified<Integer> {

    private int id;
    private String name;
    private String email;
    private Date dateOfBirth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    protected void setId(int id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
