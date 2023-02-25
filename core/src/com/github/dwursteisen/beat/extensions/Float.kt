package com.github.dwursteisen.beat.extensions

fun Float.between(min: Float, max: Float): Boolean {
    return this in min..max
}
