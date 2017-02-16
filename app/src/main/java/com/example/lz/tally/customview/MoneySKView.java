package com.example.lz.tally.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;

import com.example.lz.tally.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liz on 16-12-21.
 */

public class MoneySKView extends FrameLayout {

    private static final String LOG_TAG = "MoneySKView";

    private Button num1TV;
    private Button num2TV;
    private Button num3TV;
    private Button num4TV;
    private Button num5TV;
    private Button num6TV;
    private Button num7TV;
    private Button num8TV;
    private Button num9TV;
    private Button num0TV;
    private Button dotTV;
    private TextView currencyTV;
    private TextView currencyNameTV;
    private LinearLayout currencyWrapper;
    private Button addLayout;
    private Button equalLayout;
    private Button backspaceTv;
    private Button okTV;
    private String moneyStr;

    private View.OnClickListener softkeyboardClickListener;
    private MoneySKButtonListener moneySKButtonListener;
    private MoneySKGlobalActionListener moneySKGlobalActionListener;
    private Map<GlobalActionType, View> globalActionsMap = new HashMap<>();

    public MoneySKView(Context context) {
        super(context);
        init();
    }

    public MoneySKView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.include_money_sk, this, true);
        initNode();
        initEventListener();
    }

    private void initNode() {
        num1TV = (Button) findViewById(R.id.billSK_one);
        num2TV = (Button) findViewById(R.id.billSK_two);
        num3TV = (Button) findViewById(R.id.billSK_three);
        num4TV = (Button) findViewById(R.id.billSK_four);
        num5TV = (Button) findViewById(R.id.billSK_five);
        num6TV = (Button) findViewById(R.id.billSK_six);
        num7TV = (Button) findViewById(R.id.billSK_seven);
        num8TV = (Button) findViewById(R.id.billSK_eight);
        num9TV = (Button) findViewById(R.id.billSK_nine);
        num0TV = (Button) findViewById(R.id.billSK_zero);
        dotTV = (Button) findViewById(R.id.billSK_dot);
        backspaceTv = (Button) findViewById(R.id.billSK_backspace);
        currencyWrapper = (LinearLayout) findViewById(R.id.billSK_currencyWrapper);
        currencyTV = (TextView) findViewById(R.id.billSK_currency);
        currencyNameTV = (TextView) findViewById(R.id.billSK_currencyName);
        addLayout = (Button) findViewById(R.id.billSK_add);
        equalLayout = (Button) findViewById(R.id.billSK_equal);
        okTV = (Button) findViewById(R.id.billSK_ok);
    }

    private void initEventListener() {
        softkeyboardClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.billSK_one:
                        moneySKButtonListener.clickNum(1);
                        break;
                    case R.id.billSK_two:
                        moneySKButtonListener.clickNum(2);
                        break;
                    case R.id.billSK_three:
                        moneySKButtonListener.clickNum(3);
                        break;
                    case R.id.billSK_four:
                        moneySKButtonListener.clickNum(4);
                        break;
                    case R.id.billSK_five:
                        moneySKButtonListener.clickNum(5);
                        break;
                    case R.id.billSK_six:
                        moneySKButtonListener.clickNum(6);
                        break;
                    case R.id.billSK_seven:
                        moneySKButtonListener.clickNum(7);
                        break;
                    case R.id.billSK_eight:
                        moneySKButtonListener.clickNum(8);
                        break;
                    case R.id.billSK_nine:
                        moneySKButtonListener.clickNum(9);
                        break;
                    case R.id.billSK_zero:
                        moneySKButtonListener.clickNum(0);
                        break;
                    case R.id.billSK_dot:
                        moneySKButtonListener.clickNumAction(NumActionType.DOT);
                        break;
                    case R.id.billSK_backspace:
                        moneySKButtonListener.clickNumAction(NumActionType.BACKSPACE);
                        break;
                    case R.id.billSK_add:
                        showEqualBtn();
                        moneySKButtonListener.clickNumAction(NumActionType.ADD);
                        break;
                    case R.id.billSK_equal:
                        showOkBtn();
                        moneySKButtonListener.clickNumAction(NumActionType.EQUAL);
                        break;
                    case R.id.billSK_ok:
                        moneySKGlobalActionListener.clickGlobalAction(GlobalActionType.OK);
                        break;
                    case R.id.billSK_currencyWrapper:
                        moneySKGlobalActionListener.clickGlobalAction(GlobalActionType.CURRENCY);
                        break;
                }
            }
        };

        View[] views = new View[]{
                num1TV,
                num2TV,
                num3TV,
                currencyWrapper,
                num4TV,
                num5TV,
                num6TV,
                addLayout,
                num7TV,
                num8TV,
                num9TV,
                backspaceTv,
                num0TV,
                dotTV,
                okTV,
                equalLayout
        };
        for (View view : views) {
            view.setOnClickListener(softkeyboardClickListener);
        }

        globalActionsMap.put(GlobalActionType.CURRENCY, currencyWrapper);
    }

    public void setMoneySKButtonListener(MoneySKButtonListener moneySKButtonListener) {
        this.moneySKButtonListener = moneySKButtonListener;
    }

    public void setMoneySKGlobalActionListener(MoneySKGlobalActionListener moneySKGlobalActionListener) {
        this.moneySKGlobalActionListener = moneySKGlobalActionListener;
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    public void hide() {
        setVisibility(View.GONE);
    }

    public boolean isVisible() {
        return getVisibility() == View.VISIBLE;
    }

    public void showOkBtn() {
        equalLayout.setVisibility(GONE);
        okTV.setVisibility(VISIBLE);
    }

    public void showEqualBtn() {
        equalLayout.setVisibility(VISIBLE);
        okTV.setVisibility(GONE);
    }

    public void setMoney(String moneyStr) {
        this.moneyStr = moneyStr;
    }

    public void disableGlobalActionItems(GlobalActionType[] disabledActionTypes) {
        List<GlobalActionType> disabledActionTypeList;
        if (disabledActionTypes == null) {
            disabledActionTypeList = new ArrayList<>(0);
        } else {
            disabledActionTypeList = Arrays.asList(disabledActionTypes);
        }
        Set<GlobalActionType> globalActionTypes = globalActionsMap.keySet();
        for (GlobalActionType actionType : globalActionTypes) {
            View view = globalActionsMap.get(actionType);
            if (disabledActionTypeList.contains(actionType)) {
                view.setOnClickListener(null);
                view.setEnabled(false);
                if (actionType == GlobalActionType.CURRENCY) {
                    currencyTV.setAlpha(0.5f);
                    currencyNameTV.setAlpha(0.5f);
                }
            } else {
                view.setOnClickListener(softkeyboardClickListener);
                view.setEnabled(true);
                if (actionType == GlobalActionType.CURRENCY) {
                    currencyTV.setAlpha(1);
                    currencyNameTV.setAlpha(1);
                }
            }
        }
    }

    public void hideMoneyTypeView() {
        GlobalActionType[] disabledActionTypes = {GlobalActionType.CURRENCY};
        disableGlobalActionItems(disabledActionTypes);
    }

    public void showMoneyTypeView() {
        GlobalActionType[] disabledActionTypes = {};
        disableGlobalActionItems(disabledActionTypes);
    }

    public enum GlobalActionType {
        OK,
        CURRENCY
    }

    public enum NumActionType {
        DOT,
        BACKSPACE,
        ADD,
        EQUAL
    }

    public interface MoneySKButtonListener {

        public void clickNum(int num);

        public void clickNumAction(NumActionType numActionType);
    }

    public interface MoneySKGlobalActionListener {

        public void clickGlobalAction(GlobalActionType globalActionType);
    }

}
