package com.ziv_nergal.gerva

import android.content.res.Resources
import android.util.TypedValue

fun Int.toDp(): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()