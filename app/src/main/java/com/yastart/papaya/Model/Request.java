package com.yastart.papaya.Model;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by makazone on 04.04.15.
 */
public class Request {
    public enum State {
        PROCESSING(1), WAITS_APPROVAL(2);

        private int value;

        private State(int value) {
            this.value = value;
        }

        public int getValue() { return value; }
    }

    private String id;

    private String initiatorID;
    private String responderID;

    private boolean initiatorApproved;
    private boolean responderApproved;

    private State status;

    // Exchange bookA on bookB
    private String bookDesiredID;
    private String bookInReturnID;

    public Request() {}
//    public Request(Book bookDesired, User initiator) {
//        this.bookDesired = bookDesired;
//        this.initiator   = initiator;
//    }

    public void save(VoidHandler handler) {
    }

    /**
     * Returns a 2-d list of requests created by user or for given user
     * 0 contains requests from user
     * 1 contains requests to user
     * @param user
     * @param handler
     */
    public static void getRequestsForUser(final User user, final GetListHandler<ArrayList<Request>> handler) {
        RequestParams params = new RequestParams();
        params.put("order", "-created");
        params.put("user_id_from", user.getId());

        Server.get("requests", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("A", "a");
                handler.done(null);
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
                handler.done(null);
                super.onSuccess(statusCode, headers, responseString);
            }
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // If the response is JSONObject instead of expected JSONArray
//                try {
//                    onSuccess(statusCode, headers, response.getJSONArray("items"));
//                } catch (JSONException e) {
//                    onFailure(statusCode, headers, e.getMessage(), e);
//                }
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray requestsJSON) {
//                final ArrayList<Request> requestsToMe = fromJson(requestsJSON);
//
//                RequestParams n_params = new RequestParams();
//                n_params.put("user_id_from", user.getId());
//                Server.get("requests", n_params, new JsonHttpResponseHandler(){
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        // If the response is JSONObject instead of expected JSONArray
//                        try {
//                            onSuccess(statusCode, headers, response.getJSONArray("items"));
//                        } catch (JSONException e) {
//                            onFailure(statusCode, headers, e.getMessage(), e);
//                        }
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONArray requests1JSON) {
//                        ArrayList<Request> requestsFromMe = fromJson(requests1JSON);
//
//                        ArrayList<ArrayList<Request>> result = new ArrayList<ArrayList<Request>>();
//                        result.add(requestsFromMe);
//                        result.add(requestsToMe);
//
//                        handler.done(result);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        handler.error(responseString);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                handler.error(responseString);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
        });
    }

    // Creating objects from JSON

    /**
     * Decodes Request json into Request model object
     * @param jsonObject
     * @return Book object from json
     */
    public static Request fromJson(JSONObject jsonObject) {
        Request r = new Request();

        // Deserialize json into object fields
        try {

            int status = jsonObject.getInt("status");
            if (status == 1) {
                r.status = State.PROCESSING;
            } else r.status = State.WAITS_APPROVAL;

            r.initiatorApproved = jsonObject.getBoolean("user_from_flag");
            r.responderApproved = jsonObject.getBoolean("user_to_flag");

            r.bookDesiredID  = jsonObject.getString("book_id_to");
            r.bookInReturnID = jsonObject.getString("book_if_from");

            r.id = jsonObject.getString("id");

            r.initiatorID = jsonObject.getString("user_id_from"); // which id should be? from or to?

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        // Return new object
        return r;
    }

    /**
     * Decodes array of business json results into business model objects
     * @param jsonArray
     * @return
     */
    public static ArrayList<Request> fromJson(JSONArray jsonArray) {
        ArrayList<Request> requests = new ArrayList<Request>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject requestJson = null;
            try {
                requestJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Request request = Request.fromJson(requestJson);
            if (request != null) {
                requests.add(request);
            }
        }

        return requests;
    }
}
