package com.example.lz.tally;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;

/**
 * Created by liz on 16-12-21.
 */

public class TallyApplication extends Application {
    private static TallyApplication instance;
    public static TallyApplication getInstance() {
        if (instance == null) {
            throw new IllegalStateException();
        }
        return instance;
    }

    public TallyApplication(){
        instance  = this;
    }

    @Override
    public void onCreate() {
        SpeechUtility.createUtility(instance, "appid=" + "5859e4bf");
        super.onCreate();
    }
}
