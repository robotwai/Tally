package com.example.lz.tally.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lz.tally.Bean.BillType;
import com.example.lz.tally.R;
import com.example.lz.tally.customview.MoneySKView;
import com.example.lz.tally.customview.MoneyTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liz on 16-12-21.
 */

public class EditBillActivity extends BaseSwipeBackActivity {

    @Bind(R.id.main_moneyTextView)
    MoneyTextView mainMoneyTextView;
    @Bind(R.id.iv_type1)
    ImageView ivType1;
    @Bind(R.id.tv_type1)
    TextView tvType1;
    @Bind(R.id.ll_type1)
    LinearLayout llType1;
    @Bind(R.id.iv_type2)
    ImageView ivType2;
    @Bind(R.id.tv_type2)
    TextView tvType2;
    @Bind(R.id.ll_type2)
    LinearLayout llType2;
    @Bind(R.id.iv_type3)
    ImageView ivType3;
    @Bind(R.id.tv_type3)
    TextView tvType3;
    @Bind(R.id.ll_type3)
    LinearLayout llType3;
    @Bind(R.id.iv_type4)
    ImageView ivType4;
    @Bind(R.id.tv_type4)
    TextView tvType4;
    @Bind(R.id.ll_type4)
    LinearLayout llType4;
    @Bind(R.id.iv_type5)
    ImageView ivType5;
    @Bind(R.id.tv_type5)
    TextView tvType5;
    @Bind(R.id.ll_type5)
    LinearLayout llType5;
    @Bind(R.id.iv_type6)
    ImageView ivType6;
    @Bind(R.id.tv_type6)
    TextView tvType6;
    @Bind(R.id.ll_type6)
    LinearLayout llType6;
    @Bind(R.id.iv_type7)
    ImageView ivType7;
    @Bind(R.id.tv_type7)
    TextView tvType7;
    @Bind(R.id.ll_type7)
    LinearLayout llType7;
    @Bind(R.id.iv_type8)
    ImageView ivType8;
    @Bind(R.id.tv_type8)
    TextView tvType8;
    @Bind(R.id.ll_type8)
    LinearLayout llType8;
    @Bind(R.id.iv_type9)
    ImageView ivType9;
    @Bind(R.id.tv_typ9)
    TextView tvTyp9;
    @Bind(R.id.ll_type9)
    LinearLayout llType9;
    @Bind(R.id.ll_type10)
    LinearLayout llType10;
    @Bind(R.id.ll_top)
    LinearLayout llTop;
    @Bind(R.id.rb_ali)
    RadioButton rbAli;
    @Bind(R.id.rb_wx)
    RadioButton rbWx;
    @Bind(R.id.rb_cash)
    RadioButton rbCash;
    @Bind(R.id.rg_pay_type)
    RadioGroup rgPayType;
    @Bind(R.id.main_moneySKView)
    MoneySKView mainMoneySKView;
    private Context context;
    private MoneyTextView moneyTextView;
    private MoneySKView moneySKView;
//    private ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tintManager.setTintColor(getResources().getColor(R.color.colorMain));
        setContentView(R.layout.ac_edit_bill);
        ButterKnife.bind(this);
        init();

    }


    private void init() {
        initData();
        initNode();
        initEventListener();
    }

    private void initData() {
        context = this;
    }

    private void initNode() {
        moneyTextView = (MoneyTextView) findViewById(R.id.main_moneyTextView);
        moneySKView = (MoneySKView) findViewById(R.id.main_moneySKView);
//        pager = (ViewPager) findViewById(R.id.page);
    }

    private void initEventListener() {
        moneySKView.setMoneySKButtonListener(new MoneySKView.MoneySKButtonListener() {
            @Override
            public void clickNum(int num) {
                moneyTextView.clickNum(String.valueOf(num));
            }

            @Override
            public void clickNumAction(MoneySKView.NumActionType actionType) {
                switch (actionType) {
                    case DOT:
                        moneyTextView.clickDot();
                        startActivity(new Intent(EditBillActivity.this,MyReactActivity.class));
                        break;
                    case BACKSPACE:
                        moneyTextView.delStr();
                        break;
                    case ADD:
                        moneyTextView.clickAdd();
                        break;
                    case EQUAL:
                        moneyTextView.clickEqual();
                        break;
                }
            }
        });

        moneySKView.setMoneySKGlobalActionListener(new MoneySKView.MoneySKGlobalActionListener() {
            @Override
            public void clickGlobalAction(MoneySKView.GlobalActionType globalActionType) {
                switch (globalActionType) {
                    case OK:
                        break;
                    case CURRENCY:
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,
                R.anim.eb_out);
    }

    List<BillType> typeList;

    private void setData() {
        typeList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

        }
    }

    @OnClick({R.id.ll_type1, R.id.ll_type2, R.id.ll_type3, R.id.ll_type4, R.id.ll_type5, R.id.ll_type6, R.id.ll_type7, R.id.ll_type8, R.id.ll_type9, R.id.ll_type10})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_type1:
                break;
            case R.id.ll_type2:
                break;
            case R.id.ll_type3:
                break;
            case R.id.ll_type4:
                break;
            case R.id.ll_type5:
                break;
            case R.id.ll_type6:
                break;
            case R.id.ll_type7:
                break;
            case R.id.ll_type8:
                break;
            case R.id.ll_type9:
                break;
            case R.id.ll_type10:
                break;
        }
    }
}
