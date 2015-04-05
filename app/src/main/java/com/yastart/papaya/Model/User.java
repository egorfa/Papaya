package com.yastart.papaya.Model;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private static User currentUser;

    private String id; // Test user id 102363055574899025750
    private String username;
    private String email;
    private String contacts;
    private String city;

    public static void findUserByID(String id, final GetItemHandler<User> handler) {
        RequestParams params = new RequestParams();

        Server.get("user/"+id, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                User u = User.fromJson(response);
                handler.done(u);
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
    public static User fromJson(JSONObject jsonObject) {
        User u = new User();
        // Deserialize json into object fields
        try {
            u.id = jsonObject.getString("id");
            u.username = jsonObject.getString("username");
            u.email = jsonObject.getString("mail");
//            u.contacts = jsonObject.getString("");
            u.city = jsonObject.getString("city");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return u;
    }

//    /**
//     * Decodes array of business json results into business model objects
//     * @param jsonArray
//     * @return
//     */
//    public static ArrayList<User> fromJson(JSONArray jsonArray) {
//        ArrayList<Book> books = new ArrayList<Book>(jsonArray.length());
//        // Process each result in json array, decode and convert to business object
//        for (int i=0; i < jsonArray.length(); i++) {
//            JSONObject bookJson = null;
//            try {
//                bookJson = jsonArray.getJSONObject(i);
//            } catch (Exception e) {
//                e.printStackTrace();
//                continue;
//            }
//
//            Book book = Book.fromJson(bookJson);
//            if (book != null) {
//                books.add(book);
//            }
//        }
//
//        return books;
//    }


    // Getters & Setters

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Should return current logged user
     * @return
     */
    public static User getCurrentUser() {
//        if (currentUser != null) { return currentUser; }
//
//        currentUser = new User();
//        currentUser.setEmail("skvoter46@gmail.com");
//        currentUser.setUsername("skvoter46");
//        currentUser.setId("5664248772427776");
//        currentUser.setCity("Moscow");

        currentUser = new User();
        currentUser.setEmail("makazone@gmail.com");
        currentUser.setUsername("Makazone");
        currentUser.setId("1636319331666438");
        currentUser.setCity("Moscow");

        return currentUser;
    }

    @Override
    public String toString() {
        return "username = " + username + " " + " id = " + id;
    }
}
