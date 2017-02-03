package com.example.lz.tally.customview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lz.tally.R;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Created by liz on 16-12-21.
 */

public class MoneyTextView extends EditText {

    private static final String LOG_TAG = "MoneyTextView";
    private static final String DEFAULT_MONEY = "0.00";
    private String initMoney;
    private int selection = 0;
    private static final int DEFAULT_INTEGER_FONT_SIZE = 27;
    private static final int DEFAULT_DOT_FONT_SIZE = 21;
    private static final boolean DEFAULT_NEED_FLAG = true;
    private View.OnClickListener clickActionListener;
    private TextWatcher textWatcher;
    private Context context;
    private Editable.Factory editableFacotry = Editable.Factory.getInstance();
    private Editable moneyEditable = editableFacotry.newEditable("");
    private static final String DIVIDER = ",";
    private boolean isEnable = true;
    private static final int DEFAULT_INTEGER_NUM = 10;//默认整数位8位
    private static final int DEFAULT_DECIMAL_NUM = 2;//默认小数位2位
    private int integerNum;//整数位
    private int decimalNum;//小数位
    private static final double MAX_NUM = 9999999999.99;//允许输入的最大数字
    private boolean needFlag;//是否需要显示货币符号
    private int integerFontSize;
    private int decimalPlaceFontSize;
    private int intialIntegerFontSize;
    private int intialDecimalPlaceFontSize;
    private int focusTextColor;
    private int unfocusTextColor;
    private int disableTextColor;
    private int defaultTextColor;
    private int defaultColorId;
    private AttributeSet attributeSet;
    private int minDecimalNum = 0;//小数位最小支持多少位
    private int width;//view宽度
    private boolean isCursorVisibleEnable = true;
    private boolean isNeedResize = true;//是否需要根据宽度变化大小

