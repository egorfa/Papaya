package com.yastart.papaya.Model;

import java.util.ArrayList;

public interface GetListHandler<T> {
    public void done(ArrayList<T> data);
    public void error(String responseError);
}
