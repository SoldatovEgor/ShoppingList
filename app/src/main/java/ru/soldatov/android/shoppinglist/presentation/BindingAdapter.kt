package ru.soldatov.android.shoppinglist.presentation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import ru.soldatov.android.shoppinglist.R

@BindingAdapter("errorInputName")
fun bindInputTextErrorName(inputTextError: TextInputLayout, value: Boolean) {
    val message = if (value) {
        inputTextError.context.getString(R.string.invalid_name)
    } else {
        null
    }
    inputTextError.error = message
}

@BindingAdapter("errorInputCount")
fun bindInputTextErrorCount(inputTextError: TextInputLayout, value: Boolean) {
    val message = if (value) {
        inputTextError.context.getString(R.string.invalid_count)
    } else {
        null
    }
    inputTextError.error = message
}