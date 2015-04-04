package com.yastart.papaya.fragments;

import android.app.Fragment;
import android.content.Context;

public abstract class BaseFragment extends Fragment {

    protected Context mContext;

    protected abstract int getLayoutResourceIdentifier();

    protected abstract String getTitleToolBar();

}
