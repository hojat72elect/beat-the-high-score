package com.github.dwursteisen.beat.extensions

import com.badlogic.gdx.utils.Array

inline fun <reified T> Collection<T>.asGdx(): Array<T> {
    return Array(toTypedArray())
}
