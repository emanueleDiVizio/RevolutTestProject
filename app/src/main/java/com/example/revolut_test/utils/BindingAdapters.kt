package com.example.revolut_test.utils

import android.graphics.Color
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

object BindingAdapters {
    @BindingAdapter("textChangedListener")
    @JvmStatic
    fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher?) {
        editText.addTextChangedListener(textWatcher)
    }

    @BindingAdapter("onClickListener")
    @JvmStatic
    fun onClickListener(editText: EditText, listener: () -> Unit) {
        editText.setOnClickListener { listener.invoke() }
    }

    @BindingAdapter("shouldPreventFocus")
    @JvmStatic
    fun shouldPreventFocus(editText: EditText, condition: Boolean) {


        if (condition) {
            editText.isFocusableInTouchMode = false

            editText.isFocusable = false
            editText.setTextColor(Color.BLACK)
        } else {
            editText.isFocusableInTouchMode = true
            editText.isFocusable = true
        }
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun setImageUrl(view: ImageView, url: String) {
        Glide.with(view.context).load(url).apply(RequestOptions().dontAnimate()).transition(DrawableTransitionOptions().dontTransition()).into(view)
    }
}