package com.app.numbercounterview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NumberCounterView extends RelativeLayout {

    private Context context;
    private AttributeSet attributeSet;
    private int defStyleAttr;

    private int initialNumber;
    private int maxValue;
    private int previousValue;

    private LinearLayout counterLayout;
    private TextView txtCounter;
    private ImageView imgAdd;
    private ImageView imgSub;
    private OnValueChangeListener onValueChangeListener;

    public NumberCounterView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public NumberCounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attributeSet = attrs;
        init();
    }

    public NumberCounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attributeSet = attrs;
        this.defStyleAttr = defStyleAttr;
        init();
    }

    private void init() {

        inflate(context, R.layout.layout_add_sub, this);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.NumberCounterView, defStyleAttr, 0);

        initialNumber = typedArray.getInt(R.styleable.NumberCounterView_initialValue, 0);
        maxValue = typedArray.getInt(R.styleable.NumberCounterView_maxValue, Integer.MAX_VALUE);

        Drawable backGroundDrawable = typedArray.getDrawable(R.styleable.NumberCounterView_backgroundDrawable);
        int backGroundColor = typedArray.getColor(R.styleable.NumberCounterView_backgroundColor, ContextCompat.getColor(context, R.color.transparent));

        Drawable backgroundDrawableAdd = typedArray.getDrawable(R.styleable.NumberCounterView_backgroundDrawableAdd);
        Drawable backgroundDrawableSub = typedArray.getDrawable(R.styleable.NumberCounterView_backgroundDrawableSub);
        Drawable imageResourceAdd = typedArray.getDrawable(R.styleable.NumberCounterView_srcAddButton);
        Drawable imageResourceSub = typedArray.getDrawable(R.styleable.NumberCounterView_srcSubButton);

        int textColor = typedArray.getColor(R.styleable.NumberCounterView_textColor, getResources().getColor(R.color.text_color));
        int textSize = typedArray.getDimensionPixelSize(R.styleable.NumberCounterView_textSize, 14);
        int paddingCounterLeft = typedArray.getDimensionPixelSize(R.styleable.NumberCounterView_counterPaddingLeft, 0);
        int paddingCounterRight = typedArray.getDimensionPixelSize(R.styleable.NumberCounterView_counterPaddingRight, 0);
        int tintAdd = typedArray.getColor(R.styleable.NumberCounterView_tintAdd, getResources().getColor(android.R.color.transparent));
        int tintSub = typedArray.getColor(R.styleable.NumberCounterView_tintSub, getResources().getColor(android.R.color.transparent));


        if (imageResourceAdd == null)
            imageResourceAdd = getResources().getDrawable(R.drawable.ic_plus);
        if (imageResourceSub == null)
            imageResourceSub = getResources().getDrawable(R.drawable.ic_sub);

        if (backGroundDrawable == null) {
            backGroundDrawable = getResources().getDrawable(R.drawable.round_circle_lib);
        }

        counterLayout = findViewById(R.id.rl_number_holder);
        counterLayout.setBackgroundColor(backGroundColor);
        counterLayout.setBackground(backGroundDrawable);

        txtCounter = findViewById(R.id.txt_counter);
        imgAdd = findViewById(R.id.img_add);
        imgSub = findViewById(R.id.img_sub);


        txtCounter.setText(String.valueOf(initialNumber));
        txtCounter.setTextColor(textColor);
        txtCounter.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        txtCounter.setPadding(paddingCounterLeft, 0, paddingCounterRight, 0);

        imgAdd.setImageDrawable(imageResourceAdd);
        imgSub.setImageDrawable(imageResourceSub);

        imgAdd.setBackground(backgroundDrawableAdd);
        imgSub.setBackground(backgroundDrawableSub);

        Log.i("TintAdd", String.valueOf(tintAdd));
        Log.i("TintSub", String.valueOf(tintSub));

        if (tintAdd != 0)
            imgAdd.setColorFilter(tintAdd, android.graphics.PorterDuff.Mode.SRC_IN);
        if (tintSub != 0)
            imgSub.setColorFilter(tintSub, android.graphics.PorterDuff.Mode.SRC_IN);

        imgAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                previousValue = getCounterValue(txtCounter);
                txtCounter.setText(String.valueOf(getCounterValue(txtCounter) + 1 > maxValue ?
                        maxValue : getCounterValue(txtCounter) + 1));
                callOnValueChange(v, previousValue, getCounterValue(txtCounter));
            }
        });

        imgSub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                previousValue = getCounterValue(txtCounter);
                txtCounter.setText(String.valueOf(getCounterValue(txtCounter) - 1 < initialNumber ?
                        initialNumber : getCounterValue(txtCounter) - 1));
                callOnValueChange(v, previousValue, getCounterValue(txtCounter));
            }
        });
        typedArray.recycle();
    }

    private void callOnValueChange(View view, int previousValue, int currentValue) {
        if (onValueChangeListener != null) {
            onValueChangeListener.onValueChange(view, previousValue, currentValue);
        }
    }

    private int getCounterValue(TextView view) {
        return Integer.valueOf(view.getText().toString());
    }

    public int getValue() {
        return Integer.valueOf(txtCounter.getText().toString());
    }

    public void setValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }

    public void setCounterBackground(Drawable drawable) {
        counterLayout.setBackground(drawable);
    }

    public void setCounterBackgroundColor(int resColor) {
        counterLayout.setBackgroundColor(resColor);
    }

    public void setBackgroundDrawableAdd(Drawable drawable) {
        imgAdd.setImageDrawable(drawable);
    }


    public void setBackgroundDrawableSub(Drawable drawable) {
        imgSub.setImageDrawable(drawable);
    }

    public void setInitialValue(int initialValue) {
        txtCounter.setText(String.valueOf(this.initialNumber = initialValue));
    }

    public void setMaxValue(int maxValue) throws CounterException {

        if (maxValue <= initialNumber)
            throw new CounterException(context.getString(R.string.max_value));
        else
            this.maxValue = maxValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setCounterPadding(int paddingLeft, int paddingRight) {
        txtCounter.setPadding(paddingLeft, 0, paddingRight, 0);
    }

    public void setAddButtonRes(Drawable drawable) {
        imgAdd.setImageDrawable(drawable);
    }

    public void setSubButtonRes(Drawable drawable) {
        imgSub.setImageDrawable(drawable);
    }

    public void setTextColor(int textColor) {
        txtCounter.setTextColor(textColor);
    }

    public void setTextSize(float textSize) {
        txtCounter.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    public void setAddTint(int color) {
        imgAdd.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public void setSubTint(int color) {
        imgSub.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);
    }
}

