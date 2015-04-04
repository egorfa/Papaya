package com.yastart.papaya.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by makazone on 04.04.15.
 */
public class Book {

    private String id;
    private String city;
    private String ownerID;

    private String description;
    private String title;
    private String authors;

    private String coverUrl;

    public enum bookCondition {
        BAD, NORMAL, EXCELLENT;
    }

    // Methods to create object from JSON


    // Read methods

    /**
     * @param city
     * @return An arraylist of books associated with a city
     */
    public static ArrayList<Book> getBooksForCity(String city) {
        ArrayList<Book> result = new ArrayList<Book>();
        return result;
    }

    /**
     *
     * @param u
     * @return
     */
    public static ArrayList<Book> getBooksForUser(User u) {
        ArrayList<Book> result = new ArrayList<Book>();
        return result;
    }

    // Creating objects from JSON

    /**
     * Decodes business json into business model object
     * @param jsonObject
     * @return Book object from json
     */
    public static Book fromJson(JSONObject jsonObject) {
        Book b = new Book();
        // Deserialize json into object fields
        try {
            b.id = jsonObject.getString("id");
            b.title = jsonObject.getString("title");
            b.ownerID = jsonObject.getString("owner");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }

    /**
     * Decodes array of business json results into business model objects
     * @param jsonArray
     * @return
     */
    public static ArrayList<Book> fromJson(JSONArray jsonArray) {
        ArrayList<Book> books = new ArrayList<Book>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject bookJson = null;
            try {
                bookJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Book business = Book.fromJson(bookJson);
            if (business != null) {
                books.add(business);
            }
        }

        return books;
    }

    // Getters & Setters

    public String getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
