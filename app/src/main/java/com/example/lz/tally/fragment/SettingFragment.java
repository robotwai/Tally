package com.example.lz.tally.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.example.lz.tally.R;
import com.example.lz.tally.customview.TestView2;

/**
 * Created by liz on 16-12-19.
 */

public class SettingFragment extends BaseFragment {

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();

        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.f_setting,container,false);
    }
    String path = "http://admin.app.waijiaojun.com/resource//waijiaojun/app/category/20160617081515515.mp4";
    VideoView vv;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vv = (VideoView) view.findViewById(R.id.vv);
//        vv.setVideoPath(path);
//        vv.start();

    }
}
