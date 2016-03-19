package com.linzon.ru.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView {
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if(text != null) {
            if (text.length() > 0) {
                text = String.valueOf(text.charAt(0)).toUpperCase() + text.subSequence(1, text.length());
            }
        } else
            this.setVisibility(GONE);
        super.setText(text, type);
    }
}