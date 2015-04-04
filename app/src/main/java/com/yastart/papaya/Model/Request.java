package com.yastart.papaya.Model;

/**
 * Created by makazone on 04.04.15.
 */
public class Request {
    public enum State {
        PROCESSING, ACCEPTED, DECLINED;
    }

    private User initiator;
    private User responder;

    // Exchange bookA on bookB
    private Book bookDesired;
    private Book bookInReturn;

    public Request() {}
    public Request(Book bookDesired, User initiator) {
        this.bookDesired = bookDesired;
        this.initiator   = initiator;
    }

    public void save(VoidHandler handler) {
        handler.done();
    }

    public void getRequestsForUser(User user, GetListHandler<Request> handler) {

    }
}
