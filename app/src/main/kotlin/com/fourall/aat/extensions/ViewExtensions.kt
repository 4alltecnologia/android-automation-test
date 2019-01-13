package com.fourall.aat.extensions

import android.view.View

fun View.fadeVisibility(show: Boolean) {
    val animationTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

    if (show) {
        this.visibility = View.VISIBLE
        this.animate()
            .alpha(1f)
            .setDuration(animationTime).start()
    } else {
        this.animate()
            .alpha(0f)
            .setDuration(animationTime).start()
        this.visibility = View.GONE
    }
}