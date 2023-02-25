package com.github.dwursteisen.beat.extensions

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

fun Rectangle.set(position: Vector2, size: Vector2): Rectangle {
    return this.set(position.x, position.y, size.x, size.y)
}
