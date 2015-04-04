package com.yastart.papaya.Model;

/**
 * Created by Egor on 04.04.2015.
 */
public class FeedBack {

    private String ProfileSenderId;
    private String ProfileOwnerId;
    private String Feedback;

    public FeedBack(String profileSenderId, String profileOwnerId, String feedback) {
        ProfileSenderId = profileSenderId;
        ProfileOwnerId = profileOwnerId;
        Feedback = feedback;
    }

    public FeedBack() {
    }

    public String getProfileSenderId() {
        return ProfileSenderId;
    }

    public void setProfileSenderId(String profileSenderId) {
        ProfileSenderId = profileSenderId;
    }

    public String getProfileOwnerId() {
        return ProfileOwnerId;
    }

    public void setProfileOwnerId(String profileOwnerId) {
        ProfileOwnerId = profileOwnerId;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }
}
