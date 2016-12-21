package com.example.lz.tally.utils;

import android.widget.Toast;

import com.example.lz.tally.TallyApplication;

/**
 * Created by liz on 16-12-21.
 */

public class ToastUtils {
    public static void Tip(String tips){
        Toast.makeText(TallyApplication.getInstance(),tips,Toast.LENGTH_SHORT).show();
    }
}
