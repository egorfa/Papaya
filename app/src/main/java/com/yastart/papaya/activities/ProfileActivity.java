package com.yastart.papaya.activities;

import com.yastart.papaya.R;
import com.yastart.papaya.fragments.BaseFragment;

/**
 * Created by Egor on 04.04.2015.
 */
public class ProfileActivity extends BaseFragment {


    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_profile;
    }

    @Override
    protected String getTitleToolBar() {
        return "Профиль";
    }



}
