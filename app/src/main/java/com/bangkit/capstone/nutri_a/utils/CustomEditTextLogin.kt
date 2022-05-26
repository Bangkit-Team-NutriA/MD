package com.bangkit.capstone.nutria.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import com.bangkit.capstone.nutri_a.R

class CustomEditTextLogin : AppCompatEditText {

    var type = ""

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (type == "email") {
                    if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                        error = context.getString(R.string.email_warning)
                    }
                } else if(type == "password") {
                    if (s.isEmpty()) {
                        error = context.getString(R.string.password_null)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

}