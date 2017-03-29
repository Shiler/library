package com.epam.library.dao.query;

/**
 * Created by Yauhen_Yushkevich on 29.03.17.
 */
public enum DaoName {

    BOOK {
        @Override
        public String toString() {
            return "book";
        }
    },

    EMPLOYEE {
        @Override
        public String toString() {
            return "employee";
        }
    }

}
