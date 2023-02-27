package com.github.dwursteisen.beat.extensions

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array

inline fun <reified T> Collection<T>.asGdx(): Array<T> {
    return Array(toTypedArray())
}

fun <T> List<T>.pickOne(): T {
    val index = MathUtils.random(this.size - 1)
    return this.elementAt(index)
}