package com.example.permissionmanager.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

object ToastUtil {

    fun shortToast(context: Context, @StringRes resId: Int) {
        Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show()
    }

}