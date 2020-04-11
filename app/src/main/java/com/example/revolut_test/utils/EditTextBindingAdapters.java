package com.example.revolut_test.utils;

import android.text.TextWatcher;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

public class EditTextBindingAdapters {

    @BindingAdapter("textChangedListener")
    public static void bindTextWatcher(EditText editText, TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }
}