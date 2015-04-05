package com.yastart.papaya.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Book  implements Parcelable {

    private String id;
    private String city;
    private String ownerID;

    private String description;
    private String title;
    private String authors;

    private Date createdAt;

    private String coverUrl;

    // Conditions types
    public static final int BAD = 1;
    public static final int NORMAL = 2;
    public static final int EXCELLENT = 3;

    private int condition;

    public Book() {

    }

    public void saveBook(final VoidHandler handler) {
//        JSONObject jsonParams = new JSONObject();
//        jsonParams.put("notes", "Test api support");
//        StringEntity entity = new StringEntity(jsonParams.toString());
//        client.post(context, restApiUrl, entity, "application/json",
//                responseHandler);
        JSONObject params = new JSONObject();
        try {
            params.put("title", title);
            params.put("description", description);
            params.put("photo", coverUrl);
            params.put("author", authors);
            params.put("condition", condition);
            params.put("owner", ownerID);
            params.put("city", city);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            Server.post("book", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    Log.d("A", "a");
                    handler.done();
                    super.onSuccess(statusCode, headers, response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("A", "b");
                    handler.error(errorResponse.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.d("A", "c");
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d("A", "d");
                    handler.done();
                    super.onSuccess(statusCode, headers, responseString);
                }
            });
        } catch (UnsupportedEncodingException e) {
            handler.error("FAILURE!");
        }
    }

    public void updateBook(Book book, final VoidHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", book.id);
        params.put("title", book.title);
        params.put("description", book.description);
        params.put("photo", book.coverUrl);
        params.put("author", book.authors);
        params.put("condition", book.condition);
        params.put("owner", book.ownerID);
        params.put("city", book.city);

        Server.post("update", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                handler.done();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                handler.error(responseString);
            }

        });
    }

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

        Server.get("books", params, new JsonHttpResponseHandler() {
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
                ArrayList<Book> filtered = fromJson(booksJSON);

                for (Iterator<Book> it = books.iterator(); it.hasNext();) {
                    if (it.next().ownerID.equals(User.getCurrentUser().getId())) {
                        it.remove();
                    }
                }

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
            b.condition = jsonObject.getInt("condition");
            b.city = jsonObject.getString("city");
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

            Book book = Book.fromJson(bookJson);
            if (book != null) {
                books.add(book);
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

    public int getCondition() {
        return condition;
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

    public void setCondition(int condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "Book " + this.id + " title: " + this.title;
    }

    public int describeContents() {
        return 0;
    }

    // упаковываем объект в Parcel
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(city);
        parcel.writeString(ownerID);
        parcel.writeString(description);
        parcel.writeString(title);
        parcel.writeString(authors);
        parcel.writeString(coverUrl);
        parcel.writeInt(condition);

    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        // распаковываем объект из Parcel
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    private Book(Parcel parcel) {
        id = parcel.readString();
        city = parcel.readString();
        ownerID = parcel.readString();
        description = parcel.readString();
        title = parcel.readString();
        authors = parcel.readString();
        coverUrl = parcel.readString();
        condition = parcel.readInt();
    }

}
