package com.example.lz.tally.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lz.tally.Bean.DataEntity;
import com.example.lz.tally.R;
import com.example.lz.tally.customview.FabTagLayout;
import com.example.lz.tally.customview.FloatingActionButtonPlus;
import com.example.lz.tally.customview.LineChartView;
import com.example.lz.tally.utils.DateUtils;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by liz on 16-12-19.
 */

public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.f_main,container,false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LineChartView curersView = (LineChartView) view.findViewById(R.id.curersView);

        List<DataEntity> entityList = new ArrayList<>();
        long currentTime = DateUtils.currentTime();
        for (int i = 0; i < 7; i++) {
            DataEntity dataEntity = new DataEntity();
            long millis;
            millis = DateUtils.afterTime(currentTime,i);
            dataEntity.setTime(millis);
            dataEntity.setFloat((float) (Math.random() * 200f));
            entityList.add(dataEntity);
        }
        curersView.setEntityList(entityList);

    }


}
