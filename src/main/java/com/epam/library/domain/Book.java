package com.epam.library.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Evgeny Yushkevich on 15.03.2017.
 */
public class Book implements Identified<Integer>, Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                dateOfPublishing == book.dateOfPublishing &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(brief, book.brief);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, brief, dateOfPublishing);
    }
    
}
