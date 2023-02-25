package com.github.dwursteisen.beat.extensions

val Double.seconds: Float
    get() = this.toFloat()