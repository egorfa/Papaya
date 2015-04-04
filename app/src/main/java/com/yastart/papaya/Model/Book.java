package com.yastart.papaya.Model;

import java.util.ArrayList;

/**
 * Created by makazone on 04.04.15.
 */
public class Book {

    private int id;
    private String city;
    private User owner;

    private String description;
    private String title;
    private String authors;

    private String coverUrl;

    public enum bookCondition {
        BAD, NORMAL, EXCELLENT;
    }

    // Getters

    public static ArrayList<Book> getBooksForCity(String city) {
        ArrayList<Book> result = new ArrayList<Book>();
        return result;
    }
}
