package com.yastart.papaya.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by makazone on 04.04.15.
 */
public class Request implements Parcelable {
    public Request() {

    }

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

    private State  status;

    // Exchange bookA on bookB
    private String bookDesiredID;
    private String bookInReturnID;

    /*
        Request newRequest = new Request();
        newRequest.setInitiatorID(User.getCurrentUser().getId());
        newRequest.setResponderID("117211419728589565827");
        newRequest.setBookDesiredID("5139717033033728");

        newRequest.save(new VoidHandler() {
            @Override
            public void done() {
                Log.d("SAVED", "SAAAAVED!!!!");
            }

            @Override
            public void error(String responseError) {
                Log.d("ERROR", responseError);
            }
        });
    */

    public void save(final VoidHandler handler) {
        JSONObject params = new JSONObject();
        try {
            params.put("book_id_to", bookDesiredID);
            params.put("user_id_from", initiatorID);
            params.put("user_id_to", responderID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            Server.post("request", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // If the response is JSONObject instead of expected JSONArray
                    handler.done();
                }

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

    /**
     * Returns a 2-d list of requests created by user or for given user
     * 0 contains requests from user
     * 1 contains requests to user
     * @param user
     * @param handler
     */
    public static void getRequestsForUser(final User user, final GetListHandler<ArrayList<Request>> handler) {
        RequestParams params = new RequestParams();
        params.put("user_id_from", user.getId());
        params.put("order", "-created");

        Server.get("requests", params, new JsonHttpResponseHandler() {

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
            public void onSuccess(int statusCode, Header[] headers, JSONArray requestsJSON) {
                final ArrayList<Request> requestsToMe = fromJson(requestsJSON);

                RequestParams n_params = new RequestParams();
                n_params.put("order", "-created");
                n_params.put("user_id_to", user.getId());

                Server.get("requests", n_params, new JsonHttpResponseHandler() {

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
                    public void onSuccess(int statusCode, Header[] headers, JSONArray requestsJSON) {
                        final ArrayList<Request> requestsFromMe = fromJson(requestsJSON);

                        ArrayList<ArrayList<Request>> result = new ArrayList<ArrayList<Request>>();
                        result.add(requestsFromMe);
                        result.add(requestsToMe);

                        handler.done(result);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        handler.error(responseString);
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                handler.error(responseString);
            }

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
            r.bookInReturnID = jsonObject.getString("book_id_from");

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

    public String getId() {
        return id;
    }

    public String getInitiatorID() {
        return initiatorID;
    }

    public String getResponderID() {
        return responderID;
    }

    public boolean isInitiatorApproved() {
        return initiatorApproved;
    }

    public boolean isResponderApproved() {
        return responderApproved;
    }

    public State getStatus() {
        return status;
    }

    public String getBookDesiredID() {
        return bookDesiredID;
    }

    public String getBookInReturnID() {
        return bookInReturnID;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInitiatorID(String initiatorID) {
        this.initiatorID = initiatorID;
    }

    public void setResponderID(String responderID) {
        this.responderID = responderID;
    }

    public void setInitiatorApproved(boolean initiatorApproved) {
        this.initiatorApproved = initiatorApproved;
    }

    public void setResponderApproved(boolean responderApproved) {
        this.responderApproved = responderApproved;
    }

    public void setStatus(State status) {
        this.status = status;
    }

    public void setBookDesiredID(String bookDesiredID) {
        this.bookDesiredID = bookDesiredID;
    }

    public void setBookInReturnID(String bookInReturnID) {
        this.bookInReturnID = bookInReturnID;
    }

    public int describeContents() {
        return 0;
    }

    // упаковываем объект в Parcel
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(initiatorID);
        parcel.writeString(responderID);
        parcel.writeByte((byte) (initiatorApproved ? 1 : 0));
        parcel.writeByte((byte) (initiatorApproved ? 1 : 0));
        parcel.writeInt(status.getValue());
        parcel.writeString(bookDesiredID);
        parcel.writeString(bookInReturnID);

    }

    public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() {
        // распаковываем объект из Parcel
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

    private Request(Parcel parcel) {
        id = parcel.readString();
        initiatorID = parcel.readString();
        responderID = parcel.readString();
        initiatorApproved = parcel.readByte() != 0;
        responderApproved = parcel.readByte() != 0;
        switch(parcel.readInt())
        {
            case 1:
                status = State.PROCESSING;
                break;
            case 2:
                status = State.WAITS_APPROVAL;
                break;
        }
        bookDesiredID = parcel.readString();
        bookInReturnID = parcel.readString();
    }

}
