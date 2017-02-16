package com.example.lz.tally.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lz.tally.R;
import com.example.lz.tally.customview.TestView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liz on 16-12-19.
 */

public class StatisticsFragment extends BaseFragment {
    @Bind(R.id.tv)
    TestView tv;

    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.f_statistics, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv.startAnim();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
