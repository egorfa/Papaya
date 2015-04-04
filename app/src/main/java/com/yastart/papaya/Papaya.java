package com.yastart.papaya;

import android.app.Application;

import com.parse.Parse;

public class Papaya extends Application {

    public static final String PARSE_APPLICATION_ID = "CWpI83oY22MKeVOS2hLoIR8ZW80RO0DjTQHjtRps";
    public static final String PARSE_CLIENT_KEY = "Q9NsuN0NW1kcqjPf0xyLyDBW2HgmsXrVeVRP8cjA";

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
    }
}
