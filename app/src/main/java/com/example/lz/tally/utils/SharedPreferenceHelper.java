package com.example.lz.tally.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.lz.tally.TallyApplication;

/**
 * Created by liz on 16-8-8.
 */
public class SharedPreferenceHelper implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final SharedPreferenceHelper instance;

    static {
        instance = new SharedPreferenceHelper();
    }

    public static SharedPreferenceHelper getInstance() {
        return instance;
    }

    private SharedPreferenceHelper(){
        getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    private static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(TallyApplication.getInstance());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    private static String getString(String key, String def) {
        return getSharedPreferences().getString(key, def);
    }

    private static void setString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();

    }


}
