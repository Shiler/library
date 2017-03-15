package com.epam.library.domain;

import com.epam.library.dao.Identified;

/**
 * Created by Evgeny Yushkevich on 15.03.2017.
 */
public class Book implements Identified<Integer> {

    private int id;
    private String title;
    private String author;
    private String brief;
    private int dateOfPublishing;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getDateOfPublishing() {
        return dateOfPublishing;
    }

    public void setDateOfPublishing(int dateOfPublishing) {
        this.dateOfPublishing = dateOfPublishing;
    }

    protected void setId(int id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
