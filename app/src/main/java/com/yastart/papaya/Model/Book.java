package com.yastart.papaya.Model;

import org.apache.http.Header;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

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

    private Date createdAt;

    private String coverUrl;

    public enum bookCondition {
        BAD, NORMAL, EXCELLENT;
    }



    // Methods to create object from JSON


    // Read methods

    public static void getBookByID(String id, final GetItemHandler<Book> handler) {
        Server.get("book/"+id, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Book b = Book.fromJson(response);
                handler.done(b);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                handler.error(responseString);
            }
        });
    }

    /**
     * @param city
     * @return An arraylist of books associated with a city
     */
    public static void getBooksForCity(String city, final GetListHandler<Book> handler) {
        RequestParams params = new RequestParams();
        params.put("city", city);
        params.put("order", "-created");

        Server.get("books", params,  new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    onSuccess(statusCode, headers, response.getJSONArray("items"));
                } catch (JSONException e) {
                    onFailure(statusCode, headers, e.getMessage(), e);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray booksJSON) {
                ArrayList<Book> books = fromJson(booksJSON);
                handler.done(books);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                handler.error(responseString);
            }

        });
    }

    /**
     *
     * @param user
     * @return
     */
    public static void getBooksForUser(User user, final GetListHandler<Book> handler) {
        RequestParams params = new RequestParams();
        params.put("owner", user.getId());
        params.put("order", "-created");

        Server.get("books", params,  new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    onSuccess(statusCode, headers, response.getJSONArray("items"));
                } catch (JSONException e) {
                    onFailure(statusCode, headers, e.getMessage(), e);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray booksJSON) {
                ArrayList<Book> books = fromJson(booksJSON);
                handler.done(books);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                handler.error(responseString);
            }

        });
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
            b.coverUrl = jsonObject.getString("photo");
            b.description = jsonObject.getString("description");
            b.authors = jsonObject.getString("author");
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

    @Override
    public String toString() {
        return "Book " + this.id + " title: " + this.title;
    }
}
