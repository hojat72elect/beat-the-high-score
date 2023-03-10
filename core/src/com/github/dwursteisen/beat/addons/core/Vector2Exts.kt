package com.github.dwursteisen.beat.addons.core

import com.badlogic.gdx.math.Vector2


infix fun Number.v2(other: Number): Vector2 {
    return Vector2(this.toFloat(), other.toFloat())
}