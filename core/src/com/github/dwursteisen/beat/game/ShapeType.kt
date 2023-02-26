package com.github.dwursteisen.beat.game

/**
 * All the different types of shapes that you are going to draw in this game.
 */
sealed class ShapeType(val filed: Boolean) {
    object FilledRectangle : ShapeType(true)
    object FilledCircle : ShapeType(true)
    object Rectangle : ShapeType(false)
    object Circle : ShapeType(false)
}