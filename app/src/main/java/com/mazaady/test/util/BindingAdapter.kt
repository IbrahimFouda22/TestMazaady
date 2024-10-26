package com.mazaady.test.util

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.mazaady.test.ext_fun.gone
import com.mazaady.test.ext_fun.visible

@BindingAdapter("bindProgress")
fun bindProgress(progressBar: ProgressBar, boolean: Boolean) {
    if (boolean) progressBar.visible()
    else progressBar.gone()
}
@BindingAdapter("bindVisibility")
fun bindVisibility(view: View, boolean: Boolean) {
    if (boolean) view.visible()
    else view.gone()
}