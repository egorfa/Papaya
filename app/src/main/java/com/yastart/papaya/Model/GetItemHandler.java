package com.yastart.papaya.Model;

import java.util.ArrayList;

/**
 * Created by makazone on 04.04.15.
 */
public interface GetItemHandler<T> {
    public void done(T data);
    public void error(String responseError);
}
