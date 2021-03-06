package com.epam.library.dao;

import com.epam.library.dao.exception.PersistException;
import com.epam.library.domain.Identified;

import java.lang.reflect.Field;

/**
 * Responds for implementing relation many-to-many.
 *
 * @param <Owner>      an object class whose field references to dependence object.
 * @param <Dependence> class of the dependent object.
 */
public class ManyToOne<Owner extends Identified, Dependence extends Identified> {

    private DaoFactory factory;

    private Field field;

    private String name;

    private int hash;

    public Dependence getDependence(Owner owner) throws IllegalAccessException {
        return (Dependence) field.get(owner);
    }

    public void setDependence(Owner owner, Dependence dependence) throws IllegalAccessException {
        field.set(owner, dependence);
    }

    public Identified persistDependence(Owner owner)
            throws IllegalAccessException, PersistException {
        return factory.getDao(field.getType()).persist(getDependence(owner));
    }

    public void updateDependence(Owner owner)
            throws IllegalAccessException, PersistException {
        factory.getDao(field.getType()).update(getDependence(owner));
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    public ManyToOne(Class<Owner> ownerClass, DaoFactory factory, String field)
            throws NoSuchFieldException {
        this.factory = factory;
        this.field = ownerClass.getDeclaredField(field);
        this.field.setAccessible(true);

        name = ownerClass.getSimpleName() + " to " + this.field.getType().getSimpleName();
        hash = ownerClass.hashCode() & field.hashCode();
    }
}