    public MoneyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        attributeSet = attrs;
        init();
    }

    public MoneyTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setSingleLine();
        initData();
        initAttributes();
        initEventListener();
        initMoneyET();
        setBackgroundDrawable(null);
        setInputType(InputType.TYPE_CLASS_TEXT);
        setTextIsSelectable(true);
        setLongClickable(false);
        setPadding(0, 0, 0, 0);
		/*
		默认不显示光标 当拥有focus的时候再置光标为显示
		cursor will show when focusing
		 */
        setCursorVisible(false);
    }

    private void initAttributes() {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.MoneyTextView);
        if (typedArray == null) {
            return;
        }
        try {
            integerFontSize = typedArray.getDimensionPixelSize(R.styleable.MoneyTextView_integerFontSize, DEFAULT_INTEGER_FONT_SIZE);
            decimalPlaceFontSize = typedArray.getDimensionPixelSize(R.styleable.MoneyTextView_decimalPlaceFontSize, DEFAULT_DOT_FONT_SIZE);
            needFlag = typedArray.getBoolean(R.styleable.MoneyTextView_needFlag, DEFAULT_NEED_FLAG);
            decimalNum = typedArray.getInt(R.styleable.MoneyTextView_decimalNum, DEFAULT_DECIMAL_NUM);
            integerNum = typedArray.getInt(R.styleable.MoneyTextView_integerNum, DEFAULT_INTEGER_NUM);
            defaultTextColor = typedArray.getColor(R.styleable.MoneyTextView_defaultTextColor, defaultColorId);
            focusTextColor = typedArray.getColor(R.styleable.MoneyTextView_focusTextColor, defaultColorId);
            unfocusTextColor = typedArray.getColor(R.styleable.MoneyTextView_unfocusTextColor, defaultColorId);
            disableTextColor = typedArray.getColor(R.styleable.MoneyTextView_disableTextColor, defaultColorId);
            initMoney = typedArray.getString(R.styleable.MoneyTextView_initialMoneyStr);

        } finally {
            typedArray.recycle();
        }
        if (initMoney == null) {
            initMoney = DEFAULT_MONEY;
        }
        intialIntegerFontSize = integerFontSize;
        intialDecimalPlaceFontSize = decimalPlaceFontSize;
        setTextSize(TypedValue.COMPLEX_UNIT_PX, integerFontSize);
    }

    private void initData() {
        context = getContext();
        ColorStateList colorStateList = getTextColors();
        defaultColorId = colorStateList.getDefaultColor();
    }

    private void initMoneyET() {
        moneyEditable.replace(0, moneyEditable.length(), DEFAULT_MONEY);
        this.setText(parseEditableToSpannString(moneyEditable), TextView.BufferType.SPANNABLE);
    }

    private void initEventListener() {
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = getWidth();
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (isCursorVisibleEnable) {
                        setCursorVisible(true);
                    }
                    if (clickActionListener != null) {
                        clickActionListener.onClick(v);
                    }
                } else {
                    setCursorVisible(false);
                }
            }
        });

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextColor(focusTextColor);
                if (isCursorVisibleEnable) {
                    setCursorVisible(true);
                }
                if (clickActionListener != null) {
                    clickActionListener.onClick(v);
                }
            }
        });

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (textWatcher != null) {
                    textWatcher.beforeTextChanged(s, start, count, after);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textWatcher != null) {
                    textWatcher.onTextChanged(s, start, before, count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (textWatcher != null) {
                    textWatcher.afterTextChanged(s);
                }
                if (isNeedResize) {
                    resizeMoneyTextView();
                }
            }
        });

        this.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }

    @Override
    public boolean isSuggestionsEnabled() {
        return false;
    }

    /**
     * http://stackoverflow.com/questions/27869983/edittext-disable-paste-replace-menu-pop-up-on-text-selection-handler-click-even/28893714#28893714
     * This is a replacement method for the base TextView class' method of the same name. This
     * method is used in hidden class android.widget.Editor to determine whether the PASTE/REPLACE popup
     * appears when triggered from the text insertion handle. Returning false forces this window
     * to never appear.
     * @return false
     */
    boolean canPaste() {
        return false;
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        setSelection(getText().length());
    }

    public void setMinDecimalNum(int minDecimalNum) {
        this.minDecimalNum = minDecimalNum;
    }

    public double getMoney()  {
        return parse(getMoneyStr());
    }

    /**
     * @param money
     */
    public void setMoney(double money) {
        String moneyStr = formatMoney(money);
        int selection = getMoneySelection(money);
        setMoneyStr(moneyStr,selection);
    }

    /**
     * 得到不带有千分位符号和货币符号的金额字符串
     * get the money str without the symbol of thousands and flag
     * @return
     */
    public String getMoneyStr() {
        String moneyStr = moneyEditable.toString();
        int addIndex = moneyStr.indexOf("+");
        if (addIndex != -1) {
            if (addIndex == moneyStr.length() - 1) {
                moneyStr = moneyStr.substring(0, addIndex);
            } else {
                String[] moneyArray = moneyStr.split("\\+");
                double sum = 0;
                for (String money : moneyArray) {
                    sum += parse(money);
                }
                moneyStr = formatMoney(sum);
            }
        }
        return moneyStr;
    }

    /**
     * @param moneyStr 代表数字的字符串，可以包含加号 the money string for inputing
     * @param selection 代表当前输入位置的坐标 the position of inputing money currently
     */
    public void setMoneyStr(String moneyStr, int selection) {
        try {
            moneyStr = formatMoney(Double.parseDouble(moneyStr));
        } catch (NumberFormatException e) {
            Log.e(LOG_TAG, "setMoneyStr parseDouble Error:" + e);
        }
        moneyEditable.replace(0, moneyEditable.length(), moneyStr);
        this.selection = selection;
        this.setText(parseEditableToSpannString(moneyEditable));
    }

    /**
     * @param moneyStr
     */
    public void setMoneyStr(String moneyStr) {
        try {
            moneyStr = formatMoney(Double.parseDouble(moneyStr));
        } catch (NumberFormatException e) {
            Log.e(LOG_TAG, "setMoneyStr parseDouble Error:" + e);
        }

        moneyEditable.replace(0, moneyEditable.length(), moneyStr);
        this.selection = getMoneySelection(moneyStr);
        this.setText(parseEditableToSpannString(moneyEditable));
    }

    public void setMoneyTextColor(int color) {
        setTextColor(color);
        this.setText(parseEditableToSpannString(moneyEditable));
    }

    public void setEnable() {
        isEnable = true;
        this.setText(parseEditableToSpannString(moneyEditable));
        setEnabled(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public void setDisable() {
        isEnable = false;
        this.setText(parseEditableToSpannString(moneyEditable));
        setEnabled(false);
        setFocusable(false);
        setFocusableInTouchMode(false);
    }

    public void setTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    public void clickNum(String insertStr) {
        Editable editable = moneyEditable;
        String str = editable.toString();
        int selection = getMoneyTextViewSelection();
        if (selection >= str.length()) {
            return;
        }
        if (selection == 0 && str.equals(DEFAULT_MONEY)) {
            editable.replace(selection, editable.length(), "0");
            str = editable.toString();
        }
        if (selection == 0) {
            //针对初始为0的情况 进行替换操作 不需要移动坐标
            //replacing zero not inserting number when the moneystr is just zero
            if (str.charAt(selection) == '0') {
                editable.replace(selection, selection + 1, insertStr);
            } else {
                editable.insert(selection + 1, insertStr);
                setMoneyTextViewSelection(selection + 1);
            }

        } else {
            //若包含加号 if moneystr contains add
            if (str.contains("+")) {
                int addIndex = str.indexOf("+");
                //若包含小数点 if moneystr contains dot
                if (str.contains(".")) {
                    int dotIndex = str.lastIndexOf(".");
                    //若加号后面存在小数点 if dot exists after add
                    if (dotIndex > addIndex) {
                        if (selection - dotIndex <= decimalNum - 1) {
                            editable.insert(selection + 1, insertStr);
                            setMoneyTextViewSelection(selection + 1);
                        }
                    } else {
                        if (selection - addIndex <= integerNum - 1) {
                            editable.insert(selection + 1, insertStr);
                            setMoneyTextViewSelection(selection + 1);
                        }
                    }
                } else {
                    if (selection - addIndex <= integerNum - 1) {
                        editable.insert(selection + 1, insertStr);
                        setMoneyTextViewSelection(selection + 1);
                    }
                }

            } else {
                //若不包含加号 if not contain add
                if (str.contains(".")) {
                    //如果包含点号 if contain dot
                    //若存在两个点 则取最后一个点 if we have two dots,then get the last dot
                    int dotIndex = str.lastIndexOf(".");
                    if (selection - dotIndex <= decimalNum - 1) {
                        editable.insert(selection + 1, insertStr);
                        setMoneyTextViewSelection(selection + 1);
                    }
                } else {
                    //不包含小数点 直接进行插入 注意这里是小于号而不是小于等于号
                    //insert when there is no dot
                    if (selection < integerNum - 1) {
                        editable.insert(selection + 1, insertStr);
                        setMoneyTextViewSelection(selection + 1);
                    }
                }
            }
        }

        this.setText(parseEditableToSpannString(editable));
    }

    public void delStr() {
        Editable editable = moneyEditable;
        String str = editable.toString();
        int selection = getMoneyTextViewSelection();
        if (str.length() == 0) {
            return;
        }
        if (selection > str.length()) {
            return;
        }
        if (str.equals("0")) {
            return;
        }
        if (selection == 0) {
            editable.replace(selection, selection + 1, "0");
        } else {
            editable.delete(selection, selection + 1);
            setMoneyTextViewSelection(selection - 1);
        }

        this.setText(parseEditableToSpannString(editable));
    }

    public void clickDot() {
        Editable editable = moneyEditable;
        String str = editable.toString();
        int selection = getMoneyTextViewSelection();
        if (selection >= str.length()) {
            return;
        }
        if (selection == 0 && str.equals(DEFAULT_MONEY)) {
            editable.replace(selection, editable.length(), "0");
            str = editable.toString();
        }
        //如果当前selection在+号上面 则点击点号 则直接插入0.
        //if the char of position of current string is add,When you input dot,then insert zero
        if (str.charAt(selection) == '+') {
            setMoneyTextViewSelection(selection + 2);
            editable.insert(selection + 1, formatMoneyStr("0.", focusTextColor, integerFontSize, decimalPlaceFontSize));
            this.setText(parseEditableToSpannString(editable));
        } else {
            //若当前str不包含点 则直接插入点
            //insert dot when the current string not contain dot
            if (!str.contains(".")) {
                editable.insert(selection + 1, ".");
                setMoneyTextViewSelection(selection + 1);
                this.setText(parseEditableToSpannString(editable));
            } else {
                /*
                    若当前str包含点 但不包含加号 则不能进行插入
                    若当前str包含点 且包含加号 但是点在加号之后 则不能进行插入
                    if the current string contain dot and not contain add,then you can't input
                    if the current string contain add and dot behind add,then you can't input
                 */
                if (str.contains("+")) {
                    int dotIndex = str.lastIndexOf(".");
                    int addIndex = str.indexOf("+");
                    if (dotIndex < addIndex) {
                        editable.insert(selection + 1, ".");
                        setMoneyTextViewSelection(selection + 1);
                        this.setText(parseEditableToSpannString(editable));
                    }

                }
            }
        }
    }

    /**
     * 目前只支持输入一个加号
     * now you can input just one add
     */
    public void clickAdd() {
        integerFontSize = intialIntegerFontSize;
        decimalPlaceFontSize = intialDecimalPlaceFontSize;
        Editable editable = moneyEditable;
        String str = editable.toString();
        String moneyArray[];
        double moneySum = 0.0;
        if (str.length() != 0 && str.charAt(str.length() - 1) != '+') {
            if (str.contains("+")) {
                moneyArray = str.split("\\+");
                moneySum = parse(moneyArray[0]) + parse(moneyArray[1]);
                if (moneySum > MAX_NUM) {
                    moneySum = MAX_NUM;
                }
                str = formatMoney(moneySum);
            }
            String moneyStr = str + "+";
            editable.replace(0, editable.length(), moneyStr);
            setMoneyTextViewSelection(moneyStr.indexOf("+"));
            this.setText(parseEditableToSpannString(editable));
        }
    }

    public void clickEqual() {
        integerFontSize = intialIntegerFontSize;
        decimalPlaceFontSize = intialDecimalPlaceFontSize;
        Editable editable = moneyEditable;
        String str = editable.toString();
        String[] moneyArray = str.split("\\+");
        double moneySum = 0.0;
        for (String moneyStr : moneyArray) {
            moneySum += parse(moneyStr);
        }
        if (moneySum > MAX_NUM) {
            moneySum = MAX_NUM;
        }
        String moneyStr = formatMoney(moneySum);
        editable.replace(0, editable.length(), moneyStr);
        setMoneyTextViewSelection(editable.length() - 1);
        this.setText(parseEditableToSpannString(editable));
    }

    /**
     * 将金额edtiable 转换为对应格式的string
     * 需要处理的情况包括多个数字通过加号拼接的情况
     * 同时需要处理货币符号的问题
     * convert the editable of money to the formated string that we need
     */
    private SpannableString parseEditableToSpannString(Editable editable) {
        String str = editable.toString();
        StringBuilder sb = new StringBuilder();
        String flag = "";
        int currentColorId;
        if (str.contains("+")) {
            String[] moneyArray = str.split("\\+");
            for (int i = 0, len = moneyArray.length; i < len; i++) {
                sb.append(moneyArray[i]);
                if (i != len - 1 || len == 1) {
                    sb.append("+");
                }
            }
        } else {
            sb.append(str);
        }
        if (isEnable) {
            if (str.equals(DEFAULT_MONEY) && selection == 0) {
                currentColorId = defaultTextColor;
            } else {
                currentColorId = focusTextColor;
            }
        } else {
            currentColorId = disableTextColor;
        }
        return formatMoneyStr(sb.toString(), currentColorId, integerFontSize, decimalPlaceFontSize);
    }

    public int getMoneyTextViewSelection() {
        return selection;
    }

    public void setMoneyTextViewSelection(int selection) {
        this.selection = selection;
    }

    public Editable getMoneyEditable() {
        return moneyEditable;
    }

    public boolean isNeedFlag() {
        return needFlag;
    }

    public void setNeedFlag(boolean needFlag) {
        this.needFlag = needFlag;
    }

    //清空数据 reset the money and selection
    public void clear() {
        selection = 0;
        initMoneyET();
    }

    public void setClickActionListener(View.OnClickListener clickActionListener) {
        this.clickActionListener = clickActionListener;
    }

    /**
     * 根据给定的money值得到selection的位置
     * get selection base on money
     * @param money
     * @return
     */
    public int getMoneySelection(double money) {
        String moneyStr = formatMoney(money);
        if (moneyStr.length() == 1) {
            return 0;
        } else {
            return moneyStr.length() - 1;
        }
    }

    private int getMoneySelection(String moneyStr) {
        if (moneyStr.length() == 1) {
            return 0;
        } else {
            return moneyStr.length() - 1;
        }
    }

    /**
     * 根据给定的money值得到moneyStr
     * 将xx.0转换为xx x不为0
     * 将xx.x0转换为xx.x
     * get moneystr base on money
     * this function convert xx.0 to xx
     * convert xx.x0 to xx.x
     * the x is not zero
     * @param moneySum
     * @return
     */
    private String formatMoney(double moneySum) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(decimalNum);
        nf.setMinimumFractionDigits(minDecimalNum);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        nf.setGroupingUsed(false);
        return nf.format(moneySum);
    }


    private void resizeMoneyTextView() {
        int contentWidth = getTextViewWidth();
        if (width == 0) {
            return;
        }
        if (contentWidth > width) {
            do {
                integerFontSize--;
                decimalPlaceFontSize--;
                contentWidth = getTextViewWidth();
            } while (contentWidth > width);
        } else {
            integerFontSize = intialIntegerFontSize;
            decimalPlaceFontSize = intialDecimalPlaceFontSize;
        }
    }

    /**
     * 根据内容宽度 和总宽度计算出实际的字体大小
     * calculate the width of textview base on the inputing
     */
    private int getTextViewWidth() {
        Rect bounds = new Rect();
        Paint textPaint = this.getPaint();
        textPaint.setTextSize(integerFontSize);
        String text = getText().toString();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        int height = bounds.height();
        int width = bounds.width();
        return width;
    }

    public void setIsCursorVisible(boolean isCursorVisible) {
        this.isCursorVisibleEnable = isCursorVisible;
        setCursorVisible(isCursorVisible);
    }

    public void setIsNeedResize(boolean isNeedResize) {
        this.isNeedResize = isNeedResize;
    }

    private double parse(String str) {
        String amountS = str.replace(DIVIDER, "");
        boolean isNegative = false;
        if (amountS.charAt(amountS.length() - 1) == '+') {
            amountS = amountS.substring(0, amountS.length() - 1);
        }
        //省略币种位 ignore the flag
        if (str.charAt(0) != 45 && (str.charAt(0) < 48 || str.charAt(0) > 57)) {
            amountS = amountS.substring(1);
        }
        if (str.charAt(0) == 45) {
            amountS = amountS.substring(1);
            isNegative = true;
        }
        if (amountS.equals("")) {
            amountS = "0";
        }
        if (isNegative) {
            return -Double.valueOf(amountS);
        } else {
            return Double.valueOf(amountS);
        }
    }

    public SpannableString formatMoneyStr(String moneyStr, int color, int integerFontSize, int decimalPlaceFontSize) {
        int commaIndex = moneyStr.indexOf(".");
        SpannableString spanText = new SpannableString(moneyStr);
        float proportion = (float) decimalPlaceFontSize / integerFontSize;

        if (moneyStr.contains("+")) {
            int lastCommaIndex = moneyStr.lastIndexOf(".");
            if (commaIndex != lastCommaIndex) {
                spanText.setSpan(
                        new RelativeSizeSpan(proportion),
                        lastCommaIndex + 1,
                        Math.min(lastCommaIndex + 3, spanText.length()),
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }

        spanText.setSpan(
                new RelativeSizeSpan(proportion),
                commaIndex + 1,
                Math.min(commaIndex + 3, spanText.length()),
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        spanText.setSpan(new ForegroundColorSpan(color), 0, spanText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanText;
    }
}
