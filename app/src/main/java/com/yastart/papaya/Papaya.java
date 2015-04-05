package com.yastart.papaya;

import android.app.Application;
import android.graphics.Typeface;

import com.parse.Parse;

public class Papaya extends Application {

    public static final String PARSE_APPLICATION_ID = "CWpI83oY22MKeVOS2hLoIR8ZW80RO0DjTQHjtRps";
    public static final String PARSE_CLIENT_KEY = "Q9NsuN0NW1kcqjPf0xyLyDBW2HgmsXrVeVRP8cjA";

    public static Typeface font_regular;
    public static Typeface font_semibold;

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
        font_regular = Typeface.createFromAsset(getAssets(), "fonts/Mark Simonson - Proxima Nova Regular.otf");
        font_semibold = Typeface.createFromAsset(getAssets(), "fonts/Mark Simonson - Proxima Nova Semibold.otf");
    }
}
